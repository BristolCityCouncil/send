@expCommInter
Feature: Medical, physical, motor-skills and mobility - provision list - provision is selectable when need level is chosen [Test Suite 32955]

  Scenario: mpmmProvisions1- Add Needs and Provision from a blank assessment1
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Medical, physical, motor-skills and mobility needs
    And user selects Medical, physical, motor-skills and mobility needs - "Health anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Medical, physical, motor-skills and mobility provisions
    When under Medical, physical, motor-skills and mobility > "Specialist professional involvement" - user selects "Specialist professional advice, supervision or support" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Medical, physical, motor-skills and mobility Provisions displayed on Summary

  Scenario: mpmmProvisions2- Add Needs and Provision from a blank assessment3
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Medical, physical, motor-skills and mobility needs
    And user selects Medical, physical, motor-skills and mobility needs - "Health anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Medical, physical, motor-skills and mobility provisions
    When under Medical, physical, motor-skills and mobility > "Additional Staff support" - user selects "In class Staff Suppport for vision and hearing needs" option "1"
    And under Medical, physical, motor-skills and mobility > "Additional Staff support" - user selects "Medical condition related staff support" option "1"
    And under Medical, physical, motor-skills and mobility > "Additional Staff support" - user selects "Staff Support for Sensory processing needs" option "1"
    And under Medical, physical, motor-skills and mobility > "Additional Staff support" - user selects "Support over transition between educational settings (pre-16)" option "1"
    And under Medical, physical, motor-skills and mobility > "Additional Staff support" - user selects "Support over transition between educational settings (post-16)" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Medical, physical, motor-skills and mobility Provisions displayed on Summary

  Scenario: mpmmProvisions3- Add Needs and Provision from a blank assessment4
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Medical, physical, motor-skills and mobility needs
    And user selects Medical, physical, motor-skills and mobility needs - "Health anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Medical, physical, motor-skills and mobility provisions
    When under Medical, physical, motor-skills and mobility > "family Support" - user selects "Family Support" option "1"
    And under Medical, physical, motor-skills and mobility > "family Support" - user selects "Family Intervention (pre-16)" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Medical, physical, motor-skills and mobility Provisions displayed on Summary

  Scenario: mpmmProvisions4- Add Needs and Provision from a blank assessment5
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Medical, physical, motor-skills and mobility needs
    And user selects Medical, physical, motor-skills and mobility needs - "Health anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Medical, physical, motor-skills and mobility provisions
    When under Medical, physical, motor-skills and mobility > "Support in non-teaching/unstructured times" - user selects "lunchtime support" option "1"
    And under Medical, physical, motor-skills and mobility > "Support in non-teaching/unstructured times" - user selects "break time support" option "1"
    And under Medical, physical, motor-skills and mobility > "Support in non-teaching/unstructured times" - user selects "support over transitions in current setting (pre-16)" option "1"
    And under Medical, physical, motor-skills and mobility > "Support in non-teaching/unstructured times" - user selects "Support over transitions post-16" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Medical, physical, motor-skills and mobility Provisions displayed on Summary

  Scenario: mpmmProvisions5- Add Needs and Provision from a blank assessment6
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Medical, physical, motor-skills and mobility needs
    And user selects Medical, physical, motor-skills and mobility needs - "Health anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Medical, physical, motor-skills and mobility provisions
    When under Medical, physical, motor-skills and mobility > "Safety" - user selects "Physical Intervention" option "1"
    And under Medical, physical, motor-skills and mobility > "Safety" - user selects "Safety off site pre-16" option "1"
    And under Medical, physical, motor-skills and mobility > "Safety" - user selects "Safety off site post-16" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Medical, physical, motor-skills and mobility Provisions displayed on Summary

  Scenario: mpmmProvisions6- Add Needs and Provision from a blank assessment7
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Medical, physical, motor-skills and mobility needs
    And user selects Medical, physical, motor-skills and mobility needs - "Health anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Medical, physical, motor-skills and mobility provisions
    When under Medical, physical, motor-skills and mobility > "Health care" - user selects "Medical care" option "1"
    And under Medical, physical, motor-skills and mobility > "Health care" - user selects "Support at meal times" option "1"
    And under Medical, physical, motor-skills and mobility > "Health care" - user selects "Intimate care" option "1"
    And under Medical, physical, motor-skills and mobility > "Health care" - user selects "Personal care" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Medical, physical, motor-skills and mobility Provisions displayed on Summary

  Scenario: mpmmProvisions7- Add Needs and Provision from a blank assessment7
    Given user views assessment without any Needs or Provisions selected
    And user clicks link for Medical, physical, motor-skills and mobility needs
    And user selects Medical, physical, motor-skills and mobility needs - "Health anxiety" - "Block C"
    And user clicks the Save button
    And user clicks the Back button
    And user clicks link for Medical, physical, motor-skills and mobility provisions
    When under Medical, physical, motor-skills and mobility > "Additional and Different provision" - user selects "Training" option "1"
    And under Medical, physical, motor-skills and mobility > "Additional and Different provision" - user selects "Post-16 specific provision" option "1"
    And under Medical, physical, motor-skills and mobility > "Additional and Different provision" - user selects "VI specific provision" option "1"
    And under Medical, physical, motor-skills and mobility > "Additional and Different provision" - user selects "HI Specific Provision" option "1"
    And under Medical, physical, motor-skills and mobility > "Additional and Different provision" - user selects "Educational/ communication resources" option "1"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Medical, physical, motor-skills and mobility Provisions displayed on Summary

