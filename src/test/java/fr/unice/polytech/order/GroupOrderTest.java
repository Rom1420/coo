package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.*;
import fr.unice.polytech.user.RegisteredUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupOrderTest {
    private GroupOrderImpl groupOrder;
    private Article burger;
    private Article fries;
    private Article drink;
    private Menu classicMenu;
    private Order order1;
    private Order order2;
    private Restaurant restaurant;
    private RegisteredUser user1;
    private RegisteredUser user2;
    private Date deliveryDate;

    @BeforeEach
    void setUp() {
        // Initialisation des articles
        burger = new Article("Burger", 8.50f, 10, Categorie.PLAT);
        fries = new Article("Frites", 2.50f, 5, Categorie.ACCOMPAGNEMENT);
        drink = new Article("Boisson", 1.50f, 2, Categorie.BOISSON);
        List<Article> articles = new ArrayList<>();
        articles.add(burger);
        articles.add(fries);
        articles.add(drink);

        // Initialisation du menu
        classicMenu = new Menu("Menu Classique", 10.00f);
        classicMenu.addArticleInMenu(fries);
        classicMenu.addArticleInMenu(drink);

        // Initialisation du restaurant
        restaurant = new Restaurant("Mcdo", TypeCuisine.FASTFOOD ,articles, List.of(classicMenu));

        // Initialisation des données de test
        Date orderDate = new Date();
        String deliveryLocation = "123 Street";
        deliveryDate = new Date(orderDate.getTime() + (long) (60 * 60000)); // Livraison dans 60 minutes

        // Initialisation de l'objet GroupOrder
        groupOrder = new GroupOrderImpl(667, restaurant, deliveryDate, deliveryLocation);

        // Initialisation des utilisateurs
        user1 = new RegisteredUser("user1", 0, "password");
        user2 = new RegisteredUser("user2", 1, "password");
    }

    @Test
    void testAddOrUpdateUserOrder() {
        Restaurant restaurant = new Restaurant("Restau");
        restaurant.addMenu(classicMenu);
        Order order = new Order(new Date(), deliveryDate, "123 Street", restaurant);
        order.addMenu(classicMenu);
        groupOrder.addOrUpdateUserOrder(user1, order);

        assertEquals(order, groupOrder.getOrder(user1));
        assertEquals(1, groupOrder.getUsersOrders().size());
    }

    @Test
    void testGetOrder() {
        Restaurant restaurant = new Restaurant("Restau");
        restaurant.addMenu(classicMenu);
        Order order = new Order(new Date(), deliveryDate, "123 Street", restaurant);
        order.addMenu(classicMenu);
        groupOrder.addOrUpdateUserOrder(user1, order);

        Order retrievedOrder = groupOrder.getOrder(user1);
        assertNotNull(retrievedOrder);
        assertEquals(classicMenu.toString(), retrievedOrder.getOrderedMenus().get(0).toString());
    }

    @Test
    void testRemoveOrder() {
        Order order = new Order(new Date(), deliveryDate, "123 Street", new Restaurant("Restau"));
        groupOrder.addOrUpdateUserOrder(user1, order);

        assertTrue(groupOrder.getUsersOrders().containsKey(user1));

        groupOrder.removeOrder(user1);

        assertFalse(groupOrder.getUsersOrders().containsKey(user1));
    }

    @Test
    void testGetGroupOrderDeliveryDate() {
        assertEquals(deliveryDate, groupOrder.getGroupOrderDeliveryDate());
    }

    @Test
    void testGetTotalPreparationTime() {
        Restaurant restaurant = new Restaurant("Restau");
        restaurant.addMenu(classicMenu);
        restaurant.addArticle(burger);
        Order order1 = new Order(new Date(), deliveryDate, "123 Street", restaurant);
        order1.addMenu(classicMenu);
        groupOrder.addOrUpdateUserOrder(user1, order1);

        Order order2 = new Order(new Date(), deliveryDate, "123 Street", restaurant);
        order2.addArticle(burger);
        groupOrder.addOrUpdateUserOrder(user2, order2);

        assertEquals(17, groupOrder.getTotalPreparationTime()); // 10 (menu) + 10 (burger) + 5 (fries)
    }

    @Test
    void testGetGroupId(){
        assertEquals(667,groupOrder.getGroupId());
    }

    @Test
    void getRestaurant(){
        assertEquals(groupOrder.getRestaurant(), restaurant);
    }

}