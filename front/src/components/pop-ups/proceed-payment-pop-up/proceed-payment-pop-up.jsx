import './proceed-payment-pop-up.css';
import Button from '../../tools/button/button';

function ProceedPaymentPopUp({onClose, closing}) {
    const logoPaypalUrl = `/logos/logo-paypal.png`
    const logoGooglePayUrl=`/logos/logo-googlepay.png`
    const logoApplePayUrl=`/logos/logo-applepay.png`

    return (
        <div className={`proceed-payment-container ${closing ? 'closing' : ''}`}>
            <div className="popup-content">
                <i className="fa-solid fa-xmark" onClick={onClose}></i>
                <h4 className='popup-title'>Choose Payment</h4>
                <div className="separation-line"></div>
                <div className="proceed-payment-content">
                    <div className={"buttons-container"}>
                        <Button style={{
                            width:'50%', height: "4vh", backgroundColor: 'white',
                            border: '2px solid var(--blue)',
                            color: 'black',
                            filter: 'drop-shadow(0px 2px 2px rgba(0, 0, 0, 0.5))',
                            borderRadius: '4px',}} imageSrc={logoPaypalUrl} text="PayPal" onClick={() => {onClose();}}/>
                        <Button style={{
                            width:'50%', height: "4vh", backgroundColor: 'white',
                            border: '2px solid var(--blue)',
                            color: 'black',
                            filter: 'drop-shadow(0px 2px 2px rgba(0, 0, 0, 0.5))',
                            borderRadius: '4px',}} imageSrc={logoGooglePayUrl} text="GooglePay" onClick={() => {onClose();}}/>
                        <Button style={{
                            width:'50%', height: "4vh", backgroundColor: 'white',
                            border: '2px solid var(--blue)',
                            color: 'black',
                            filter: 'drop-shadow(0px 2px 2px rgba(0, 0, 0, 0.5))',
                            borderRadius: '4px',}} imageSrc={logoApplePayUrl} text="ApplePay" onClick={() => {onClose();}}/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ProceedPaymentPopUp;
