package fr.unice.polytech.discount;

import fr.unice.polytech.order.GroupOrderImpl;

public class GroupSizeDiscountStrategy implements DiscountStrategy {
    @Override
    public float applyDiscount(GroupOrderImpl groupOrder) {
        if (groupOrder.getUserList().size() >= 10) {
            System.out.println("Applying group size discount for 10 or more people.");
            return 0.1f; // 10% discount
        }
        return 0f;
    }
}
