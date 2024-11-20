import './validation-create-pop-up.css';
import Button from '../../tools/button/button';

function ValidationCreatePopUp({onClose, closing}) {
    return (
        <div className={`validation-create-container ${closing ? 'closing' : ''}`}>
            <div className="validation-popup-content">
                <img src="" alt="" />
                <h2 className='title-validation'>Your Group Order</h2>
                <h3 className='small-text-validation'>has been successfully created</h3>
                <h4 className='consigne'>copy the code to share it with your friends and invite them to join your group.</h4>
                <div className="buttons-container">
                    <Button text="Share code"/>
                    <Button text="Order now" onClick={onClose}/>
                </div>
            </div>
        </div>
    );
}

export default ValidationCreatePopUp;