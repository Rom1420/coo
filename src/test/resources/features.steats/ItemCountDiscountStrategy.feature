Feature: Item Count Discount Strategy

  Background:
    Given restaurant "RestoB" offering an item count discount
    And a group order with id 3 with restaurant "RestoB" is validated with 2 members with "Jimmy" with id 10 and "Paul" in it

  Scenario: Item count discount applied in "RestoB"
    When "Jimmy" ordered 5 items and "Paul" ordered 2 items
    Then group order with id 3 is validated
    And a 5% item count discount is applied to "Jimmy"'s order
    And no discount is applied to "Paul"'s order
