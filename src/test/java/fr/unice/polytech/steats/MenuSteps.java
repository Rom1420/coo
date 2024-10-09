package fr.unice.polytech.steats;

import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Menu;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MenuSteps {
    private String userName;
    private List<Restaurant> availableRestaurants;
    private Restaurant selectedRestaurant;

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

    @Given("the user consults restaurants")
    public void theUserConsultsRestaurants() {
        // Création d'un restaurant
        Article burger = new Article("Burger", 8.50f, 10);
        Article fries = new Article("Frites", 2.50f, 5);
        Article drink = new Article("Boisson", 1.50f, 2);
        List<Article> articlesInMenu = new ArrayList<>();
        articlesInMenu.add(burger);
        articlesInMenu.add(fries);
        articlesInMenu.add(drink);
        Menu classicMenu = new Menu("Menu Classique", 10.00f, articlesInMenu);
        Restaurant restaurant = new Restaurant("Restoto", articlesInMenu, List.of(classicMenu));

        RestaurantManager manager = RestaurantManager.getRestaurantManagerInstance();
        manager.addRestaurant(restaurant);

        availableRestaurants = manager.consultRestaurant();

        assertTrue("The list of available restaurants should not be empty",
                availableRestaurants != null && !availableRestaurants.isEmpty());
    }

    @When("the user selects {string}")
    public void theUserSelects(String restaurantName) {
        RestaurantManager manager = RestaurantManager.getRestaurantManagerInstance();
        this.selectedRestaurant = manager.findRestaurantByName(restaurantName);
    }

    @Then("they should see the menus for {string}")
    public void theyShouldSeeTheMenuFor(String restaurantName) {
        assertTrue("The selected restaurant should not be null", selectedRestaurant != null);

        // Affichage du menu
        List<Menu> menus = selectedRestaurant.getMenusOfRestaurant(); // Supposons que Restaurant a une méthode getMenu
        for(Menu menu : menus){
            System.out.println("\n"+menu.toString());
            assertTrue("The menu should not be empty", !menu.getArticlesInMenu().isEmpty());
        }
    }
}
