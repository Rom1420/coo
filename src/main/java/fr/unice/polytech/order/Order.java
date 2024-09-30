package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Menu;
import fr.unice.polytech.restaurant.Restaurant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private List<Article> orderedArticles; // Liste d'articles commandés
    private List<Menu> orderedMenus; // Liste de menus commandés
    private float totalPrice; // Prix total de la commande
    private int totalPreparationTime; // Temps de préparation total en minutes
    private Date orderDate; // Date de commande
    private Date deliveryDate; // Date de livraison prévue
    private String deliveryLocation; // Lieu de livraison
    private String status; // État de la commande
    private Restaurant restaurant;

    public Order(Date orderDate, Date deliveryDate, String deliveryLocation) {
        this.orderedArticles = new ArrayList<>();
        this.orderedMenus = new ArrayList<>();
        this.totalPrice = 0;
        this.totalPreparationTime = 0;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.deliveryLocation = deliveryLocation;
        this.status = "en attente";
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }


    public List<Article> getAvailableArticles() {
        // TODO : Récupérer la liste des articles disponible pour le restaurant
        return orderedArticles;
    }

    public List<Article> getOrderedArticles() {
        return orderedArticles;
    }

    public void addArticle(Article article){
        orderedArticles.add(article);
        totalPrice += article.getPrice();
        totalPreparationTime += article.getTimeRequiredForPreparation();
    }

    public void addMenu(Menu menu){
        orderedMenus.add(menu);
        totalPrice += menu.getPrice();
        totalPreparationTime += menu.getTotalTimeRequiredForPreparation();
    }
}
