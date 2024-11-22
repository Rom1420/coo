import './menu-item.css';

function MenuItem({image, title, smallText, onClick}) {
  return (
    <div className="menu-item-container" onClick={onClick}>
        <img src={image} className='menu-item-pic' alt={title} />
        <h3 className='menu-item-title'>{title}</h3>
        <p className='menu-item-small-text'>{smallText}</p>
        <div className="details-arrow">
          <i className="fa-solid fa-arrow-right"></i>
        </div>
    </div>
  );
}

export default MenuItem;
