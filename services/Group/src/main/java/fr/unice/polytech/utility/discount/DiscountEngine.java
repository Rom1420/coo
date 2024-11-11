package fr.unice.polytech.utility.discount;

import fr.unice.polytech.utility.order.GroupOrderImpl;
import fr.unice.polytech.utility.restaurant.Restaurant;
import fr.unice.polytech.utility.user.RegisteredUser;

import java.util.Map;

import static fr.unice.polytech.utility.discount.DiscountType.*;

//Le moteur de réduction est le cœur qui choisit et applique la stratégie :

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

    /*chooseStrategy() sélectionne la stratégie en fonction du type de réduction du restaurant.
       Une fois que la stratégie est définie avec setStrategy(), elle est appliquée à la commande groupée
       via applyDiscount().*/
}
