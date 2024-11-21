import './menu-item.css';

function MenuItem({image, title, smallText}) {
  return (
    <div className="menu-item-container">
        <img src={image} className='menu-item-pic' alt={title} />
        <h3 className='menu-item-title'>{title}</h3>
        <p className='menu-item-small-text'>{smallText}</p>
    </div>
  );
}

export default MenuItem;
