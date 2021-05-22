package helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementHandler {
    private WebDriver driver;

    public ElementHandler(WebDriver webDriver) {
        driver = webDriver;
    }

    /**
     * Checking the current URL contains a given string
     *
     * @param expectedString
     * @param timeout
     * @return boolean
     */
    public boolean waitForUrlContains(String expectedString, int timeout) {
        boolean isCheck = false;
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        ExpectedCondition<Boolean> urlIsCorrect = arg0 -> driver.getCurrentUrl().contains(expectedString);
        if (wait.until(urlIsCorrect)) {
            isCheck = true;
        }
        return isCheck;
    }
}
