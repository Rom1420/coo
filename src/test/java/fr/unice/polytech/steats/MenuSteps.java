package fr.unice.polytech.steats;

import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MenuSteps {
    private String userName;
    private List<Restaurant> availableRestaurants;
    @Given("an internet User {string}")
    public void anInternetUser(String name) {
        this.userName = name;
    }
    @When("the user navigates to the restaurant section")
    public void userNavigatesToRestaurantSection() {
        RestaurantManager manager = RestaurantManager.getRestaurantManagerInstance();
        Restaurant italianRestaurant = new Restaurant("Italian Bistro");
        Restaurant chineseRestaurant = new Restaurant("Dragon Wok");

        manager.addRestaurant(italianRestaurant);
        manager.addRestaurant(chineseRestaurant);
        availableRestaurants = manager.consultRestaurant();
    }

    @Then("he should see a list of available restaurants")
    public void userShouldSeeListOfRestaurants() {
        assertTrue("The list of available restaurants should not be empty",
                availableRestaurants != null && !availableRestaurants.isEmpty());

        availableRestaurants.forEach(restaurant -> System.out.println(restaurant));
    }
}
