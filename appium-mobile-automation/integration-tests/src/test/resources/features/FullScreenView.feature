@full_screen_view

Feature:Full Screen View functionality

  Background: User logins and sees Guide
    Given I am on the Live page with "AUTOTEST" lineup
    And I accept location service dialog
    Then I see app screen
    And I will see "Live" tab
    And "Live" tab is highlighted


  @regression  @ios
  Scenario:User sees overlay on mobile, Full Screen, view portrait orientation
    When I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    And I click "Full Screen View" button
    When I tap Play/Pause button
    Then I will see "Show Title" overlay in full screen view
    And I will see "CC" overlay in full screen view
    And I will see "Player Slider" overlay in full screen view
    And I will see "Player Mute Button" overlay in full screen view
    And I will see "Slider Time Label" overlay in full screen view
    And I will see "30 sec Forward" overlay in full screen view
    And I will see "30 sec Back" overlay in full screen view
    And I will see "More Button" overlay in full screen view
    And I will see "Collapse Full Screen Chevron" overlay in full screen view



  @regression  @ios
  Scenario:User sees overlay on mobile, portrait orientation, overlay may be manually dismissed
    When I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    Then I click "Full Screen View" button
    When I tap Play/Pause button
    Then I will see "Show Title" overlay in full screen view
    And I will see "Player Slider" overlay in full screen view
    When I tap app video screen
    Then I dont see "Show Title" overlay in full screen view
    And I dont see "Player Slider" overlay in full screen view
    And I dont see "CC" overlay in full screen view
    And I dont see "Player Mute Button" overlay in full screen view
    And I dont see "Slider Time Label" overlay in full screen view
    And I dont see "30 sec Forward" overlay in full screen view
    And I dont see "30 sec Back" overlay in full screen view


  @regression  @ios
  Scenario:User can rewind -20 seconds, +20 seconds in portrait mode
    When I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    Then I click "Full Screen View" button
    When I tap Play/Pause button
    Then I will see "30 sec Back" overlay in full screen view
    When I tap "30 sec Back" overlay in full screen view
    Then I dont see "30 sec Back" overlay in full screen view
    And I tap Play/Pause button
    And I will see "30 sec Forward" overlay in full screen view
    When I tap "30 sec Forward" overlay in full screen view
    Then I dont see "30 sec Forward" overlay in full screen view

  @regression  @ios
  Scenario:User can rewind -20 seconds, +20 seconds in landscape mode
    When I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    When I tap Play/Pause button
    And I rotate the screen horizontally
    Then screen has landscape orientation
    And app is in full screen view
    And I will see "30 sec Back" overlay in full screen view
    When I tap "30 sec Back" overlay in landscape mode
    Then I dont see "30 sec Back" overlay in full screen view
    When I tap Play/Pause button
    Then I will see "30 sec Forward" overlay in full screen view
    When I tap "30 sec Forward" overlay in landscape mode
    Then I dont see "30 sec Forward" overlay in full screen view
    And I rotate the screen vertically
    And screen has portrait orientation


  @regression  @ios
  Scenario:User can view More from overlay
    When I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    And I click "Full Screen View" button
    Then I tap Play/Pause button
    And I will see "More Button" overlay in full screen view
    When I tap "More" button in Full Screen View
    Then I will see channel logo on Info Dialog
    And I will see show name on Info Dialog
    And I will see "Record" if recording is toggled OFF


  @regression @ios
  Scenario:User can background/foreground when paused on mobile, portrait mode
    When I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    And I click "Full Screen View" button
    When I tap Play/Pause button
    And I background an app
    And I bring app from background
    Then I see "Play/Pause" button

  @regression @ios
  Scenario:User can background/foreground when paused on mobile, landscape mode
    When I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    And I click "Full Screen View" button
    And I tap Play/Pause button
    And I rotate the screen horizontally
    Then screen has landscape orientation
    And I background an app
    When I bring app from background
    Then I see "Play/Pause" button
    And I rotate the screen vertically
    And screen has portrait orientation


  @regression  @ios @android
  Scenario:User can schedule recording in Info dialog from overlay
    And I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    And I click "Full Screen View" button
    And I tap Play/Pause button
    And I will see "More Button" overlay in full screen view
    When I tap "More" button in Full Screen View
    Then I will see channel logo on Info Dialog
    And I will see show name on Info Dialog
    And I will see "Record" if recording is toggled OFF
    And I tap "Record" button on Info Dialog to see record options
    And I tap Record to schedule recording
    And I will remember show name
    And I dismiss Info Dialog
    When I click "Recordings" button
    And I navigate to "Recording Schedule"
    And I tap "Up" button on "Recordings" tab
    Then I find scheduled recording on "Recording Schedule" Tab



  @regression @ios
  Scenario: User can change channels using swipe gesture in Full Screen on mobile
    When I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    When I will swipe Full Screen View right 4 times
    And I will swipe Full Screen View left 4 times
    And I tap Play/Pause button
    Then I see that Full Screen View channel value corresponds to the first channel for "AUTOTEST" lineup


  @regression @ios
  Scenario: User can change channels using chevrons from Full Screen on mobile
    When I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    Then I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    When I tap Play/Pause button
    Then I go to the next channel for "AUTOTEST" lineup in Full Screen View
    And I tap Play/Pause button
    Then I see that Full Screen View channel value corresponds to the second channel for "AUTOTEST" lineup



  @regression  @ios
  Scenario:User sees overlay in full screen portrait (overlay fades after tap)
    When I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    When I click "Live" button
    And I show record options
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    When I tap Play/Pause button
    And I will see "Show Title" overlay in full screen view
    And I will see "Player Slider" overlay in full screen view
    When I tap app video screen
    And I wait in full screen view untill controls disappear
    When I tap app video screen
    Then I will see "Player Slider" overlay in full screen view


  @regression @ios
  Scenario: User can rotate Live tab to Full Screen
    And I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And I click "Live" button
    And I show record options
    And I will remember show name on Live page
    When I rotate the screen horizontally
    Then screen has landscape orientation
    Then I dont see "Show Title" overlay in full screen view
    And I dont see "Player Slider" overlay in full screen view
    And I dont see "CC" overlay in full screen view
    And I dont see "Player Mute Button" overlay in full screen view
    And I dont see "Slider Time Label" overlay in full screen view
    And I dont see "30 sec Forward" overlay in full screen view
    And I dont see "30 sec Back" overlay in full screen view
    When I rotate the screen vertically
    Then screen has portrait orientation
    And I will see "show name" on Live page
    And I see that Live tab show name is the same as I remember


  @regression  @ios
  Scenario:User can expand tabs from full screen portrait
    When I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    And I click "Full Screen View" button
    And I will see "Collapse Full Screen Chevron" overlay in full screen view
    When I tap Play/Pause button
    And I click "Recordings" button
    And I will see "All" dropdown box on Recordings page
    And I click "Full Screen View" button
    And I will see "Collapse Full Screen Chevron" overlay in full screen view
    When I click "Guide" button
    And first channel in the Guide is default
    And I click "Full Screen View" button
    And I will see "Collapse Full Screen Chevron" overlay in full screen view
    And I click "Live" button
    And I will see "Next Channel" button on Live tab
