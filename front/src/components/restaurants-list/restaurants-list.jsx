import React, { useEffect, useState } from 'react';
import Restaurant from '../tools/restaurant/restaurant';
import './restaurants-list.css';

function RestaurantList({ closeRestaurantList }) {
    const [restaurants, setRestaurants] = useState([]);

    useEffect(() => {
        // Charge les données des restaurants depuis le fichier JSON
        fetch('/restaurants.json')
            .then((response) => response.json())
            .then((data) => setRestaurants(data))
            .catch((error) => console.error('Erreur lors du chargement des données:', error));
    }, []);

    // Mise à jour de la couleur du thème
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#fff');

    return (
        <div className="restaurant-container">
            <i className="fa-solid fa-arrow-left restaurant-list" onClick={closeRestaurantList}></i>
            <h1 className='restaurant-title'>Liste des Restaurants</h1>
            <div className="restaurant-list">
                {restaurants.map((restaurant, index) => (
                    <Restaurant key={index} restaurant={restaurant} />
                ))}
            </div>
        </div>
    );
}

export default RestaurantList;
