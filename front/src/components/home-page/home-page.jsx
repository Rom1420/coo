import './home-page.css';
import { ReactComponent as Tmax } from '../../assets/tmax.svg';
import Button from '../button/button';
import { useState } from 'react';
import JoinGroupPopUp from '../join-group-pop-up/join-group-pop-up';

function HomePage() {

  const [isJoinGroupPopUpVisible, setJoinGroupPopUpVisible] = useState(false);
  const [isClosing, setIsClosing] = useState(false);
  const themeColorMetaTag = document.querySelector('meta[name="theme-color"]');

  const handleJoinGroupeClick = () => {
    if (isJoinGroupPopUpVisible) {
      setIsClosing(true);
      setTimeout(() => {
        setJoinGroupPopUpVisible(false);
        setIsClosing(false);
        themeColorMetaTag.setAttribute('content', '#ffffff');
      }, 300);  
    } else {
      setJoinGroupPopUpVisible(true);
      themeColorMetaTag.setAttribute('content', '#6c6c6c'); 
    }
  };

  return (
    <div className="home">
        {isJoinGroupPopUpVisible && <div className="darker-overlay"></div>}
        <Tmax/>
        <h3 className="small-text">Order your favorite food delivered fast, wherever you are</h3>
        <div className="buttons-container">
            <Button text="Order Now" />
            <Button text="Create Group Order" />
            <Button text="Join Group Order" onClick={handleJoinGroupeClick} />
        </div>
        {isJoinGroupPopUpVisible && <JoinGroupPopUp onClose={handleJoinGroupeClick} closing={isClosing}/>}
    </div>
  );
}

export default HomePage;
