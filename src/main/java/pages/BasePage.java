package pages;

import com.google.common.collect.Ordering;
import helper.ElementHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import utilities.DriverFactory;

import java.util.*;

/**
 * Commonly used variables and methods are listed under the Base(Parent class),
 * so that it can be used in all the Page classes
 */
public class BasePage {
    public static final int TIMEOUT_ONE = 10;
    public static final int TIMEOUT_TWO = 5;
    private static final Logger LOGGER = LogManager.getLogger(BasePage.class.getName());
    public AjaxElementLocatorFactory ajaxElementLocatorFactory;
    public WebDriver driver;
    public ElementHandler elementHandler;

    public BasePage() {
        driver = DriverFactory.getDriver();
        ajaxElementLocatorFactory = new AjaxElementLocatorFactory(driver, TIMEOUT_ONE);
        elementHandler = new ElementHandler(driver);
    }

    /**
     * Validate given list of elements text is sorted
     *
     * @param elementList
     * @return boolean
     */
    public boolean isSorted(List<WebElement> elementList) {
        boolean isInOrder = false;
        try {
            List<String> dataList = new ArrayList<>();
            for (WebElement ele : elementList) {
                dataList.add(ele.getText());
            }
            isInOrder = Ordering.natural().isOrdered(dataList);
            LOGGER.debug("Actual Order - " + dataList);
            Collections.sort(dataList);
            LOGGER.debug("Expected Order- " + dataList);
        } catch (RuntimeException e) {
            LOGGER.error("isSorted - " + e);
        }
        return isInOrder;
    }

    /**
     * Validate given search text is available in all list items
     *
     * @param elementList
     * @param searchText
     * @return boolean
     */
    public boolean isContains(List<WebElement> elementList, String searchText) {
        try {
            String value = null;
            for (WebElement ele : elementList) {
                value = ele.getText().toLowerCase();
                if (!value.contains(searchText.toLowerCase())) {
                    LOGGER.error("Filtered value - " + searchText);
                    LOGGER.error(value + " - This value doesn't match the filter");
                    return false;
                }
            }
        } catch (RuntimeException e) {
            LOGGER.error("isContains - " + e);
        }
        return true;
    }

    /**
     * Check given data in two list are matched
     *
     * @param list1
     * @param list2
     * @param <T>
     * @return boolean
     */
    public <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }
}
