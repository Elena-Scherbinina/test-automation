package stepdefs;

import com.google.common.collect.ImmutableMap;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import pages.*;

import java.time.Duration;


public class LivePageStepDefinitions {
    private AppiumDriver driver;


    public LivePageStepDefinitions() {
        this.driver = Utils.getDriver();
        if (MyPages._guidePage == null) {
            MyPages._guidePage = new GuidePage(driver);
        }

    }

    /**
     * Verifies that the app loads in Full-Screen View by ensuring the FullScreenPage is initialized.
     */
    @Then("^I see app loading in Full Screen View$")
    public void appIsLOadingInFullScreenView() throws Throwable {
        if (MyPages._fullScreenPage == null) {
            MyPages._fullScreenPage = new FullScreenPage(driver);
        }
    }

    /**
     * Verifies that the specified button is visible on the Live tab.
     */
    @And("^I will see \"([^\"]*)\" button on Live tab$")
    public void iSee1(String name) throws Throwable {
        Assert.assertTrue(MyPages._livePage.isVisible(name));
    }

    /**
     * Verifies that the "Record" button is visible on the Live tab.
     */
    @And("^I will see \"Record\" for record opions on Live tab$")
    public void iwillSeeRecord() throws Throwable {
        Assert.assertTrue(MyPages._livePage.isVisible("RECORD"));
    }

    /**
     * Verifies that the "Last" button is disabled on the Live tab.
     */
    @And("^I see that \"Last\" button is disabled$")
    public void buttinIsDisabled() throws Throwable {
        Assert.assertFalse(MyPages._livePage.isLastEnabled());
    }

    /**
     * Verifies that the "Last" button is disabled for a specific channel.
     */
    @And("^I see that \"Last\" button is disabled for channel \"([^\"]*)\"$")
    public void buttinLastIsDisabled(String expectedChannel) throws Throwable {
        if (Utils.isAndroid()) {

        } else {
            MyPages._livePage.buttonClick("LAST");

            String channelNumber = MyPages._livePage.getChannelNumber();
            Assert.assertEquals(channelNumber, expectedChannel);
        }
    }

    /**
     * Verifies that the banner displays the specified show.
     */
    @Then("^I will see \"([^\"]*)\" in the banner$")
    public void iWillSee(String showName) throws Throwable {

        Assert.assertTrue(MyPages._livePage.isShown(showName));
    }

    /**
     * Verifies that the expected channel is visible on the Live tab.
     */
    @Then("^I expect to see \"([^\"]*)\" channel on the \"Live\" tab$")
    public void IExpectTosee(String expectedValue) throws Throwable {
        String channelNumber = MyPages._livePage.getChannelNumber(); //"7.1 AZTV7"
        Assert.assertEquals(channelNumber, expectedValue);
    }

    /**
     * Navigates to the Recording Schedule page and sets the current video.
     */
    @Then("^I navigate to Recording Schedule$")
    public void navigateToRecordingSchedule(String name) throws Throwable {
        System.out.println("I navigate to : " + name);

        MyPages._recordingsPage = (RecordingsPage) MyPages._livePage.toolbarButtonClick(name);
        MyPages._recordingsPage.setCurrentVideo();
    }

    /**
     * Displays recording options on the Live tab.
     */
    @And("^I show record options$")
    public void showRecordOptions() throws Throwable {
        if (Utils.isAndroid()) {
            MyPages._livePage.showRecordOptions();
        } else {
            MobileElement el = Utils.findElementByXPath("live_recordButton");
            el.click();
        }
    }

    /**
     * Navigates to the last channel in the specified lineup.
     */
    @Then("I go to the last channel for \"([^\"]*)\" lineup$")
    public void test(String lineup) throws Throwable {
        MobileElement elNextChevron = null;
        String channelNumber = MyPages._livePage.getChannelNumber();
        String lastChannel = xConsts.AUTOTEST_LAST_CHANNEL;
        while (!channelNumber.equals(lastChannel)) {
            if (Utils.isAndroid()) {
                elNextChevron = Utils.findElement("liveTab_nextChevron");
            } else {
                MyPages._infoDialog = new InfoDialog(driver);
                MyPages._infoDialog.buttonClick("RECORD");
                elNextChevron = Utils.findElement("liveTab_nextChevron");
            }
            elNextChevron.click();
            channelNumber = MyPages._livePage.getChannelNumber();

        }
        Assert.assertEquals(channelNumber, lastChannel);
    }

