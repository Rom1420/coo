package fr.unice.polytech.steats;

import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderManager;
import fr.unice.polytech.system.PaymentSystem;
import fr.unice.polytech.user.UserAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Date;

public class passOrder {

    private UserAccount user;

    private OrderManager manager;

    private Order order;

    private PaymentSystem paymentSystem;


    @Given("a user of name {string} and with id {int}")
    public void aUserOfNameAndWithId(String arg0, int arg1) {
        user = new UserAccount(arg0, arg1, "1111");
    }
    @When("{string} create an order {string} at {string} and confirms it")
    public void createAnOrderAtAndConfirmsIt(String arg0, String arg1, String arg2) {
        manager = new OrderManager();
        order = new Order(new Date(), arg2);
    }

    @Then("the order is add to {string}'s orders and he pays it")
    public void theOrderIsAddToSOrdersAndHePaysIt(String arg0) {
        manager.addOrder(user.getId(), order);
        paymentSystem = new PaymentSystem();
        paymentSystem.payOrder(order, order.getTotalPrice());
    }
}
