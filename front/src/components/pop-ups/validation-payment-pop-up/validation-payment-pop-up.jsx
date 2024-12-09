import './validation-payment-pop-up.css';
import Button from '../../tools/button/button';

function ValidationPaymentPopUp({onClose, closing, backToHomeClick, paymentStatus }) {
    return (
        <div className={`validation-payment-container ${closing ? 'closing' : ''}`}>
            <div className="validation-popup-content">
                <i className="fa-solid fa-xmark" onClick={onClose}></i>
                {paymentStatus === 'success' ? (
                    <>
                        <img src={'/images/validation-payment.png'} alt={''} style={{ height: '30vh', width: 'auto', margin: '20px' }} />
                        <h1 className='title-validation'>Thank You!</h1>
                        <h2 className='small-text-validation'>for your order</h2>
                        <h3 className='small-text-validation-2'>Your order is now being processed. Check the status of your order.</h3>
                        <Button style={{ width:'50%', height: "4vh"}} text="Track my order" onClick={onClose}/>
                        <Button style={{ width:'50%', height: "4vh", backgroundColor: 'white', color: 'black'}} text="Back to home" onClick={backToHomeClick}/>
                    </>
                ) : (
                    <>
                        <img src={'/images/error-icon.png'} alt={''} style={{ height: '20vh', width: 'auto', margin: '20px' }} />
                        <h1 className='title-failure'>Payment Failed</h1>
                        <h2 className='small-text-failure'>We encountered an issue processing your payment.</h2>
                        <h3 className='small-text'>Please try again or contact support if the issue persists.</h3>
                        <Button style={{ width:'50%', height: "4vh"}} text="Retry Payment" onClick={onClose}/>
                        <Button style={{ width:'50%', height: "4vh", backgroundColor: 'white', color: 'black'}} text="Back to home" onClick={backToHomeClick}/>
                    </>
                )}
            </div>
        </div>
    );
}

export default ValidationPaymentPopUp;