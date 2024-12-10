import './join-group-pop-up.css';
import Button from '../../tools/button/button';
import Input from '../../tools/input/input';
import { useState, useEffect } from 'react';

function JoinGroupPopUp({ onClose, closing, setValidationPopUpVisible, setGroupId, setGroupNameFB }) {
    const [inputValue, setInputValue] = useState('');
    const [joinedGroups, setJoinedGroups] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Fetch groups from the server
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

    const handleJoinClick = () => {
        // Find group by ID and check status
        const group = joinedGroups.find(
            (group) => group.groupId === parseInt(inputValue, 10) && group.status === 'pending'
        );

        if (group) {
            setGroupId(group.groupId);
            setGroupNameFB(group.groupName);
            onClose();
            setTimeout(() => {
                setValidationPopUpVisible(true);
            }, 300);
        } else {
            alert('ID not found or group not pending');
        }
    };

    if (loading) {
        return <div>Loading groups...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className={`join-group-container ${closing ? 'closing' : ''}`}>
            <div className="popup-content">
                <i className="fa-solid fa-xmark" onClick={onClose}></i>
                <h4 className="popup-title">Join Group Order</h4>
                <div className="separation-line"></div>
                <div className="input-button-container">
                    <Input
                        placeholder="Group Code"
                        value={inputValue}
                        onChange={(e) => setInputValue(e.target.value)}
                    />
                    <Button text="Join Group Order" onClick={handleJoinClick} />
                </div>
            </div>
        </div>
    );
}

export default JoinGroupPopUp;
