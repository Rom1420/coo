import React from 'react';
import './restaurant.css';

function Restaurant({ restaurant }) {
    const {
        name,
        articlesSimples,
        weeklySchedules,
        nbOfCook,
        typeCuisine,
        discountType,
        open,
    } = restaurant;

    const imageUrl = `/images/${name.toLowerCase().replace(/ /g, '-')}.png`;

    return (
        <div className="restaurant-component-container">
            <img src={imageUrl} alt="" className='restaurant-component-image' />
            <h2 className='restaurant-component-name'>{name}</h2>
            <p className='restaurant-component'>Type de cuisine : {typeCuisine}</p>
            <p className='restaurant-component'>Ouvert : {open ? 'Oui' : 'Non'}</p>
            <p className='restaurant-component'>Type de remise : {discountType}</p>
            <p className='restaurant-component-none'>Number of Cook : {nbOfCook}</p>
            <h3 className='restaurant-component'>Plats disponibles :</h3>
            <ul className='restaurant-component'>
                {articlesSimples.map((article, index) => (
                    <li key={index} className='restaurant-component'>
                        {article.name} - {article.price}€ ({article.categorie})
                    </li>
                ))}
            </ul>
            <h3 className='restaurant-component'>Horaires d'ouverture :</h3>
            <ul className='restaurant-component'>
                {Object.entries(weeklySchedules).map(([day, schedule], index) => (
                    <li key={index}className='restaurant-component'>
                        {day} : {schedule.opening} - {schedule.closing}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Restaurant;
