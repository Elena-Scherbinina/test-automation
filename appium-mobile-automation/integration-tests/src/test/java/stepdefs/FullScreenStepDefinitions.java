package stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.testng.Assert;
import pages.*;


public class FullScreenStepDefinitions {
    private AppiumDriver driver;

    public FullScreenStepDefinitions() {
        this.driver = Utils.getDriver();
    }


    /**
     * Verifies that the specified channel (e.g., "channel1") has loaded into the Full Screen view.
     * It compares the expected channel number (retrieved from the Guide page) with the channel
     * number displayed in Full Screen.
     */
    @Then("^I see that \"([^\"]*)\" has loaded into Full Screen View$")
    public void channel1LoadedIntoTheViewer(String channel) throws Throwable {
        String expectedChannel = "";//5.1 CBS 5 (remembered)
        String playingChannel = "";  //
        expectedChannel = MyPages._guidePage.getFirstChannelNumber();
        if (channel.equalsIgnoreCase("channel1")) {
            playingChannel = MyPages._fullScreenPage.getChannelNumber().split(" ")[1];  // ios"5.1. CBS5"
        }

        Assert.assertEquals(playingChannel, expectedChannel);
    }


    /**
     * Verifies that there is no loading overlay present in the Full Screen view.
     * Uses a platform-specific element ID and checks if the element is invisible within a timeout.
     */
    @Then("^there is no Loading overlay in the viewer on Full Screen View$")
    public void noLoadingverlayInTheViewer() throws Throwable {
        boolean resultPlayPause = true;
        String id = "";
        if (Utils.isAndroid())   {
            id = Utils.getId("loadingProgress_image");
        }
        else{
            id = Utils.getId("loadingProgress_image");
            }
        boolean absent = Utils.isElementInVisible(id, 15);
        Assert.assertTrue(absent);
    }

    /**
     * Sstores the show name for the default lineup channel.
     * This information can later be used for verifying consistency.
     */
    @And("^I will remember show name for default lineup channel$")
    public void rememberShowName() throws Throwable {
        MyPages._fullScreenPage.setLineupDefaultShowTitle();

    }

    /**
     * Ensures that the Play/Pause button is visible.
     * If it is not visible, a tap is performed to reveal it.
     */
    @And("^Play/Pause button is visible$")
    public void itIsVisible() throws Throwable {
        boolean isShown = MyPages._fullScreenPage.isShown("Play/Pause");
        //if not tap screen
        if (!isShown) {
            Utils.playPauseButtonClick(6);
        } else {
            System.out.println("Play/Pause is not shown");
        }
    }


    /**
     * Taps the Play/Pause button to start channel playback, if the button is visible.
     */
    @And("^I tap \"Play\" button to play channel$")
    public void tapPlay() throws Throwable {
        boolean isShown = MyPages._fullScreenPage.isShown("Play/Pause");
        //if not tap screen
        if (isShown) {
            MyPages._fullScreenPage.buttonClick("Play/Pause");
        } else {
            System.out.println("Play/Pause is not shown");
        }
    }

    /**
     * Stores the current show name displayed in the Full Screen view.
     * This method can be used later to verify that the show name remains consistent.
     */
    @And("^I will remember show name for Full Screen View$")
    public void rememberFullScreenShowName() throws Throwable {
        MyPages._fullScreenPage.setLineupDefaultShowTitle();
    }


    /**
     * Stores the current channel number displayed in the Full Screen view.
     */
    @And("^I will remember channel number for Full Screen View$")
    public void rememberFullScreenChannelNumberName() throws Throwable {
        MyPages._fullScreenPage.setChannelNumber();
    }


    /**
     * Verifies that the specified overlay is visible in the Full Screen view.
     * For iOS, additional checks are made to ensure specific overlays are enabled.
     */
    @And("^I will see \"([^\"]*)\" overlay in full screen view$")
    public void iWillSeeInFullSCreenView(String name) throws Throwable {
        MobileElement el = null;
        boolean b = false;
        if (Utils.isAndroid()) {
            b = MyPages._fullScreenPage.isShown(name);
        }else{
            b = MyPages._fullScreenPage.isShown(name);
            if (name.equalsIgnoreCase("30 sec FORWARD")) {
                el = Utils.findElement("player_seekForwardButton");
                b = el.isEnabled();
            }else if (name.equalsIgnoreCase("30 sec BACK")){
                el = Utils.findElement( "player_seekBackButton");
                b = el.isEnabled();
            }else if (name.equalsIgnoreCase("Collapse Full Screen Chevron")){
                el = Utils.findElement("fullScreen_lowerChevron");
                b = el.isEnabled();
            } else if (name.equalsIgnoreCase("Show Title")) {
                el = Utils.findElement("player_title");
                b = el.isEnabled();
            }
        }
            Assert.assertTrue(b);
    }


