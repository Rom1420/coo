import './menu.css';
import mainPic from '../../assets/chez-pippo-main-pic.png'
import {ReactComponent as ChezPippoLogo} from '../../assets/logo-chez-pippo.svg'
import Favorite from '../tools/favorite/favorite';
import MenuItem from '../tools/menu-item/menu-item';

// Import des images pour les menu items
import socca from '../../assets/menu/socca.png'
import pissaladiere from '../../assets/menu/pissaladiere.png'
import biere from '../../assets/menu/biere.png'
import tourteDeBlette from '../../assets/menu/tourte-de-blette.png'

function Menu({onBackToHomeClick }) {

    const menuItems = [
        { image: socca, title: 'Socca', smallText: 'Orignale ou garnies' },
        { image: pissaladiere, title: 'Entrées', smallText: 'Pissaladière, tartinade, pan bagnat...' },
        { image: biere, title: 'Boissons', smallText: 'Tourte de blettes, glaces' },
        { image: tourteDeBlette, title: 'Desserts', smallText: 'Bières, vins, sodas...' },
    ];

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
                        <h1 className='menu-title'>Menu</h1>
                        <div className="blue-box"></div>
                        {menuItems.map((item, index) => (
                        <MenuItem
                            key={index}
                            image={item.image}
                            title={item.title}
                            smallText={item.smallText}
                        />
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Menu;
