package com.branch.automation.task;

import com.branch.automation.task.data.Urls;
import com.branch.automation.task.pages.BranchTeamPage;
import com.branch.automation.task.pages.GoogleSearchHomePage;
import com.branch.automation.task.utils.WebDriverUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Tests the flow: search for url under test (branch.io), click that url
 * Scrolls the page down till found team link
 */
public class BranchTeamTests {
    private static final Logger LOGGER = Logger.getLogger(BranchTeamTests.class.getName());
    private static WebDriver driver;

    /**
     * Initialise web driver and navigates to google.com
     */
    @Before
    public void init() {
        driver = WebDriverUtils.initDriver();
        WebDriverUtils.navigateToURL(driver, Urls.GOOGLE_SEARCH.getUrl());
    }

    @Test
    public void compareTotalEmployeesNumberTest() {
        BranchTeamPage teamPage = searchAndNavigateToTeamPage();
        int employeesFromAllTab = teamPage.getEmployeesNumberFromAllTab();
        int employeesFromOtherTabs = teamPage.getEmployeesInOtherDepartments();

        Assert.assertEquals("The number of employees from All tab does not match with the total number from other tabs",
                employeesFromAllTab, employeesFromOtherTabs);
    }

    @Test
    public void compareTotalEmployeesNamesTest() {
        BranchTeamPage teamPage = searchAndNavigateToTeamPage();
        List<String> employeesNamesFromAllTab = teamPage.getEmployeesNamesFromAllTab();
        List<String> employeesNamesFromOtherTab = teamPage.getEmployeesNamesFromOthersTabs();

        Assert.assertEquals("The names of employees from All tab does not match with the names from other tabs",
                employeesNamesFromAllTab, employeesNamesFromOtherTab);
    }

    @Test
    public void compareEmployeesDepartmentTest() throws InterruptedException {
        BranchTeamPage teamPage = searchAndNavigateToTeamPage();

        Map<String, String> map = teamPage.getEmpWithDep();
        List<WebElement> departments = teamPage.getDepartments();
        String currentName;
        String currentDep;

        //go to each department except first and get every name and corresponding department
        for (int i = 1; i < departments.size(); i++) {
            departments.get(i).click();
            for (WebElement name : teamPage.getEmployeesNamesShownInPage()) {
                currentName = name.getAttribute("textContent");
                currentDep = teamPage.getEmployeeByName(currentName)
                        .findElement(By.xpath("following-sibling::h4")).getText();
                //compare current department for every employee with what we have in "all" map
                if (map.containsKey(currentName)){
                    Assert.assertEquals(name.getText() + " has incorrect department",
                            map.get(currentName), currentDep);
                }
                else {
                    LOGGER.info("Employee -" + name.getText() + " is not present in All tab");
                }
            }

        }
    }

    /**
     * Method loop through "all" array and add every name to the map with counter 1.
     * Then loop though "others" array and increment the counter if names already exist in map.
     * Finally check name's counters if any less than 2, it means the matching name wasn't found.
     * Log the names without match.
     * @param all - list of employees from all tab
     * @param others - list of employees from others tabs
     * @return {@link boolean} - returns true if all names are matching.
     */
    private boolean verifyEmployeesMatching(List<String> all, List<String> others) {
        Map<String,Integer> map = new HashMap<>();
        boolean hasMatch = true;
        for (String employeeName : all) {
            if (!map.containsKey(employeeName)) map.put(employeeName, 1);
        }
        for (String employeeName : others) {
            if (map.containsKey(employeeName)) map.put(employeeName, map.get(employeeName) + 1);
        }
        for (String employee : map.keySet()) {
            if (map.get(employee) < 2) {
                hasMatch = false;
                LOGGER.info("The employees was not found: " + employee);
            }
        }
        return hasMatch;
    }

    /**
     * Open Google Search and searches for branch.io, navigates to Branch home page,
     * goes to Team page.
     *
     * @return {@link BranchTeamPage} - returns instance of BranchTeamPage
     */
    private BranchTeamPage searchAndNavigateToTeamPage() {
        return new GoogleSearchHomePage(driver)
                .searchFor(Urls.BRANCH_IO.getUrl())
                .goToBranchHomePage()
                .goToTeamPage();
    }

    /**
     * Quit the driver
     */
    @After
    public void tearDown() {
        WebDriverUtils.tearDownBrowser(driver);
    }
}
