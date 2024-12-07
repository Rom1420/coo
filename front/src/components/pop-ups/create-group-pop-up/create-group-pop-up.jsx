import './create-group-pop-up.css';
import Button from '../../tools/button/button';
import Input from '../../tools/input/input';
import ToggleSwitch from '../../tools/toggle-switch/toggle-switch';
import { useState } from 'react';
import Dropdown from '../../tools/dropdown/dropdown';
import RestaurantList from '../../restaurants-list/restaurants-list';

function CreateGroupPopUp({onClose, closing, setValidationCreatePopUpVisible}) {

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

/*  const handleCreateClick = () => {
    onClose(); 
    setTimeout(() => {
      setValidationCreatePopUpVisible(true);
    }, 300);
  };*/

    const handleCreateClick = () => {
        if (!groupName || !deliveryLocation || !selectedRestaurant) {
            alert('Fill all the fields before creating the group !');
            return;
        }

        const groupData = {
            name: groupName,
            location: deliveryLocation,
            restaurant: selectedRestaurant,
            deliveryTime: isToggleSwitchOn ? `${deliveryTime.hours}:${deliveryTime.minutes}` : null,
        };
        console.log('Group Data to be sent:', groupData);

        onClose();
        setTimeout(() => {
            setValidationCreatePopUpVisible(true);
        }, 300);

        fetch('/api/groups', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(groupData),
        })
            .then((response) => {
                if (response.ok) {
                    onClose();
                    setTimeout(() => setValidationCreatePopUpVisible(true), 300);
                } else {
                    console.error('Erreur lors de la création du groupe');
                }
            })
            .catch((error) => console.error('Erreur lors de la requête API :', error));
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
                <Input placeholder="Group Name" value={groupName} onChange={(e) => setGroupName(e.target.value)}
                />
                <Input placeholder="Group Delivery Location" value={deliveryLocation} onChange={(e) => setDeliveryLocation(e.target.value)} />
                <Dropdown 
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
    </div>
        {isRestaurantListVisible && (
            <div className={`restaurants-list-popup ${isHidden ? 'hidden' : ''}`}>
                <RestaurantList closeRestaurantList={closeRestaurantList} onSelectRestaurant={handleSelectRestaurant}/>
            </div>
        )}
    </>
  );
}

export default CreateGroupPopUp;
