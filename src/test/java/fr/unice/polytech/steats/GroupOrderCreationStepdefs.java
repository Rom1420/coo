package fr.unice.polytech.steats;

import fr.unice.polytech.order.GroupOrder;
import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.GroupOrderManager;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;
import fr.unice.polytech.user.RegisteredUserManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.internal.matchers.Or;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GroupOrderCreationStepdefs {

    RegisteredUser user;
    RegisteredUserManager registeredUserManager = RegisteredUserManager.getRegisteredUserManagerInstance();
    GroupOrderManager groupOrderManager = GroupOrderManager.getGroupOrderManagerInstance();
    GroupOrderImpl groupOrder = new GroupOrderImpl(0);
    Restaurant restaurant = new Restaurant("Restau");
    String deliveryLocalisation;
    Date deliveryTime;
    Order order;
    int nbOfGroupOrderBeforeCreation;

    @Given("a registered user {string} with id {int}")
    public void a_registered_user_of_name_paul_with_id(String string, Integer int1) {
        user = new RegisteredUser(string, int1, "pp");
        registeredUserManager.addNewRegisteredUser(user);
    }

    @When("{string} with id {int} creates a group order")
    public void creates_a_group_order(String string, Integer int1) {
        user = registeredUserManager.getRegisteredUserById(int1);
        assertEquals(string, user.getName());
    }

    @Then("a group order with id {int} is created")
    public void a_group_order_with_id_is_created(Integer int1) {
        nbOfGroupOrderBeforeCreation = groupOrderManager.getGroupOrders().size();
        groupOrder = new GroupOrderImpl(int1);
        groupOrderManager.addGroupOrder(int1, groupOrder);
    }

    @When("{string} chooses a restaurant, a localisation and a delivery time for the group order with id {int}")
    public void chooses_a_restaurant_a_localisation_and_a_delivery_time_for_the_group_order_with_id(String string, Integer int1) {
        assertEquals(string, user.getName());
        deliveryLocalisation = "Templiers";
        deliveryTime = new Date(1767225600000L);
        groupOrder = groupOrderManager.getGroupOrderById(int1);
        groupOrder.setGroupOrderRestaurant(restaurant);
        groupOrder.setGroupOrderDeliveryLocation(deliveryLocalisation);
        groupOrder.setGroupOrderDeliveryDate(deliveryTime);
    }

    @Then("group order with id {int} restaurant parameter is the one given")
    public void group_order_with_id_restaurant_parameter_is_the_one_given(Integer int1) {
        groupOrder = groupOrderManager.getGroupOrderById(int1);
        assertEquals("Restau", groupOrder.getRestaurant().getName());
    }

    @Then("group order with id {int} localisation parameter is the one given")
    public void group_order_with_id_localisation_parameter_is_the_one_given(Integer int1) {
        groupOrder = groupOrderManager.getGroupOrderById(int1);
        assertEquals("Templiers", groupOrder.getGroupOrderDeliveryLocation());
    }

    @Then("group order with id {int} delivery time parameter is the one given")
    public void group_order_with_id_delivery_time_parameter_is_the_one_given(Integer int1) {
        groupOrder = groupOrderManager.getGroupOrderById(int1);
        assertEquals(deliveryTime, groupOrder.getGroupOrderDeliveryDate());
    }

    @When("{string} creates his order for the group order with id {int}")
    public void creates_his_order_with_id_for_the_group_order_with_id(String string, Integer int1) {
        assertEquals(string, user.getName());
        groupOrder = groupOrderManager.getGroupOrderById(int1);
        order = new Order(new Date(), groupOrder.getGroupOrderDeliveryDate(), groupOrder.getGroupOrderDeliveryLocation(), restaurant);
        groupOrder.addOrUpdateUserOrder(user, order);
    }

    @Then("the order restaurant parameter is the one given for group order with id {int}")
    public void the_order_with_id_restaurant_parameter_is_the_one_given_for_group_order_with_id(Integer int1) {
        groupOrder = groupOrderManager.getGroupOrderById(int1);
        order = groupOrder.getOrder(user);
        assertEquals(groupOrder.getRestaurant().getName(), order.getRestaurant().getName());
    }

    @Then("the order localisation parameter is the one given for group order with id {int}")
    public void the_order_with_id_localisation_parameter_is_the_one_given_for_group_order_with_id(Integer int1) {
        order = groupOrder.getOrder(user);
        assertEquals(groupOrderManager.getGroupOrderById(int1).getGroupOrderDeliveryLocation(), order.getDeliveryLocation());
    }

    @Then("the order delivery time parameter is the one given for group order with id {int}")
    public void the_order_with_id_delivery_time_parameter_is_the_one_given_for_group_order_with_id(Integer int1) {
        order = groupOrder.getOrder(user);
        assertEquals(groupOrderManager.getGroupOrderById(int1).getGroupOrderDeliveryDate(), order.getDeliveryDate());
    }

    @Then("the group order with id {int} is added to the list of group orders")
    public void the_group_order_with_id_is_added_to_the_list_of_group_orders(Integer int1) {
        assertEquals(nbOfGroupOrderBeforeCreation+1, groupOrderManager.getGroupOrders().size());
        assertNotNull(groupOrderManager.getGroupOrderById(int1));
    }


}
