Feature: Browse Restaurant Menus


  Background:
    Given an internet User "Womain"

  Scenario: View the list of available restaurants
    When the user navigates to the restaurant section
    Then he should see a list of available restaurants