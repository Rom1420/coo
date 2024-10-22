package fr.unice.polytech.discount;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.user.RegisteredUser;

import java.util.Map;

public class LoyaltyDiscountStrategy implements DiscountStrategy {
    private Map<RegisteredUser, Integer> orderHistory;

    public LoyaltyDiscountStrategy(Map<RegisteredUser, Integer> orderHistory) {
        this.orderHistory = orderHistory;
    }

    @Override
    public float applyDiscount(GroupOrderImpl groupOrder) {
        float totalDiscount = 0f;

        // Appliquer la réduction en fonction de la fidélité de chaque utilisateur
        for (Map.Entry<RegisteredUser, Order> entry : groupOrder.getUsersOrders().entrySet()) {
            RegisteredUser user = entry.getKey();
            Order order = entry.getValue();

            int ordersThisMonth = orderHistory.getOrDefault(user, 0);

            if (ordersThisMonth >= 5) {
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
}
