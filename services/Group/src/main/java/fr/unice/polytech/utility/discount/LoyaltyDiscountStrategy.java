package fr.unice.polytech.utility.discount;

import fr.unice.polytech.utility.order.GroupOrderImpl;
import fr.unice.polytech.utility.order.Order;
import fr.unice.polytech.utility.user.RegisteredUser;

import java.util.Map;

public class LoyaltyDiscountStrategy implements DiscountStrategy {
    private Map<Integer, Integer> orderHistory;

    public LoyaltyDiscountStrategy(Map<Integer, Integer> orderHistory) {
        this.orderHistory = orderHistory;
    }

    @Override
    public float applyDiscount(GroupOrderImpl groupOrder) {
        float totalDiscount = 0f;

        // Appliquer la réduction en fonction de la fidélité de chaque utilisateur
        for (Map.Entry<Integer, Order> entry : groupOrder.getUsersOrders().entrySet()) {
            Integer userId = entry.getKey();
            Order order = entry.getValue();

            int ordersInRestaurant = orderHistory.getOrDefault(userId, 0);

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
}
