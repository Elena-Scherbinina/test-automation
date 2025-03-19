package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import stepdefs.Utils;
import stepdefs.xConsts;

import java.util.concurrent.TimeUnit;

/**
 * LivePage represents the Live screen of the mobile TV app.
 * It provides methods to interact with live content, switch channels,
 * verify UI elements, and retrieve current channel information.
 * Supports both Android and iOS platforms.
 */

public class LivePage extends CommonComponentsPage{

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/live_pager")
    private MobileElement liveTabContainer;     //live tab controls container

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/image_station_next")
    @iOSFindBy(accessibility="airing_options_nextButton")
    private MobileElement nextChannelChevron;

    @AndroidFindBy(id = "com.example.tvapp:id/image_station_previous")
    @iOSFindBy(accessibility="airing_options_previousButton")
    private MobileElement previousChannelChevron;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/image_promo_link")
    @iOSFindBy(accessibility="airing_options_promolinkImageView")
    private MobileElement promoLink;

    @AndroidFindBy(id = "com.android.chrome:id/url_bar")
    private MobileElement urlBar;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/text_attributes")
    @iOSFindBy(accessibility="airing_options_attributesLabel")
    private MobileElement showAttributes;

    @AndroidFindBy(id = "com.example.tvapp:id/image_station_logo")
    @iOSFindBy(accessibility="airing_options_stationImage2")
    private MobileElement bannerChannelLogo;


    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/button_watch")
    @iOSFindBy(accessibility="airing_options_watchButton")
    private MobileElement watchButton;

    @AndroidFindBy(id = "com.example.tvapp:id/button_record_episode")
    @iOSFindBy(accessibility="airing_options_recordEpisodeButton")
    private MobileElement recordEpisodeButton;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/button_record")
    @iOSFindBy(accessibility = "airing_options_recordOptionsButton")
    private MobileElement recordButton;


    @AndroidFindBy(id = "com.example.tvapp:id/text_title")
    @iOSFindBy(accessibility = "airing_options_titleLabel")
    private MobileElement showName;                        //Live tab show name

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @iOSFindBy(accessibility = "airing_options_warningLabel")
    private MobileElement blackoutText;

    //value of Record option button on Live Tab, true if selected
    private boolean isRecordButtonSelected = false;
    //store Live tab banner show name
    String showNameText = "";
    private CurrentVideoPage viewer;
    private String channelNumber1 = "";
    private String channelNumber2 = "";
    private String channelNumber3 = "";


    public LivePage(AppiumDriver driver){
        super(driver);
    }

    /**
     * Method returns the mobile element representing the "next channel" chevron on the Live tab
     */
    public MobileElement getNextChannelChevron(){  //Live tab
        MobileElement rightChevron = null;
        if (Utils.isAndroid()){ // android has 2 "com.example.tvapp:id/image_station_next" ids
            MobileElement cont = Utils.findElement("liveTab_layoutTabContainer");
            rightChevron = cont.findElement(By.id("com.example.tvapp:id/image_station_next"));
        }else{
            rightChevron = nextChannelChevron;  //ios
        }
        return rightChevron;
    }

    /**
     * Method returns the  "previous channel" chevron on the Live tab
     */
    public MobileElement getPreviousChannelChevron(){  //Live tab
        MobileElement leftChevron = null;
        if (Utils.isAndroid()){ // android has 2 "com.example.tvapp:id/image_station_next" ids
            MobileElement cont = Utils.findElement("liveTab_layoutTabContainer");
            leftChevron = cont.findElement(By.id("com.example.tvapp:id/image_station_previous"));
        }else{
            leftChevron = previousChannelChevron;  /
        }
        return leftChevron;
    }


    /**
     *   Method returns the current channel number from the Live tab attributes,
     *   handling differences between ios and android
     **/
    public String getChannelNumber(){
        String channelNumber = "";
        MobileElement el = null;
        if (Utils.isAndroid()){
            el = Utils.findElement("player_attributes");
            System.out.println("attributes : " + el.getText());
            String arr[] = (el.getText()).split(xConsts.CHANNEL_NUMBER__REGEX);  // two non-breaking space Unicode char.
            if (arr.length > 1) {
                channelNumber = arr[0];     //"5.1. CBS5"
            }
        }else {
            el = (MobileElement) Utils.findElementByXPath("liveTab_attributes");
            System.out.println(el.getText());
            String arr[] = (el.getText()).split(" / ");
            if (arr.length > 1) {
                channelNumber = arr[0];
            }
        }
        return channelNumber.trim();
    }

    /**
     * Method clicks the "next channel" chevron to navigate to the next channel
     */
    public void navigateToNextChannel(){
        nextChannelChevron.click();
    }

    /**
     * Method clicks the "previous channel" chevron to navigate to the previous channel.
     */
    public void navigateToPreviousChannel(){
        previousChannelChevron.click();
    }

    /**
     * Method checks if the play/pause overlay is absent within a given timeout period.
     */
    public boolean verifyPlayPauseOverlayIsAbsent(int timeout) {
        return Utils.isElementAbsent(getPlayPauseOverlay(), timeout);
    }

    /**
     * Method stores the currently displayed show name on the live tab.
     */
    public void setShowName() {
        showNameText = showName.getText();
    }

    /**
     * Method returns the stored show name
     */
    public String getShowName() {
        return showNameText;
    }

