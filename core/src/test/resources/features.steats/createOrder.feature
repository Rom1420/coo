Feature: Create an order

  Background:
    Given a user of name "Marcel" and with student id 1234


  Scenario: create an order
    When "Marcel" create an order "today" at "Popo"
    Then the order is add to "Marcel"'s orders
