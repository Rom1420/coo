package fr.unice.polytech.steats;

import fr.unice.polytech.order.GroupOrder;
import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.GroupOrderManager;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;

public class GroupOrderJoinStepdefs {

    GroupOrderManager groupOrderManager = GroupOrderManager.getGroupOrderManagerInstance();
    GroupOrderImpl groupOrder1;
    Date group1DeliveryDate = new Date(1767225600000L);
    String group1DeliveryLocation = "Templiers";
    GroupOrderImpl groupOrder2;
    Date group2DeliveryDate = new Date(1767225610000L);
    String group2DeliveryLocation = "Sophia";
    RegisteredUser user1;
    RegisteredUser user2;
    RegisteredUser user3;
    Order user3Order;
    Restaurant restaurant = new Restaurant("Francky");

    @Given("a group order with id {int} not already validated")
    public void a_group_order_with_id_not_already_validated(Integer int1) {
        groupOrder1 = new GroupOrderImpl(int1, restaurant, group1DeliveryDate, group1DeliveryLocation);
        groupOrderManager.addGroupOrder(int1, groupOrder1);
    }

    @Given("a group order with id {int} already validated")
    public void a_group_order_with_already_valited(Integer int1) {
        groupOrder2 = new GroupOrderImpl(int1, restaurant, group2DeliveryDate, group2DeliveryLocation);
        groupOrderManager.addGroupOrder(int1, groupOrder2);
    }

    @Given("a registered user of name {string} who created the group order with id {int}")
    public void a_registered_user_of_name_who_created_the_group_order_with_id(String string, Integer int1) {
        user1 = new RegisteredUser(string, 1, "u1p");
        groupOrderManager.getGroupOrderById(int1).addMember(user1);
    }

    @Given("a registered user of name {string} who created the group order with id {int} and validated it")
    public void a_registered_user_of_name_who_created_the_group_order_with_with_and_validated_it(String string, Integer int1) {
        user2 = new RegisteredUser(string, 2, "u2p");
        groupOrderManager.getGroupOrderById(int1).addMember(user2);
        groupOrder2.validateOrder();
    }

    @Given("a registered user named {string} with id {int}")
    public void a_registered_user_named_with_id(String string, Integer int1) {
        user3 = new RegisteredUser(string, int1, "u3p");
    }

    @When("{string} enters the group id {int}")
    public void enters_the_group_id(String string, Integer int1) {
        assertEquals(string, user3.getName());
        groupOrderManager.getGroupOrderById(int1).addMember(user3);
    }

    @Then("{string} creates his personal order for the group order with id {int}")
    public void creates_his_personal_order_for_the_group_order_with_id(String string, Integer int1) {
        assertEquals(string, user3.getName());
        user3Order = new Order(new Date(), group1DeliveryDate, group1DeliveryLocation, restaurant);
        groupOrderManager.getGroupOrderById(int1).addOrUpdateUserOrder(user3, user3Order);
    }

    @Then("The restaurant, the localisation and delivery time of his individual order is predefined by the group order with id {int}")
    public void the_restaurant_the_localisation_and_delivery_time_of_his_individual_order_is_predefined_by_the_group_order_with_id(Integer int1) {
        assertEquals(groupOrderManager.getGroupOrderById(int1).getRestaurant(), user3Order.getRestaurant());
        assertEquals(groupOrderManager.getGroupOrderById(int1).getGroupOrderDeliveryDate(), user3Order.getDeliveryDate());
        assertEquals(groupOrderManager.getGroupOrderById(int1).getGroupOrderDeliveryLocation(), user3Order.getDeliveryLocation());
    }

    @Then("There is {int} members in group order with id {int}")
    public void there_is_members_in_group_order_with_id(Integer int1, Integer int2) {
        int nbOfusers = groupOrderManager.getGroupOrderById(int2).getUserList().size();
        assertEquals(Optional.of(nbOfusers), Optional.of(int1));
    }

    @When("{string} enters the group id {int} of an validated group")
    public void enters_the_group_id_of_an_validated_group(String string, Integer int1) {
        assertEquals(string, user3.getName());
        assertThrows(RuntimeException.class, () -> groupOrderManager.getGroupOrderById(int1).addMember(user3));
    }

    @Then("{string} receives an error message which saying that group order is already validated")
    public void receives_an_error_message_wich_saying_that_group_order_is_already_validated(String string) {
        System.out.println("Impossible d'ajouter un membre car le groupe est fermé");
    }

    @Then("{string} can't join the group with id {int}")
    public void can_t_join_the_group(String string, Integer int1) {
        int nbOfusers = groupOrderManager.getGroupOrderById(int1).getUserList().size();
        assertEquals(Optional.of(1), Optional.of(nbOfusers));
    }

    @When("{string} enters the group id {int} of an none existant group")
    public void enters_the_group_id_of_an_none_existant_group(String string, Integer int1) {
        assertThrows(RuntimeException.class, () -> groupOrderManager.joinGroup(true, user3.getId(), int1, new ArrayList<>(), new ArrayList<>()));
    }

    @Then("{string} receives an error message which saying that group order does not exist")
    public void receives_an_error_message_wich_saying_that_group_order_does_not_exist(String string) {
        System.out.println("Idenrifiant de groupe inexistant !");
    }

    @Then("{string} don't join a group")
    public void don_t_join_a_group(String string) {
        assertEquals(2, groupOrderManager.getGroupOrders().size());
        groupOrderManager.removeGroupOrderById(456);
        groupOrderManager.removeGroupOrderById(789);
        assertEquals(0, groupOrderManager.getGroupOrders().size());
    }
}
