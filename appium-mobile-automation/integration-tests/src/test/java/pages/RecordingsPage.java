package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stepdefs.Utils;
import stepdefs.xConsts;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * RecordingsPage class represents the Recordings tab in the mobile TV app.
 * It encapsulates UI elements and actions related to viewing, playing,
 * and interacting with user recordings on both Android and iOS platforms.
 * This class extends CommonComponentsPage to reuse shared app components.
 */

public class RecordingsPage extends CommonComponentsPage{

    private CurrentVideoPage viewer;
    String recordingIndex = "";
    String recordingChannnel = "";
    String recordingDate = "";
    String recordingTitle = "";
    String recordingChannnel2 = "";
    String recordingDate2 = "";
    String recordingTitle2 = "";
    int viewerHeight = 0;
    int tabHeight = 0;
    int recordingsTabY = 0;
    int tabExpanedeHeight = 0;
    int recordingsExtendedTabY = 0;

    ArrayList<RecordingInfo> recordingsList = new ArrayList<RecordingInfo>();

    @AndroidFindBy(id = "com.example.tvapp:id/image_schedule")  //schedule
    private MobileElement recordingScheduleButton;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/menu_sort")  //sort
    private MobileElement sortButton;

    @AndroidFindBy(id = "com.example.tvapp:id/menu_filter")  //All Drop-Down
    @iOSFindBy(accessibility="ALL")
    private MobileElement allDropdownButton;