    /**
     * Verifies that the specified overlay is not visible in the Full Screen view.
     * On iOS, different element locators are used based on the overlay's name.
     */
    @And("^I dont see \"([^\"]*)\" overlay in full screen view$")
    public void iDontSeeInFullSCreenView(String name) throws Throwable {
        MobileElement el = null;
        boolean b = false;
        if (Utils.isAndroid()) {
             b = MyPages._fullScreenPage.isShown(name);
        }else{
            if (name.equalsIgnoreCase("Show Title")) {
                b = Utils.isElementInVisible(xConsts.APPLE_IDS.get("player_title"),6);
            }else if (name.equalsIgnoreCase("Player Slider")){
                b = Utils.isElementInVisible(xConsts.APPLE_IDS.get("player_slider"),6);
            }else if (name.equalsIgnoreCase("CC")){
                b = Utils.isElementInVisible(xConsts.APPLE_IDS.get("player_ccButon"),6);
            }else if (name.equalsIgnoreCase("Player Mute Button")){
                b = Utils.isElementInVisible(xConsts.APPLE_IDS.get("player_muteButton"),6);
            }else if (name.equalsIgnoreCase("Slider Time Label")){
                b = Utils.isElementInVisible(xConsts.APPLE_IDS.get("player_ellapsedTime"),6);
            }else if (name.equalsIgnoreCase("30 sec FORWARD")){
                b = Utils.isElementInVisible(xConsts.APPLE_IDS.get("player_seekForwardButton"),6);
            }else if (name.equalsIgnoreCase("30 sec BACK")){
                b = Utils.isElementInVisible(xConsts.APPLE_IDS.get("player_seekBackButton"),6);
            }

        }
        Assert.assertTrue(b);
    }

    /**
     * Waits until all control elements (e.g., title, slider, CC button) in the Full Screen view disappear.
     * This is useful to verify that the UI auto-hides controls after a period of inactivity.
     */
    @And("^I wait in full screen view untill controls disappear$")
    public void iWaitUNtillControlsDisappear() throws Throwable {
        boolean b = false;
        if (Utils.isAndroid()) {
                b = MyPages._fullScreenPage.isShown(name);
        }else{
                b = Utils.isElementInVisible(Utils.getId("player_title"),6);
                Assert.assertTrue(b);
                b = Utils.isElementInVisible(Utils.getId("player_slider"),6);
                Assert.assertTrue(b);
                b = Utils.isElementInVisible(Utils.getId("player_ccButton"),6);
                Assert.assertTrue(b);
                b = Utils.isElementInVisible(Utils.getId("player_muteButton"),6);
                Assert.assertTrue(b);
                b = Utils.isElementInVisible(Utils.getId("player_ellapsedTime"),6);
                Assert.assertTrue(b);
                b = Utils.isElementInVisible(Utils.getId("player_seekForwardButton"),6);
                Assert.assertTrue(b);
                b = Utils.isElementInVisible(Utils.getId("player_seekBackButton"),6);
                Assert.assertTrue(b);
            }
    }

    /**
     * Ensures that the Full Screen view is instantiated.
     * If not already available, a new FullScreenPage object is created.
     */
    @And("^I see full screen view$")
    public void iSeeFullSCreenView() throws Throwable {
        if (MyPages._fullScreenPage != null) {
            MyPages._fullScreenPage =  MyPages._fullScreenPage;
        } else {
            MyPages._fullScreenPage = new FullScreenPage(driver);
        }
    }

    /**
     * Remembers the recording attributes for the specified Full Screen view.
     * Currently supports only the "FIRST" recording.
     */
    @Then("I remember recording attributes for the \"([^\"]*)\" Full Screen View$")
    public void iRememberInfoForFirstFullScreenView(String value) throws Throwable {
        switch (value.toUpperCase()) {
            case "FIRST":
                    MyPages._fullScreenPage.storeRecordingAttributes(0);
                    break;
            default:
                throw new IllegalArgumentException("Invalid scheduled recording number : " + value);
        }
    }

    /**
     * Remembers the show attributes for the specified Full Screen view.
     * Currently supports only the "FIRST" view.
     */
    @Then("I remember attributes for the \"([^\"]*)\" Full Screen View$")
    public void iRememberInfoForTheFirstFullScreenView(String value) throws Throwable {
        switch (value.toUpperCase()) {
            case "FIRST":
                MyPages._fullScreenPage.storeShowAttributes(0);
                break;
            default:
                throw new IllegalArgumentException("Invalid value in - I remember attributes : " + value);
        }
    }


