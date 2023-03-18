Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Scenario: Search books by publication year
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-04-14
    And another book with the title 'Some other book', written by 'Tim Tomson', published in 2014-08-23
    And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2021-01-01
    When the customer searches for books published between 2013-01-01 and 2014-12-31
    Then 2 books should have been found
    And Book 1 should have the title 'Some other book'
    And Book 2 should have the title 'One good book'

  Scenario: Search books by author
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-04-14
    And another book with the title 'Some other book', written by 'Tim Tomson', published in 2014-08-23
    And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2021-01-01
    When the customer searches for books written by 'Tim Tomson'
    Then 1 book should have been found
    And Book 1 should have the title 'Some other book'

  Scenario: Correct non-zero number of books found by author by map
    Given I have the following books in the store by map
      | title                 | author          |
      | One good book         | Anonymous       |
      | The Lord of the Rings | J.R.R. Tolkien  |
      | Some other book       | Tim Tomson      |
      | How to cook a dino    | Fred Flintstone |
      | The Hobbit            | J.R.R. Tolkien  |

    When I search for books by author 'J.R.R. Tolkien'
    Then I find 2 books
