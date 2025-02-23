Feature: End to End simulation test

# Scenario: As a user I can add new data
#     Given A list of products are available
#     When I add new product to etalase
#     Then The product is available

Scenario Outline: As a user I can add new data with some data 
    Given A list of products are available
    When I add new "<payload>" to etalase
    Then The product is available
Examples:
    |payload    |
    |addItem    |
    |addItem2   |