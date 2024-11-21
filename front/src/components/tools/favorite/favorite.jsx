import './favorite.css';
import { useState } from 'react';

function Favorite() {
    const [isFilled, setIsFilled] = useState(false);

    const toggleFavorite = () => {
        setIsFilled(!isFilled); 
    };

    return (
    <div className="favorite-container" onClick={toggleFavorite}>
        <i className={`fa-solid fa-heart ${isFilled ? 'filled' : ''}`}></i>
    </div>
    );
}

export default Favorite;
