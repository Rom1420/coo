import './menu.css';
import Favorite from '../tools/favorite/favorite';
import MenuItem from '../tools/menu-item/menu-item';
import MenuItemElement from '../tools/menu-item-element/menu-item-element';
import Cart from '../tools/cart/cart';
import { useState } from 'react';

function Menu({onBackToRestaurantClick, onMenuElementClick, onCheckCartClick, restaurant}) {
    const [selectedCategory, setSelectedCategory] = useState(null);
    const logoUrl = `/logos/logo-${restaurant.name.toLowerCase().replace(/ /g, '-')}.png`;
    const imageUrl = `/images/${restaurant.name.toLowerCase().replace(/ /g, '-')}.png`;

    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#6c6c6c');

    const getCategories = (articles) => {
        const categories = {};
        articles.forEach((article) => {
            if (!categories[article.categorie]) {
            categories[article.categorie] = [];
            }
            categories[article.categorie].push(article);
        });
        return categories;
    };

    const categories = getCategories(restaurant.articlesSimples);

    const getCategoryImageUrl = (category) =>
    `/menu/${restaurant.name.toLowerCase().replace(/ /g, '-')}/categories/${category.toLowerCase()}-${restaurant.name.toLowerCase().replace(/ /g, '-')}.png`;

    return (
        <div className="menu-container">
            {selectedCategory ? (
                <i className="fa-solid fa-arrow-left" onClick={() => setSelectedCategory(null)}></i>
            ) : (
                <i className="fa-solid fa-arrow-left" onClick={onBackToRestaurantClick}></i>
            )}
            <img className="main-pic" src={imageUrl} alt="" />
            <div className="menu-content-container">
                <img src={logoUrl} className='restaurant-logo' alt=''/>
                <Favorite/>
                <div className="menu-content-scroll">
                    <div className="menu-content">
                        <h3 className='menu-title'>{selectedCategory || 'Menu'}</h3>
                        <div className="blue-box"></div>
                            {selectedCategory ? (
                                categories[selectedCategory].map((element, index) => (
                                    <MenuItemElement
                                    key={index}
                                    image={getCategoryImageUrl(selectedCategory)}
                                    title={element.name}
                                    smallText={element.description}
                                    onClick={() => onMenuElementClick(element)}
                                    />
                                ))
                                ) : (
                                Object.keys(categories).map((category, index) => (
                                    <MenuItem
                                    key={index}
                                    title={category}
                                    image={getCategoryImageUrl(category)}
                                    onClick={() => setSelectedCategory(category)}
                                    />
                                ))
                            )}
                        </div>
                    <Cart onCheckCartClick={onCheckCartClick} onArticlePage={false}/>
                </div>
            </div>
        </div>
    );
}

export default Menu;
