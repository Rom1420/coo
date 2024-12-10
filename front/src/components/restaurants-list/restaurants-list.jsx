import React, { useEffect, useState } from 'react';
import Restaurant from '../tools/restaurant/restaurant';
import './restaurants-list.css';
import Filter from '../tools/filter/filter';

function RestaurantList({ closeRestaurantList, onSelectRestaurant, onHomePage }) {
    const [restaurants, setRestaurants] = useState([]);
    const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

    useEffect(() => {
        // fetch(`${API_BASE_URL}/api/restaurant`)
        console.log("\n\n***********************************APPEL API RESTAURANT***********************************\n\n")
        console.log(`Appel au service externe Restaurant pour récupérer la liste des restaurants \nA l'adresse : GET http://localhost:8080/api/restaurant`);
        console.log("\n\n******************************************************************************************\n\n")
        
        fetch(`http://localhost:8080/api/restaurant`)
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

        if (filters.cuisineType) params.append('cuisineType', filters.cuisineType.toUpperCase());
        if (filters.day) params.append('day', filters.day.toUpperCase());
        if (filters.time) params.append('time', filters.time);

        // fetch(`${API_BASE_URL}/api/restaurant/filters?${params.toString()}`)
        
        console.log("\n\n***********************************APPEL API RESTAURANT***********************************\n\n")
        console.log(`Appel au service externe Restaurant pour récupérer la liste des restaurants en fonction du ou des filtres :`)
        console.log(`Filtres appliqués :`);
        console.log(`- Type de cuisine : ${filters.cuisineType || 'Non spécifié'}`);
        console.log(`- Jour : ${filters.day || 'Non spécifié'}`);
        console.log(`- Heure : ${filters.time || 'Non spécifié'}`);
        console.log(`A l'adresse : GET http://localhost:8080/api/restaurant/filters?${params.toString()}`);
        console.log("\n\n******************************************************************************************\n\n")
        
        fetch(`http://localhost:8080/api/restaurant/filters?${params.toString()}`)
            .then((response) => response.json())
            .then((data) => setRestaurants(data))
            .catch((error) => console.error('Erreur lors de l\'application des filtres:', error));
    };

    const handleRestaurantClick = (restaurant) => {
        onSelectRestaurant(restaurant); 
    };


    // Mise à jour de la couleur du thème
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#fff');

    return (
    <>
        {onHomePage && (
            <div className="restaurant-list-home-page">
                <h3 className='restaurant-list-home-page-title'>Restaurants List</h3>
                <div className="separation-line"></div>
                <div className="restaurant-list-container">
                    <i className="fa-solid fa-arrow-left restaurant-list-home-page" onClick={closeRestaurantList}></i>
                    <div className="restaurant-list">
                        <Filter text={"Filters"} type={"restaurant"} onApplyFilters={applyFilters} />
                        {restaurants.map((restaurant, index) => (
                            <div
                                key={index}
                                onClick={() => {
                                    handleRestaurantClick(restaurant);
                                }}
                            >
                                <Restaurant restaurant={restaurant} />
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        )}
        {!onHomePage && (
            <div className="restaurant-list-container">
                <i className="fa-solid fa-arrow-left restaurant-list" onClick={closeRestaurantList}></i>
                <div className="restaurant-list">
                    <Filter text={"Filters"} type={"restaurant"} onApplyFilters={applyFilters} />
                    {restaurants.map((restaurant, index) => (
                        <div
                            key={index}
                            onClick={() => {
                                handleRestaurantClick(restaurant);
                            }}
                        >
                            <Restaurant restaurant={restaurant} />
                        </div>
                    ))}
                </div>
            </div>
        )}
    </>
    );
}

export default RestaurantList;
