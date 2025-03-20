package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebElement;
import stepdefs.Utils;
import stepdefs.xConsts;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * CommonComponentsPage class represents the common UI components of the TV app automation.
 * It provides locators and actions for various elements available on both
 * Android and iOS platforms, including navigation buttons, video player controls,
 * and permission dialogs. This class extends BasePage and serves as a base for
 * interacting with core app functionalities.
 */


public class CommonComponentsPage extends BasePage {
    @AndroidFindBy(id = "com.example.tvapp:id/menu_last")
    @iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"last_button\"]")
    private MobileElement lastButton;

    @AndroidFindBy(id = "com.example.tvapp:id/menu_full_screen")
    @iOSFindBy(accessibility = "fullscreen_button")
    private MobileElement fullScreenViewButton;

    @AndroidFindBy(id = "com.example.tvapp:id/menu_recordings")
    @iOSFindBy(accessibility = "recordings_tab")
    private MobileElement recordingsButton;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/menu_local")
    @iOSFindBy(id = "guide_tab")
    private MobileElement guideButton;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @iOSFindBy(accessibility = "guide")
    private MobileElement guide;

    @AndroidFindBy(id = "com.example.tvapp:id/menu_live")
    @iOSFindBy(accessibility = "live_tab")
    private MobileElement liveButton;

    @AndroidFindBy(id = "com.example.tvapp:id/menu_community")
    @iOSFindBy(accessibility = "id_community_tab")
    private MobileElement communityButton;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/menu_overflow")
    @iOSFindBy(accessibility = "settings_button")
    private MobileElement topDropDownMenuButton;

    //ios only
    @iOSFindBy(accessibility = "AirPlay")
    private MobileElement airPlayButton;

    @iOSFindBy(accessibility = "GCKUICastButton")
    private MobileElement castButton;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = " android:id/navigationBarBackground")
    private MobileElement androidBack;

    /**
     * Location Service dialog
     **/
    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_allow_button")
    @iOSFindBy(accessibility = "Allow While Using App")
    private MobileElement permissionAllowButton; //enabled :true/false


    @AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
    private MobileElement allowLocationButton; //e  While Using app button

    /**
     * Track activity dialog
     **/
    @iOSFindBy(accessibility = "Ask App not to Track")      //
    private MobileElement notTrackButton;

    /**
     * Allow access to location  dialog
     */
    @AndroidFindBy(id = "android:id/message")
    @iOSFindBy(accessibility = "alert_titleLabel")            // "Please allow access to your location" - label
    private MobileElement pleaseAllowAccessLocationMessage;

    /**
     * Allow access to location  dialog message
     */
    @iOSFindBy(accessibility = "alert_messageLabel")
    private MobileElement PhoenixBtvNeedsToKnowMessage;


    @AndroidFindBy(id = "android:id/button1")
    private MobileElement pleaseAllowAccessLocationOkButton;

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_deny_button")
    @iOSFindBy(accessibility = "Don’t Allow")
    private MobileElement permissionDenyButton;


    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_message")
    //Allow to access this device's location?
    private MobileElement permissionAllowText;

    @AndroidFindBy(id = "com.android.packageinstaller:id/do_not_ask_checkbox")
    //Allow app to access this device's location?
    private MobileElement dontaskCheckbox;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)  // Google update button
    @AndroidFindBy(id = "android:id/button1")
    private MobileElement googleUpdate;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/view_video")
    @iOSFindBy(accessibility = "player")
    private MobileElement viewerElement;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/layout_expandable")
    @iOSFindBy(accessibility = "recording")
    private MobileElement tab;

    @AndroidFindBy(id = "com.example.tvapp:id/image_grabber")  //arrow up, to expand Guide or Recordings
    @iOSFindBy(accessibility = "main_grabBar")
    private MobileElement upButton;

    @AndroidFindBy(id = "com.example.tvapp:id/image_grabber")  //arrow down, expandable tab
    @iOSFindBy(accessibility = "grab_bar_down")
    private MobileElement downButton;

    private CurrentVideoPage viewer;
    public static String defaultGuideShowName = "";
    boolean googlePlayServicesNotUpdated = true;
    int viewerHeight = 0;
    int tabHeight = 0;
    int tabY = 0;
    int tabExpanedeHeight = 0;
    int extendedTabY = 0;    //store extended tab y coordinate for Guide, Recordings or Community tab

    ArrayList<String> autoutoTestLineup = new ArrayList<String>();
    ArrayList<String> bayAreaLineup = new ArrayList<String>();

    public CommonComponentsPage(AppiumDriver driver, ArrayList<String> lineup) {
        super(driver);
        this.setCurrentVideo();

        for (String channel : lineup) {
            if (channel.contains("Bay")) {
                bayAreaLineup.add(channel);
            } else {
                autoTestLineup.add(channel);
            }
        }
    }

    /**
     * Initializes the current video viewer.
     */
    public void setCurrentVideo() {
        viewer = new CurrentVideoPage(driver);
    }

    /**
     * Retrieves the current video viewer object.
     */
    public CurrentVideoPage getCurrentViewer() {
        return viewer;
    }

    /**
     * Checks if the page is properly initialized by verifying the visibility of the menu element.
     */
    public boolean isInitialized() {
        boolean visible = false;
        if (Utils.isAndroid()) {
            visible = Utils.isElementVisible(Utils.getId("menu"), 8);
        } else {
            visible = Utils.isElementVisible(xConsts.APPLE_IDS.get("menu"), 8);
        }
        return visible;
    }

    /**
     * Method returns true if Last button is enabled
     */
    public boolean isLastEnabled() {
        return lastButton.isEnabled();
    }


    /**
     * This method simulates a toolbar button click in the application and navigates to the
     * corresponding page object
     */
    public BasePage toolbarButtonClick(String name) {
        BasePage bp = null;
        MobileElement el = null;
        switch (name.toUpperCase()) {
            case "LIVE":
                liveButton.click();
                if (MyPages._livePage != null) {
                    bp = MyPages._livePage;
                } else {
                    bp = new LivePage(driver);
                }
                break;
            case "RECORDINGS":
                recordingsButton.click();
                if (MyPages._recordingsPage != null) {
                    bp = MyPages._recordingsPage;
                } else {
                    bp = new RecordingsPage(driver);
                }
                break;
            case "GUIDE":
                guideButton.click();
                if (MyPages._guidePage != null) {
                    bp = MyPages._guidePage;
                } else {
                    bp = new GuidePage(driver);
                }
                break;
            case "COMMUNITY":
                communityButton.click();
                if (MyPages._guidePage != null) {
                    bp = MyPages._guidePage;
                } else {
                    bp = new GuidePage(driver);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid element: " + name);
        }
        return bp;
    }


    /**
     * This method checks whether a given toolbar button is currently selected in the app.
     * The selection status is determined differently for Android and iOS platforms.
     */
    public boolean isToolbarButtonSelected(String name) {
        boolean ret = false;

        if (Utils.isAndroid()) {
            switch (name.toUpperCase()) {
                case "LIVE":
                    ret = Boolean.parseBoolean(liveButton.getAttribute("selected"));
                    break;
                case "RECORDINGS":
                    ret = Boolean.parseBoolean(recordingsButton.getAttribute("selected"));
                    break;
                case "GUIDE":
                    ret = Boolean.parseBoolean(guideButton.getAttribute("selected"));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid element: " + name);
            }
        } else {

            switch (name.toUpperCase()) {
                // if the tab item is selected value = 1 if it isn't selected the value doesn't exist and parseInt throws an exception
                case "LIVE":
                    try {
                        int liveVal = Integer.parseInt(liveButton.getAttribute("value"));
                        ret = liveVal == 1 ? true : false;
                    } catch (Exception e) {
                        ret = false;
                    }
                    break;
                case "RECORDINGS":
                    try {
                        int recordingsVal = Integer.parseInt(recordingsButton.getAttribute("value"));
                        ret = recordingsVal == 1 ? true : false;
                    } catch (Exception e) {
                        ret = false;
                    }
                    break;
                case "GUIDE":
                    try {
                        int guideVal = Integer.parseInt(guideButton.getAttribute("value"));
                        ret = guideVal == 1 ? true : false;
                    } catch (Exception e) {
                        ret = false;
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Invalid element: " + name);
            }
        }
        return ret;
    }

    /**
     * Retrieves the channel names list for the specified lineup.
     */
    public ArrayList<String> getChannelsNamesArrayForLineup(String lineup) {
        ArrayList<String> channelsList = new ArrayList<String>();
        if (Utils.isAndroid()) {
            switch (lineup.toUpperCase()) {
                case "AUTOTEST":
                    channelsList = autoTestLineup;
                    break;
                case "BAY_AREA":
                    channelsList = bayAreaLineup;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid element: " + lineup);
            }

        } else {
            switch (lineup.toUpperCase()) {
                case "AUTOTEST":
                    channelsList = autoTestLineup;
                    break;
                case "BAY_AREA":
                    channelsList = bayAreaLineup;
                    break;

                default:
                    throw new IllegalArgumentException("Invalid element: " + lineup);
            }
        }
        return channelsList;
    }

    public String getChannel(String lineup, int index) {
        String channel = "";
        if (lineup.equalsIgnoreCase("AUTOTEST")) {
            channel = autoTestLineup.get(index);
        } else {
            channel = bayAreaLineup.get(index);
        }
        return channel;
    }

    /**
     * Method checks if the specified UI element is currently displayed on the screen.
     */

    public boolean isShown(String name) {
        MobileElement el = null;
        switch (name.toUpperCase()) {
            case "LIVE":
                el = liveButton;
                break;
            case "GUIDE":
                el = guideButton;
                break;
            case "RECORDINGS":
                el = recordingsButton;
                break;
            case "COMMUNITY":
                el = communityButton;
                break;
            case "PLAY/PAUSE":
                el = viewer.getPlayPauseOverlay();
                break;
            case "LAST":
                el = lastButton;
                break;
            case "AIRPLAY":
                if (Utils.isAndroid()) {
                } else {
                    el = airPlayButton;
                }
                break;
            case "SETTINGS":
                el = topDropDownMenuButton;
                break;
            case "30 SEC FORWARD":
                el = viewer.get30SecForwardOverlay();
                break;
            case "30 SEC BACK":
                el = viewer.get30SecBackOverlay();
                break;
            case "CC":
                el = Utils.findElement("player_ccButton");
                break;
            case "SHOW TITLE":
                el = viewer.getCurrentVideoNameOverlay();
                break;
            case "PLAYER SLIDER":
                el = viewer.getPlayerSlider();
                break;
            case "PLAYER MUTE BUTTON":
                el = viewer.getButtonMute();
                break;
            case "CHANNEL NUMBER":
                el = viewer.getChannelNumber();
                break;
            case "CHEVRON RIGHT":
                el = viewer.getChevronRight();
                break;
            case "FULL SCREEN VIEW":
                el = fullScreenViewButton;
                break;
            case "UP":
                el = upButton;
                break;
            default:
                throw new IllegalArgumentException("Invalid element on CommonComponentsPage: " + name);
        }
        return el.isDisplayed();
    }

    /**
     * Simulates a tap anywhere on the app screen.
     */
    public void tapScreen() {
        Utils.tapAppScreen();
    }

    /**
     * Simulates clicking the Play/Pause button in the video viewer.
     */
    public void playPauseButtonClick() {
        viewer.PlayPauseVideoClick("Play/Pause");
    }

    public void nextChannelLiveTabButtonClick() {
        viewer.PlayPauseVideoClick("Play/Pause");
    }

    /**
     * Navigates to the next item (channel) by swiping right on the screen.
     */
    public void goToNext() {
        MobileElement el = viewer.getChevronRight();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("direction", "right");
        params.put("velocity", 2500);
        params.put("element", ((RemoteWebElement) el).getId());
        js.executeScript("mobile: swipe", params);
    }

    /**
     * Method provides a way to handle button clicks and navigate to different
     * pages within the application.
     */
    public BasePage buttonClick(String name) {
        BasePage bp = null;
        switch (name.toUpperCase()) {
            case "TOP DROP-DOWN MENU":
                topDropDownMenuButton.click();
                if (MyPages._topDropDownMenu != null) {
                    bp = MyPages._topDropDownMenu;
                } else {
                    bp = new TopMenu(driver);
                }
                break;
            case "FULL SCREEN VIEW":
                fullScreenViewButton.click();
                if (MyPages._fullScreenPage != null) {
                    bp = MyPages._fullScreenPage;
                } else {
                    bp = new FullScreenPage(driver);
                }
                break;
            case "LAST":
                try {
                    System.out.println("In try  last click..");
                    lastButton.click();
                } catch (Exception e) {
                    MobileElement lastButton = Utils.findElement("last");
                    lastButton.click();
                }
                break;

            default:
                throw new IllegalArgumentException("Guide Page click - Invalid element: " + name);
        }
        return bp;
    }

    public MobileElement getPlayPauseOverlay() {
        return viewer.getPlayPauseOverlay();
    }

    public MobileElement getLoadingVideoOverlay() {
        return viewer.getLoadingVideoOverlay();
    }

    public MobileElement get30SecBackOverlay() {
        return viewer.get30SecBackOverlay();
    }

    public MobileElement get30SecForwardOverlay() {
        return viewer.get30SecForwardOverlay();
    }

    public boolean verify30SecBackOverlayIsPresent() {
        return Utils.isElementPresent(get30SecBackOverlay(), 2);
    }

    public boolean verify30SecForwardOverlayIsPresent() {
        return Utils.isElementPresent(get30SecForwardOverlay(), 2);
    }

    public boolean verifyPlayPauseOverlayIsAbsent(int timeout) {
        return Utils.isElementAbsent(getPlayPauseOverlay(), timeout);
    }

    public boolean verifyLoadingVideoOverlayIsAbsent(int timeout) {
        String id = "";
        if (Utils.isAndroid()) {
            id = Utils.getId("loadingProgress_image");
        } else {
            id = Utils.getId("loadingProgress_image");
        }
        return Utils.isElementInVisible(id, timeout);
    }

    public boolean verifyPlayPauseOverlayIsPresent(int timeout) {
        return Utils.isElementPresent(getPlayPauseOverlay(), timeout);
    }

    public String getCurrentVideoNameFromViewer() {
        String ret = viewer.getCurrentVideoName();
        return ret;
    }


    public void acceptLocationService() throws Exception {
        if (Utils.isAndroid()) {
            permissionAllowButton.click();
        } else {
            // On iOS location services alert isn't shown after permission is accepted.
            // Don't fail the test if it is not found
            try {
                //for os> 14.0 "Allow location dialog" is not dismissed automatically
                String os = Utils.getCapability("platformVersion");
                String version = os.substring(0, os.indexOf("."));
                // System.out.println("os. version : " + version);
                if (version.equalsIgnoreCase("14")) {
                    permissionAllowButton.click();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * return true if conrol is enabled
     **/

    public boolean isEnabled(String name) {
        boolean enabled = false;
        switch (name.toUpperCase()) {
            case "ALLOW":
                String enabledString = permissionAllowButton.getAttribute("enabled");
                enabled = Boolean.valueOf(enabledString);
                break;
            default:
                throw new IllegalArgumentException("Invalid element: " + name);
        }
        return enabled;

    }

    public boolean seeLocationServiceDlg() throws Exception {
        return permissionAllowButton.isDisplayed();
    }

    public void tapOkOnPleaseAllowAccessToLocation() {
        if (Utils.isAndroid()) {
            pleaseAllowAccessLocationOkButton.click();
        } else {

        }
    }

    public void tapDontAskAgain() {
        if (Utils.isAndroid()) {
            dontaskCheckbox.click();
        } else {

        }
    }

    /**
     * Retrieves the text from the permission allow dialog.
     */
    public String getAllowPermissionText() {
        return permissionAllowText.getText();
    }

    /**
     * Retrieves the message text requesting location access permission.
     */
    public String getAllowLocationAccessText() {
        return pleaseAllowAccessLocationMessage.getText();
    }

    /**
     * Retrieves the iOS-specific message explaining why location access is needed.
     */
    public String getAllowLocationAccessTextForIos() {     ///"PhoenixBTV needs to know where you are so we can provide you .."
        return PhoenixBtvNeedsToKnowMessage.getText();
    }

    public void denyLocationService() {
        if (Utils.isAndroid()) {
            permissionDenyButton.click();
        } else {
            permissionDenyButton.click();
        }
    }

    /**
     * android -- Update Google Play Services
     **/
    public void allowAppPermission() {
        boolean b = Utils.isElementAbsent(googleUpdate, 2);
        if (!b) {
            Utils.findElement("googlePlayServices_UpdateButton").click();
            if (googlePlayServicesNotUpdated) {
                MobileElement container = Utils.findElementNoPrefix("buttonContainer");
                container.findElement(By.className(xConsts.ANDROID_CLASSNAMES.get("androidWidgetButton"))).click();
                googlePlayServicesNotUpdated = true;
            }
            driver.navigate().back();
        }
    }


    /**
     * Tap device status bar at 1/2 width to resolve Appium 1.7.2 bug for Android - it doesn't recognize elements after
     * System location message
     **/
    public void clickOutsideAllowLocationDialog() {
        if (Utils.isAndroid()) {
            Dimension size = driver.manage().window().getSize();
            int startx = (int) (size.width * 0.5);
            new TouchAction(driver).tap(startx, xConsts.STATUSBAR_Y).perform();
        }
    }

    /**
     * Stores the top-left Y-coordinate of the Guide/Recordings Tab and viewer height for Android.
     * Android uses the same ID for both Guide and Recordings tabs.
     */

    public void storeTabCoordinates() {
        if (Utils.isAndroid()) {
            tabY = tab.getLocation().getY();  //Guide or Recordings tab
            viewerHeight = viewerElement.getSize().getHeight(); //810
        }
    }

    /**
     * Stores the top-left Y-coordinate of the Guide/Recordings Tab and viewer height for iOS.
     */

    public void storeTabCoordinatesForiOS(String page) {
        if (page.equalsIgnoreCase("Recordings")) {
            tabY = tab.getLocation().getY();
        } else {
            tabY = guide.getLocation().getY();
        }
        viewerHeight = viewerElement.getSize().getHeight();
    }


    /**
     * Stores the Y-coordinate of the Guide/Recordings Tab after it has been expanded (Android only).
     */

    public void storeExtendedTabCoordinates() {
        if (Utils.isAndroid()) {
            extendedTabY = tab.getLocation().getY();
        }
    }


    /**
     * Stores the Y-coordinate of the Guide/Recordings Tab after expansion for iOS.
     */

    public void storeExtendedTabCoordinatesForiOS(String page) {
        if (page.equalsIgnoreCase("Recordings")) {  //Extended Tab y  : 82
            extendedTabY = tab.getLocation().getY();
        } else {
            MobileElement extGuideTab = Utils.findElement("guide");
            extendedTabY = extGuideTab.getLocation().getY();
        }
    }

    /**
     * Method expands or collapses the Guide/Recordings Tab based on the specified direction.
     */

    public void extendOrCollapseTab(String name) {
        MobileElement el = null;
        switch (name.toUpperCase()) {
            case "UP":
                if (Utils.isAndroid()) {
                    upButton.click();
                } else {
                    this.clickUpOrDownButtonForIos(upButton);
                }
                break;
            case "DOWN":
                if (Utils.isAndroid()) {
                    downButton.click();
                } else {
                    this.clickUpOrDownButtonForIos(upButton);
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid element: " + name);
        }
    }

    /**
     * Method get y coordinate for Guide, Recordings or Community tab
     **/
    public int getYCoordinate(String tabValue) {
        int retValue = 0;
        if (tabValue.equalsIgnoreCase("Community") || tabValue.equalsIgnoreCase("Guide")) {
            retValue = guide.getLocation().getY();
        } else if (tabValue.equalsIgnoreCase("Recordings")) {
            retValue = tab.getLocation().getY();

        }
        return retValue;
    }

    /**
     * Returns the stored Y-coordinate of the tab.
     */
    public int getTabYCoordinate() {
        return tabY;
    }

    /**
     * Returns the stored Y-coordinate of the expanded tab.
     */
    public int getExtendedTabYCoordinate() {
        return extendedTabY;
    }

    /**
     * Returns the height of the video viewer element.
     */
    public int getViewerHeight() {
        return viewerElement.getSize().getHeight();
    }


    /**
     * Finds and taps the center coordinates of the "grab_bar_up" or "grab_bar_down" button on iOS.
     */
    public void clickUpOrDownButtonForIos(MobileElement el) {
        int graby = el.getLocation().getY();
        int grabx = el.getLocation().getX();
        int grabHeight = el.getSize().getHeight();
        int grabLength = el.getSize().getWidth();

        int yloc = graby + grabHeight / 2;
        int xloc = grabx + grabLength / 2;

        TouchAction touchAction = new TouchAction(driver);
        touchAction.tap(xloc, yloc).perform();  //x=203, y=306
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }


}

