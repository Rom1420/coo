import React, { useEffect, useState } from 'react';
import CounterButton from '../counter-button/counter-button';
import "./counter.css"

function Counter({onCountChange}) {
  const [count, setCount] = useState(0);

  useEffect(() => {
    onCountChange(count)
  },[count, onCountChange])


  const handleIncrement = () => {
    setCount((prevCount) => prevCount + 1);
  };

  const handleDecrement = () => {
    setCount((prevCount) => (prevCount > 1 ? prevCount - 1 : 0));
  };
  
  return (
    <div className="counter-buttons-container">
      <CounterButton text="-" onClick={handleDecrement} />
      <div className="portions-counter">{count}</div>
      <CounterButton text="+" onClick={handleIncrement} />
    </div>
  );
}

export default Counter;
