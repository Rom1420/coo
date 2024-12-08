import './home-page.css';
import { ReactComponent as Tmax } from '../../assets/tmax.svg';
import Button from '../tools/button/button';
import { useEffect, useState } from 'react';
import JoinGroupPopUp from '../pop-ups/join-group-pop-up/join-group-pop-up';
import CreateGroupPopUp from '../pop-ups/create-group-pop-up/create-group-pop-up';
import ValidationJoinPopUp from '../pop-ups/validation-join-pop-up/validation-join-pop-up';
import ValidationCreatePopUp from "../pop-ups/validation-create-pop-up/validation-create-pop-up";
import MyGroupsPopUp from "../pop-ups/my-groups-pop-up/my-groups-pop-up";

function HomePage({onOrderNowClick}) {

  const [isJoinGroupPopUpVisible, setJoinGroupPopUpVisible] = useState(false);
  const [isValidationPopUpVisible, setValidationPopUpVisible] = useState(false);
  const [isCreateGroupPopUpVisible, setCreateGroupPopUpVisible] = useState(false);
  const [isValidationCreatePopUpVisible, setValidationCreatePopUpVisible] = useState(false);
  const [isMyGroupsPopUpVisible, setMyGroupsPopUpVisible] = useState(false)
  const [isClosing, setIsClosing] = useState(false);
  const [groupId, setGroupId] = useState(null);
  const [joinedGroups, setJoinedGroups] = useState([]);



  useEffect(() => {
    const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');

    if (isJoinGroupPopUpVisible || isValidationPopUpVisible || isCreateGroupPopUpVisible || isValidationCreatePopUpVisible) {
      themeColorMetaTag.setAttribute('content', '#6c6c6c'); // Couleur sombre quand ya le pop up
    } else {
      themeColorMetaTag.setAttribute('content', '#ffffff'); // Couleur par défaut
    }
  }, [isJoinGroupPopUpVisible, isValidationPopUpVisible, isCreateGroupPopUpVisible, isValidationCreatePopUpVisible]);

  const handleJoinGroupeClick = () => {
    if (isJoinGroupPopUpVisible) {
      setIsClosing(true);
      setTimeout(() => {
        setJoinGroupPopUpVisible(false);
        setIsClosing(false);
      }, 300);  
    } else {
      setJoinGroupPopUpVisible(true);
    }
  };

  const handleCreateGroupeClick = () => {
    if (isCreateGroupPopUpVisible) {
      setIsClosing(true);
      setTimeout(() => {
        setCreateGroupPopUpVisible(false);
        setIsClosing(false);
      }, 300);  
    } else {
      setCreateGroupPopUpVisible(true);
    }
  };

  const handleMyGroupsClick = () => {
    if (isMyGroupsPopUpVisible) {
      setIsClosing(true);
      setTimeout(() => {
        setMyGroupsPopUpVisible(false);
        setIsClosing(false);
      }, 300);
    } else {
      setMyGroupsPopUpVisible(true);
    }
  };


  return (
    <div className="home">
        {( isJoinGroupPopUpVisible || isCreateGroupPopUpVisible || isValidationPopUpVisible || isValidationCreatePopUpVisible || isMyGroupsPopUpVisible)
              && <div className="darker-overlay"></div>}
        <Tmax/>
        <h3 className="small-text">Order your favorite food delivered fast, wherever you are</h3>
        {!isValidationPopUpVisible && <div className="buttons-container">
            <Button text="Order Now" onClick={onOrderNowClick} />
            <Button text="Create Group Order" onClick={handleCreateGroupeClick}/>
            <Button text="Join Group Order" onClick={handleJoinGroupeClick} />
            <Button text="My Groups" onClick={handleMyGroupsClick} />
        </div>}
        {isJoinGroupPopUpVisible && <JoinGroupPopUp onClose={handleJoinGroupeClick} closing={isClosing} setValidationPopUpVisible={setValidationPopUpVisible} setGroupId={setGroupId}/>}
        {isCreateGroupPopUpVisible && <CreateGroupPopUp onClose={handleCreateGroupeClick} closing={isClosing} setValidationCreatePopUpVisible={setValidationCreatePopUpVisible} setGroupId={setGroupId}/>}
        {isValidationCreatePopUpVisible && <ValidationCreatePopUp onClose={()=> setValidationCreatePopUpVisible(false)} closing={isClosing} groupId={groupId}/>}
        {isValidationPopUpVisible && <ValidationJoinPopUp onClose={() => setValidationPopUpVisible(false)} closing={isClosing} onOrderNowClick={onOrderNowClick}/>}
        {isMyGroupsPopUpVisible && <MyGroupsPopUp onClose={handleMyGroupsClick} closing={isClosing} joinedGroups={joinedGroups} />}
    </div>
  );
}

export default HomePage;
