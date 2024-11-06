package fr.unice.polytech.steats;

import fr.unice.polytech.discount.DiscountEngine;
import fr.unice.polytech.discount.DiscountType;
import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderManager;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.TypeCuisine;
import fr.unice.polytech.system.Facade;
import fr.unice.polytech.user.RegisteredUser;
import fr.unice.polytech.user.RegisteredUserManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.internal.matchers.Or;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GroupOrderManageStepdefs {

    GroupOrderImpl groupOrder;
    Restaurant restaurant;
    RegisteredUser user1;
    RegisteredUser user2;
    RegisteredUser user3;
    Map<RegisteredUser, Integer> orderHistory;

    Date deliveryTime;
    String deliveryLocation;
    Order order;
    Order order1;
    Order order2;



    @Given("a group order with id {int} and and already {string} and {string} in it")
    public void a_group_order_with_id_and_and_already_and_in_it(Integer int1, String string, String string2) {
        restaurant = new Restaurant("Nono", TypeCuisine.AUTRE, new ArrayList<>(), new ArrayList<>(), DiscountType.LOYALTY);
        deliveryTime = new Date();
        deliveryLocation = "Templiers";
        groupOrder = new GroupOrderImpl(int1, restaurant, deliveryTime, deliveryLocation);
        user1 = new RegisteredUser(string, 123, "dp");
        user2 = new RegisteredUser(string2, 456, "pp");
        groupOrder.addMember(user1);
        groupOrder.addMember(user2);

        OrderManager orderManager = OrderManager.getOrderManagerInstance();
        orderManager.addOrder(123,new Order(new Date(), "location", restaurant));
        orderManager.addOrder(123,new Order(new Date(), "location", restaurant));
        orderManager.addOrder(123,new Order(new Date(), "location", restaurant));
        orderManager.addOrder(123,new Order(new Date(), "location", restaurant));
        orderManager.addOrder(123,new Order(new Date(), "location", restaurant));

        orderHistory = groupOrder.calculateOrderHistory();

        order1 = new Order(new Date(), deliveryTime, deliveryLocation, restaurant);
        order1.setTotalPrice(20f);
        groupOrder.addOrUpdateUserOrder(user1, order1);

        order2 = new Order(new Date(), deliveryTime, deliveryLocation, restaurant);
        order2.setTotalPrice(50f);
        groupOrder.addOrUpdateUserOrder(user2, order2);

    }

    @Given("a registered user of name {string} with id {int}")
    public void a_registered_user_of_name_with_id(String string, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        user3 = new RegisteredUser(string, int1, "rp");
    }

    @When("{string} enters the group order id {int}")
    public void enters_the_group_order_id(String name, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println(user3.getName() + " enters groupId 1234 !");
    }

    @Then("{string} joins the group order with id {int}")
    public void joins_the_group_order_with_id(String name, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        groupOrder.addMember(user3);
    }

    @Then("There is {int} members in the group order with id {int}")
    public void there_is_members_in_the_group_order_with_id(Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        Integer nMembers = groupOrder.getUserList().size();
        assertEquals(nMembers, int1);
    }

    @Then("{string} creates his individual order for the group order with id {int}")
    public void creates_his_individual_order_for_the_group_order_with_id(String name, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        order = new Order(new Date(), deliveryTime, deliveryLocation, restaurant);
        groupOrder.addOrUpdateUserOrder(user3, order);
    }

    @Then("The localisation and delivery time of his individual order is predefined by the group order with id {int}")
    public void the_localisation_and_delivery_time_of_his_individual_order_is_predefined_by_the_group_order_with_id(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(order.getDeliveryDate(), deliveryTime);
        assertEquals(order.getDeliveryLocation(), deliveryLocation);
    }

    @When("{string} validates the group order")
    public void validate_the_group_order(String userName) {
        // Write code here that turns the phrase above into concrete actions
        groupOrder.validateOrder();
        System.out.println(userName + " has validated the group order");
    }

    @Then("the group order status should be {string}")
    public void the_group_order_status_should_be(String expectedStatus) {
        // Write code here that turns the phrase above into concrete actions
        String actualStatus = groupOrder.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }

    @And("no individual orders can be modified after validation")
    public void noIndividualOrdersCanBeModifiedAfterValidation() {
        assertThrows(IllegalStateException.class, () ->
                groupOrder.addOrUpdateUserOrder(
                        new RegisteredUser("John", 4, "password"),
                        new Order(new Date(), "Location", groupOrder.getRestaurant())
                )
        );
    }


    @And("personalized discounts \\(based on group size, loyalty, or number of items) are applied to each eligible individual order")
    public void personalized_discounts_based_on_group_size_loyalty_or_number_of_items_are_applied_to_each_eligible_individual_order() {
        System.out.println("prix avant "+ order1.getTotalPrice());
        groupOrder.applyDiscount();
        System.out.println("prix après "+ order1.getTotalPrice() );
    }

    @And("the group order is ready for restaurant preparation")
    public void theGroupOrderIsReadyForRestaurantPreparation() {
        assertEquals("The group order should be ready for restaurant preparation", "validated", groupOrder.getStatus());
        System.out.println("The group order is ready for restaurant preparation");
    }


    @And("the group order status should change to {string}")
    public void the_group_order_status_should_change_to(String expectedStatus) {
        groupOrder.closeOrder();

        assertEquals(expectedStatus, groupOrder.getStatus());
        System.out.println("group order status changed to " + expectedStatus);
    }


}
