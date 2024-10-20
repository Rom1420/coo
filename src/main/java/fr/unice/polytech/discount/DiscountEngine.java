package fr.unice.polytech.discount;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;

import java.util.Map;

import static fr.unice.polytech.discount.DiscountType.*;

public class DiscountEngine {
    private DiscountStrategy strategy;

    // Setter de la stratégie
    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    // Méthode pour appliquer le discount à un GroupOrder
    public float applyDiscount(GroupOrderImpl groupOrder) {
        if (strategy == null) {
            throw new IllegalStateException("Discount strategy is not set.");
        }
        return strategy.applyDiscount(groupOrder);
    }

    // Exemple de choix de stratégie basé sur le restaurant
    public void chooseStrategy(Restaurant restaurant, Map<RegisteredUser, Integer> orderHistory) {
        switch (restaurant.getDiscountType()) {
            case GROUP_SIZE:
                setStrategy(new GroupSizeDiscountStrategy());
                break;
            case ITEM_COUNT:
                setStrategy(new ItemCountDiscountStrategy());
                break;
            case LOYALTY:
                setStrategy(new LoyaltyDiscountStrategy(orderHistory));
                break;
            default:
                setStrategy(null); // Aucune stratégie
                break;
        }
    }
}
