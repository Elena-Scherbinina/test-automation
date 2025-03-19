@forgot_password

Feature: Login to the account
  As a user
  I want to be able to login succesfully

  Background: User starts the app
    Given I have an app running with an appium


  @regression @ios @android
  Scenario: User can view Sign In screen on mobile and  User can click Forgot Your Password link and see page loaded
    When I click "Sign In" button on the first screen
    Then I see second Signup screen
    And I see Forgot your password link
    When I click Forgot your password
    Then I see Forgot Your Password page


  @regression @android @ios
  Scenario: android - User can't rotate Reset Password screen horizontally
    And I click "Sign In" button on the first screen
    When I click Forgot your password
    Then I see Forgot Your Password page
    And screen has portrait orientation
    When I rotate the screen horizontally
    Then screen has portrait orientation
    When I rotate the screen vertically
    Then screen has portrait orientation

  @regression @ios @android
  Scenario Outline: User can see controls on Forgot Password Page and text at the bottom of the page
    When I click "Sign In" button on the first screen
    Then I see second Signup screen
    When I click Forgot your password
    Then I see app logo on Forgot Your Password page
    And I see "<reset_button>" on Forgot Your Password page
    And I see email on Forgot Your Password page
    And I see "<bottom_text>" on Forgot Your Password page
    Examples:
      | bottom_text                 | reset_button   |
      | Need more help?\nContact Us | Reset Password |

  @regression @ios @android
  Scenario Outline: android - Forgot Password page has email field enabled and Forget Password button disabled
    And I click "Sign In" button on the first screen
    When I click Forgot your password
    Then I see Forgot Your Password page
    And Reset Password button is disabled
    And email text field is enabled
    And I see "<email_text>" on Forgot Your Password page
    Examples:
      | email_text               |
      | Enter your email address |


  @regression @ios @android
  Scenario Outline: User can enter, clear email field on Forgot Your Password page
    And I click "Sign In" button on the first screen
    And I see second Signup screen
    And I click Forgot your password
    And I enter email "<email>" to reset password
    When I click "Clear Text" on Forgot Your Password page
    Then I see "<email_text>" on Forgot Your Password page
    Examples:
      | email                | email_text               |
      | tester_199@gmail.com | Enter your email address |


  @regression  @android @ios
  Scenario:  User can click on Contact Us link and see that page loaded
    And I click "Sign In" button on the first screen
    And I see second Signup screen
    When I click Forgot your password
    And I click "Contact Us" on Forgot Your Password page
    Then I see Home app page

  @regression @ios @android
  Scenario Outline: User can Show/Hide keyboard at Reset Password screen
    And I click "Sign In" button on the first screen
    And I see second Signup screen
    And I click Forgot your password
    And I tap on email on Reset Password page
    Then keyboard for "alphabet" is shown
    And I enter email "<email>" to reset password
    When I click Clear Text button on Reset Password page
    Then "<email>" value is replaced by "<email_replacedBy>" on Reset Password screen
    Examples:
      | email                | email_replacedBy         |
      | tester_199@gmail.com | Enter your email address |


