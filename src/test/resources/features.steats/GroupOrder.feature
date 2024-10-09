Feature: manage group order

  Background:
    Given a group order with id 1234 and and already "Daniel" and "Paul" in it
      And a registered user of name "Robert" with id 123
      And a registered user of name "Daniel" with id 456
      And a registered user of name "Paul" with id 789

  Scenario: add a member
    When "Robert" enters the group order id 1234
    Then "Robert" joins the group order with id 1234
      And There is 3 members in the group order with id 1234
      And "Robert" creates his individual order for the group order with id 1234
      And The localisation and delivery time of his individual order is predefined by the group order with id 1234