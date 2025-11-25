Feature: Error Validation
 
 Scenario Outline: Verify Error message displayed on incorrect user name/password
    Given landing on Ecommerce Page
    Given Logging with username "<name>" and password "<password>"
    Then "Incorrect email or password." message is displayed

    Examples: 
      | name  								| password		|
      | santoshsp17@gmai.com	| Gunnaa@12	|
