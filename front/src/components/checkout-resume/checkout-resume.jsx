import React from 'react';
import './checkout-resume.css';

function CheckoutResume({cart}) {
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#fff');

    const deliveryCost = 2;

    const subTotal = cart.reduce((sum, item) => {
        const price = parseFloat(item.price); 
        return sum + (price * item.quantity);
    }, 0);

    const total = subTotal + deliveryCost;

    return (
    <div className="checkout-resume-container">
        <div className="checkout-resume-content">
            <h4 className='checkout-resume-title'>Deliver For</h4>
            <h4 className='checkout-resume-element'>18:00</h4>
        </div>
        <div className="separation-line"></div>
        <div className="checkout-resume-content">
            <h4 className='checkout-resume-title'>Sub Total</h4>
            <h4 className='checkout-resume-element'>{subTotal}  €</h4>
        </div>
        <div className="checkout-resume-content">
            <h4 className='checkout-resume-title'>Delivery Cost</h4>
            <h4 className='checkout-resume-element'>{deliveryCost} €</h4>
        </div>
        <div className="separation-line"></div>
        <div className="checkout-resume-content">
            <h4 className='checkout-resume-title-total'>Total</h4>
            <h4 className='checkout-resume-total'>{total}  €</h4>
        </div>
    </div>
    );
}

export default CheckoutResume;
