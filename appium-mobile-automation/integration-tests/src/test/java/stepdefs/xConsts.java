package stepdefs;

import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.*;


public class
xConsts {
    public static final long BTV_EMPLICIT_WAIT = 60;
    public static final String AUTOTEST_EMAIL = "tv+tester@gmail.com";
    public static final String AUTOTEST_PASSWORD = "qatest";
    public static final String BUNDLE_ID_ANDROID_ACTIVITY = "com.tvapp.activity.StartupActivity";
    public static final String BUNDLE_ID_IOS = "com.example.debug.tvapp";

    public static final int STATUSBAR_Y = 20;

    public static final String SIGNUP_SCREEN1_ZIPCODE_TEXT = "Enter your ZIP Code";

    public static final String LOGO = "BTV_LOGO";
    public static final String CLEAR_TEXT_BTN = "CLEAR_TEXT_BUTTON";
    public static final String CONTACT_US_LINK = "CONTACT_US_LINK";
    public static final String SIGN_UP_BTN = "SIGN UP";
    public static final String SIGN_IN_BTN_SECOND_SCREEN = "SIGN IN";
    public static final String SIGN_IN_BTN_SECOND_SCREEN_IOS = "SIGN IN";
    public static final String GO_TO_FIRST_SCREEN = "NAVIGATE TO SIGN UP";
    public static final String TERMS_AND_CONDITIONS = "TERMS & CONDITIONS";
    public static final String TERMS_OF_USE_PAGE_TITLEBAR = "Terms of Use | LocalTV";
    public static final String HOME_TITLEBAR = "Free Local TV";

    public static final String FORGOT_YOUR_PASSWORD_LINK = "Forgot your password";
    public static final String RESET_PASSWORD_BTN = "Reset Password";
    public static final String NEED_MORE_HELP_CONTACT_US = "Need more help?Contact Us";

    public static final String REQUEST_NEW_CODE = "Request a new code";
    public static final String UPDATE_PASSWORD = "Update Password";
    public static final String OK_BTN = "Ok";
    public static final String CANCEL_BTN = "Cancel";
    public static final String VERIFICATION_CODE = "Verification code";
    public static final String NEW_PASSWORD = "New password";
    public static final String CONFIRM_PASSWORD = "Confirm your password";

    public static final String GOOGLE = "GOOGLE";
    public static final String GOOGLE_EMAIL_NEXT_BTN = "NEXT";
    public static final String GOOGLE_PASSWORD_NEXT_BTN = "PASSWORD_NEXT";

    public static final String LIVE = "Live";
    public static final String GUIDE = "Guide";
    public static final String RECORDINGS = "Recordings";
    public static final String EMAIL_IN_USE_MESSAGE = "E-mail address already in use";

    public static final String RECORD_EPISODE_CHECKBOX = "Record";
    public static final String CHECK_AVAILABILITY_BTN = "CHECK AVAILABILITY";
    public static final String SIGNED_IN_BTN = "SIGN IN";
    public static final String WATCHING = "Watching";
    public static final String RECORDING = "Recording";
    public static final String RESET_PASSWORD = "Reset Password";


    public static final String FACEBOOK_CONTINUE_AS_BUTTON = "CONTINUE AS";
    public static final String AUTOTEST_LAST_CHANNEL = "51.2 QUBO";   //"150 NATTY"
    public static final String AUTOTEST_FIRST_CHANNEL = "5.1 CBS5";
    public static final int SWIPE_DURATION = 2000;  // Swipe duration in milliseconds

    public static final String MENU_RECORDING_SCHEDULE = "Recording Schedule";
    public static final String MENU_CUSTOMIZE_CHANNELS = "Customize Channels";
    public static final String MENU_LOGOUT = "Logout";

    public static final String NOT_AVAILALE_ERROR_MESSAGE_ANDROID = "Sorry! Application isn't available in your area right now.\n\nDo you want to be notified when we're available in your market?";
    public static final String NOT_AVAILALE_ERROR_MESSAGE_IOS = "Sorry! Application isn't available in your area right now.";

    public static final String PIP_IOS_CLOSE_WINDOW = "Close Picture-in-Picture";

    public static final String NETWORK_PROBLEMS_WARNING = "This station may be off the air or you may be experiencing network problems. Please tap to try again.";

    public static final String LOCATION_DLG_ANDROID = "In order to work, we need to know where you are. Please allow access to your location.";

    public static final String ADB_KEY_EVENT_66 = "adb shell input keyevent 66";


    //Full Screen View
    public static final String CHANNEL_NUMBER__REGEX = "\u00A0\u00A0/\u00A0\u00A0";


    public static final Map<String, String> APPLE_IDS = createIosMap();

    public static Map<String, String> createIosMap() {
        Map<String, String> appleIdsMap = new HashMap<>();
        appleIdsMap.put("player_ccButton", "player_ccButton");                          //player
        appleIdsMap.put("player_nextChevron", "player_nextIcon");                       //player
        appleIdsMap.put("player_channelChanger", "player_channelChangerView");          //player
        appleIdsMap.put("player_previousChevron", "player_previousIcon");               //player
        appleIdsMap.put("custChannels_stationName", "station_cell_nameLabel");          //CustomizeChannelsPage
        appleIdsMap.put("player_attributes", "player_airingDetails");                   //FullScreenPage
        appleIdsMap.put("player_title", "player_showTitle");                            //FullScreenPage
        appleIdsMap.put("player_slider", "player_slider");                              //FullScreenPage
        appleIdsMap.put("player_muteButton", "player_muteButton");                      //FullScreenPage
        appleIdsMap.put("player_ccButon", "player_ccButon");                            //FullScreenPage
        appleIdsMap.put("player_ellapsedTime", "player_ellapsedTimeLabel");             //FullScreenPage
        appleIdsMap.put("collapseScreen_chevron", "main_grabBar");                      //FullScreenPage
        appleIdsMap.put("player_nextStationName", "player_nextStationName");            //FullScreenPage
        appleIdsMap.put("player_seekForwardButton", "player_seekForwardButton");        //FullScreenPage
        appleIdsMap.put("player_seekBackButton", "player_seekBackButton");              //FullScreenPage
        appleIdsMap.put("player_previousStationName", "player_previousStationName");    //FullScreenPage
        appleIdsMap.put("fullScreen_lowerChevron", "main_grabBar");                     //FullScreenPage
        appleIdsMap.put("fullScreen_button", "fullscreen_button");                      //FullScreenPage
        appleIdsMap.put("guide", "guide");                                              //Guide
        appleIdsMap.put("guide_CollectionView", "guide_CollectionView");                //Guide
        appleIdsMap.put("collectionView", "XCUIElementTypeCollectionView");             //GuideappleIdsMap.put("guideCellTitle", "guide_cell_title");
        appleIdsMap.put("guide_cellLabel", "guide_cell_label");
        appleIdsMap.put("liveTab_showTitle", "airing_options_titleLabel");              //Live
        appleIdsMap.put("liveTab_previousChevron", "airing_options_previousButton");    //Live
        appleIdsMap.put("liveTab_nextChevron", "airing_options_nextButton");            //Live
        appleIdsMap.put("liveTab_attributes", "airing_options_attributesLabel");        //Live Tab
        appleIdsMap.put("recordingsTable", "recording");                                //Recordings
        appleIdsMap.put("recordingsTab_menu", "menu");                                  //Recordings
        appleIdsMap.put("recordingsTab_menuNewest", "menu_newest");                     //Recordings
        appleIdsMap.put("recordingsTab_menuOldest", "menu_oldest");                     //Recordings
        appleIdsMap.put("recordingsTab_menuAtoZ", "menu_atoz");                         //Recordings
        appleIdsMap.put("recordingsTab_menuZtoA", "menu_ztoa");                         //Recordings
        appleIdsMap.put("recordingsTab_menuAll", "ALL");                                //Recordings
        appleIdsMap.put("recordingsTab_menuLibrary", "LIBRARY");                        //Recordings
        appleIdsMap.put("watchButton", "airing_options_watchButton");
        appleIdsMap.put("recordingsTab_recordingTitle", "recording_row_topLabel");          //Recordings
        appleIdsMap.put("recordingsTab_emptyTabMessage", "There are no recorded programs.");//Recordings
        appleIdsMap.put("recordingSchedule_table", "scheduled_recording");                 //RecordingSchedule
        appleIdsMap.put("recordingSchedule_rowTitle", "scheduled_recording_row_titleLabel");    //RecordingSchedule
        appleIdsMap.put("recordingSchedule_rowAttributes", "scheduled_recording_row_detailsLabel"); //RecordingSchedule
        appleIdsMap.put("infoDlg_showTitle", "schedule_options_titleLabel");              //Info DialogDialog
        appleIdsMap.put("app_logo", "app_logo_ios");                                      //Top menu
        appleIdsMap.put("alertWrongArea_NotifyMeButton", "alert_rightButtonLabel");       //BTV not available in tha area
        appleIdsMap.put("alertWrongArea_NoThanksButton", "alert_leftButtonLabel");        //BTV not available in tha area
        appleIdsMap.put("blackout_text", "player_warningLabel");                          //blackout
        appleIdsMap.put("menu", "settings_button");
        appleIdsMap.put("play_pause", "player_pausePlayButton");                          //player
        appleIdsMap.put("loadingProgress_image", "player_activityIndicator");
        appleIdsMap.put("forgotPassword_emailField", "forgot_password_emailTextField");  // Reset Password screen
        appleIdsMap.put("Allow While Using App", "Allow While Using App");
        appleIdsMap.put("Ask App not to Track", "Ask App not to Track");
        appleIdsMap.put("buttonNo", "alertWrongArea_NoThanksButton");
        return Collections.unmodifiableMap(appleIdsMap);
    }

    public static final Map<String, String> APPLE_XPATHS  = createAppleXPathMap();

    public static Map<String, String> createAppleXPathMap(){
        Map<String, String> appleXPathsMap = new HashMap<>();
        appleXPathsMap.put("deleteButton", "//XCUIElementTypeButton[@name=\"Delete\"]");
        appleXPathsMap.put("guideCollection",  "//XCUIElementTypeOther[@name=\"guide_CollectionView\"]");
        appleXPathsMap.put("playerCCButton",  "//XCUIElementTypeButton[@name=\"player_ccButton\"]");
        appleXPathsMap.put("playerPreviousButton",  "/XCUIElementTypeImage[@name=\"player_previousIcon\"]");
        appleXPathsMap.put("liveTab_attributes",  "//XCUIElementTypeStaticText[@name=\"airing_options_attributesLabel\"]");
        appleXPathsMap.put("live_recordButton",  "//XCUIElementTypeButton[@name=\"airing_options_recordOptionsButton\"]");
        appleXPathsMap.put("recordingSchedule",  "(//XCUIElementTypeButton[@name=\"recording_airingButton\"])[1]");
        appleXPathsMap.put("stationName",  "(//XCUIElementTypeStaticText[@name=\"station_cell_label\"])[1]");
        appleXPathsMap.put("player_nextIcon",  "//XCUIElementTypeImage[@name=\"player_nextIcon\"])");

        return Collections.unmodifiableMap(appleXPathsMap);
    }

    public static final Map<String, String> APPLE_CLASSNAMES  = createIosClassnamesMap();

    public static Map<String, String> createIosClassnamesMap(){
        Map<String, String> iosClassnamesMap = new HashMap<>();
        iosClassnamesMap.put("cellElement", "XCUIElementTypeCell");
        iosClassnamesMap.put("collectionView", "XCUIElementTypeCollectionView");
        iosClassnamesMap.put("navigationBar", "XCUIElementTypeNavigationBar");
        iosClassnamesMap.put("staticText", "XCUIElementTypeStaticText");
        iosClassnamesMap.put("recording_row_titleLabel", "recording_row_titleLabel");
        return Collections.unmodifiableMap(iosClassnamesMap);
    }


    public final static String ANDROID_ID_PREFIX = "com.example.tvapp:id/";
    public static final Map<String, String> ANDROID_IDS = createAndroidMap();

    public static Map<String, String> createAndroidMap() {
        Map<String, String> andoidIdsMap = new HashMap<>();
        andoidIdsMap.put("pipWindow_content", "content");                              //Pip window
        andoidIdsMap.put("play_pause", "image_play_pause");                            //player
        andoidIdsMap.put("player_ccButton", "image_cc_play");                          //CommonComponents
        andoidIdsMap.put("player_muteButton", "image_mute_play");                      //CommonComponents
        andoidIdsMap.put("player_seekForwardButton", "image_skip_forward");            //CommonComponents
        andoidIdsMap.put("player_attributes", "text_attributes");                      //FullScreenPage, LivePage, InfoDialog (Android)
        andoidIdsMap.put("player_recordingAttributes", "text_attributes_recording");   //FullScreenPage for recording, InfoDlg(Rec.Tab) - ("RECORDED" FEB 24..)
        andoidIdsMap.put("player_seekBackButton", "image_skip_back");                  //FullScreenPage
        andoidIdsMap.put("fullScreen_button", "menu_full_screen");                    //FullScreenPage
        andoidIdsMap.put("channel_name", "text_callsign");                             //Guide
        andoidIdsMap.put("liveTab_nextChevron", "image_station_next");                 //Live
        andoidIdsMap.put("liveTab_previousChevron", "image_station_previous");         //Live
        andoidIdsMap.put("liveTab_layoutTabContainer", "layout_tab_container");        //Live
        andoidIdsMap.put("liveTab_recordEpisodeButton", "button_record_episode");      //Live
        andoidIdsMap.put("infoDlg_showTitle", "text_title");                           //Info Dialog
        andoidIdsMap.put("infoDlg_cancel", "button_record_cancel");                    //Info Dialog
        andoidIdsMap.put("infoDlg_librarySwitch", "android.widget.Switch");            //Info Dialog
        andoidIdsMap.put("recordingsTab_emptyTabMessage", "text_empty_message");       //Recordings
        andoidIdsMap.put("recycler_view_recording", "recycler_view_recording");        //Recordings
        andoidIdsMap.put("loadingProgress_image", "progress");                         //player, barImage for
        andoidIdsMap.put("player_nextChevron", "image_station_next");                  //player, Portrait view
        andoidIdsMap.put("recordings_menuSort", "menu_sort");                          //Recordings
        andoidIdsMap.put("recordingsSubtitle_subtitle", "text_recording_sub_title");   //Recordings
        andoidIdsMap.put("recordingsTitle_title", "text_recording_title");             //Recordings
        andoidIdsMap.put("layout_expandable", "layout_expandable");
        andoidIdsMap.put("password", "password");                                       //sign in
        andoidIdsMap.put("buttonNo", "button2");
        andoidIdsMap.put("edit_ZipCode", "edit_zip_code");
        andoidIdsMap.put("forgotPassword_emailField", "edit_email");
        andoidIdsMap.put("recSchedule_container", "layout_tab_container");
        andoidIdsMap.put("alertWrongArea_NoThanksButton", "com.example.tvapp");
        return Collections.unmodifiableMap(andoidIdsMap);
    }

    public static final Map<String, String> ANDROID_IDS2 = createAndroidIds2Map();

    public static Map<String, String> createAndroidIds2Map() {
        Map<String, String> andoidIds2Map = new HashMap<>();
        andoidIds2Map.put("pipWindow_content", "android:id/content");
        andoidIds2Map.put("actionBar", "com.android.chrome:id/action_bar_root");
        andoidIds2Map.put("androidChromeUrl", "com.android.chrome:id/url_bar");
        andoidIds2Map.put("alertWrongArea_no", "android:id/button2");
        andoidIds2Map.put("alertWrongArea_yes", "android:id/button1");
        andoidIds2Map.put("buttonContainer", "com.android.vending:id/button_container");
        andoidIds2Map.put("titleBar", "com.android.chrome:id/title_bar");
        return Collections.unmodifiableMap(andoidIds2Map);
    }


    public static final Map<String, String> ANDROID_XPATHS  = createAndroidXPathMap();

    public static Map<String, String> createAndroidXPathMap(){
        Map<String, String> andoidXPathsMap = new HashMap<>();
        andoidXPathsMap.put("topMenu_menuItemsList",  "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView");
        andoidXPathsMap.put("pipWindow_close","//android.widget.ImageView[@content-desc=\"Close\"]");
        return Collections.unmodifiableMap(andoidXPathsMap);
    }

    public static final Map<String, String> ANDROID_CLASSNAMES  = createAndroidClassnamesMap();

    public static Map<String, String> createAndroidClassnamesMap(){
        Map<String, String> andoidClassnamesMap = new HashMap<>();
        andoidClassnamesMap.put("textview", "android.widget.TextView");
        andoidClassnamesMap.put("androidRelativeLayout", "android.widget.RelativeLayout");
        andoidClassnamesMap.put("gpoogleAccount_view", "android.view.View");
        andoidClassnamesMap.put("viewGroup", "android.view.ViewGroup");
        andoidClassnamesMap.put("androidLinearLayout", "android.widget.LinearLayout");
        andoidClassnamesMap.put("androidWidgetButton", "android.widget.Button");
        andoidClassnamesMap.put("androidView",  "android.view.View");
        andoidClassnamesMap.put("androidWidgetText", "android.widget.EditText");
        return Collections.unmodifiableMap(andoidClassnamesMap);
    }


}
