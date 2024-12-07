import './input.css';

function Input({ placeholder, value, onChange }) {
  return (
      <input
          type="text"
          className="input"
          placeholder={placeholder}
          value={value} // Liaison avec l'état React
          onChange={onChange} // Gestion de l'événement onChange
      />
  );
}

export default Input;
