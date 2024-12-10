import './create-group-pop-up.css';
import Button from '../../tools/button/button';
import Input from '../../tools/input/input';
import ToggleSwitch from '../../tools/toggle-switch/toggle-switch';
import { useState, useEffect } from 'react';
import Dropdown from '../../tools/dropdown/dropdown';
import RestaurantList from '../../restaurants-list/restaurants-list';

function CreateGroupPopUp({onClose, closing, setValidationCreatePopUpVisible, setGroupId, setGroupNameFB, setRestaurant}) {

    const [isToggleSwitchOn, setIsToggleSwitchOn] = useState(false);
    const [selectedRestaurant, setSelectedRestaurant] = useState('');
    const [groupName, setGroupName] = useState('');
    const [deliveryLocation, setDeliveryLocation] = useState('');
    const [deliveryTime, setDeliveryTime] = useState({ hours: '', minutes: '' });
    const [isRestaurantListVisible, setIsRestaurantListVisible] = useState(false);
    const [isHidden, setIsHidden] = useState(false); 
    const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

    useEffect(() => {
        if (selectedRestaurant) {
            console.log("\n\n***********************************APPEL API RESTAURANT***********************************\n\n")
            console.log(`Appel au service externe Restaurant pour récupérer les détails du restaurant séléctionné : ${selectedRestaurant} \nA l'adresse : GET http://localhost:8080/api/restaurant/${selectedRestaurant}`);
            console.log("\n\n******************************************************************************************\n\n")
            // fetch(`${API_BASE_URL}/api/restaurant/${selectedRestaurant}`)
            fetch(`http://localhost:8080/api/restaurant/${selectedRestaurant}`)
                .then((response) => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then((data) => setRestaurant(data))
                .catch((error) => console.error('Erreur lors du chargement des données:', error));
            }
    }, [API_BASE_URL, selectedRestaurant, setRestaurant]);

    const closeRestaurantList = () => {
        setIsHidden(true); 
        setTimeout(() => {
            setIsRestaurantListVisible(false); 
            setIsHidden(false); 
        }, 100); 
    };

    const handleSelectRestaurant = (restaurantName) => {
        setSelectedRestaurant(restaurantName.name); 
        setRestaurant(restaurantName);
        closeRestaurantList(); 
    };

    const handleToggleSwitch = () => {
        setIsToggleSwitchOn((prevState) => !prevState);
    };



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


        onClose();
        setTimeout(() => {
            setValidationCreatePopUpVisible(true);
        }, 300);

        console.log("\n\n***********************************APPEL API GROUP***********************************\n\n")
        console.log(`Appel au service externe Group pour créer un groupe \nA l'adresse: POST http://localhost:8001/api/group/create`);
        console.log("Données envoyées :", groupData);
        console.log("\n\n*************************************************************************************\n\n")
        
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
                if (data.id && data.groupName) {
                    setGroupId(data.id);
                    setGroupNameFB(data.groupName);
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
                <RestaurantList closeRestaurantList={closeRestaurantList} onSelectRestaurant={handleSelectRestaurant} onHomePage={false}/>
            </div>
        )}
    </div>
    </>
  );
}

export default CreateGroupPopUp;
