import './join-group-pop-up.css';
import Button from '../../tools/button/button';
import Input from '../../tools/input/input';

function JoinGroupPopUp({onClose, closing, setValidationPopUpVisible}) {
  const handleJoinClick = () => {
    onClose(); 
    setTimeout(() => {
      setValidationPopUpVisible(true);
    }, 300);
  };

  return (
    <div className={`join-group-container ${closing ? 'closing' : ''}`}>
        <div className="popup-content">
            <i className="fa-solid fa-xmark" onClick={onClose}></i>
            <h4 className='popup-title'>Join Group Order</h4>
            <div className="separation-line"></div>
            <div className="input-button-container">
              <Input placeholder="Group Code"></Input>
              <Button text="Join Group Order" onClick={handleJoinClick}/>
            </div>
        </div>
    </div>
  );
}

export default JoinGroupPopUp;
