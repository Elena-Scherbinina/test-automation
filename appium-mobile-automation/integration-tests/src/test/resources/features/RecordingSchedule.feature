@recording_schedule

Feature:Recording Schedule tab functionality

  Background:User logins and  sees Guide
    Given I am on the Live page with "BTV_AUTOTEST" lineup
    And I accept location service dialog
    Then I see BTV screen


  @regression @android @ios
  Scenario: BTV-374 : User can close Recording Schedule on mobile
    And I click "Guide" button
    And I click "Recordings" button
    When I navigate to "Recording Schedule"
    Then Recording Schedule is shown
    And I close Recording Schedule

  @regression @android @ios
  Scenario:BTV-362 : User can background/foreground Recording Schedule
    When I click "Recordings" button
    Then "Recordings" tab is highlighted
    When I navigate to "Recording Schedule"
    And I background an app
    Then I bring app from background
    Then Recording Schedule is shown


  @regression @android @ios @btv375
  Scenario:BTV-375 : User can dismiss Recording Schedule using tab navigation on mobile
    When I click "Recordings" button
    Then "Recordings" tab is highlighted
    When I navigate to "Recording Schedule"
    Then Recording Schedule is shown
    And I click "Guide" button
    Then first channel in the Guide is default
    When I click "Recordings" button
    And I will see "Sort" dropdown box on Recordings page


  @regression @android  @369
  Scenario: BTV-369 : User can sleep/wake Recording Schedule on mobile
    When I click "Recordings" button
    Then "Recordings" tab is highlighted
    And I navigate to "Recording Schedule"
    And I put device to sleep
    Then I wake device


  @regression @android  @ios @376
  Scenario: BTV-360, -376 : User can cancel scheduled recording(s) using swipe on Android, ios mobile
    And I click "Guide" button
    And I click "Recordings" button
    When I navigate to "Recording Schedule"
    And I remember recording attributes for "first" scheduled recording
    And I swipe to cancel first recording from Recording Schedule tab
    Then I dont see canceled recording on Recording Schedule tab


  @regression @ios  @377a
  Scenario: BTV-377 : User can delete recordings using half swipe on iOS mobile
    And I click "Guide" button
    And I click "Recordings" button
    And I navigate to "Recording Schedule"
    And I remember recording attributes for "first" scheduled recording
    When I cancel "first" recording with half swipe from Recording Schedule tab
    Then I see "Delete" button on Recording Schedule tab
    When I tap "Delete" button to remove recording on Recording Schedule tab
    And I click "Recordings" button
    Then I dont see canceled recording on Recording Schedule tab


  @regression @android
  Scenario: BTV-364 : User can Undo when canceling scheduled recording on Android mobile
    And I click "Guide" button
    And I click "Recordings" button
    And I navigate to "Recording Schedule"
    And I remember recording attributes for "first" scheduled recording
    When I swipe to cancel first recording from Recording Schedule tab
    And I tap "Undo" button on Recording Schedule for android
    And I refresh "Recordings" tab
    Then I find "Undo" recording on "Recording Schedule" Tab



