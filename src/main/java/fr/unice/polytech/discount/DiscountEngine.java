package fr.unice.polytech.discount;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;

import java.util.Map;

import static fr.unice.polytech.discount.DiscountType.*;

public class DiscountEngine {
    private DiscountStrategy strategy;
    private GroupSizeDiscountStrategy groupSizeDiscountStrategy = new GroupSizeDiscountStrategy();
    private ItemCountDiscountStrategy itemCountDiscountStrategy = new ItemCountDiscountStrategy();

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public float applyDiscount(GroupOrderImpl groupOrder) {
        if (strategy == null) {
            throw new IllegalStateException("Discount strategy is not set");
        }
        return strategy.applyDiscount(groupOrder);
    }

    public void chooseStrategy(Restaurant restaurant, Map<RegisteredUser, Integer> orderHistory) {
        switch (restaurant.getDiscountType()) {
            case GROUP_SIZE -> setStrategy(groupSizeDiscountStrategy);
            case ITEM_COUNT -> setStrategy(itemCountDiscountStrategy);
            case LOYALTY -> setStrategy(new LoyaltyDiscountStrategy(orderHistory));
            default -> setStrategy(null);
        }
    }
}
