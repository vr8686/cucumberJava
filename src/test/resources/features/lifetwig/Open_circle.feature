Feature: Open Circle Login Functionality

  Scenario: User can successfully log in to the account from Open Circle login page
    #Test date is hardwired into the code
    Given user opens login page
    When user fills out the form with valid credentials
    Then user is landing on their Circle homepage

  Scenario: User can successfully log in to the account from Open Circle login page 2
    #We are using Context here
    Given user credentials are "qwerwty@werty.com" and "qwertyasfgd1F"
    And user opens login page
    When user fills out the form with valid credentials using Context
    Then user is landing on their Circle homepage

  Scenario Outline: User can successfully log in to the account from Open Circle login page (with Examples)
    #Same as the the one above but with "Examples"
    Given user opens login page
    When user fills out the form with "<CORRECT_LOGIN>" and "<CORRECT_PASSWORD>"
    Then user is landing on their Circle homepage
    Examples:
      |CORRECT_LOGIN    |CORRECT_PASSWORD |
      |qwerwty@werty.com|qwertyasfgd1F    |

  Scenario: Username is mandatory during login process
    Given user opens login page
    When user fills out the form with login field empty
    Then assert "This input is required." error message is present

  Scenario: User should not login with incorrect username but using correct password
    Given user opens login page
    When user inputs incorrect email with correct password
    Then assert warning message is displayed

  Scenario: Password field is mandatory during login process
    Given user opens login page
    When user fills out the form with password field empty
    Then assert "This input is required." error message is present

  Scenario Outline: Password field accepts 20 characters
    Given user opens login page
    When user fills out the form with valid credentials
    And changes password to "<PASSWORD-20CHARS>" and logs out
    And user fills out the form with new "<PASSWORD-20CHARS>"
    Then user is landing on their Circle homepage
    And changes "<PASSWORD-20CHARS>" back
    Examples:
    |PASSWORD-20CHARS|
    |PaSs1234PaSs12341234|

  Scenario Outline: Current password field is limited to 20 characters by typing
    Given user opens login page
    When user fills out the form with 21 characters long password
    Then assert "<ERROR_MESSAGE>" error message is present
    Examples:
      | ERROR_MESSAGE |
      |too_short_error_message|
      |Field should contain at least one upper-case, at least one lower-case and at least one digit and be between 8 and 20|

  Scenario: User should not login with incorrect password
    Given user opens login page
    When user fills out the form with incorrect password
    Then assert warning message is displayed
