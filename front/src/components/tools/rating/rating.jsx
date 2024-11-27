import './rating.css';

function Rating({ color = '#EE5A30', size = '1.2rem' }) { // Défaut : couleur or, taille 1.2rem
  const starStyle = {
    fontSize: size,
    color: color,
  };

  return (
    <div className="stars-container">
      <i className="fa fa-star" style={starStyle}></i>
      <i className="fa fa-star" style={starStyle}></i>
      <i className="fa fa-star" style={starStyle}></i>
      <i className="fa fa-star" style={starStyle}></i>
      <i className="fa fa-star-half-alt" style={starStyle}></i>
    </div>
  );
}

export default Rating;
