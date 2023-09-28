@expCommInter
Feature: Needs Assessment - high level summary page - TC32294

  Scenario: summaryPage1- User can view Needs areas and links - [TC32443 -> TC32449] [TC32532 -> TC32538]
    Given user views assessment without any Needs or Provisions selected
    When page title contains the text "Summary"
    Then the following assessment area headings are displayed:
        | Communication and interaction |
        | Cognition and learning |
        | Social, emotional and mental health |
        | Physical and sensory |
    And all sub-area Needs links are visible and enabled
    And all sub-area Provisions links are visible and disabled

  Scenario: summaryPage2- User can navigate to sub area Needs pages - [TC32539]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Expressive language and communication need
    Then page title contains the text "Expressive language and communication"

  Scenario: summaryPage3- User can navigate to sub area Needs pages - [TC32540]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Receptive language and communication need
    Then page title contains the text "Receptive language and communication"

  Scenario: summaryPage4- User can navigate to sub area Needs pages - [TC32541]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Academic learning and employability need
    Then page title contains the text "Academic learning and employability"

  Scenario: summaryPage5- User can navigate to sub area Needs pages - [TC32542]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Behaviours for learning need
    Then page title contains the text "Behaviours for learning"

  Scenario: summaryPage6- User can navigate to sub area Needs pages - [TC32543]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Social understanding and interaction needs
    Then page title contains the text "Social understanding and interaction"

  Scenario: summaryPage7- User can navigate to sub area Needs pages - [TC32544]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Behaviour, emotional and mental health needs
    Then page title contains the text "Behaviour, emotional and mental health needs"

  Scenario: summaryPage8- User can navigate to sub area Needs pages - [TC32548]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Sensory processing and integration needs
    Then page title contains the text "Sensory processing and integration"