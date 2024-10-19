package fr.unice.polytech.steats;

import fr.unice.polytech.restaurant.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilteringRestaurantsSteps {
    private RestaurantManager restaurantManager;
    private List<Restaurant> filteredRestaurants;

    @Given("the restaurant manager is initialized")
    public void initRestaurantManager() {
        restaurantManager = RestaurantManager.getRestaurantManagerInstance();
    }

    @Given("a restaurant named {string} with hours {string} from {string} to {string} and type {string}")
    public void aRestaurantNamedWithHours(String name, String day, String openingTime, String closingTime, String type) {
        Restaurant restaurant = new Restaurant(name);

        type = type.trim().toUpperCase().replace(" ", "");
        TypeCuisine cuisineType;

        try {
            cuisineType = TypeCuisine.valueOf(type);
        } catch (IllegalArgumentException e) {
            // Si le type n'est pas valide, attribuer AUTRE
            cuisineType = TypeCuisine.AUTRE;
            System.out.println("Type de cuisine non valide, attribué à AUTRE : " + type);
        }

        restaurant.setTypeCuisine(cuisineType);

        DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
        LocalTime opening = LocalTime.parse(openingTime);
        LocalTime closing = LocalTime.parse(closingTime);
        restaurant.setSchedules(dayOfWeek, opening, closing);

        Article article = new Article("Cassoulet", 10f, 10, Categorie.PLAT);
        restaurant.addArticle(article);
        restaurantManager.addRestaurant(restaurant);
    }

    @When("the user filters restaurants open on {string} at {string} and of type {string}")
    public void theUserFiltersRestaurantsOpenOnAtAndOfType(String day, String time, String foodType) {
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
        LocalTime hour = LocalTime.parse(time);
        filteredRestaurants = restaurantManager.searchOpenRestaurant(dayOfWeek, hour);
        filteredRestaurants = restaurantManager.filterRestaurantsByCuisineType(TypeCuisine.valueOf(foodType.toUpperCase()), filteredRestaurants);
    }

    @Then("the filtered restaurants should be: {string}")
    public void theFilteredRestaurantsShouldBe(String expectedNames) {
        String[] expectedRestaurantNames = expectedNames.split(", ");
        assertEquals(expectedRestaurantNames.length, filteredRestaurants.size());
        for (String expectedName : expectedRestaurantNames) {
            assertTrue(filteredRestaurants.stream().anyMatch(restaurant -> restaurant.getName().equals(expectedName)));
        }
    }
}
