package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.WithTimeout;
import org.openqa.selenium.By;
import org.testng.Assert;
import stepdefs.Utils;
import stepdefs.xConsts;

import java.util.concurrent.TimeUnit;

public class CurrentVideoPage extends BasePage {

    @AndroidFindBy(id = "com.example.tvapp:id/text_show_name")
    @iOSFindBy(accessibility = "live_overlay_wholeBannerShowTitle")
    private MobileElement currentVideoName;

    @WithTimeout(time = 200, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/text_show_name")
    @iOSFindBy(accessibility = "player_bannerView")
    private MobileElement playingRecordingVideoName;


    @iOSFindBy(accessibility = "recording_overlay_recordedLabel")
    private MobileElement playingRecordingRecordedLabel;

    @WithTimeout(time = 200, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/progress")
    private MobileElement progressOverlay;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/progress")
    @iOSFindBy(accessibility = "player_activityIndicator")  //current
    private MobileElement loadingVideoOverlay;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/image_play_pause")
    @iOSFindBy(accessibility = "player_pausePlayButton")  //player_pausePlayButton
    private MobileElement playPauseOverlay;


    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/image_skip_back")
    @iOSFindBy(accessibility = "player_seekBackButton")
    private MobileElement play30SecBackOverlay;


    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/image_skip_forward")
    @iOSFindBy(accessibility = "player_seekForwardButton")
    private MobileElement play30SecForwardOverlay;

    @AndroidFindBy(id = "com.example.tvapp:id/seekbar_scrubber_portrait")
    @iOSFindBy(accessibility = "player_slider")
    private MobileElement playerSlider;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS) //check
    @AndroidFindBy(id = "com.example.tvapp:id/image_cc_play")
    @iOSFindBy(xpath = "//XCUIElementTypeButton[@name=\"player_ccButton\"]")
    private MobileElement ccBbutton;

    @AndroidFindBy(id = "com.example.tvapp:id/image_mute_play")
    @iOSFindBy(accessibility = "player_muteButton")
    private MobileElement muteButton;

    @AndroidFindBy(id = "com.example.tvapp:id/text_scrubber_time")
    @iOSFindBy(accessibility = "slider_timeLabel")
    private MobileElement timeLabelButton;

    @AndroidFindBy(id = "com.example.tvapp:id/text_channel_number")
    private MobileElement channelNumber;

    @AndroidFindBy(id = "com.example.tvapp:id/layout_station_next")  // Player next/previous chevron
    @iOSFindBy(xpath = "//XCUIElementTypeImage[@name=\"player_nextIcon\"]")
    private MobileElement chevronRight;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS) //check or remove
    @AndroidFindBy(id = "com.example.tvapp:id/layout_station_previous")
    @iOSFindBy(xpath = "//XCUIElementTypeImage[@name=\"player_previousIcon\"]")
    private MobileElement chevronLeft;

    @iOSFindBy(accessibility = "player_warningLabel")    //Channel off the air ...
    private MobileElement warningLabel;


    public CurrentVideoPage(AppiumDriver driver) {
        super(driver);
    }

    public String getCurrentVideoName() {
        return currentVideoName.getText();
    }

    /**
     *  Method supports navigation in the viewer using chevron
     * */
    public void navigateToLastChannel() throws Throwable {
        MobileElement elNextChevron = null;
        String channelNumber = MyPages._livePage.getChannelNumber();
        String lastChannel = xConsts.AUTOTEST_LAST_CHANNEL;
        while (!channelNumber.equals(lastChannel)) {
            if (Utils.isAndroid()) {
                elNextChevron = Utils.findElement("liveTab_nextChevron");
            } else {
                elNextChevron = Utils.findElement("player_nextChevron");  //"player_nextIcon")
            }
            Utils.tapElement(elNextChevron);
            Utils.tapAppScreen();
            channelNumber = MyPages._livePage.getChannelNumber();

        }
        Assert.assertEquals(channelNumber, lastChannel);
    }

