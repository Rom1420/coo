import './join-group-pop-up.css';
import Button from '../button/button';
import Input from '../input/input';

function JoinGroupPopUp({onClose, closing}) {
  return (
    <div className={`join-group-container ${closing ? 'closing' : ''}`}>
        <div className="popup-content">
            <i class="fa-solid fa-xmark" onClick={onClose}></i>
            <h4 className='popup-title'>Join Group Order</h4>
            <div className="separation-line"></div>
            <div className="input-button-container">
              <Input placeholder="Group Code"></Input>
              <Button text="Join Group Order"/>
            </div>
        </div>
    </div>
  );
}

export default JoinGroupPopUp;
