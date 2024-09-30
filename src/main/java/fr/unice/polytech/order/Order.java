package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private List<Article> articles;

    public Order(int orderId){
        this.orderId = orderId;
        this.articles = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public List<Article> getAvailableArticles() {
        // TODO : Récupérer la liste des articles disponible pour le restaurant
        return articles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void addArticle(Article article){
        if(.contains(article)){
            this.articles.add(article);
        }
        else{
            throw new IllegalArgumentException("L'article : '"+article+"' n'est pas disponible !");
        }
    }
}
