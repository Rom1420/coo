import React, {useEffect, useState} from 'react';
import './cart-resume.css';
import RestaurantResume from '../tools/restaurant-resume/restaurant-resume';
import ArticleList from '../tools/aticle-list/article-list';
import CheckoutResume from '../checkout-resume/checkout-resume';
import Button from '../tools/button/button';
import ProceedPaymentPopUp from "../pop-ups/proceed-payment-pop-up/proceed-payment-pop-up";
import ValidationPaymentPopUp from "../pop-ups/validation-payment-pop-up/validation-payment-pop-up";

function CartResume({ onBackToPrevClick, onBackToHomeClick,cart, removeArticleFromCart, restaurant }) {
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#fff');

    const [paymentStatus, setPaymentStatus] = useState(null);
    const [isProceedPaymentPopUpVisible, setProceedPaymentPopUpVisible] = useState(false);
    const [isValidationPaymentPopUpVisible, setValidationPaymentPopUpVisible] = useState(false);
    const [isClosing, setIsClosing] = useState(false);

    useEffect(() => {
        const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');

        if (isProceedPaymentPopUpVisible || isValidationPaymentPopUpVisible) {
            themeColorMetaTag.setAttribute('content', '#6c6c6c'); 
        } else {
            themeColorMetaTag.setAttribute('content', '#ffffff'); 
        }
    }, [isProceedPaymentPopUpVisible, isValidationPaymentPopUpVisible]);

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

    const handlePostRequest = async () => {
        const data = { articles: []};
        for(const element of cart) {
            for (let j = 0; j < element.quantity; j++) {
                const article = {
                    name: element.name,
                    price: element.price
                }
                data.articles.push(article);
            }
        }
        try {
            console.log("\n\n***********************************APPEL API ORDER***********************************\n\n")
            console.log(`Appel au service externe Order qui crée une commande après qu'elle soit validée\nA l'adresse : POST http://localhost:8002/api/order`);
            console.log("Données envoyées :", data);
            console.log("\n\n**************************************************************************************\n\n")
            
            const response = await fetch("http://localhost:8002/api/order", {
                method: 'POST', // Type de requête
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data), // Transformation des données en JSON
                mode: 'cors'
            });

            if (response.ok) {
                await response.json();
            } else {
                console.error("Erreur dans la requête :", response.status);
            }
        } catch (error) {
            console.error("Erreur réseau :", error);
        }
    };

    const handleValidationPaymentClick = async () => {
        setPaymentStatus(null);
        try {
            console.log("\n\n***********************************APPEL API PAYMENT***********************************\n\n")
            console.log(`Appel au service externe Payment qui valid ou non le payement\nA l'adresse : GET http://localhost:8050/api/payment`);
            console.log("\n\n****************************************************************************************\n\n")

            const response = await fetch('http://localhost:8050/api/payment');
            if (response.ok) {
                const result = await response.text();
                if (result === "valid payment") {
                    setPaymentStatus('success');
                    await handlePostRequest();
                } else {
                    setPaymentStatus('failure');
                }
            } else {
                setPaymentStatus('failure');
            }
        } catch (error) {
            setPaymentStatus('failure');
        }
        if (isValidationPaymentPopUpVisible) {
            setIsClosing(true);
            setTimeout(() => {
                setValidationPaymentPopUpVisible(false);
                setIsClosing(false);
            }, 300);
        } else {
            setValidationPaymentPopUpVisible(true);
        }
    };

    const logoUrl = `/logos/logo-${restaurant.name.toLowerCase().replace(/ /g, '-')}.png`;

    return (
    <div className="cart-resume-container">
        {(isProceedPaymentPopUpVisible || isValidationPaymentPopUpVisible) && <div className="darker-overlay cart"></div>}
        <i className="fa-solid fa-arrow-left carte-resume" onClick={onBackToPrevClick}></i>
        <div className="cart-resume-content">
            <h2 className='carte-resume-title'>My Order</h2>
            <div className="restaurant-resume-container">
                <img src={logoUrl} className='restaurant-resume-logo' alt=''/>
                <RestaurantResume restaurant={restaurant}/>
            </div>
            <div className="separation-line-cart-resume"></div>
            <ArticleList cart={cart} removeArticleFromCart={removeArticleFromCart}/>
            <div className="separation-line"></div>
            <CheckoutResume cart={cart}/>
            {!isProceedPaymentPopUpVisible && !isValidationPaymentPopUpVisible &&<div className="checkout-button-container">
                <Button text={"Choose Payment"} style={{width: "20%", marginBottom:4}} onClick={handleProceedPaymentClick}/>
                <Button text={"Checkout"} style={{width: "20%"}} onClick={handleValidationPaymentClick}/>
            </div>}
        </div>
        {isProceedPaymentPopUpVisible && <ProceedPaymentPopUp onClose={() => {setProceedPaymentPopUpVisible(false)}} closing={isClosing}/>}
        {isValidationPaymentPopUpVisible && <ValidationPaymentPopUp onClose={() => setValidationPaymentPopUpVisible(false)}
                                                                    closing={isClosing}
                                                                    backToHomeClick={() => onBackToHomeClick()}
                                                                    paymentStatus={paymentStatus}/>}
    </div>
    );
}

export default CartResume;
