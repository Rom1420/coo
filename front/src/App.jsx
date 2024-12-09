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
  const [cart, setCart] = useState([]);

  const [groupIdForApp, setGroupIdForApp] = useState(null);

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
    console.log(cart, article)
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
      {currentPage === 'home' && <HomePage onOrderNowClick={() => setCurrentPage('restaurantPage')} setRestaurant={setRestaurant} setCurrentPage={setCurrentPage} setGroupIdForApp={setGroupIdForApp} groupIdForApp={groupIdForApp}/ >}
      {currentPage === 'restaurantPage' && (
          selectedRestaurant 
          ? (
              <RestaurantPage 
                onBackToHomeClick={() => {
                  console.log(groupIdForApp)
                  if(!groupIdForApp){
                    setCurrentPage('home'); setRestaurant(null)
                  } else{
                    console.log('hahahaha')
                    setCurrentPage('home');
                  }
                }}
                onMenuButtonClick={() => setCurrentPage('menu')} 
                onCheckCartClick={() => setCurrentPage('cartResume')}
                restaurant={selectedRestaurant}
              />
            ) 
          : setCurrentPage('restaurants-list')
      )}
      {currentPage === 'menu' && (
        <Menu
          onBackToRestaurantClick={() => setCurrentPage('restaurantPage')}
          onMenuElementClick={(menuElement) => {
            setSelectedMenuElement(menuElement); 
            setCurrentPage('menuElementDetail'); 
          }}
          selectedItem={selectedItem} 
          setSelectedItem={setSelectedItem}
          onCheckCartClick={() =>  setCurrentPage('cartResume')}
          restaurant={selectedRestaurant}
        />
      )}
      {currentPage === 'menuElementDetail' && (
          <MenuElementDetail
          onBackToPrevClick={() => setCurrentPage('menu')}
          menuElement={selectedMenuElement}
          onCheckCartClick={() =>  setCurrentPage('cartResume')}
          onAddArticle={addArticleToCart}
          restaurant={selectedRestaurant}
      />
      )}
      {currentPage === 'cartResume' && (
        <CartResume 
          onBackToPrevClick={() => {
            if (selectedMenuElement && !selectedItem) {
              setCurrentPage('menuElementDetail'); 
            } else if(selectedItem){
              setCurrentPage('menu'); 
            } else{
              setCurrentPage('restaurantPage'); 
            }
          }}
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
