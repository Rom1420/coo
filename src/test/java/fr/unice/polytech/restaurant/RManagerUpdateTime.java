package fr.unice.polytech.restaurant;

import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Menu;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.unice.polytech.user.UserAccount;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class RManagerUpdateTime {
    private UserAccount user,user2;
    private Article friesOfMcdo;
    private Restaurant restaurant, restaurant2;
    private RestaurantManager manager;
    private List<Restaurant> openRestaurant;
    @Given("an user registered as manager of name {string} with id {int}")
    public void an_user_registered_as_manager_of_name_with_id(String string, Integer int1) {
        user = new UserAccount(string, int1, "1111");
    }

    @Given("a restaurant of name {string} with {int} people for meal preparation")
    public void a_restaurant_of_name_with_people_for_meal_preparation(String string, Integer int1) {
        restaurant =new Restaurant(string,int1);
    }
    @Given("a article of name {string} with a preparation time of {int} min")
    public void aArticleOfNameWithAPreparationTimeOfMin(String arg0, int arg1) {
        friesOfMcdo = new Article(arg0,1,arg1);
        restaurant.addArticle(friesOfMcdo);
    }
    @Given("an user registered of name {string} with id {int}")
    public void an_user_registered_of_name_with_id(String string, Integer int1) {
        user2 = new UserAccount(string, int1, "2222");
    }

    @When("{string} specifies a preparation time of {int} seconds per person for the {string}")
    public void specifies_a_preparation_time_of_seconds_per_person_for_the(String string, Integer int1, String string2) {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }


    @Then("they can produce up to {int} portions of fires")
    public void they_can_produce_up_to_portions_of_fires(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }

    @When("{string} updates the opening hours of {string} for 11h-15h")
    public void updates_the_opening_hours_of_for_11h_15h(String string, String string2) {
        DayOfWeek day= DayOfWeek.MONDAY;
        restaurant.setSchedules(day, LocalTime.of(11, 0),LocalTime.of(15, 0));
    }

    @Then("the restaurant opening hours between {int}:{int} and {int}:{int}")
    public void the_restaurant_opening_hours_between_pm_and_pm(Integer int1, Integer int2, Integer int3, Integer int4) {
        LocalTime ouverture = LocalTime.of(int1, int2);
        LocalTime fermeture = LocalTime.of(int3, int4);
        Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> schedules = restaurant.getWeeklySchedules();
        Map.Entry<LocalTime, LocalTime> mondaySchedules = schedules.get(DayOfWeek.MONDAY);
        assertEquals(ouverture, mondaySchedules.getKey());
        assertEquals(fermeture, mondaySchedules.getValue());

    }

    @Then("they can produce up to {int} portions of fries during opening hours")
    public void they_can_produce_up_to_portions_of_fries_during_opening_hours(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
    }

    @Given("a restaurant of name {string} with opening hours between {int}:{int} and {int}:{int}")
    public void a_restaurant_of_name_with_opening_hours_between_and(String string, Integer int1, Integer int2, Integer int3, Integer int4) {
        manager =RestaurantManager.getRestaurantManagerInstance();
        restaurant2 =new Restaurant(string);
        DayOfWeek day= DayOfWeek.MONDAY;
        restaurant2.setSchedules(day, LocalTime.of(int1, int2),LocalTime.of(int3, int4));
        manager.addRestaurant(restaurant2);
    }

    @When("{string} searches {string} at {int}:{int}")
    public void searches_at(String string, String string2, Integer int1, Integer int2) {
        openRestaurant=manager.searchOpenRestaurant(DayOfWeek.MONDAY,LocalTime.of(int1,int2));
    }

    @Then("the restaurant {string} is closed")
    public void the_restaurant_is_closed(String string) {
        boolean isPresentInList= false;
        for (Restaurant restaurant : openRestaurant) {
            if (restaurant.getName().equalsIgnoreCase(string)) {
                isPresentInList = true;
            }
        }
        assertFalse(isPresentInList);
    }
    @Then("the restaurant {string} is open")
    public void the_restaurant_is_open(String string) {
        boolean isPresentInList= false;
        for (Restaurant restaurant : openRestaurant) {
            if (restaurant.getName().equalsIgnoreCase(string)) {
                isPresentInList = true;
            }
        }
        assertTrue(isPresentInList);
    }

    @When("{string} specifies a preparation time of {int} minute per person for the {string}")
    public void specifies_a_preparation_time_of_seconds_per_person_for_the(String string, Integer int1, String string2) {
        for(Article art : restaurant.getArticlesSimples()){
            if(art.getName().equals(string2)){
                art.setTimeRequiredForPreparation(int1);
            }
        }
    }
    @Then("they can produce up to {int} portions of {string} during {int} hours")
    public void they_can_produce_up_to_portions_of_fries_during_opening_hours(Integer int1,String string1,Integer int2) {
        int nbofsecondes=60*60*int2;
        int res =(int)restaurant.calculateNbOfArticleCanBePrepared(friesOfMcdo,nbofsecondes);
        assertEquals((int)int1,res);
    }


}
