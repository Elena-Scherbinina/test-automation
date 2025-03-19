package stepdefs;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.MyPages;


import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HooksService {
    private static AppiumDriver driver;
    public static String platform = "";
    public static String id = "";
    public static String port = "";
    public static String deviceName = "";
    public static String osVersion = "";
    public static boolean aws = false;

    @Before
    public void setUpAppium() throws Exception  //throws MalformedURLException
    {
        platform = System.getProperty("platform");
        port = System.getProperty("port");
        aws = Boolean.parseBoolean(Utils.readProperty("run.aws"));

        if (platform == null || platform.equalsIgnoreCase("")) {
            System.out.println("Platform parameter is not set.");
            System.exit(-1);
        }

        if (port == null || port.equalsIgnoreCase("")) {
            System.out.println("Port parameter is not set.");
            System.exit(-1);
        }


        DesiredCapabilities cap = new DesiredCapabilities();
        String appiumUrl = "http://0.0.0.0:" + port + "/wd/hub";
        /**
         * Configures and initializes the Appium driver for AWS cloud execution.
         * */
        if (aws) {
            if (!Utils.isAndroid()) {
                cap.setCapability("autoGrantPermissions", true);
                cap.setCapability("noReset", false);
                driver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
            } else {
                cap.setCapability("appPackage", xConsts.BTV_BUNDLE_ID);
                cap.setCapability("appActivity", xConsts.BTV_BUNDLE_ID + ".activity.StartupActivity");
                driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
            }
        } else {
            /**
             * Configures and initializes the Appium driver for local execution.
             * It retries driver initialization up to 5 times in case of failures.
             */
            Utils.setCapabilities(platform, cap);

            int retry = 5;
            while (retry > 0 && driver == null) {
                try {
                    driver = Utils.isAndroid() ?
                            new AndroidDriver<>(new URL(appiumUrl), cap) :
                            new IOSDriver<>(new URL(appiumUrl), cap);
                } catch (Exception e) {
                    retry--;
                    System.err.println("Retry " + retry + ": Waiting 10 seconds before retrying...");
                    e.printStackTrace();
                    TimeUnit.SECONDS.sleep(10);
                }
            }
        }
        // Set implicit wait time
        driver.manage().timeouts().implicitlyWait(xConsts.BTV_EMPLICIT_WAIT, TimeUnit.SECONDS);
    }
}


@After
public void tearDown() {
    cleanUpPages();

    if (driver != null) {
        driver.quit();
        driver = null;
    }

    if (Utils.isAndroid()) {
        try {
            Runtime.getRuntime().exec("adb shell pm clear " + xConsts.BTV_BUNDLE_ID);
        } catch (IOException e) {
            System.err.println("Failed to clear app data: " + e.getMessage());
        }
    }
}

/**
 * Resets all page objects to null.
 */
private void cleanUpPages() {
    MyPages._recordingsPage = null;
    MyPages._guidePage = null;
    MyPages._livePage = null;
    MyPages._recordingSchedulePage = null;
    MyPages._signUpScreen2Page = null;
    MyPages._signUpScreen1Page = null;
    MyPages._fullScreenPage = null;
    MyPages._infoDialog = null;
    MyPages._myAccountPage = null;
}

/**
 * Captures and embeds a screenshot if the scenario fails.
 **/
public void embedScreenshot(Scenario scenario) {
    if (scenario.isFailed()) {
        System.out.println("Scenario '" + scenario.getName() + "' failed.");

        if (driver != null) {
            if (!takeScreenshot(scenario.getName())) {
                System.err.println("Failed to capture screenshot for scenario: " + scenario.getName());
            }
        } else {
            System.err.println("Driver is null. Cannot take a screenshot.");
        }
    }
}

/**
 * Takes a screenshot and saves it to the working directory.
 **/
public void takeScreenshot(String screenshotName) {
    if (driver == null) {
        System.err.println("Driver is null. Cannot take a screenshot.");
        return;
    }

    try {
        File screenshotDirectory = new File(System.getenv("WORKING_DIRECTORY"));
        if (!screenshotDirectory.exists()) {
            screenshotDirectory.mkdirs();  // Ensure the directory exists
        }

        File destinationFile = new File(screenshotDirectory, screenshotName + ".png");

        // Capture screenshot
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(srcFile, destinationFile);

        System.out.println("Screenshot saved: " + destinationFile.getAbsolutePath());
    } catch (IOException e) {
        System.err.println("Failed to take screenshot: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        System.err.println("Unexpected error while taking a screenshot: " + e.getMessage());
        e.printStackTrace();
    }
}

/**
 * Returns the current instance of the Appium driver.
 **/
public static AppiumDriver getDriver() {
    return driver;
}
}
