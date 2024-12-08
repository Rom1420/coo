import './create-group-pop-up.css';
import Button from '../../tools/button/button';
import Input from '../../tools/input/input';
import ToggleSwitch from '../../tools/toggle-switch/toggle-switch';
import { useState } from 'react';
import Dropdown from '../../tools/dropdown/dropdown';
import RestaurantList from '../../restaurants-list/restaurants-list';

function CreateGroupPopUp({onClose, closing, setValidationCreatePopUpVisible, setGroupId}) {

<<<<<<< HEAD
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
=======
    const [isToggleSwitchOn, setIsToggleSwitchOn] = useState(false);
    const [selectedRestaurant, setSelectedRestaurant] = useState('');
    const [groupName, setGroupName] = useState('');
    const [deliveryLocation, setDeliveryLocation] = useState('');
    const [deliveryTime, setDeliveryTime] = useState({ hours: '', minutes: '' });
    const [isRestaurantListVisible, setIsRestaurantListVisible] = useState(false);
    const [isHidden, setIsHidden] = useState(false); 

    const closeRestaurantList = () => {
        setIsHidden(true); 
        setTimeout(() => {
            setIsRestaurantListVisible(false); 
            setIsHidden(false); 
        }, 100); 
    };

    const handleSelectRestaurant = (restaurantName) => {
        setSelectedRestaurant(restaurantName); 
        closeRestaurantList(); 
    };


    const handleToggleSwitch = () => {
        setIsToggleSwitchOn((prevState) => !prevState);
    };
>>>>>>> d41345c36846d3d8678874e0d63aa30edfa25653

/*  const handleCreateClick = () => {
    onClose(); 
    setTimeout(() => {
      setValidationCreatePopUpVisible(true);
    }, 300);
  };*/

    function emptyInputs() {
        const inputs = [
            { id: 'groupName', value: groupName },
            { id: 'groupDeliveryLocation', value: deliveryLocation },
            { id: 'selectedRestaurant', value: selectedRestaurant },
        ];

        inputs.forEach(({ id, value }) => {
            const element = document.querySelector(`#${id}`);
            if (element) {
                if (value) {
                    element.classList.remove('empty');
                } else {
                    element.classList.add('empty');
                }
            } else {
                console.error(`Element with id "${id}" not found`);
            }
        });
    }

    const handleCreateClick = () => {
        emptyInputs();

        if (!groupName || !deliveryLocation || !selectedRestaurant) {
            return;
        }

        const groupData = {
            name: groupName,
            location: deliveryLocation,
            restaurant: selectedRestaurant,
            deliveryTime: isToggleSwitchOn ? `${deliveryTime.hours}:${deliveryTime.minutes}` : null,
        };
        console.log('Group Data to be sent:', groupData);

        const mockedId = 1234567; // Simulez un ID
        setGroupId(mockedId); // Appelle la fonction du parent pour définir l'ID
        console.log('Mocked Group ID set in parent:', mockedId);


        onClose();
        setTimeout(() => {
            setValidationCreatePopUpVisible(true);
        }, 300);




        /*        fetch('/api/groups', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(groupData),
                })
                    .then((response) => response.json())
                    .then((data) => {
                        if (data.id) {
                            setGroupId(data.id); // Stocke l'ID du groupe
                            setValidationCreatePopUpVisible(true); // Ouvre la validation
                        } else {
                            console.error('Erreur : ID du groupe non reçu');
                        }
                    })
                    .catch((error) => console.error('Erreur lors de la requête API :', error));*/
    };

  return (
    <>
    <div className={`create-group-container ${closing ? 'closing' : ''} ${isToggleSwitchOn ? 'expanded' : 'collapsed'}`}>
        <div className="popup-content">
            {!isRestaurantListVisible &&
                <i className="fa-solid fa-xmark" onClick={onClose}></i>
            }
            <h4 className='popup-title'>
                {isRestaurantListVisible ? 'Restaurants List' : 'Create Group Order'}
            </h4>
            <div className="separation-line"></div>
            <div className={`input-button-container ${isToggleSwitchOn ? 'expanded' : 'collapsed'}`}>
                <Input placeholder="Group Name" id='groupName' value={groupName} onChange={(e) => setGroupName(e.target.value)}
                />
                <Input placeholder="Group Delivery Location" id='groupDeliveryLocation' value={deliveryLocation} onChange={(e) => setDeliveryLocation(e.target.value)} />
                <Dropdown 
                    id="selectedRestaurant"
                    selectedRestaurant={selectedRestaurant}
                    onRestaurantChange={(value) => setSelectedRestaurant(value)}
                    onDetailsClick={() => setIsRestaurantListVisible(true)}
                />
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
        {isRestaurantListVisible && (
            <div className={`restaurants-list-popup ${isHidden ? 'hidden' : ''}`}>
                <RestaurantList closeRestaurantList={closeRestaurantList} onSelectRestaurant={handleSelectRestaurant}/>
            </div>
        )}
    </div>
    </>
  );
}

export default CreateGroupPopUp;
