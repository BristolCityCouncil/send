@validation
Feature: Summary - Start new assessment - TS_32291

  Scenario: basicChecks1- Create assessment without a school
    Given I am logged in as an approved user
    And user enters UPN "A333222222222"
    When user clicks Create new assessment
    Then the following school name error is displayed "You need to tell us the name of the school"

  Scenario: basicChecks2- Entering UPN with invalid characters - TC32667
    Given I am logged in as an approved user
    When user enters UPN "(!?£$%&*)"
    When user clicks Create new assessment
    Then the following invalid UPN error is displayed "You need to enter a valid UPN"

  Scenario: basicChecks3- Entering UPN that already exists - TC32666
    Given I am logged in as an approved user
    And user enters UPN "A991111111119"
    And user enters school name of "Red Brick School"
    When user clicks Create new assessment
    Then the following UPN error is displayed "There is already an assessment for this UPN"


  Scenario: basicChecks4- User can delete an assessment
    Given I am logged in as an approved user
    And the following UPN exists: "A991111111119"
    And user clicks Delete link under assessment
    And popup confirmation message displays corresponding UPN: "A991111111119"
    And I click OK in the confirmation popup
    Then assessment is removed with UPN: "A991111111119"

  Scenario: basicChecks5- Entering UPN with no capital letter - TC32668
    Given I am logged in as an approved user
    And user enters UPN "9991111111119"
    And user enters school name of "Red Brick School"
    When user clicks Create new assessment
    Then the following invalid UPN error is displayed "You need to enter a valid UPN"