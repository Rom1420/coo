Feature: Restaurant managers can update their opening hours and menu offering

  Background:
    Given an user registered as manager of name "Marcel" with id 22
    And a restaurant of name "McDo" with 4 people for meal preparation
    And a article of name "fries" with a preparation time of 1 min
    And an user registered of name "Patrick" with id 45
    And a restaurant of name "Pizzz" with opening hours between 12:00 and 14:00

  Scenario: update opening hour
    When "Marcel" updates the opening hours of "McDo" for 11h-15h
    Then the restaurant opening hours between 11:00 and 15:00

  Scenario: search a restaurant out of its opening hours
    When "Patrick" searches "Pizzz" at 11:00
    Then the restaurant "Pizzz" is closed

  Scenario: search a restaurant during its opening hours
    When "Patrick" searches "Pizzz" at 13:00
    Then the restaurant "Pizzz" is open

    #---------------------------------------------------------------------------------#
  Scenario: Specification of necessary preparation time
    When "Marcel" specifies a preparation time of 1 minute per person for the "fries"
    Then they can produce up to 480 portions of "fries" during 2 hours