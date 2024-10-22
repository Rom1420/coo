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

public class GroupSizeDiscountStepdefs {

    private GroupOrderImpl groupOrder1;
    private GroupOrderImpl groupOrder2;
    private Order order;
    private Restaurant restaurant1;
    private Article article = new Article("Poisson", 10, 100);;
    private RegisteredUser user1;
    private Date deliveryDate = new Date(1767225600000L);
    private String deliveryLocation = "Location";
    private RestaurantManager restaurantManager = RestaurantManager.getRestaurantManagerInstance();

    @Given("restaurant {string} offering a group size discount")
    public void restaurant_offering_a_group_size_discount(String string) {
        restaurant1 = new Restaurant(string, TypeCuisine.AUTRE, new ArrayList<>(), new ArrayList<>(), DiscountType.GROUP_SIZE);
        restaurant1.addArticle(article);
        restaurantManager.addRestaurant(restaurant1);
    }

    @Given("a group order with id {int} with restaurant {string} with {int} members with {string} with id {int} in it")
    public void a_group_order_with_id_with_restaurant_with_members_with_with_id_in_it(Integer id, String restaurantName, Integer members, String userName, Integer userId) {
        Restaurant restaurant = restaurantManager.findRestaurantByName(restaurantName);
        groupOrder1 = new GroupOrderImpl(id, restaurant1, deliveryDate, deliveryLocation);

        for (int i = 0; i < members; i++) {
            groupOrder1.addMember(new RegisteredUser("NoName", i + 100, "Nopwd"));
        }

        user1 = new RegisteredUser(userName, userId, "u1p");
        groupOrder1.addMember(user1);

        order = new Order(new Date(), deliveryDate, deliveryLocation, restaurant1);
        order.addArticle(article);
        System.out.println("-- the inital price is : " + order.getTotalPrice() );

    }

    @When("group order with id {int} is validated by a user")
    public void group_order_with_id_is_validated_by_a_user(Integer id) {
        groupOrder1.addOrUpdateUserOrder(user1, order);
        groupOrder1.validateOrder();
    }

    @Then("a {int}% group size discount is applied to all individual orders")
    public void a_group_size_discount_is_applied_to_all_individual_orders(Integer discount) {
        //System.out.println(groupOrder1.getRestaurant().getDiscountType());
        groupOrder1.applyDiscount();
        //System.out.println(groupOrder1.getUsersOrders().get(user1));
        float expectedPrice = (100 - discount) / 100f * 10;
        float actualPrice = groupOrder1.getOrder(user1).getTotalPrice();

        assertEquals(expectedPrice, actualPrice, 0.01f);
        System.out.println("the price after discount is : " + actualPrice );
    }

    @Given("a group order with id {int} with restaurant {string} is validated with {int} members with {string} with id {int} in it")
    public void a_group_order_with_id_with_restaurant_is_validated_with_members(Integer id, String restaurantName, Integer members, String userName, Integer userId) {
        Restaurant restaurant = restaurantManager.findRestaurantByName(restaurantName);
        groupOrder2 = new GroupOrderImpl(id, restaurant1, deliveryDate, deliveryLocation);

        // Ajout des membres au groupe
        for (int i = 0; i < members; i++) {
            groupOrder2.addMember(new RegisteredUser("NoName", i + 100, "Nopwd"));
        }

        // Créer un utilisateur et l'ajouter au groupe
        user1 = new RegisteredUser(userName, userId, "u1p");
        groupOrder2.addMember(user1);

        // Créer une commande pour cet utilisateur
        Order order = new Order(new Date(), deliveryDate, deliveryLocation, restaurant1);
        order.addArticle(article); // Utilise l'article du restaurant

    }

    @When("group order with id {int} is validated by any user")
    public void group_order_with_id_is_validated_by_any_user(Integer id) {
        groupOrder2.addOrUpdateUserOrder(user1, order);
        groupOrder2.validateOrder();
    }

    @Then("no group size discount is applied")
    public void no_group_size_discount_is_applied() {
        groupOrder2.applyDiscount();
        float actualPrice = groupOrder2.getOrder(user1).getTotalPrice();
        assertEquals(10, actualPrice, 0.01f); // Prix sans réduction
        System.out.println("no group size discount is applied");
        System.out.println("the price still " + actualPrice);

    }
}