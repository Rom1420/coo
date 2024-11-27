import React from 'react';
import {ReactComponent as ChezPippoLogo} from '../../assets/logo-chez-pippo.svg'
import './cart-resume.css';
import RestaurantResume from '../tools/restaurant-resume/restaurant-resume';

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
            <div className="separation-line"></div>
            .
        </div> 
    </div>
    );
}

export default CartResume;
