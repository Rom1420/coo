import React, { useState } from 'react';
import './cart.css';

function Cart({ onCheckCartClick }) {
  const [showPopup, setShowPopup] = useState(false);

  const togglePopup = () => {
    setShowPopup(!showPopup);
  };

  return (
    <div className="cart-container">
      <i className="fa-solid fa-cart-shopping" onClick={togglePopup}></i>
      {showPopup && (
        <div className="cart-popup">
          <button className="popup-option" onClick={() => {
                onCheckCartClick();
                }}>
                Check cart
                </button>

          <button className="popup-option" onClick={() => alert('Add article')}>
            Add Article
          </button>
        </div>
      )}
    </div>
  );
}

export default Cart;
