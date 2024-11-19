import './validation-join-pop-up.css';
import Button from '../../tools/button/button';

function ValidationJoinPopUp({onClose, closing}) {
  return (
    <div className={`validation-join-container ${closing ? 'closing' : ''}`}>
        <div className="validation-popup-content">
            <img src="" alt="" />
            <h2 className='title-validation'>You have succesfully</h2>
            <h3 className='small-text-validation'>joined the group</h3>
            <h1 className='group-name'>PolyMiamMiam</h1>
            <Button text="Join Group Order" onClick={onClose}/>
        </div>
    </div>
  );
}

export default ValidationJoinPopUp;
