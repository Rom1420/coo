package fr.unice.polytech.restaurant;

import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Menu;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;

import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RManagerUpdateTime {
    private String userName;
    @Given("an user registred as manager of name {string} with id {int}")
    public void an_user_registred_as_manager_of_name_with_id(String string, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("a restaurant of name {string} with {int} people for meal preparation")
    public void a_restaurant_of_name_with_people_for_meal_preparation(String string, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("{string} specifies a preparation time of {int} seconds per person for the {string}")
    public void specifies_a_preparation_time_of_seconds_per_person_for_the(String string, Integer int1, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("they can produce up to {int} portions of fires")
    public void they_can_produce_up_to_portions_of_fires(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("{string} updates the opening hours of {string} for 11h-15h")
    public void updates_the_opening_hours_of_for_11h_15h(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the restaurant opening hours between {int}:{int} and {int}:{int}")
    public void the_restaurant_opening_hours_between_pm_and_pm(Integer int1, Integer int2, Integer int3, Integer int4) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("they can produce up to {int} portions of fries during opening hours")
    public void they_can_produce_up_to_portions_of_fries_during_opening_hours(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Given("a restaurant of name {string} with opening hours between {int}:{int} and {int}:{int}")
    public void a_restaurant_of_name_with_opening_hours_between_and(String string, Integer int1, Integer int2, Integer int3, Integer int4) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("{string} searches {string} at {int}:{int}")
    public void searches_at(String string, String string2, Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the restaurant is closed")
    public void the_restaurant_is_closed() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("the restaurant is open")
    public void the_restaurant_is_open() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