    /**
     * Verifies that the Full Screen view attributes match the previously stored recording attributes.
     * This method compares either the show title or other attributes based on the match.
     */
    @And("^\"([^\"]*)\" Full Screen View attributes are the same as I remember for the \"([^\"]*)\" Full Screen View$")
    public void FSAttributesAreThesame(String last, String first) throws Throwable {
        System.out.println("Full Screen View attributes are the same ...");
        int index1 = 0, index2 = 0;

        String title1 = MyPages._fullScreenPage.getStoredAttributesInfo(index1).getTitle();
        String attributes1 = MyPages._fullScreenPage.getStoredAttributesInfo(index1).getAttributes();

        String title2 = MyPages._fullScreenPage.getShowTitle();
        String attributes2 = MyPages._fullScreenPage.getAttributes();

        if (title1.equalsIgnoreCase(title2)) {
            Assert.assertEquals(attributes2, attributes1);
        }else{
            Assert.assertEquals(title1, title2);
        }
    }

    /**
     * Verifies that the Full Screen view attributes in landscape mode match the previously remembered attributes.
     * Adjusts for differences in attribute display formatting between portrait and landscape modes.
     */
    @And("^\"([^\"]*)\" Full Screen View attributes are the same as I remember for the \"([^\"]*)\" Full Screen View in landscape mode$")
    public void portraitAndLandscapeAttributesAreTheSame(String last, String first) throws Throwable {
        System.out.println("Full Screen View attributes are the same ...");
        int index1 = 0, index2 = 0;

        String title1 = MyPages._fullScreenPage.getStoredAttributesInfo(index1).getTitle();
        String attributes1 = MyPages._fullScreenPage.getStoredAttributesInfo(index1).getAttributes();

        String title2 = MyPages._fullScreenPage.getShowTitle();
        System.out.println("title2 : " + title2);
        String attributes2 = MyPages._fullScreenPage.getAttributes();
        String a[] = attributes2.split("\n");
        attributes2 = a[0] + "  /  "  + a[1];

        if (title1.equalsIgnoreCase(title2)) {
            Assert.assertEquals(attributes2, attributes1);
        }else{
            Assert.assertEquals(title1, title2);
        }
    }



    /**
     * Verifies that the "RECORDED" attribute is present on the Full Screen view.
     */
    @Then("^I see \"RECORDED\" attribute on Full Screen View$")
    public void I_see_recorded_attribute_on_FullScreenView() throws Throwable {
        boolean isPresent = MyPages._fullScreenPage.isRecordedAttributePresent();
        Assert.assertTrue(isPresent);
    }


    @Then("^I tap \"More\" button in Full Screen View$")
    public void I_tap_more() throws Throwable {
        MyPages._infoDialog = (InfoDialog) MyPages._fullScreenPage.buttonClick("MORE BUTTON");
    }

    /**
     * Taps the specified overlay in the Full Screen view.
     * Supports overlays such as "30 SEC BACK", "30 SEC FORWARD", "MORE BUTTON", "MUTE BUTTON", "PLAY_BUTTON", and "COLLAPSE FULL SCREEN".
     * Captures a screenshot if an exception occurs.
     *
     * @param name the overlay name to be tapped
     */
    @Then("^I tap \"([^\"]*)\" overlay in full screen view$")
    public void    tapOverlay(String name) throws Throwable {
        System.out.println("name : " + name);
        try {
            switch (name.toUpperCase()) {
                case "30 SEC BACK":
                    MyPages._fullScreenPage.buttonClick("30 SEC BACK");
                    break;
                case "30 SEC FORWARD":
                    MyPages._fullScreenPage.buttonClick("30 SEC FORWARD");
                    break;
                case "MORE BUTTON":
                    MyPages._fullScreenPage.buttonClick("MORE BUTTON");
                    break;
                case "MUTE BUTTON":
                    MyPages._fullScreenPage.buttonClick("MUTE BUTTON");
                    break;
                case "PLAY_BUTTON":
                    MyPages._fullScreenPage.buttonClick("PLAY/PAUSE");
                    break;
                case "COLLAPSE FULL SCREEN":
                    MyPages._fullScreenPage.buttonClick("COLLAPSE FULL SCREEN");
                    break;
                 default:
                    throw new IllegalArgumentException("Invalid element: " + name);
            }
        }catch(Exception e){
            Utils.takeScreenshot(name);
            throw e;
        }
    }


