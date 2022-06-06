@expCommInter
Feature: Verify Needs Headings and Expanding Sections with selected Needs levels

  Scenario: Verify Expressive language and communication Needs links [Test suite - 32293]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Expressive language and communication need
    Then the Needs page has the following accordion sections:
    | Style of communication |
    | Vocabulary and sentence structure |
    | Word finding |
    | Speech |
    | Needs relating to brain injury |
    | Impact of anxiety/emotional wellbeing |
    | Social expressive language |
    | Conversational rules |
    | Functional communication |
    | Non-verbal, alternative/augmented communication |
    And all selected Expressive language and communication need levels are displayed at the bottom of page

  Scenario: Verify Receptive language and communication Needs links [Test suite - 32880]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Receptive language and communication need
    Then the Needs page has the following accordion sections:
      | Attention and listening |
      | Memory |
      | Functional understanding |
      | Understanding social language |
      | Non-verbal, alternative/augmented communication |
    And all selected Receptive language and communication need levels are displayed at the bottom of page


  Scenario: Verify Academic learning and employability Needs links [Test suite - 33001]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Academic learning and employability need
    Then the Needs page has the following accordion sections:
      | Qualifications and employability |
      | Processing |
      | Theory of mind |
      | Problem solving |
      | Memory |
      | Numeracy |
      | Literacy |
    And all selected Academic learning and employability need levels are displayed at the bottom of page

  Scenario: Verify Behaviours for learning Needs links [Test suite - 33002]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Behaviours for learning
    Then the Needs page has the following accordion sections:
      | Independence as a learner |
      | Following adult expectations with learning |
      | Organisation for learning |
      | Flexible thinking |
      | Attention and concentration |
      | Accepting challenge with learning |
      | Motivation and aspiration |
    And all selected Behaviours for learning need levels are displayed at the bottom of page

  Scenario: Verify Social understanding and interaction Needs links [Test suite - 33192]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Social understanding and interaction
    Then the Needs page has the following accordion sections:
      | Social interest |
      | Social anxiety |
      | Social understanding |
      | Social interaction and communication |
      | Belonging and social inclusion |
      | Sex and romantic relationships |
      | Gender identity |
    And all selected Social understanding and interaction need levels are displayed at the bottom of page

  Scenario: Verify Behaviour, emotional and mental health Needs links [Test suite - 33193]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Behaviour, emotional and mental health needs
    Then the Needs page has the following accordion sections:
      | Incidents of behaviour that challenges |
      | Emotional development and emotional literacy |
      | Emotional regulation and impulsivity |
      | Anxiety |
    And all selected Behaviour, emotional and mental health need levels are displayed at the bottom of page

  Scenario: Verify Hearing Needs links [Test Suite 33483]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Hearing needs
    Then the Needs page has the following accordion sections:
      | Understanding needs |
      | Curriculum access |
      | Social inclusion |
      | Physical safety |
      | Wellbeing and identity |
    And all selected Hearing need levels are displayed at the bottom of page

  Scenario: Verify Medical, physical, motor-skills and mobility Needs links [Test Suite 33289]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Medical, physical, motor-skills and mobility needs
    Then the Needs page has the following accordion sections:
      | Physical health/ Medical needs |
      | Pain |
      | Health anxiety |
      | Traumatic brain injury |
      | Fine motor skills (and personal care) |
      | Mobility, gross motor skills and coordination |
      | Continence |
      | Sleep |
    And all selected Medical, physical, motor-skills and mobility need levels are displayed at the bottom of page


  Scenario: Verify Vision Needs links [Test Suite 33290]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Vision needs
    Then the Needs page has the following accordion sections:
      | Understanding needs |
      | Curriculum access |
      | Social inclusion |
      | Physical safety |
      | Wellbeing and identity |
    And all selected Vision need levels are displayed at the bottom of page


  Scenario: Verify Sensory, processing and integration Needs links [Test Suite 33484]
    Given user views assessment without any Needs or Provisions selected
    When user clicks link for Sensory processing and integration needs
    Then the Needs page has the following accordion sections:
      | Vestibular system (how our body reacts to gravity) |
      | Interoceptive sense (interpreting sensations from internal organs) |
      | Proprioceptive sense |
      | Tactile Awareness (leading to avoidant or sensory seeking behaviours) |
      | Gustatory (taste) and Olifactory (smell) systems |
      | Visual processing |
      | Auditory processing |
    And all selected Sensory, processing and integration need levels are displayed at the bottom of page