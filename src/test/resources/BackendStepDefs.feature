Feature: Testing the weather site
  Scenario: Testing the weather site scenario
    Given City name is Lonfon
    When Requesting weather information
    Then Coordinates are lon: -0.13 and lat: 51.51
