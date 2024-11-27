import './App.css';
import HomePage from './components/home-page/home-page';
import ChezPippo from './components/chez-pippo/chez-pippo';
import Menu from './components/menu/menu';
import MenuElementDetail from './components/menu-element-detail/menu-element-detail';
import CartResume from './components/cart-resume/cart-resume';
import { useState } from 'react';

function App() {

  const [currentPage, setCurrentPage] = useState('home');
  const [selectedMenuElement, setSelectedMenuElement] = useState(null);
  const [selectedItem, setSelectedItem] = useState(null);

  return (
    <div className="App">
      {currentPage === 'home' && <HomePage onOrderNowClick={() => setCurrentPage('chezPippo')} />}
      {currentPage === 'chezPippo' && (
        <ChezPippo 
          onBackToHomeClick={() => setCurrentPage('home')} 
          onMenuButtonClick={() => setCurrentPage('menu')} 
          onCheckCartClick={() =>  setCurrentPage('cartResume')}
        />
      )}
      {currentPage === 'menu' && (
        <Menu
          onBackToHomeClick={() => setCurrentPage('home')}
          onMenuElementClick={(menuElement) => {
            console.log("clicked");
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
        />
      )}
    </div>
  );
}

export default App;
