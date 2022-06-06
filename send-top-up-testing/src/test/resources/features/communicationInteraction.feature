@expCommInter
Feature: Communication and Interaction - Expressive Language - Needs and Provisions - [TC34820-TC34824] [TC34517]

  Scenario: Log in, view assessment and add Need - an example of micro steps
    Given I am logged in as an approved user
    And user clicks View or edit assessment
    And user clicks link for Expressive language and communication need
    When user clicks Style of communication accordion
    And user selects Style of communication "Level A"
    And user clicks the Save button
    Then the Expressive Communication Level of need is identified as "A"
    And user clicks the Back button
    And selected need level under Communication and interaction on the Summary is "Level A"

  Scenario: Provision is deleted with Remove link
    Given assessment wih Communication and interaction Need "Level C" selected
    And user clicks link for Expressive language and communication provision
    And under Specialist Professional Provision selects "Specialist professional advice and support 1" option "2"
    And under Additional Staff Support selects "General In lesson learning support post-16" option "1"
    And user clicks the Save button
    And user clicks the Back button
    When I click the Remove link for Expressive language and communication provision item "1"
    And I click OK in the confirmation popup
    Then all saved Expressive language and communication Provisions displayed on Summary

  Scenario: Add Needs and Provision from a blank assessment
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Expressive language and communication need
    When user clicks Style of communication accordion
    And user selects Style of communication "Level A"
    And user clicks the Save button
    Then the Expressive Communication Level of need is identified as "A"
    And user clicks the Back button
    And user clicks link for Expressive language and communication provision
    When under Specialist Professional Provision selects "Specialist professional advice and support 1" option "2"
    And under Additional Staff Support selects "General In lesson learning support post-16" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Expressive language and communication Provisions displayed on Summary

  Scenario: Additional Staff drop down provision being flexed
    Given I am logged in as an approved user
    And user clicks View or edit assessment
    And user clicks link for Expressive language and communication need
    When user clicks Style of communication accordion
    And user selects Style of communication "Level A"
    And user clicks the Save button
    Then the Expressive Communication Level of need is identified as "A"
    And user clicks the Back button
    And user clicks link for Expressive language and communication provision
    When under Additional Staff Support selects "General In lesson learning support post-16" option "2"
    And user clicks the Save button
    And user clicks the Back button
    Then provision is displayed under Communication and interaction on the Summary containing "General In lesson learning support post-16"

  Scenario: Provision is deleted when corresponding Needs level is cleared
    Given assessment wih Communication and interaction Need "Level C" selected
    And selected need level under Communication and interaction on the Summary is "Level C"
    And user clicks link for Expressive language and communication provision
    And under Specialist Professional Provision selects "Specialist professional advice and support 1" option "2"
    And user clicks the Save button
    And user clicks the Back button
    And provision is displayed under Communication and interaction on the Summary containing "Specialist professional advice and support 1"
    And user clicks link for Expressive language and communication need
    And user clicks Style of communication accordion
    When user clicks Clear selection button
    And user clicks the Save button
    And I click OK in the confirmation popup
    Then the Expressive Communication level of need has been cleared
    And user clicks the Back button
    And selected need level under Communication and interaction on the Summary has been cleared
    And the Expressive language and communication provision is removed
    And Communication and interaction Provisions links are disabled

  Scenario: Provision is deleted when corresponding Needs level is changed - [TC34820 -> TC34823]
    Given assessment wih Communication and interaction Need "Level C" selected
    And selected need level under Communication and interaction on the Summary is "Level C"
    And user clicks link for Expressive language and communication provision
    And under Specialist Professional Provision selects "Specialist professional advice and support 1" option "2"
    And user clicks the Save button
    And user clicks the Back button
    And provision is displayed under Communication and interaction on the Summary containing "Specialist professional advice and support 1"
    And user clicks link for Expressive language and communication need
    And user clicks Style of communication accordion
    When user selects Style of communication "Level A"
    And user clicks the Save button
    And I click OK in the confirmation popup
    Then the Expressive Communication Level of need is identified as "A"
    And user clicks the Back button
    And the Expressive language and communication provision is removed

  Scenario: Provision is not deleted when corresponding Needs level is changed then popup cancelled - [TC34824]
    Given assessment wih Communication and interaction Need "Level C" selected
    And selected need level under Communication and interaction on the Summary is "Level C"
    And user clicks link for Expressive language and communication provision
    And under Specialist Professional Provision selects "Specialist professional advice and support 1" option "2"
    And user clicks the Save button
    And user clicks the Back button
    And provision is displayed under Communication and interaction on the Summary containing "Specialist professional advice and support 1"
    And user clicks link for Expressive language and communication need
    And user clicks Style of communication accordion
    When user selects Style of communication "Level A"
    And user clicks the Save button
    And I click Cancel in the confirmation popup
    Then the Expressive Communication Level of need is identified as "C"
    And user clicks the Back button
    And provision is displayed under Communication and interaction on the Summary containing "Specialist professional advice and support 1"

  Scenario: Remove a provision with Expressive language and communication sub-type - TC34517
    Given assessment wih Communication and interaction Need "Level C" selected
    And user clicks link for Expressive language and communication provision
    And under Specialist Professional Provision selects "Specialist professional advice and support 1" option "2"
    And user clicks the Save button
    And user clicks the Back button
    And all saved Expressive language and communication Provisions displayed on Summary
    When I click the Remove link for Expressive language and communication provision item "1"
    And I click OK in the confirmation popup
    Then the Expressive language and communication provision is removed
