import './App.css';
import HomePage from './components/home-page/home-page';
import Menu from './components/menu/menu';
import MenuElementDetail from './components/menu-element-detail/menu-element-detail';
import CartResume from './components/cart-resume/cart-resume';
import { useState, useEffect } from 'react';
import RestaurantList from './components/restaurants-list/restaurants-list';
import RestaurantPage from './components/restaurant-page/restaurant-page';

function App() {

  const [currentPage, setCurrentPage] = useState('home');
  const [selectedRestaurant, setRestaurant] = useState(null);
  const [selectedMenuElement, setSelectedMenuElement] = useState(null);
  const [selectedItem, setSelectedItem] = useState(null);
  const [cart, setCart] = useState([]);

  const addArticleToCart = (article) => {
    setCart((prevCart) => {
      const existingArticleIndex = prevCart.findIndex((item) => item.name === article.name);
      
      if (existingArticleIndex !== -1) {
        const updatedCart = [...prevCart];
        updatedCart[existingArticleIndex].quantity += Number(article.quantity);
        return updatedCart;
      } else {
        return [...prevCart, { ...article, quantity: Number(article.quantity) }];
      }
    });
  };

  const removeArticleFromCart = (index) => {
    setCart((prevCart) => {
      const updatedCart = [...prevCart];
      if (updatedCart[index].quantity > 1) {
        updatedCart[index].quantity -= 1;
      } else {
        updatedCart.splice(index, 1); 
      }
      return updatedCart;
    });
  };

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
            setSelectedMenuElement(menuElement); 
            setCurrentPage('menuElementDetail'); 
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
          onAddArticle={addArticleToCart}
      />
      )}
      {currentPage === 'cartResume' && (
        <CartResume 
          onBackToPrevClick={() => setCurrentPage('menuElementDetail')}
          cart={cart} 
          removeArticleFromCart={removeArticleFromCart}
          restaurant={selectedRestaurant}
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
