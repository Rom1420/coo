package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;
import fr.unice.polytech.user.RegisteredUserManager;
import io.cucumber.java.zh_tw.假如;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class GroupOrderManagerTest {

    GroupOrderManager groupOrderManager = GroupOrderManager.getGroupOrderManagerInstance();
    int gid = groupOrderManager.getGroupOrderId();

    RegisteredUserManager registeredUserManager = RegisteredUserManager.getRegisteredUserManagerInstance();


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

    }

    @Test
    void setGroupOrderAttributes() {
        Date date = new Date();
        int gid = groupOrderManager.addGroupOrder();
        groupOrderManager.setGroupOrderAttributes(gid, new Restaurant("Nono"), "Templiers", date);
        assertEquals("Nono", groupOrderManager.getGroupOrderById(gid).getRestaurant().getName());
        assertEquals("Templiers", groupOrderManager.getGroupOrderById(gid).getGroupOrderDeliveryLocation());
        assertEquals(date, groupOrderManager.getGroupOrderById(gid).getGroupOrderDeliveryDate());
    }

    @Test
    void createGroup() {
        registeredUserManager.addNewRegisteredUser(new RegisteredUser("p1", 1, "pp1"));
        registeredUserManager.addNewRegisteredUser(new RegisteredUser("p2", 2, "pp2"));
        int lastUsedId = gid;
        int currentId = gid;
        assertEquals(lastUsedId, gid);
        assertEquals(currentId, gid);
        // On valide la création d'un groupe avec les inputs
        groupOrderManager.createGroup(true, 1, new Restaurant("Franky Vincent Le Restaurant"), "Templiers", new Date());
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
        groupOrderManager.createGroup(true, 2, new Restaurant("Bella Vita"), "Templiers", new Date());
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
        groupOrderManager.createGroup(false, 3, new Restaurant("Pizzati"), "Béal", new Date());
        gid = groupOrderManager.getGroupOrderId();
        assertEquals(lastUsedId, gid);
        assertEquals(2, groupOrderManager.getGroupOrders().size());
    }

    @Test
    void removeGroupOrder() {
        registeredUserManager.addNewRegisteredUser(new RegisteredUser("p1", 1, "pp1"));
        registeredUserManager.addNewRegisteredUser(new RegisteredUser("p2", 2, "pp2"));
        int firstId = gid;
        int secondId = firstId + 1;
        groupOrderManager.createGroup(true, 1, new Restaurant("Franky Vincent Le Restaurant"), "Templiers", new Date());
        assertEquals(1, groupOrderManager.getGroupOrders().size());
        assertEquals("Templiers", groupOrderManager.getGroupOrderById(firstId).getGroupOrderDeliveryLocation());
        groupOrderManager.createGroup(true, 2, new Restaurant("Bella Vita"), "Béal", new Date());
        assertEquals(2, groupOrderManager.getGroupOrders().size());
        assertEquals("Béal", groupOrderManager.getGroupOrderById(secondId).getGroupOrderDeliveryLocation());

        groupOrderManager.removeGroupOrderById(secondId);
        assertEquals(1, groupOrderManager.getGroupOrders().size());
        assertEquals("Templiers", groupOrderManager.getGroupOrderById(firstId).getGroupOrderDeliveryLocation());

        groupOrderManager.removeGroupOrderById(firstId);
        assertEquals(0, groupOrderManager.getGroupOrders().size());
    }
}