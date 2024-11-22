import './menu-item-element.css';

/**  Correspond aux elements d'un type de nourriture ( genre bière, vins de la partie Boisson 
avec bière->MenuItemElement et Boisson->MenuItem) */

function MenuItemElement({image, title, smallText}) {
  return (
    <div className="menu-item-element-container">
      <img src={image} className="menu-item-element-pic" alt={title} />
      <div className="menu-item-element-content">
        <h3 className="menu-item-element-title">{title}</h3>
        <p className="menu-item-element-small-text">{smallText}</p>
      </div>
    </div>
  );
}

export default MenuItemElement;
