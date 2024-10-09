package fr.unice.polytech.steats;

import fr.unice.polytech.order.GroupOrder;
import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class GroupOrderCreationStepdefs {

    RegisteredUser user;
    GroupOrderImpl groupOrder;
    Restaurant restaurant;
    Date deliveryTime;
    String deliveryLocation;
    Integer groupId;
    Order order = new Order(new Date(), deliveryTime, deliveryLocation);

    @Given("a registered user of name Paul with id {int}")
    public void a_registered_user_of_name_paul_with_id(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        user = new RegisteredUser("Paul", int1, "pp");
    }

    @When("{string} wants to create a group order")
    public void wants_to_create_a_group_order(String string) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("Paul is creating a group !");
    }

    @Then("id {int} is created for the group order")
    public void id_is_created_for_the_group_order(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        groupId = int1;
    }

    @Then("{string} chooses a localisation and a delivery time for the group order with id {int}")
    public void chooses_a_localisation_and_a_delivery_time_for_the_group_order_with_id(String string, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        deliveryTime = new Date();
        deliveryLocation = "Templiers";
    }

    @Then("the group with id {int} is created")
    public void the_group_with_id_is_created(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        groupOrder = new GroupOrderImpl(int1, new Restaurant("nono"), deliveryTime, deliveryLocation);
        assertEquals(groupOrder.getGroupOrderDeliveryLocation(), deliveryLocation);
        assertEquals(groupOrder.getGroupOrderDeliveryDate(), deliveryTime);
    }

}
