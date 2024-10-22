package fr.unice.polytech.steats;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.Order;
import fr.unice.polytech.order.OrderManager;
import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;
import fr.unice.polytech.discount.DiscountType;
import fr.unice.polytech.restaurant.TypeCuisine;
import fr.unice.polytech.user.RegisteredUser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LoyaltyDiscountStepdefs {

    private GroupOrderImpl groupOrder4;
    private Restaurant restaurant3;
    private Article article;
    private RegisteredUser user2;
    private RegisteredUser user3;
    private Date deliveryDate = new Date(1767225600000L);
    private String deliveryLocation = "Location";
    private RestaurantManager restaurantManager = RestaurantManager.getRestaurantManagerInstance();
    private OrderManager orderManager = OrderManager.getOrderManagerInstance();

    @Given("restaurant {string} offering a loyalty discount")
    public void restaurant_offering_a_loyalty_discount(String restaurantName) {
        restaurant3 = new Restaurant(restaurantName, TypeCuisine.AUTRE, new ArrayList<>(), new ArrayList<>(), DiscountType.LOYALTY);
        article = new Article("Poisson", 10, 100); // Exemple d'article
        restaurant3.addArticle(article);
        restaurantManager.addRestaurant(restaurant3);
    }

    @Given("a registered user {string} with id {int} who has already placed {int} orders in Restaurant {string}")
    public void a_registered_user_with_id_who_has_already_placed_orders_in_restaurant(String userName, Integer userId, Integer orders, String restaurantName) {
        user2 = new RegisteredUser(userName, userId, "pwd");
        groupOrder4 = new GroupOrderImpl(4, restaurantManager.findRestaurantByName(restaurantName), deliveryDate, deliveryLocation);
        groupOrder4.addMember(user2);
        for (int i = 0; i < orders; i++) {
            orderManager.addOrder(userId, new Order(new Date(), deliveryDate, deliveryLocation, restaurantManager.findRestaurantByName(restaurantName)));

        }
        System.out.println(userName + " has already placed " + orders + " orders in " + restaurantName);
    }

    @Given("a group order with id {int} with restaurant {string} is validated with {int} members with {string} with id {int} and {string} with id {int} in it")
    public void a_group_order_with_id_with_restaurant_is_validated_with_members_with_with_id_and_with_id_in_it(Integer id, String restaurantName, Integer members, String userName1, Integer userId1, String userName2, Integer userId2) {
        groupOrder4 = new GroupOrderImpl(id, restaurantManager.findRestaurantByName(restaurantName), deliveryDate, deliveryLocation);
        user2 = new RegisteredUser(userName1, userId1, "pwd1");
        user3 = new RegisteredUser(userName2, userId2, "pwd2");

        groupOrder4.addMember(user2);
        groupOrder4.addMember(user3);
    }

    @When("{string} and {string} place orders")
    public void and_place_orders(String userName1, String userName2) {
        // Créer des commandes pour les deux utilisateurs
        Order order1 = new Order(new Date(), deliveryDate, deliveryLocation, restaurant3);
        order1.addArticle(article);

        Order order2 = new Order(new Date(), deliveryDate, deliveryLocation, restaurant3);
        order2.addArticle(article);

        groupOrder4.addOrUpdateUserOrder(user2, order1);
        groupOrder4.addOrUpdateUserOrder(user3, order2);
        System.out.println(userName1 + " and " + userName2 + " place orders");
        System.out.println(userName1 + "'s order costs " + order1.getTotalPrice() + " and " + userName2 + "'s order costs " + order2.getTotalPrice());
    }

    @Then("group order with id {int} is validate")
    public void group_order_with_id_is_validate(Integer int1) {
        groupOrder4.validateOrder();
    }

    @Then("a {int}% loyalty discount is applied to {string}'s order")
    public void a_loyalty_discount_is_applied_to_s_order(Integer discount, String userName) {
        RegisteredUser user = userName.equals(user2.getName()) ? user2 : user3;
        Order userOrder = groupOrder4.getOrder(user);
        float originalPrice = userOrder.getTotalPrice();
        groupOrder4.applyDiscount();

        float expectedPrice = originalPrice * (1 - (discount / 100f));
        assertEquals(expectedPrice, userOrder.getTotalPrice(), 0.01f);
        System.out.println(discount + "% 'loyalty discount' is applied to " + userName + "'s order");
        System.out.println(userName + "'s order costs now " + userOrder.getTotalPrice());
    }

    @Then("no discount applied to {string}'s order")
    public void no_discount_applied_to_order(String userName) {
        RegisteredUser user = userName.equals(user2.getName()) ? user2 : user3;
        Order userOrder = groupOrder4.getOrder(user);
        groupOrder4.applyDiscount();
        assertEquals(userOrder.getTotalPrice(), userOrder.getTotalPrice(), 0.01f);
        System.out.println("no discount for "+ userName + ", his order still costs " + userOrder.getTotalPrice());

    }
}
