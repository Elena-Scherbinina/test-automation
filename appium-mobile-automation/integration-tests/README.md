
Summary
=======
This automation framework is designed to test the mobile application across different platforms.
The framework follows the Page Object Model (POM) and utilizes Appium. 
Automated scenarios can be executed on both Android and iOS, either locally or within a Jenkins pipeline.


Tech Stack
=============
• Programming Language: Java
• Test Automation Framework: Appium
• Design Pattern: Page Object Model (POM)
• Test Runner: Cucumber (BDD)
• Dependency Management: Maven
• Platforms Supported: Android, iOS


Page Object Model (POM) Implementation
========================================
This framework implements Page Object Model (POM) to maintain clean and reusable code.
The BasePage class initializes elements using Appium’s PageFactory.
A CommonComponentsPage class extends BasePage to include frequently used UI elements.
GuidePage, LivePage, RecordingsPage, and RecordingsSchedulePage extend 
CommonComponentsPage and add additional methods. Tests are written in Gherkin syntax inside .feature files.

Note on Appium 2 Migration:
============================
This framework was originally designed for Appium 1 and has been updated to work with Appium 2.
The old /wd/hub endpoints remain for backward compatibility, but Appium 2 does not require them.
To use the latest Appium 2 features, update the WebDriver URL accordingly if necessary.

Start Appium server
====================

     •  Start appium server from command prompt :
     appium -p <port number> -dc ./caps.json --chromedriver-executable ./chromedriver  --log-timestamp --tmp appium --log-level debug --log appium.log -ka 800 -pa /wd/hub
     where -p<port number> -   port to connect to Apium server
           --chromedriver-executable -   path to chromedriver executable
           --dc -   default desired capabilities unless overridden by received capabilities
           Example of caps.json file:
           {
            "app": "./build.apk",
            "platformName": "Android",
            "deviceName": "Pixel_6",
            "udid": "device_udid", 
            "platformVersion": "13",
            "isHeadless": true,
            "appium:chromeOptions":
            {"w3c" : false}
           }

           --log -   path to appium log file

    Note: The -pa /wd/hub option is included for backward compatibility with Appium 1. For Appium 2, 
    it is not required unless explicitly needed.



Running tests 
=============

    •   To run tests from command line :
    java -Dplatform=<platform> -Dport=<port number> -Dtag=@regression org.testng.TestNG -testjar didja-automation-1.0-SNAPSHOT-tests.jar -d ./test-output -verbose 10
         where :
                -Dplatform - platform to run - example:android (or ios )
                -Dport - appium port number (example : 5000)
                -Dtag - specify tags for tests to run
         Examples :
                -Dtag=@6,@8,@12   ------>  Runs tests with tags @6, @8, and @12  
                -Dtag=@smoke_test,@5,~@4   ------>  Runs tests with @smoke_test and @5 but excludes @4  
                -Dtag=@smoke_test,~@5,~@4   ------>  Runs tests with @smoke_test while excluding @5 and @4  
                -Dtag=~@smoke_test   ------>  Runs all tests except those tagged @smoke_test


         Note : "--tags @android or --tags @ios" is added to every tags combination to filter tests according to ios or android  platform.


    •   Results
	Reports are generated in xml format in the test-output/Cucumber.xml file.


Run tests on device farm
========================
To execute tests on a cloud-based device farm (such as AWS Device Farm), run aws_tests.py


Note
========================
This repository does not include the application under test, as it was a company-owned app (the company
is now closed). Additionally, not all page objects are included, as this repository is intended to showcase 
my automation logic and approach rather than serve as a fully functional test suite.


