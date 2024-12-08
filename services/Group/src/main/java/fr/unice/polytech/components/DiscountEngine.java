package fr.unice.polytech.components;

import fr.unice.polytech.entities.GroupSizeDiscountStrategy;
import fr.unice.polytech.entities.ItemCountDiscountStrategy;
import fr.unice.polytech.entities.LoyaltyDiscountStrategy;
import fr.unice.polytech.entities.Restaurant;
import fr.unice.polytech.interfaces.DiscountStrategy;


import java.util.Map;

import static fr.unice.polytech.entities.DiscountType.*;


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

    public void chooseStrategy(Restaurant restaurant, Map<Integer, Integer> orderHistory) {
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
