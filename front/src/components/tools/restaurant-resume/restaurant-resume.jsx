import Rating from '../rating/rating';
import './restaurant-resume.css';

function RestaurantResume() {
  return (
    <div className="restaurant-resume-content">
        <h4 className='restaurant-resume-title'>Chez Pippo</h4>
         <Rating color="#00A5FF" size="1rem" />
        <p className='restaurant-resume-small-text'>Spécialités Niçoises</p>
        <div className="restaurant-address">
            <i className="fa-solid fa-location-dot"></i>
            <p className='restaurant-resume-small-text'>13 Rue Bavastro, 06300 Nice</p>
        </div>
    </div>
  );
}

export default RestaurantResume;
