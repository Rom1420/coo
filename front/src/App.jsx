import './App.css';
import HomePage from './components/home-page/home-page';
import ChezPippo from './components/chez-pippo/chez-pippo';
import Menu from './components/menu/menu';
import { useState } from 'react';

function App() {

  const [currentPage, setCurrentPage] = useState('home');

  return (
    <div className="App">
      {currentPage === 'home' && <HomePage onOrderNowClick={() => setCurrentPage('chezPippo')} />}
      {currentPage === 'chezPippo' && (
        <ChezPippo 
          onBackToHomeClick={() => setCurrentPage('home')} 
          onMenuButtonClick={() => setCurrentPage('menu')} 
        />
      )}
      {currentPage === 'menu' && <Menu onBackToHomeClick={() => setCurrentPage('home')} />}
    </div>
  );
}

export default App;
