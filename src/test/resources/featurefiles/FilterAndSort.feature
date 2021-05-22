Feature: Filter and Sort data

  Background: User landed on the web page
    Given user should landed into Cyber Attack Statistics Page

  Scenario: Verify sorting options available
    And user see default sort option as "Name"
    Then user check the values in sort data dropdown list:
      | Name            |
      | Number of cases |
      | Impact score    |
      | Complexity      |

  Scenario Outline: Verify filter by Name and sort by Name
    And user select "Name" as the Sort option
    When user enter a '<Name>' in filter
    Then check filtered data is correct in "Name" column
    Then check "Name" column is sorted in ascending order
    Examples:
      | Name     |
      | A        |
      | a        |
      | Phishing |

  Scenario Outline: Verify filter by Name and sort by Number of cases
    And user select "Number of cases" as the Sort option
    When user enter a '<Name>' in filter
    Then check filtered data is correct in "Name" column
    Then check "Number of cases" column is sorted in ascending order
    Examples:
      | Name     |
      | A        |
      | a        |
      | Phishing |

  Scenario Outline: Verify filter by Name and sort by Impact score
    And user select "Impact score" as the Sort option
    When user enter a '<Name>' in filter
    Then check filtered data is correct in "Name" column
    Then check "Impact score" column is sorted in ascending order
    Examples:
      | Name     |
      | A        |
      | a        |
      | Phishing |

  Scenario Outline: Verify filter by Name and sort by Complexity
    And user select "Complexity" as the Sort option
    When user enter a '<Name>' in filter
    Then check filtered data is correct in "Name" column
    Then check "Complexity" column is sorted in ascending order
    Examples:
      | Name     |
      | A        |
      | a        |
      | Phishing |


  Scenario Outline: Verify filter by Complexity and sort by Name
    And user select "Name" as the Sort option
    When user enter a '<Complexity>' in filter
    Then check filtered data is correct in "Complexity" column
    Then check "Name" column is sorted in ascending order
    Examples:
      | Complexity |
      | Low        |
      | MEDIUM     |
      | high       |

  Scenario Outline: Verify filter by Complexity and sort by Number of cases
    And user select "Number of cases" as the Sort option
    When user enter a '<Complexity>' in filter
    Then check filtered data is correct in "Complexity" column
    Then check "Number of cases" column is sorted in ascending order
    Examples:
      | Complexity |
      | Low        |
      | MEDIUM     |
      | high       |

  Scenario Outline: Verify filter by Complexity and sort by Impact score
    And user select "Impact score" as the Sort option
    When user enter a '<Complexity>' in filter
    Then check filtered data is correct in "Complexity" column
    Then check "Impact score" column is sorted in ascending order
    Examples:
      | Complexity |
      | Low        |
      | MEDIUM     |
      | high       |

  Scenario Outline: Verify filter by Complexity and sort by Complexity
    And user select "Complexity" as the Sort option
    When user enter a '<Complexity>' in filter
    Then check filtered data is correct in "Complexity" column
    Then check "Complexity" column is sorted in ascending order
    Examples:
      | Complexity |
      | Low        |
      | MEDIUM     |
      | high       |

  Scenario: Verify sort by Name for all data
    And user select "Name" as the Sort option
    Then check "Name" column is sorted in ascending order

  Scenario: Verify sort by Complexity for all data
    And user select "Complexity" as the Sort option
    Then check "Complexity" column is sorted in ascending order

