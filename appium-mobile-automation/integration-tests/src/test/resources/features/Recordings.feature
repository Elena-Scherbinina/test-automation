@recordings

Feature:Recordings tab functionality

  Background:User logins and  sees Guide
    Given I am on the Live page with "AUTOTEST" lineup
    And I accept location service dialog
    Then I see app screen


  @regression @android @ios @smoke_test
  Scenario: User can expand and collapse Recordings tab on mobile device
    When I click "Recordings" button
    And I remember "Recordings" tab height
    When I tap "Up" button on "Recordings" tab
    And I remember extended "Recordings" tab height
    When I tap "Down" button on "Recordings" tab
    Then "Recordings" tab get back to its original height


  @regression @android @smoke_test
  Scenario: android - User sees overlay controls in the viewer
    When I click "Recordings" button
    And there is no Loading overlay in the viewer on the "Recordings" tab
    Then I tap app video screen
    And I click "Play/Pause" button
    And I will see "CC" overlay in the viewer
    And I will see "Player Mute Button" overlay in the viewer
    And I will see "30 sec Forward" overlay in the viewer
    And I will see "30 sec Back" overlay in the viewer
    And I will see "Chevron Right" overlay in the viewer


  @regression @ios @smoke_test
  Scenario: ios - User sees overlay controls in the viewer
    When I click "Recordings" button
    And there is no Loading overlay in the viewer on the "Recordings" tab
    Then I tap Play/Pause button
    And I will see "CC" overlay in the viewer
    And I will see "Player Mute Button" overlay in the viewer
    And I will see "30 sec Forward" overlay in the viewer
    And I will see "30 sec Back" overlay in the viewer
    And I will see "Chevron Right" overlay in the viewer


  @regression @android @ios @smoke_test
  Scenario: ios, android - User launch app,navigate to Recordings tab, then to Guide tab and verifies first guide channel
    And I click "Recordings" button
    When I click "Guide" button
    Then first channel in the Guide is default

  @regression @android @ios
  Scenario:User can background/foreground Recording tab
    When I click "Recordings" button
    Then "Recordings" tab is highlighted
    And I background an app
    Then I bring app from background

  @regression @android @ios
  Scenario:User can background/foreground from expanded Recordings tab in Full Screen on mobile
    When I click "Recordings" button
    Then "Recordings" tab is highlighted
    And I click "Full Screen View" button
    And I rotate the screen horizontally
    Then screen has landscape orientation
    And I background an app
    Then I bring app from background
    And I rotate the screen vertically
    Then screen has portrait orientation


  @regression @android @ios
  Scenario:User can rotate Recordings tab to Full Screen playback on mobile
    When I click "Recordings" button
    Then "Recordings" tab is highlighted
    And I rotate the screen horizontally
    Then screen has landscape orientation
    And I see full screen view
    And there is no Loading overlay in the viewer on Full Screen View
    When I tap Play/Pause button in landscape orientation
    And I will see "Show Title" overlay in full screen view
    And I rotate the screen vertically
    Then screen has portrait orientation
    And I will see "All" dropdown box on Recordings page


  @regression @android
  Scenario: User can sleep/wake Recordings tab on mobile
    When I click "Recordings" button
    Then "Recordings" tab is highlighted
    And I put device to sleep
    Then I wake device


  @regression @android  @ios
  Scenario: User can delete recordings using swipe on Android, ios mobile
    And I click "Guide" button
    And I click "Recordings" button
    And I will see "All" dropdown box on Recordings page
    And I remember recording attributes for the "first" recording
    And I swipe to cancel "first" recording from Recordings tab
    And I will see "All" dropdown box on Recordings page
    And I refresh "Recordings" tab
    Then I dont see deleted recording on Recordings tab


  @regression @ios
  Scenario: ser can cancel scheduled recording(s) using half swipe on iOS mobile
    And I click "Guide" button
    And I click "Recordings" button
    And I will see "All" dropdown box on Recordings page
    And I remember recording attributes for the "first" recording
    And I cancel "first" recording with half swipe from Recordings tab
    And I see "Delete" button on Recordings tab
    And I tap "Delete" button to remove recording on Recordings tab
    When I refresh "Recordings" tab
    Then I dont see deleted recording on Recordings tab


  @regression @android
  Scenario: User can Undo when canceling scheduled recording on Android mobile
    And I click "Guide" button
    And I click "Recordings" button
    And I remember recording attributes for the "first" recording
    And I swipe to cancel "first" recording from Recordings tab
    When I tap "Undo" button on Recordings tab for android
    And I refresh "Recordings" tab
    Then I find "Undo" recording on "Recordings" Tab


  @regression @ios @no_android
  Scenario: User sees static sort/filter Recordings tab headers on mobile
    When I click "Recordings" button
    And I will see "All" dropdown box on Recordings page
    And I will see "SORT" button on Recordings page
    And the value in the dropdown is "NEWEST"
    And I scroll down Recordings tab
    And I tap sort button
    Then I will see sort menu
    And I tap "MENU_OLDEST" sort menu item
    When I scroll down Recordings tab
    And I will see sort button on Recordings page
    And I tap sort button
    And I tap "MENU_ATOZ" sort menu item
    When I scroll down Recordings tab
    And I will see sort button on Recordings page
    And I tap sort button
    And I tap "MENU_ZTOA" sort menu item
    When I scroll down Recordings tab
    And I will see sort button on Recordings page
    And the value in the dropdown is "Z-A"


  @regression @android @ios
  Scenario: User can dismiss Delete confirmation dialog with Back/Close button
    And I click "Guide" button
    And I click "Recordings" button
    And I will see sort button on Recordings page
    When I tap "first" recording on Recordings tab
    Then I will see Info dialog
    And I will see channel logo on Info Dialog
    When I dismiss Info Dialog
    Then I will see sort button on Recordings page


  @regression @android @ios
  Scenario: User can dismiss Delete confirmation dialog with rotation
    And I click "Guide" button
    And I click "Recordings" button
    And I will see sort button on Recordings page
    When I tap "first" recording on Recordings tab
    Then I will see Info dialog
    And I will see channel logo on Info Dialog
    And I rotate the screen horizontally
    Then screen has landscape orientation
    And I rotate the screen vertically
    Then screen has portrait orientation
    And I will see sort button on Recordings page


  @regression @android @ios @456
  Scenario: User can Watch from recording details
    And I click "Guide" button
    And I click "Recordings" button
    And I will see "All" dropdown box on Recordings page
    And I remember recording attributes for the "first" recording
    When I tap "first" recording on Recordings tab
    Then I will see Info dialog for Recordings
    And I tap "Watch" button on recordings InfoDialog
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    When I tap Play/Pause button
    And recording info is the same as I remember for "first" recording



  @regression @ios
  Scenario:User can Save to Library from recording details
    And I click "Guide" button
    And I click "Recordings" button
    And I will see "All" dropdown box on Recordings page
    When I tap "first" recording on Recordings tab
    Then I will see Info dialog for Recordings
    And I see "Save To Library" option button on Info Dialog for Recordings tab
    And I select "Save to Library" option button on Info Dialog for Recordings tab
    Then "Save To Library" button is selected on recordings Info Dialog


  @regression @android @ios
  Scenario:User can dismiss menu from Recordings tab on mobile
    And I click "Guide" button
    And I click "Recordings" button
    And I will see "All" dropdown box on Recordings page
    And I see top drop-down menu button
    And I click "Top Drop-Down Menu" button
    And I see "Customize Channels" menu item
    And I background an app
    When I bring app from background
    Then menu is dismissed


  @regression @android @ios  @396
  Scenario:User can use Recordings tab to return to top of the list
    And I click "Guide" button
    And I click "Recordings" button
    And I will see "All" dropdown box on Recordings page
    And I remember recording attributes for the "first" recording
    And I scroll down the list of recordings
    And I will see "All" dropdown box on Recordings page
    And I remember recording attributes at the top of the Recordings Tab
    And "last" recording attributes are different in comparison to the "first" recording
    When I click "Recordings" button
    And I remember recording attributes at the top of the Recordings Tab
    Then "last" recording attributes is the same as I remember for the "first" recording


  @regression @android @ios
  Scenario:User can delete a recording with swipe while watching, live playback begins after deleting
    When I click "Guide" button
    Then I see "Guide" tab
    And I see that "Last" button is disabled
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    When I tap Play/Pause button
    And I remember recording attributes for the "first" Full Screen View
    And I click "Recordings" button
    Then I watch "first" recording on Recordings tab
    And I see that "Last" button is enabled
    And I click "Guide" button
    And I click "Live" button
    And I click "Recordings" button
    And I swipe to cancel "first" recording from Recordings tab
    When I click "Recordings" button
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    When I tap Play/Pause button
    Then "last" Full Screen View attributes are the same as I remember for the "first" Full Screen View
    And I see that "Last" button is not enabled


  @regression @android @ios
  Scenario:User can Stop a recording, Last Live playback begins in viewer, "Recorded" no longer appears in overlay.
    And I click "Guide" button
    Then I see "Guide" tab
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    And I tap Play/Pause button
    And I remember recording attributes for the "first" Full Screen View
    Then I click "Recordings" button
    And I watch "first" recording on Recordings tab
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    When I tap Play/Pause button
    Then I see "RECORDED" attribute on Full Screen View
    And I click "Recordings" button
    When I tap Play/Pause button for "first" recording to stop playing
    And I click "Full Screen View" button
    And there is no Loading overlay in the viewer on Full Screen View
    And I tap Play/Pause button
    Then "last" Full Screen View attributes are the same as I remember for the "first" Full Screen View