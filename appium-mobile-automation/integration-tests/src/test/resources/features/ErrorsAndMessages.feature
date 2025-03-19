@misc_messages

Feature: Error Messaging
  As a user
  I want to be able to see error messages

  Background: User starts the app
    Given I have an app running with an appium


  @regression @android  @ios
  Scenario Outline: User sees empty messaging in Recordings tab
    And I click "Sign In" button on the first screen
    And I enter email "<email>" and password "<password>"
    And I see app screen after signin
    And I click "Guide" button
    When I click "Recordings" button
    And there are no recordings on Recordings tab
    Then I see a message "<msg_text>" in Recordings
    Examples:
      | email                | password | lineup   | msg_text                        |
      | tester_199@gmail.com | qatest   | AUTOTEST | There are no recorded programs. |


  @regression @ios @android
  Scenario Outline: User sees empty messaging in expanded Recordings tab on mobile device
    And I click "Sign In" button on the first screen
    When I enter email "<email>" and password "<password>"
    Then I see app screen after signin
    And I click "Recordings" button
    When I tap "Up" button on "Recordings" tab
    And I see a message "<msg_text>" in Recordings
    When I refresh screen with swipe
    Then I see a message "<msg_text>" in Recordings
    When I tap "Down" button on "Recordings" tab
    Then I see a message "<msg_text>" in Recordings
    Examples:
      | email                   | password | msg_text                        |
      | tester_199@gmail.com    | qatest   | There are no recorded programs. |


  @regression @ios
  Scenario Outline:User sees empty messaging in expanded Recordings tab after tapping sort button
    And I click "Sign In" button on the first screen
    When I enter email "<email>" and password "<password>"
    Then I see app screen after signin
    And I click "Recordings" button
    When I tap "Up" button on "Recordings" tab
    And I see a message "<msg_text>" in Recordings
    And I tap sort button
    And I will see sort menu
    When I tap "MENU_OLDEST" sort menu item
    Then I see a message "<msg_text>" in Recordings
    And I tap sort button
    When I tap "MENU_ATOZ" sort menu item
    Then I see a message "<msg_text>" in Recordings
    And I tap sort button
    When I tap "MENU_ZTOA" sort menu item
    Then I see a message "<msg_text>" in Recordings
    When I tap "Down" button on "Recordings" tab
    Then I see a message "<msg_text>" in Recordings
    Examples:
      | email                     | password | msg_text                        |
      | elena+tester1@didjatv.com | qatest   | There are no recorded programs. |

  @regression @ios @android
  Scenario Outline: User sees empty messaging in expanded Recordings after rotation and refresh
    And I click "Sign In" button on the first screen
    When I enter email "<email>" and password "<password>"
    Then I see app screen after signin
    And I click "Recordings" button
    When I tap "Up" button on "Recordings" tab
    And I rotate the screen horizontally
    Then screen has landscape orientation
    And I see a message "<msg_text>" in Recordings
    When I refresh screen with swipe
    Then I see a message "<msg_text>" in Recordings
    And I rotate the screen vertically
    Then screen has portrait orientation
    When I tap "Down" button on "Recordings" tab
    Then I see a message "<msg_text>" in Recordings
    Examples:
      | email                | password | msg_text                        |
      | tester_199@gmail.com | qatest   | There are no recorded programs. |


  @regression @ios @android
  Scenario Outline:User can rotate Recordings tab to Full Screen playback on mobile
    And I click "Sign In" button on the first screen
    When I enter email "<email>" and password "<password>"
    Then I see app screen after signin
    And I click "Recordings" button
    And I rotate the screen horizontally
    Then screen has landscape orientation
    And I rotate the screen vertically
    Then screen has portrait orientation

    Examples:
      | email                | password |
      | tester_199@gmail.com | qatest   |


  @regression @android @ios
  Scenario Outline: User sees empty messaging in Recording Schedule
    And I click "Sign In" button on the first screen
    And I enter email "<email>" and password "<password>"
    And I see app screen after signin
    And I click "Recordings" button
    When I navigate to "Recording Schedule"
    Then there are no scheduled recordings in Recording Schedule
    And I see a message "<msg_text>" in Recording Schedule
    Examples:
      | email                | password | msg_text                           |
      | tester_199@gmail.com | qatest   | No recordings have been scheduled. |

