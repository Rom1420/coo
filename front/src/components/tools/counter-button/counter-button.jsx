import './counter-button.css';

function CounterButton({text, onClick, style}) {
  return (
    <div className="counter-button" onClick={onClick} style={style}>{text}</div>
  );
}

export default CounterButton;
