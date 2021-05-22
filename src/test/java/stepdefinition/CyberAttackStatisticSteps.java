package stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import pages.BasePage;
import pages.CyberAttackStatisticPage;

import java.util.List;

public class CyberAttackStatisticSteps {

    private static final Logger LOGGER = LogManager.getLogger(CyberAttackStatisticSteps.class.getName());
    private final CyberAttackStatisticPage cyberAttackStatisticPage;

    public CyberAttackStatisticSteps() {
        cyberAttackStatisticPage = new CyberAttackStatisticPage();
    }

    @Given("user should landed into Cyber Attack Statistics Page")
    public void userShouldLandedIntoCyberAttackStatisticsPage() {
        try {
            Assert.assertTrue(cyberAttackStatisticPage.isCyberAttackStatisticPageLoaded());
        } catch (AssertionError e) {
            LOGGER.error("Cyber Attack Statistic page is NOT loaded");
        }
    }

    @And("user see default sort option as {string}")
    public void userSeeDefaultSortOptionAs(String defaultOption) {
        try {
            Assert.assertTrue(cyberAttackStatisticPage.isDefaultValueCorrectInSortDDL(defaultOption));
        } catch (AssertionError e) {
            LOGGER.error("'" + defaultOption + "' is NOT loaded as default sort option in Sort drop down");
        }
    }

    @Then("user check the values in sort data dropdown list:")
    public void userCheckTheValuesInSortDataDropdownList(List<String> expectedSortingOptions) {
        try {
            Assert.assertTrue(cyberAttackStatisticPage.isSortOptionsAvailableInSortDDL(expectedSortingOptions));
        } catch (AssertionError e) {
            LOGGER.error("Expected sorting options are NOT loaded in sorting drop down");
        }
    }

    @And("user select {string} as the Sort option")
    public void userSelectAsTheSortOption(String chooseOption) {
        try {
            Assert.assertTrue(cyberAttackStatisticPage.isSelectSortOptionFromSortDDL(chooseOption));
        } catch (AssertionError e) {
            LOGGER.error("Could NOT select '" + chooseOption + "' from sorting drop down");
        }
    }

    @When("user enter a {string} in filter")
    public void userEnterANameInFilter(String searchText) {
        try {
            Assert.assertTrue(cyberAttackStatisticPage.isSetFilter(searchText));
        } catch (AssertionError e) {
            LOGGER.error("Could type '" + searchText + "' in filter data field");
        }
    }

    @Then("check {string} column is sorted in ascending order")
    public void checkColumnIsSortedInAscendingOrder(String columnName) {
        try {
            Assert.assertTrue(cyberAttackStatisticPage.isColumnSorted(columnName));
        } catch (AssertionError e) {
            LOGGER.error("'" + columnName + "' column is NOT sorted");
            Assert.fail("'" + columnName + "' column is NOT sorted");
        }
    }

    @Then("check filtered data is correct in {string} column")
    public void checkFilteredDataIsCorrectInColumn(String columnName) {
        try {
            Assert.assertTrue(cyberAttackStatisticPage.isFilteredDataCorrect(columnName));
        } catch (AssertionError e) {
            LOGGER.error("Filtered data is NOT correct in '" + columnName + "' column");
        }
    }
}
