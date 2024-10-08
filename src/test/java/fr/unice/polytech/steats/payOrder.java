package fr.unice.polytech.steats;

import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderManager;
import fr.unice.polytech.system.PaymentSystem;
import fr.unice.polytech.user.UserAccount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Date;

public class payOrder {
    UserAccount user;

    OrderManager manager;

    Order order;

    PaymentSystem paymentSystem;

    @Given("a user of name {string} and with id {int} and an order")
    public void aUserOfNameAndWithId(String arg0, int arg1) {
        user = new UserAccount(arg0, arg1, "1111");
        manager = new OrderManager();
        order = new Order(new Date(), "Popo");
        manager.addOrder(user.getId(), order);
    }

    @When("the user have an order and want to pay it")
    public void theUserHaveAnOrderAndWantToPayIt() {}


    @Then("proceed to payment")
    public void proceedToPayment() {
        paymentSystem = new PaymentSystem();
        paymentSystem.payOrder(order, order.getTotalPrice());
    }
}
