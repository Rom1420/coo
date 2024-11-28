import React from 'react';
import './checkout-resume.css';

function CheckoutResume() {
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#fff');
    return (
    <div className="checkout-resume-container">
        <div className="checkout-resume-content">
            <h4 className='checkout-resume-title'>Deliver For</h4>
            <h4 className='checkout-resume-element'>18:00</h4>
        </div>
        <div className="separation-line"></div>
        <div className="checkout-resume-content">
            <h4 className='checkout-resume-title'>Sub Total</h4>
            <h4 className='checkout-resume-element'>91€</h4>
        </div>
        <div className="checkout-resume-content">
            <h4 className='checkout-resume-title'>Delivery Cost</h4>
            <h4 className='checkout-resume-element'>2€</h4>
        </div>
        <div className="separation-line"></div>
        <div className="checkout-resume-content">
            <h4 className='checkout-resume-title-total'>Total</h4>
            <h4 className='checkout-resume-total'>93€</h4>
        </div>
    </div>
    );
}

export default CheckoutResume;
