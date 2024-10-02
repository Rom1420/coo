package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Menu;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroupOrderProxyTest {
    private GroupOrderImpl groupOrder;
    private GroupOrderProxy groupOrderProxy;
    private Restaurant restaurant;
    private RegisteredUser user;
    private Order order;
    private Article burger;
    private Article fries;
    private Menu classicMenu;

    @BeforeEach
    void setUp() {
        // Initialize articles
        burger = new Article("Burger", 8.50f, 10);
        fries = new Article("Frites", 2.50f, 5);
        classicMenu = new Menu("Menu Classique", 10.00f);
        classicMenu.addArticleInMenu(fries);
        List<Article> articles = new ArrayList<>(); articles.add(burger); articles.add(fries);

        // Create a real restaurant instance
        restaurant = new Restaurant("Test Restaurant", articles, List.of(classicMenu));
        restaurant.setOpen(true); // Assuming you have a way to set the restaurant state

        // Initialize an order
        order = new Order(new Date(), new Date(System.currentTimeMillis() + 600000), "123 Street"); // Delivery in 1 hour
        order.addArticle(burger);

        // Create an actual GroupOrder instance
        groupOrder = new GroupOrderImpl(667, restaurant, new Date(System.currentTimeMillis() + 600000), "123 Street"); // Delivery in 1 hour
        groupOrder.addOrUpdateUserOrder(user, order); // Assuming this method exists

        // Create the proxy
        groupOrderProxy = new GroupOrderProxy(groupOrder);

        // Initialize the user
        user = new RegisteredUser("user1", 0, "password");
    }

    @Test
    void testAddOrUpdateUserOrder_Success() {
        groupOrderProxy.addOrUpdateUserOrder(user, order);
        assertNotNull(groupOrderProxy.getOrder(user));
        System.out.println(groupOrderProxy.getOrder(user));
    }

    @Test
    void testAddOrUpdateUserOrder_RestaurantClosed() {
        restaurant.setOpen(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            groupOrderProxy.addOrUpdateUserOrder(user, order);
        });
        assertEquals("Restaurant non disponible pour cette commande.", exception.getMessage());
    }

    @Test
    void testAddOrUpdateUserOrder_DeliveryTimeExceeded() {
        Order longOrder = new Order(new Date(), "123 Street");
        longOrder.addArticle(burger);
        longOrder.addArticle(fries);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            groupOrderProxy.addOrUpdateUserOrder(user, longOrder);
        });
        assertEquals("Impossible d'ajouter cette commande, elle dépasserait la date de livraison du groupe.", exception.getMessage());
    }
}