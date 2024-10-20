package fr.unice.polytech.discount;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.Order;

public class ItemCountDiscountStrategy implements DiscountStrategy {
    @Override
    public float applyDiscount(GroupOrderImpl groupOrder) {
        float totalDiscount = 0f;

        for (Order order : groupOrder.getUsersOrders().values()) {
            if (order.getOrderedArticles().size() >= 5) {
                // Appliquer 5% de réduction uniquement à cet utilisateur
                float originalPrice = order.getTotalPrice();
                float discount = originalPrice * 0.05f;
                float newPrice = originalPrice - discount;

                // Mettre à jour le prix de la commande avec la réduction
                order.setTotalPrice(newPrice);

                System.out.println("Un utilisateur a commandé 5 articles ou plus. Réduction de 5% appliquée à sa commande.");

                // Calcul du total de la réduction appliquée (utile pour des statistiques ou d'autres traitements)
                totalDiscount += discount;
            }
        }

        // Retourne la somme des réductions appliquées, même si ce n'est pas nécessaire pour cette logique
        return totalDiscount;
    }
}
