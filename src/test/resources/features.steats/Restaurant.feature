Feature: Browse Restaurant Menus


  Background:
    Given an internet User "Womain"

  Scenario: View the list of available restaurants
    When the user navigates to the restaurant section
    Then he should see a list of available restaurants

  Scenario: View the menu of a specific restaurant
    Given the user consults restaurants
    When the user selects "Restoto"
    Then they should see the menus for "Restoto"