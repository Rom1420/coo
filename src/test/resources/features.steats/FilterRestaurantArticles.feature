Feature: Browse and filter Restaurant Menus
  Background:
    Given an internet User "Womain"

  Scenario: View the list of available restaurants
    When the user navigates to the restaurant section
    Then he should see a list of available restaurants

  Scenario: Filter articles by category
    Given the user consults restaurants
    When the user selects "Restoto"
    And the user filters by category "PLAT"
    Then they should see articles in the "PLAT" category

  Scenario: Filter articles by maximum price
    Given the user consults restaurants
    When the user selects "Restoto"
    And the user filters by maximum price 5.0
    Then they should see articles costing less than 5.0 euros

  Scenario: Filter articles by maximum preparation time
    Given the user consults restaurants
    When the user selects "Restoto"
    And the user filters by maximum preparation time 5
    Then they should see articles taking less than 5 min