    /**
     * Navigates to the first channel in the specified lineup.
     */
    @Then("I go to the first channel for \"([^\"]*)\" lineup$")
    public void navigateToFirstChannel(String lineup) throws Throwable {
        MobileElement elPreviosChevron = null;
        String channelNumber = MyPages._livePage.getChannelNumber();
        System.out.println("Start channel : " + channelNumber);
        String firstChannel = xConsts.AUTOTEST_FIRST_CHANNEL;

        while (!channelNumber.equals(firstChannel)) {
            elPreviosChevron = Utils.findElement("liveTab_previousChevron");
            elPreviosChevron.click();
            channelNumber = MyPages._livePage.getChannelNumber();
        }
        System.out.println("channel :  " + channelNumber);
        System.out.println("firstChannel :  " + firstChannel);
        Assert.assertEquals(channelNumber, firstChannel);
    }

    /**
     * Verifies that the channel displayed on the Live tab corresponds to the first channel
     * in the specified lineup.
     */
    @Then("I see that Live Tab channel value corresponds to the first channel for \"([^\"]*)\" lineup$")
    public void nliveTabChannel(String lineup) throws Throwable {
        String channelNumber = MyPages._livePage.getChannelNumber().trim();
        System.out.println("Channel number : " + channelNumber);
        String expectedChannel = xConsts.AUTOTEST_FIRST_CHANNEL;
        Assert.assertEquals(channelNumber, expectedChannel);
    }

    /**
     * Verifies that the channel displayed on the Live tab corresponds to the last channel
     * in the specified lineup.
     */
    @Then("I see that Live Tab channel value corresponds to the last channel for \"([^\"]*)\" lineup$")
    public void lastChannelForLineup(String lineup) throws Throwable {
        String channelNumber = MyPages._livePage.getChannelNumber().trim();
        System.out.println("Channel number : " + channelNumber);
        String expectedChannel = xConsts.AUTOTEST_LAST_CHANNEL;
        Assert.assertEquals(channelNumber, expectedChannel);
    }


    @And("^I choose promotional link on Live Tab$")
    public void iChoosePromotionalLink() throws Throwable {
        MyPages._livePage.promoLinkClick();
    }

    /**
     * Remembers the current state of the Record button on the Live tab.
     */
    @And("^I remember Record button state$")
    public void iRememberRecordButtonState() throws Throwable {
        MyPages._livePage.saveRecordButtonSelectionState();
    }

    @Then("^I click Record button on the Live tab$")
    public void iClickRecordButton() throws Throwable {
        MyPages._livePage.recordBarButtonClick();
    }

    @Then("^the state of Record opton button on the Live tab changed to opposite$")
    public void stateOfRecordButtonChangedToOpposite() throws Throwable {
        boolean recordButtonSavedState = MyPages._livePage.getRecordButtonSavedState();
        boolean recordButtonSelected = MyPages._livePage.isSelected(xConsts.RECORDING);
        Assert.assertNotEquals(recordButtonSavedState, recordButtonSelected);
    }

    @Then("^I see Record Dialog$")
    public void iSeeRecordDialog() throws Throwable {
        Assert.assertTrue(MyPages._livePage.isShown("RECORD_DIALOG"));
    }


    @And("^I click Record option button on Record Dialog$")
    public void iClickRecordButtonOnRecordDialog() throws Throwable {
        MyPages._livePage.buttonClick(xConsts.RECORD_EPISODE_CHECKBOX);
    }

    @Then("^Record opton button is checked$")
    public void optionButtonIsChecked() throws Throwable {
        boolean b = MyPages._livePage.isSelected(xConsts.RECORD_EPISODE_CHECKBOX);
        Assert.assertTrue(b);
    }

