import React from 'react';
import Rating from '../rating/rating';
import './restaurant.css';

function Restaurant({ restaurant }) {
    const {name,open} = restaurant;

    const imageUrl = `/images/${name.toLowerCase().replace(/ /g, '-')}.png`;

    const RandomStarsNumber = Math.floor((Math.random() * ((5 - 3.5) / 0.5 + 1))) * 0.5 + 3.5;

    return (
        <div className="restaurant-component-container">
            <img src={imageUrl} alt="" className='restaurant-component-image' />
            <div className="restaurant-component-content">
                <h2 className='restaurant-component-name'>{name}</h2>
                <Rating rating={RandomStarsNumber} size="1rem" />
                <p className={`restaurant-component ${open ? 'open' : 'close'}`}>
                    {open ? 'Open' : 'Close'}
                </p>
            </div>
        </div>
    );
}

export default Restaurant;
