import './menu.css';
import mainPic from '../../assets/chez-pippo-main-pic.png'
import {ReactComponent as ChezPippoLogo} from '../../assets/logo-chez-pippo.svg'
import Favorite from '../tools/favorite/favorite';
import MenuItem from '../tools/menu-item/menu-item';
import MenuItemElement from '../tools/menu-item-element/menu-item-element';
import Cart from '../tools/cart/cart';
import { menuItems } from '../../data/menu-data';

function Menu({onBackToHomeClick, onMenuElementClick, selectedItem, setSelectedItem }) {

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
            {selectedItem ? (
                <i className="fa-solid fa-arrow-left" onClick={handleBackClick}></i> // Retour au sous-menu
            ) : (
                <i className="fa-solid fa-house" onClick={onBackToHomeClick}></i> // Retour à la page d'accueil
            )}
            <img className="main-pic" src={mainPic} alt="" />
            <div className="menu-content-container">
                <ChezPippoLogo className='chez-pippo-logo'/>
                <Favorite/>
                <div className="menu-content-scroll">
                    <div className="menu-content">
                        <h1 className='menu-title'>{selectedItem ? selectedItem.title : 'Menu'}</h1>
                        <div className="blue-box"></div>
                        {selectedItem ? (
                        <>
                            {selectedItem.elements.map((element, index) => (
                            <MenuItemElement
                                key={index}
                                image={element.image}
                                title={element.title}
                                smallText={element.smallText}
                                onClick={() => onMenuElementClick(element)}
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
                    <Cart/>
                </div>
            </div>
        </div>
    );
}

export default Menu;
