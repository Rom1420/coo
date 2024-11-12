package fr.unice.polytech.validate;

import fr.unice.polytech.GroupOrderService;
import fr.unice.polytech.utility.order.GroupOrderImpl;

import java.util.HashMap;

public class ValidateGroupService {

    private final HashMap<Integer, GroupOrderImpl> groupOrders = GroupOrderService.getGroupOrderServiceInstance().getGroupOrders();

    public void validateGroupOrder(int groupOrderId) {
        GroupOrderImpl groupOrder = groupOrders.get(groupOrderId);

        if (groupOrder != null) {
            try {
                groupOrder.validateOrder();
                groupOrder.applyDiscount();
                groupOrder.closeOrder();
                System.out.println("Commande de groupe validée, fermée et envoyée en préparation");
            } catch (IllegalStateException e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        } else {
            System.out.println("Erreur: Le groupe de commande n'existe pas");
        }
    }
}