    @And("^I unmute playback if it is muted$")
    public void iUnmutePlayback() throws Throwable {
        boolean muted = MyPages._fullScreenPage.isMuted();
        if (muted)
        {
            MyPages._fullScreenPage.buttonClick("MUTE BUTTON");
        }
    }

    @And("^I mute playback if it is unmuted$")
    public void imutePlayback() throws Throwable {
        boolean muted = MyPages._fullScreenPage.isMuted();
        if (!muted)
        {
            MyPages._fullScreenPage.buttonClick("MUTE BUTTON");
        }
    }

    @And("^playback is muted$")
    public void isPlaybackMuted() throws Throwable {
        boolean muted = MyPages._fullScreenPage.isMuted();
        Assert.assertTrue(muted);

    }

    @And("^playback is unmuted$")
    public void isPlaybackUnMuted() throws Throwable {
        boolean muted = MyPages._fullScreenPage.isMuted();
        Assert.assertFalse(muted);

    }

    /**
     * Taps the specified overlay in landscape mode.
     * Locates the appropriate element based on the overlay name and performs a tap action.
     */
    @Then("^I tap \"([^\"]*)\" overlay in landscape mode$")
    public void tapInLandscapeMode(String name) throws Throwable {
        System.out.println("name : " + name);
        MobileElement el = null;
        try {
            switch (name.toUpperCase()) {
                case "30 SEC BACK":
                    el = Utils.findElement("player_seekBackButton");
                    Utils.tapElement(el);
                    System.out.println("30 SEC BACK clicked");
                    break;
                case "30 SEC FORWARD":
                    el = Utils.findElement("player_seekForwardButton");
                    Utils.tapElement(el);
                    System.out.println("30 SEC FORWARD clicked");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid element: " + name);
            }
        } catch (Exception e) {
            Utils.takeScreenshot(name);
            throw e;
        }
    }


    /**
     * Swipes the Full Screen view to the left a specified number of times.
     * For iOS, performs a swipe on the player controls view.
     */
    @Then("^I will swipe Full Screen View left (\\d+) times$")
    public void swipeFullSCreenViewLeft(int arg) throws Throwable {
        if (Utils.isAndroid()) {

        } else {
            Utils.swipeInsideObject("player_playerControlsView",5, "left");
        }
    }

    /**
     * Verifies that the channel value displayed in the Full Screen view corresponds to the last channel
     * for the given lineup.
     */
    @Then("I see that Full Screen View channel value corresponds to the last channel for \"([^\"]*)\" lineup$")
    public void correspondsToLastChannel(String lineup) throws Throwable {
        String channelNumber = MyPages._fullScreenPage.getChannelNumber().trim();
        System.out.println("Channel number : "+ channelNumber);
        String expectedChannel = xConsts.AUTOTEST_LAST_CHANNEL;
        Assert.assertEquals(channelNumber, expectedChannel);
    }
    /**
     * Verifies that the channel value in the Full Screen view corresponds to the first channel
     * for the given lineup.
     */
    @Then("I see that Full Screen View channel value corresponds to the first channel for \"([^\"]*)\" lineup$")
    public void nliveTabChannel(String lineup) throws Throwable {
        String channelNumber = MyPages._fullScreenPage.getChannelNumber().trim();
        System.out.println("Channel number : "+ channelNumber);
        String expectedChannel = xConsts.AUTOTEST_FIRST_CHANNEL;
        Assert.assertEquals(channelNumber, expectedChannel);
    }
    /**
     * Verifies that the channel value in the Full Screen view corresponds to the second channel for the given lineup.
     */
    @Then("I see that Full Screen View channel value corresponds to the second channel for \"([^\"]*)\" lineup$")
    public void FullScreenChannelValue(String lineup) throws Throwable {
        String channelNumber = MyPages._fullScreenPage.getChannelNumber().trim();
        System.out.println("Channel number : "+ channelNumber);
        String expectedChannel = "7.1 AZTV7";
        Assert.assertEquals(channelNumber, expectedChannel);
    }

    /**
     * Verifies that the channel value displayed via a chevron overlay (left or right) in Full Screen view
     * corresponds to the expected channel.
     */
    @Then("I see that Full Screen View \"([^\"]*)\" chevron channel corresponds to the \"([^\"]*)\" channel$")
    public void FullScreenChannelValueCorrepondToVal(String direction,String expectedChannel) throws Throwable {
        String channelNumber = "";
        if (direction.equalsIgnoreCase("right")) {
            channelNumber = MyPages._fullScreenPage.getRightChevronChannelName();
        }else{
            channelNumber = MyPages._fullScreenPage.getLeftChevronChannelName();
        }
        System.out.println("full screen channel number : "+ channelNumber);
        Assert.assertEquals(channelNumber, expectedChannel);
    }

