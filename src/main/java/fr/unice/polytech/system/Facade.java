package fr.unice.polytech.system;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.GroupOrderManager;
import fr.unice.polytech.order.OrderManager;
import fr.unice.polytech.restaurant.Restaurant;
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

    public void validateGroupOrder(int groupOrderId) {
        groupOrderManager.validateGroupOrder(groupOrderId);
    }


    public static void main (String args[]) {
        return;
    }

}
