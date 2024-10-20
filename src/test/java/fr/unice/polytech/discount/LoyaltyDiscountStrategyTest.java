package fr.unice.polytech.discount;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class LoyaltyDiscountStrategyTest {

    private GroupOrderImpl groupOrder;
    private Restaurant restaurant;
    private Order order1, order2, order3;
    private RegisteredUser user1, user2, user3;
    private Article article1, article2, article3;

    private Map<RegisteredUser, Integer> orderHistory;

    @Before
    public void setUp() {
        restaurant = new Restaurant("Test Restaurant");

        // Création d'articles pour les tests
        article1 = new Article("Pizza", 12.0f, 10);
        article2 = new Article("Pasta", 15.0f, 8);
        article3 = new Article("Salad", 8.0f, 5);
        restaurant.addArticle(article1);
        restaurant.addArticle(article2);
        restaurant.addArticle(article3);

        // Création du GroupOrder
        groupOrder = new GroupOrderImpl(1, restaurant, new Date(), "Delivery Location");

        // Création des utilisateurs et des commandes
        user1 = new RegisteredUser("User1", 1, "password");
        user2 = new RegisteredUser("User2", 2, "password");
        user3 = new RegisteredUser("User3", 3, "password");

        order1 = new Order(new Date(), "Location 1", restaurant);
        order2 = new Order(new Date(), "Location 2", restaurant);
        order3 = new Order(new Date(), "Location 3", restaurant);

        // Ajouter des articles aux commandes
        order1.addArticle(article1);
        order2.addArticle(article2);
        order3.addArticle(article3);

        // Ajouter les commandes au GroupOrder
        groupOrder.addOrUpdateUserOrder(user1, order1);
        groupOrder.addOrUpdateUserOrder(user2, order2);
        groupOrder.addOrUpdateUserOrder(user3, order3);

        // Création de l'historique des commandes
        orderHistory = new HashMap<>();
        orderHistory.put(user1, 6); // User1 a passé 6 commandes (donc éligible à la réduction)
        orderHistory.put(user2, 4); // User2 a passé 4 commandes (pas de réduction)
        orderHistory.put(user3, 5); // User3 a passé 5 commandes (éligible à la réduction)
    }

    @Test
    public void testLoyaltyDiscountStrategy() {
        // Appliquer la stratégie de fidélité
        LoyaltyDiscountStrategy discountStrategy = new LoyaltyDiscountStrategy(orderHistory);
        float totalDiscount = discountStrategy.applyDiscount(groupOrder);

        // On vérifie que User1 et User3 ont reçu une réduction de 10% chacun
        float expectedDiscountUser1 = 12.0f * 0.10f;
        float expectedDiscountUser3 = 8.0f * 0.10f;
        float totalExpectedDiscount = expectedDiscountUser1 + expectedDiscountUser3;

        // Utiliser une tolérance plus large pour la comparaison
        float tolerance = 0.1f;

        // Vérifier que le total des réductions est correct
        assertEquals(totalExpectedDiscount, totalDiscount, tolerance);

        // Vérifier que les prix des commandes ont été correctement mis à jour
        assertEquals(12.0f - expectedDiscountUser1, order1.getTotalPrice(), tolerance);
        assertEquals(15.0f, order2.getTotalPrice(), tolerance); // Pas de réduction pour user2
        assertEquals(8.0f - expectedDiscountUser3, order3.getTotalPrice(), tolerance);

        // Imprimer des informations plus parlantes
        System.out.println("=== Détails des réductions de fidélité appliquées ===");
        System.out.println("Utilisateur 1 (" + user1.getName() + ") : " + "Prix original = 12.0€, Réduction de 10% appliquée, Prix après réduction = " + order1.getTotalPrice() + "€");
        System.out.println("Utilisateur 2 (" + user2.getName() + ") : " + "Prix original = 15.0€, Pas de réduction, Prix final = " + order2.getTotalPrice() + "€");
        System.out.println("Utilisateur 3 (" + user3.getName() + ") : " + "Prix original = 8.0€, Réduction de 10% appliquée, Prix après réduction = " + order3.getTotalPrice() + "€");
        System.out.println("Réduction totale appliquée sur le groupe : " + totalDiscount + "€");
    }
}

