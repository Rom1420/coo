package fr.unice.polytech.order;

import static org.junit.jupiter.api.Assertions.*;

import fr.unice.polytech.order.Order;
import fr.unice.polytech.user.RegisteredUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fr.unice.polytech.restaurant.Restaurant;

import java.util.Date;

public class GroupOrderManagerTest {

    private GroupOrderManager groupOrderManager;
    private GroupOrderImpl groupOrder;
    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        groupOrderManager = GroupOrderManager.getGroupOrderManagerInstance();
        restaurant = new Restaurant("yoyo");
        groupOrder = new GroupOrderImpl(1, restaurant, new Date(), "Campus");
        groupOrderManager.addGroupOrder(1, groupOrder);
    }

    @Test
    public void testValidateGroupOrder_Success() {
        // Ajoute un membre et sa commande
        RegisteredUser user1 = new RegisteredUser("User1", 1, "password");
        Order order1 = new Order(new Date(), "Campus");
        groupOrder.addOrUpdateUserOrder(user1, order1);  // L'utilisateur passe sa commande

        // Valide la commande
        groupOrderManager.validateGroupOrder(1);

        // Vérifie que la commande est bien validée et que le statut est 'closed'
        assertEquals("closed", groupOrder.getStatus());
    }

}
