Feature: WeatherTest

  Scenario: Test town coordinates
    Given City name is London
    When Requesting weather information
    Then Coordinates are lon: -0.13 and lat: 51.51
