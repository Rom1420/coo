import './validation-join-pop-up.css';
import Button from '../../tools/button/button';
import { ReactComponent as ValidationPic } from '../../../assets/validation-pic.svg';

function ValidationJoinPopUp({onClose, closing, onOrderNowClick}) {
  return (
    <div className={`validation-join-container ${closing ? 'closing' : ''}`}>
        <div className="validation-popup-content">
            <ValidationPic style={{ height: '20vh', width: 'auto' }} />
            <h2 className='title-validation'>You have succesfully</h2>
            <h3 className='small-text-validation'>joined the group</h3>
            <h1 className='group-name'>PolyMiamMiam</h1>
            <Button style={{ width:'50%', height: "4vh"}} text="Order Now" onClick={() => {onClose();  onOrderNowClick()}}/>
        </div>
    </div>
  );
}

export default ValidationJoinPopUp;
