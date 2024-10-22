Feature: Group Size Discount Application in Restaurant

  Background:
    Given restaurant "RestoA" offering a group size discount
    And a group order with id 1 with restaurant "RestoA" with 9 members with "Paul" with id 7 in it
    And a group order with id 2 with restaurant "RestoA" is validated with 5 members with "Paul" with id 7 in it

  Scenario: Group size discount applied in "RestoA"
    When group order with id 1 is validated by a user
    Then a 10% group size discount is applied to all individual orders

  Scenario: No group size discount for less than 10 users in "RestoA"
    When group order with id 2 is validated by any user
    Then no group size discount is applied