package stepdefs;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static boolean myAccountPage = false;   // true, when myAccountPage is shown


    public Utils() {

    }


    public static void setCapabilities(String platform, DesiredCapabilities cap) throws Exception {
        switch (platform.toLowerCase()) {
            case "ios":
                cap.setCapability("waitForIdleTimeout", 0);   //handling stale exception

                cap.setCapability("appium:" + "autoAcceptAlerts", true);
                cap.setCapability("appium:" + "autoDismissAlerts", true);
                break;

            case "android":
                cap.setCapability("appium:" + MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
                cap.setCapability("appium:" + "appPackage", xConsts.BUNDLE_ID);
                cap.setCapability("appium:" + "appActivity", xConsts.BUNDLE_ID_ANDROID_ACTIVITY);
                cap.setCapability("appium:" + "autoWebiew", true);
                cap.setCapability("appium:" + "ignoreHiddenApiPolicyError", true);
                cap.setCapability("appium:" + MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);

                break;

            default:
                throw new Exception("Platform  not supported");
        }
    }

    /**
     * Reads a property value from the config.properties file located in src/test/resources.
     */
    public static String readProperty(String property) {
        Properties prop = new Properties();
        InputStream input = null;
        String value = null;
        /*load in the properties file from src/test/resources */
        try {

            input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
            prop.load(input);
            value = prop.getProperty(property);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }


    /**
     * Finds a mobile element by ID for both Android and iOS platforms.
     */
    public static MobileElement findElement(String id) {
        String realId = Utils.isAndroid() ? xConsts.ANDROID_ID_PREFIX + xConsts.ANDROID_IDS.get(id) : xConsts.APPLE_IDS.get(id);
        return (MobileElement) Utils.getDriver().findElement(By.id(realId));
    }


    /**
     * Finds a web element by ID for both Android and iOS platforms.
     */
    public static WebElement findWebElement(String id) {
        return (WebElement) Utils.getDriver().findElement(By.id(id));
    }

    /**
     * Finds a remote web element by ID for both Android and iOS platforms.
     */
    public static RemoteWebElement findRemoteWebElement(String id) {
        return (RemoteWebElement) Utils.getDriver().findElement(By.id(id));
    }

/**
 * Finds a mobile element by ID for Android when the prefix differs from the standard ID prefix.
 */
    c

    static MobileElement findElementNoPrefix(String id) {
        return (MobileElement) Utils.getDriver().findElement(By.id(xConsts.ANDROID_IDS2.get(id)));
    }

    /**
     * Finds multiple mobile elements by ID and returns them as a list.
     */
    public static List<MobileElement> findElements(String id) {
        String realId = Utils.isAndroid() ? xConsts.ANDROID_ID_PREFIX + xConsts.ANDROID_IDS.get(id) : xConsts.APPLE_IDS.get(id);
        System.out.println("realId :" + realId);
        List<MobileElement> rows = null;
        if (Utils.isAndroid()) {
            rows = Utils.getDriver().findElements(By.id(realId));
        } else {

        }
        return rows;
    }


    /**
     * Retrieves the platform-specific ID for a given element.
     */
    public static String getId(String id) {
        String realId = Utils.isAndroid() ? xConsts.ANDROID_ID_PREFIX + xConsts.ANDROID_IDS.get(id) : xConsts.APPLE_IDS.get(id);
        System.out.println(realId);
        return realId;
    }

    /**
     * Finds a mobile element by XPath for both Android and iOS platforms.
     */
    public static MobileElement findElementByXPath(String xpathId) {
        String xpath = Utils.isAndroid() ? xConsts.ANDROID_XPATHS.get(xpathId) : xConsts.APPLE_XPATHS.get(xpathId);
        return (MobileElement) Utils.getDriver().findElement(By.xpath(xpath));
    }


    /**
     * Finds a mobile element by class name for both Android and iOS platforms.
     */
    public static MobileElement findElementByClassname(String className) {
        String xpath = Utils.isAndroid() ? xConsts.ANDROID_CLASSNAMES.get(className) : xConsts.APPLE_CLASSNAMES.get(className);
        return (MobileElement) Utils.getDriver().findElement(By.className(className));
    }


    /**
     * Checks whether the testing platform is Android.
     *
     * @return True if the platform is Android, false otherwise.
     */
    public static boolean isAndroid() {
        if (HooksService.platform.equalsIgnoreCase("android")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves the platform-specific bundle ID.
     */
    public static String getBundleId() {
        String bundleId = "";
        if (Utils.isAndroid()) {
            bundleId = xConsts.BTV_BUNDLE_ID;
        } else {
            bundleId = xConsts.BTV_BUNDLE_ID_IOS;
        }
        return bundleId;
    }

    /**
     * Hides the keyboard for iOS devices running platform version 14 or higher.
     */
    public static void hideKeyboard(MobileElement element) {
        String os = Utils.getCapability("platformVersion");
        System.out.println("platform version : " + os);
        int length = os.length();
        if (!Utils.isAndroid() && os.substring(0, 2).equalsIgnoreCase("14")) {
            System.out.println("Inside hidekeyboard ");
            element.sendKeys("\n");  // or  signInButton.click();
        }
    }


    public static String readProperty1(String property) {
        Properties prop;
        String value = null;
        try {
            prop = new Properties();
            prop.load(new FileInputStream(new File("config.properties")));

            value = prop.getProperty(property);

            if (value == null || value.isEmpty()) {
                throw new Exception("Value not set or empty");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }


    /*
     *  Method returns capability for a String parameter "key"
     */
    public static String getCapability(String key) {
        return (String) HooksService.getDriver().getCapabilities().getCapability(key);
    }


    /*
     *  Method returns AppiumDriver
     */
    public static AppiumDriver getDriver() {
        return HooksService.getDriver();
    }


    /**
     * Hides the keyboard on mobile devices.
     * On iOS version 14, it simulates a tap above the keyboard to dismiss it.
     * On other platforms, it invokes the default hide keyboard function.
     */
    public static void hideKeyboard() {
        String os = Utils.getCapability("platformVersion");
        int length = os.length();
        if (!Utils.isAndroid() && os.substring(0, 2).equalsIgnoreCase("14")) {
            //dismiss keyboard for ios v.14.4 -  tap screen above keyboard on sign in screen
            boolean visible = Utils.isElementVisible("1", 4);
            if (visible) {
                MobileElement el = (MobileElement) Utils.getDriver().findElement(By.id("1"));
                int graby = el.getLocation().getY();
                int grabx = el.getLocation().getX();
                int grabHeight = el.getSize().getHeight();
                int y = graby - grabHeight;
                TouchAction touchAction = new TouchAction(Utils.getDriver());
                touchAction.tap(PointOption.point(grabx, y))
                        .perform();
            }

        } else {
            Utils.getDriver().hideKeyboard();  //if present gave exception for android Google_Pixel_7.1.2
        }
    }

    /**
     * Returns the expected error message for an invalid email or password.
     *
     * @param msg       The custom error message.
     * @param errorType The type of error ("EMAIL" or "PASSWORD").
     * @return The expected error message.
     */
    public static String getExpectedInvalidEmailMessage(String msg, String errorType) {
        String expectedMsg = "";
        if (errorType.equalsIgnoreCase("EMAIL")) {
            expectedMsg = "* Email is invalid";
        } else {
            expectedMsg = msg + ".\nIf your password is less than 6 characters, " +
                    "please tap Forgot Your Password to choose a new one.";
        }
        return expectedMsg;
    }

    /**
     * Retrieves the name of the currently connected device.
     */
    public static String getDeviceName() {
        return (String) HooksService.getDriver().getCapabilities().getCapability(MobileCapabilityType.DEVICE_NAME);
    }


    /**
     * Determines if Picture-in-Picture (PIP) mode is supported.
     * PIP is supported on iOS version 14 and above, and Android version 8 and above.
     *
     * @return {@code true} if PIP is supported, otherwise {@code false}.
     */
    public static boolean isPIPSupported() {
        boolean pip = true;
        String os = Utils.getCapability("platformVersion");
        int index = os.indexOf(".");
        if (index == -1) {
            index = os.length();
        }
        String version = os.substring(0, index);
        int v = Integer.parseInt(version);
        if (Utils.isAndroid()) {
            if (v < 8) {
                System.out.println("Pip is not supported for android devices with os < 8");
                pip = false;
            }
        } else {
            if (v < 14) {
                System.out.println("Pip is not supported for ios devices with os < 14");
                pip = false;
            }
        }
        return pip;
    }


    /**
     * Determines if Dark Mode is supported on the current device.
     * Dark Mode is supported on iOS version 13 and above, and Android version 9 and above.
     */
    public static boolean isDarkModeSupported() {
        boolean darkMode = true;
        String os = Utils.getCapability("platformVersion");
        int index = os.indexOf(".");
        if (index == -1) {
            index = os.length();
        }
        int v = Integer.parseInt(os.substring(0, index));
        if (Utils.isAndroid()) {
            if (v < 9) {
                System.out.println("Dark Mode is not supported for android devices with os < 9");
                darkMode = false;
            }
        } else {
            if (v < 13) {
                System.out.println("Dark Mode is not supported for ios devices with os < 13");
                darkMode = false;
            }
        }
        return darkMode;
    }


    /**
     * Enables Dark Mode on the device.
     * This method is only applicable to iOS devices, as enabling Dark Mode on Android requires a device reboot.
     */
    public static void turnOnDarkMode() {
        if (Utils.isAndroid()) {
            System.out.println("Test is not implemented for android, it requires device reboot.");
            return;
        }
        Utils.getDriver().executeScript("mobile: setAppearance", ImmutableMap.of("style", "dark"));
    }

    /**
     * Disables Dark Mode on the device.
     * This method is only applicable to iOS devices, as disabling Dark Mode on Android requires a device reboot.
     */
    public static void turnOffDarkMode() {
        if (Utils.isAndroid()) {
            System.out.println("Test is not implemented for android, it requires device reboot.");
            return;
        }
        Utils.getDriver().executeScript("mobile: setAppearance", ImmutableMap.of("style", "light"));
    }


    /**
     * Navigates back in the application.
     * Uses the Android back button if running on an Android device.
     * If running on iOS, it puts the app in the background for a brief moment.
     */
    public static void goBack() {
        if (Utils.isAndroid()) {
            ((AndroidDriver) Utils.getDriver()).pressKeyCode(AndroidKeyCode.BACK);
        } else {
            Utils.getDriver().runAppInBackground(Duration.ZERO);
        }
    }


    /**
     * Rotates the device screen to the specified orientation.
     * <p>
     * A character representing the desired screen orientation:
     * 'L' for Landscape, 'P' for Portrait.
     */
    public static void rotateScreen(char orientation) {
        Character ch = new Character(orientation);
        try {
            if (ch.equals('L')) {
                Utils.getDriver().rotate(ScreenOrientation.LANDSCAPE);
            } else if (ch.equals('P')) {
                Utils.getDriver().rotate(ScreenOrientation.PORTRAIT);
            }
        } catch (Exception e) {
            System.out.println("Can't rotate screen.");
            e.getMessage();
        }
    }


    /**
     * Simulates pressing the power button to turn the device screen on or off.
     * This method is currently implemented for Android devices only.
     */
    public static void pressPowerButton() {
        if (Utils.isAndroid()) {
            ((AndroidDriver) Utils.getDriver()).pressKeyCode(AndroidKeyCode.KEYCODE_POWER);
        } else {

        }
    }

    /**
     * Retrieves the AWS working directory from the environment variables.
     *
     * @return The value of the "WORKING_DIRECTORY" environment variable.
     */
    public static String getAwsWorkingDir() {
        return System.getenv("WORKING_DIRECTORY");
    }

    /**
     * Swipes to the next channel using the chevron icon.
     * The swipe is performed with a velocity of 2500.
     */
    public static void navigateToNextChannel() throws Throwable {
        MobileElement el = Utils.findElementByXPath("player_nextIcon");
        JavascriptExecutor js = (JavascriptExecutor) Utils.getDriver();
        Map<String, Object> params = new HashMap<>();
        params.put("direction", "right");
        params.put("velocity", 2500);
        params.put("element", ((RemoteWebElement) el).getId());
        js.executeScript("mobile: swipe", params);
    }

    /**
     * Performs a scrolling action from one point to another on the screen.
     */
    public static void scroll(int fromX, int fromY, int toX, int toY, int scrollCount, int swipeDuration, int sleep) throws Throwable {
        int count = 1;
        while (count <= scrollCount) {
            new TouchAction(Utils.getDriver())
                    .press(fromX, fromY)
                    .waitAction(Duration.ofMillis(swipeDuration))
                    .moveTo(toX, toY)
                    .release()
                    .perform();
            Thread.sleep(sleep);
            count++;
        }
    }

    public static void scrollIosViewer(int scrollCount, int sleep) throws Throwable {
        int count = 1;
        while (count <= scrollCount) {
            JavascriptExecutor js = (JavascriptExecutor) Utils.getDriver();
            HashMap<String, String> scrollObject = new HashMap<String, String>();
            scrollObject.put("direction", "right");
            js.executeScript("mobile: scroll", scrollObject);
            Thread.sleep(2000);
            count++;
        }
    }

    public static void scrollIosGuideRight(int scrollCount, int sleep) throws Throwable {
        int count = 1;
        RemoteWebElement element = Utils.findRemoteWebElement("guide_CollectionView");
        while (count <= scrollCount) {
            String elementID = element.getId();
            HashMap<String, String> scrollObject = new HashMap<String, String>();
            scrollObject.put("element", elementID); // Only for 'scroll in element'
            scrollObject.put("direction", "right");
            Utils.getDriver().executeScript("mobile:scroll", scrollObject);
            count++;
        }
    }


    /**
     * Swipes in the specified direction inside an object identified by its ID.
     *
     * @param id          The ID of the object to swipe inside.
     * @param scrollCount The number of times to perform the swipe.
     * @param direction   The direction to swipe (right, left, up, down).
     * @throws Exception If the swipe fails.
     */
    public static void swipeInsideObject(String id, int scrollCount, String direction) throws Exception {
        direction = direction.toLowerCase();
        List<String> validDirections = Arrays.asList("right", "left", "up", "down");

        if (!validDirections.contains(direction)) {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        for (int i = 0; i < scrollCount; i++) {
            try {
                RemoteWebElement element = (RemoteWebElement) Utils.getDriver().findElement(By.id(id));
                String elementID = element.getId();
                HashMap<String, String> scrollObject = new HashMap<>();
                scrollObject.put("element", elementID);
                scrollObject.put("direction", direction);
                Utils.getDriver().executeScript("mobile:scroll", scrollObject);
            } catch (Exception e) {
                System.err.println("Error swiping inside object: " + e.getMessage());
                throw e;
            }
        }
    }

    public static void scrollIosGuideLeft(int scrollCount, int sleep) throws Throwable {
        int count = 1;
        RemoteWebElement element = Utils.findRemoteWebElement("guide_CollectionView");
        while (count <= scrollCount) {
            String elementID = element.getId();
            HashMap<String, String> scrollObject = new HashMap<String, String>();
            scrollObject.put("element", elementID); // Only for 'scroll in element'
            scrollObject.put("direction", "left");
            Utils.getDriver().executeScript("mobile:scroll", scrollObject);
            count++;
        }
    }


    /**
     * Swipes inside an object in the specified direction.
     * The direction to swipe ("right", "left", "down", "up").
     */
    public static void swipeInsideObject(RemoteWebElement element, String direction) throws Exception {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }

        String elementID = element.getId();
        HashMap<String, String> scrollObject = new HashMap<>();
        scrollObject.put("element", elementID);

        direction = direction.toLowerCase();
        List<String> validDirections = Arrays.asList("right", "left", "up", "down");

        if (!validDirections.contains(direction)) {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        scrollObject.put("direction", direction);
        Utils.getDriver().executeScript("mobile:scroll", scrollObject);
    }


    /**
     * Taps on the given mobile element at its center.
     */
    public static void tapElement(MobileElement element) throws InterruptedException {
        try {
            if (element == null) {
                throw new IllegalArgumentException("Element cannot be null.");
            }

            int centerX = element.getCenter().getX();
            int centerY = element.getCenter().getY();

            new TouchAction<>(Utils.getDriver())
                    .tap(PointOption.point(centerX, centerY))
                    .perform();
        } catch (Exception e) {
            System.err.println("Error while tapping element: " + e.getMessage());
            throw e; // Rethrowing the exception for better debugging
        }
    }


   import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.time.Duration;
import java.util.Collections;

    /**
     * Taps 1/4 of the way down the screen, centered horizontally using W3C Actions API.
     */
    public static void tapAppScreen() {
        try {
            if (Utils.getDriver() == null) {
                throw new IllegalStateException("Driver is not initialized.");
            }

            Dimension screenSize = Utils.getDriver().manage().window().getSize();
            int x = screenSize.width / 2;
            int y = screenSize.height / 4;  // 1/4th of screen height

            System.out.println("Tapping at coordinates: x = " + x + ", y = " + y);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1)
                    .addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y))
                    .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                    .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            Utils.getDriver().perform(Collections.singletonList(tap));
        } catch (Exception e) {
            System.err.println("Error in tapAppScreen: " + e.getMessage());
            throw e;
        }
    }


    /**
     * Taps on the iOS PIP (Picture-in-Picture) window.
     */
    public static void iosTapPipWindow() {
        try {
            if (Utils.getDriver() == null) {
                throw new IllegalStateException("Driver is not initialized.");
            }

            Dimension screenSize = Utils.getDriver().manage().window().getSize();
            int x = (int) (screenSize.width * 4 / 5);
            int y = screenSize.height * 3 / 4;

            System.out.println("Tapping at coordinates: x = " + x + ", y = " + y);

            new TouchAction<>(Utils.getDriver())
                    .tap(PointOption.point(x, y))
                    .perform();
        } catch (Exception e) {
            System.err.println("Error in iosTapPipWindow: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Method to tap the Play/Pause button and show the overlay.
     */
    public static void playPauseButtonClick(int timeout) {
        System.out.println("Inside playPauseButtonClick()");

        MobileElement playPauseBtn = null;
        String id, idPlayPause;

        if (Utils.isAndroid()) {
            id = Utils.getId("loadingProgress_image");
            idPlayPause = Utils.getId("play_pause");
        } else {
            id = "player_stationIcon";  // iOS: Channel loading indicator
            idPlayPause = "player_pausePlayButton";
        }

        // Wait for progress indicator to disappear before interacting
        if (Utils.isElementInVisible(id, timeout) && Utils.isElementInVisible(idPlayPause, timeout)) {
            System.out.println("Loading icon is not visible. Tapping screen...");

            Utils.tapAppScreen();

            try {
                WebDriverWait wait = new WebDriverWait(Utils.getDriver(), Duration.ofSeconds(5));

                if (Utils.isAndroid()) {
                    playPauseBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idPlayPause)));
                } else {
                    int retry = 0;
                    while (retry < 3 && Utils.isElementInVisible(idPlayPause, 3)) {
                        System.out.println("Retrying tap, attempt: " + (retry + 1));
                        Utils.tapAppScreen();
                        retry++;
                    }
                    playPauseBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idPlayPause)));
                }

                if (playPauseBtn != null) {
                    playPauseBtn.click();
                } else {
                    System.out.println("Play/Pause button is still not found after retries.");
                }

            } catch (Exception ex) {
                System.out.println("Exception: Play/Pause button not found, retrying tap...");
                Utils.tapAppScreen();
                playPauseBtn = Utils.findElement("play_pause");

                if (playPauseBtn != null) {
                    playPauseBtn.click();
                } else {
                    System.out.println("Play/Pause button is still not found.");
                }
            }
        } else {
            System.out.println("Loading icon is still visible. Cannot tap the screen.");
        }
    }


    public static void playPauseButtonClickForAndroid(int timeout) {
        System.out.println("Inside playPauseButtonClickForAndroid()");

        MobileElement playPauseBtn = null;
        String id = Utils.getId("loadingProgress_image");
        String idPlayPause = Utils.getId("play_pause");

        /* Wait for progress image to become invisible, then tap screen */
        if (Utils.isElementInVisible(id, timeout)) {
            Utils.tapAppScreen();
            try {
                for (int i = 0; i < 4; i++) {  // Ensures up to 4 retries
                    System.out.println("Attempt " + (i + 1) + " to locate Play/Pause button.");
                    if (Utils.isElementVisible(idPlayPause, 3)) {
                        playPauseBtn = (MobileElement) Utils.getDriver().findElement(By.id(idPlayPause));
                        break; // Exit loop if found
                    }
                    Utils.tapAppScreen();  // Tap again if not found
                }

                if (playPauseBtn != null) {
                    playPauseBtn.click();
                } else {
                    System.out.println("Play/Pause button not found after retries.");
                }

            } catch (Exception ex) {
                System.out.println("Exception: Play/Pause button not found, retrying tap...");
                Utils.tapAppScreen();

                playPauseBtn = Utils.findElement("play_pause");
                if (playPauseBtn != null) {
                    playPauseBtn.click();
                } else {
                    System.out.println("Play/Pause button still not found.");
                }
            }
        } else {
            System.out.println("Loading icon is still visible... Can't tap the screen.");
        }
    }


    /*
     * Method performs tap at 1/4 of the screen
     */
    public static void clickBtvScreen() {
        Dimension size = Utils.getDriver().manage().window().getSize();
        int startx = size.width / 2;  // More readable
        int starty = size.height / 4;

        new TouchAction<>(Utils.getDriver())
                .tap(PointOption.point(startx, starty))
                .perform();
    }


    /*
     * Method clears text field for Android using Clear Text button and for iOS using keyboard delete button.
     */
    public static void clearTextField(MobileElement element) {
        try {
            if (Utils.isAndroid()) {
                // Tap near the right side of the element to trigger the Clear button
                int x = element.getLocation().getX() + (int) (element.getSize().getWidth() * 0.93);
                int y = element.getLocation().getY() + (element.getSize().getHeight() / 2);

                new TouchAction<>(Utils.getDriver())
                        .tap(PointOption.point(x, y))
                        .perform();
            } else {
                element.clear();  // iOS uses clear() method directly
            }
        } catch (Exception e) {
            System.out.println("Error clearing text field: " + e.getMessage());
            e.printStackTrace();
        }
    }

//_______________________________________

    public static boolean verifyElementAbsent(String locator) {
        WebDriver driver = Utils.getDriver();

        // Store the original implicit wait time
        int originalWait = driver.manage().timeouts().getImplicitWaitTimeout().toSecondsPart();

        try {
            // Temporarily disable implicit wait
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

            // Check for element
            if (!driver.findElements(By.id(locator)).isEmpty()) {
                System.out.println("Element Present");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error checking element presence: " + e.getMessage());
        } finally {
            // Restore original implicit wait time
            driver.manage().timeouts().implicitlyWait(originalWait, TimeUnit.SECONDS);
        }

        System.out.println("Element Absent");
        return true;
    }



    /*
     *  Method waits, when element becomes visible  for time == timeout
     */

    public static boolean isElementPresent(MobileElement element, int timeout) {//sec
        Utils.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            WebDriverWait wait = new WebDriverWait(Utils.getDriver(), timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
            System.out.println("elementPresent == true");
            return true;
        } catch (Exception e) {
            System.out.println("elementPresent == false");
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
            return false;
        } finally {
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
        }
    }


    /*
     *  Method waits, when element becomes invisible  for time == timeout
     */
    public static boolean isElementAbsent(MobileElement element, int timeout) {//sec
        boolean elementAbsent = false;
        long start_time = System.currentTimeMillis();
        long wait_time = timeout * 1000;
        long end_time = start_time + wait_time;
        System.out.println("elementAbsent == false");

        while (System.currentTimeMillis() < end_time) {
            if (isElementPresent(element, 2) == false) {
                elementAbsent = true;
                System.out.println("elementAbsent == true");
                break;
            }
        }
        return elementAbsent;
    }

    /*
     *  Method waits, when element becomes invisible  for time == timeout,
     *  using element id
     */

    public static boolean isElementInVisible(String id, int timeout) {//sec
        Utils.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            WebDriverWait wait = new WebDriverWait(Utils.getDriver(), timeout);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(id)));
            System.out.println("Element not visible - return true");
            return true;
        } catch (Exception e) {
            System.out.println("Element is visible - return false");
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
            return false;
        } finally {
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
        }
    }

    /*
     *  Method waits, when element becomes visible  for time == timeout (sec),
     *  using element id
     */

    public static boolean isElementVisible(String id, int timeout) {//sec
        Utils.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            WebDriverWait wait = new WebDriverWait(Utils.getDriver(), timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
            System.out.println("Element with id : " + id + " is visible");
            return true;
        } catch (Exception e) {
            System.out.println("Element with id : " + id + " is not visible");
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
            return false;
        } finally {
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
        }
    }

    /*
     *  Method waits, when element becomes visible  for time == timeout (sec),
     *  using class name
     */

    public static boolean isElementLocatedByClassNameVisible(String className, int timeout) {//sec
        Utils.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            WebDriverWait wait = new WebDriverWait(Utils.getDriver(), timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
            return true;
        } catch (Exception e) {
            System.out.println("Element with class name : " + className + " is not visible");
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
            return false;
        } finally {
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
        }
    }

    /*
     *  Method waits, when element becomes invisible  for time == timeout (sec),
     *  using xpath
     */

    public static boolean isElementLocatedByXPathInVisible(String xPath, int timeout) {//sec
        Utils.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            WebDriverWait wait = new WebDriverWait(Utils.getDriver(), timeout);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
            return true;
        } catch (Exception e) {
            System.out.println("Element with class name : " + xPath + " is not visible");
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
            return false;
        } finally {
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
        }
    }


    /*
     *  Method return true if text found on the page
     */
    public static boolean isTextFound(String id, String textToMatch, int timeout) {
        //Does implicut wait here need to be less than WebDriverWait or it needs to be 0?
        Utils.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            WebDriverWait wait = new WebDriverWait(Utils.getDriver(), 30);
            boolean found = wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id(id), textToMatch));
            return true;
        } catch (Exception e) {
            System.out.println(textToMatch + "is not found");
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
            return false;
        } finally {
            Utils.getDriver().manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
        }

    }


    public static String geElementTextFromList(List<MobileElement> list, int i) {
        String text = list.get(i).getText();
        return text;
    }


    /*
     *   Method set context to "NATIVE_APP" or "WEBVIEW_chrome"
     */
    public static void setContext(String contextValue) {
        String context = Utils.getDriver().getContext();
        if (!context.equalsIgnoreCase(contextValue)) {
            Utils.getDriver().context(contextValue);
        }
    }


    /*
     *  Method returns web page label  (FAQ, Neilsen Ratings, ...)
     */
    public static String getWebPageElementText(String id) {
        MobileElement el = null;
        Utils.setContext("NATIVE_APP");
        if (Utils.isAndroid()) {
            el = Utils.findElementNoPrefix("titleBar");
        } else {
            el = Utils.findElement(id);
        }
        return el.getText();

    }


    /*
     *  Method returns title from MyAccount page
     */
    public String getTitle(int timeout) {
        Utils.setContext("WEBVIEW_chrome");
        WebElement root = Utils.findWebElement("root");
        WebElement title = root.findElement(By.xpath(xConsts.APPLE_XPATHS.get("myAccount")));
        return title.getText();
    }


    /*
         Method returns screen state : ON or (DOZE or OFF)
         When power is ON output -->   "Display Power: state=ON"
         When power is OFF output -->   Display Power: state=DOZE  or OFF, depending on device
     */
    public static String getScreenState() {
        try {
            String outputString;
            String parameter = "Display Power: state=";
            Process p = Runtime.getRuntime().exec("adb shell dumpsys power | grep 'Power: state'");
            p.waitFor();

            DataInputStream curlIn = new DataInputStream(
                    p.getInputStream());
            StringBuffer response = new StringBuffer();
            while ((outputString = curlIn.readLine()) != null) {
                response.append(outputString);
            }
            Integer index = response.indexOf(parameter);
            String value = response.substring(index + parameter.length());
            return value;
        } catch (Exception e) {
            System.out.println(" Exception, running adb shell");
            return "exception";
        }
    }

    public static File takeScreenshot(String screenshotName) {
        String dir = System.getProperty("user.dir") + "/screenshots/";
        DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy__h.mm.ssaa");
        String destinationFile = dir + dateFormat.format(new Date()) + "_" + screenshotName + ".png";
        File scrFile = ((TakesScreenshot) Utils.getDriver()).getScreenshotAs(OutputType.FILE);

        try {
            File testScreenshot = new File(destinationFile);
            FileUtils.copyFile(scrFile, testScreenshot);
            System.out.println("Screenshot stored in " + testScreenshot.getAbsolutePath());

            return testScreenshot;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delete scheduled recording or recording with swipe
     **/
    public static void deleteRecordingWithSwipe(boolean isRecording) throws Throwable {
        MobileElement returnedElement = null;
        List<MobileElement> els = new ArrayList<MobileElement>();
        boolean found = false;

        if (Utils.isAndroid()) {
            MobileElement recordingsView = (MobileElement) Utils.getDriver().findElement(By.id("com.didja.btv.debug:id/recycler_view_recording"));
            els = recordingsView.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("viewGroup")));
        } else {
            MobileElement recordingsView = null;
            if (isRecording) {
                recordingsView = Utils.findElement("recordingsTable");
            } else {
                recordingsView = Utils.findElement("recordingSchedule_table");
            }
            els = recordingsView.findElements(By.className(xConsts.APPLE_CLASSNAMES.get("cellElement")));
        }
        returnedElement = els.get(0);

        Point pointStart, pointEnd;
        PointOption pointOptionStart, pointOptionEnd;
        int y = returnedElement.getLocation().getY() + returnedElement.getSize().height / 2;
        int x = returnedElement.getLocation().getX() + returnedElement.getSize().getWidth() / 20;
        int xEnd = (returnedElement.getLocation().getX() + returnedElement.getSize().getWidth()) * 5 / 6;
        pointOptionStart = PointOption.point(x, y);
        pointOptionEnd = PointOption.point(xEnd, y);
        try {
            if (Utils.isAndroid()) {
                new TouchAction(Utils.getDriver())
                        .press(pointOptionStart)
                        // a bit more reliable when we add small wait
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                        .moveTo(pointOptionEnd)
                        .release().perform();
            } else {
                new TouchAction(Utils.getDriver())
                        .press(pointOptionEnd)
                        // adding small wait
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                        .moveTo(pointOptionStart)
                        .release().perform();
            }
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
        }
    }


    /**
     * Use half swipe on ios recording on Recordings or Recording Schedule tab
     **/
    public static void halfSwipe(boolean isRecording) throws Throwable {
        MobileElement returnedElement = null;
        List<MobileElement> els = new ArrayList<MobileElement>();
        boolean found = false;
        MobileElement recordingsView = null;
        if (isRecording) {
            recordingsView = recordingsView = Utils.findElement("recordingsTable");
        } else {
            recordingsView = Utils.findElement("recordingSchedule_table");
        }
        els = recordingsView.findElements(By.className(xConsts.APPLE_CLASSNAMES.get("cellElement")));

        returnedElement = els.get(0);
        System.out.println(returnedElement.getText());

        Point pointStart, pointEnd;
        PointOption pointOptionStart, pointOptionEnd;
        int y = returnedElement.getLocation().getY() + returnedElement.getSize().height / 2;
        int x = (returnedElement.getLocation().getX() + returnedElement.getSize().getWidth()) / 2;
        //stsrt on the right
        int xStart = (returnedElement.getLocation().getX() + returnedElement.getSize().getWidth()) * 5 / 6;
        pointOptionEnd = PointOption.point(x, y);
        pointOptionStart = PointOption.point(xStart, y);
        try {

            new TouchAction(Utils.getDriver())
                    .press(pointOptionStart)
                    // adding small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
        }
    }


    /**
     * swipe  down for android
     **/
    public static void refreshAndroidRecordingTabWithSwipeDown() throws Throwable {
        MobileElement returnedElement = null;
        List<MobileElement> els = new ArrayList<MobileElement>();
        boolean found = false;

        MobileElement view = Utils.findElement("recycler_view_recording");
        Point pointStart, pointEnd;
        PointOption pointOptionStart, pointOptionEnd;
        int y = view.getLocation().getY() + view.getSize().height / 5;
        int x = view.getLocation().getX() + view.getSize().getWidth() / 2;
        int yEnd = view.getLocation().getY() + view.getSize().height / 2;
        pointOptionStart = PointOption.point(x, y);
        pointOptionEnd = PointOption.point(x, yEnd);
        try {
            new TouchAction(Utils.getDriver())
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
        }
    }

    /**
     * Performs a swipe-up gesture on the 'recycler_view_recording' element fpr
     * android platform.
     */
    public static void swipUp() throws Throwable {
        MobileElement view = Utils.findElement("recycler_view_recording");
        Point pointStart, pointEnd;
        PointOption pointOptionStart, pointOptionEnd;
        int y = view.getLocation().getY() + view.getSize().height / 5;  //5
        int x = view.getLocation().getX() + view.getSize().getWidth() / 2;
        int yEnd = view.getLocation().getY() + view.getSize().height / 2;
        pointOptionStart = PointOption.point(x, y);
        pointOptionEnd = PointOption.point(x, yEnd);
        try {
            new TouchAction(Utils.getDriver())
                    .press(pointOptionEnd)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                    .moveTo(pointOptionStart)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
        }
    }

    /**
     * Performs a swipe-up gesture on an Android device using specified coordinates.
     * <p>
     * This method simulates a swipe-up action by pressing at the end coordinates and moving to the start coordinates.
     * It utilizes Appium's TouchAction class to perform the gesture.
     */
    public static void swipUp(int x, int y, int xEnd, int yEnd) throws Throwable {
        PointOption pointOptionStart = PointOption.point(x, y);
        PointOption pointOptionEnd = PointOption.point(xEnd, yEnd);
        try {
            new TouchAction(Utils.getDriver())
                    .press(pointOptionEnd)
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                    .moveTo(pointOptionStart)
                    .release()
                    .perform();
        } catch (Exception e) {
            System.err.println("swipeUp(x, y, xEnd, yEnd): FAILED\n" + e.getMessage());
        }
    }


    /**
     * Method tap Undo to cancel record deletion on Recordings or Schedule Recording tab for android
     **/
    public static void tapUndo() {
        MobileElement undoButton = Utils.findElement("deleteRecording");
        undoButton.click();
    }


    /**
     * Checks if a recording with the specified title is present in the "Recordings"
     * or "Scheduled Recordings" tab on an Android device.
     * <p>
     * This method searches through the items displayed in a RecyclerView to find a recording
     * that matches the provided title.
     * It iterates over each child element within the RecyclerView, retrieves the title,
     * and compares it to the specified value.
     */
    public static boolean isRecordingFound(String value) {
        MobileElement returnedElement = null;
        boolean found = false;
        MobileElement recordingsView = null;
        recordingsView = Utils.findElement("recycler_view_recording");
        List<MobileElement> els = recordingsView.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("viewGroup")));
        for (MobileElement e : els) {
            // Retrieves the title of each recording item
            MobileElement title = (MobileElement) e.findElement(By.id(xConsts.ANDROID_IDS.get("infoDlg_showTitle")));
            String titleText = title.getText();
            if (titleText.contains(value)) {
                found = true;
                break;
            }
        }
        return found;
    }


    /**
     * Method returns true if android recording is found on first page Recordings or Schedule Recording tab
     * using recoring title and attributes
     **/
    public static boolean isRecordingFound(String value, String attributes) {
        MobileElement returnedElement = null;
        boolean found = false;
        System.out.println("Looking for : " + value);
        MobileElement recordingsView = Utils.findElement("recycler_view_recording");
        List<MobileElement> els = recordingsView.findElements(By.className(xConsts.ANDROID_CLASSNAMES.get("viewGroup")));
        for (MobileElement e : els) {
            MobileElement title = (MobileElement) e.findElement(By.id(xConsts.ANDROID_IDS.get("infoDlg_showTitle")));   //"com.didja.btv.debug:id/text_title"));
            String titleText = title.getText();
            System.out.println("recording title : " + titleText);
            if (titleText.contains(value)) {
                MobileElement foundAttributes = (MobileElement) e.findElement(By.id(xConsts.ANDROID_IDS.get("player_attributes")));    //"com.didja.btv.debug:id/text_attributes"));
                if (foundAttributes.getText().contains(attributes)) {
                    found = true;
                }
                if (found) {
                    break;
                }
            }
        }
        return found;
    }

    /**
     * Checks if an iOS recording is present in the Recordings tab based on the title.
     */
    public static boolean isIosRecordingFound(String value) {
        boolean found = false;
        System.out.println("Searching for recording with title: " + value);

        try {
            MobileElement recordingsView = Utils.findElement("recordingsTable");
            List<MobileElement> elements = recordingsView.findElements(By.className(xConsts.APPLE_CLASSNAMES.get("cellElement")));

            for (MobileElement element : elements) {
                // Retrieve the title of the recording
                MobileElement titleElement = element.findElement(By.name(xConsts.APPLE_CLASSNAMES.get("recording_row_titleLabel")));
                String titleText = titleElement.getText();
                System.out.println("Found recording title: " + titleText);

                // Check if the title matches the search value
                if (titleText.contains(value)) {
                    found = true;
                    break;
                }
            }
        } catch (NoSuchElementException e) {
            System.err.println("Element not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while searching for the recording: " + e.getMessage());
        }

        return found;
    }


    /**
     * Checks if an iOS recording is present in the Recordings tab based on the title and
     * attributes.
     */
    public static boolean isIosRecordingFound(String value, String attributes) {
        boolean found = false;
        System.out.println("Looking for recording with title: " + value);

        try {
            // Locate the recordings table
            MobileElement recordingsView = Utils.findElement("recordingsTable");
            List<MobileElement> els = recordingsView.findElements(By.className(xConsts.APPLE_CLASSNAMES.get("cellElement")));

            for (MobileElement e : els) {
                // Get the title of the recording
                MobileElement title = e.findElement(By.name(xConsts.APPLE_CLASSNAMES.get("recording_row_titleLabel")));
                String titleText = title.getText();
                System.out.println("Found recording title: " + titleText);

                if (titleText.contains(value)) {
                    System.out.println("Title match found.");

                    // Locate the attributes label using iOS Predicate String
                    String selector = "type == 'XCUIElementTypeStaticText' AND name == 'recording_row_attributesLabel'";
                    MobileElement attributesElement = e.findElement(MobileBy.iOSNsPredicateString(selector));
                    String foundAttributes = attributesElement.getAttribute("value");
                    System.out.println("Found attributes: " + foundAttributes + " | Looking for: " + attributes);

                    // Check if the attributes match
                    found = foundAttributes.contains(attributes);
                    if (found) {
                        System.out.println("Attributes match found.");
                        break;
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.err.println("Element not found: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred while searching for the recording: " + e.getMessage());
        }

        return found;
    }


}

