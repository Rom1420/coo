import './validation-create-pop-up.css';
import Button from '../../tools/button/button';
import { ReactComponent as ValidationPic } from '../../../assets/validation-pic.svg';



function ValidationCreatePopUp({onClose, closing, groupId, groupNameFB}) {
    console.log('Received Group ID in ValidationCreatePopUp:', groupId );
    console.log('Received Group Name in ValidationCreatePopUp:', groupNameFB );

    const handleShareCode = () => {
        alert(`The ID "${groupId}" has been shared with your friends.`);
    };

    return (
        <div className={`validation-create-container ${closing ? 'closing' : ''}`}>
            <div className="validation-popup-content">
                <i className="fa-solid fa-xmark" onClick={onClose}></i>
                <ValidationPic style={{ height: '20vh', width: 'auto' }} />
                <h2 className='title-validation'>Your Group Order: {groupNameFB}</h2>
                <h3 className='small-text-validation'>has been successfully created</h3>
                <h4 className='consigne'>copy the code to share it with your friends and invite them to join your group.</h4>
                <div className="group-id-container">
                    <strong>Group ID: {groupId}</strong> {/* Affiche l'ID */}
                </div>
                <div className="buttons-container">
                    <Button text="Share code" onClick={handleShareCode} />
                    <Button text="Order now" onClick={onClose}/>
                </div>
            </div>
        </div>
    );
}

export default ValidationCreatePopUp;