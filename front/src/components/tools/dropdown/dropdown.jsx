import Button from '../button/button';
import './dropdown.css';

function Dropdown({selectedRestaurant, onRestaurantChange, onDetailsClick, id}) {


    // Liste des restaurants (à récupérer via l'API des restautants)
    const restaurantOptions = [
        'McDonald\'s',
        'KFC',
        'Subway',
        'Burger King',
        'Chez Pippo'
    ];

  return (
    <div className="dropdown-container">
      {/* <label htmlFor="restaurant-select" className="dropdown-label">Group Restaurant: </label> */}
      <select
        id={id}
        value={selectedRestaurant}
        onChange={(e) => onRestaurantChange(e.target.value)}
        className={`restaurant-dropdown ${selectedRestaurant === '' ? 'placeholder-active' : 'value-selected'}`}
  >
        <option value="" disabled className="placeholder-option">Restaurant</option>
        {restaurantOptions.map((restaurant, index) => (
          <option key={index} value={restaurant}>
            {restaurant}
          </option>
        ))}
      </select>
      <Button
        text="Details"
        onClick={onDetailsClick}
        style={{
            gridColumn: '2 / 3', // Ensure it occupies the second column
            gridRow: '1 / 2', // Ensure it occupies the first row
            padding: '0',
            width: '100%',
        }}
        />

    </div>
  );
}

export default Dropdown;
