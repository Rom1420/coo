package fr.unice.polytech.discount;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class GroupSizeDiscountStrategyTest {

    private GroupOrderImpl groupOrder;
    private Restaurant restaurant;
    private Article article1;
    private Date date;

    @Before
    public void setUp() {
        restaurant = new Restaurant("Test Restaurant");
        article1 = new Article("Pizza", 12.0f, 10);
        restaurant.addArticle(article1);
        date = new Date(1767225600000L);

        groupOrder = new GroupOrderImpl(1, restaurant, date, "Delivery Location");

        // Ajouter 9 utilisateurs pour un test initial
        for (int i = 1; i <= 9; i++) {
            Order newOrder = new Order(new Date(), date,"Delivery Location", restaurant);
            newOrder.addArticle(article1);
            groupOrder.addOrUpdateUserOrder(new RegisteredUser("User" + i, i, "password"), newOrder);
        }

        System.out.println("Setup: Ajout de 9 utilisateurs au groupe.");
    }

    @Test
    public void testDiscountWithLessThan10Users() {
        System.out.println("Test: Réduction avec moins de 10 utilisateurs.");
        System.out.println("Nombre d'utilisateurs dans le groupe: " + groupOrder.getUserList().size());

        // Tester avec 9 utilisateurs (pas de réduction)
        GroupSizeDiscountStrategy discountStrategy = new GroupSizeDiscountStrategy();
        float discount = discountStrategy.applyDiscount(groupOrder);

        System.out.println("Réduction appliquée: " + discount * 100 + "%");

        assertEquals(0f, discount, 0.01);
        System.out.println("Test réussi: Aucune réduction appliquée avec moins de 10 utilisateurs.\n");
    }

    @Test
    public void testDiscountWith10OrMoreUsers() {
        System.out.println("Test: Réduction avec exactement 10 utilisateurs.");

        // Ajouter un utilisateur pour atteindre 10
        Order newOrder = new Order(new Date(), date,"Delivery Location", restaurant);
        newOrder.addArticle(article1);
        groupOrder.addOrUpdateUserOrder(new RegisteredUser("User10", 10, "password"), newOrder);

        System.out.println("Nombre d'utilisateurs dans le groupe après ajout: " + groupOrder.getUserList().size());

        GroupSizeDiscountStrategy discountStrategy = new GroupSizeDiscountStrategy();
        float discount = discountStrategy.applyDiscount(groupOrder);

        System.out.println("Réduction appliquée: " + discount * 100 + "%");

        assertEquals(0.1f, discount, 0.01);
        System.out.println("Test réussi: Réduction de 10% appliquée avec 10 utilisateurs.\n");
    }

    @Test
    public void testDiscountWithMoreThan10Users() {
        System.out.println("Test: Réduction avec plus de 10 utilisateurs.");

        // Ajouter deux utilisateurs pour atteindre 11
        for (int i = 11; i <= 12; i++) {
            Order newOrder = new Order(new Date(), date, "Delivery Location", restaurant);
            newOrder.addArticle(article1);
            groupOrder.addOrUpdateUserOrder(new RegisteredUser("User" + i, i, "password"), newOrder);
        }

        System.out.println("Nombre d'utilisateurs dans le groupe après ajout: " + groupOrder.getUserList().size());

        GroupSizeDiscountStrategy discountStrategy = new GroupSizeDiscountStrategy();
        float discount = discountStrategy.applyDiscount(groupOrder);

        System.out.println("Réduction appliquée: " + discount * 100 + "%");

        assertEquals(0.1f, discount, 0.01);
        System.out.println("Test réussi: Réduction de 10% appliquée avec plus de 10 utilisateurs.\n");
    }
}
