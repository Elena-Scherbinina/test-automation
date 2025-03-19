package stepdefs;

import com.google.common.collect.ImmutableMap;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.appmanagement.ApplicationState;
import org.openqa.selenium.By;
import org.testng.Assert;
import pages.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class GuidePageStepDefinitions {
    private AppiumDriver driver;
    private String dateValue = "";


    public GuidePageStepDefinitions() {
        this.driver = Utils.getDriver();
    }


    /**
     * Navigates to the Guide page for the specified lineup.
     * Depending on the lineup provided, it logs in with the corresponding credentials.
     */
    @Given("^I am on the Guide page with \"([^\"]*)\" lineup$")
    public void iAmOnTheGuidePageWithLinup(String lineup) throws Throwable {
        MyPages._signUpScreen1Page = new SignUpScreen1(driver);
        MyPages._signUpScreen2Page = (SignUpScreen2) MyPages._signUpScreen1Page.buttonClick(xConsts.SIGNED_IN_BTN);
        switch (lineup) {
            case "AUTOTEST":
                MyPages._guidePage = (GuidePage) MyPages._signUpScreen2Page.login(xConsts.AUTOTEST_EMAIL, xConsts.AUTOTEST_PASSWORD);
                break;
            default:
                throw new IllegalArgumentException("Invalid choice : " + lineup);
        }
    }


    /**
     * Navigates to the Live page for the specified lineup.
     * Uses credentials based on the lineup selection.
     */
    @Given("^I am on the Live page with \"([^\"]*)\" lineup$")
    public void iAmOnTheLivePageWithLinup(String lineup) throws Throwable {
        MyPages._signUpScreen1Page = new SignUpScreen1(driver);
        MyPages._signUpScreen2Page = (SignUpScreen2) MyPages._signUpScreen1Page.buttonClick(xConsts.SIGNED_IN_BTN);
        switch (lineup) {
            case "AUTOTEST":
                MyPages._livePage = (LivePage) MyPages._signUpScreen2Page.login(xConsts.AUTOTEST_EMAIL, xConsts.AUTOTEST_PASSWORD);
                break;
            default:
                throw new IllegalArgumentException("Invalid choice : " + lineup);
        }
    }


    /**
     * Verifies that the top drop-down menu button is visible on the Live page.
     */
    @And("^I see top drop-down menu button$")
    public void iSeeTopDropDownMenuButton() {
        Assert.assertTrue(MyPages._livePage.isInitialized());
    }

    /**
     * Handles the screen appearance by allowing permissions and dismissing dialogs if needed.
     * This method is for Android devices.
     */
    @When("^I see an app screen$")
    public void seeAppScreen() throws Throwable {
        if (Utils.isAndroid()) {
            MyPages._livePage.allowAppPermission();
            TimeUnit.SECONDS.sleep(Long.parseLong("2"));
            MyPages._livePage.clickOutsideAllowLocationDialog();
        }
        Assert.assertTrue(MyPages._livePage.isInitialized());
    }

    /**
     * Handles the screen appearance after sign-in.
     * The flow varies slightly between Android and iOS.
     */
    @When("^I see the screen after signin$")
    public void seeAppScreenAfterSignin() throws Throwable {
        if (Utils.isAndroid()) {
            MyPages._livePage = (LivePage) MyPages._signUpScreen2Page.buttonClick(xConsts.SIGN_IN_BTN_SECOND_SCREEN);
            MyPages._livePage.acceptLocationService();
            MyPages._livePage.allowAppPermission();
            TimeUnit.SECONDS.sleep(Long.parseLong("2"));
            MyPages._livePage.clickOutsideAllowLocationDialog();
        } else {
            MyPages._livePage = (LivePage) MyPages._signUpScreen2Page.buttonClick(xConsts.SIGN_IN_BTN_SECOND_SCREEN_IOS);
            MyPages._livePage.acceptLocationService();
        }
        Assert.assertTrue(MyPages._livePage.isInitialized());
    }


    /**
     * Verifies that a button with the given name is visible on the Live page.
     */
    @And("^I will see \"([^\"]*)\" button$")
    public void iWillSee(String name) throws Throwable {
        Assert.assertTrue(MyPages._livePage.isShown(name));
    }


    /**
     * Verifies that the first lineup channel is displayed in the viewer.
     * It checks for the absence of a loading overlay and compares the channel number.
     */
    @Then("^I see first lineup channel in the viewer$")
    public void seeChannelNumber() throws Throwable {
        boolean absent = false;
        if (Utils.isAndroid()) {
            absent = Utils.isElementInVisible(xConsts.ANDROID_IDS.get("loadingProgress_image"), 15);
        } else {
            absent = Utils.isElementInVisible("player_activityIndicator", 15);
        }

        if (absent) {
            Utils.tapAppScreen();
        }
        String channel = MyPages._fullScreenPage.getChannelNumber();
        ;
        Assert.assertEquals(channel, xConsts.AUTOTEST_FIRST_CHANNEL);
    }


    /**
     * Verifies that the specified overlay is visible in the viewer.
     * For Android, it checks directly, while for iOS it inspects the specific overlay elements.
     */
    @And("^I will see \"([^\"]*)\" overlay in the viewer$")
    public void iWillSeeOverlay(String name) throws Throwable {
        System.out.println(name);
        MobileElement el = null;
        boolean b = false;
        if (Utils.isAndroid()) {
            b = MyPages._livePage.isShown(name);
        } else {
            CurrentVideoPage v = MyPages._livePage.getCurrentViewer();
            if (name.equalsIgnoreCase("30 sec Forward")) {
                el = v.get30SecForwardOverlay();
                b = el.isEnabled();
            } else if (name.equalsIgnoreCase("30 sec Back")) {
                el = v.get30SecBackOverlay();
                b = el.isEnabled();
            } else if (name.equalsIgnoreCase("Chevron Right")) {
                MobileElement channelChanger = Utils.findElement("player_channelChanger");
                MobileElement e = channelChanger.findElement(By.id("player_nextIcon"));
                b = e.isEnabled();
                System.out.println("Chevron-Right ): " + e.isEnabled());
            } else if (name.equalsIgnoreCase("Chevron-Left")) {
                MobileElement e = Utils.findElementByXPath("playerPreviousButton");
                b = e.isEnabled();
            } else {
                System.out.println("in iWillSeeOverlay()): " + name);
                b = MyPages._livePage.isShown(name);
            }
        }
        Assert.assertTrue(b);
    }


    /**
     * Verifies that there are no overlays in the viewer.
     * This step is used to confirm that video overlays (like Play/Pause) are not displayed.
     */
    @Then("^there are no overlays in the viewer$")
    public void noOverlaysInTheViewer() throws Throwable {
        boolean resultPlayPause = MyPages._guidePage.verifyPlayPauseOverlayIsAbsent(20);
        Assert.assertTrue(resultPlayPause);
    }


    /**
     * Verifies that there is no "Play/Pause" overlay in the viewer on a specified tab.
     */
    @Then("^there is no \"Play/Pause\" overlay in the viewer on the \"([^\"]*)\" tab$")
    public void noOverlayInTheViewer(String tab) throws Throwable {
        boolean resultPlayPause = true;
        String id = "";
        id = Utils.getId("play_pause");
        if (tab.equalsIgnoreCase("Guide")) {
            resultPlayPause = Utils.isElementInVisible(id, 12);
        }
        Assert.assertTrue(resultPlayPause);
    }


    /**
     * Verifies that there are no overlays in the viewer on a specified tab.
     * This includes checking that loading and Play/Pause overlays are not visible.
     */
    @Then("^there are no overlays in the viewer on the \"([^\"]*)\" tab$")
    public void noOverlaysInTheViewer(String tab) throws Throwable {
        //Verify that loading overlay and PlayPause overlay are not shown
        boolean overlay = true;
        if (tab.equalsIgnoreCase("Guide")) {
            overlay = MyPages._guidePage.verifyLoadingVideoOverlayIsAbsent(15);
        } else if (tab.equalsIgnoreCase("Live")) {
            overlay = MyPages._livePage.verifyPlayPauseOverlayIsAbsent(12);
        } else if (tab.equalsIgnoreCase("Recordings")) {//Recordings tab
            overlay = MyPages._recordingsPage.verifyLoadingVideoOverlayIsAbsent(12);
        }
        Assert.assertTrue(overlay);
        boolean resultPlayPause = true;
        String id = "";
        id = Utils.getId("play_pause");
        if (tab.equalsIgnoreCase("Guide")) {
            resultPlayPause = Utils.isElementInVisible(id, 12);
        }
        Assert.assertTrue(resultPlayPause);
    }


    /**
     * Verifies that there is no "CC" overlay in the viewer on the specified tab.
     */
    @Then("^there is no \"CC\" overlay in the viewer on the \"([^\"]*)\" tab$")
    public void noCCOverlayInTheViewer(String tab) throws Throwable {
        boolean resultPlayPause = true;
        String id = "";
        String xPath = "";
        if (tab.equalsIgnoreCase("Live")) {
            if (Utils.isAndroid()) {
                resultPlayPause = Utils.isElementInVisible(Utils.getId("player_ccButton"), 12);
            } else {
                xPath = xConsts.APPLE_XPATHS.get("playerCCButton");
                resultPlayPause = Utils.isElementLocatedByXPathInVisible(xPath, 10);
            }
        }
        Assert.assertTrue(resultPlayPause);
    }


    /**
     * Verifies that there is no "Player Mute Button" overlay in the viewer on the specified tab.
     */
    @Then("^there is no \"Player Mute Button\" overlay in the viewer on the \"([^\"]*)\" tab$")
    public void noMuteButtonOverlayInTheViewer(String tab) throws Throwable {
        boolean resultPlayPause = true;
        String id = "";
        if (tab.equalsIgnoreCase("Live")) {
            id = Utils.getId("player_muteButton");
        }
        resultPlayPause = Utils.isElementInVisible(id, 12);
        Assert.assertTrue(resultPlayPause);
    }


    /**
     * Verifies that there is no "30 sec Forward" overlay in the viewer on the specified tab.
     */
    @Then("^there is no \"30 sec Forward\" overlay in the viewer on the \"([^\"]*)\" tab$")
    public void no30SecForwardOverlayInTheViewer(String tab) throws Throwable {
        boolean resultPlayPause = true;
        String id = "";
        if (tab.equalsIgnoreCase("Live")) {
            id = Utils.getId("player_seekForwardButton");
        }
        resultPlayPause = Utils.isElementInVisible(id, 12);
        Assert.assertTrue(resultPlayPause);
    }


    @Then("^there is no Loading overlay in the viewer on the \"([^\"]*)\" tab$")
    public void noLoadingverlayInTheViewer(String tab) throws Throwable {
        boolean resultPlayPause = true;
        if (tab.equalsIgnoreCase("Guide")) {
            resultPlayPause = MyPages._guidePage.verifyLoadingVideoOverlayIsAbsent(15);
        } else if (tab.equalsIgnoreCase("Live")) {
            resultPlayPause = MyPages._livePage.verifyPlayPauseOverlayIsAbsent(12);
        } else if (tab.equalsIgnoreCase("Recordings")) {//Recordings tab
            resultPlayPause = MyPages._recordingsPage.verifyLoadingVideoOverlayIsAbsent(12);
        }
        Assert.assertTrue(resultPlayPause);
    }


    @And("^I will see \"([^\"]*)\" tab$")
    public void iWillSeeTab(String name) throws Throwable {
        Assert.assertTrue(MyPages._livePage.isShown(name));
    }

    @And("^I see \"([^\"]*)\" tab$")
    public void iSeeTab(String name) throws Throwable {
        Assert.assertTrue(MyPages._guidePage.isShown(name));
    }

    /**
     * Checks that the Today button is visible and its caption matches the expected value.
     */
    @And("^Today button has caption \"([^\"]*)\"$")
    public void todayButtonHasCaptionToday(String expectedValue) throws Throwable {
        Assert.assertTrue(MyPages._guidePage.isShownTodayButton());
        Assert.assertEquals(MyPages._guidePage.getTodayButtonCaption(), expectedValue);
    }

    /**
     * Verifies that the Today button's caption does not match the provided caption.
     */
    @And("^Today buttons caption is not equal \"([^\"]*)\"$")
    public void todayButtonCaptionIsNotToday(String caption) throws Throwable {
        Assert.assertNotEquals(MyPages._guidePage.getTodayButtonCaption(), caption);
    }


    /**
     * Ensures that the app is currently in Full Screen view.
     * If not already instantiated, it creates a new FullScreenPage instance.
     */
    @Then("^app is in full screen view$")
    public void appInFullScreenView() throws Throwable {
        if (MyPages._fullScreenPage != null) {
            MyPages._fullScreenPage = MyPages._fullScreenPage;
        } else {
            MyPages._fullScreenPage = new FullScreenPage(driver);
        }
    }

    /**
     * Performs a button click based on the provided button name.
     * Supports navigation among Live, Guide, Recordings, and other actions.
     */
    @Then("^I click \"([^\"]*)\" button$")
    public void buttonClick(String name) throws Throwable {
        System.out.println("name : " + name);
        try {
            switch (name.toUpperCase()) {
                case "LIVE":
                    MyPages._livePage = (LivePage) MyPages._livePage.toolbarButtonClick(name);
                    MyPages._livePage.setCurrentVideo();
                    break;
                case "RECORDINGS":
                    MyPages._recordingsPage = (RecordingsPage) MyPages._livePage.toolbarButtonClick(name);
                    MyPages._recordingsPage.setCurrentVideo();
                    break;
                case "GUIDE":
                    MyPages._guidePage = (GuidePage) MyPages._livePage.toolbarButtonClick(name);
                    MyPages._guidePage.setCurrentVideo();
                    break;
                case "COMMUNITY":
                    MyPages._guidePage = (GuidePage) MyPages._livePage.toolbarButtonClick(name);
                    MyPages._guidePage.setCurrentVideo();
                    break;
                case "PLAY/PAUSE":
                    System.out.println("In I click Play/Pause");
                    if (Utils.isAndroid()) {
                        MyPages._livePage.playPauseButtonClick();
                    } else {
                        MyPages._livePage.playPauseButtonClick();
                    }

                    break;
                case "FULL SCREEN VIEW":
                    System.out.println("I click Full Screen.. ");
                    Utils.isElementVisible(Utils.getId("fullScreen_button"), 6);
                    MyPages._fullScreenPage = (FullScreenPage) MyPages._livePage.buttonClick(name);
                    break;
                case "TOP DROP-DOWN MENU":
                    MyPages._topDropDownMenu = (TopMenu) MyPages._livePage.buttonClick("Top Drop-Down Menu");
                    break;
                case "LAST":
                    MyPages._livePage.buttonClick("LAST");
                    break;
                case "WATCH LIVE":
                    MyPages._livePage.watchButtonClick();
                    break;
                case "BACK":
                    if (Utils.isAndroid()) {
                        Utils.goBack();
                    } else {
                        MyPages._signUpScreen2Page.buttonClick("Back");
                    }
                    break;
                case "NEXT":
                    MyPages._guidePage.goToNext();
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
     * Simulates a tap on the app video screen.
     */
    @Then("^I tap app video screen$")
    public void tapAppScreen() throws Throwable {
        Utils.tapAppScreen();
    }

    /**
     * Taps the Play/Pause button.
     * Uses different implementations based on the platform (Android or iOS).
     */
    @Then("^I tap Play/Pause button$")
    public void tapPlayPauseButton() throws Throwable {
        if (Utils.isAndroid()) {
            Utils.playPauseButtonClickForAndroid(15);
        } else {
            Utils.playPauseButtonClick(15);
        }
    }

    /**
     * Taps the Play/Pause button while the app is in landscape orientation.
     * On Android, it triggers the Play/Pause action specifically designed for landscape mode.
     * On iOS, it uses the generic Play/Pause tap method with a timeout.
     */
    @Then("^I tap Play/Pause button in landscape orientation$")
    public void tapPlayPauseButtonInLandscapeOrienatation() throws Throwable {
        System.out.println("Tap Play/Pause ..");
        if (Utils.isAndroid()) {
            MyPages._fullScreenPage.tapPlayPauseInLandscapeOrientation();
        } else {
            Utils.playPauseButtonClick(15);
        }
    }

    /**
     * Verifies that the specified tab is highlighted (selected) in the app's toolbar.
     */
    @Then("^\"([^\"]*)\" tab is highlighted$")
    public void tabIsHighlighted(String name) throws Throwable {
        if (!Utils.isAndroid()) {
            System.out.println("Ios Tab higlighting is not implemented in Appium.");
            return;
        }
        boolean generatedValue = false;
        switch (name.toUpperCase()) {
            case "LIVE":
                generatedValue = MyPages._livePage.isToolbarButtonSelected("LIVE");
                break;
            case "GUIDE":
                generatedValue = MyPages._livePage.isToolbarButtonSelected("GUIDE");
                break;
            case "RECORDINGS":
                generatedValue = MyPages._livePage.isToolbarButtonSelected("RECORDINGS");
                break;

            default:
                throw new IllegalArgumentException("Invalid element: " + name);
        }
        Assert.assertTrue(generatedValue);

    }

    /**
     * Verifies that the specified tab is not highlighted.
     * Only applicable for Android as iOS implementation is omitted.
     */
    @Then("^\"([^\"]*)\" tab is not highlighted$")
    public void tabIsNotHighlighted(String name) throws Throwable {
        if (!Utils.isAndroid()) {
            return;
        }
        boolean generatedValue = false;
        try {
            switch (name.toUpperCase()) {
                case "LIVE":
                    generatedValue = MyPages._livePage.isToolbarButtonSelected("LIVE");

                    break;
                case "GUIDE":
                    generatedValue = MyPages._livePage.isToolbarButtonSelected("GUIDE");
                    break;
                case "RECORDINGS":
                    generatedValue = MyPages._livePage.isToolbarButtonSelected("RECORDINGS");
                    break;

                default:
                    throw new IllegalArgumentException("Invalid element: " + name);
            }

        } catch (Exception e) {
            throw e;
        }
        Assert.assertFalse(generatedValue);
    }


    /**
     * Performs a right swipe gesture on the Live tab a specified number of times.
     * The swipe is adjusted for Android or iOS platforms.
     */
    @And("^I will swipe Live tab right (\\d+) times$")
    public void swipeLiveTabRight(int arg) throws Throwable {
        if (Utils.isAndroid()) {
            MobileElement liveTab = Utils.findElement("layout_expandable");
            int y = liveTab.getLocation().getY() + liveTab.getSize().height / 2;
            int startX = liveTab.getLocation().getX() + 50;
            int endX = liveTab.getLocation().getX() + (liveTab.getSize().getWidth() * 2 / 3);
            Utils.scroll(startX, y, endX, y, arg, xConsts.SWIPE_DURATION);
        } else {
            Utils.swipeInsideObject("airing_options", 4, "right");
        }
    }

    @And("^I will swipe Live tab left (\\d+) times$")
    public void swipeLiveTabLeft(int arg) throws Throwable {
        if (Utils.isAndroid()) {
            MobileElement liveTab = Utils.findElement("layout_expandable");
            int y = liveTab.getLocation().getY() + liveTab.getSize().height / 2;
            int x = (liveTab.getLocation().getX() + liveTab.getSize().getWidth()) * 2 / 3;
            Utils.scroll(50, y, x, y, arg, 2000);
        } else {
            Utils.swipeInsideObject("airing_options", 5, "left");
        }
    }


    /**
     * Scrolls down the Guide tab a specified number of times.
     * For Android, it calculates the swipe coordinates based on the Guide's collection view
     * and performs vertical scroll gestures. For iOS, it uses a utility method to swipe inside
     * the guide collection view.
     */
    @And("^I will scroll Guide (\\d+) times down$")
    public void scrollGuideDown(int arg) throws Throwable {
        if (Utils.isAndroid()) {
            MobileElement guideElement = Utils.findElement("guide_CollectionView");
            // Calculate the horizontal center of the element.
            int centerX = guideElement.getLocation().getX() + guideElement.getSize().getWidth() / 2;
            // Define the starting y coordinate near the top of the element.
            int startY = guideElement.getLocation().getY() + (int) (guideElement.getSize().getHeight() * 0.2);
            // Define the ending y coordinate near the bottom of the element.
            int endY = guideElement.getLocation().getY() + (int) (guideElement.getSize().getHeight() * 0.8);
            // Perform the swipe gesture 'arg' times with a duration of 1000 milliseconds.
            Utils.scroll(centerX, startY, centerX, endY, arg, 1000);
        } else {
            Utils.swipeInsideObject("guide_CollectionView", arg, "down");
        }
    }

    @And("^I will scroll Guide (\\d+) times up$")
    public void scrollGuideUp(int arg) throws Throwable {
        if (Utils.isAndroid()) {
            MobileElement guideElement = Utils.findElement("guide_CollectionView");
            // Calculate the horizontal center.
            int centerX = guideElement.getLocation().getX() + guideElement.getSize().getWidth() / 2;
            // Define the starting y coordinate near the bottom of the element.
            int startY = guideElement.getLocation().getY() + (int) (guideElement.getSize().getHeight() * 0.8);
            // Define the ending y coordinate near the top of the element.
            int endY = guideElement.getLocation().getY() + (int) (guideElement.getSize().getHeight() * 0.2);
            // Perform the swipe gesture 'arg' times with a duration of 1000 milliseconds.
            Utils.scroll(centerX, startY, centerX, endY, arg, 1000);
        } else {
            Utils.swipeInsideObject("guide_CollectionView", arg, "up");
        }
    }

    /**
     * Remembers the default show name currently displayed in the Guide tab.
     * This method captures the name of the default show from the Guide and stores it
     * for later comparison or validation steps during the test execution.
     */
    @And("^I will remember default Guide show name$")
    public void setDefaultGuideShowName() throws Throwable {
        MyPages._guidePage.setDefaultGuideShowName();
    }

    /**
     * Verifies that the first channel displayed in the Guide matches the default channel
     * for the "AUTOTEST" lineup.
     */
    @And("^first channel in the Guide is default$")
    public void firstChannelInTheGuide() throws Throwable {
        String firstChannelInApp = MyPages._guidePage.getFirstVisibleChannelFromTheGuide();
        String autoTestChannel = MyPages._guidePage.getChannelsNamArrayeForLineup("AUTOTEST").get(0);
        Assert.assertEquals(firstChannelInApp, autoTestChannel);
    }


    /**
     * Verifies that the first channel displayed in the Guide matches the expected channel.
     */
    @And("^first channel in the Guide is \"([^\"]*)\" channel$")
    public void firstChannelInTheGuideIs(String expectedChannel) throws Throwable {
        String firstChannelInApp = MyPages._guidePage.getFirstVisibleChannelFromTheGuide();
        String autoutoTestChannel = expectedChannel;
        Assert.assertEquals(firstChannelInApp, autoTestChannel);
    }

    /**
     * Retrieves the first visible channel from the Guide and compares it with the default channel
     * for the "AUTOTEST" lineup. The test fails if both channels are identical.
     */
    @Then("^I will see that first channel in the guide is different from default$")
    public void firstChannelIsDifferentFromDefault() throws Throwable {
        String firstVisibleChannel = MyPages._guidePage.getFirstVisibleChannelFromTheGuide();
        String autoTestChannel = MyPages._guidePage.getChannelsNamArrayeForLineup("AUTOTEST").get(0);
        Assert.assertNotEquals(autoTestChannel, firstVisibleChannel);
    }

    /**
     * Verifies that the current live show name corresponds to the default show name for the specified tab.
     */
    @And("^I will see that current live show name corresponds default name for \"([^\"]*)\" tab$")
    public void I_will_see_that_current_live_show_name(String tabName) throws Throwable {
        String viewerVideoName = MyPages._guidePage.getCurrentVideoNameFromViewer();
        String guideVideoName = "";

        switch (tabName) {
            case "Live":
                guideVideoName = MyPages._fullScreenPage.getLineupDefaultShowTitle();
                viewerVideoName = MyPages._livePage.getCurrentVideoNameFromViewer();
                break;
            case "Guide":
                guideVideoName = MyPages._guidePage.getDefaultGuideShowName();
                viewerVideoName = MyPages._fullScreenPage.getShowTitle();
                break;
            case "Recordings":
                guideVideoName = MyPages._guidePage.defaultGuideShowName;
                viewerVideoName = MyPages._recordingsPage.getCurrentVideoNameFromViewer();
                break;

            default:
                throw new IllegalArgumentException("Invalid element: " + tabName);
        }
        Assert.assertEquals(viewerVideoName, guideVideoName);
        Thread.sleep(3000);
    }


    /**
     * Verifies that the specified channel name is displayed as the first visible channel in the Guide.
     * This step fetches the first visible channel name from the Guide and compares it with
     * the expected channel name provided in the test scenario. The test passes if both match.
     */
    @And("^I expect to see \"([^\"]*)\" channel name in the Guide$")
    public void iSeeChannelName(String channelName) throws Throwable {
        String generatedChannelName = MyPages._guidePage.getFirstVisibleChannelFromTheGuide();
        Assert.assertEquals(generatedChannelName, channelName);
    }

    @And("^I play channel \"([^\"]*)\" title$")
    public void iTapFirst(String channelName) throws Throwable {
        MyPages._guidePage.loadChannelIntoViewer(channelName);
    }

    /**
     * Verifies that the current show name displayed in the Guide matches the previously remembered default show name.
     * This step compares the remembered default guide show name (stored earlier) with the name of the
     * current show displayed in the Guide at index 0. It ensures consistency in show information
     * presentation and validates that the correct show is being referenced.
     */
    @Then("^I will see that current show name from guide the same as remembered$")
    public void test() throws Throwable {
        String remGuideVideoName = MyPages._guidePage.defaultGuideShowName;
        String guideVideoName = MyPages._guidePage.getGuideVideoName(0);
        Assert.assertEquals(remGuideVideoName, guideVideoName);
    }

    /**
     * Verifies that the current live show is "now" by comparing the current video name from the viewer
     * with the guide video name at index 0.
     */
    @And("^I will see that current live show is now$")
    public void currentLiveShowIsNow() throws Throwable {
        String viewerVideoName = MyPages._guidePage.getCurrentVideoNameFromViewer();
        String guideVideoName = MyPages._guidePage.getGuideVideoName(0);
        viewerVideoName = MyPages._guidePage.getCurrentVideoNameFromViewer();

        Assert.assertEquals(viewerVideoName, guideVideoName);
        Thread.sleep(3000);
    }


    @And("^I tap Guide cell for the current live show$")
    public void ITapGuideCellForTheCurrentLiveShow() throws Throwable {
        if (Utils.isAndroid()) {
            List<MobileElement> lGuide = MyPages._guidePage.getTvGuideList();
            MyPages._infoDialog = (InfoDialog) MyPages._guidePage.clickGuideCellByIndex(lGuide, 0);
        } else {
            MyPages._guidePage.clickFirstGuideCellIos();
        }
    }


    /**
     * Checks for the presence of a scheduled recording based on the show name retrieved
     * from the info dialog. If the "Recording Schedule" tab is specified, it attempts to locate the recording
     * using platform-specific methods for Android and iOS.
     */
    @Then("^I find scheduled recording on \"([^\"]*)\" Tab$")
    public void xtesting(String name) throws Throwable {
        boolean found = false;
        String showName = MyPages._infoDialog.getShowName();
        if (name.equalsIgnoreCase("Recording Schedule")) {
            if (Utils.isAndroid()) {
                found = MyPages._recordingSchedulePage.findScheduledRecordingForAndroid(showName);
            } else {
                found = MyPages._recordingSchedulePage.isRecordingFoundForIos(showName);
            }
        }
        if (!found) {
            MyPages._recordingSchedulePage.closeRecordingSchedule();
            MyPages._guidePage.toolbarButtonClick("Recordings");
            MyPages._recordingSchedulePage.extendOrCollapseTab("Down");
            MyPages._recordingSchedulePage.extendOrCollapseTab("Up");
            if (Utils.isAndroid()) {
                found = Utils.isAndroidRecordingFound(showName);
            } else {
                found = Utils.isIosRecordingFound(showName);
            }
        }
        Assert.assertTrue(found);
    }


    /**
     * Remembers the current live channel number on the Guide tab based on the provided channel identifier.
     */
    @And("^I remember current live channel number on the Guide tab as \"([^\"]*)\"$")
    public void iRememberCurrentChannel1(String channel) throws Throwable {
        switch (channel.toUpperCase()) {
            case "CHANNEL1":
                MyPages._guidePage.rememberFirstChannelNumber();
                break;
            case "CHANNEL2":
                MyPages._livePage.rememberNextChannelNumber();
                break;
            default:
                throw new IllegalArgumentException("Invalid element: " + channel);
        }
    }

    /**
     * Verifies that the first remembered channel number matches the channel currently loaded and displayed in the viewer
     * on the Guide tab.
     * This step ensures that the correct channel (previously remembered) is playing in the viewer,
     * confirming that the channel selection and playback flow worked as expected.
     */
    @Then("^I see that channel1 has loaded into the viewer on Guide tab$")
    public void channel1LoadedIntoTheViewer() throws Throwable {
        String firstRememberedChannel = MyPages._guidePage.getFirstChannelNumber();
        String playingChannel = MyPages._guidePage.getFirstVisibleChannelFromTheGuide();
        Assert.assertEquals(firstRememberedChannel, playingChannel);
    }


    /**
     * Verifies that the recording information displayed in the viewer matches the previously remembered recording title.
     * This step compares the title of the remembered recording from the Recordings page with
     * the current video title displayed in the viewer to ensure the correct recording is playing.
     */
    @Then("^recording info is the same as I remember$")
    public void recordingInfoTheSame() throws Throwable {
        System.out.println("Inside recording infois the same");
        String recordingTitle = MyPages._recordingsPage.getRememberedRecordingTitle();
        String viewerVideoTitle = MyPages._guidePage.getCurrentVideoNameFromViewer();

        Assert.assertEquals(recordingTitle, viewerVideoTitle);
    }


    /**
     * Verifies that the recording information matches the stored recording information for the
     * specified recording.
     */
    @Then("^recording info is the same as I remember for \"([^\"]*)\" recording$")
    public void recordingInfoTheSameForSecondRecoding(String id) throws Throwable {
        String expectedTitle = "";
        String viewerVideoTitle = "";

        switch (id.toLowerCase()) {
            case "first":
                expectedTitle = MyPages._recordingsPage.getStoredRecordingInfo(0).getTitle();
                viewerVideoTitle = MyPages._fullScreenPage.getShowTitle();
                break;
            case "second":
                expectedTitle = MyPages._recordingsPage.getStoredRecordingInfo(1).getTitle();
                if (Utils.isAndroid()) {
                    viewerVideoTitle = MyPages._fullScreenPage.getShowTitle();
                } else {
                    viewerVideoTitle = MyPages._fullScreenPage.getShowTitle();
                }
                break;
            default:
                throw new IllegalArgumentException("Recording info is the same as I remember .., Invalid element for " + id);
        }
        Assert.assertEquals(viewerVideoTitle, expectedTitle);
    }


    @And("^I accept location service dialog$")
    public void acceptLocationServiceDialog() throws Throwable {
        MyPages._livePage.acceptLocationService();
    }

    @And("^I deny location service dialog$")
    public void denytLocationServiceDialog() throws Throwable {
        MyPages._livePage.denyLocationService();
    }

    /**
     * Validates that the location permission dialog displays the expected text.
     * This step retrieves the actual permission dialog text from the Live Page
     * and compares it against the expected message passed as a parameter.
     * It ensures the app prompts the user with the correct location access request.
     */
    @Then("^I will see allow access location text \"([^\"]*)\"$")
    public void seeTheFollowingMessage(String expectedText) {
        String generatedMsg = MyPages._livePage.getAllowPermissionText();
        Assert.assertEquals(generatedMsg, expectedText);
    }


    @Then("^I see a dialog with a following message \"([^\"]*)\"$")
    public void seeDialogWithFollowingMessage(String expectedMsg) {
        String generatedMsg = "";
        if (Utils.isAndroid()) {
            generatedMsg = MyPages._livePage.getAllowLocationAccessText();
            Assert.assertEquals(generatedMsg, expectedMsg);
        } else {
            generatedMsg = MyPages._livePage.getAllowLocationAccessText();
            Assert.assertEquals(generatedMsg, expectedMsg);
        }
    }


    @Then("^I see a dialog with android \"([^\"]*)\" and ios \"([^\"]*)\"$")
    public void seeDialogWithMessages(String expectedAndroidMsg, String expectedIosMsg) {
        String generatedMsg = "";
        generatedMsg = MyPages._livePage.getAllowLocationAccessText();
        if (Utils.isAndroid()) {
            Assert.assertEquals(generatedMsg, expectedAndroidMsg);
        } else {
            Assert.assertEquals(generatedMsg, expectedIosMsg);
        }
    }

    /**
     * Verifies that the location access dialog is displayed with the expected text.
     * This step validates the permission dialog content on both Android and iOS:
     * - On Android, it compares the dialog text with a predefined constant.
     * - On iOS, it verifies the dialog displays the expected message passed as a parameter.
     */
    @Then("^I see a dialog with text \"([^\"]*)\"$")
    public void seeDialogWithMessages(String expectedIosMsg) {
        String generatedMsg = "";
        if (Utils.isAndroid()) {
            generatedMsg = MyPages._livePage.getAllowLocationAccessText();
            Assert.assertEquals(generatedMsg, xConsts.LOCATION_DLG_ANDROID);
        } else {
            generatedMsg = MyPages._livePage.getAllowLocationAccessText();
            Assert.assertEquals(generatedMsg, expectedIosMsg);
        }
    }


    @And("^I tap Ok button on Please Allow Access Location dialog$")
    public void tapOkButton() throws Throwable {
        MyPages._livePage.tapOkOnPleaseAllowAccessToLocation();
    }


    /**
     * Taps the "Don't Ask Again" checkbox on the location service dialog.
     * This action ensures that the location permission dialog will not appear again by enabling the
     * "Don't Ask Again" option. It is useful for testing scenarios where the app behavior changes
     * after permanently denying location permissions.
     */
    @And("^I tap Dont ask again on location service dialog$")
    public void tapDontASkAgain() throws Throwable {
        MyPages._livePage.tapDontAskAgain();
    }


    @Then("^Allow button is disabled on location service dialog$")
    public void buttonIsDisableOnLocationDerviceDialog() throws Throwable {
        Assert.assertFalse(MyPages._livePage.isEnabled("ALLOW"));
    }

    /**
     * Toggles the state of the device's location services and navigates back to the previous screen.
     */
    @Then("^toggle location services switch$")
    public void app_is_not_shown() throws Throwable {
        ((AndroidDriver) driver).toggleLocationServices();
        Utils.goBack();
        Utils.goBack();
    }

    /**
     * Validates that the current activity corresponds to the device's App Info screen.
     */
    @Then("^I see device settings App Info screen$")
    public void iSeeAppInfo() throws Throwable {
        String appInfoPackage = xConsts.APP_INFO_PACKAGE;
        String activity = Utils.getCurrentActivity();
        Assert.assertTrue(activity.contains(appInfoPackage));
    }

    @Then("^I press Home button$")
    public void pressHome() throws Throwable {
        if (Utils.isAndroid()) {
            ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.HOME);
            Thread.sleep(3000);
        } else {
            driver.runAppInBackground(Duration.ofSeconds(-1));
        }
    }

    @Then("^app is backgrounded$")
    public void appIsBackgrounded() throws Throwable {
        if (Utils.isAndroid()) {
            final ApplicationState state = driver.queryAppState(xConsts.BUNDLE_ID);
            Assert.assertEquals(state, ApplicationState.RUNNING_IN_BACKGROUND);
        }
    }

    /**
     * Verifies the presence of the location service dialog.
     */
    @And("^I see location service dialog$")
    public void seeLocationServiceDialog() throws Throwable {
        MyPages._livePage.seeLocationServiceDlg();
    }


    @Then("^I press Power button$")
    public void pressPower() throws Throwable {
        Utils.pressPowerButton();
    }


    /**
     * Verifies the current state of the device's screen (ON or OFF) against the expected state.
     */
    @Then("^device screen is \"([^\"]*)\"$")  //screen OFF or ON
    public void deviceScreenIsOff(String expectedValue) {
        boolean isOn = true;
        String generatedValue = Utils.getScreenState();  // if OFF -->DOZE or OFF
        if (expectedValue.equalsIgnoreCase("OFF")) {
            if (!generatedValue.equalsIgnoreCase("ON")) {
                isOn = false;
            }
            Assert.assertFalse(isOn);
        } else {
            Assert.assertEquals(generatedValue, expectedValue);
        }
    }


    /**
     * Verifies that the blackout message displayed in the Guide matches the expected message.
     * This step is used to confirm that the correct blackout message appears in the Guide tab
     * when a channel or show is not available due to blackout restrictions.
     */
    @Then("I see blackout message \"([^\"]*)\" in the Guide$")
    public void iSeeAMessage(String expectedMsg) throws Throwable {
        String generatedMsg = MyPages._guidePage.getBlackoutText();
        Assert.assertEquals(generatedMsg, expectedMsg);
    }

    /**
     * Verifies that the number of channels displayed in the Guide matches the expected number.
     */
    @And("^I expect to see (\\d+) channels in the Guide$")
    public void ihave5Channels(int expectedNumberOfChannels) throws Throwable {
        if (!Utils.isAndroid()) {
            MobileElement el = Utils.findElement("guide_CollectionView");
            MobileElement channelsName = el.findElement(By.className(Utils.getId("collectionView")));
            String selector = "type == 'XCUIElementTypeStaticText' AND name == 'station_cell_channelLabel'";
            List<MobileElement> elements = channelsName.findElements(MobileBy.iOSNsPredicateString(selector));
            int number = elements.size();
            System.out.println("number " + number);
        }
        int numberOfCustomizedChannels = MyPages._customizeChannelsPage.getNumberOfCustomizeChannels();
        Assert.assertEquals(numberOfCustomizedChannels, expectedNumberOfChannels);
    }

    /**
     * Verifies that the specified channel is not present in the Guide.
     */
    @And("I dont see \"([^\"]*)\" channel in the Guide")
    public void iDontSeeChannelInTheGuide(String expectedChannel1) throws Throwable {
        if (!Utils.isAndroid()) {
            MobileElement el = Utils.findElement("guide_CollectionView");
            MobileElement channelsName = el.findElement(By.className(Utils.getId("collectionView")));
            String selector = "type == 'XCUIElementTypeStaticText' AND name == 'station_cell_channelLabel'";
            List<MobileElement> elements = channelsName.findElements(MobileBy.iOSNsPredicateString(selector));
            boolean found = MyPages._guidePage.isChannelFound(elements, expectedChannel1);
            Assert.assertFalse(found);
        }
    }


    /**
     * Compares the attributes of a show in Full Screen view with those in the Guide Info Dialog
     * to ensure they match.
     */
    @And("^\"([^\"]*)\" Full Screen View attributes are the same as I see on Guide Info Dialog$")
    public void fullScreenAttributesAndInfoDialogAttributes(String last) throws Throwable {
        int index1 = 0;

        String title1 = MyPages._fullScreenPage.getStoredShowAttributesInfo(index1).getTitle();
        String attributes1 = MyPages._fullScreenPage.getStoredShowAttributesInfo(index1).getAttributes();
        String title2 = MyPages._infoDialog.getGuideInfoDialogShowName();
        String attributes2 = MyPages._infoDialog.getGuideInfoDialogAttributes();

        if (title1.equalsIgnoreCase(title2)) {
            Assert.assertEquals(attributes2, attributes1);
        } else {
            Assert.assertEquals(title1, title2);
        }
    }
}