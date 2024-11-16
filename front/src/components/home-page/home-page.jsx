import './home-page.css';
import { ReactComponent as Tmax } from '../../assets/tmax.svg';
import Button from '../button/button';


function HomePage() {
  return (
    <div className="home">
        <Tmax/>
        <h3 className="small-text">Order your favorite food delivered fast, wherever you are</h3>
        <div className="buttons-container">
            <Button text="Order Now" />
            <Button text="Create Group Order" />
            <Button text="Join Group Order" />
        </div>
    </div>
  );
}

export default HomePage;
