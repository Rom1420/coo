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
                groupOrder.validateOrder(); // La commande passe à "validated"
                groupOrder.closeOrder();  // Assure que la commande est fermée après notification
                System.out.println("Commande de groupe validée, fermée et envoyée en préparation");
            } catch (IllegalStateException e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        } else {
            System.out.println("Erreur: Le groupe de commande n'existe pas");
        }
    }

    public void closeGroupOrder(GroupOrderImpl groupOrder) {
        groupOrder.setStatus("closed");
    }


    public void removeOrder(Integer groupOrderId) {
        groupOrders.remove(groupOrderId);
    }
}

