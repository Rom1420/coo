import Rating from '../rating/rating';
import './restaurant-resume.css';


const restaurantAddresses = [
  { name: "Chez Pippo", address: "13 Rue Bavastro, 06300 Nice" },
  { name: "KFC", address: "64 Av. Jean Médecin, 06000 Nice" },
  { name: "McDonald's", address: "20 Bd Gambetta, 06000 Nice" },
  { name: "Burger King", address: "41 Av Jean Médecin, 06000 Nice" },
  { name: "Subway", address: "20 Bd Gambetta, 06000 Nice" },
];

function getAddressByName(restaurantName) {
  const restaurant = restaurantAddresses.find((r) => r.name === restaurantName);
  return restaurant ? restaurant.address : "Adresse inconnue";
}

function RestaurantResume({restaurant}) {
  const address = getAddressByName(restaurant.name);

  return (
    <div className="restaurant-resume-content">
        <h4 className='restaurant-resume-title'>{restaurant.name}</h4>
         <Rating color="#00A5FF" size="1rem" />
        <p className='restaurant-resume-small-text'>Cuisine {restaurant.typeCuisine}</p>
        <div className="restaurant-address">
            <i className="fa-solid fa-location-dot"></i>
            <p className='restaurant-resume-small-text'>{address}</p>
        </div>
    </div>
  );
}

export default RestaurantResume;
