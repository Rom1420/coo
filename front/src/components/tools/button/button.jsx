import './button.css';

function Button({text, onClick, style}) {
  return (
    <div className="button" onClick={onClick} style={style}>{text}</div>
  );
}

export default Button;
