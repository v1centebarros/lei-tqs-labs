Feature: Flight purchase

  Scenario: User purchases a flight
    Given the user is on the Simple Travel Agency homepage
    When the user selects 'Portland' as the departure city
    And the user selects 'Rome' as the destination city
    And the user clicks on Find Flights
    Then the user is taken to the flight reservation page
    When the user selects a flight from 'Virgin America' number '43' with a price of '$472.56'
    And the user clicks on Choose This Flight
    Then the user is taken to the flight purchase page
    When the user fills out the flight purchase form with:
      | Name           | Vicente Barros |
      | Address        | Rua de Cima    |
      | City           | Porto          |
      | State          | Porto          |
      | Zip Code       | 4320-123       |
      | Card Type      | American Express |
      | Card Number    | 42069123       |
      | Card Exp. Month| 12             |
      | Card Exp. Year | 2020           |
      | Name on Card   | Vicente Barros |
      | Remember Me    | Yes            |
    And the user clicks on Purchase Flight
    Then the user is taken to the flight purchase confirmation page
