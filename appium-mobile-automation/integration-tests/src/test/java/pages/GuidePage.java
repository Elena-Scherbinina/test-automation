package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import stepdefs.Utils;
import stepdefs.xConsts;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * GuidePage represents the Guide screen in the mobile TV app.
 * It provides methods to interact with the channel guide, fetch channel details,
 * and perform actions like tapping on guide cells or retrieving show information.
 * This class supports both Android and iOS platforms.
 */

public class GuidePage extends CommonComponentsPage {
    public static String defaultGuideShowName = "";
    private String channelNumber1 = "";

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.android.vending:id/progress_bar")
    private MobileElement googleProgressBar;


    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.google.android.gms:id/message")  //turn on location msg
    private MobileElement locationMessage;

    @AndroidFindBy(id = "com.google.android.googlequicksearchbox:id/icon")
    private MobileElement app;

    /*   Choose browser to open promotion link */
    /*  Google Pixel */
    @AndroidFindBy(id = "android:id/title")
    private MobileElement openWith;

    @AndroidFindBy(id = "android:id/resolver_list")
    private MobileElement browsersList;

    @AndroidFindBy(id = "com.example.tvapp:id/recycler_view_station")
    @iOSFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"station_cell_label\"])[1]")
    private MobileElement channelsList;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/view_guide")
    @iOSFindBy(accessibility = "guide_tab")
    private MobileElement guide;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @iOSFindBy(xpath = "//XCUIElementTypeOther[@name=\"guide_CollectionView\"]")     //  //XCUIElementTypeOther[@name=\"guide_CollectionView\"]"
    private MobileElement guideCollection;

    @AndroidFindBy(accessibility = "Apps")
    private MobileElement appsButton;

    @AndroidFindBy(id = "com.example.tvapp:id/text_channel_label")
    @iOSFindBy(accessibility = "recording_overlay_recordedLabel")
    private MobileElement labelRecorded;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @iOSFindBy(accessibility = "guide_timeLabel")
    private MobileElement dateTimeButton;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @iOSFindBy(accessibility = "player_warningLabel")
    private MobileElement blackoutText;


    ArrayList<String> btvAutoTestLineup = new ArrayList<String>();
    ArrayList<String> bayAreaLineup = new ArrayList<String>();

    boolean googlePlayServicesNotUpdated = true;

    public GuidePage(AppiumDriver driver) {
        super(driver);
    }


    public boolean findChannel(String channel) {
        boolean ret = false;
        if (btvAutoTestLineup.contains(channel)) {
            ret = true;
        }
        return ret;
    }


    public boolean isShownTodayButton() {
        boolean isShown = false;
        isShown = dateTimeButton.isDisplayed();
        return isShown;
    }


    /**
     * Retrieves the name of the first visible channel from the Guide.
     *
     * This method checks the platform (Android or iOS) and returns the name of the first channel in the Guide.
     * - For Android, it locates the channel name by finding the first row and accessing the channel name element.
     * - For iOS, it retrieves the channel name from the collection view by locating the first item in the list.
     */
    public String getFirstVisibleChannelFromTheGuide() {
        if (Utils.isAndroid()) {
            List<MobileElement> rows = Utils.findElements("guideStationName");
           MobileElement channel = (MobileElement) rows.get(0).findElement(By.id(Utils.getId("channel_name")));
            String txt = channel.getText();

            return txt;
        } else {
            MobileElement el = Utils.findElementByXPath("guideCollection");
            MobileElement channelsList = el.findElement(By.className(Utils.getId("collectionView")));
            List<MobileElement> channelElements = channelsList.findElements(By.name(Utils.getId("custChannels_stationName")));
            String val = channelElements.get(0).getAttribute("value");
            return val;
        }
    }

    /**
     * Taps on the first visible channel in the Guide on iOS.
     *
     * This method locates the collection view of channels within the Guide, retrieves the list of channel elements,
     * and taps on the first channel in the list. It then logs the text of the tapped channel.
     */
    public void tapFirstIosVisibleChannelFromTheGuide() {
        MobileElement channelsList = guideCollection.findElement(By.className(Utils.getId("collectionView")));
        List<MobileElement> lChannels = channelsList.findElements(By.xpath(xConsts.APPLE_XPATHS.get("stationName")));

        lChannels.get(0).click();
        System.out.println("First cell click : " + lChannels.get(0).getText());
    }


    /**
     * Clicks on a guide cell by its index in the provided list of MobileElements.
     *
     * This method clicks the guide cell at the specified index from the list of MobileElements and returns
     * an instance of the `InfoDialog` page. It is assumed that the list is not empty, and the index is valid.
     */
    public BasePage clickGuideCellByIndex(List<MobileElement> l, int i) {
        BasePage bp = null;
        l.get(i).click();
        bp = new InfoDialog(driver);
        return bp;
    }

    public String getShowNameFromGuideByIndex(List<MobileElement> l, int i) {
        String text = l.get(i).getText();
        System.out.println("Guide show name : " + text);
        return text;
    }


    /**
     * Retrieves the video name (show name) from the guide by index.
     *
     * This method retrieves the show name from the guide list by calling the `getShowNameFromGuideByIndex`
     * method from the `GuidePage` instance. It uses the provided index to locate the specific show in the guide list.
     */
    public String getGuideVideoName(int index) {
        List<MobileElement> lGuide = MyPages._guidePage.getTvGuideList();
        return MyPages._guidePage.getShowNameFromGuideByIndex(lGuide, index);
    }


    /**
     * Retrieves a MobileElement from the list based on matching text.
     *
     * This method iterates through a list of `MobileElement` objects and compares their text content with
     * the provided value. If a match is found, the corresponding `MobileElement` is returned.
     */
    public MobileElement getElementFromListByText(List<MobileElement> list, String value) {
        MobileElement returnedElement = null;
        for (MobileElement e : list) {
            String innerText = e.getText();
            if (innerText == value) {
                returnedElement = e;
            }
        }
        return returnedElement;
    }

    public boolean isChannelFound(List<MobileElement> list, String channelName) {
        MobileElement returnedElement = null;
        boolean found = false;
        for (MobileElement e : list) {
            String innerText = e.getAttribute("value");
            if (innerText.equalsIgnoreCase(channelName)) {
                found = true;
            }
        }
        return found;
    }


    /**
     * Sets the default show name from the Guide to `CurrentVideoPage.defaultGuideShowName`.
     *
     * This method retrieves the show name from the Guide at index 0 in the collection
     * and stores it in the `defaultGuideShowName` field.
     * It uses the `getTvGuideList()` method to obtain the list of guide elements and
     * `getShowNameFromGuideByIndex()` to get the show name corresponding to index 0.
     */

    public void setDefaultGuideShowName() throws Exception {
        List<MobileElement> lGuide = MyPages._guidePage.getTvGuideList();
        String currentVideoNameFromGuide = MyPages._guidePage.getShowNameFromGuideByIndex(lGuide, 0);
        this.defaultGuideShowName = currentVideoNameFromGuide;
    }

    /**
     * Returns the default show name from the Guide.
     *
     * This method retrieves the value of the `defaultGuideShowName` field, which is
     * previously set using the `setDefaultGuideShowName()` method.
     * It provides access to the default show name from the Guide for further use.
     */
    public String getDefaultGuideShowName() {
        return this.defaultGuideShowName;
    }

    public List<MobileElement> getTvGuideList() {
        List<MobileElement> lGuide = null;

        if (Utils.isAndroid()) {
            lGuide = guide.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("textview")));
        } else {
            lGuide = new ArrayList<MobileElement>();

            MobileElement channelsList = guideCollection.findElement(By.className(xConsts.APPLE_CLASSNAMES.get("collectionView")));
            //XCUIElementTypeCell
            lGuide = channelsList.findElements(By.name(Utils.getId("guide_cellLabel")));
            String text = lGuide.get(0).getText();
            System.out.println(" show name = " + text);
        }
        return lGuide;
    }

    /**
     * Retrieves a list of Guide elements.
     *
     * This method returns a list of `MobileElement` objects representing the TV Guide items.
     * The elements are retrieved based on the platform (Android or iOS). On Android,
     * it finds elements using a class name, while on iOS, it finds elements by name
     * within a collection view.
     */
    public void clickFirstGuideCellIos() {
        List<MobileElement> lGuide = null;
        lGuide = new ArrayList<MobileElement>();

        MobileElement channelsList = guideCollection.findElement(By.className(Utils.getId("collectionView")));
        List<MobileElement> elements = channelsList.findElementsByName("guide_cell_title");
        elements.get(0).click();
    }

    /**
     * Taps a specific cell in the iOS Guide based on its index.
     *
     * This method locates and taps a cell in the Guide for iOS, where each cell represents a show.
     * It checks if the cell at the provided index is visible and then clicks it.
     */
    public void clickCellInTheGuideIos(int i) {
        int j = 0;
        if (i >= 0) {
            MobileElement channelsList = Utils.findElement("guide_CollectionView");
            String select = "name == 'guide_cell_title' AND visible == 1";  //cells with show names
            List<MobileElement> els = channelsList.findElements(MobileBy.iOSNsPredicateString(select));
            for (MobileElement e : els) {
                MobileElement elTypeOther = e.findElement(By.className("XCUIElementTypeOther"));
                MobileElement elLabel = elTypeOther.findElement(By.className("XCUIElementTypeStaticText"));
                if (elLabel != null) {
                    System.out.println(elLabel.getText());
                    int x = elLabel.getLocation().getX();
                    String visibleAsString = elLabel.getAttribute("visible");
                    boolean visible = Boolean.valueOf(visibleAsString);
                    if ((visible) & (j == i)){
                      elLabel.click();
                      break;
                    }
                    if (visible) j++;
                }
            }
        }
    }

    /**
     * Taps on a specific channel name in the guide grid to load its live playback into the viewer.
     *
     * This method searches for a channel by name within the guide grid and taps on the channel to
     * initiate the live playback in the viewer. It compares the `channelName` provided with the
     * names of the channels in the grid and clicks the one that matches.
     */
    public void loadChannelIntoViewer(String channelName) {
        MobileElement el = Utils.findElementByXPath("guideCollection");
        MobileElement channelsList = el.findElement(By.className(Utils.getId("collectionView")));

        List<MobileElement> elements = channelsList.findElementsByName(Utils.getId("custChannels_stationName"));

        for (MobileElement e : elements) {
            String channel = e.getAttribute("value").toString();
            if (channel.equalsIgnoreCase(channelName)) {
                e.click();
                break;
            }
        }
    }

    /**
     * Taps on the cell in the guide grid to load the Info Dialog for the specified channel.
     *
     * This method searches for the specified channel in the guide grid and clicks the corresponding
     * cell to load its Info Dialog. For iOS, it handles the case where the first cell could have an
     * index of either 0 or 1. The method checks that the channel is visible before clicking.
     */

    public void tapGuideCellForChannel(String channelName) {
        System.out.println("In tapGuideCellForChannel ..");
        int i = 0;
        MobileElement el = Utils.findElementByXPath("guideCollection");
        MobileElement channelsList = el.findElement(By.className(Utils.getId("collectionView")));

        List<MobileElement> elements = channelsList.findElementsByName(Utils.getId("custChannels_stationName"));
        System.out.println(elements.get(0).getAttribute("value").toString());
        System.out.println(elements.get(1).getAttribute("value").toString());

        for (MobileElement e : elements) {
            String channel = e.getAttribute("value").toString();
            String visibleAsString = e.getAttribute("visible"); //added
            boolean visible = Boolean.valueOf(visibleAsString);//added
            if (channel.equalsIgnoreCase(channelName) && visible) {
                System.out.println("tap channel : " + channel + "  i = " + i);
                  e.click();
                break;
            }
            i++;
        }
        this.clickCellInTheGuideIos(i);
    }


    /**
     * Stores the channel number of the first visible channel in the guide for later verification.
     *
     * This method retrieves the name of the first visible channel from the guide and stores it in the
     * `channelNumber1` variable. It is typically used for verifying the correct channel in subsequent
     * interactions, such as when checking the functionality of the last button.
     */
    public void rememberFirstChannelNumber() {
        String ch = getFirstVisibleChannelFromTheGuide();
        this.channelNumber1 = ch;
    }

    public String getFirstChannelNumber() {
        return this.channelNumber1;
    }

    public String getRecordingLabel() {
        if (labelRecorded.isDisplayed()) {
            return labelRecorded.getText();
        }
        return "";
    }

    public void dismissAdminMsg() {
        MobileElement el = Utils.findElement("adminMessage_button");
        el.click();
    }

    public String getBlackoutText() {
        return blackoutText.getText();
    }

    /**
     * Retrieves the caption (label) of the "Today" button.
     *
     * This method fetches the label attribute of the "Today" button element and returns it as a string.
     * It is typically used to verify the text or caption displayed on the "Today" button in the UI.
     */
    public String getTodayButtonCaption() {
        String label = dateTimeButton.getAttribute("label");
        return label;
    }


}

