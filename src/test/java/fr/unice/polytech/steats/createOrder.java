package fr.unice.polytech.steats;

import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderManager;
import fr.unice.polytech.user.UserAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Date;

public class createOrder {
    private UserAccount user;

    private OrderManager manager;

    private Order order;


    @Given("a user of name {string} and with student id {int}")
    public void aUserOfNameAndWithStudentId(String arg0, int arg1) {
        user = new UserAccount(arg0, arg1, "1111");
    }

    @When("{string} create an order {string} at {string}")
    public void createAnOrderAt(String arg0, String arg1, String arg2) {
        manager = new OrderManager();
        order = new Order(new Date(), arg2);
    }

    @Then("the order is add to {string}'s orders")
    public void theOrderIsAddToSOrders(String arg0) {
        manager.addOrder(user.getId(), order);
    }


}
