@expCommInter
Feature: Cognition and learning - Academic learning and employability - Needs and Provisions - [TC]

  Scenario: cognitionAleProvisions1- Add Needs and Provision from a blank assessment1
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Academic learning and employability need
    When user selects Qualifications and employability "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Academic learning and employability provision
    And under Academic learning > "Specialist professional involvement" - user selects "Specialist professional advice, supervision or support" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Academic learning and employability Provisions displayed on Summary

  Scenario: cognitionAleProvisions2- Add Needs and Provision from a blank assessment2
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Academic learning and employability need
    When user selects Qualifications and employability "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Academic learning and employability provision
    When under Academic learning > "Group & Individual Intervention" - user selects "Generalisation and reinforcement" option "1"
    And under Academic learning > "Group & Individual Intervention" - user selects "1:1 Intervention" option "1"
    And under Academic learning > "Group & Individual Intervention" - user selects "Small group Intervention" option "1"
    And under Academic learning > "Group & Individual Intervention" - user selects "Post-16 tutoring" option "1"
    And under Academic learning > "Group & Individual Intervention" - user selects "Post-16 exam support" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Academic learning and employability Provisions displayed on Summary

  Scenario: cognitionAleProvisions3- Add Needs and Provision from a blank assessment3
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Academic learning and employability need
    When user selects Qualifications and employability "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Academic learning and employability provision
    When under Academic learning > "Additional Staff support" - user selects "General In class learning support pre-16" option "1"
    And under Academic learning > "Additional Staff support" - user selects "General In lesson learning support post-16" option "1"
    And under Academic learning > "Additional Staff support" - user selects "In-Class staff support for social interaction" option "1"
    And under Academic learning > "Additional Staff support" - user selects "In-Class staff support for social, emotional needs and development" option "1"
    And under Academic learning > "Additional Staff support" - user selects "Post-16 support" option "1"
    And under Academic learning > "Additional Staff support" - user selects "Life skills/functional skills provision" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Academic learning and employability Provisions displayed on Summary

  Scenario: cognitionAleProvisions4- Add Needs and Provision from a blank assessment4
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Academic learning and employability need
    When user selects Qualifications and employability "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Academic learning and employability provision
    When under Academic learning > "Family Support" - user selects "Family Support" option "1"
    And under Academic learning > "Family Support" - user selects "Family Intervention (pre-16)" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Academic learning and employability Provisions displayed on Summary

  Scenario: cognitionAleProvisions5- Add Needs and Provision from a blank assessment5
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Academic learning and employability need
    When user selects Qualifications and employability "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Academic learning and employability provision
    When under Academic learning > "Support in non-teaching/unstructured times" - user selects "lunchtime support" option "1"
    When under Academic learning > "Support in non-teaching/unstructured times" - user selects "break time support" option "1"
    When under Academic learning > "Support in non-teaching/unstructured times" - user selects "support over transitions in current setting (pre-16)" option "1"
    When under Academic learning > "Support in non-teaching/unstructured times" - user selects "Support over transitions post-16" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Academic learning and employability Provisions displayed on Summary

  Scenario: cognitionAleProvisions6- Add Needs and Provision from a blank assessment6
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Academic learning and employability need
    When user selects Qualifications and employability "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Academic learning and employability provision
    When under Academic learning > "Additional and Different provision" - user selects "Training" option "1"
    When under Academic learning > "Additional and Different provision" - user selects "Post-16 specific provision" option "1"
    When under Academic learning > "Additional and Different provision" - user selects "Educational/ communication resources" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Academic learning and employability Provisions displayed on Summary