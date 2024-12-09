import React, { useState } from 'react';
import CounterButton from '../counter-button/counter-button';
import "./counter.css"

function Counter({onCountChange}) {
  const [count, setCount] = useState(0);

  const handleIncrement = () => {
    setCount((prevCount) => {
      const newCount = prevCount + 1;
      onCountChange(newCount); 
      return newCount;
    });
  };

  const handleDecrement = () => {
    setCount((prevCount) => {
      const newCount = prevCount > 1 ? prevCount - 1 : 0;
      onCountChange(newCount); 
      return newCount;
    });
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
