package fr.unice.polytech.steats;

import fr.unice.polytech.restaurant.*;
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
    private List<Article> filteredArticles;

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
        Article burger = new Article("Burger", 8.50f, 10, Categorie.PLAT);
        Article fries = new Article("Frites", 2.50f, 5, Categorie.ACCOMPAGNEMENT);
        Article drink = new Article("Boisson", 1.50f, 2, Categorie.BOISSON);
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

    @When("the user filters by category {string}")
    public void theUserFiltersByCategory(String category) {
        Categorie categorie = Categorie.valueOf(category.toUpperCase());
        this.filteredArticles = selectedRestaurant.filterArticlesByCategory(categorie);
    }
    @Then("they should see articles in the {string} category")
    public void theyShouldSeeArticlesInTheCategory(String category) {
        assertTrue("Filtered articles should not be empty", filteredArticles != null && !filteredArticles.isEmpty());

        filteredArticles.forEach(article -> {
            System.out.println(article);
            assertTrue("Article category should match", article.getCategorie().toString().equalsIgnoreCase(category));
        });
    }

    @When("the user filters by maximum price {float}")
    public void theUserFiltersByMaximumPrice(float maxPrice) {
        this.filteredArticles = selectedRestaurant.filterArticlesByMaxPrice(maxPrice);
    }

    @Then("they should see articles costing less than {float} euros")
    public void theyShouldSeeArticlesCostingLessThan(float maxPrice) {
        assertTrue("Filtered articles should not be empty", filteredArticles != null && !filteredArticles.isEmpty());

        filteredArticles.forEach(article -> {
            System.out.println(article);
            assertTrue("Article price should be less than or equal to max price", article.getPrice() <= maxPrice);
        });
    }
}
