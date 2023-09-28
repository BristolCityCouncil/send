@expCommInter
Feature: Cognition and Learning

  Scenario: cognitionLearning1- Academic learning Needs and Provisions
    Given assessment with Cognition and learning Need "Block C" selected
    And user clicks link for Academic learning and employability provision
    And page title contains the text "Academic learning and employability"
    And under Academic learning > Specialist Professional Provision - user selects "Specialist professional advice, supervision or support" option "2"
    And user clicks the Save button
    And user clicks the Back button
    Then all saved Academic learning and employability Provisions displayed on Summary