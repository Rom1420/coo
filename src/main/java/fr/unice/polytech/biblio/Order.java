package fr.unice.polytech.biblio;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private List<String> articles; // TODO : List<Article> articles

    public Order(int orderId){
        this.orderId = orderId;
        this.articles = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public List<String> getArticles() {
        return articles;
    }

    public List<String> getAvailableArticles() {
        // TODO : Récupérer la liste des articles disponible pour le restaurant
        return articles;
    }

    public void addArticle(String article){
        if(getAvailableArticles().contains(article)){
            this.articles.add(article);
        }
        else{
            throw new IllegalArgumentException("L'article : '"+article+"' n'est pas disponible !");
        }
    }
}
