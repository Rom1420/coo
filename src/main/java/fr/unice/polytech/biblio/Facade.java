package fr.unice.polytech.biblio;

import fr.unice.polytech.order.GroupOrderManager;
import fr.unice.polytech.order.OrderManager;
import fr.unice.polytech.restaurant.RestaurantManager;

public class Facade {

    private static int orderId = 0;
    private static int groupOrderId = 0;

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

    public void createGroup () {
        return;
    }

    public void joinGroup() {
        return;
    }

    public static void main (String args[]) {
        return;
    }

}