    /**
      Method remembers the first watched channel number for verification of the "Last"
      button functionality.
    */
    public void rememberFirstChannelNumber() {
        this.channelNumber1 = this.getChannelNumber();
        System.out.println(" remember " + this.channelNumber1);
    }

    /**
     * Method returns remembered channel number
     */
    public String getFirstRememberedChannelNumber(){
        return this.channelNumber1;
    }

    public String getNextRememberedChannelNumber(){
        return this.channelNumber2;
    }

    /**
     * Method remembers the next watched channel number.
     */
    public void rememberNextChannelNumber() {
        this.channelNumber2 = this.getChannelNumber();
    }

    /**
     * Method retrieves a stored channel number based on the given index (1, 2, or 3)
     */
    public String geRememberedChannelNumber(int number) {
        String value = "";
        if (number == 1) {
            value =  this.channelNumber1;
        } else if (number == 2) {
            value =  this.channelNumber2;
        } else if (number == 3) {
            value =  this.channelNumber3;
        }
        return value;
    }

    /**
     * Method stores a channel number based on the given index
     */
    public void rememberChannel(int number) {
        if (number == 3) {
            this.channelNumber3 = this.getChannelNumber();
        }else if (number == 2){
            this.channelNumber2 = this.getChannelNumber();
        }
    }

    /**
     * Method determines if a specified UI element is visible
     */
    public boolean isVisible(String name) {
        MobileElement el = null;
        switch (name.toUpperCase()) {
            case "WATCH LIVE":
                el = watchButton;
                break;
            case "RECORD":
                el = recordButton;
                break;
            case "NEXT CHANNEL":
                el = nextChannelChevron;
                break;
            case "PREVIOUS CHANNEL":
                el = previousChannelChevron;
                break;
            case "SHOW NAME":
                if (Utils.isAndroid()) {
                    el = showName;
                }else {
                    Utils.isElementVisible(xConsts.APPLE_IDS.get("liveTab_showTitle"), 6);
                    el = Utils.findElement("liveTab_showTitle");
                }
                break;
            case "CHANNEL LOGO":
                el = bannerChannelLogo;
                break;
            case "RECORD_DIALOG":
                el = recordEpisodeButton;
                break;
            case "RIGHT CHEVRON":
                el = viewer.getChevronRight();
                break;
            case "ATTRIBUTES":
                el = showAttributes;
                break;
            default:
                throw new IllegalArgumentException("Unknown UI element: " + name);
        }
        return el.isDisplayed();
    }

    /**
     * Method navigates to the last watched channel in the viewer
     */
    public void navigateRightInTheViewer(){
        try {
            viewer = this.getCurrentViewer();
            viewer.navigateToLastChannel();
        }catch(Exception e){
            System.out.println(e.getMessage());

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Method simulates tapping the right chevron in the viewer.
     */
    public void tapRightChevronInTheViewer(){
        try {
            viewer = this.getCurrentViewer();
            viewer.tapRightChevronInTheViewer();
        }catch(Exception e){
            System.out.println(e.getMessage());

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Method simulates tapping the left chevron in the viewer
     */
    public void tapLeftChevronInTheViewer(){
        try {
            viewer = this.getCurrentViewer();
            viewer.tapLeftChevronInTheViewer();
        }catch(Exception e){
            System.out.println(e.getMessage());

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Method navigates to the first watched channel in the viewer
     */
    public void navigateLeftInTheViewer(){
        try {
            viewer = this.getCurrentViewer();
            viewer.navigateToFirstChannel();
        }catch(Exception e){
            System.out.println(e.getMessage());

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    public boolean isSelected(String name) {
        MobileElement el = null;
        switch (name) {
            case xConsts.RECORD_EPISODE_CHECKBOX:
                el = recordEpisodeButton;
                break;
            case xConsts.RECORDING:
                el = recordButton;
                break;

            default:
                throw new IllegalArgumentException("Unsupported or unrecognized UI element: " + name);
        }
        return el.isSelected();
    }

    /**
     * Clicks the "Watch" button to start playing the selected content.
     */
    public void watchButtonClick() {
        watchButton.click();
    }

    public void recordBarButtonClick() {
        recordButton.click();
    }

    public void saveRecordButtonSelectionState(){
        isRecordButtonSelected = recordButton.isSelected();
    }

    public boolean getRecordButtonSavedState(){
        return isRecordButtonSelected;
    }

    public void promoLinkClick() {
        promoLink.click();
    }

    public void promoLinkIsShown() {
       promoLink.isDisplayed();
    }

    public String getPromotionalLinkURL() {
        return urlBar.getText();
    }


    public void showRecordOptions() {
        recordButton.click();
    }


    /**
     * Clicks on the specified record option button.
     */
    public void recordOptionsClick(String name) {
        MobileElement button;

        switch (name) {
            case xConsts.RECORD_EPISODE_CHECKBOX:
                button = recordEpisodeButton;
                break;
            case xConsts.WATCHING:
                button = watchButton;
                break;
            default:
                throw new IllegalArgumentException("Live Tab: Invalid element - " + name);
        }

        button.click();
    }


    /**
     * Retrieves the show title displayed on the Live Tab.
     */
    public String getLiveTabShowTitle() throws Exception {
        return showName.getText();
    }



    public String getLiveTabShowTAttributes() throws Exception {
        return this.showAttributes.getText();
    }

    public String getBlackoutText(){
        return blackoutText.getText();
    }

}
