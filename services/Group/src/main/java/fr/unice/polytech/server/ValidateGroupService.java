package fr.unice.polytech.server;



public class ValidateGroupService {

   /* private final HashMap<Integer, GroupOrderProxy> groupOrders = GroupOrderService.getGroupOrderServiceInstance().getGroupOrders();

    public void validateGroupOrder(int groupOrderId) {
        GroupOrderProxy groupOrder = groupOrders.get(groupOrderId);
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
    }*/
}