    @WithTimeout(time = 700, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/recycler_view_recording")
    @iOSFindBy(accessibility = "recording")
    private MobileElement recordingsView;

    @WithTimeout(time = 500, unit = TimeUnit.MILLISECONDS)
    @AndroidFindBy(id = "com.example.tvapp:id/view_video")
    @iOSFindBy(accessibility="Video")
    private MobileElement viewerElement;

    @AndroidFindBy(id = "com.example.tvapp:id/image_play_pause")
    private MobileElement recordingPlayPause;

    @AndroidFindBy(id = "com.example.tvapp:id/layout_expandable")
    @iOSFindBy(accessibility="recording")
    private MobileElement recordingTab;

    @AndroidFindBy(id = "com.example.tvapp:id/image_grabber")  //arrow up, expandable tab
    @iOSFindBy(accessibility = "main_grabBar")
    private MobileElement upButton;



    public RecordingsPage(AppiumDriver driver){
        super(driver);
    }

    public String getCurrentVideoNameFromViewer() {
        return viewer.getCurrentVideoName();
    }

    public boolean btvViewerIsShown(int timeout){
        return Utils.isElementPresent(viewerElement, timeout);
    }


    /*
       return true if button Play/Pause is not shown
       There is an appium bug for android:
       (MobileElement)driver.findElement(By.id("com.example.tvapp:id/image_play_pause")); can find
       an element for playing recording, when Play/Pause button is not visible
     */
    public boolean verifyPlayPauseOverlayIsAbsent(int timeout) {
        if (Utils.isAndroid()) {
            return Utils.isElementInVisible("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout" +
                    "/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup[2]/" +
                    "android.view.ViewGroup/android.view.ViewGroup/android.widget.ImageView[2]", timeout);
        }else{
            return Utils.isElementAbsent(getPlayPauseOverlay(), timeout);
        }
    }

    public boolean isUpEnabled(){
     return upButton.isEnabled();
    }

    public boolean isVisible(String name){
        MobileElement el = null;
        switch (name.toUpperCase()) {
            case "ALL":
                el = allDropdownButton;
                break;
            case "SORT":
                if (Utils.isAndroid()) {
                    MobileElement sort = Utils.findElement("recordings_menuSort");
                    el = sort;
                }else{
                    MobileElement table = Utils.findElement("recordingsTable");
                    String selector = "type == 'XCUIElementTypeButton' AND name == 'recording_sortButton'";
                    MobileElement e = table.findElement(MobileBy.iOSNsPredicateString(selector));
                    el = e;
                }
                break;
            case "RECORDING SCHEDULE":
                if (Utils.isAndroid()) {
                    el = recordingScheduleButton;
                }else{
                    MobileElement t = Utils.findElement("recordingsTable");
                    el = t.findElement(By.xpath("(//XCUIElementTypeButton[@name=\"recording_airingButton\"])[1]"));
                }
                break;
            case "DELETE_SWIPE":  //ios
                el = Utils.findElementByXPath("deleteButton");
                    break;
            case "UP":
                el = upButton;
                break;
            case "SORT_FILTER":
                if (Utils.isAndroid()){
                    // Appium doesdn't show recordings menu for android
                }else {
                    el = Utils.findElement("recordingsTab_menu");
                }
                break;
            case "MENU_NEWEST":
                el = Utils.findElement("recordingsTab_menuNewest");
                break;
            case "MENU_OLDEST":
                if (Utils.isAndroid()){
                    // Appium doesdn't show recordings menu for android
                }else {
                    el = Utils.findElement("recordingsTab_menuOldest");
                }
                break;
            case "MENU_ATOZ":
                if (Utils.isAndroid()){
                    // Appium doesdn't show recordings menu for android
                }else {
                    el = Utils.findElement("recordingsTab_menuAtoZ");
                }
                break;
            case "MENU_ZTOA":
                if (Utils.isAndroid()){
                    // Appium doesdn't show recordings menu for android
                }else {
                    el = Utils.findElement("recordingsTab_menuZtoA");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid element: " + name);
        }
        return el.isDisplayed();
    }

    public void tapDeleteSwipe(){
        MobileElement el = (MobileElement)driver.findElement(By.xpath("//XCUIElementTypeButton[@name=\"Delete\"]"));
        el.click();
    }

    public void tapSortFilter(){
        if (Utils.isAndroid()){
            sortButton.click();
        }else {
            try {
                MobileElement table = Utils.findElement("recordingsTable");
                String selector = "type == 'XCUIElementTypeButton' AND name == 'recording_sortButton'";
                MobileElement e = table.findElement(MobileBy.iOSNsPredicateString(selector));
                Utils.tapElement(e);
            } catch (Exception e) {
            }
        }
    }


    public void tapFilterButton(){
        if (Utils.isAndroid()){
            sortButton.click();
        }else {
            try {
                MobileElement table = Utils.findElement("recordingsTable");
                String selector = "type == 'XCUIElementTypeButton' AND name == 'recording_filterButton'";
                MobileElement e = table.findElement(MobileBy.iOSNsPredicateString(selector));
                Utils.tapElement(e);
            } catch (Exception e) {
            }
        }
    }

    public void tapSortFilterMenu(String name){
        MobileElement el = null;
        switch (name.toUpperCase()) {
            case "MENU_NEWEST":
                if (!Utils.isAndroid()) { //Apppium doesn't show Recording tab menu for sorting for android
                    el = Utils.findElement("recordingsTab_menuNewest");
                    el.click();
                }
                break;
            case "MENU_OLDEST":
                if (!Utils.isAndroid()) {
                    el =  Utils.findElement("recordingsTab_menuOldest");
                    el.click();
                }
                break;
            case "MENU_ATOZ":
                if (!Utils.isAndroid()) {
                    el = Utils.findElement("recordingsTab_menuAtoZ");
                    el.click();
                }
                break;
            case "MENU_ZTOA":
                if (!Utils.isAndroid()) {
                    el = Utils.findElement("recordingsTab_menuZtoA");
                    el.click();
                }
                break;
            case "MENU_All":
                if (!Utils.isAndroid()) {
                    el = Utils.findElement("recordingsTab_menuAll");
                    el.click();
                }
                break;
            case "MENU_LIBRARY":
                if (!Utils.isAndroid()) {
                    el = Utils.findElement("recordingsTab_menuLibrary");
                    el.click();
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid choice on Recordings tab sort filter : " + name);
        }

    }

    public String getDropDownValue(String name){
            MobileElement el = null;
            String value = "";
            switch (name.toUpperCase()) {
            case "ALL":
                value = allDropdownButton.getText();
                break;
            case "NEWEST":
                if(Utils.isAndroid()) {
                    value = sortButton.getText();
                }else{
                    String selector = "type == 'XCUIElementTypeButton' AND name == 'recording_sortButton'";
                    el = recordingsView.findElement(MobileBy.iOSNsPredicateString(selector));
                    value = el.getAttribute("label");
                }
                break;
                case "Z-A":
                    if(Utils.isAndroid()) {
                        value = sortButton.getText();
                    }else{
                        String selector = "type == 'XCUIElementTypeButton' AND name == 'recording_sortButton'";
                        el = recordingsView.findElement(MobileBy.iOSNsPredicateString(selector));
                        value = el.getAttribute("label");
                    }
                    break;
            default:
                throw new IllegalArgumentException("Invalid element: " + name);
        }
        return value;
    }


    /**
     * Navigates to the Recording Schedule screen.
     * On Android, it clicks the recording schedule button.
     * On iOS, it locates and clicks the Recording Schedule cell in the recordings table.
     * Initializes the RecordingSchedulePage if not already initialized.
     */
   public BasePage navigateToRecordingSchedule(){
       BasePage bp = null;
       if (Utils.isAndroid()) {
           recordingScheduleButton.click();
       }else{
           MobileElement recordingsTable = Utils.findElement("recordingsTable");
           MobileElement el = recordingsTable.findElement(By.xpath(xConsts.APPLE_XPATHS.get("recordingSchedule")));
           el.click();
       }
       if (MyPages._recordingSchedulePage != null) {
           bp = MyPages._recordingSchedulePage;
       } else {
           bp = new RecordingSchedulePage(driver);
       }
       return bp;
  }


    /**   Watch recording
     *
     * @param index - recording index, starting from 0
     */
    public void watchRecording1(int index) {
        List<MobileElement> recordings = null;
        if (Utils.isAndroid()) {
            boolean visible = Utils.isElementVisible(Utils.getId("play_pause"), 6);
            if (!visible){
                visible = Utils.isElementVisible(Utils.getId("play_pause"), 6);
            }
            if (visible){
                recordings = recordingsView.findElements(By.id(Utils.getId("play_pause")));
            }

        } else {
            MobileElement table = null;
            List<MobileElement> rows = null;
            table = Utils.findElement("recordingsTable");
            rows = table.findElements(By.name("recordings_playpause"));

            recordings = table.findElements(By.name("recordings_playpause"));
        }
        recordings.get(index).click();
    }


    /**   Tap recording to show Info Dialog
     *
     * @param index - recording index, starting from 0
     */
    public BasePage tapRecording(int index) {
        MobileElement title = null;
        BasePage bp = null;
        if (Utils.isAndroid()) {
            List<MobileElement> titles = recordingsView.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("viewGroup"))); //doesn't have prefix "com.example.tvapp:id/"
            title = titles.get(index);

            try {
                Utils.tapElement(title);
            }catch(Exception e)
            {
              System.out.println ("Exception in tapRecording(" + index + ")");
            }
        } else {
            MobileElement table = Utils.findElement("recordingsTable");
            String selector = "type == 'XCUIElementTypeCell' AND visible == 1";
            List<MobileElement> recordings  = table.findElements(MobileBy.iOSNsPredicateString(selector));

            MobileElement r = recordings.get(index);
            try {
                Utils.tapElement(r);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        bp = new RecordingsInfoDialog(driver);
        return bp;
    }


    public String getRecordingTitle(int index){
        String title = "";
        if (Utils.isAndroid()) {
            List<MobileElement> titles = recordingsView.findElements(By.id(Utils.getId("recordingsTitle_title")));
            title = titles.get(index).getText();
        }else{
            List<MobileElement> rows = recordingsView.findElements(By.className(Utils.getId("cellElement")));
            MobileElement xcUiElementTYpeCell = (MobileElement)rows.get(index);
            MobileElement e = (MobileElement)xcUiElementTYpeCell.findElement(By.name(Utils.getId("recordingsTab_recordingTitle")));
            String t = e.getAttribute("value");
            title = t;

            xcUiElementTYpeCell = (MobileElement)rows.get(index);
            e = (MobileElement)xcUiElementTYpeCell.findElement(By.name(Utils.getId("recordingsTab_recordingTitle")));
            t = e.getAttribute("value");
        }
        return title;
    }

    public String getRecordingSubtitle(int index){
        List<MobileElement> subTitles = null;
        String subTitle  = "";
        String showName  = "";
        String expirationLabel  = "";
        if (Utils.isAndroid()) {
            subTitles = recordingsView.findElements(By.id(Utils.getId("recordingsSubtitle_subtitle")));
            subTitle  = subTitles.get(index).getText();
        }else{
            MobileElement table = Utils.findElement("recordingsTable");
            List<MobileElement> rows = table.findElementsByClassName(xConsts.APPLE_CLASSNAMES.get("staticText"));
            int i = 0;

            for (MobileElement row : rows) {
                String a = row.getAttribute("name");
                a = (a == null) ? "" : a;
                if (a==""){
                    continue;
                }
                if (a.equalsIgnoreCase("recording_row_middleLabel")) {
                    subTitle = row.getAttribute("value");
                }else if (a.equalsIgnoreCase("recording_row_topLabel")) {
                    showName = row.getAttribute("value");
                }else if (a.equalsIgnoreCase("recording_row_expirationLabel")) {
                    expirationLabel = row.getAttribute("value");
                }
                if (i-2 == index) {
                    break;
                }
                i++;
            }

        }
        subTitle.split("|");

        return subTitle;
    }


    /**
     *  Getting recording attributes for recording and storing them
     *  in RecordingInfo class
     * @param index  - recording index, starts from 0
     */

    public void storeRecordingAttributesAsRecordingInfo(int index){
        List<MobileElement> subTitles = null;
        String subTitle  = "";
        String showName  = "";
        String expirationLabel  = "";
        String date = "";
        String channel = "";

        if (Utils.isAndroid()) {
            subTitles = recordingsView.findElements(By.id(Utils.getId("infoDlg_showTitle")));
            subTitle  = subTitles.get(index).getText();
        }else{
            MobileElement table = Utils.findElement("recordingsTable");
            List<MobileElement> rows = table.findElementsByClassName(xConsts.APPLE_CLASSNAMES.get("staticText"));
            int i = 0;

            for (MobileElement row : rows) {
                String a = row.getAttribute("name");
                a = (a == null) ? "" : a;
                if (a==""){
                    continue;
                }
                if (a.equalsIgnoreCase("recording_row_middleLabel")) {

                    subTitle = row.getAttribute("value");

                }else if (a.equalsIgnoreCase("recording_row_topLabel")) {
                    showName = row.getAttribute("value");
                }else if (a.equalsIgnoreCase("recording_row_expirationLabel")) {
                    expirationLabel = row.getAttribute("value");
                }
                if (i-2 == index) {
                    break;
                }
                i++;

            }

        }
        String arr[] = subTitle.split("\\|");

        if (arr.length > 1 ){
            channel = arr[0];
        }

        //get DATE from subtitle
        if (arr.length > 1 ){
            date = arr[1];
        }

        // get recording title
        if (Utils.isAndroid()) {
            recordingTitle = this.getRecordingTitle(index);
        }else {
            recordingTitle = showName;
        }

        RecordingInfo info = new RecordingInfo(channel, date, recordingTitle, index,"");
        recordingsList.add(info);
    }

    public void storeRecordingInfoFromInfoDialog(int index, String channel, String date,String recordingTitle){
        RecordingInfo info = new RecordingInfo(channel, date, recordingTitle, index, "");
        recordingsList.add(info);
    }

    /*
      Store attributes at the top of the list for android
      If after scrolling show name in not visible and not accessible through Appium, get attributes from next
      recording.
     */
    public void storeTopOfTheListRecordingAttributes(int index) {
        if (Utils.isAndroid()) {
            String title = "";
            MobileElement record = null;
            MobileElement titleElement = null;

            MobileElement view = Utils.findElement("recycler_view_recording");
            List<MobileElement> els = view.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("viewGroup")));
            record = els.get(index);

            try {
                titleElement = (MobileElement) record.findElement(By.id(Utils.getId("infoDlg_showTitle")));
            } catch (Exception e) {
                System.out.println("Exception - show name is not visible in the Recordings List. Get next recording.");
                MobileElement v  = Utils.findElement("recycler_view_recording");
                els = v.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("viewGroup")));
                record = els.get(1);
                titleElement = (MobileElement) record.findElement(By.id(Utils.getId("infoDlg_showTitle")));
            }
            title = titleElement.getText();
            MobileElement attributesElement = (MobileElement) record.findElement(By.id(Utils.getId("player_attributes")));
            String attributes = attributesElement.getText();
            int recId = recordingsList.size();
            RecordingInfo info = new RecordingInfo("", "", title, recId, attributes);  //or recId = index?
            recordingsList.add(info);
            System.out.println("Store : " + recId + "  " + title + " " + attributes);
        }
    }



    public void storeRecordingAttributes(int index) {
        if (Utils.isAndroid()) {
            MobileElement view = Utils.findElement("recycler_view_recording");
            List<MobileElement> els = view.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("viewGroup")));

            MobileElement record = els.get(index);

            MobileElement titleElement = (MobileElement) record.findElement(By.id(Utils.getId("infoDlg_showTitle")));
            String title = titleElement.getText();

            MobileElement attributesElement = (MobileElement) record.findElement(By.id(Utils.getId("player_attributes")));
            String attributes = attributesElement.getText();
            int recId = recordingsList.size();
            RecordingInfo info = new RecordingInfo("", "", title, recId, attributes);  //or recId = index?
            recordingsList.add(info);
        }else{
            recordingsView = Utils.findElement("recordingsTable");
            String select = "type == 'XCUIElementTypeCell' AND visible == 1";
            List<MobileElement> els  = recordingsView.findElements(MobileBy.iOSNsPredicateString(select));

            MobileElement record = els.get(index);

            String selector = "type == 'XCUIElementTypeStaticText' AND name == 'recording_row_titleLabel'";
            MobileElement titleElement = record.findElement(MobileBy.iOSNsPredicateString(selector));
            String title = titleElement.getText();

            selector = "type == 'XCUIElementTypeStaticText' AND name == 'recording_row_attributesLabel'";
            MobileElement attributesElement = (MobileElement) record.findElement(MobileBy.iOSNsPredicateString(selector));
            String attributes = attributesElement.getText();
            int recId = recordingsList.size();//
            RecordingInfo info = new RecordingInfo("","", title, recId, attributes);
            recordingsList.add(info);
        }
    }



    public String getRecordingChannel(int index){
        String subTitle  = getRecordingSubtitle(index);
        String arr[] = subTitle.split("\\|");
        return arr[0];
    }

    public String getRecordingDate(int index){
        String ret = "";
        String subTitle  = getRecordingSubtitle(index);
        String arr[] = subTitle.split("\\|");
        if (arr.length > 1 ){
            ret = arr[1];
        }
        return ret;
    }


    public void storeRecordingInfo(int index){
        recordingTitle = this.getRecordingTitle(index);
        recordingChannnel = this.getRecordingChannel(index);
        recordingDate = this.getRecordingDate(index);
    }


    public RecordingInfo getStoredRecordingInfo(int id){
        RecordingInfo returnedInfo = null;
        for (RecordingInfo e : recordingsList) {
            int recId = e.getId();
            if (recId == id) {
                returnedInfo = e;
                break;
            }
        }
        return returnedInfo;
    }

    public void storeRecordingInfoForSecondRecording(){
        recordingTitle2 = this.getRecordingTitle(1);
        recordingChannnel2 = this.getRecordingChannel(1);
        recordingDate2 = this.getRecordingDate(1);
    }

    public String getRememberedRecordingTitle(){
        return this.recordingTitle;
    }



    public RecordingInfo getRecordingInfo(int id){
        RecordingInfo info = null;

        for (RecordingInfo e : recordingsList) {
            int recId = e.getId();
            if (recId == id) {
                info = e;
                break;
            }
        }
        return info;
    }

    public int getRecordingsInfoSize(){
        int infoSize = recordingsList.size();
        return infoSize;
    }


    public int getNumberOfVisibleRecords(){
        int size = 0;
        if (Utils.isAndroid()) {
            List<MobileElement> recordsAndroid = recordingsView.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("viewGroup")));
            size = recordsAndroid.size();
        }else{
            //Takes a lot of time
            List<MobileElement> recordsIos = recordingsView.findElements(By.className("XCUIElementTypeCell"));
            size = recordsIos.size();
        }
        return size;
    }


    public String getEmptyMessageText(){
        MobileElement title = null;
        title = Utils.findElement("recordingsTab_emptyTabMessage");
        return title.getText();
    }
}

