Feature: Wishlist management

  Scenario: Add a product to the wishlist
    Given the client with ID "clientId01"
    When the client adds a product with ID "1" and name "Produto Teste"
    Then the product should be added to the wishlist

  Scenario: Retrieve all products from the wishlist
    Given the client with ID "clientId02"
    And the wishlist contains products
    When the client retrieves their wishlist
    Then the system should return all products in the wishlist

  Scenario: Prevent adding more than 20 products to the wishlist
    Given the client with ID "clientId03"
    And the wishlist already contains 20 products
    When the client adds a product with ID "21" and name "Produto Extra"
    Then the system should not allow the product to be added
    And the system should return a message saying "A wishlist atingiu o limite máximo de 20 produtos."

  Scenario: Verify if a product is in the wishlist
    Given the client with ID "clientId04"
    And the client has a product with ID "1" and name "Produto Teste" in the wishlist
    When the client checks if the product with ID "1" is in the wishlist
    Then the system should confirm that the product is in the wishlist

  Scenario: Remove a product from the wishlist
    Given the client with ID "clientId05"
    And the client has a product with ID "1" and name "Produto Teste" in the wishlist
    When the client removes the product with ID "1"
    Then the product should not be in the wishlist

  Scenario: Add a duplicate product to the wishlist
    Given the client with ID "clientId06"
    And the client has a product with ID "1" and name "Produto Teste" in the wishlist
    When the client adds a product with ID "1" and name "Produto Teste" again
    Then the system should not allow the duplicate product to be added
    And the system should return a message saying "O produto já está na wishlist."

  Scenario: Retrieve an empty wishlist
    Given the client with ID "clientId07"
    And the wishlist is empty
    When the client retrieves their wishlist
    Then the system should return an empty list
