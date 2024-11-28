import './article-list.css';
import React, { useEffect, useState } from 'react';
import Article from '../article/article';

function ArticleList() {
    const [cart, setCart] = useState([]);

    useEffect(() => {
        fetch('/cart.json')
            .then((response) => response.json())
            .then((data) => setCart(data))
            .catch((error) => console.error('Erreur lors du chargement des données:', error));
    }, []);

    return (
        <div className="article-list-container">
            {cart.map((item, index) => (
                <Article key={index} name={item.name} price={item.price} quantity={item.quantity} />
            ))}
        </div>
    );
}

export default ArticleList;