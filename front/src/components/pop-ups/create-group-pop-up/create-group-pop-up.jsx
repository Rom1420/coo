import './create-group-pop-up.css';
import Button from '../../tools/button/button';
import Input from '../../tools/input/input';
import ToggleSwitch from '../../tools/toggle-switch/toggle-switch';
import { useState } from 'react';

function CreateGroupPopUp({onClose, closing, setValidationCreatePopUpVisible}) {

  const [isToggleSwitchOn, setIsToggleSwitchOn] = useState(false);

  const handleToggleSwitch = () => {
    setIsToggleSwitchOn((prevState) => !prevState);
  };

  return (
    <div className={`create-group-container ${closing ? 'closing' : ''} ${isToggleSwitchOn ? 'expanded' : 'collapsed'}`}>
        <div className="popup-content">
            <i className="fa-solid fa-xmark" onClick={onClose}></i>
            <h4 className='popup-title'>Create Group Order</h4>
            <div className="separation-line"></div>
            <div className={`input-button-container ${isToggleSwitchOn ? 'expanded' : 'collapsed'}`}>
                <Input placeholder="Group Name"></Input>
                <Input placeholder="Group Adress"></Input>
                <Input placeholder="Group Postal Code"></Input>
                <Input placeholder="City"></Input>
                <Input placeholder="Restaurant"></Input>
                <div className="delivery-time-container">
                    <div className="delivery-time-header">
                        <h5 className='delivery-time-text'>Choose Delivery Time</h5>
                        <ToggleSwitch onChange={handleToggleSwitch}/>
                    </div>
                    { isToggleSwitchOn && 
                        <div className="delivery-time-content">
                            <Input placeholder="Heure"></Input>
                            <Input placeholder="Minutes"></Input>
                        </div>
                    }
                </div>
                <Button text="Create Group Order" onClick={()=>{onClose(); setValidationCreatePopUpVisible(true);}}/>
            </div>
        </div>
    </div>
  );
}

export default CreateGroupPopUp;
