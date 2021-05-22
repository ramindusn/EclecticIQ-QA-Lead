package helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementHandler {
    private WebDriver driver;

    public ElementHandler(WebDriver webDriver) {
        driver = webDriver;
    }

    /**
     * Checking that an element is present on the DOM of a page and visible.
     *
     * @param locator
     * @param timeout
     * @return WebElement
     */
    public WebElement visibilityOfElementLocated(By locator, int timeout) {
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element;
    }

    /**
     * Checking that an element is present on the DOM of a page.
     *
     * @param locator
     * @param timeout
     * @return WebElement
     */
    public WebElement presenceOfElementLocated(By locator, int timeout) {
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element;
    }

    /**
     * Checking an element is visible and enabled such that you can click it.
     *
     * @param locator
     * @param timeout
     * @return WebElement
     */
    public WebElement elementToBeClickable(By locator, int timeout) {
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        return element;
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
