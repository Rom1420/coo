Feature: create group order

  Background:
    Given a registered user of name Paul with id 789

  Scenario: create a group order
    When "Paul" wants to create a group order
    Then id 1234 is created for the group order
    And "Paul" chooses a localisation and a delivery time for the group order with id 123
    And the group with id 1234 is created


