import './input.css';

function Input({ placeholder, value, onChange, id }) {
  return (
      <input
          id={id}
          type="text"
          className="input"
          placeholder={placeholder}
          value={value} // Liaison avec l'état React
          onChange={onChange} // Gestion de l'événement onChange
      />
  );
}

export default Input;
