import React, { useState } from 'react';
import './filter.css';
import Button from '../button/button';

function Filter({ text, type }) {
    const cuisineTypes = ['Italienne', 'Asiatique', 'Francaise', 'FastFood', 'Méditerranéenne', 'Végétarienne', 'Indienne', 'Autre'];
    const days = ['Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su'];
    const times = ['Morning', 'Afternoon', 'Evening'];

    const [isContentVisible, setContentVisible] = useState(false);
    const [isTransitioning, setTransitioning] = useState(false);

    const [selectedCuisines, setSelectedCuisines] = useState([]);
    const [selectedDays, setSelectedDays] = useState([]);
    const [selectedTimes, setSelectedTimes] = useState([]);

    const handleSelection = (value, setSelected, selected) => {
        if (selected.includes(value)) {
            setSelected(selected.filter((item) => item !== value));
        } else {
            setSelected([...selected, value]);
        }
    };

    const handleToggle = () => {
        if (!isContentVisible) {
            setContentVisible(true);
            setTimeout(() => setTransitioning(true), 100); 
        } else {
            setContentVisible(false);
            setTransitioning(false);
        }
    };

    return (
        <div className="filter-component-container">
            <div className="filter-button" onClick={handleToggle}>
                {text}
            </div>
            {isContentVisible && (
                <div className={`filter-content ${isTransitioning ? 'visible' : ''}`}>
                    {type === 'restaurant' && (
                        <>
                            {/* Cuisine Types */}
                            <div className="cuisine-type-filter-container">
                                <h5 className="filter-small-text">Type of cuisine</h5>
                                <div className="cuisine-type-list">
                                    <div className="cuisine-type-scroll">
                                        {cuisineTypes.map((cuisine) => (
                                            <div
                                                key={cuisine}
                                                className={`cuisine-type-element ${
                                                    selectedCuisines.includes(cuisine) ? 'selected' : ''
                                                }`}
                                                onClick={() => handleSelection(cuisine, setSelectedCuisines, selectedCuisines)}
                                            >
                                                {cuisine}
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            </div>

                            {/* Days */}
                            <div className="date-filter-container">
                                <h5 className="filter-small-text">Day</h5>
                                <div className="cuisine-type-list">
                                    <div className="cuisine-type-scroll">
                                        {days.map((day) => (
                                            <div
                                                key={day}
                                                className={`cuisine-type-element ${
                                                    selectedDays.includes(day) ? 'selected' : ''
                                                }`}
                                                onClick={() => handleSelection(day, setSelectedDays, selectedDays)}
                                            >
                                                {day}
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            </div>

                            {/* Times */}
                            <div className="time-filter-container">
                                <h5 className="filter-small-text">Hour</h5>
                                <div className="cuisine-type-list">
                                    <div className="cuisine-type-scroll">
                                        {times.map((time) => (
                                            <div
                                                key={time}
                                                className={`cuisine-type-element ${
                                                    selectedTimes.includes(time) ? 'selected' : ''
                                                }`}
                                                onClick={() => handleSelection(time, setSelectedTimes, selectedTimes)}
                                            >
                                                {time}
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            </div>
                        </>
                    )}

                    {type === 'article' && (
                        <div>Contenu des articles</div>
                    )}
                    <Button style={{padding:"10px 2rem"}} text={"Apply"} onClick={handleToggle}/>
                </div>
            )}
        </div>
    );
}

export default Filter;
