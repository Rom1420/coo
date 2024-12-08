import './rating.css';

function Rating({ rating = 4.5, maxStars = 5, color = '#EE5A30', size = '1.2rem' }) {
  const starStyle = {
    fontSize: size,
    color: color,
  };
  
  const stars = [];
    for (let i = 1; i <= maxStars; i++) {
      if (i <= Math.floor(rating)) {
        stars.push(<i key={i} className="fa fa-star" style={starStyle}></i>); 
      } else if (i === Math.ceil(rating) && rating % 1 !== 0) {
        stars.push(<i key={i} className="fa fa-star-half-alt" style={starStyle}></i>); 
      } else {
        stars.push(<i key={i} className="fa fa-star-o" style={starStyle}></i>); 
      }
    }

  return <div className="stars-container">{stars}</div>;
}

export default Rating;
