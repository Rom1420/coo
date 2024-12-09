import './article.css';

function Article({ name, price, quantity, onRemove }) {
    const handleRemoveClick = () => {
    onRemove();
  };
    return (
        <div className="article">
            <div className="article-and-quantity">
                <h3 className='article-name'>{name}</h3>
                <p>x {quantity}</p>   
            </div>
                <div className="price-and-delete" >
                    <p className='article-price'>{price}</p>
                    <i className="fa-solid fa-trash" onClick={handleRemoveClick}></i>
                </div>
        </div>
    );
}

export default Article;