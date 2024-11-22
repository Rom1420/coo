import './menu.css';
import mainPic from '../../assets/chez-pippo-main-pic.png'
import {ReactComponent as ChezPippoLogo} from '../../assets/logo-chez-pippo.svg'
import Favorite from '../tools/favorite/favorite';
import MenuItem from '../tools/menu-item/menu-item';
import MenuItemElement from '../tools/menu-item-element/menu-item-element';
import Button from '../tools/button/button';
import { useState } from 'react';

// Import des images pour les menu items
import socca from '../../assets/menu/socca.png'
import soccaGarnie from '../../assets/menu/socca-garnie.png'
import painBagnat from '../../assets/menu/pain-bagnat.png'
import tartinades from '../../assets/menu/tartinades.png'
import glace from '../../assets/menu/glace.png'
import pissaladiere from '../../assets/menu/pissaladiere.png'
import biere from '../../assets/menu/biere.png'
import tourteDeBlette from '../../assets/menu/tourte-de-blette.png'

function Menu({onBackToHomeClick }) {

    const [selectedItem, setSelectedItem] = useState(null);

    const menuItems = [
        { 
        image: socca, 
        title: 'Socca', 
        smallText: 'Orignale ou garnies',
        elements: [
            { image: socca, title: 'Socca Nature', smallText: 'Classique' },
            { image: soccaGarnie, title: 'Socca Garnie', smallText: 'Avec fromage ou légumes' },
        ],
        },
        { 
        image: pissaladiere, 
        title: 'Entrées', 
        smallText: 'Pissaladière, tartinade, pan bagnat...',
        elements: [
            { image: pissaladiere, title: 'Pissaladière', smallText: 'Spécialité locale' },
            { image: tartinades, title: 'Tartinade', smallText: 'Assortiment de tartines' },
            { image: painBagnat, title: 'Pan Bagnat', smallText: 'Trop bon' },
        ],
        },
        { 
        image: biere, 
        title: 'Boissons', 
        smallText: 'Bières, vins, sodas...',
        elements: [
            { image: biere, title: 'Bière Blonde', smallText: 'Légère et rafraîchissante' },
            { image: biere, title: 'Bière Ambrée', smallText: 'Notes caramélisées' },
        ],
        },
        { 
        image: tourteDeBlette, 
        title: 'Desserts', 
        smallText: 'Tourte de blettes, glaces',
        elements: [
            { image: glace, title: 'Glace Artisanale', smallText: 'Vanille, chocolat...' },
            { image: tourteDeBlette, title: 'Tourte de Blettes', smallText: 'Un classique sucré' },
        ],
        },
    ];

    const handleMenuItemClick = (item) => {
        setSelectedItem(item);
    };

    const handleBackClick = () => {
        setSelectedItem(null);
    };

    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#6c6c6c');



    return (
        <div className="menu-container">
            <i className="fa-solid fa-house" onClick={onBackToHomeClick}></i>
            <img className="main-pic" src={mainPic} alt="" />
            <div className="menu-content-container">
                <ChezPippoLogo className='chez-pippo-logo'/>
                <Favorite/>
                <div className="menu-content-scroll">
                    <div className="menu-content">
                        <h1 className='menu-title'>{selectedItem ? selectedItem.title : 'Menu'}</h1>
                        {!selectedItem &&<div className="blue-box"></div>}
                        {selectedItem ? (
                        <>
                            <Button style={{height: "5vh", width: "10vw", padding: "5px 15px"}} onClick={handleBackClick} text="Retour"/>
                            {selectedItem.elements.map((element, index) => (
                            <MenuItemElement
                                key={index}
                                image={element.image}
                                title={element.title}
                                smallText={element.smallText}
                            />
                            ))}
                        </>
                        ) : (
                        menuItems.map((item, index) => (
                            <MenuItem
                            key={index}
                            image={item.image}
                            title={item.title}
                            smallText={item.smallText}
                            onClick={() => handleMenuItemClick(item)}
                            />
                        ))
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Menu;
