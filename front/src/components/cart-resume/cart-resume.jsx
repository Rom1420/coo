import React from 'react';
import {ReactComponent as ChezPippoLogo} from '../../assets/logo-chez-pippo.svg'
import './cart-resume.css';
import RestaurantResume from '../tools/restaurant-resume/restaurant-resume';
import ArticleList from '../tools/aticle-list/article-list';
import CheckoutResume from '../checkout-resume/checkout-resume';
import Button from '../tools/button/button';

function CartResume({ onBackToPrevClick }) {
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#fff');
    return (
    <div className="cart-resume-container">
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
            <div className="checkout-button-container">
                <Button text={"Checkout"} style={{width: "20%"}}/>
            </div>
        </div> 
    </div>
    );
}

export default CartResume;
