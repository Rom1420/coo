package fr.unice.polytech.entities;

import fr.unice.polytech.components.GroupOrderImpl;
import fr.unice.polytech.interfaces.DiscountStrategy;

import java.util.Map;

public class LoyaltyDiscountStrategy implements DiscountStrategy {
    private Map<Integer, Integer> orderHistory;

    public LoyaltyDiscountStrategy(Map<Integer, Integer> orderHistory) {
        this.orderHistory = orderHistory;
    }

    /*@Override
    public float applyDiscount(GroupOrderImpl groupOrder) {
        float totalDiscount = 0f;

        // Appliquer la réduction en fonction de la fidélité de chaque utilisateur
        for (Map.Entry<Integer, Order> entry : groupOrder.getUsersOrders().entrySet()) {
            Integer user = entry.getKey();
            Order order = entry.getValue();

            int ordersInRestaurant = orderHistory.getOrDefault(user, 0);

            if (ordersInRestaurant % 5 == 0) {
                float originalPrice = order.getTotalPrice();
                float discount = originalPrice * 0.10f; // 10%
                float newPrice = originalPrice - discount;

                order.setTotalPrice(newPrice);
                //System.out.println(user.getName() + " bénéficie de 10% de réduction grâce à sa fidélité.");

                totalDiscount += discount;
            }
        }

        return totalDiscount;
    }
*/
    public float applyDiscount(GroupOrderImpl groupOrder) {
        float totalDiscount = 0f;
        return totalDiscount;
    }
}
