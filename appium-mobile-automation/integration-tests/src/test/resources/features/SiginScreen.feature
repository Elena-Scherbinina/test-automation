@login

Feature: Login to the account
  As a user
  I want to be able to login successfully

  Background: User starts the app
    Given I have a btv app running with an appium


  @regression @android @ios
  Scenario: BTV-581 : User can Show/Hide keyboard at Sign In screen on mobile
    When I click "Sign In" button on the first screen
    Then I will see "email" field on the second screen
    And I tap on email
    Then keyboard for "alphabet" is shown
    And I background an app
    When I bring app from background
    Then I will see "email" field on the second screen
    And keyboard is not shown


  @regression @android @ios
  Scenario: BTV-579 : User cannot rotate Sign In screen on mobile
    When I click "Sign In" button on the first screen
    Then I will see "email" field on the second screen
    And screen has portrait orientation
    When I rotate the screen horizontally
    Then screen has portrait orientation
    When I rotate the screen vertically
    Then screen has portrait orientation

  @regression @android @ios
  Scenario Outline: BTV-591  User can enter, clear text at Sign In screen
    When I click "Sign In" button on the first screen
    Then I will see "email" field on the second screen
    And I will see "password" field on the second screen
    And I tap on email
    Then keyboard for "alphabet" is shown
    And I enter my email "<email>"
    When I click Clear Text button for "email" field
    Then "<email>" value is replaced by "<email_replacedBy>" on the second screen
    Examples:
      | email               | email_replacedBy         |
      | tester_199@gmail.com | Enter your email address |

  @regression @android @ios
  Scenario Outline: BTV-591  User can enter, clear text at Sign In screen
    When I click "Sign In" button on the first screen
    And I will see "password" field on the second screen
    And I tap on password
    Then keyboard for "alphabet" is shown
    And I enter my password "<password>"
    When I click Clear Text button for "password" field
    Then "<password>" value is replaced by "<password_replacedBy>" on the second screen
    Examples:
      | password | password_replacedBy |
      | 123456   | Enter your password |


  @regression @ios @9991
  Scenario Outline: BTV-592 : User can see message when email is unsupported at Sign In screen
    When I click "Sign In" button on the first screen
    Then I will see "email" field on the second screen
    When I enter my email "<email>"
    And I enter my password "<password>"
    And I click Sign In button on the second screen
    Then I will see the following "<error>" message for "<err_type>" signin error
    Examples:
      | zipcode | email        | qatester    | err_type    | error                                                                   |
      | 95129   | gmail.com    | qatester    |EMAIL_SIGNIN | Sorry, we couldn’t find an account that matches this email and password.|
      | 95129   | 456          | qatester    |EMAIL_SIGNIN | Sorry, we couldn’t find an account that matches this email and password.|
      | 95129   | abc          | qatester    |EMAIL_SIGNIN | Sorry, we couldn’t find an account that matches this email and password.|
      | 95129   | foo@         | qatester    |EMAIL_SIGNIN | Sorry, we couldn’t find an account that matches this email and password.|



  @regression @ios
  Scenario Outline: User can see message when password is unsupported at Sign In screen
    When I click "Sign In" button on the first screen
    When I enter my email "<email>"
    And I enter my password "<password>"
    And I click Sign In button on the second screen
    Then I will see the following "<error>" message for "<err_type>" signin error
    Examples:
      | zipcode | email           | password        | err_type     | error                            |
      | 95129   | test@gmail.com  | abc             | PWD_SIGNIN   |  Password must be 6-15 characters|
      | 95129   | test@gmail.com  | 123             | PWD_SIGNIN   | Password must be 6-15 characters |
      | 95129   | test@gmail.com  | 1234567890123456| PWD_SIGNIN   | Password must be 6-15 characters |
      | 95129   | test@gmail.com  | 123456          | EMAIL_SIGNIN | Sorry, we couldn’t find an account that matches this email and password. |
      | 95129   | test@gmail.com   | abc            | PWD_SIGNIN  | Password must be 6-15 characters|



  @regression @ios
  Scenario Outline:Email and password are supported but not valid
    When I click "Sign In" button on the first screen
    When I enter my email "<email>"
    And I enter my password "<password>"
    And I click Sign In button on the second screen
    Then I will see the following "<error>" message for "<err_type>" signin error
    And I tap "OK" on the Sign In Failed dialog
    And I dont see Sign In Fail dialog
    Examples:
      | zipcode | email           | password        | err_type     | error                            |
      | 95129   | test@didjatv.com| 123456          | EMAIL_SIGNIN | Sorry, we couldn’t find an account that matches this email and password. |



