Feature: pay an order

  Background:
    Given a user of name "Marcel" and with id 1234 and an order

  Scenario:
    When the user have an order and want to pay it
    Then proceed to payment