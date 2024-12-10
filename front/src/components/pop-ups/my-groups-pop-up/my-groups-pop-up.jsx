import './my-groups-pop-up.css';
import Button from '../../tools/button/button'; // Assurez-vous que Button est bien importé
import { useState, useEffect } from 'react';

function MyGroupsPopUp({ onClose, closing }) {
    const [joinedGroups, setJoinedGroups] = useState([]);
    const [loading, setLoading] = useState(true); // Pour afficher un indicateur de chargement
    const [error, setError] = useState(null); // Pour gérer les erreurs


    useEffect(() => {
        const fetchGroups = async () => {
            try {
                const response = await fetch('http://localhost:8001/api/group/create', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    const groups = await response.json();
                    setJoinedGroups(groups);
                } else {
                    throw new Error(`Error: ${response.status} - ${response.statusText}`);
                }
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchGroups();
    }, []);

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
                        group.groupId === groupId ? { ...group, status: 'validated' } : group
                    )
                );
                console.log("Group validated successfully");
            } else {
                console.error("Error validating group:", response.statusText);
            }
        } catch (error) {
            console.error("An error occurred:", error);
        }
    };

    // Rendu du composant
    return (
        <div className={`my-groups-container ${closing ? 'closing' : ''}`}>
            <div className="popup-content">
                <i className="fa-solid fa-xmark" onClick={onClose}></i>
                <h4 className="popup-title">My Groups</h4>
                <div className="separation-line"></div>
                <div className="groups-list-container">
                    {loading ? (
                        <p>Loading groups...</p>
                    ) : error ? (
                        <p>Error loading groups: {error}</p>
                    ) : joinedGroups && joinedGroups.length > 0 ? (
                        <ul className="groups-list">
                            {joinedGroups.map((group, index) => (
                                <li key={index}>
                                    <div>
                                        <strong>{group.groupName}</strong>
                                        <p>ID: {group.groupId}</p>
                                        <p>Delivery Location: {group.groupOrderDeliveryLocation}</p>
                                        <p>Status: {group.status}</p>
                                    </div>
                                    {group.status !== "validated" && (
                                        <Button
                                            text="Validate Group"
                                            onClick={() => handleValidateGroup(group.groupId)}
                                        />
                                    )}
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
