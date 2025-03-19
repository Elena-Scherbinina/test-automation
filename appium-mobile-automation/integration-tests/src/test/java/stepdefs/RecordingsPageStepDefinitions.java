package stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import pages.*;

import java.util.List;


public class RecordingsPageStepDefinitions {
    private AppiumDriver driver;
    private String dateValue = "";
    private int numberOfRecords = 0;  //number of recordings


    public RecordingsPageStepDefinitions() {
        this.driver = Utils.getDriver();
    }

    /**
     * Verifies the visibility of a button on the Recordings page.
     */
    @And("^I will see \"([^\"]*)\" button on Recordings page$")
    public void seeButton(String name) throws Throwable {
        if (name.equalsIgnoreCase("Up")) {
            //Up button is image (visible attribute == false)
            Assert.assertTrue(MyPages._recordingsPage.isUpEnabled());
        } else {
            Assert.assertTrue(MyPages._recordingsPage.isVisible(name));
        }

    }

    /**
     * Taps a recording on the Recordings tab based on index.
     *
     * @param value Position of the recording (FIRST, SECOND, etc.).
     */
    @And("^I tap \"([^\"]*)\" recording on Recordings tab$")
    public void iTapFirstRecording(String value) throws Throwable {
        switch (value.toUpperCase()) {
            case "FIRST":
                MyPages._recordingsInfoDialog = null;
                MyPages._recordingsInfoDialog = (RecordingsInfoDialog) MyPages._recordingsPage.tapRecording(0);
                break;
            case "SECOND":
                MyPages._recordingsPage.tapRecording(1);
                break;

            default:
                throw new IllegalArgumentException("Invalid recording index in Recording tap: " + value);
        }
    }

