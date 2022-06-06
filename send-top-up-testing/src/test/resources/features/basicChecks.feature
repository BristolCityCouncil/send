@validation
Feature: SEND - Needs Assessment - Validation for UPN input

  Scenario: Create assessment without a school
    Given I am logged in as an approved user
    And user enters UPN "A333222222222"
    When user clicks Create new assessment
    Then the following school name error is displayed "You need to tell us the name of the school"

  Scenario: Entering UPN with invalid characters - TC32667
    Given I am logged in as an approved user
    When user enters UPN "(!?£$%&*)"
    When user clicks Create new assessment
    Then the following invalid UPN error is displayed "You need to enter a valid UPN"

  Scenario: Entering UPN that already exists - TC32666
    Given I am logged in as an approved user
    And user enters UPN "A991111111119"
    And user enters school name of "Red Brick School"
    When user clicks Create new assessment
    Then the following UPN error is displayed "There is already an assessment for this UPN"