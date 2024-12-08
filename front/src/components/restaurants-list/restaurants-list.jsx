import React, { useEffect, useState } from 'react';
import Restaurant from '../tools/restaurant/restaurant';
import './restaurants-list.css';
import Filter from '../tools/filter/filter';

function RestaurantList({ closeRestaurantList, onSelectRestaurant }) {
    const [restaurants, setRestaurants] = useState([]);
    const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

    useEffect(() => {
        fetch(`${API_BASE_URL}/api/restaurant`)
        // fetch(`http://localhost:8080/api/restaurant`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then((data) => setRestaurants(data))
            .catch((error) => console.error('Erreur lors du chargement des données:', error));
    }, [API_BASE_URL]);

    const applyFilters = (filters) => {
        const params = new URLSearchParams();
        console.log(filters)

        if (filters.cuisineType) params.append('cuisineType', filters.cuisineType.toUpperCase());
        if (filters.day) params.append('day', filters.day.toUpperCase());
        if (filters.time) params.append('time', filters.time);

        fetch(`${API_BASE_URL}/api/restaurant/filters?${params.toString()}`)
        // fetch(`http://localhost:8080/api/restaurant/filters?${params.toString()}`)
            .then((response) => response.json())
            .then((data) => setRestaurants(data))
            .catch((error) => console.error('Erreur lors de l\'application des filtres:', error));
    };

    // Mise à jour de la couleur du thème
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#fff');

    return (
        <div className="restaurant-list-container">
            <i className="fa-solid fa-arrow-left restaurant-list" onClick={closeRestaurantList}></i>
            <div className="restaurant-list">
                <Filter text={"Filters"} type={"restaurant"} onApplyFilters={applyFilters}/>
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