    @And("^I dismiss Record Dialog$")
    public void iDismissRecordDialog() throws Throwable {
        driver.navigate().back();
    }

    @Then("^Record button on the Live tab is now Recording with a checkmark$")
    public void rocordButtonIsRecording() throws Throwable {
        boolean b = MyPages._livePage.isSelected(xConsts.RECORDING);
    }

    @Then("^I go to the next channel with \"(next|previous)\" chevron$")
    public void goToTheNextChannel(String value) throws Throwable {
        if (value.equalsIgnoreCase("next")) {
            if (Utils.isAndroid()) {
                MobileElement cont = Utils.findElement("liveTab_layoutTabContainer");
                MobileElement rightChevron = cont.findElement(By.id(Utils.getId("liveTab_nextChevron")));
                rightChevron.click();
            } else {
                MobileElement chevron = MyPages._livePage.getNextChannelChevron();
                if (chevron.isDisplayed()) {
                    MyPages._livePage.navigateToNextChannel();
                }
            }

        } else if (value.equalsIgnoreCase("video next")) {
            MobileElement videoCont = Utils.findElement("videoOverlay");
            MobileElement rightChevron = videoCont.findElement(By.id(xConsts.ANDROID_ID_PREFIX + xConsts.ANDROID_IDS.get("liveTab_nextChevron")));
            rightChevron.click();
        }
    }

    @And("^I navigate Next on Live Tab$")
    public void navigateNextOnLiveTab() throws Throwable {
        MobileElement chevron = MyPages._livePage.getNextChannelChevron();
        if (chevron.isDisplayed()) {
            MyPages._livePage.navigateToNextChannel();
        }
    }


    @And("^I cant navigate left on Live tab$")
    public void iCantNavigateLeft() throws Throwable {
        boolean invisible = false;
        if (Utils.isAndroid()) {

        } else {
            invisible = Utils.isElementInVisible(Utils.getId("liveTab_previousChevron"), 4);
        }
        Assert.assertTrue(invisible);
    }

    @And("^I cant navigate right on Live tab$")
    public void iCantNavigateRight() throws Throwable {
        //"liveTab_nextChevron"
        boolean invisible = false;
        if (Utils.isAndroid()) {

        } else {
            invisible = Utils.isElementInVisible(Utils.getId("liveTab_nextChevron"), 4);
        }
        Assert.assertFalse(invisible);
    }

    @And("^I remember current live channel number on the Live tab as \"([^\"]*)\"$")
    public void iRememberCurrentChannel1(String channel) throws Throwable {
        switch (channel.toUpperCase()) {
            case "CHANNEL1":
                MyPages._livePage.rememberFirstChannelNumber();
                break;
            case "CHANNEL2":
                MyPages._livePage.rememberNextChannelNumber();
                break;
            case "CHANNEL3":
                MyPages._livePage.rememberChannel(3);
                break;
            default:
                throw new IllegalArgumentException("Invalid element: " + channel);
        }
    }

    @Then("^I see that channel1 has loaded into the viewer on Live tab$")
    public void channelLoadedIntoTheViewer() throws Throwable {
        String firstRememberedChannel = MyPages._livePage.getFirstRememberedChannelNumber();
        String liveTabChannelNumber = MyPages._livePage.getChannelNumber();

        Assert.assertEquals(firstRememberedChannel, liveTabChannelNumber);
    }

    @Then("^I see that channel2 has loaded into the viewer on Live tab$")
    public void secondChannelLoadedIntoTheViewer() throws Throwable {
        String secondRememberedChannel = MyPages._livePage.getNextRememberedChannelNumber();
        String bannerChannelNumber = MyPages._livePage.getChannelNumber();

        Assert.assertEquals(secondRememberedChannel, bannerChannelNumber);
    }

    @Then("^I see that channel \"([^\"]*)\" has loaded into the viewer on Live tab$")
    public void secondChannelHasLoadedIntoTheViewer(String expectedChannel) throws Throwable {
        String channel = MyPages._livePage.getChannelNumber();
        Assert.assertEquals(channel, expectedChannel);
    }

