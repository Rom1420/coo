package fr.unice.polytech.server.microservices;

import fr.unice.polytech.db.GroupOrderManager;
import fr.unice.polytech.components.GroupOrderProxy;

public class ValidateGroup {

    public static boolean validateGroup(int groupId) {
        GroupOrderProxy groupOrder = GroupOrderManager.getGroupOrderManagerInstance().getGroupOrders().get(groupId);

        if (groupOrder == null) {
            System.out.println("Group " + groupId + " not found");
            return false;
        }

        try {
            System.out.println("Validating group: " + groupId);
            groupOrder.validateOrder();
            groupOrder.applyDiscount();
            groupOrder.closeOrder();
            return true;
        } catch (IllegalStateException e) {
            System.out.println("Error during validation: " + e.getMessage());
            return false;
        }
    }
}
