@live

Feature:Live tab functionality

  Background: User logins and  sees Guide
    Given I am on the Live page with "AUTOTEST" lineup
    And I accept location service dialog
    Then I see app screen
    And I will see "Live" tab
    And "Live" tab is highlighted

  @regression @android @smoke_test
  Scenario: User is on the Live screen is able to see all controls
    And I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And I tap Play/Pause button
    When I click "Live" button
    And I navigate Next on Live Tab
    Then I will see "Next Channel" button on Live tab
    And I will see "Previous Channel" button on Live tab
    And I will see "Last" button
    And I will see "Full Screen View" button
    And I will see "Settings" button
    And I will see "Guide" tab
    And I will see "Recordings" tab



  @regression @android @ios @smoke_test
  Scenario: User can browse lineup in Live tab mini-portrait via chevrons, playback is unchanged
    And I click "Guide" button
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And I click "Play/Pause" button
    And I click "Live" button
    When I go to the last channel for "AUTOTEST" lineup
    And I go to the first channel for "AUTOTEST" lineup
    Then I see that Live Tab channel value corresponds to the first channel for "AUTOTEST" lineup


  @regression @ios
  Scenario: User can browse lineup in Live tab mini-portrait via swipe
    And I click "Guide" button
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And I click "Play/Pause" button
    And I click "Live" button
    And I will swipe Live tab right 4 times
    And I will swipe Live tab left 4 times
    Then I see that Live Tab channel value corresponds to the first channel for "AUTOTEST" lineup


  @regression @android @ios @smoke_test
  Scenario: User can cancel from Record options pop-up
    And I click "Guide" button
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And I click "Play/Pause" button
    When I click "Live" button
    Then I will see "show name" on Live page
    And I will see "Record" button on Live page
    When I unselect "Record" option button on Info Dialog
    Then "Record" button is unselected on Info Dialog


  @regression @android @ios @smoke_test
  Scenario: User sees Save to Library in Record options pop-up on Live Tab
    And I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    When I click "Live" button
    Then I will see "show name" on Live page
    And I tap "Record" button on Info Dialog to see record options
    And I tap Record to schedule recording
    And I see "Save To Library" option button on Info Dialog for Live Tab
    When I unselect "Record" option button on Info Dialog
    Then "Record" button is unselected on Info Dialog


  @regression @android @ios
  Scenario: User can schedule recording in Record options pop-up
    And I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And I click "Live" button
    And I tap "Record" button on Info Dialog to see record options
    And I will remember show name
    And I tap Record to schedule recording
    When I click "Recordings" button
    And I navigate to "Recording Schedule"
    Then I find scheduled recording on "Recording Schedule" Tab


  @regression @android @ios
  Scenario: User can see PIP window
    And I click "Live" button
    And screen has portrait orientation
    And I background an app
    Then I see PIP window on the screen


  @regression @android @ios
  Scenario: User can background/foreground, User can exit PIP and return to the app
    And I click "Live" button
    And I background an app
    Then I see PIP window on the screen
    And I bring app from background
    And I will see "Live" tab


  @regression @android @ios
  Scenario: User can can play recording in PIP
    And I click "Live" button
    And I click "Recordings" button
    And I watch "first" recording on Recordings tab
    And there is no "Play/Pause" overlay in the viewer on the "Recordings" tab
    And I background an app
    Then I see PIP window on the screen


  @regression @ios @pip
  Scenario: User can't see PIP window if video is paused for ios
    And I click "Live" button
    And screen has portrait orientation
    And I dont see Loading circle
    And I click "Play/Pause" button
    And I background an app
    Then I dont see PIP window on the screen


  @regression @android @pip
  Scenario: User can see PIP window if video is paused for android
    And I click "Guide" button
    And screen has portrait orientation
    And I dont see Loading circle
    And I click "Play/Pause" button
    And I click "Live" button
    And I background an app
    Then I see PIP window on the screen


  @regression @android @ios @pip
  Scenario:  User can close PIP window
    And I click "Live" button
    And screen has portrait orientation
    And I dont see Loading circle
    And I background an app
    When I tap Close button on PIP window
    Then I dont see PIP window on the screen


  @regression @android @ios  @pip
  Scenario: User can background/foreground Live tab in mini-portrait
    And I click "Live" button
    And I background an app
    Then I see PIP window on the screen
    When I bring app from background
    And I click "Guide" button
    And I tap Play/Pause button
    And I click "Live" button
    And I show record options
    And I will see "show name" on Live page
    And I will see "attributes" on Live page
    And I will see "channel logo" on Live page



  @regression  @ios
  Scenario:User views last channel played upon launch, cold launch
    And I click "Guide" button
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And I click "Play/Pause" button
    And I click "Live" button
    When I show record options
    Then I see that "Last" button is disabled
    And I remember current live channel number on the Live tab as "channel1"
    When I go to the next channel with "next" chevron
    And I will see "Watch Live" button on Live tab
    When I click "Watch Live" button
    And I remember current live channel number on the Live tab as "channel2"
    Then I see that "Last" button is enabled
    When I kill and start app
    And I remember current live channel number on the Live tab as "channel3"
    Then I see that channel3 has loaded into the viewer on Live tab


  @regression @ios
  Scenario: User can change channels using swipe gesture from Live tab mini-portrait (viewer)
    And I click "Guide" button
    And I click "Full Screen View" button
    And I tap Play/Pause button
    And I click "Live" button
    When I remember current live channel number on the Live tab as "channel1"
    And I will scroll right 7 times
    And I tap Play/Pause button
    And I remember current live channel number on the Live tab as "channel2"
    And I will scroll left 7 times
    And I remember current live channel number on the Live tab as "channel3"
    Then I see that channel1 is the same as channel2


  @regression @ios
  Scenario: User can change channels using viewer chevrons from Live tab in mini-portrait (navigation with viewer)
    And I click "Guide" button
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And I tap Play/Pause button
    And I click "Live" button
    When I navigate lineup using right chevron in the viewer
    And I navigate lineup using left chevron in the viewer
    Then I see that Live Tab channel value corresponds to the first channel for "AUTOTEST" lineup


  @regression @ios
  Scenario: User can change channels using viewer chevrons from Live tab in mini-portrait (navigation with viewer)
    And I click "Guide" button
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And I tap Play/Pause button
    And I click "Live" button
    When I navigate lineup using right chevron in the viewer
    Then I see that Live Tab channel value corresponds to the last channel for "AUTOTEST" lineup
   # And I cant navigate right on Live tab


  @regression  @ios
  Scenario: User can expand Live tab from chevron
    And I click "Live" button
    And I show record options
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    And I will see "Collapse Full Screen Chevron" overlay in full screen view
    When I tap "Collapse Full Screen" overlay in full screen view
    And I tap "Record" button on Info Dialog to see record options
    Then I will see "show name" on Live page
    And I will remember show name
    And I click "Full Screen View" button
    And I tap Play/Pause button
    Then I see that Full Screen View show name corresponds to Live tab show name


  @regression  @ios
  Scenario Outline:User can expand tabs from full screen portrait
    And I click "Live" button
    And I show record options
    When I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    And I tap Play/Pause button
    And I will remember show name for default lineup channel
    When I click "Guide" button
    Then I will see the same show name in the "Guide" tab
    When I click "Full Screen View" button
    And I click "Live" button
    Then I will see the same show name in the "Live" tab
    And I click "Full Screen View" button
    And I click "Recordings" button
    Then I will see "All" dropdown box on Recordings page
    And I click "Full Screen View" button
    When I click "Community" button
    Then I expect to see "<channel>" channel name in the Guide
    Examples:
      | channel |
      | Channel1|


  @regression @ios
  Scenario: User can change channels using swipe gesture from full screen portrait
    When I click "Live" button
    And I tap Full Screen view button on the top of Live page
    And there is no Loading overlay in the viewer on Full Screen View
    When I will swipe Full Screen View right 4 times
    And I tap Play/Pause button
    Then I see that Full Screen View channel value corresponds to the last channel for "AUTOTEST" lineup
    Then I tap app video screen
    And I wait in full screen view untill controls disappear
    And I cant navigate right in Full Screen view


  @regression @ios
  Scenario: User can change channel from Live tab mini-portrait via Watch Live option
    And I click "Live" button
    And I show record options
    When I go to the next channel with "next" chevron
    And I will see "Watch Live" button on Live tab
    When I click "Watch Live" button
    And I remember current live channel number on the Live tab as "channel2"
    And I will remember show name on Live page
    And I tap Full Screen view button on the top of Live page
    And I tap Play/Pause button
    And I see that Full Screen View channel value corresponds to the second channel for "AUTOTEST" lineup


  @regression  @ios
  Scenario: User can dismiss Record options pop-up (unselect Record option button)
    And I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    Then I click "Live" button
    And I tap "Record" button on Info Dialog to see record options
    And I tap Record to schedule recording
    And I unselect "Record" option button on Info Dialog
    Then "Record" button is unselected on Info Dialog
    And I am waiting for Live tab to extend
    And app is in full screen view

  @regression  @ios
  Scenario: User can dismiss Record options pop-up (rotation)
    And I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    Then I click "Live" button
    And I tap "Record" button on Info Dialog to see record options
    And I tap Record to schedule recording
    When I rotate the screen horizontally
    Then screen has landscape orientation
    When I rotate the screen vertically
    Then screen has portrait orientation
    And I am waiting for Live tab to extend
    And app is in full screen view

  @regression  @ios
  Scenario: User can dismiss Record options pop-up (tap Home button)
    And I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    Then I click "Live" button
    And I show record options
    And I background an app
    Then I see PIP window on the screen
    And I bring app from background
    And I am waiting for Live tab to extend
    And app is in full screen view












