import './validation-join-pop-up.css';
import Button from '../../tools/button/button';
import { ReactComponent as ValidationPic } from '../../../assets/validation-pic.svg';

function ValidationJoinPopUp({ onClose, closing, onOrderNowClick, groupId, groupName }) {
    return (
        <div className={`validation-join-container ${closing ? 'closing' : ''}`}>
            <div className="validation-popup-content">
                <ValidationPic style={{ height: '20vh', width: 'auto' }} />
                <h2 className="title-validation">You have successfully</h2>
                <h3 className="small-text-validation">joined the group</h3>
                <h1 className="group-name">{groupName}</h1>
                <h4 className="group-id">Group ID: {groupId}</h4>
                <Button
                    style={{ width: '50%', height: '4vh' }}
                    text="Order Now"
                    onClick={() => {
                        onClose();
                        onOrderNowClick();
                    }}
                />
            </div>
        </div>
    );
}

export default ValidationJoinPopUp;
