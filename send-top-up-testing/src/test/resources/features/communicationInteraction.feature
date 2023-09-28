@expCommInter
Feature: Communication and Interaction - Expressive Language - Needs and Provisions - [TC34820-TC34824] [TC34517]

  Scenario: communicationInteraction1- Log in, view assessment and add Need - confirm Need selected is correct
    Given I am logged in as an approved user
    And user clicks View or edit assessment
    And user clicks link for Expressive language and communication need
    And page title contains the text "Expressive language and communication"
    When user selects Style of communication "Block A"
    And user clicks the Save button
    Then the Expressive Communication Level of need is identified as "You may select provisions"
    And user clicks the Back button
    And selected need level under Communication and interaction on the Summary is "You may select provisions"


  Scenario: communicationInteraction2- Provision is deleted with Remove link
    Given assessment with Communication and interaction Need "Block C" selected
    And user clicks link for Expressive language and communication provision
    And under Expressive language > "Specialist professional involvement" - user selects "Specialist professional advice, supervision or support" option "2"
    And under Expressive language > "Additional Staff support" - user selects "General In lesson learning support post-16" option "1"
    And user clicks the Save button
    And user clicks the Back button
    And all saved Expressive language and communication Provisions displayed on Summary
    When I click the Remove link for Expressive language and communication provision item "1"
    And I click OK in the confirmation popup
    Then all saved Expressive language and communication Provisions displayed on Summary

  Scenario: communicationInteraction3- Add Needs and Provision from a blank assessment
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Expressive language and communication need
    And page title contains the text "Expressive language and communication"
    When user selects Style of communication "Block A"
    And user clicks the Save button
    Then the Expressive Communication Level of need is identified as "You may select provisions"
    And user clicks the Back button
    And user clicks link for Expressive language and communication provision
    And page title contains the text "Expressive language and communication"
    And under Expressive language > "Specialist professional involvement" - user selects "Specialist professional advice, supervision or support" option "2"
    And under Expressive language > "Additional Staff support" - user selects "General In lesson learning support post-16" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Expressive language and communication Provisions displayed on Summary

  Scenario: communicationInteraction4- Additional Staff drop down provision being flexed
    Given I am logged in as an approved user
    And user clicks View or edit assessment
    And user clicks link for Expressive language and communication need
    When user selects Style of communication "Block A"
    And user clicks the Save button
    Then the Expressive Communication Level of need is identified as "You may select provisions"
    And user clicks the Back button
    And user clicks link for Expressive language and communication provision
    When under Expressive language > "Additional Staff support" - user selects "General In lesson learning support post-16" option "2"
    And user clicks the Save button
    And user clicks the Back button
    Then provision is displayed under Communication and interaction on the Summary containing "General In lesson learning support post-16"

  Scenario: communicationInteraction5- Provision is deleted when corresponding Needs level is cleared
    Given assessment with Communication and interaction Need "Block C" selected
    And selected need level under Communication and interaction on the Summary is "You may select provisions"
    And user clicks link for Expressive language and communication provision
    And under Expressive language > "Specialist professional involvement" - user selects "Specialist professional advice, supervision or support" option "2"
    And user clicks the Save button
    And user clicks the Back button
    And provision is displayed under Communication and interaction on the Summary containing "Specialist professional advice, supervision or support"
    And user clicks link for Expressive language and communication need
    When user clicks Clear selection button
    And user clicks the Save button
    And I click OK in the confirmation popup
    Then the Expressive Communication level of need has been cleared
    And user clicks the Back button
    And selected need level under Communication and interaction on the Summary has been cleared
    And the Expressive language and communication provision is removed
    And Communication and interaction Provisions links are disabled

  Scenario: communicationInteraction6- Provision is deleted when corresponding Needs level is changed - [TC34820 -> TC34823]
    Given assessment with Communication and interaction Need "Block C" selected
    And selected need level under Communication and interaction on the Summary is "You may select provisions"
    And user clicks link for Expressive language and communication provision
    And under Expressive language > "Specialist professional involvement" - user selects "Specialist professional advice, supervision or support" option "2"
    And user clicks the Save button
    And user clicks the Back button
    And provision is displayed under Communication and interaction on the Summary containing "Specialist professional advice, supervision or support"
    And user clicks link for Expressive language and communication need
    When user selects Style of communication "Block A"
    And user clicks the Save button
    And I click OK in the confirmation popup
    Then the Expressive Communication Level of need is identified as "You may select provisions"
    And user clicks the Back button
    And the Expressive language and communication provision is removed

  Scenario: communicationInteraction7- Provision is not deleted when corresponding Needs level is changed then popup cancelled - [TC34824]
    Given assessment with Communication and interaction Need "Block C" selected
    And selected need level under Communication and interaction on the Summary is "You may select provisions"
    And user clicks link for Expressive language and communication provision
    And under Expressive language > "Specialist professional involvement" - user selects "Specialist professional advice, supervision or support" option "2"
    And user clicks the Save button
    And user clicks the Back button
    And provision is displayed under Communication and interaction on the Summary containing "Specialist professional advice, supervision or support"
    And user clicks link for Expressive language and communication need
    When user selects Style of communication "Block A"
    And user clicks the Save button
    And I click Cancel in the confirmation popup
    Then the Expressive Communication Level of need is identified as "You may select provisions"
    And user clicks the Back button
    And provision is displayed under Communication and interaction on the Summary containing "Specialist professional advice, supervision or support"

  Scenario: communicationInteraction8- Remove a provision with Expressive language and communication sub-type - TC34517
    Given assessment with Communication and interaction Need "Block C" selected
    And user clicks link for Expressive language and communication provision
    And under Expressive language > "Specialist professional involvement" - user selects "Specialist professional advice, supervision or support" option "2"
    And user clicks the Save button
    And user clicks the Back button
    And all saved Expressive language and communication Provisions displayed on Summary
    When I click the Remove link for Expressive language and communication provision item "1"
    And I click OK in the confirmation popup
    Then the Expressive language and communication provision is removed
