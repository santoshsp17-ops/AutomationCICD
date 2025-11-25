
Feature: Submit the order from Ecoommerce Websote

Background:
Given landing on Ecommerce Page

  Scenario Outline: Verify placing an order with all valid details
    Given Logging with username "<name>" and password "<password>"
    When Add the the product <productName> to cart
    And  Checkout <productName> and submit the order
    Then "THANKYOU for the order." message is displayed on ConfirmationPage 

    Examples: 
      | name  								| password		|	productName	|
      | santoshsp17@gmai.com	| Gunnaa@123	|	ZARA COAT 3	|
