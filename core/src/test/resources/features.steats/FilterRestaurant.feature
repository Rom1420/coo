Feature: Filtering Restaurants

  As an Internet user,
  I want to filter restaurants by opening hours and type of food
  So that I can easily find restaurants open at a specific time with a certain type.

  Background:
    Given an internet User "Womain"

  Scenario: Filter restaurants open on Monday at 7 PM and serve Italian food
    Given the restaurant manager is initialized
    And a restaurant named "Restoto" with hours "MONDAY" from "18:00" to "22:00" and type "Italienne"
    And a restaurant named "Cassoul Boss" with hours "MONDAY" from "12:00" to "15:00" and type "Francaise"
    When the user filters restaurants open on "MONDAY" at "19:00" and of type "Italienne"
    Then the filtered restaurants should be: "Restoto"

  Scenario: Filter restaurants open on Tuesday at 12 PM and serve fast food
    Given the restaurant manager is initialized
    And a restaurant named "Burger Boss" with hours "TUESDAY" from "11:00" to "14:00" and type "FastFood"
    And a restaurant named "Salad Bar" with hours "TUESDAY" from "15:00" to "22:00" and type "Vegetarienne"
    When the user filters restaurants open on "TUESDAY" at "12:00" and of type "FastFood"
    Then the filtered restaurants should be: "Burger Boss"

  Scenario: Filter restaurants open on Wednesday at 8 PM and serve vegetarian food
    Given the restaurant manager is initialized
    And a restaurant named "Veggie Delight" with hours "WEDNESDAY" from "18:00" to "21:00" and type "Vegetarienne"
    And a restaurant named "Meat Lovers" with hours "WEDNESDAY" from "17:00" to "23:00" and type "Autre"
    When the user filters restaurants open on "WEDNESDAY" at "20:00" and of type "Vegetarienne"
    Then the filtered restaurants should be: "Veggie Delight"
