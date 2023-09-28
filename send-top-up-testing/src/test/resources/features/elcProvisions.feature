@expCommInter
Feature: Communication and Interaction - Expressive Language - Needs and Provisions - [TC34820-TC34824] [TC34517]

  Scenario: elcProvisions1- Provision is deleted with Remove link
    Given assessment with Communication and interaction Need "Block C" selected
    And user clicks link for Expressive language and communication provision
    And under Expressive language > "Specialist professional involvement" - user selects "Specialist professional advice, supervision or support" option "1"
    And under Expressive language > "Group & Individual Intervention" - user selects "Generalisation and reinforcement" option "1"
    And user clicks the Save button
    And user clicks the Back button
    And all saved Expressive language and communication Provisions displayed on Summary
    When I click the Remove link for Expressive language and communication provision item "1"
    And I click OK in the confirmation popup
    Then all saved Expressive language and communication Provisions displayed on Summary

  Scenario: elcProvisions2- Add Needs and Provision from a blank assessment1
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Expressive language and communication need
    When user selects Style of communication "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Expressive language and communication provision
    When under Expressive language > "Group & Individual Intervention" - user selects "Generalisation and reinforcement" option "1"
    And under Expressive language > "Group & Individual Intervention" - user selects "1:1 Intervention" option "1"
    And under Expressive language > "Group & Individual Intervention" - user selects "Small group Intervention" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Expressive language and communication Provisions displayed on Summary

  Scenario: elcProvisions3- Add Needs and Provision from a blank assessment2
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Expressive language and communication need
    When user selects Style of communication "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Expressive language and communication provision
    When under Expressive language > "Additional Staff support" - user selects "General In class learning support pre-16" option "1"
    And under Expressive language > "Additional Staff support" - user selects "General In lesson learning support post-16" option "1"
    And under Expressive language > "Additional Staff support" - user selects "In-Class staff support for social interaction" option "1"
    And under Expressive language > "Additional Staff support" - user selects "In-Class staff support for social, emotional needs and development" option "1"
    And under Expressive language > "Additional Staff support" - user selects "Post-16 support" option "1"
    And under Expressive language > "Additional Staff support" - user selects "Life skills/functional skills provision" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Expressive language and communication Provisions displayed on Summary


  Scenario: elcProvisions4- Add Needs and Provision from a blank assessment3
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Expressive language and communication need
    When user selects Style of communication "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Expressive language and communication provision
    When under Expressive language > "Support in non-teaching/unstructured times" - user selects "lunchtime support" option "1"
    When under Expressive language > "Support in non-teaching/unstructured times" - user selects "break time support" option "1"
    When under Expressive language > "Support in non-teaching/unstructured times" - user selects "support over transitions in current setting (pre-16)" option "1"
    When under Expressive language > "Support in non-teaching/unstructured times" - user selects "Support over transitions post-16" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Expressive language and communication Provisions displayed on Summary

  Scenario: elcProvisions5- Add Needs and Provision from a blank assessment4
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Expressive language and communication need
    When user selects Style of communication "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Expressive language and communication provision
    When under Expressive language > "Additional and Different provision" - user selects "Training" option "1"
    When under Expressive language > "Additional and Different provision" - user selects "Post-16 specific provision" option "1"
    When under Expressive language > "Additional and Different provision" - user selects "Educational/ communication resources" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Expressive language and communication Provisions displayed on Summary

  Scenario: elcProvisions6- Add Needs and Provision from a blank assessment5
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Expressive language and communication need
    When user selects Style of communication "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Expressive language and communication provision
    When under Expressive language > "Family Support" - user selects "Family Support" option "1"
    And under Expressive language > "Family Support" - user selects "Family Intervention (pre-16)" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Expressive language and communication Provisions displayed on Summary