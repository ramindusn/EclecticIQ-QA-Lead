package support;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utilities.DefaultProperties;
import utilities.DriverFactory;

public class Hooks {
    private static final Logger LOGGER = LogManager.getLogger(Hooks.class.getName());
    public Scenario scenario;
    public WebDriver driver;
    public DefaultProperties defaultProperties;

    /**
     * This method will run before every test.
     * This initialized the default.properties file.
     * Create the first webdriver instance.
     * Maximize the browser window.
     *
     * @param scenario
     */
    @Before
    public void createDriver(Scenario scenario) {
        this.scenario = scenario;
        defaultProperties = new DefaultProperties();
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        LOGGER.info("-------------------------------------------");
        LOGGER.info("TEST START - " + scenario.getName());
    }

    /**
     * This method will run after every test.
     * If a test step fails, this will print the current fail page screenshot and attached it to the cucumber report.
     *
     * @param scenario
     */
    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                scenario.attach(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png", "FailedAttachment");
                LOGGER.error("---TEST FAILED---");
            } else {
                LOGGER.info("---TEST PASSED---");
            }
            LOGGER.info("TEST END - " + scenario.getName());
            LOGGER.info("-------------------------------------------");
            DriverFactory.close();
        } catch (Exception e) {
            LOGGER.error("tearDown - " + e);
        }
    }
}