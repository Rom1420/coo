package fr.unice.polytech.utility.order;

import fr.unice.polytech.utility.restaurant.RestaurantManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private List<String> orderedArticles; // Liste d'articles commandés
    private List<String> orderedMenus; // Liste de menus commandés

    private float totalPrice; // Prix total de la commande

    private int totalPreparationTime; // Temps de préparation total en minutes
    private Date orderDate; // Date de commande
    private Date deliveryDate; // Date de livraison prévue

    private Date estimatedDeliveryDate;

    private String deliveryLocation; // Lieu de livraison

    private String status; // État de la commande
    private String restaurant;

    public Order(Date orderDate, Date deliveryDate, String deliveryLocation, String restaurant) {
        this.orderedArticles = new ArrayList<>();
        this.orderedMenus = new ArrayList<>();
        this.totalPrice = 0;
        this.totalPreparationTime = 0;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.estimatedDeliveryDate = orderDate;
        this.deliveryLocation = deliveryLocation;
        this.restaurant = restaurant;
        this.status = "en attente";
    }
    public Order(Date orderDate, String deliveryLocation, String restaurant) {
        this.orderedArticles = new ArrayList<>();
        this.orderedMenus = new ArrayList<>();
        this.totalPrice = 0;
        this.totalPreparationTime = 0;
        this.orderDate = orderDate;
        this.deliveryDate = null;
        this.deliveryLocation = deliveryLocation;
        this.restaurant = restaurant;
        this.status = "en attente";

    }


    public List<String> getOrderedArticles() {
        return orderedArticles;
    }

    public List<String> getOrderedMenus() {
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

    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public String getRestaurant() {
        return restaurant;
    }
    public void setTotalPrice(float totalPrice) {this.totalPrice = totalPrice;}

    public void addArticle(String article){
        if (!RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getArticlesSimplesNames().contains(article)) {
            throw new RuntimeException("Impossible d'ajouter cette article, il n'appartient pas au restaurant de la commande.");
        }
        if (deliveryDate == null) {
            // Aucune date de livraison choisie, on met à jour la prévision
            updateEstimatedDeliveryDate(RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getArticleByName(article).getTimeRequiredForPreparation()); // Minutes
        } else {
            // Vérifier si le menu peut être ajouté sans dépasser la date de livraison
            if (!canAddArticleOrMenu(RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getArticleByName(article).getTimeRequiredForPreparation())) {
                throw new RuntimeException("Impossible d'ajouter cette article, cela dépasserait la date de livraison.");
            }
        }
        orderedArticles.add(article);
        totalPrice += RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getArticleByName(article).getPrice();
        totalPreparationTime += RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getArticleByName(article).getTimeRequiredForPreparation();
    }

    public void addMenu(String menu){
        if (!RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getMenusOfRestaurantNames().contains(menu)) {
            throw new RuntimeException("Impossible d'ajouter ce menu, il n'appartient pas au restaurant de la commande.");
        }
        if (deliveryDate == null) {
            // Aucune date de livraison choisie, on met à jour la prévision
            updateEstimatedDeliveryDate(RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getMenuByName(menu).getTotalTimeRequiredForPreparation());
        } else {
            // Vérifier si le menu peut être ajouté sans dépasser la date de livraison
            if (!canAddArticleOrMenu(RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getMenuByName(menu).getTotalTimeRequiredForPreparation())) {
                throw new RuntimeException("Impossible d'ajouter ce menu, cela dépasserait la date de livraison.");
            }
        }
        orderedMenus.add(menu);
        totalPrice += RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getMenuByName(menu).getPrice();
        totalPreparationTime += RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getMenuByName(menu).getTotalTimeRequiredForPreparation();
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

    public void updateEstimatedDeliveryDate(long estimatedDeliveryDate) {
        Date newDate = new Date(estimatedDeliveryDate);
        this.estimatedDeliveryDate = newDate;
    }

    public void setOrderArticlesAndMenus(ArrayList<String> articles, ArrayList<String> menus) {
        for (String article : articles) addArticle(article);
        for (String menu : menus) addMenu(menu);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Details:\n");
        sb.append("Restaurant: ").append(restaurant).append("\n");
        sb.append("Order Date: ").append(orderDate).append("\n");
        sb.append("Delivery Date: ").append(deliveryDate != null ? deliveryDate : "Not specified").append("\n");
        sb.append("Delivery Location: ").append(deliveryLocation).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Total Price: ").append(String.format("%.2f", totalPrice)).append(" €\n");
        sb.append("Total Preparation Time: ").append(totalPreparationTime).append(" minutes\n");
        sb.append("Estimated Delivery Date: ").append(estimatedDeliveryDate).append("\n");

        sb.append("Ordered Articles:\n");
        for (String article : orderedArticles) {
            sb.append("- ").append(article).append(": ").append(String.format("%.2f", RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getArticleByName(article).getPrice())).append(" € (Preparation time: ").append(RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getArticleByName(article).getTimeRequiredForPreparation()).append(" minutes)\n");
        }

        sb.append("Ordered Menus:\n");
        for (String menu : orderedMenus) {
            sb.append("- ").append(menu).append(": ").append(String.format("%.2f", RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getMenuByName(menu).getPrice())).append(" € (Total preparation time: ").append(RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getMenuByName(menu).getTotalTimeRequiredForPreparation()).append(" minutes)\n");
        }

        return sb.toString();
    }

}
