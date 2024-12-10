import './my-groups-pop-up.css';
import Button from '../../tools/button/button'; // Assurez-vous que Button est bien importé
import { useState } from 'react';

function MyGroupsPopUp({ onClose, closing }) {
    // Récupérer les groupes rejoins qui sont en état pending !!!
    const [joinedGroups, setJoinedGroups] = useState([
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
    ]);

    // Fonction pour valider le groupe
    const handleValidateGroup = async (groupId) => {
        try {
            const response = await fetch(`http://localhost:8003/api/group/validate?groupId=${groupId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                setJoinedGroups(prevGroups =>
                    prevGroups.map(group =>
                        group.id === groupId ? { ...group, status: 'validated' } : group
                    )
                );
            } else {
                console.error("Error validating group:", response.statusText);
            }
        } catch (error) {
            console.error("An error occurred:", error);
        }
    };

    return (
        <div className={`my-groups-container ${closing ? 'closing' : ''}`}>
            <div className="popup-content">
                <i className="fa-solid fa-xmark" onClick={onClose}></i>
                <h4 className="popup-title">My Groups</h4>
                <div className="separation-line"></div>
                <div className="groups-list-container">
                    {joinedGroups && joinedGroups.length > 0 ? (
                        <ul className="groups-list">
                            {joinedGroups.map((group, index) => (
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
