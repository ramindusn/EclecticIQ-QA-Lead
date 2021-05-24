package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    public static WebDriver driver;

    /**
     * Get the current running webdriver instance
     *
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            switch (DefaultProperties.readProperty("browserType")) {
                case "CHROME":
                    driver = getChromeDriver();
                    break;
                case "FIREFOX":
                    driver = getFireFoxDriver();
                    break;
            }
        }
        return driver;
    }

    /**
     * Create a Chrome driver instance
     *
     * @return WebDriver
     */
    private static WebDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver", DefaultProperties.readProperty("chromedriverPath"));
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("safebrowsing.enabled", "true");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.setHeadless(Boolean.parseBoolean(DefaultProperties.readProperty("headlessMode")));
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--disable-notifications");
        return new ChromeDriver(chromeOptions);
    }

    /**
     * Create a gecko driver instance
     *
     * @return WebDriver
     */
    private static WebDriver getFireFoxDriver() {
        System.setProperty("webdriver.gecko.driver", DefaultProperties.readProperty("geckodriverPath"));
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setAcceptUntrustedCertificates(true);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setHeadless(Boolean.parseBoolean(DefaultProperties.readProperty("headlessMode")));
        return new FirefoxDriver(firefoxOptions);
    }

    /**
     * Kill the driver instance
     */
    public static void close() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
