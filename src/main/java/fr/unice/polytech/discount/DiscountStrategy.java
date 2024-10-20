package fr.unice.polytech.discount;

import fr.unice.polytech.order.GroupOrderImpl;

public interface DiscountStrategy {
    float applyDiscount(GroupOrderImpl groupOrder);
}
