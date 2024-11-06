package fr.unice.polytech.order;

import fr.unice.polytech.discount.DiscountType;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Menu;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.TypeCuisine;
import fr.unice.polytech.user.RegisteredUser;
import fr.unice.polytech.user.RegisteredUserManager;
import org.junit.jupiter.api.BeforeEach;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupOrderManagerTest {

  
    private GroupOrderManager groupOrderManager;
    private GroupOrderImpl groupOrder;
    private Restaurant restaurant;
    int gid;
    RegisteredUserManager registeredUserManager = RegisteredUserManager.getRegisteredUserManagerInstance();
    Date deliveryDate;

    @BeforeEach
    public void setUp() {
        groupOrderManager = GroupOrderManager.getGroupOrderManagerInstance();
        gid = groupOrderManager.getGroupOrderId();
        restaurant = new Restaurant("yoyo");
        deliveryDate = new Date(1767225600000L);
        groupOrder = new GroupOrderImpl(10, restaurant, deliveryDate, "Campus");
    }

    @Test
    public void testValidateGroupOrder_Success() {
        groupOrderManager.addGroupOrder(10, groupOrder);
        RegisteredUser user1 = new RegisteredUser("User1", 1, "password");
        Order order1 = new Order(new Date(), deliveryDate,"Campus", restaurant);
        groupOrder.addOrUpdateUserOrder(user1, order1);
        groupOrder.setGroupOrderRestaurant(new Restaurant("toto", TypeCuisine.AUTRE, new ArrayList<>(), new ArrayList<>(), DiscountType.LOYALTY));
        groupOrderManager.validateGroupOrder(10);

        assertEquals("closed", groupOrder.getStatus());
        groupOrderManager.removeGroupOrderById(10);
        assertEquals(0, groupOrderManager.getGroupOrders().size());
    }

    @Test
    void addGroupOrder() {
        int currentId = gid;
        int currentNumberOfGroupOrders = groupOrderManager.getGroupOrders().size();
        assertEquals(currentId, gid);
        assertEquals(currentNumberOfGroupOrders, groupOrderManager.getGroupOrders().size());
        groupOrderManager.addGroupOrder();
        currentNumberOfGroupOrders++;
        gid = groupOrderManager.getGroupOrderId();
        currentId+=1;
        assertEquals(currentId, gid);
        assertEquals(currentNumberOfGroupOrders, groupOrderManager.getGroupOrders().size());
        groupOrderManager.addGroupOrder();
        currentNumberOfGroupOrders++;
        gid = groupOrderManager.getGroupOrderId();
        currentId+=1;
        assertEquals(currentId, gid);
        assertEquals(currentNumberOfGroupOrders, groupOrderManager.getGroupOrders().size());
        groupOrderManager.removeGroupOrderById(currentId-1);
        groupOrderManager.removeGroupOrderById(currentId-2);
        assertEquals(0, groupOrderManager.getGroupOrders().size());
    }

    @Test
    void setGroupOrderAttributes() {
        Date date = new Date();
        int gid = groupOrderManager.addGroupOrder();
        groupOrderManager.setGroupOrderAttributes(gid, new Restaurant("Nono"), "Templiers", date);
        assertEquals("Nono", groupOrderManager.getGroupOrderById(gid).getRestaurant().getName());
        assertEquals("Templiers", groupOrderManager.getGroupOrderById(gid).getGroupOrderDeliveryLocation());
        assertEquals(date, groupOrderManager.getGroupOrderById(gid).getGroupOrderDeliveryDate());
        groupOrderManager.removeGroupOrderById(gid);
        assertEquals(0, groupOrderManager.getGroupOrders().size());
    }

    @Test
    void createGroup() {
        Restaurant restaurant1 = new Restaurant("Franky Vincent Le Restaurant");
        Restaurant restaurant2 = new Restaurant("Bella Vita");
        Article article = new Article("Poisson", 10, 10);
        restaurant1.addArticle(article);
        restaurant2.addArticle(article);

        registeredUserManager.addNewRegisteredUser(new RegisteredUser("p1", 1, "pp1"));
        registeredUserManager.addNewRegisteredUser(new RegisteredUser("p2", 2, "pp2"));
        int lastUsedId = gid;
        int currentId = gid;
        assertEquals(lastUsedId, gid);
        assertEquals(currentId, gid);
        // On valide la création d'un groupe avec les inputs
        groupOrderManager.createGroup(true, 1, restaurant1, "Templiers", new Date(1767225600000L), new ArrayList<>(List.of(article)), new ArrayList<>());
        gid = groupOrderManager.getGroupOrderId();
        currentId+=1;
        assertEquals(currentId, gid);
        assertEquals(lastUsedId, gid - 1);
        assertEquals(1, groupOrderManager.getGroupOrders().size());
        assertEquals("Franky Vincent Le Restaurant", groupOrderManager.getGroupOrderById(lastUsedId).getRestaurant().getName());
        assertEquals(1, groupOrderManager.getGroupOrderById(lastUsedId).getUserList().size());
        assertEquals("p1", groupOrderManager.getGroupOrderById(lastUsedId).getUserList().get(0).getName());
        assertNotEquals("p2", groupOrderManager.getGroupOrderById(lastUsedId).getUserList().get(0).getName());

        lastUsedId = gid; // Nouvel id à utiliser
        groupOrderManager.createGroup(true, 2, restaurant2, "Templiers", new Date(1767225600000L), new ArrayList<>(List.of(article)), new ArrayList<>());
        gid = groupOrderManager.getGroupOrderId();
        currentId+=1;
        assertEquals(currentId, gid);
        assertEquals(lastUsedId, gid - 1);
        assertEquals(2, groupOrderManager.getGroupOrders().size());
        assertEquals("Bella Vita", groupOrderManager.getGroupOrderById(lastUsedId).getRestaurant().getName());
        assertEquals(1, groupOrderManager.getGroupOrderById(lastUsedId).getUserList().size());
        assertEquals("p2", groupOrderManager.getGroupOrderById(lastUsedId).getUserList().get(0).getName());
        assertNotEquals("p1", groupOrderManager.getGroupOrderById(lastUsedId).getUserList().get(0).getName());

        // On annule la commande en cours de création
        lastUsedId = gid;
        groupOrderManager.createGroup(false, 3, restaurant1, "Béal", new Date(), new ArrayList<>(List.of(article)), new ArrayList<>());
        gid = groupOrderManager.getGroupOrderId();
        assertEquals(lastUsedId, gid);
        assertEquals(2, groupOrderManager.getGroupOrders().size());

        groupOrderManager.removeGroupOrderById(lastUsedId-2);
        groupOrderManager.removeGroupOrderById(lastUsedId-1);

        assertEquals(0, groupOrderManager.getGroupOrders().size());
    }

    @Test
    void removeGroupOrder() {
        Restaurant restaurant1 = new Restaurant("Franky Vincent Le Restaurant");
        Restaurant restaurant2 = new Restaurant("Bella vita");
        Article article = new Article("Poisson", 10, 10);
        restaurant1.addArticle(article);
        restaurant2.addArticle(article);

        registeredUserManager.addNewRegisteredUser(new RegisteredUser("p1", 1, "pp1"));
        registeredUserManager.addNewRegisteredUser(new RegisteredUser("p2", 2, "pp2"));
        int firstId = gid;
        int secondId = firstId + 1;
        groupOrderManager.createGroup(true, 1, restaurant1, "Templiers", new Date(1767225600000L),new ArrayList<>(List.of(article)), new ArrayList<>() );
        assertEquals(1, groupOrderManager.getGroupOrders().size());
        assertEquals("Templiers", groupOrderManager.getGroupOrderById(firstId).getGroupOrderDeliveryLocation());
        groupOrderManager.createGroup(true, 2, restaurant2, "Béal", new Date(1767225600000L), new ArrayList<>(List.of(article)), new ArrayList<>());
        assertEquals(2, groupOrderManager.getGroupOrders().size());
        assertEquals("Béal", groupOrderManager.getGroupOrderById(secondId).getGroupOrderDeliveryLocation());

        groupOrderManager.removeGroupOrderById(secondId);
        assertEquals(1, groupOrderManager.getGroupOrders().size());
        assertEquals("Templiers", groupOrderManager.getGroupOrderById(firstId).getGroupOrderDeliveryLocation());

        groupOrderManager.removeGroupOrderById(firstId);
        assertEquals(0, groupOrderManager.getGroupOrders().size());
    }

    @Test
    void joinGroupOrder() {
        int lastUsedId = gid;
        Restaurant restaurant1 = new Restaurant("Franky Vincent Le Restaurant");
        Article article = new Article("Poisson", 10, 10);
        Article article2 = new Article("Steak", 12, 12);
        restaurant1.addArticle(article);
        restaurant1.addArticle(article2);
        Date date = new Date(1767225600000L);
        RegisteredUser user1 = new RegisteredUser("p1", 1, "pp1");
        RegisteredUser user2 = new RegisteredUser("p2", 2, "pp2");
        registeredUserManager.addNewRegisteredUser(user1);
        registeredUserManager.addNewRegisteredUser(user2);

        groupOrderManager.createGroup(true, 1, restaurant1, "Learning Center", date, new ArrayList<>(List.of(article)), new ArrayList<>());
        assertEquals(1, groupOrderManager.getGroupOrderById(lastUsedId).getUserList().size());

        groupOrderManager.joinGroup(true, 2, lastUsedId, new ArrayList<>(List.of(article2)), new ArrayList<>());
        assertEquals(2, groupOrderManager.getGroupOrderById(lastUsedId).getUserList().size());

        assertEquals("Poisson", groupOrderManager.getGroupOrderById(lastUsedId).getOrder(user1).getOrderedArticles().get(0).getName());
        assertEquals("Steak", groupOrderManager.getGroupOrderById(lastUsedId).getOrder(user2).getOrderedArticles().get(0).getName());

        assertEquals("Learning Center", groupOrderManager.getGroupOrderById(lastUsedId).getOrder(user1).getDeliveryLocation());
        assertEquals("Learning Center", groupOrderManager.getGroupOrderById(lastUsedId).getOrder(user2).getDeliveryLocation());

        assertEquals(2, groupOrderManager.getGroupOrderById(lastUsedId).getUserList().size());
        groupOrderManager.joinGroup(true, 2, lastUsedId, new ArrayList<>(List.of(article)), new ArrayList<>());
        assertEquals(2, groupOrderManager.getGroupOrderById(lastUsedId).getUserList().size());
        assertEquals("Poisson", groupOrderManager.getGroupOrderById(lastUsedId).getOrder(user2).getOrderedArticles().get(0).getName());
        assertNotEquals("Steak", groupOrderManager.getGroupOrderById(lastUsedId).getOrder(user2).getOrderedArticles().get(0).getName());

        groupOrderManager.removeGroupOrderById(lastUsedId);
    }
}