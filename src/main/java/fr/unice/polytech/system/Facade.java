package fr.unice.polytech.system;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.GroupOrderManager;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderManager;
import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Menu;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;

import java.util.ArrayList;
import java.util.Date;

public class Facade {

    //private static int orderId = 0; Pour OrderManager


    private OrderManager orderManager = OrderManager.getOrderManagerInstance();
    private GroupOrderManager groupOrderManager = GroupOrderManager.getGroupOrderManagerInstance();
    private RestaurantManager restaurantManager = RestaurantManager.getRestaurantManagerInstance();

    public void consultRestaurants () {
        restaurantManager.consultRestaurant();
    }

    public void placeOrder (){
        return;
    }

    public void payOrder () {
        return;
    }

    public void updateService () {
        return;
    }

    public void createGroup (Boolean validate, Integer creatorId, Restaurant restaurant, String deliveryLocation, Date deliveryDate, ArrayList<Article> articles, ArrayList<Menu> menus) {
        groupOrderManager.createGroup(validate, creatorId, restaurant, deliveryLocation, deliveryDate, articles, menus);
    }

    public void joinGroup(Boolean validate, Integer joinerId, Integer groupOrderId, ArrayList<Article> articles, ArrayList<Menu> menus) {
        groupOrderManager.joinGroup(validate, joinerId, groupOrderId, articles, menus);
    }

    public static void main (String args[]) {
        return;
    }

}
