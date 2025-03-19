
package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


@CucumberOptions(
        features = "classpath:features/",
        glue = {"stepdefs"},
        tags = {"@regression"},
        plugin = {
                "pretty",
                "html:test-output/cucumber-pretty",
                "json:test-output/CucumberTestReport.json",
                "junit:test-output/Cucumber.xml"
        }
)
public class
TestRunner {
    private TestNGCucumberRunner testNGCucumberRunner;
    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    /**
     * Method set cucumber.options with System.setProperty("cucumber.options", value)
     **/
    @BeforeClass(alwaysRun = true)
    public void setUpClass() throws Exception {
        logger.info("Setting up TestRunner...");
        String tagsToExclude = "";    //tags with ~
        String tagsToExecute = "";   //tags to add for running tests : example:@4,@5,@10
        String tags = "", resultTag = "";
        String platform = System.getProperty("platform").toLowerCase();  //tag @android or tag @ios
        String tag = System.getProperty("tag");
        if (tag != null) {
            int index = tag.indexOf('~');
            if (index >= 0) {
                String params[] = tag.split(",");
                for (int i = 0; i < params.length; i++) {
                    if (params[i].charAt(0) == '~') {
                        tagsToExclude += "--tags " + params[i] + " ";
                    } else {
                        tagsToExecute += params[i] + ",";
                    }
                }
                if (tagsToExecute.length() > 0) {
                    tags = "--tags " + tagsToExecute.substring(0, tagsToExecute.length() - 1) + " " + tagsToExclude.trim();
                } else {
                    tags = tagsToExclude.trim();
                }
                resultTag = tags + " --tags @" + platform + "";
            } else {
                resultTag = "--tags " + tag + " --tags @" + platform + "";
            }
            logger.info("Cucumber options: {}", resultTag);
            System.setProperty("cucumber.options", resultTag);
        }
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        try {
            testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
        } catch (Exception e) {
            logger.error("Exception occurred within TestRunner: {}", e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
    }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        logger.info("Tearing down TestRunner...");
        testNGCucumberRunner.finish();
    }
}
