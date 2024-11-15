package fr.unice.polytech.interfaces;

import fr.unice.polytech.components.GroupOrderImpl;


public interface DiscountStrategy {
    float applyDiscount(GroupOrderImpl groupOrder);
}


