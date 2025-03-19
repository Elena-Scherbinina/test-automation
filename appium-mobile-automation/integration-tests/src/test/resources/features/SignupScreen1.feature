@login

Feature: Signup to the account
  As a user
  I want to be able to login successfully

  Background: User starts the app
    Given I have an app running with an appium


  @regression @android @ios
  Scenario: Verify all UI components exists on join page
    And I see app logo
    And I see SignIn link
    And I see ZipCode textbox
    And I see the following text messages:
      | Already have an account? SignIn |
      | Enter your ZIP Code              |

    And I see Check Availability button
    And the button is disabled


  @regression @android @ios
  Scenario Outline: User can return to ZIP Code screen
    And I see ZipCode textbox
    And I enter my zipcode "<zipcode>"
    And I click "Check Availability" button on the first screen
    When I click "Back" button
    Then I see ZipCode textbox
    Examples:
      | zipcode |
      | 95129   |


  @regression @ios
  Scenario: "Enter your ZIP Code" text should be visible in the dark mode
    And I see ZipCode textbox
    When I turn on dark mode
    And I tap app video screen
    Then I see default text in "Zip Code" textbox
    When I turn off dark mode
    And I tap app video screen
    Then I see default text in "Zip Code" textbox


  @regression @android @ios
  Scenario: Verify keyboard screen appears when tapped on zipcode
    When I tap on zip code
    Then keyboard for "numbers" is shown
    When I hide keyboard
    Then keyboard is not shown


  @regression @ios @android
  Scenario Outline: User can enter, clear text at Join screen
    And I see ZipCode textbox
    When I tap on zip code
    And I enter my zipcode "<zipcode>"
    Then keyboard for "numbers" is shown
    When I click "Clear Text" button on the first screen
    Then "<zipcode>" value is replaced by "<replacedBy>"
    Examples:
      | zipcode | replacedBy          |
      | 95129   | Enter your ZIP Code |


  @regression @android @ios
  Scenario: User can background/foreground ZIP Code screen on mobile
    And I see ZipCode textbox
    And I see Check Availability button
    And I background an app
    When I launch the app
    Then I see Check Availability button

  @regression @ios @android
  Scenario: User can use Back button on ZipCode screen
    And I see ZipCode textbox
    And I navigate back on Zipcode screen
    When I launch the app
    Then I see Check Availability button


  @regression @ios @android @smoke_test
  Scenario Outline: android - The user should be able to see UI on the page with Zipcode input with Check availability link
    And I see SignIn link
    And I see ZipCode textbox
    And I see Check Availability button
    When I click "Sign In" button on the first screen
    Then I see second Signup screen
    When I tap Sign Up link
    Then I see first Signup screen
    And I enter my zipcode "<zipcode>"
    When I click "Check Availability" button on the first screen
    Then I see Sign Up screen for "<area>" area
    Examples:
      | zipcode | area     |
      | 95129   | BAY_AREA |


  @ios @android
  Scenario Outline: User can see message when ZIP Code is not supported, or outside market
    When I enter my zipcode "<zipcode>"
    And I click "Check Availability" button on the first screen
    Then I will see error message for "<err_type>"
    Examples:
      | zipcode | err_type      |
      | 99999   | NOT_AVAILABLE |


   @android @ios
   Scenario Outline: User can see message when ZIP Code is not supported and click No to dismiss that message
    When I enter my zipcode "<zipcode>"
    And I click "Check Availability" button on the first screen
    Then I will see "<button_1>" and "<button_2>" or "<button_1_ios>" and "<button_2_ios>" on the message box
    When I tap "No Thanks" button to dismiss Check Availability message box
    Then Check Availability message box dismissed
    Examples:
      | zipcode | button_2   | button_1  |button_2_ios|button_1_ios|
      | 99999   | NO, THANKS | NOTIFY ME |No Thanks   |Notify Me   |

   @android
   Scenario Outline: User can see message when ZIP Code is not supported and click NOTIFY ME to dismiss that message
    When I enter my zipcode "<zipcode>"
    And I click "Check Availability" button on the first screen
    Then I will see "<button_1>" and "<button_2>" or "<button_1_ios>" and "<button_2_ios>" on the message box
    When I tap "<button_1>" button on Check Availability message box
    Then Check Availability message box dismissed
    Examples:
      | zipcode | button_2   | button_1  |button_2_ios|button_1_ios|
      | 99999   | NO, THANKS | NOTIFY ME |No Thanks   |Notify Me   |


  @regression @android @ios
  Scenario Outline: User can see disabled Check Availability when invalid ZIP Code is entered
    And I enter my zipcode "<zipcode>"
    Then I see Check Availability button
    And the button is disabled
    Examples:
      | zipcode |
      | 8       |
      | 99      |
      | 995     |
      | 9912    |
      | aaa     |


  @regression @android @ios
  Scenario: android - User can't rotate Join screen horizontally
    And I see app logo
    And I see SignIn link
    And screen has portrait orientation
    When I rotate the screen horizontally
    Then screen has portrait orientation
    When I rotate the screen vertically
    Then screen has portrait orientation


  @regression @android @ios
  Scenario: User can view Sign In screen on mobile
    When I click "Sign In" button on the first screen
    Then I will see "email" field on the second screen
    And I will see "password" field on the second screen
    And I see default text in "Email Address" textbox on the second sign up screen
    And I see default text in "Password" textbox on the second sign up screen
    And SignIn button is disabled
    And I see Forgot your password link


  @regression @android  @ios
  Scenario Outline: Successfull signin
    And I click "Sign In" button on the first screen
    When I enter my email "<email>"
    And I enter my password "<password>"
    And I click Sign In button on the second screen
    And I accept location service dialog
    Then I see app screen
    And I will see "Live" tab
    And "Live" tab is highlighted
    When I click "Guide" button
    Then I see first channel in the Guide for "<lineup>" lineup
    Examples:
      | email                     | password | lineup   |
      | elena+tester2@didjatv.com | qatest   | AUTOTEST |

  @regression @android  @ios
  Scenario Outline: User can Sign In successfully with email containing a mix of upper, lower case characters
    And I click "Sign In" button on the first screen
    When I enter my email "<email>"
    And I enter my password "<password>"
    And I click Sign In button on the second screen
    And I accept location service dialog
    Then I see app screen
    And I will see "Live" tab
    And "Live" tab is highlighted
    When I click "Guide" button
    Then I see first channel in the Guide for "<lineup>" lineup
    Examples:
      | email                     | password | lineup     |
      | Elena+Tester2@didjatv.com | qatest   |  AUTOTEST  |
      | ELENA+TESTER2@Didjatv.com | qatest   |  AUTOTEST  |










