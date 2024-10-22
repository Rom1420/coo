Feature: Loyalty Discount Strategy

  Background:
    Given restaurant "RestoC" offering a loyalty discount
    And a group order with id 4 with restaurant "RestoC" is validated with 2 members with "Daniel" with id 4 and "Jimmy" with id 10 in it
    And a registered user "Daniel" with id 4 who has already placed 5 orders in Restaurant "RestoC"

  Scenario: Loyalty discount applied in "RestoC"
    When "Daniel" and "Jimmy" place orders
    Then group order with id 4 is validate
    And a 10% loyalty discount is applied to "Daniel"'s order
    And no discount applied to "Jimmy"'s order
