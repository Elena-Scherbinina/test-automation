package stepdefs;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import pages.*;

import java.util.HashMap;
import java.util.Map;



public class RecordingScheduleStepDefinitions {
    private AppiumDriver driver;
    private String dateValue = "";
    private int numberOfRecords = 0;


    public RecordingScheduleStepDefinitions() {
        this.driver = Utils.getDriver();
    }

    @And("^I will see \"([^\"]*)\" button on Recording Schedule page$")
    public void see1(String name) throws Throwable {
        if (name.equalsIgnoreCase("Up")){
            //Up button is image (visible attribute == false)
            Assert.assertTrue(MyPages._recordingsPage.isUpEnabled());
        }else{
            Assert.assertTrue(MyPages._recordingsPage.isVisible(name));
        }
    }


    /* Navigate to Recording Schedule */
    @Then("^Recording Schedule is shown$")
    public void iSeeRecordingSchedule() throws Throwable {
       // doesn't work
        String title = MyPages._recordingSchedulePage.getTitle();
        System.out.println(title);

    }

    @Then("I close Recording Schedule$")
    public void iCloseRecordingSchedule() throws Throwable {
        MyPages._recordingSchedulePage.closeRecordingSchedule();
    }


    @Then("I swipe to cancel first recording from Recording Schedule tab$")
    public void iDelete() throws Throwable {
        Utils.deleteRecordingWithSwipe(false);
    }

    @Then("^I see \"Delete\" button on Recording Schedule tab$")
    public void I_see_delete_button_on_recording_schedule() throws Throwable {
        boolean isVisible = MyPages._recordingSchedulePage.isVisible("CANCEL_RECORDING");
        Assert.assertTrue(isVisible);
    }

    @Then("^I tap \"Delete\" button to remove recording on Recording Schedule tab$")
    public void I_tap_delete() throws Throwable {
        MyPages._recordingSchedulePage.tapCancelRecording();
        ;
    }

    @Then("I cancel \"([^\"]*)\" recording with half swipe from Recording Schedule tab$")
    public void ihalfSwipeToCancelFromRecordingSchedule(String recording) throws Throwable {
        Utils.halfSwipe(false);
    }

    @Then("I tap \"Undo\" button on Recording Schedule for android$")
    public void iTapUndo() throws Throwable {
        Utils.tapUndo();
    }

    @Then("I find \"Undo\" recording on \"Recording Schedule\" Tab$")
    public void iFoundUndoRecording() throws Throwable {
        ScheduledRecordingInfo info = MyPages._recordingSchedulePage.getScheduledRecordingInfo(0);
        boolean found = Utils.isRecordingFound(info.getTitle());
                //   boolean found = MyPages._recordingSchedulePage.isRecordingFound(info.getTitle());
        System.out.println(found);
        Assert.assertTrue(found);
    }

    @Then("I remember recording attributes for \"([^\"]*)\" scheduled recording$")
    public void iRememberRecordingInfoForScheduledRecording(String value) throws Throwable {
        switch (value.toUpperCase()) {
            case "FIRST":
                if (Utils.isAndroid()) {
                    MyPages._recordingSchedulePage.storeRecordingAttributes(0);
                }else{
                    MyPages._recordingSchedulePage.storeRecordingAttributesForIos(0);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid scheduled recording number : " + value);
        }
    }


    @Then("I dont see canceled recording on Recording Schedule tab$")
    public void iDontSeeScheduledRecording() throws Throwable {
        if (Utils.isAndroid()) {
            ScheduledRecordingInfo info = MyPages._recordingSchedulePage.getScheduledRecordingInfo(0);
            boolean found = Utils.isRecordingFound(info.getTitle());
            System.out.println(found);
            Assert.assertFalse(found);
        }else{
            ScheduledRecordingInfo info = MyPages._recordingSchedulePage.getScheduledRecordingInfo(0);
            boolean found = Utils.isIosScheduledRecordingFound(info.getTitle());
            System.out.println(found);
            Assert.assertFalse(found);
        }
    }

    @Then("there are no scheduled recordings in Recording Schedule$")
    public void noScheduledRecordings() throws Throwable {
        int number = MyPages._recordingSchedulePage.getNumberOfVisibleRecords();
        numberOfRecords = number;
    }



    @Then("I see a message \"([^\"]*)\" in Recording Schedule$")
    public void iSeeAMessage(String expectedMsg) throws Throwable {
        String generatedMsg = "";
      if (this.numberOfRecords == 0){
            generatedMsg = MyPages._recordingSchedulePage.getEmptyMessageText();
            Assert.assertEquals(generatedMsg, expectedMsg);
      }
    }

}
