package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import stepdefs.Utils;
import stepdefs.xConsts;

import java.util.ArrayList;
import java.util.List;

/**
 * Page Object representing the Recording Schedule screen in the mobile app.
 * Provides methods to interact with scheduled recordings, retrieve recording attributes,
 * and verify UI elements on both Android and iOS platforms.
 */

public class RecordingSchedulePage extends CommonComponentsPage {

    @AndroidFindBy(id = "com.example.tvapp:id/actionbar")
    //   @iOSFindBy(xpath = "//XCUIElementTypeNavigationBar[@name=\"Recording Schedule\"")
    private MobileElement actionBar;

    @AndroidFindBy(id = "com.example.tvapp:id/recycler_view_recording")
    private MobileElement recordingsView;


    @AndroidFindBy(id = "com.example.tvapp:id/image_close")
    @iOSFindBy(xpath = "(//XCUIElementTypeButton[@name=\"recording_closeButton\"])[1]")
    private MobileElement closeButton;

    @iOSFindBy(accessibility = "Recording Schedule")
    private MobileElement recordingScheduleBar;

    @iOSFindBy(accessibility = "com.example.tvapp:id/text_empty_message")
    private MobileElement emptyMessage;

    @AndroidFindBy(id = "com.example.tvapp:id/snackbar_action")
    private MobileElement undoButton;

    @AndroidFindBy(id = "com.example.tvapp:id/snackbar_text")
    private MobileElement undoText;

    ArrayList<ScheduledRecordingInfo> recordingsList = new ArrayList<ScheduledRecordingInfo>();


    public RecordingSchedulePage(AppiumDriver driver) {
        super(driver);
    }



    /**
     * Retrieves the header text of the Recording Schedule page.
     */
    public String getHeader() {
        MobileElement el = null;
        if (Utils.isAndroid()) {
            MobileElement bar = (MobileElement) actionBar.findElement(By.className(xConsts.ANDROID_CLASSNAMES.get("textview")));
            el = bar.findElement(By.className(xConsts.ANDROID_CLASSNAMES.get("textview")));
        } else {
            MobileElement e = Utils.findElementByClassname("navigationBar");
            el = (MobileElement) e.findElement(By.id("Cancel"));
        }
        return el.getText();
    }

    /**
     * Retrieves the title of the Recording Schedule page.
     */
    public String getTitle() {
        String title = "";
        MobileElement bar = null;
        if (Utils.isAndroid()) {
            MobileElement container = Utils.findElement("recSchedule_container");
            MobileElement header = (MobileElement) container.findElement(By.id(xConsts.ANDROID_IDS.get("recSchedule_layoutHeader")));
            title = header.findElement(By.className(xConsts.ANDROID_CLASSNAMES.get("textview"))).getText();
            System.out.println(title);
        } else {
            if (!Utils.isElementLocatedByClassNameVisible("XCUIElementTypeStaticText", 8)) {
                Utils.isElementLocatedByClassNameVisible(xConsts.APPLE_CLASSNAMES.get("staticText"), 8);
            }
            bar = recordingScheduleBar.findElement(By.className(xConsts.APPLE_CLASSNAMES.get("staticText")));
            title = bar.getText();
        }
        return title;
    }

    /**
     * Closes the Recording Schedule page by tapping the close button.
     */
    public void closeRecordingSchedule()
    {
        closeButton.click();
    }


    /**
     * Retrieves the empty message text displayed when there are no scheduled recordings.
     */
    public String getEmptyMessageText() {
        MobileElement title = null;
        String msg = "";
        if (Utils.isAndroid()) {
            title = Utils.findElement("recordingsTab_emptyTabMessage");
            msg = title.getText();
        } else {
            MobileElement recordingsView = Utils.findElement("recordingSchedule_table");
            List<MobileElement> els = recordingsView.findElements(By.className(xConsts.APPLE_CLASSNAMES.get("staticText")));
            for (MobileElement e : els) {
                msg = e.getAttribute("name");
                System.out.println("recording title : " + msg);
                if (msg.contains("No recordings")) {
                    break;
                }
            }

        }
        return msg;
    }

    /**
     * Returns the number of visible records on the Recording Schedule page.
     */
    public int getNumberOfVisibleRecords() {
        int size = 0;
        if (Utils.isAndroid()) {
            List<MobileElement> recordsAndroid = recordingsView.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("viewGroup")));
            size = recordsAndroid.size();
        } else {
            MobileElement table = Utils.findElement("recordingSchedule_table");
            String selector = "type == 'XCUIElementTypeCell' AND visible == 1";
            List<MobileElement> recordsIos = table.findElements(MobileBy.iOSNsPredicateString(selector));
            size = recordsIos.size();
        }
        return size;
    }


    /**
     * Stores recording attributes for an Android device.
     */
    public void storeRecordingAttributes(int index) {
        List<MobileElement> els = recordingsView.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("viewGroup")));
        MobileElement record = els.get(index);
        MobileElement titleElement = record.findElement(By.id(Utils.getId("infoDlg_showTitle")));
        String title = titleElement.getText();

        MobileElement attributesElement = Utils.findElement("player_attributes");
        String attributes = attributesElement.getText();
        ScheduledRecordingInfo info = new ScheduledRecordingInfo(attributes, title, index);
        recordingsList.add(info);
    }

    /**
     * Stores recording attributes for an iOS device.
     */
    public void storeRecordingAttributesForIos(int index) {
        recordingsView = Utils.findElement("recordingSchedule_table");
        List<MobileElement> els = recordingsView.findElements(By.className(xConsts.APPLE_CLASSNAMES.get("cellElement")));
        MobileElement record = els.get(index);

        MobileElement titleElement = record.findElement(By.id(Utils.getId("recordingSchedule_rowTitle")));
        String title = titleElement.getText();
        MobileElement attributesElement = record.findElement(By.id(Utils.getId("recordingSchedule_rowAttributes")));
        String attributes = attributesElement.getText();

        ScheduledRecordingInfo info = new ScheduledRecordingInfo(attributes, title, index);
        recordingsList.add(info);
    }

    /**
     * Checks if a recording is found on iOS based on its title.
     */
    public boolean isRecordingFoundForIos(String showTitle) {
        boolean found = false;
        MobileElement table = Utils.findElement("recordingSchedule_table");
        List<MobileElement> rows = table.findElementsByName(Utils.getId("recordingSchedule_rowTitle"));
        for (MobileElement e : rows) {
            if (e.getAttribute("value").equalsIgnoreCase(showTitle)) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Retrieves scheduled recording information based on ID.
     */
    public ScheduledRecordingInfo getScheduledRecordingInfo(int id) {
        for (ScheduledRecordingInfo e : recordingsList) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
}
