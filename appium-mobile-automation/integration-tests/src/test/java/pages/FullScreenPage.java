package pages;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import stepdefs.Utils;
import stepdefs.xConsts;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FullScreenPage extends BasePage {


    @iOSFindBy(accessibility = "player_stationImage")
    private MobileElement stationIcon;

    @AndroidFindBy(id = "com.example.tvapp:id/image_show_tabs")
    private MobileElement showTabsChevron;

    @iOSFindBy(accessibility = "player_stationIcon")
    private MobileElement loadingStationIcon;

    @AndroidFindBy(id = "com.example.tvapp:id/layout_progress")
    @iOSFindBy(accessibility = "player_activityIndicator")
    private MobileElement loadingVideoOverlay;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/image_play_pause")
    @iOSFindBy(accessibility = "player_pausePlayButton")
    private MobileElement playPauseOverlay;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/text_title")
    @iOSFindBy(accessibility = "player_showTitle")   // ex. "Match Game"
    private MobileElement showName;

    @AndroidFindBy(id = "com.example.tvapp:id/text_attributes")
    @iOSFindBy(accessibility = "player_airingDetails")       //  ex "2.4 BUZZR / 2:30PM / 30MIN / TVPG"
    private MobileElement showAttributes;


    @AndroidFindBy(id = "com.example.tvapp:id/text_attributes_recording") //  ex "RECORDED NOV 17 3:30PM"
    private MobileElement recordedAttributes;

    @iOSFindBy(accessibility = "player_nextIcon")
    private MobileElement nextChannelChevron;

    @iOSFindBy(accessibility = "player_previousIcon")
    private MobileElement previousChannelChevron;

    @AndroidFindBy(id = "com.example.tvapp:id/menu_last")
    @iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"last_button\"]")
    private MobileElement lastButton;


    @AndroidFindBy(id = "com.example.tvapp:id/menu_full_screen")
    @iOSFindBy(accessibility = "fullscreen_button")
    private MobileElement fullScreenViewButton;

    @AndroidFindBy(id = "com.example.tvapp:id/menu_recordings")
    @iOSFindBy(accessibility = "recordings_tab")
    private MobileElement recordingsButton;

    @AndroidFindBy(id = "com.example.tvapp:id/menu_guide")
    @iOSFindBy(id = "guide_tab")
    private MobileElement guideButton;

    @AndroidFindBy(id = "com.example.tvapp:id/menu_live")
    @iOSFindBy(accessibility = "live_tab")
    private MobileElement liveButton;

    @iOSFindBy(accessibility = "id_community_tab")
    private MobileElement communityButton;

    @AndroidFindBy(id = "com.example.tvapp:id/menu_overflow")
    @iOSFindBy(accessibility = "settings_button")
    private MobileElement topDropDownMenuButton;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/image_cc_play")
    @iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"player_ccButton\"]")
    private MobileElement ccButton;

    @AndroidFindBy(id = "com.example.tvapp:id/seekbar_scrubber_portrait")
    @iOSFindBy(accessibility = "player_slider")
    private MobileElement playerSlider;

    @AndroidFindBy(id = "com.example.tvapp:id/image_mute_play")
    @iOSFindBy(accessibility = "player_muteButton")
    private MobileElement muteButton;

    @iOSFindBy(accessibility = "player_ellapsedTimeLabel")
    private MobileElement labelIime;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/image_skip_back")
    @iOSFindBy(accessibility = "player_seekBackButton")
    private MobileElement play30SecBackOverlay;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/image_skip_forward")
    @iOSFindBy(accessibility = "player_seekForwardButton")
    private MobileElement play30SecForwardOverlay;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/image_info")
    @iOSFindBy(accessibility = "player_moreButton")
    private MobileElement moreButton;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @iOSFindBy(accessibility = "main_grabBar")
    private MobileElement grabBarButton;          //collapse Full Screen View

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @iOSFindBy(accessibility = "player_warningLabel")
    private MobileElement blackoutText;


    public static String showTitle = "";
    public static String channelNumber = "";
    ArrayList<RecordingInfo> showsList = new ArrayList<RecordingInfo>();
    ArrayList<ShowInfo> liveShowsList = new ArrayList<ShowInfo>();


    public FullScreenPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isInitialized() {
        return showTabsChevron.isDisplayed();
    }

    public boolean isShowNameShown() {
        return showName.isDisplayed();
    }

    public boolean isPlayPauseShown() {
        return playPauseOverlay.isDisplayed();
    }

    /**
     * Checks if the Play/Pause button is visible on the full-screen view.
     * If the button is not initially found, it attempts to make it visible by tapping the screen
     * and interacting with the play/pause overlay.
     */

    public boolean isPlayPauseButtonShown() {
        boolean visible = false;
        try {
            MobileElement el = Utils.findElement("play_pause");
        }catch(Exception e) {
            Utils.tapBtvScreen();
            visible = playPauseOverlay.isDisplayed();
            if (!visible) {
                Utils.playPauseButtonClick(10);
            }
            visible = playPauseOverlay.isDisplayed();
        }
        return visible;
    }

    public String getShowTitle() {
        return showName.getText();
    }

    /**
     * Retrieves the blackout message displayed when streaming is stopped.
     */

    public String getBlackoutText() {
        boolean visible = false;
        String text = "";
        if (Utils.isAndroid()) {

        } else {
            visible = Utils.isElementVisible(xConsts.APPLE_IDS.get("blackout_text"), 8);
            text = Utils.findElement("blackout_text").getText();
        }
        return text;
    }

    /**
     * Retrieves the text of the player attributes.
     */
    public String getAttributes() {
        MobileElement el = null;
        el = Utils.findElement("player_attributes");
        return el.getText();
    }


    /**
     * Method determines if the "RECORDED" attribute is present in the player attributes.
     */
    public boolean isRecordedAttributePresent() {
        MobileElement el = null;
        boolean isPresent = false;
        if (Utils.isAndroid()) {
            el = Utils.findElement("player_recordingAttributes");  //text "RECORDED NOV 17 3:30PM"
        } else {
            el = Utils.findElement("player_attributes");
        }
        if (el.getText().contains("RECORDED")) {
            isPresent = true;
        }
        return isPresent;
    }

    /**
     * Checks if a specified UI element is currently displayed on the screen.
     */

    public boolean isShown(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("isShown(): Element name cannot be null or empty.");
        }

        MobileElement el = null;
        String upperName = name.toUpperCase();

        switch (upperName) {
            case "PLAY/PAUSE":
                el = playPauseOverlay;
                break;
            case "LAST":
                el = lastButton;
                break;
            case "SETTINGS":
                el = topDropDownMenuButton;
                break;
            case "30 SEC FORWARD":
                el = play30SecForwardOverlay;
                break;
            case "30 SEC BACK":
                el = play30SecBackOverlay;
                break;
            case "SHOW TITLE":
                el = showName;
                break;
            case "CC":
                el = ccButton;
                break;
            case "PLAYER SLIDER":
                el = playerSlider;
                break;
            case "PLAYER MUTE BUTTON":
                el = muteButton;
                break;
            case "SLIDER TIME LABEL":
                el = labelIime;
                break;
            case "MORE BUTTON":
                el = moreButton;
                break;
            case "COLLAPSE FULL SCREEN CHEVRON":
                el = grabBarButton;
                break;
            default:
                throw new IllegalArgumentException("isShown(): Unsupported element name '" + name + "'. "
                        + "Expected values: ['PLAY/PAUSE', 'LAST', 'SETTINGS', '30 SEC FORWARD', '30 SEC BACK', "
                        + "'SHOW TITLE', 'CC', 'PLAYER SLIDER', 'PLAYER MUTE BUTTON', 'SLIDER TIME LABEL', "
                        + "'MORE BUTTON', 'COLLAPSE FULL SCREEN CHEVRON'].");
        }

        if (el == null) {
            throw new IllegalStateException("isShown(): Element '" + name + "' is null. Check element initialization.");
        }

        return el.isDisplayed();
    }



    /**
     * Retrieves the channel name displayed near the right chevron in the  Full Screen view.
     */

    public String getRightChevronChannelName() {
        String channelName = "";
        MobileElement el = null;
        if (Utils.isAndroid()) {

        } else {
            el = Utils.findElement("player_nextStationName");
            channelName = el.getAttribute("value");
        }
        return channelName;  //return "CBS"
    }

    /**
     * Retrieves the channel name displayed near the left chevron in the Full Screen view.
     */
    public String getLeftChevronChannelName() {
        String channelName = "";
        MobileElement el = null;
        if (Utils.isAndroid()) {

        } else {
            el = Utils.findElement("player_previousStationName");
            channelName = el.getAttribute("value");
        }
        return channelName;  //return "CBS"
    }


    /**
     * Extracts and returns the channel number from the Full Screen View attributes.
     * On Android, it splits the attribute text using a predefined regex,
     * on ios, it splits the text using " / ".
     */

    public String getChannelNumber() {
        String channelNumber = "";
        MobileElement el = null;
        if (Utils.isAndroid()) {
            el = Utils.findElement("player_attributes");
            System.out.println("attributes : " + el.getText());
            String arr[] = (el.getText()).split(xConsts.CHANNEL_NUMBER__REGEX);  // two non-breaking space Unicode char.
            if (arr.length > 1) {
                channelNumber = arr[0];     //"5.1. CBS5"
            }
        } else {
            //Full Screen View
            el = Utils.findElement("player_attributes");
            System.out.println(el.getText());
            String arr[] = (el.getText()).split(" / ");
            if (arr.length > 1) {
                channelNumber = arr[0];
            }
        }
        return channelNumber.trim();  //return "5.1. CBS"
    }

    public void setChannelNumber(){
        channelNumber =  getChannelNumber();
    }

    public String getRememberedChannelNumber(){
        return channelNumber;
    }


    /**
     * Stores the show title from the Full Screen View.
     * The title should match the value from the Guide's `XCUIElementTypeCell` collection
     * where the element has the name `"guide_cell_label"` and an index of `0`.
     */

    public void setLineupDefaultShowTitle() throws Exception {

        this.showTitle = this.getShowTitle();
        System.out.println("set showTitle : " + this.showTitle);
    }

    public String getLineupDefaultShowTitle() throws Exception {

        return this.showTitle;
    }

    public boolean isDeleteButtonsShown() {
        MobileElement el = Utils.findElementByXPath("deleteButton");
        return el.isDisplayed();
    }

    public void tapDeleteButton() {
        MobileElement el = Utils.findElementByXPath("deleteButton");
        el.click();
    }

    public MobileElement getLoadingVideoOverlay() {
        return loadingVideoOverlay;
    }


    public MobileElement getPlayPauseOverlay() {
        return playPauseOverlay;
    }
    /**
     * Stores the recording attributes for a show.
     * Retrieves the show title and attributes from the Full Screen View and stores them
     * in the `showsList` collection as a `RecordingInfo` object.
     */

    public void storeRecordingAttributes(int index) {
        String title = showName.getText();
        MobileElement attributesElement = Utils.findElement("player_attributes");
        String attributes = attributesElement.getText();
        int recId = Utils.isAndroid() ? showsList.size() : index;

        RecordingInfo info = new RecordingInfo("", "", title, recId, attributes);
        showsList.add(info);

        if (Utils.isAndroid()) {
            System.out.println("Store : " + recId + "  " + title + " " + attributes);
        }
    }


    public int getAttributesListSize() {
        int infoSize = showsList.size();
        return infoSize;
    }


    /**
     * Retrieves the stored recording attributes for a given ID.
     * Searches through the `showsList` and returns the `RecordingInfo` object that matches the provided ID.
     */

    public RecordingInfo getStoredAttributesInfo(int id) {
        System.out.println("In getStoredAttributesgInfo " + id + " ...");
        for (RecordingInfo e : showsList) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;  // Return null if no match is found
    }


    /**
     * Retrieves the stored show attributes for a given ID.
     * Searches through the `liveShowsList` and returns the `ShowInfo` object that matches the provided ID.
     */

    public ShowInfo getStoredShowAttributesInfo(int id) {
        System.out.println("In getStoredShowAttributesgInfo " + id + " ...");
        for (ShowInfo e : liveShowsList) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;  // Return null if no match is found
    }


    /**
     * Stores the show attributes (title and player attributes) for a specific show, either using the provided index
     * or the size of the `liveShowsList`.
     * The method retrieves the show title and attributes from the UI elements and adds them to the `
     * liveShowsList` as a new `ShowInfo` object.
     */

    public void storeShowAttributes(int index) {
        if (Utils.isAndroid()) {
            String title = showName.getText();
            MobileElement attributesElement = Utils.findElement("player_attributes");
            String attributes = attributesElement.getText();
            int recId = liveShowsList.size();
            ShowInfo info = new ShowInfo("", "", title, recId, attributes);
            liveShowsList.add(info);
        } else {
            String title = showName.getText();
            MobileElement attributesElement = Utils.findElement("player_attributes");
            String attributes = attributesElement.getText();  //  "5.1 CBS5 / 3PM / 1HR / TVPG"

            ShowInfo info = new ShowInfo("", "", title, index, attributes);
            liveShowsList.add(info);
        }
    }


    /**
     * Taps the Play/Pause button in Full Screen View while in landscape orientation on an Android device.
     *
     * The method calculates the screen dimensions to determine a tap position near the upper quarter
     * of the screen to reveal controls. It then attempts to tap the Play/Pause button using a direct
     * element interaction.
     */
    public void tapPlayPauseInLandscapeOrientation() {
        //Show Play/Pause button
        Dimension size = Utils.getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.5);
        int starty = size.height / 4;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence clickSequence = new Sequence(finger, 1);
        clickSequence.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startx, starty));
        clickSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        ((AndroidDriver) Utils.getDriver()).perform(Arrays.asList(clickSequence));

        //Tap Play/Pause button
        MobileElement playPauseBtn = Utils.findElement("play_pause");
        playPauseBtn.click();
    }



    /**
     * Handles button click actions based on the provided button name. This method identifies the button using the
     * given `name` and performs the corresponding action, such as clicking the button or interacting
     * with the UI elements.
     */

    public BasePage buttonClick(String name) {
        BasePage bp = null;
        switch (name.toUpperCase()) {
            case "30 SEC BACK":
                play30SecBackOverlay.click();
                break;
            case "30 SEC FORWARD":
                play30SecForwardOverlay.click();
                break;
            case "MORE BUTTON":
                moreButton.click();
                bp = new InfoDialog(driver);
                break;
            case "PLAY/PAUSE":
                playPauseOverlay.click();
                break;
            case "MUTE BUTTON":
                muteButton.click();
                break;
            case "COLLAPSE FULL SCREEN":
                try {
                    MobileElement el = Utils.findElement("collapseScreen_chevron");
                    Utils.tapElement(el);
                } catch (Exception e) {
                    System.err.println("Error occurred while trying to tap the collapse screen chevron: " + e.getMessage());
                    e.printStackTrace();
                }
                break;

            default:
                throw new IllegalArgumentException("FullScreen Page click - Invalid element: " + name);
        }
        return bp;
    }


    /**
     * Checks whether the mute button is currently activated (muted) on the player.
     *
     * The behavior of this method depends on the platform:
     * - For Android: The mute status is determined by the "selected" attribute of the mute button (currently not implemented).
     * - For non-Android (iOS): The mute status is determined by the "value" attribute of the mute button,
     * where a value of `1` indicates the button is active (muted), and `0` or `null` indicates the button is
     * inactive (unmuted).
     */
    public boolean isMuted() {
        boolean mute = false;
        try {
            if (Utils.isAndroid()) {
                String selectedString = muteButton.getAttribute("selected");
                mute = Boolean.valueOf(selectedString);
            } else {
                // For non-Android devices, checking the mute button's value
                String val = muteButton.getAttribute("value");
                if (val == null || Integer.parseInt(val) == 0) {
                    mute = false;  // Mute is off
                } else {
                    mute = true;  // Mute is on
                }
            }
        } catch (Exception e) {
            System.err.println("Error occurred while checking the mute status: " + e.getMessage());
            e.printStackTrace();
        }
        return mute;
    }


    /**
     * Taps the screen to simulate an interaction with the Full Screen View.
     *
     * This method taps on a specified point on the screen to trigger a Full Screen interaction. The tap is performed at a calculated position based on the screen's dimensions:
     * - The X-coordinate is at the center of the screen (50% of screen width).
     * - The Y-coordinate is at one-sixth of the screen height.
     */
    public void tapFullScreenView() {
        int i = 0;
        Utils.tapBtvScreen();
        Dimension size = Utils.getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.5);
        int starty = size.height / 6;

        TouchAction touchAction1 = new TouchAction(Utils.getDriver());
        touchAction1.tap(PointOption.point(startx, starty))
                .perform();
    }

    /**
     * Checks if the show title is visible on the screen.
     *
     * This method attempts to locate the show title element using the identifier "player_title".
     * If the element is not found, it performs a tap on the Full Screen View to trigger the
     * visibility of the title and attempts to find the element again.
     */
    public boolean isShowTitleVisible() {
        boolean ret = false;
        try {
            MobileElement el = Utils.findElement("player_title");
            ret = true;
        } catch (Exception e) {
            this.tapFullScreenView();
            MobileElement el = Utils.findElement("player_title");
            ret = true;
        }
        return ret;
    }

}
