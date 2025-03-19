@guide

Feature:Guide functionality

  Background: User logins and  sees app Guide
    Given I am on the Live page with "AUTOTEST" lineup
    And I accept location service dialog
    Then I see an app screen
    And I will see "Live" tab
    And "Live" tab is highlighted


  @regression @android @ios @smoke_test
  Scenario: iOS,Android live tab highlighted
    When I click "Guide" button
    And I click "Live" button
    Then "Live" tab is highlighted
    And "Guide" tab is not highlighted
    And "Recordings" tab is not highlighted

  @regression @ios @android @smoke_test
  Scenario: ios,android guide tab highlighted
    When I click "Guide" button
    Then "Guide" tab is highlighted
    And "Live" tab is not highlighted
    And "Recordings" tab is not highlighted


  @regression @ios @android @smoke_test
  Scenario: ios, android: User can view all the buttons on guidePage
    When I click "Guide" button
    And I will see "Settings" button
    And I will see "Last" button
    And I will see "Full Screen View" button
    And I will see "Live" tab
    And I will see "Guide" tab
    And I will see "Recordings" tab



  @regression @ios @android @smoke_test
  Scenario: ios, android: Overlay in the Viewer will disappear in 3 seconds after I tapped Viewer
    When I click "Guide" button
    Then "Guide" tab is highlighted
    And there is no Loading overlay in the viewer on the "Guide" tab
    And there is no "Play/Pause" overlay in the viewer on the "Guide" tab
    Then I tap app video screen
    And I will see "Play/Pause" overlay in the viewer
    When I wait for "5" seconds
    Then there are no overlays in the viewer


  @regression @android @ios @smoke_test
  Scenario: ios, android - User launch app,navigate to Guide tab and verifies first Guide channel
    When I click "Guide" button
    Then "Guide" tab is highlighted
    And first channel in the Guide is default

  @regression @android @ios @smoke_test
  Scenario: ios, android - User is able to scroll viewer right and left on the Guide tab
    When I click "Guide" button
    And first channel in the Guide is default
    And I will scroll right 7 times
    And I will scroll left 7 times
    Then first channel in the Guide is default


  @regression @android @ios
  Scenario:User can background/foreground Guide tab
    When I click "Guide" button
    Then "Guide" tab is highlighted
    And I background an app
    Then I bring app from background


  @regression @android @ios @smoke_test
  Scenario: android - User is able to scroll Guide in right and left direction
    When I click "Guide" button
    Then I will scroll schedule 3 times in right direction
    And I will scroll schedule 3 times in left direction
    Then first channel in the Guide is default


  @regression @android
  Scenario: User is on the Guide screen and can expand and collapse Guide tab
    When I click "Guide" button
    And I remember "Guide" tab height
    When I tap "Up" button on "Guide" tab
    And I remember extended "Guide" tab height
    When I tap "Down" button on "Guide" tab
    Then "Guide" tab get back to its original height


  @regression @android  @ios @smoke_test
  Scenario: User can view Guide Info dialog for current live show
    When I click "Guide" button
    And I tap Guide cell for the current live show
    Then I will see Info dialog
    And I will see channel logo on Info Dialog
    And I will see show name on Info Dialog


  @regression @android @ios
  Scenario: User can cancel recording from Guide Info dialog
    When I click "Guide" button
    And I tap Guide cell with index 1
    Then I will see Info dialog
    And I unselect "Record" option button on Info Dialog
    Then "Record" button is unselected on Info Dialog


  @regression @android @ios @smoke_test
  Scenario: User can schedule recording in Guide Info dialog
    When I click "Guide" button
    And I tap Guide cell for the current live show
    Then I will see Info dialog
    And I will see "Record" if recording is toggled OFF
    And I tap "Record" button on Info Dialog to see record options
    And I tap Record to schedule recording
    Then I will see "Recording" on Info Dialog if recording is toggled ON
    And I unselect "Record" option button on Info Dialog


  @regression @android @ios @smoke_test
  Scenario: User can dismiss Guide Info dialog
    When I click "Guide" button
    And I tap Guide cell for the current live show
    Then I will see Info dialog
    When I dismiss Info Dialog
    Then I see app screen


  @regression @android @ios
  Scenario: User can schedule recording from Guide and find it in Recording Schedule
    When I click "Guide" button
    And I tap Guide cell for the current live show
    Then I will see Info dialog
    And I tap "Record" button on Info Dialog to see record options
    And I tap Record to schedule recording
    And I will remember show name
    And I dismiss Info Dialog
    When I click "Recordings" button
    And I navigate to "Recording Schedule"
    Then Recording Schedule is shown
    And I find scheduled recording on "Recording Schedule" Tab


  @regression @android @ios
  Scenario: User can tap Live tab's Last button to navigate to previous live watching channel
    And I click "Guide" button
    And there is no Loading overlay in the viewer on the "Guide" tab
    And I tap Play/Pause button
    When I click "Live" button
    And I see that "Last" button is disabled
    And I remember current live channel number on the Live tab as "channel1"
    When I go to the next channel with "next" chevron
    And I click "Watch Live" button
    And I remember current live channel number on the Live tab as "channel2"
    Then I see that "Last" button is enabled
    When I click "Last" button
    Then I see that channel1 has loaded into the viewer on Live tab
    And I see that "Last" button is enabled
    When I click "Last" button
    Then I see that channel2 has loaded into the viewer on Live tab



  @regression @ios
  Scenario: User can navigate between expanded tabs on mobile
    When I click "Guide" button
    And I remember "Guide" tab height
    When I tap "Up" button on "Guide" tab
    And I remember extended "Guide" tab height
    When I click "Recordings" button
    Then "Recordings" tab has extended height
    And I navigate to "Recording Schedule"
    Then "Recording Schedule" tab has extended height
    When I click "Community" button
    Then "Community" tab has extended height
    When I click "Live" button
    Then "Live" tab is collapsed


  @regression @ios
  Scenario: User can view Guide Info dialog for current show not being watched
    When I click "Guide" button
    And I tap Guide live show for the channel "channel1"
    Then I will see Info dialog
    And I will see show name on Info Dialog
    And I will see "Watch Live" button on Info Dialog
    And I will see channel logo on Info Dialog

  @regression @ios
  Scenario: User can view Guide Info dialog for current show not being watched
    When I click "Guide" button
    And first channel in the Guide is default
    And I tap Guide live show for the channel "channel2"
    Then I will see Info dialog
    And I will see "Watch Live" button on Info Dialog
    When I click "Watch Live" button
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    When I tap Play/Pause button
    Then I see that Full Screen View channel value corresponds to the "channel2"



  @regression @ios
  Scenario: User can view Guide Info dialog for future show
    When I click "Guide" button
    And first channel in the Guide is default
    Then I will scroll Guide 1 times down
    And I tap Guide cell with index 6
    Then I will see Info dialog
    And I will see show name on Info Dialog
    And I will see "Record" if recording is toggled OFF
    And I dont see "Watch Live" button on Info Dialog


  @regression @ios
  Scenario: User can vertically scroll page Guide
    When I click "Guide" button
    And first channel in the Guide is "CBS5" channel
    And Today button has caption "Today"
    Then I will scroll Guide 3 times down
    And Today buttons caption is not equal "Today"
    And I will scroll Guide 3 times up
    Then first channel in the Guide is default