    /**
     * Verifies that the show name in the Full Screen view corresponds to the show name from the Live tab.
     */
    @Then("I see that Full Screen View show name corresponds to Live tab show name$")
    public void FullScreenShiwNameCorespondsToLive() throws Throwable {
        String showName = MyPages._fullScreenPage.getShowTitle();
        String expectedName = MyPages._infoDialog.getShowName();
        System.out.println("Show name : "+ showName);
        System.out.println("expectedName : "+ expectedName);
        Assert.assertEquals(showName, expectedName);
    }


    /**
     * Verifies that the channel value in the Full Screen view matches the provided expected channel value.
     */
    @Then("I see that Full Screen View channel value corresponds to the \"([^\"]*)\"$")
    public void FullScreenChannelValueCorrespondTo(String expectedChannel) throws Throwable {
        String channelNumber = MyPages._fullScreenPage.getChannelNumber().trim();
        System.out.println("Channel number : "+ channelNumber);
        Assert.assertEquals(channelNumber, expectedChannel);
    }

    /**
     * Verifies that the show name in the Full Screen view is the same as the show name remembered from the Live tab.
     */
    @Then("^I see that Full Screen view show name is the same as I remember for Live tab$")
    public void shownameOnFullSCreenView() throws Throwable {
        String showName = MyPages._fullScreenPage.getShowTitle();
        String rememberedShowName = MyPages._livePage.getShowName();
        Assert.assertEquals(showName, rememberedShowName);
    }

    /**
     * Navigates to the next channel in the Full Screen view for the specified lineup.
     * Handles platform-specific interactions (iOS vs. Android).
     */
    @Then("I go to the next channel for \"([^\"]*)\" lineup in Full Screen View$")
    public void test(String lineup) throws Throwable {
        MobileElement elNextChevron = null;
        String channelNumber = MyPages._fullScreenPage.getChannelNumber();

            if (Utils.isAndroid()){
                elNextChevron = (MobileElement) Utils.getDriver().findElement(By.id("com.didja.btv.debug:id/image_station_next"));
            }else {
                elNextChevron = Utils.findElement("player_nextChevron");
                //click doesn't work because element visible  attribute equal false
                Utils.tapElement(elNextChevron);
            }
    }

    /**
     * Navigates to the previous channel in the Full Screen view for the specified lineup.
     * Handles platform-specific interactions (iOS vs. Android).
     */
    @Then("I go to the previous channel for \"([^\"]*)\" lineup in Full Screen View$")
    public void goToPreviousChannel(String lineup) throws Throwable {
        MobileElement elPrevChevron = null;
        String channelNumber = MyPages._fullScreenPage.getChannelNumber();

        if (Utils.isAndroid()){
            elNextChevron = (MobileElement) Utils.getDriver().findElement(By.id("com.example.tvapp:id/image_station_next"));
        }else {
            elPrevChevron = Utils.findElement("player_previousChevron");
            //click doesn't work because element's visible  attribute equal false
            Utils.tapElement(elPrevChevron);
        }
    }

    /**
     * Verifies that the blackout message displayed in the Full Screen view matches the expected message.
     */
    @Then("I see blackout message \"([^\"]*)\"$")
    public void iSeeAMessage(String expectedMsg) throws Throwable {
        String generatedMsg = MyPages._fullScreenPage.getBlackoutText();
        Assert.assertEquals(generatedMsg, expectedMsg);

    }

    /**
     * Verifies that navigation to the right is disabled by ensuring the right chevron is not visible.
     */
    @And("^I cant navigate right in Full Screen view$")
    public void iCantNavigateRightInFullSCreenView() throws Throwable {
        boolean invisible = Utils.isElementInVisible(xConsts.APPLE_IDS.get("player_nextChevron"), 4);
        System.out.println("player_nextIcon->" + invisible);
        Assert.assertTrue(invisible);
    }

    /**
     * Verifies that navigation to the left is disabled by ensuring the left chevron is not visible.
     */
    @And("^I cant navigate left in Full Screen view$")
    public void iCantNavigateLefttInFullSCreenView() throws Throwable {
        boolean invisible = Utils.isElementInVisible(xConsts.APPLE_IDS.get("player_previousChevron"), 4);
        System.out.println("player_previousIcon->" + invisible);
        Assert.assertTrue(invisible);
    }

}
