package pages;

import com.google.common.collect.Ordering;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utilities.DefaultProperties;
import utilities.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CyberAttackStatisticPage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(CyberAttackStatisticPage.class.getName());

    //Page element
    @FindBy(tagName = "h1")
    private WebElement pageHeader;

    @FindBy(id = "filter-input")
    private WebElement filterField;

    @FindBy(id = "sort-select")
    private WebElement sortDropDown;

    public CyberAttackStatisticPage() {
        driver.get(DefaultProperties.readProperty("url"));
        //Initializing the page elements
        PageFactory.initElements(ajaxElementLocatorFactory, this);
    }

    /**
     * This method validate Cyber Attack Statistic page is loaded
     *
     * @return boolean
     */
    public boolean isCyberAttackStatisticPageLoaded() {
        boolean isLoaded = false;
        try {
            if (elementHandler.waitForUrlContains("mystifying-beaver", TIMEOUT_TWO)) {
                isLoaded = pageHeader.isDisplayed();
            }
        } catch (RuntimeException e) {
            LOGGER.error("isCyberAttackStatisticPageLoaded - " + e);
        }
        return isLoaded;
    }

    /**
     * Type search text in the filter data
     *
     * @param searchText
     * @return boolean
     */
    public boolean isSetFilter(String searchText) {
        boolean isSet = false;
        try {
            filterField.sendKeys(searchText);
            isSet = true;
        } catch (Exception e) {
            LOGGER.error("isSetFilter - " + e);
        }
        return isSet;
    }

    /**
     * Validate default value is correct in Sort dropdown list
     *
     * @param expectedName
     * @return boolean
     */
    public boolean isDefaultValueCorrectInSortDDL(String expectedName) {
        boolean isClicked = false;
        try {
            Select sortOptions = new Select(sortDropDown);
            if (sortOptions.getFirstSelectedOption().getText().equalsIgnoreCase(expectedName)) {
                isClicked = true;
            }
        } catch (RuntimeException e) {
            LOGGER.error("isDefaultValueCorrectInSortDDL - " + e);
        }
        return isClicked;
    }

    /**
     * Validate sort options avaible in sort drop down list
     *
     * @param expectedList
     * @return boolean
     */
    public boolean isSortOptionsAvailableInSortDDL(List<String> expectedList) {
        boolean isCheck = false;
        try {
            Select sortOptions = new Select(sortDropDown);
            List<String> actualOptionsList = new ArrayList<>();
            for (WebElement ele : sortOptions.getOptions()) {
                actualOptionsList.add(ele.getText());
            }
            LOGGER.debug("Actual sorting options - " + actualOptionsList);
            LOGGER.debug("Expected sorting options - " + expectedList);
            isCheck = listEqualsIgnoreOrder(actualOptionsList, expectedList);
        } catch (RuntimeException e) {
            LOGGER.error("isSortOptionsAvailableInSortDDL - " + e);
        }
        return isCheck;
    }

    /**
     * Select a sort option from drop down
     *
     * @param chooseOption
     * @return boolean
     */
    public boolean isSelectSortOptionFromSortDDL(String chooseOption) {
        boolean isSelect = false;
        try {
            Select sortOptions = new Select(sortDropDown);
            sortOptions.selectByVisibleText(chooseOption);
            isSelect = true;
        } catch (RuntimeException e) {
            LOGGER.error("isSelectSortOptionFromSortDDL - " + e);
        }
        return isSelect;
    }

    /**
     * Validate filtered data is correct
     *
     * @param columnName
     * @return boolean
     */
    public boolean isFilteredDataCorrect(String columnName) {
        boolean isDataCorrect = false;
        try {
            String enteredText = filterField.getAttribute("value");
            boolean isEleClassName = true;
            String eleClassName = null;
            switch (columnName) {
                case "Name":
                    eleClassName = "name";
                    break;
                case "Complexity":
                    eleClassName = "complexity";
                    break;
                default:
                    isEleClassName = false;
                    LOGGER.error(columnName + " - this column name NOT found");
            }
            if (isEleClassName) {
                List<WebElement> cellElements = driver.findElements(By.xpath("//div[@class='table-data data-" + eleClassName + "']"));
                isDataCorrect = isContains(cellElements, enteredText);
            }
        } catch (RuntimeException e) {
            LOGGER.error("isFilteredDataCorrect - " + e);
        }
        return isDataCorrect;
    }

    /**
     * Validate given column data is sorted
     *
     * @param columnName
     * @return boolean
     */
    public boolean isColumnSorted(String columnName) {
        boolean isInOrder = false;
        try {
            boolean isEleClassName = true;
            String eleClassName = null;
            switch (columnName) {
                case "Name":
                    eleClassName = "name";
                    break;
                case "Number of cases":
                    eleClassName = "cases";
                    break;
                case "Impact score":
                    eleClassName = "averageImpact";
                    break;
                case "Complexity":
                    eleClassName = "complexity";
                    break;
                default:
                    isEleClassName = false;
                    LOGGER.error(columnName + " - this column name NOT found");
            }
            if (isEleClassName) {
                if (columnName.equalsIgnoreCase("Number of cases")) {
                    List<WebElement> cellElements = driver.findElements(By.xpath("//div[@class='table-data data-" + eleClassName + "']"));
                    isInOrder = isNumberOfCasesSorted(cellElements);
                } else if (columnName.equalsIgnoreCase("Complexity")) {
                    List<WebElement> cellElements = driver.findElements(By.xpath("//div[@class='table-data data-" + eleClassName + "']"));
                    isInOrder = isComplexitySorted(cellElements);
                } else {
                    List<WebElement> cellElements = driver.findElements(By.xpath("//div[@class='table-data data-" + eleClassName + "']"));
                    isInOrder = isSorted(cellElements);
                }
            }
        } catch (RuntimeException e) {
            LOGGER.error("isColumnSorted - " + e);
        }
        return isInOrder;
    }

    /**
     * Validate number of cases column data is sorted
     *
     * @param elementList
     * @return
     */
    public boolean isNumberOfCasesSorted(List<WebElement> elementList) {
        boolean isInOrder = false;
        try {
            List<Double> dataList = new ArrayList<>();
            for (WebElement ele : elementList) {
                StringBuilder stringBuilder = new StringBuilder(ele.getText());
                double number = 0.0;
                if (ele.getText().contains("k")) {
                    number = Double.parseDouble(stringBuilder.deleteCharAt(ele.getText().length() - 1).toString()) * 100000;
                } else if (ele.getText().contains("M")) {
                    number = Double.parseDouble(stringBuilder.deleteCharAt(ele.getText().length() - 1).toString()) * 1000000;
                } else if (ele.getText().contains("B")) {
                    number = Double.parseDouble(stringBuilder.deleteCharAt(ele.getText().length() - 1).toString()) * 1000000000;
                } else {
                    number = Double.parseDouble(ele.getText());
                }
                dataList.add(number);
            }
            isInOrder = Ordering.natural().isOrdered(dataList);
            LOGGER.debug("Actual Order- " + dataList);
            Collections.sort(dataList);
            LOGGER.debug("Expected Order- " + dataList);
        } catch (RuntimeException e) {
            LOGGER.error("isNumberOfCasesSorted - " + e);
        }
        return isInOrder;
    }

    /**
     * Validate complexity column data is sorted
     *
     * @param elementList
     * @return boolean
     */
    public boolean isComplexitySorted(List<WebElement> elementList) {
        boolean isInOrder = false;
        try {
            List<String> dataList = new ArrayList<>();
            List<Integer> orderList = new ArrayList<>();
            for (WebElement ele : elementList) {
                if (ele.getText().equalsIgnoreCase(Level.LOW.name())) {
                    orderList.add(1);
                } else if (ele.getText().equalsIgnoreCase(Level.MEDIUM.name())) {
                    orderList.add(2);
                } else if (ele.getText().equalsIgnoreCase(Level.HIGH.name())) {
                    orderList.add(3);
                } else {
                    LOGGER.error(ele.getText() + " - this Complexity type NOT found");
                    break;
                }
                dataList.add(ele.getText());
            }
            isInOrder = Ordering.natural().isOrdered(orderList);
            LOGGER.debug("1-low, 2-medium, 3-high");
            LOGGER.debug("Expect- " + orderList);
            LOGGER.debug("Actual Order- " + dataList);
        } catch (RuntimeException e) {
            LOGGER.error("isComplexitySorted - " + e);
        }
        return isInOrder;
    }
}
