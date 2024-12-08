import React, { useState } from 'react';
import './filter.css';
import Button from '../button/button';
import Input from '../input/input';

function Filter({ text, type, onApplyFilters }) {
    const cuisineTypes = ['Italienne', 'Asiatique', 'Francaise', 'FastFood', 'Méditerranéenne', 'Végétarienne', 'Indienne', 'Autre'];
    const days = ['Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su'];

    const [isContentVisible, setContentVisible] = useState(false);
    const [isTransitioning, setTransitioning] = useState(false);

    const [selectedCuisine, setSelectedCuisine] = useState(null);
    const [selectedDay, setSelectedDay] = useState(null);
    const [hour, setHour] = useState('');
    const [minutes, setMinutes] = useState('');

    const handleHourChange = (e) => setHour(e.target.value);
    const handleMinutesChange = (e) => setMinutes(e.target.value);

    const handleSelection = (value, setSelected, selected) => {
        setSelected(selected === value ? null : value);
    };

    const convertDayToFullName = (abbreviation) => {
        const dayMapping = {
            Mo: "MONDAY",
            Tu: "TUESDAY",
            We: "WEDNESDAY",
            Th: "THURSDAY",
            Fr: "FRIDAY",
            Sa: "SATURDAY",
            Su: "SUNDAY",
        };
        return dayMapping[abbreviation] || null;
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

    const handleApplyFilters = () => {
        const formattedTime = hour 
                                ? `${hour.padStart(2, '0')}:${(minutes || '00').padStart(2, '0')}`
                                : null;
        
        if ((hour && (hour < 0 || hour > 23)) || (minutes && (minutes < 0 || minutes > 59))) {
            alert('Veuillez entrer une heure valide.');
            return;
        }
        
        const filters = {
            cuisineType: selectedCuisine|| null, 
            day: selectedDay ? convertDayToFullName(selectedDay) : null,
            time: formattedTime,
        };

        onApplyFilters(filters); 
        setContentVisible(false);
    };

    return (
        <div className="filter-component-container">
            <div className="filter-button" onClick={() => handleToggle()}>
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
                                                    selectedCuisine === cuisine ? 'selected' : ''
                                                }`}
                                                onClick={() => handleSelection(cuisine, setSelectedCuisine, selectedCuisine)}
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
                                    <div className="date-type-scroll">
                                        {days.map((day) => (
                                            <div
                                                key={day}
                                                className={`day-type-element ${
                                                    selectedDay === day ? 'selected' : ''
                                                }`}
                                                onClick={() => handleSelection(day, setSelectedDay, selectedDay)}
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
                                <div className="time-type-list">
                                    <Input
                                        placeholder="Heure"
                                        type="number"
                                        min="0"
                                        max="23"
                                        value={hour}
                                        onChange={handleHourChange}
                                    />
                                    <Input
                                        placeholder="Minutes"
                                        type="number"
                                        min="0"
                                        max="59"
                                        value={minutes}
                                        onChange={handleMinutesChange}
                                    />
                                </div>
                            </div>
                        </>
                    )}

                    {type === 'article' && (
                        <div>Contenu des articles</div>
                    )}
                    <Button style={{padding:"10px 2rem"}} text={"Apply"} onClick={handleApplyFilters}/>
                </div>
            )}
        </div>
    );
}

export default Filter;
