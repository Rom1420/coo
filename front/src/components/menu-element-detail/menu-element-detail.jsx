import './menu-element-detail.css';
import Rating from '../tools/rating/rating';
import Favorite from '../tools/favorite/favorite';
import Cart from '../tools/cart/cart';
import Counter from '../tools/counter/counter';
import React, { useState } from 'react';

function MenuElementDetail({onBackToPrevClick, menuElement, onCheckCartClick, onAddArticle, restaurant}) {
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#6c6c6c');

    const [portions, setPortions] = useState(0);

    const logoUrl = `/logos/logo-${restaurant.name.toLowerCase().replace(/ /g, '-')}.png`;
    const imageUrl = `/images/${restaurant.name.toLowerCase().replace(/ /g, '-')}.png`;

    const getCategoryImageUrl = () =>
    `/menu/${restaurant.name.toLowerCase().replace(/ /g, '-')}/categories/${menuElement?.categorie.toLowerCase()}-${restaurant.name.toLowerCase().replace(/ /g, '-')}.png`;

    return (
        <div className="menu-element-container">
            <i className="fa-solid fa-arrow-left" onClick={onBackToPrevClick}></i>
            <img className="main-pic" src={getCategoryImageUrl()} alt='' />
            <div className="menu-element-content-container">
                <img src={logoUrl} className='restaurant-logo' alt=''/>
                <Favorite/>
                <div className="menu-element-content-scroll">
                    <div className="menu-element-content">
                        <h2 className='menu-element-name-detail'>{menuElement?.name}</h2>
                        <div className="right-container">
                            <h2 className='menu-element-price-detail'>{menuElement?.price} €</h2>
                        </div>
                        <div className="description-container">
                            <h5 className='description-title'>Description</h5>
                            <p className='description-text'>{menuElement?.description}</p>
                        </div>
                        <div className="separation-line"></div>
                        <div className="portions-container">
                            <h5 className="portions-title">Number of portions</h5>
                            <Counter onCountChange={setPortions}/>
                        </div>
                    </div>
                    <Cart onCheckCartClick={onCheckCartClick} 
                            onArticlePage={true} 
                            onAddArticle={() =>
                                onAddArticle({
                                    name: menuElement?.name,
                                    price: menuElement?.price || 10, 
                                    image: menuElement?.image,
                                    quantity: portions,
                                })
                        }/>
                </div>
            </div>
        </div>
    );
}

export default MenuElementDetail;
