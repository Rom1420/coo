import React, { useState } from 'react';
import CounterButton from '../counter-button/counter-button';
import "./counter.css"

function Counter() {
  const [count, setCount] = useState(0);

  const handleIncrement = () => setCount(prevCount => prevCount + 1);
  const handleDecrement = () => setCount(prevCount => (prevCount > 0 ? prevCount - 1 : 0)); // Évite les valeurs négatives

  return (
    <div className="counter-buttons-container">
      <CounterButton text="-" onClick={handleDecrement} />
      <div className="portions-counter">{count}</div>
      <CounterButton text="+" onClick={handleIncrement} />
    </div>
  );
}

export default Counter;