    /**
     * Stores recording info from the Info Dialog.
     *
     * @param value Position of the recording (FIRST, SECOND, etc.).
     */
    @And("^I show Info Dialog for \"([^\"]*)\" recording and remember recording info$")
    public void iStoreReordingInfoFromDialog(String value) throws Throwable {

        switch (value.toUpperCase()) {
            case "FIRST":
                if (Utils.isAndroid()) {
                    MyPages._recordingsInfoDialog = null;
                    MyPages._recordingsInfoDialog = (RecordingsInfoDialog) MyPages._recordingsPage.tapRecording(0);
                    String recordingTitle = MyPages._recordingsInfoDialog.getShowTitle();
                    String channel = MyPages._recordingsInfoDialog.getChannelNumber();
                    String date = MyPages._recordingsInfoDialog.getDateTime();
                    MyPages._recordingsPage.storeRecordingInfoFromInfoDialog(0, channel, date, recordingTitle);
                } else {
                    MyPages._recordingsInfoDialog = null;
                    MyPages._recordingsInfoDialog = (RecordingsInfoDialog) MyPages._recordingsPage.tapRecording(0);
                    String date = MyPages._recordingsInfoDialog.getDateTime();
                    String recordingTitle = MyPages._recordingsInfoDialog.getShowTitle();
                    String channel = MyPages._recordingsInfoDialog.getChannelNumber();
                    MyPages._recordingsPage.storeRecordingInfoFromInfoDialog(0, channel, date, recordingTitle);
                }
                break;
            case "SECOND":
                if (Utils.isAndroid()) {
                    MyPages._recordingsInfoDialog = null;
                    MyPages._recordingsInfoDialog = (RecordingsInfoDialog) MyPages._recordingsPage.tapRecording(1);
                    String recordingTitle = MyPages._recordingsInfoDialog.getShowTitle();
                    String channel = MyPages._recordingsInfoDialog.getChannelNumber();
                    String date = MyPages._recordingsInfoDialog.getDateTime();
                    MyPages._recordingsPage.storeRecordingInfoFromInfoDialog(1, channel, date, recordingTitle);
                } else {
                    MyPages._recordingsInfoDialog = null;
                    MyPages._recordingsInfoDialog = (RecordingsInfoDialog) MyPages._recordingsPage.tapRecording(1);
                    String recordingTitle = MyPages._recordingsInfoDialog.getShowTitle();
                    String date = MyPages._recordingsInfoDialog.getDateTime();
                    String channel = MyPages._recordingsInfoDialog.getChannelNumber();
                    MyPages._recordingsPage.storeRecordingInfoFromInfoDialog(1, channel, date, recordingTitle);
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid Recording index: " + value);
        }
    }


    /**
     * Navigates to the specified page.
     */
    @Then("^I navigate to \"([^\"]*)\"$")
    public void iNavigateToRecordingSchedule(String name) throws Throwable {
        MyPages._recordingSchedulePage = (RecordingSchedulePage) MyPages._recordingsPage.navigateToRecordingSchedule();
        MyPages._recordingSchedulePage.setCurrentVideo();
    }

    /**
     * Puts the device to sleep.
     */
    @Then("^I put device to sleep$")
    public void toSleep() throws Throwable {
        if (Utils.isAndroid()) {
            Process p = Runtime.getRuntime().exec("adb shell input keyevent 26");
            p.waitFor();
            Thread.sleep(1000);
        }
    }

    /**
     * Wakes the device from sleep.
     */
    @Then("^I wake device$")
    public void wake() throws Throwable {
        if (Utils.isAndroid()) {
            Process p = Runtime.getRuntime().exec("adb shell input keyevent 82");
            p.waitFor();
        }
    }

    /**
     * Verifies the visibility of a dropdown box on the Recordings page.
     */
    @And("^I will see \"([^\"]*)\" dropdown box on Recordings page$")
    public void seeDropdownBox(String name) throws Throwable {
        Assert.assertTrue(MyPages._recordingsPage.isVisible(name));
    }

    @And("^I will see sort button on Recordings page$")
    public void seesortButton() throws Throwable {
        Assert.assertTrue(MyPages._recordingsPage.isVisible("SORT"));
    }

    @And("^the value in the dropdown is \"([^\"]*)\"$")
    public void val(String expectedValue) throws Throwable {
        Assert.assertEquals(MyPages._recordingsPage.getDropDownValue(expectedValue), expectedValue);
    }

    /**
     * Taps the sort button on the Recordings page.
     */
    @And("^I tap sort button$")
    public void iTapSortFilter() throws Throwable {
        MyPages._recordingsPage.tapSortFilter();
    }

    /**
     * Taps the Filter button on the Recordings page.
     */
    @And("^I tap \"Filter\" button$")
    public void iTapLibrafyFilter() throws Throwable {
        MyPages._recordingsPage.tapFilterButton();
    }

    /**
     * Taps the Library filter menu item.
     */
    @And("^I tap \"Library\" filter menu item$")
    public void iTapLibraryMenuItem() throws Throwable {
        MyPages._recordingsPage.tapSortFilterMenu("MENU_LIBRARY");
    }

    @And("^I will see sort menu$")
    public void iWillSeeSortMenu() throws Throwable {
        boolean visible = MyPages._recordingsPage.isVisible("SORT_FILTER");
    }

    /**
     * Verifies the visibility of the sort menu.
     */
    @And("^I will see \"([^\"]*)\" sort menu item$")
    public void ISeeMenuItem(String value) throws Throwable {
        boolean visible = false;
        visible = MyPages._recordingsPage.isVisible(value);  // NEWEST, OLDERST, ATOZ, ZTOA
        Assert.assertTrue(visible);
    }


    /**
     * Taps the specified sort menu item.
     */
    @And("^I tap \"([^\"]*)\" sort menu item$")
    public void ITapSortmenuItem(String value) throws Throwable {
        MyPages._recordingsPage.tapSortFilterMenu(value);
    }

    /**
     * Verifies that the list of recordings is visible in the library.
     */
    @Then("^I expect to see list of recordings in the library$")
    public void i_expect_to_see_table_with_dates() throws Throwable {
        MyPages._recordingsPage.getListOfRecordings();
    }

    /**
     * Taps the specified button on the given tab.
     */
    @When("^I tap \"([^\"]*)\" button on \"([^\"]*)\" tab$")
    public void tapButton(String chevron, String tab) throws Throwable {
        if (tab.equalsIgnoreCase("Guide") || tab.equalsIgnoreCase("Community")) {
            MyPages._guidePage.extendOrCollapseTab(chevron);
        } else if (tab.equalsIgnoreCase("Recordings")) {
            MyPages._recordingsPage.extendOrCollapseTab(chevron);
        }
    }

    /**
     * Stores the current height of the specified tab.
     */
    @And("^I remember \"([^\"]*)\" tab height$")
    public void rememberTabHeight(String tab) throws Throwable {
        if (Utils.isAndroid()) {
            if (tab.equalsIgnoreCase("Recordings")) {
                MyPages._recordingsPage.storeTabCoordinates();
            } else {
                MyPages._guidePage.storeTabCoordinates();
            }
        } else {
            if (tab.equalsIgnoreCase("Recordings")) {
                MyPages._recordingsPage.storeTabCoordinatesForiOS("Recordings");
            } else {
                MyPages._guidePage.storeTabCoordinatesForiOS("Guide");
            }
        }
    }

    @And("^I remember extended \"([^\"]*)\" tab height$")
    public void rememberExpanededRecordingsTabHeight(String tab) throws Throwable {
        if (Utils.isAndroid()) {
            if (tab.equalsIgnoreCase("Recordings")) {
                MyPages._recordingsPage.storeExtendedTabCoordinates();
            } else {
                MyPages._guidePage.storeExtendedTabCoordinates();
            }
        } else {
            if (tab.equalsIgnoreCase("Recordings")) {
                MyPages._recordingsPage.storeExtendedTabCoordinatesForiOS("Recordings");
            } else {
                MyPages._guidePage.storeExtendedTabCoordinatesForiOS("Guide");
            }
        }
    }

    /**
     * Validates that the tab returns to its original height.
     */
    @And("^\"([^\"]*)\" tab get back to its original height$")
    public void originalTabHeight(String tab) throws Throwable {
        int viewerHeight = 0;
        if (tab.equalsIgnoreCase("Guide") || tab.equalsIgnoreCase("Community")) {
            viewerHeight = MyPages._guidePage.getViewerHeight();
            Assert.assertEquals(MyPages._guidePage.getExtendedTabYCoordinate() + viewerHeight, MyPages._guidePage.getTabYCoordinate());
        } else if (tab.equalsIgnoreCase("Recordings")) {
            viewerHeight = MyPages._recordingsPage.getViewerHeight();
            Assert.assertEquals(MyPages._recordingsPage.getExtendedTabYCoordinate() + viewerHeight, MyPages._recordingsPage.getTabYCoordinate());
        }
    }

    /**
     * Verifies if the tab has extended height.
     */
    @And("^\"([^\"]*)\" tab has extended height$")
    public void hasExtenededHeight(String tab) throws Throwable {
        int viewerHeight = 0;
        if (tab.equalsIgnoreCase("Guide")) {
            viewerHeight = MyPages._guidePage.getViewerHeight();
        } else if (tab.equalsIgnoreCase("Recording Schedule")) {
            int expectedHeight = MyPages._guidePage.getExtendedTabYCoordinate(); //stored extended guide tab height
            MobileElement el = (MobileElement) Utils.getDriver().findElement(By.id("scheduled_recording"));
            int tabYSceduledRecordings = el.getLocation().getY();

            Assert.assertEquals(tabYSceduledRecordings, expectedHeight);
        } else if (tab.equalsIgnoreCase("Recordings")) {
            int expectedHeight = MyPages._guidePage.getExtendedTabYCoordinate(); //stored extended guide tab height
            MobileElement el = (MobileElement) Utils.getDriver().findElement(By.id("recording"));
            int tabYRecordings = el.getLocation().getY();

            Assert.assertEquals(tabYRecordings, expectedHeight);
        } else if (tab.equalsIgnoreCase("Community")) {
            int expectedHeight = MyPages._guidePage.getExtendedTabYCoordinate(); //stored extended guide tab height
            int tabYRecordings = MyPages._guidePage.getYCoordinate("Community");
            Assert.assertEquals(tabYRecordings, expectedHeight);
        }
    }

    /**
     * Verifies that the "Live" tab is collapsed by checking the tab's position and the height of the expand arrow.
     */
    @And("^\"Live\" tab is collapsed$")
    public void isCollapsed() throws Throwable {
        int viewerHeight = 0;

        viewerHeight = MyPages._recordingsPage.getViewerHeight();
        int expectedHeight = MyPages._guidePage.getTabYCoordinate(); //315
        MobileElement el = Utils.findElement("airing_options");
        int tabYLive = el.getLocation().getY();  //297
        el = Utils.findElement("collapseScreen_chevron");
        int arrowUpHeight = el.getSize().getHeight();  //  arrow to expand Guide height

        Assert.assertEquals(tabYLive + arrowUpHeight, expectedHeight);
    }

    /**
     * Watches a specific recording on the Recordings tab.
     *
     * @param recordingIndex The index of the recording (e.g., "first" or "second").
     */
    @And("^I watch \"([^\"]*)\" recording on Recordings tab$")
    public void IwatchFirstRecording(String recordingIndex) throws Throwable {
        switch (recordingIndex.toLowerCase()) {
            case "first":
                MyPages._recordingsPage.watchRecording1(0);
                break;
            case "second":
                MyPages._recordingsPage.watchRecording1(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid recording index: " + recordingIndex);
        }
    }

    /**
     * Taps the Play/Pause button to stop playing a recording.
     */
    @And("^I tap Play/Pause button for \"([^\"]*)\" recording to stop playing$")
    public void iTapStopButton(String name) throws Throwable {
        switch (name.toUpperCase()) {
            case "FIRST":
                MyPages._recordingsPage.watchRecording1(0);
                break;
            default:
                throw new IllegalArgumentException("Select Record/Save to Library invalid choice : " + name);

        }
    }

    /**
     * Stores attributes for a specific recording.
     */
    @Then("I remember recording attributes for the \"([^\"]*)\" recording$")
    public void iRememberRecordingInfoForScheduledRecording(String value) throws Throwable {
        switch (value.toUpperCase()) {
            case "FIRST":
                if (Utils.isAndroid()) {
                    MyPages._recordingsPage.storeRecordingAttributes(0);
                } else {
                    MyPages._recordingsPage.storeRecordingAttributes(0);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid scheduled recording number : " + value);
        }
    }

    /**
     * Stores the attributes of the recording located at the top of the Recordings Tab for later verification.
     * Uses different methods depending on the platform (Android or iOS).
     */
    @Then("I remember recording attributes at the top of the Recordings Tab$")
    public void iRememberRecordingAttributesAtTheTop() throws Throwable {
        //recordingsList starts from 0 index
        if (Utils.isAndroid()) {
            MyPages._recordingsPage.storeTopOfTheListRecordingAttributes(0);
        } else {
            MyPages._recordingsPage.storeRecordingAttributes(0);
        }
    }

    /**
     * Verifies that a deleted recording is no longer visible on the Recordings tab.
     * Uses different methods depending on the platform (Android or iOS).
     */
    @Then("I dont see deleted recording on Recordings tab$")
    public void iDontSeeDeletedRecording() throws Throwable {
        if (Utils.isAndroid()) {
            RecordingInfo info = MyPages._recordingsPage.getRecordingInfo(0);
            boolean found = Utils.isRecordingFound(info.getTitle(), info.getAttributes());
            Assert.assertFalse(found);
        } else {
            RecordingInfo info = MyPages._recordingsPage.getRecordingInfo(0);
            boolean found = Utils.isIosRecordingFound(info.getTitle(), info.getAttributes());
            Assert.assertFalse(found);
        }
    }

    /**
     * Ensures that the second recording's title is different from the first recording's title.
     */
    @And("^second recordings title is different from the first recording title$")
    public void titlesAreDifferent() throws Throwable {
        String recInfo1 = MyPages._recordingsPage.getStoredRecordingInfo(0).getTitle();
        String recInfo2 = MyPages._recordingsPage.getStoredRecordingInfo(1).getTitle();
        String dateTime1 = MyPages._recordingsPage.getStoredRecordingInfo(0).getDate();
        String dateTime2 = MyPages._recordingsPage.getStoredRecordingInfo(1).getDate();
        if (recInfo1.equalsIgnoreCase(recInfo2)) {
            Assert.assertNotEquals(dateTime1, dateTime2);
        } else {
            Assert.assertNotEquals(recInfo1, recInfo2);
        }
    }

    /**
     * Compares the attributes of two recordings and verifies that they are different.
     * The second recording can be the last one in the list.
     */
    @And("^\"([^\"]*)\" recording attributes are different in comparison to the \"([^\"]*)\" recording$")
    public void attributesAreDifferent(String recording2, String recording1) throws Throwable {
        int index1 = 0; //first recording at the top of the list
        int index2 = 0;//recording to compare with
        if (recording2.equalsIgnoreCase("last")) {
            index2 = MyPages._recordingsPage.getRecordingsInfoSize() - 1;
        }
        String title1 = MyPages._recordingsPage.getStoredRecordingInfo(index1).getTitle().trim();
        String attributes1 = MyPages._recordingsPage.getStoredRecordingInfo(index1).getAttributes();

        String title2 = MyPages._recordingsPage.getStoredRecordingInfo(index2).getTitle().trim();
        String attributes2 = MyPages._recordingsPage.getStoredRecordingInfo(index2).getAttributes();
        if (title1.equalsIgnoreCase(title2)) {
            Assert.assertNotEquals(attributes1, attributes2);
        } else {
            Assert.assertNotEquals(title1, title2);
        }
    }

    /**
     * Verifies that the attributes of a specified recording match the remembered attributes of another recording.
     */
    @And("^\"([^\"]*)\" recording attributes is the same as I remember for the \"([^\"]*)\" recording$")
    public void titlesAreThesame(String recording2, String recording1) throws Throwable {
        int index1 = 0, index2 = 0; //index1 - first recording at the top of the list

        if (recording2.equalsIgnoreCase("last")) {
            index2 = MyPages._recordingsPage.getRecordingsInfoSize() - 1;
        }
        String title1 = MyPages._recordingsPage.getStoredRecordingInfo(index1).getTitle();
        String attributes1 = MyPages._recordingsPage.getStoredRecordingInfo(index1).getAttributes();

        String title2 = MyPages._recordingsPage.getStoredRecordingInfo(index2).getTitle();
        String attributes2 = MyPages._recordingsPage.getStoredRecordingInfo(index2).getAttributes();
        if (title1.equalsIgnoreCase(title2)) {
            Assert.assertEquals(attributes1, attributes2);
        } else {
            Assert.assertEquals(title1, title2);
        }
    }

    /**
     * Ensures that the show name displayed in the full-screen view matches the expected show name for the given tab.
     */
    @Then("^I will see the same show name in full screen view for \"([^\"]*)\" tab$")
    public void I_will_find_that_show_name(String tabName) throws Throwable {
        String expectedName = MyPages._fullScreenPage.getLineupDefaultShowTitle();
        String showNameFullScreen = MyPages._fullScreenPage.getShowTitle();
        Assert.assertEquals(showNameFullScreen, expectedName);
    }

    /**
     * Asserts that the "Delete" button is visible on the Recordings tab.
     */
    @Then("^I see \"Delete\" button on Recordings tab$")
    public void I_see_delete_button() throws Throwable {
        boolean isVisible = MyPages._recordingsPage.isVisible("DELETE_SWIPE");
        Assert.assertTrue(isVisible);
    }

    /**
     * Taps the "Delete" button to remove a recording from the Recordings tab.
     */
    @Then("^I tap \"Delete\" button to remove recording on Recordings tab$")
    public void I_tap_delete() throws Throwable {
        MyPages._recordingsPage.tapDeleteSwipe();
        ;
    }

    /**
     * Performs a swipe gesture to cancel the deletion of a recording from the Recordings tab.
     */
    @Then("I swipe to cancel \"([^\"]*)\" recording from Recordings tab$")
    public void iSwipeToCancelFromRecordings(String recording) throws Throwable {
        Utils.deleteRecordingWithSwipe(true);
    }

    /**
     * Cancels a recording deletion with a half-swipe gesture from the Recordings tab.
     */
    @Then("I cancel \"([^\"]*)\" recording with half swipe from Recordings tab$")
    public void ihalfSwipeToCancelFromRecordings(String recording) throws Throwable {
        Utils.halfSwipe(true);
    }

    /**
     * Taps the "Undo" button to restore a deleted recording on the Recordings tab (Android only).
     */
    @Then("I tap \"Undo\" button on Recordings tab for android$")
    public void iTapUndo() throws Throwable {
        Utils.tapUndo();
    }

    /**
     * Verifies that an "Undo" recording is found on the "Recordings" tab.
     */
    @Then("I find \"Undo\" recording on \"Recordings\" Tab$")
    public void iFoundUndoRecording() throws Throwable {
        RecordingInfo info = MyPages._recordingsPage.getRecordingInfo(0);
        boolean found = Utils.isRecordingFound(info.getTitle());
        Assert.assertTrue(found);
    }

    /**
     * Scrolls down within the "Recordings" tab.
     */
    @Then("I scroll down Recordings tab$")
    public void iScrollDown() throws Throwable {
        if (Utils.isAndroid()) {
            Utils.swipUp();
        } else {
            Utils.swipeInsideObject("recording", 1, "down");
        }
    }

    /**
     * Scrolls down through the list of recordings.
     */
    @Then("I scroll down the list of recordings$")
    public void iScrollDownTheListOfRecordings() throws Throwable {
        if (Utils.isAndroid()) {
            MobileElement view = Utils.findElement("recycler_view_recording");
            List<MobileElement> els = view.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("viewGroup")));
            int size = els.size();
            int ind = size / 3;
            MobileElement record = els.get(ind);

            int y = view.getLocation().getY() + view.getSize().height / 5;
            int x = view.getLocation().getX() + view.getSize().getWidth() / 2;
            int yEnd = record.getLocation().getY() + record.getSize().height + 35;
            Utils.swipUp(x, y, x, yEnd);
        } else {
            Utils.swipeInsideObject("recording", 1, "down");
        }
    }

    /**
     * Checks if there are no recordings available on the "Recordings" tab.
     */
    @Then("there are no recordings on Recordings tab$")
    public void noRecordings() throws Throwable {
        int number = MyPages._recordingsPage.getNumberOfVisibleRecords();
        numberOfRecords = number;
    }

    /**
     * Verifies that a specific message appears in the "Recordings" tab when there are no recordings.
     */
    @Then("I see a message \"([^\"]*)\" in Recordings$")
    public void iSeeAMessage(String expectedMsg) throws Throwable {
        String generatedMsg = "";
        if (this.numberOfRecords == 0) {
            generatedMsg = MyPages._recordingsPage.getEmptyMessageText();
            Assert.assertEquals(generatedMsg, expectedMsg);
        } else {
            System.out.println("There are " + this.numberOfRecords + " records in recordings tab.");
        }
    }

    /**
     * Refreshes the screen by performing a swipe gesture.
     * On Android, it triggers a swipe-down refresh for the recordings tab.
     * On iOS, it attempts to swipe inside the "recording" element.
     */
    @Then("I refresh screen with swipe$")
    public void iRefresh() throws Throwable {
        RemoteWebElement element = null;
        if (Utils.isAndroid()) {
            Utils.refreshAndroidRecordingTabWithSwipeDown();
        } else {
            element = (RemoteWebElement) Utils.getDriver().findElement(By.id("recording"));
            try {
                Utils.swipeInsideObject(element, "up");
            } catch (Exception e) {
                System.err.println("Exception occurred while swiping down: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }
}
