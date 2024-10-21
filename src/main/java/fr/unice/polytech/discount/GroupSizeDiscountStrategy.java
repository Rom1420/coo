package fr.unice.polytech.discount;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.Order;

public class GroupSizeDiscountStrategy implements DiscountStrategy {
    @Override
    public float applyDiscount(GroupOrderImpl groupOrder) {
        if (groupOrder.getUserList().size() >= 10) {
            for (Order order : groupOrder.getUsersOrders().values()) {
                float originalPrice = order.getTotalPrice();
                float discount = originalPrice * 0.1f;
                float newPrice = originalPrice - discount;
                order.setTotalPrice(newPrice);
            }
            System.out.println("Applying group size discount for 10 or more people.");
            return 0.1f;
        }
        return 0f;
    }
}