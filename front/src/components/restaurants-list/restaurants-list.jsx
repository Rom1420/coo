import React, { useEffect, useState } from 'react';
import Restaurant from '../tools/restaurant/restaurant';
import './restaurants-list.css';
import Filter from '../tools/filter/filter';

function RestaurantList({ closeRestaurantList, onSelectRestaurant }) {
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
        <div className="restaurant-list-container">
            <i className="fa-solid fa-arrow-left restaurant-list" onClick={closeRestaurantList}></i>
            <div className="restaurant-list">
                <Filter text={"Filters"} type={"restaurant"} />
                {restaurants.map((restaurant, index) => (
                    <div
                        key={index}
                        onClick={() => onSelectRestaurant(restaurant.name)} // Met à jour le restaurant sélectionné
                    >
                        <Restaurant restaurant={restaurant} />
                    </div>
                ))}
            </div>
        </div>
    );
}

export default RestaurantList;
