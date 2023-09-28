@expCommInter
@phaseTwo
Feature: Overview - basic page checks

  Scenario: overviewPage1- Overview page is present and correct
    Given user views assessment without any Needs or Provisions selected in phase two
    When I navigate to the Overview page
    Then the Overview page contains the associated data