    @Then("^I see that channel3 has loaded into the viewer on Live tab$")
    public void ChannelLoadedIntoTheViewer() throws Throwable {
        String lastRememberedChannel = MyPages._livePage.geRememberedChannelNumber(3);
        String bannerChannelNumber = MyPages._livePage.getChannelNumber();
        Assert.assertEquals(lastRememberedChannel, bannerChannelNumber);
    }


    @Then("^I expect to see next available channel on Live tab$")
    public void iExpectToSeeNextAvailableChannel() throws Throwable {
        String nextAvailableChannel = MyPages._livePage.getChannel("AUTOTEST", 1);
        String channel = MyPages._livePage.getChannelNumber();
        Assert.assertEquals(channel, nextAvailableChannel);
    }


    @Then("^I see that Live tab show name is the same as I remember$")
    public void shownameOnLiveTab() throws Throwable {
        String showName = MyPages._livePage.getLiveTabShowTitle();
        String remeberedShowName = MyPages._livePage.getShowName();
        Assert.assertEquals(showName, remeberedShowName);
    }


    @Then("^I see that channel1 is the same as channel2$")
    public void ChannelTheSame() throws Throwable {
        String ch3 = MyPages._livePage.geRememberedChannelNumber(3);
        String ch2 = MyPages._livePage.geRememberedChannelNumber(2);
        String ch1 = MyPages._livePage.geRememberedChannelNumber(1);
        Assert.assertNotEquals(ch2, ch1);
        Assert.assertEquals(ch3, ch1);
    }

    @And("^I will remember show name on Live page$")
    public void iwillRememberShowName() throws Throwable {
        MyPages._livePage.setShowName();
    }


    @Then("^I background an app$")
    public void iBackgrounApp() throws Throwable {
        if (Utils.isAndroid()) {
            ((AndroidDriver) driver).pressKeyCode(AndroidKeyCode.HOME);
            Thread.sleep(2000);
        } else {
            driver.runAppInBackground(Duration.ofSeconds(3));
        }
        MyPages._pip = new PiP(driver);
    }

    @Then("^I deactivate an app$")
    public void iDeactivateApp() throws Throwable {
        if (Utils.isAndroid()) {
            driver.runAppInBackground(null);
        } else {
            driver.runAppInBackground(Duration.ofSeconds(3));
        }
        MyPages._pip = new PiP(driver);
    }

    @Then("^I background phone$")
    public void iBackgrounApp2() throws Throwable {
        if (Utils.isAndroid()) {
            driver.runAppInBackground(Duration.ofSeconds(8));
        } else {
            driver.runAppInBackground(Duration.ofSeconds(-1));
        }
        MyPages._pip = new PiP(driver);
    }

    @Then("^I background an app1$")
    public void iBackgrounApp1() throws Throwable {
        driver.executeScript("mobile: pressButton", ImmutableMap.of("name", "home"));
        try {
            Thread.sleep(1000);
        } catch (Exception ign) {
        }
        MyPages._pip = new PiP(driver);
    }

    @Then("^I bring app from background$")
    public void iBringAppFromBackground() throws Throwable {
        try {
            if (Utils.isAndroid()) {
                driver.activateApp(xConsts.BUNDLE_ID);
                driver.getOrientation();
                Thread.sleep(2000);
            } else {
                driver.activateApp(xConsts.BUNDLE_ID_IOS);
            }
        } catch (Exception e) {
            Utils.takeScreenshot("Bring_app_from_background");
            throw e;
        }
    }

    @Then("^I kill and start app$")
    public void relaunch() throws Throwable {
        try {
            driver.terminateApp(Utils.getBundleId());
            driver.activateApp(Utils.getBundleId());
        } catch (Exception e) {
            Utils.takeScreenshot("kill__and_start_app");
            throw e;
        }
    }

    @And("^I dont see Loading circle$")
    public void iDontSeeLoadingCircle() throws Throwable {
        boolean visible = true;
        MobileElement circle = null;
        if (Utils.isAndroid()) {
            visible = Utils.isElementInVisible("com.example.tvapp:id/layout_progress", 15);
            Assert.assertTrue(visible);
        }
    }


