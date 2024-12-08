import './create-group-pop-up.css';
import Button from '../../tools/button/button';
import Input from '../../tools/input/input';
import ToggleSwitch from '../../tools/toggle-switch/toggle-switch';
import { useState } from 'react';

function CreateGroupPopUp({onClose, closing, setValidationCreatePopUpVisible, setGroupId}) {

  const [isToggleSwitchOn, setIsToggleSwitchOn] = useState(false);
  const [selectedRestaurant, setSelectedRestaurant] = useState('');
  const [groupName, setGroupName] = useState('');
  const [deliveryLocation, setDeliveryLocation] = useState('');
  const [deliveryTime, setDeliveryTime] = useState({ hours: '', minutes: '' });
    // Liste des restaurants (à récupérer via l'API des restautants)
    const restaurantOptions = [
        'McDonald\'s',
        'KFC',
        'Pizza Hut',
        'Subway',
        'Burger King',
    ];

  const handleToggleSwitch = () => {
    setIsToggleSwitchOn((prevState) => !prevState);
  };

  const handleRestaurantChange = (event) => {
      setSelectedRestaurant(event.target.value);
  }



    const handleCreateClick = () => {
        if (!groupName || !deliveryLocation || !selectedRestaurant) {
            alert('Fill all the fields before creating the group !');
            return;
        }

        let deliveryDate = null;
        if (isToggleSwitchOn) {
            const currentDate = new Date();
            const deliveryHours = parseInt(deliveryTime.hours, 10) || 0;
            const deliveryMinutes = parseInt(deliveryTime.minutes, 10) || 0;

            // Créez une date avec les heures et minutes spécifiés
            currentDate.setHours(deliveryHours);
            currentDate.setMinutes(deliveryMinutes);
            currentDate.setSeconds(0);
            currentDate.setMilliseconds(0);

            deliveryDate = currentDate.getTime(); // en millisecondes
        }

        const groupData = {
            groupName: groupName,
            deliveryLocation: deliveryLocation,
            restaurant: {
                name: selectedRestaurant,
                discountType: "LOYALTY", // Par défaut, ajustez si besoin
            },
            deliveryDate: deliveryDate,
        };

        console.log('Group Data to be sent:', groupData);


        onClose();
        setTimeout(() => {
            setValidationCreatePopUpVisible(true);
        }, 300);


        fetch('http://localhost:8001/api/group/create', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(groupData),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then((data) => {
                if (data.id) {
                    setGroupId(data.id);
                    setValidationCreatePopUpVisible(true);
                } else {
                    console.error('Error: ID not returned by the API');
                }
            })
            .catch((error) => {
                console.error('API Request Failed:', error);
                alert('Failed to create group. Please try again later.');
            });

    };

  return (
    <div className={`create-group-container ${closing ? 'closing' : ''} ${isToggleSwitchOn ? 'expanded' : 'collapsed'}`}>
        <div className="popup-content">
            <i className="fa-solid fa-xmark" onClick={onClose}></i>
            <h4 className='popup-title'>Create Group Order</h4>
            <div className="separation-line"></div>
            <div className={`input-button-container ${isToggleSwitchOn ? 'expanded' : 'collapsed'}`}>
                <Input placeholder="Group Name" value={groupName} onChange={(e) => setGroupName(e.target.value)}
                />
                <Input placeholder="Group Delivery Location" value={deliveryLocation} onChange={(e) => setDeliveryLocation(e.target.value)} />
                <div className="dropdown-container">
                    <label htmlFor="restaurant-select" className="dropdown-label">Group Restaurant: </label>
                    <select
                        id="restaurant-select"
                        value={selectedRestaurant}
                        onChange={handleRestaurantChange}
                        className="restaurant-dropdown"
                    >
                        <option value="" disabled>Select a restaurant</option>
                        {restaurantOptions.map((restaurant, index) => (
                            <option key={index} value={restaurant}>
                                {restaurant}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="delivery-time-container">
                    <div className="delivery-time-header">
                        <h5 className='delivery-time-text'>Choose Delivery Time</h5>
                        <ToggleSwitch onChange={handleToggleSwitch}/>
                    </div>
                    { isToggleSwitchOn && 
                        <div className="delivery-time-content">
                            <Input placeholder="Heure" value={deliveryTime.hours} onChange={(e) => setDeliveryTime({ ...deliveryTime, hours: e.target.value })} />
                            <Input placeholder="Minutes" value={deliveryTime.minutes} onChange={(e) => setDeliveryTime({ ...deliveryTime, minutes: e.target.value })} />
                        </div>
                    }
                </div>
                <Button text="Create Group Order" onClick={handleCreateClick}/>
            </div>
        </div>
    </div>
  );
}

export default CreateGroupPopUp;
