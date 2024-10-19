package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Restaurant;

import java.util.HashMap;

public class GroupOrderManager {

    private HashMap<Integer, GroupOrderImpl> groupOrders;

    private GroupOrderManager() {
        groupOrders = new HashMap<>();
    }

    // Singleton
    private static final GroupOrderManager GROUP_ORDER_MANAGER_INSTANCE = new GroupOrderManager();

    public static GroupOrderManager getGroupOrderManagerInstance() {
        return GROUP_ORDER_MANAGER_INSTANCE;
    }

    // Stocker les objets GroupOrderImpl directement
    public void addGroupOrder(Integer groupOrderId, GroupOrderImpl groupOrder) {
        groupOrders.put(groupOrderId, groupOrder);
    }

    public GroupOrderImpl getGroupOrder(Integer groupOrderId) {
        return groupOrders.get(groupOrderId);
    }

    public void validateGroupOrder(int groupOrderId) {
        GroupOrderImpl groupOrder = groupOrders.get(groupOrderId);

        if (groupOrder != null) {
            try {
                groupOrder.validateOrder();
                notifyRestaurant(groupOrder);
                groupOrder.closeOrder();
                System.out.println("Commande de groupe validée et envoyée en préparation");
            } catch (IllegalStateException e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        } else {
            System.out.println("Erreur: Le groupe de commande n'existe pas");
        }
    }


    private void notifyRestaurant(GroupOrderImpl groupOrder) {
        Restaurant restaurant = groupOrder.getRestaurant();
        System.out.println("Le restaurant " + restaurant.getName() + " a été notifié pour commencer la préparation");
    }

    public void removeOrder(Integer groupOrderId) {
        groupOrders.remove(groupOrderId);
    }
}

