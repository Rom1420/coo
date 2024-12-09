import './button.css';

function Button({text, onClick, style, imageSrc}) {
  return (
    <div className="button" onClick={onClick} style={style}>
      {imageSrc && <img src={imageSrc} alt="" style={{ marginRight: '8px', height: '80%' }} />}
      {text}</div>
  );
}

export default Button;
