import './create-group-pop-up.css';
import Button from '../button/button';
import Input from '../input/input';

function CreateGroupPopUp({onClose, closing}) {
  return (
    <div className={`create-group-container ${closing ? 'closing' : ''}`}>
        <div className="popup-content">
            <i class="fa-solid fa-xmark" onClick={onClose}></i>
            <h4 className='popup-title'>Create Group Order</h4>
            <div className="separation-line"></div>
            <div className="input-button-container">
                <Input placeholder="Group Name"></Input>
                <Input placeholder="Group Adress"></Input>
                <Input placeholder="Group Postal Code"></Input>
                <Input placeholder="City"></Input>
                <Input placeholder="Restaurant"></Input>
                <div className="delivery-time-container">
                    <h5 className='delivery-time-text'>Delivery Time</h5>
                    <div className="delivery-time-content">
                        <Input placeholder="Heure"></Input>
                        <Input placeholder="Minutes"></Input>
                    </div>
                </div>
                <Button text="Create Group Order"/>
            </div>
        </div>
    </div>
  );
}

export default CreateGroupPopUp;
