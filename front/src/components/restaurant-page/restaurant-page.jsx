import './restaurant-page.css';
import Rating from '../tools/rating/rating';
import Button from '../tools/button/button';
import Favorite from '../tools/favorite/favorite';
import Cart from '../tools/cart/cart';

function RestaurantPage({onBackToHomeClick, onMenuButtonClick, onCheckCartClick, restaurant}) {
    console.log(restaurant)
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');
    const logoUrl = `/logos/logo-${restaurant.name.toLowerCase().replace(/ /g, '-')}.png`;
    const imageUrl = `/images/${restaurant.name.toLowerCase().replace(/ /g, '-')}.png`;
    const galerieUrl = `/images/gallerie-${restaurant.name.toLowerCase().replace(/ /g, '-')}.png`;
    
    themeColorMetaTag.setAttribute('content', '#6c6c6c');

    const daysOfWeek = ["MO", "TU", "WE", "TH", "FR", "SA", "SU"];
    const dayMapping = {
    MO: 'MONDAY',
    TU: 'TUESDAY',
    WE: 'WEDNESDAY',
    TH: 'THURSDAY',
    FR: 'FRIDAY',
    SA: 'SATURDAY',
    SU: 'SUNDAY'
    }; 
    const schedule = restaurant.weeklySchedules[Object.keys(restaurant.weeklySchedules)[0]]; // Prendre un jour comme référence


    return (
        <div className="restaurant-container">
            <i className="fa-solid fa-house" onClick={onBackToHomeClick}></i>
            <img className="main-pic" src={imageUrl} alt="" />
            <div className="restaurant-content-container">
                <img src={logoUrl} className='restaurant-logo' alt=''/>
                <Favorite/>
                <div className="restaurant-content-scroll">
                    <div className="restaurant-content">
                        <h2 className='restaurant-name'>{restaurant.name}</h2>
                        <div className="rating-container">
                            <Rating/>
                            <p className='rating-text'>1,257 Reviews</p>
                        </div>
                        <div className="schedule-container">
                            <div className="weekly-schedule">
                                {daysOfWeek.map((day, index) => {
                                    const dayKey = dayMapping[day];
                                    const isOpen = restaurant.weeklySchedules[dayKey]; 
                                    return (
                                        <span 
                                            key={index} 
                                            className={`day ${isOpen ? 'open' : ''}`}
                                        >
                                            {day}
                                        </span>
                                    );
                                })}
                            </div>
                            {schedule ? (
                                <p className="time-text">{`${schedule.opening} - ${schedule.closing}`}</p>
                            ) : (
                                <p className="time-text">Closed</p>
                            )}
                        </div>
                        <div className="description-container">
                            <h5 className='description-title'>Description</h5>
                            <p className='description-text'>Restaurant qui propose de la cuisine {restaurant.typeCuisine}</p>
                        </div>
                        <div className="separation-line"></div>
                        <div className="galerie-container">
                            <img className='galerie-pics' src={galerieUrl} alt="" />
                        </div>
                    </div>
                    <div className="menu-button-container">
                        <Button style={{height: "4vh"}}text="Menu" onClick={onMenuButtonClick}/>
                    </div>

                    <Cart onCheckCartClick={onCheckCartClick} />
                </div>
            </div>
        </div>
    );
}

export default RestaurantPage;
