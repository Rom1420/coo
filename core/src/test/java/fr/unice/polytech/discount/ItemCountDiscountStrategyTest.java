package fr.unice.polytech.discount;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ItemCountDiscountStrategyTest {

    private GroupOrderImpl groupOrder;
    private Restaurant restaurant;
    private Order order1, order2, order3, order4;
    private Article article1, article2, article3;
    private Date date;

    @Before
    public void setUp() {
        restaurant = new Restaurant("Test Restaurant");
        date = new Date(1767225600000L);

        article1 = new Article("Pizza", 12.0f, 10);
        article2 = new Article("Pasta", 15.0f, 8);
        article3 = new Article("Salad", 8.0f, 5);
        restaurant.addArticle(article1);
        restaurant.addArticle(article2);
        restaurant.addArticle(article3);

        groupOrder = new GroupOrderImpl(1, restaurant, date, "Delivery Location");

        order1 = new Order(new Date(), date, "Delivery Location", restaurant);
        order2 = new Order(new Date(), date, "Delivery Location", restaurant);
        order3 = new Order(new Date(), date, "Delivery Location", restaurant);
        order4 = new Order(new Date(), date, "Delivery Location", restaurant);

        order1.addArticle(article1);  // 1 article
        order1.addArticle(article2);  // 2 articles
        order1.addArticle(article3);  // 3 articles
        order1.addArticle(article1);  // 4 articles
        order1.addArticle(article2);  // 5 articles

        order2.addArticle(article3);  // 1 article
        order2.addArticle(article1);  // 2 articles
        order2.addArticle(article2);  // 3 articles

        order3.addArticle(article1);  // 1 article

        order4.addArticle(article1);  // 1 article
        order4.addArticle(article2);  // 2 articles
        order4.addArticle(article3);  // 3 articles
        order4.addArticle(article1);  // 4 articles
        order4.addArticle(article2);  // 5 articles

        groupOrder.addOrUpdateUserOrder(new RegisteredUser("User1", 1, "password"), order1);
        groupOrder.addOrUpdateUserOrder(new RegisteredUser("User2", 2, "password"), order2);
        groupOrder.addOrUpdateUserOrder(new RegisteredUser("User3", 3, "password"), order3);
        groupOrder.addOrUpdateUserOrder(new RegisteredUser("User4", 4, "password"), order4);
    }

    @Test
    public void testItemCountDiscountStrategy() {
        ItemCountDiscountStrategy discountStrategy = new ItemCountDiscountStrategy();

        float originalPriceOrder1 = order1.getTotalPrice();
        float originalPriceOrder2 = order2.getTotalPrice();
        float originalPriceOrder3 = order3.getTotalPrice();
        float originalPriceOrder4 = order4.getTotalPrice();

        System.out.println("Avant réduction :");
        System.out.println("Prix Order1 : " + originalPriceOrder1);
        System.out.println("Prix Order2 : " + originalPriceOrder2);
        System.out.println("Prix Order3 : " + originalPriceOrder3);
        System.out.println("Prix Order4 : " + originalPriceOrder4);

        float totalDiscount = discountStrategy.applyDiscount(groupOrder);

        System.out.println("Après réduction :");
        System.out.println("Prix Order1 (après réduction) : " + order1.getTotalPrice());
        System.out.println("Prix Order2 (après réduction) : " + order2.getTotalPrice());
        System.out.println("Prix Order3 (après réduction) : " + order3.getTotalPrice());
        System.out.println("Prix Order4 (après réduction) : " + order4.getTotalPrice());

        assertEquals(originalPriceOrder1 * 0.95f, order1.getTotalPrice(), 0.01);
        assertEquals(originalPriceOrder4 * 0.95f, order4.getTotalPrice(), 0.01);

        assertEquals(originalPriceOrder2, order2.getTotalPrice(), 0.01);
        assertEquals(originalPriceOrder3, order3.getTotalPrice(), 0.01);

        System.out.println("Total des réductions appliquées : " + totalDiscount);
        assertEquals((originalPriceOrder1 * 0.05f) + (originalPriceOrder4 * 0.05f), totalDiscount, 0.01);
    }
}
