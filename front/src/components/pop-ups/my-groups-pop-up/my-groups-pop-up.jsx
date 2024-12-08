import './my-groups-pop-up.css';
import Button from '../../tools/button/button'; // Assurez-vous que Button est bien importé
import { useState } from 'react';

function MyGroupsPopUp({ onClose, closing }) {
    const mockJoinedGroups = [
        {
            id: 1,
            groupName: "Pizza Lovers",
            deliveryLocation: "123 Main Street, Cityville",
            status: "pending"
        },
        {
            id: 2,
            groupName: "Burger Bunch",
            deliveryLocation: "456 Burger Avenue, Foodtown",
            status: "pending"
        },
        {
            id: 3,
            groupName: "Sushi Squad",
            deliveryLocation: "789 Sushi Road, Fishburg",
            status: "pending"
        }
    ];

    const handleValidateGroup = (groupId) => {
        console.log("Group validated: ", groupId);
        // Ajoutez votre logique pour valider le groupe ici
    };

    return (
        <div className={`my-groups-container ${closing ? 'closing' : ''}`}>
            <div className="popup-content">
                <i className="fa-solid fa-xmark" onClick={onClose}></i>
                <h4 className="popup-title">My Groups</h4>
                <div className="separation-line"></div>
                <div className="groups-list-container">
                    {mockJoinedGroups && mockJoinedGroups.length > 0 ? (
                        <ul className="groups-list">
                            {mockJoinedGroups.map((group, index) => (
                                <li key={index}>
                                    <div>
                                        <strong>{group.groupName}</strong>
                                        <p>ID: {group.id}</p>
                                        <p>Delivery Location: {group.deliveryLocation}</p>
                                        <p>Status: {group.status}</p>
                                    </div>
                                    <Button
                                        text="Validate Group"
                                        onClick={() => handleValidateGroup(group.id)}
                                    />
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No groups joined yet.</p>
                    )}
                </div>
            </div>
        </div>
    );
}

export default MyGroupsPopUp;