    @Then("^I dont see PIP window on the screen$")
    public void iDontSeePIPWindow() throws Throwable {
        boolean elAbsent = false;
        if (!Utils.isPIPSupported()) {
            return;
        }
        if (Utils.isAndroid()) {
            elAbsent = Utils.isElementInVisible(xConsts.ANDROID_IDS2.get("pipWindow_content"), 5);
            ApplicationState state = driver.queryAppState(xConsts.BUNDLE_ID);
            System.out.println("st :" + state.toString());
        } else {
            elAbsent = Utils.verifyElementAbsent(xConsts.PIP_IOS_CLOSE_WINDOW);
        }
        Assert.assertTrue(elAbsent);
    }

    @Then("^I tap PIP window$")
    public void iTapPIPWindow() throws Throwable {
        Utils.iosTapPipWindow();
    }


    @Then("^I will see \"([^\"]*)\" button on PIP window$")
    public void iSeeOnPipWindow(String name) throws Throwable {
        Assert.assertTrue(MyPages._pip.isShown(name));
    }

    @Then("^I tap video screen$")
    public void tapScreen() throws Throwable {
        if (Utils.isAndroid()) {
            Utils.tapAppScreen();
        } else {
            Dimension size = HooksService.getDriver().manage().window().getSize();
            int startx = (int) (size.width * 0.5);
            int starty = size.height / 4;
            TouchAction touchAction1 = new TouchAction(driver);
            touchAction1.tap(PointOption.point(startx, starty)).perform();
        }
    }


    @Then("^I tap Full Screen view button on the top of Live page$")
    public void tapFSButtonOnLiveTab() throws Throwable {
        boolean shown = Utils.isElementVisible("main_grabBar", 10);
        MobileElement el = Utils.findElement("fullScreen_lowerChevron");
        if (!el.isEnabled()) {
            MyPages._fullScreenPage = (FullScreenPage) MyPages._livePage.buttonClick("FULL SCREEN VIEW");
        } else {
            if (MyPages._fullScreenPage != null) {
                MyPages._fullScreenPage = MyPages._fullScreenPage;
            } else {
                MyPages._fullScreenPage = new FullScreenPage(driver);
            }
        }
    }


    @And("^I go to the next channel with \"right\" chevron on the viewer$")
    public void dddd() throws Throwable {
        if (!Utils.isAndroid()) {
            MyPages._livePage.tapRightChevronInTheViewer();
        }
    }

    @And("^I go to the previous channel with \"left\" chevron on the viewer$")
    public void goTopreviousChannel() throws Throwable {
        if (!Utils.isAndroid()) {
            MyPages._livePage.tapLeftChevronInTheViewer();
        }
    }


    //compare live show attributes in Full Screen view and Live tab
    @And("^\"([^\"]*)\" Full Screen View attributes are the same as I see on Live tab$")
    public void portraitAndLandscapeAttributesAreTheSame(String last) throws Throwable {
        int index1 = 0;

        String title1 = MyPages._fullScreenPage.getStoredShowAttributesInfo(index1).getTitle();
        String attributes1 = MyPages._fullScreenPage.getStoredShowAttributesInfo(index1).getAttributes();
        String title2 = MyPages._livePage.getLiveTabShowTitle();
        String attributes2 = MyPages._livePage.getLiveTabShowTAttributes();
        if (title1.equalsIgnoreCase(title2)) {
            Assert.assertEquals(attributes2, attributes1);
        } else {
            Assert.assertEquals(title1, title2);
        }
    }


    @And("^\"([^\"]*)\" Full Screen View channel are the same as I see on Live tab$")
    public void channelsAreTheSame(String last) throws Throwable {
        String liveTabChannelNumber = MyPages._livePage.getChannelNumber();
        String fullScreenChannelNumber = MyPages._fullScreenPage.getRememberedChannelNumber();
        Assert.assertEquals(fullScreenChannelNumber, liveTabChannelNumber);
    }
}