    /**
     *  Method supports navigation in the viewer using chevron
     **/
    public void navigateToFirstChannel() throws Throwable {
        MobileElement elNextChevron = null;
        String channelNumber = MyPages._livePage.getChannelNumber();
        String firstChannel = xConsts.AUTOTEST_FIRST_CHANNEL;
        while (!channelNumber.equals(firstChannel)) {
            if (!Utils.isAndroid()) {
                elNextChevron = Utils.findElement("player_previousChevron"); // id : "player_previousIcon"
            }
            Utils.tapElement(elNextChevron);
            Utils.tapAppScreen();
            channelNumber = MyPages._livePage.getChannelNumber();

        }
        Assert.assertEquals(channelNumber, firstChannel);
    }


    /**
     *  Method supports navigation to the right in the viewer using chevron  button
     * */
    public void tapRightChevronInTheViewer() throws Throwable {
        MobileElement elNextChevron = null;
        String channelNumber = MyPages._livePage.getChannelNumber();
        if (!Utils.isAndroid()) {
            elNextChevron = Utils.findElement("liveTab_nextChevron");
        } else {
            elNextChevron = Utils.findElement("player_nextChevron");
        }
        Utils.tapElement(elNextChevron);
        Utils.tapAppScreen();
        channelNumber = MyPages._livePage.getChannelNumber();
    }

    /**
     *  Method supports navigation to the left in the viewer using chevron button
     * */
    public void tapLeftChevronInTheViewer() throws Throwable {
        MobileElement elPreviousChevron = null;
        String channelNumber = MyPages._livePage.getChannelNumber();
        if (Utils.isAndroid()) {
            elPreviousChevron = Utils.findElement("player_previousChevron");
        } else {
            elPreviousChevron = Utils.findElement("player_previousChevron");
        }
        Utils.tapElement(elPreviousChevron);
        Utils.tapAppScreen();
        channelNumber = MyPages._livePage.getChannelNumber();
    }


    public MobileElement getButtonCC() {
        return ccBbutton;
    }

    public MobileElement getPlayerSlider() {
        return playerSlider;
    }


    public MobileElement getButtonMute() {
        return muteButton;
    }

    public MobileElement getChannelNumber() {
        return channelNumber;
    }

    public MobileElement getButtonTimeLabel() {
        return timeLabelButton;
    }

    public MobileElement getChevronRight() {
        return chevronRight;
    }

    public MobileElement getChevronLeft() {
        return chevronLeft;
    }

    public String getPlayingRecordingNameFromViewer() {
        return playingRecordingVideoName.getText();
    }

    public String getPlayingRecordingRecordedLabel() {
        return playingRecordingRecordedLabel.getText();
    }


    public void tapAppScreen() {
        Utils.tapAppScreen();
    }

    public MobileElement getLoadingVideoOverlay() {
        return loadingVideoOverlay;
    }


    /**
     * Clicks the play/pause overlay to control video playback.
     * If the play/pause overlay is present within 8 seconds, it is clicked directly.
     * Otherwise, the screen is tapped first to reveal the overlay before clicking it.
     */
    public void PlayPauseVideoClick(String name) {
        MobileElement el = null;
        switch (name) {
            case "Play/Pause":
                if (Utils.isElementPresent(getPlayPauseOverlay(), 8)) {
                    playPauseOverlay.click();
                } else {
                    Utils.tapAppScreen();
                    playPauseOverlay.click();
                }
                break;

            default:
                throw new IllegalArgumentException("Error in PlayPauseVideoClick(): Unrecognized element '" + name + "'. Verify the input or check for typos.");

        }
    }

    public MobileElement getProgressOverlay() {
        return playPauseOverlay;
    }

    public MobileElement getPlayPauseOverlay() {
        return playPauseOverlay;
    }

    public MobileElement get30SecBackOverlay() {
        return play30SecBackOverlay;
    }

    public MobileElement get30SecForwardOverlay() {
        return play30SecForwardOverlay;
    }

    public MobileElement getCurrentVideoNameOverlay() {
        return currentVideoName;
    }


}

