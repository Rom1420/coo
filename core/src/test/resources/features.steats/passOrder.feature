Feature: Passer une Commande

  Background:
    Given a user of name "Marcel" and with id 1234


  Scenario: create an order and confirm it
    When "Marcel" create an order "today" at "Popo" and confirms it
    Then the order is add to "Marcel"'s orders and he pays it
