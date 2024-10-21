package fr.unice.polytech.steats;

import fr.unice.polytech.order.GroupOrderImpl;
import fr.unice.polytech.order.Order;
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

public class ItemCountDiscountStepdefs {

    private GroupOrderImpl groupOrder3;
    private Restaurant restaurant2;
    private Article article;
    private RegisteredUser user1;
    private RegisteredUser user2;
    private Date deliveryDate = new Date(1767225600000L);
    private String deliveryLocation = "Location";
    private RestaurantManager restaurantManager = RestaurantManager.getRestaurantManagerInstance();

    @Given("restaurant {string} offering an item count discount")
    public void restaurant_offering_an_item_count_discount(String restaurantName) {
        restaurant2 = new Restaurant(restaurantName, TypeCuisine.AUTRE, new ArrayList<>(), new ArrayList<>(), DiscountType.ITEM_COUNT);
        article = new Article("Poisson", 10, 100); // Exemple d'article
        restaurant2.addArticle(article);
        restaurantManager.addRestaurant(restaurant2);
    }

    @Given("a group order with id {int} with restaurant {string} is validated with {int} members with {string} with id {int} and {string} in it")
    public void a_group_order_with_id_with_restaurant_is_validated_with_members_with_with_id_and_in_it(Integer id, String restaurantName, Integer members, String userName1, Integer userId1, String userName2) {
        groupOrder3 = new GroupOrderImpl(id, restaurantManager.findRestaurantByName(restaurantName), deliveryDate, deliveryLocation);

        user1 = new RegisteredUser(userName1, userId1, "pwd1");
        user2 = new RegisteredUser(userName2, userId1 + 1, "pwd2");

        groupOrder3.addMember(user1);
        groupOrder3.addMember(user2);
    }

    @When("{string} ordered {int} items and {string} ordered {int} items")
    public void ordered_items_and_ordered_items(String userName1, Integer items1, String userName2, Integer items2) {
        // Créer des commandes pour les deux utilisateurs
        Order order1 = new Order(new Date(), deliveryDate, deliveryLocation, restaurant2);
        for (int i = 0; i < items1; i++) {
            order1.addArticle(article); // Ajouter des articles pour user1
        }

        Order order2 = new Order(new Date(), deliveryDate, deliveryLocation, restaurant2);
        for (int i = 0; i < items2; i++) {
            order2.addArticle(article); // Ajouter des articles pour user2
        }

        // Ajouter les commandes au groupe
        groupOrder3.addOrUpdateUserOrder(user1, order1);
        groupOrder3.addOrUpdateUserOrder(user2, order2);
    }

    @Then("group order with id {int} is validated")
    public void group_order_with_id_is_validated(Integer id) {
        groupOrder3.validateOrder();
    }

    @Then("a {int}% item count discount is applied to {string}'s order")
    public void a_item_count_discount_is_applied_to_s_order(Integer discount, String userName) {
        RegisteredUser user = userName.equals(user1.getName()) ? user1 : user2;
        Order userOrder = groupOrder3.getOrder(user);
        float originalPrice = userOrder.getTotalPrice();
        groupOrder3.applyDiscount();

        float expectedPrice = originalPrice * (1 - (discount / 100f));
        assertEquals(expectedPrice, userOrder.getTotalPrice(), 0.01f);
    }

    @Then("no discount is applied to {string}'s order")
    public void no_discount_is_applied_to_s_order(String userName) {
        RegisteredUser user = userName.equals(user1.getName()) ? user1 : user2;
        Order userOrder = groupOrder3.getOrder(user);
        assertEquals(userOrder.getTotalPrice(), userOrder.getTotalPrice(), 0.01f);
    }
}
