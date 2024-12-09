import React, {useEffect, useState} from 'react';
import {ReactComponent as ChezPippoLogo} from '../../assets/logo-chez-pippo.svg'
import './cart-resume.css';
import RestaurantResume from '../tools/restaurant-resume/restaurant-resume';
import ArticleList from '../tools/aticle-list/article-list';
import CheckoutResume from '../checkout-resume/checkout-resume';
import Button from '../tools/button/button';
import ProceedPaymentPopUp from "../pop-ups/proceed-payment-pop-up/proceed-payment-pop-up";

function CartResume({ onBackToPrevClick }) {
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#fff');

    const [isProceedPaymentPopUpVisible, setProceedPaymentPopUpVisible] = useState(false);
    const [isClosing, setIsClosing] = useState(false);

    useEffect(() => {
        const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');

        if (isProceedPaymentPopUpVisible) {
            themeColorMetaTag.setAttribute('content', '#6c6c6c'); // Couleur sombre quand ya le pop up
        } else {
            themeColorMetaTag.setAttribute('content', '#ffffff'); // Couleur par défaut
        }
    }, [isProceedPaymentPopUpVisible]);

    const handleProceedPaymentClick = () => {
        if (isProceedPaymentPopUpVisible) {
            setIsClosing(true);
            setTimeout(() => {
                setProceedPaymentPopUpVisible(false);
                setIsClosing(false);
            }, 300);
        } else {
            setProceedPaymentPopUpVisible(true);
        }
    };

    return (
    <div className="cart-resume-container">
        {(isProceedPaymentPopUpVisible) && <div className="darker-overlay"></div>}
        <i className="fa-solid fa-arrow-left carte-resume" onClick={onBackToPrevClick}></i>
        <div className="cart-resume-content">
            <h2 className='carte-resume-title'>My Order</h2>
            <div className="restaurant-resume-container">
                <ChezPippoLogo className='restaurant-resume-logo'/>
                <RestaurantResume/>
            </div>
            <div className="separation-line-cart-resume"></div>
            <ArticleList/>
            <div className="separation-line"></div>
            <CheckoutResume/>
            {!isProceedPaymentPopUpVisible &&<div className="checkout-button-container">
                <Button text={"Choose Payment"} style={{width: "20%", marginBottom:4}} onClick={handleProceedPaymentClick}/>
                <Button text={"Checkout"} style={{width: "20%"}}/>
            </div>}
        </div>
        {isProceedPaymentPopUpVisible && <ProceedPaymentPopUp onClose={() => {setProceedPaymentPopUpVisible(false)}} closing={isClosing}/>}
    </div>
    );
}

export default CartResume;
