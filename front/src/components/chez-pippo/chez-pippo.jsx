import './chez-pippo.css';
import mainPic from '../../assets/chez-pippo-main-pic.png'
import {ReactComponent as ChezPippoLogo} from '../../assets/logo-chez-pippo.svg'
import galeriePic from '../../assets/chez-pippo-gallerie.png'
import Rating from '../tools/rating/rating';
import Button from '../tools/button/button';
import Favorite from '../tools/favorite/favorite';

function ChezPippo({onBackToHomeClick }) {
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    themeColorMetaTag.setAttribute('content', '#6c6c6c');

    return (
        <div className="restaurant-container">
            <i className="fa-solid fa-house" onClick={onBackToHomeClick}></i>
            <img className="main-pic" src={mainPic} alt="" />
            <div className="restaurant-content-container">
                <ChezPippoLogo className='chez-pippo-logo'/>
                <Favorite/>
                <div className="restaurant-content-scroll">
                    <div className="restaurant-content">
                        <h2 className='restaurant-name'>Chez Pippo</h2>
                        <div className="rating-container">
                            <Rating/>
                            <p className='rating-text'>1,257 Reviews</p>
                        </div>
                        <div className="schedule-container">
                            <p className='time-text'>11:30-14:30</p>
                            <p className='time-text'>17:30-22:00</p>
                        </div>
                        <div className="description-container">
                            <h5 className='description-title'>Description</h5>
                            <p className='description-text'>Chez Pipo, un restaurant incontournable de socca et spécialités à Nice : quand l’art de vivre rencontre la tradition !</p>
                        </div>
                        <div className="separation-line"></div>
                        <div className="galerie-container">
                            <img className='galerie-pics' src={galeriePic} alt="" />
                        </div>
                    </div>
                    <div className="menu-button-container">
                        <Button style={{height: "4vh"}}text="Menu"/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ChezPippo;
