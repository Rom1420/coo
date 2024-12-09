import './App.css';
import HomePage from './components/home-page/home-page';
import Menu from './components/menu/menu';
import MenuElementDetail from './components/menu-element-detail/menu-element-detail';
import CartResume from './components/cart-resume/cart-resume';
import { useState } from 'react';
import RestaurantList from './components/restaurants-list/restaurants-list';
import RestaurantPage from './components/restaurant-page/restaurant-page';

function App() {

  const [currentPage, setCurrentPage] = useState('home');
  const [selectedRestaurant, setRestaurant] = useState(null);
  const [selectedMenuElement, setSelectedMenuElement] = useState(null);
  const [selectedItem, setSelectedItem] = useState(null);

  return (
    <div className="App">
      {currentPage === 'home' && <HomePage onOrderNowClick={() => setCurrentPage('restaurantPage')} setRestaurant={setRestaurant} setCurrentPage={setCurrentPage}/>}
      {currentPage === 'restaurantPage' && (
          selectedRestaurant 
          ? (
              <RestaurantPage 
                onBackToHomeClick={() => {setCurrentPage('home'); setRestaurant(null)}} 
                onMenuButtonClick={() => setCurrentPage('menu')} 
                onCheckCartClick={() => setCurrentPage('cartResume')}
                restaurant={selectedRestaurant}
              />
            ) 
          : setCurrentPage('restaurants-list')
      )}
      {currentPage === 'menu' && (
        <Menu
          onBackToHomeClick={() => setCurrentPage('home')}
          onMenuElementClick={(menuElement) => {
            setSelectedMenuElement(menuElement); // Stocke l'élément sélectionné
            setCurrentPage('menuElementDetail'); // Change la page
          }}
          selectedItem={selectedItem} 
          setSelectedItem={setSelectedItem}
          onCheckCartClick={() =>  setCurrentPage('cartResume')}
        />
      )}
      {currentPage === 'menuElementDetail' && (
          <MenuElementDetail
          onBackToPrevClick={() => setCurrentPage('menu')}
          menuElement={selectedMenuElement}
          onCheckCartClick={() =>  setCurrentPage('cartResume')}
      />
      )}
      {currentPage === 'cartResume' && (
        <CartResume 
          onBackToPrevClick={() => setCurrentPage('menuElementDetail')}
          onBackToHomeClick={() => setCurrentPage('home')}
        />
      )}
      {currentPage === 'restaurants-list' && (
        <RestaurantList 
          closeRestaurantList={() => setCurrentPage('home')}
          onSelectRestaurant={(restaurant) => {
            setRestaurant(restaurant); 
            setCurrentPage('restaurantPage'); 
          }}
          onHomePage={true}
        />
      )}
    </div>
  );
}

export default App;
