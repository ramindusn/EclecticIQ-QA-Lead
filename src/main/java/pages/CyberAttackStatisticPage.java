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
import utilities.NumberSuffix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CyberAttackStatisticPage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(CyberAttackStatisticPage.class.getName());

    //Page element
    @FindBy(tagName = "h1")
    private WebElement pageHeader;

    @FindBy(id = "header-name")
    private WebElement nameColumnHeader;

    @FindBy(id = "header-cases")
    private WebElement numberOfCasesColumnHeader;

    @FindBy(id = "header-averageImpact")
    private WebElement impactScoreColumnHeader;

    @FindBy(id = "header-complexity")
    private WebElement complexityColumnHeader;

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
     * Validate given column header name is correct
     *
     * @param expectedColumnHeaderName
     * @return boolean
     */
    public boolean isColumnHeaderNameCorrect(String expectedColumnHeaderName) {
        boolean isNameCorrect = false;
        try {
            String actualColumnName = null;
            switch (expectedColumnHeaderName) {
                case "NAME":
                    actualColumnName = nameColumnHeader.getText();
                    break;
                case "NUMBER OF CASES":
                    actualColumnName = numberOfCasesColumnHeader.getText();
                    break;
                case "AVERAGE IMPACT SCORE":
                    actualColumnName = impactScoreColumnHeader.getText();
                    break;
                case "COMPLEXITY":
                    actualColumnName = complexityColumnHeader.getText();
                    break;
                default:
                    LOGGER.error("'" + expectedColumnHeaderName + "' - expected column Name NOT found");
            }
            if (actualColumnName != null) {
                if (actualColumnName.equals(expectedColumnHeaderName)) {
                    isNameCorrect = true;
                }
            }
        } catch (RuntimeException e) {
            LOGGER.error("isCyberAttackStatisticPageLoaded - " + e);
        }
        return isNameCorrect;
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
     * Validate sort options available in sort drop down list
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
            isCheck = isListEqualsIgnoreOrder(actualOptionsList, expectedList);
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
            List<WebElement> cellElements = driver.findElements(By.xpath("//div[@class='table-data data-" + columnName.toLowerCase() + "']"));
            isDataCorrect = isContains(cellElements, enteredText);
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
            List<WebElement> cellElements;
            switch (columnName) {
                case "Name":
                    cellElements = driver.findElements(By.xpath("//div[@class='table-data data-name']"));
                    isInOrder = isSorted(cellElements);
                    break;
                case "Number of cases":
                    cellElements = driver.findElements(By.xpath("//div[@class='table-data data-cases']"));
                    isInOrder = isNumberOfCasesSorted(cellElements);
                    break;
                case "Impact score":
                    cellElements = driver.findElements(By.xpath("//div[@class='table-data data-averageImpact']"));
                    isInOrder = isImpactScoreSorted(cellElements);
                    break;
                case "Complexity":
                    cellElements = driver.findElements(By.xpath("//div[@class='table-data data-complexity']"));
                    isInOrder = isComplexitySorted(cellElements);
                    break;
                default:
                    LOGGER.error(columnName + " - this column name NOT found");
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
     * @return boolean
     */
    public boolean isNumberOfCasesSorted(List<WebElement> elementList) {
        boolean isInOrder = false;
        try {
            List<Double> dataList = new ArrayList<>();
            for (WebElement ele : elementList) {
                StringBuilder stringBuilder = new StringBuilder(ele.getText());
                double number = 0.0;
                if (ele.getText().contains("k")) {
                    number = Double.parseDouble(stringBuilder.deleteCharAt(ele.getText().length() - 1).toString()) * NumberSuffix.K.getValue();
                } else if (ele.getText().contains("M")) {
                    number = Double.parseDouble(stringBuilder.deleteCharAt(ele.getText().length() - 1).toString()) * NumberSuffix.M.getValue();
                } else if (ele.getText().contains("B")) {
                    number = Double.parseDouble(stringBuilder.deleteCharAt(ele.getText().length() - 1).toString()) * NumberSuffix.B.getValue();
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
     * Validate Impact Score column is Sorted
     *
     * @param elementList
     * @return boolean
     */
    public boolean isImpactScoreSorted(List<WebElement> elementList) {
        boolean isSorted = false;
        try {
            List<Double> dataList = new ArrayList<>();
            for (WebElement ele : elementList) {
                dataList.add(Double.parseDouble(ele.getText()));
            }
            isSorted = Ordering.natural().isOrdered(dataList);
            LOGGER.debug("Actual Order - " + dataList);
            Collections.sort(dataList);
            LOGGER.debug("Expected Order- " + dataList);
        } catch (RuntimeException e) {
            LOGGER.error("isImpactScoreSorted - " + e);
        }
        return isSorted;
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
