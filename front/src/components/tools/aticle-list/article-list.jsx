import './article-list.css';
import Article from '../article/article';

function ArticleList({cart, removeArticleFromCart}) {

    return (
        <div className="article-list-container">
            {cart.map((item, index) => (
                <Article key={index} name={item.name} price={item.price} quantity={item.quantity} onRemove={() => removeArticleFromCart(index)}/>
            ))}
        </div>
    );
}

export default ArticleList;