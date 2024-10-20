Feature: create group order

  Background:
    Given a registered user "Paul" with id 789

  Scenario: create a group order
    When "Paul" with id 789 creates a group order
    Then a group order with id 123 is created

  Scenario: initialise a group order
    When "Paul" chooses a restaurant, a localisation and a delivery time for the group order with id 123
    Then group order with id 123 restaurant parameter is the one given
    And  group order with id 123 localisation parameter is the one given
    And  group order with id 123 delivery time parameter is the one given


  Scenario: validate creation of group order
    When "Paul" creates his order for the group order with id 123
    Then the order restaurant parameter is the one given for group order with id 123
    And the order localisation parameter is the one given for group order with id 123
    And the order delivery time parameter is the one given for group order with id 123
    And the group order with id 123 is added to the list of group orders