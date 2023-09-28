@expCommInter
Feature: Behaviour, emotional and mental health - provision list - provision is selectable when need level is chosen [Test Suite 32955]

  Scenario: bemProvisions1- Add Needs and Provision from a blank assessment1
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Behaviour, emotional and mental health needs
    And user selects Behaviour, emotional and mental health needs - "Anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Behaviour, emotional and mental health provision
    When under Behaviour emotional > "Specialist professional involvement" - user selects "Specialist professional advice, supervision or support" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Behaviour, emotional and mental health Provisions displayed on Summary

  Scenario: bemProvisions2- Add Needs and Provision from a blank assessment2
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Behaviour, emotional and mental health needs
    And user selects Behaviour, emotional and mental health needs - "Anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Behaviour, emotional and mental health provision
    When under Behaviour emotional > "Group & Individual Intervention" - user selects "Intervention for social and emotional learning" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Behaviour, emotional and mental health Provisions displayed on Summary

  Scenario: suiProvisions3- Add Needs and Provision from a blank assessment3
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Behaviour, emotional and mental health needs
    And user selects Behaviour, emotional and mental health needs - "Anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Behaviour, emotional and mental health provision
    When under Behaviour emotional > "Additional Staff support" - user selects "In class Staff support for physical needs" option "1"
    And under Behaviour emotional > "Additional Staff support" - user selects "Check-in support" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Behaviour, emotional and mental health Provisions displayed on Summary

  Scenario: suiProvisions4- Add Needs and Provision from a blank assessment4
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Behaviour, emotional and mental health needs
    And user selects Behaviour, emotional and mental health needs - "Anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Behaviour, emotional and mental health provision
    When under Behaviour emotional > "family Support" - user selects "Family Support" option "1"
    And under Behaviour emotional > "family Support" - user selects "Family Intervention (pre-16)" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Behaviour, emotional and mental health Provisions displayed on Summary

  Scenario: suiProvisions5- Add Needs and Provision from a blank assessment5
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Behaviour, emotional and mental health needs
    And user selects Behaviour, emotional and mental health needs - "Anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Behaviour, emotional and mental health provision
    When under Behaviour emotional > "Support in non-teaching/unstructured times" - user selects "lunchtime support" option "1"
    And under Behaviour emotional > "Support in non-teaching/unstructured times" - user selects "break time support" option "1"
    And under Behaviour emotional > "Support in non-teaching/unstructured times" - user selects "support over transitions in current setting (pre-16)" option "1"
    And under Behaviour emotional > "Support in non-teaching/unstructured times" - user selects "Support over transitions post-16" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Behaviour, emotional and mental health Provisions displayed on Summary

  Scenario: suiProvisions6- Add Needs and Provision from a blank assessment6
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Behaviour, emotional and mental health needs
    And user selects Behaviour, emotional and mental health needs - "Anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Behaviour, emotional and mental health provision
    When under Social understanding and interaction > "Safety" - user selects "Physical Intervention" option "1"
    And under Social understanding and interaction > "Safety" - user selects "Safety off site pre-16" option "1"
    And under Social understanding and interaction > "Safety" - user selects "Safety off site post-16" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Behaviour, emotional and mental health Provisions displayed on Summary

  Scenario: suiProvisions7- Add Needs and Provision from a blank assessment7
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Behaviour, emotional and mental health needs
    And user selects Behaviour, emotional and mental health needs - "Anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Behaviour, emotional and mental health provision
    When under Social understanding and interaction > "Health care" - user selects "Support at meal times" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Behaviour, emotional and mental health Provisions displayed on Summary

  Scenario: suiProvisions8- Add Needs and Provision from a blank assessment8
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Behaviour, emotional and mental health needs
    And user selects Behaviour, emotional and mental health needs - "Anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Behaviour, emotional and mental health provision
    When under Social understanding and interaction > "Additional and Different provision" - user selects "Training" option "1"
    And under Social understanding and interaction > "Additional and Different provision" - user selects "Post-16 specific provision" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Behaviour, emotional and mental health Provisions displayed on Summary