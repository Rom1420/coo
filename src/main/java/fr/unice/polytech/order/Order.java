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
    private Date estimatedDeliveryDate;
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
        this.estimatedDeliveryDate = deliveryDate;
        this.deliveryLocation = deliveryLocation;
        this.status = "en attente";
    }

    public Order(Date orderDate,  String deliveryLocation) {
        this.orderedArticles = new ArrayList<>();
        this.orderedMenus = new ArrayList<>();
        this.totalPrice = 0;
        this.totalPreparationTime = 0;
        this.orderDate = orderDate;
        this.deliveryDate = null;
        this.deliveryLocation = deliveryLocation;
        this.status = "en attente";

    }
    public List<Article> getOrderedArticles() {
        return orderedArticles;
    }

    public List<Menu> getOrderedMenus() {
        return orderedMenus;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public int getTotalPreparationTime() {
        return totalPreparationTime;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public String getStatus() {
        return status;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void addArticle(Article article){
        if (deliveryDate == null) {
            // Aucune date de livraison choisie, on met à jour la prévision
            updateEstimatedDeliveryDate(article.getTimeRequiredForPreparation()); // Minutes
        } else {
            // Vérifier si le menu peut être ajouté sans dépasser la date de livraison
            if (!canAddArticleOrMenu(article.getTimeRequiredForPreparation())) {
                throw new RuntimeException("Impossible d'ajouter ce menu, cela dépasserait la date de livraison.");
            }
        }
        orderedArticles.add(article);
        totalPrice += article.getPrice();
        totalPreparationTime += article.getTimeRequiredForPreparation();
    }

    public void addMenu(Menu menu){
        if (deliveryDate == null) {
            // Aucune date de livraison choisie, on met à jour la prévision
            updateEstimatedDeliveryDate(menu.getTotalTimeRequiredForPreparation());
        } else {
            // Vérifier si le menu peut être ajouté sans dépasser la date de livraison
            if (!canAddArticleOrMenu(menu.getTotalTimeRequiredForPreparation())) {
                throw new RuntimeException("Impossible d'ajouter ce menu, cela dépasserait la date de livraison.");
            }
        }
        orderedMenus.add(menu);
        totalPrice += menu.getPrice();
        totalPreparationTime += menu.getTotalTimeRequiredForPreparation();
    }

    private void updateEstimatedDeliveryDate(int timeRequiredForPreparation) {
        if (estimatedDeliveryDate == null) {
            estimatedDeliveryDate = new Date(System.currentTimeMillis()); // Milliseconds
        }

        long newEstimatedTime = estimatedDeliveryDate.getTime() + ((long) timeRequiredForPreparation * 60 * 1000); // Milliseconds
        estimatedDeliveryDate.setTime(newEstimatedTime);
    }

    private boolean canAddArticleOrMenu(int timeRequiredForPreparation) {
        long currentTime = System.currentTimeMillis();
        long deliveryTime = deliveryDate.getTime();
        long totalPreparationTimeWithNewItem = ((long) totalPreparationTime * 60 * 1000) + ((long) timeRequiredForPreparation * 60 * 1000); // Milliseconds
        return (currentTime + (totalPreparationTimeWithNewItem)) <= deliveryTime;
    }
}
