Feature: join group order

  Background:
    Given a group order with id 456 not already validated
      And a group order with id 789 already validated
      And a registered user of name "Jimmy" who created the group order with id 456
      And a registered user of name "John" who created the group order with id 789 and validated it
      And a registered user named "James" with id 99

  Scenario: join group order
    When "James" enters the group id 456
    Then "James" creates his personal order for the group order with id 456
      And The restaurant, the localisation and delivery time of his individual order is predefined by the group order with id 456
      And There is 2 members in group order with id 456

  Scenario: join a validated group order
    When "James" enters the group id 789 of an validated group
    Then "James" receives an error message which saying that group order is already validated
      And "James" can't join the group with id 456

  Scenario: join an none existant group
    When "James" enters the group id 12345 of an none existant group
    Then "James" receives an error message which saying that group order does not exist
      And "James" don't join a group