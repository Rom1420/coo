package fr.unice.polytech.entities;

import fr.unice.polytech.components.GroupOrderImpl;
import fr.unice.polytech.interfaces.DiscountStrategy;

public class ItemCountDiscountStrategy implements DiscountStrategy {
    @Override
    public float applyDiscount(GroupOrderImpl groupOrder) {
        float totalDiscount = 0f;

        for (Order order : groupOrder.getUsersOrders().values()) {
            if (order.getOrderedArticles().size() >= 5) {
                float originalPrice = order.getTotalPrice();
                float discount = originalPrice * 0.05f;
                float newPrice = originalPrice - discount;

                order.setTotalPrice(newPrice);

                //System.out.println("**Un utilisateur a commandé 5 articles ou plus. Réduction de 5% appliquée à sa commande**");

                totalDiscount += discount;
            }
        }
        return totalDiscount;
    }
}
