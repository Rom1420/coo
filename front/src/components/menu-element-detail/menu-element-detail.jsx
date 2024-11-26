import './menu-element-detail.css';
import mainPic from '../../assets/chez-pippo-main-pic.png'
import {ReactComponent as ChezPippoLogo} from '../../assets/logo-chez-pippo.svg'
import Rating from '../tools/rating/rating';
import Favorite from '../tools/favorite/favorite';
import Cart from '../tools/cart/cart';
import Counter from '../tools/counter/counter';

function MenuElementDetail({onBackToPrevClick, menuElement}) {
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#6c6c6c');

    return (
        <div className="menu-element-container">
            <i className="fa-solid fa-arrow-left" onClick={onBackToPrevClick}></i>
            <img className="main-pic" src={menuElement?.image || mainPic} alt={menuElement?.title} />
            <div className="menu-element-content-container">
                <ChezPippoLogo className='chez-pippo-logo'/>
                <Favorite/>
                <div className="menu-element-content-scroll">
                    <div className="menu-element-content">
                        <h2 className='restaurant-name'>{menuElement?.title}</h2>
                        <div className="rating-container">
                            <Rating/>
                            <p className='rating-text'>1,257 Reviews</p>
                        </div>
                        <div className="right-container">
                            <p className='time-text'>11:30-14:30</p>
                            <p className='time-text'>17:30-22:00</p>
                        </div>
                        <div className="description-container">
                            <h5 className='description-title'>Description</h5>
                            <p className='description-text'>{menuElement?.fullDescription}</p>
                        </div>
                        <div className="separation-line"></div>
                        <div className="portions-container">
                            <h5 className="portions-title">Number of portions</h5>
                            <Counter/>
                        </div>
                    </div>
                    <Cart/>
                </div>
            </div>
        </div>
    );
}

export default MenuElementDetail;
