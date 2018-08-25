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
public class BranchAllTeamTests {
    private static final Logger LOGGER = Logger.getLogger(BranchAllTeamTests.class.getName());
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
        Assert.assertEquals("The names of employees from All tab do not match with total names from departments",
                teamPage.getEmployeesNamesFromAllTab(), teamPage.getEmployeesNamesFromOthersTabs());
    }

    @Test
    public void namesFromDepartmentsArePresentInAllTest() {
        BranchTeamPage teamPage = searchAndNavigateToTeamPage();
        List<String> employeesNamesFromAllTab = teamPage.getEmployeesNamesFromAllTab();
        List<String> employeesNamesFromDeps = teamPage.getEmployeesNamesFromOthersTabs();
        Assert.assertTrue("Departments have extra names comparing to All tab",
                verifyNamesFromDepArePresentInAll(employeesNamesFromAllTab, employeesNamesFromDeps));
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
     * Add names from all to map, then check if names from departments are present in the map.
     * This test pass if map has extra names, but fails if departments have extra names
     * Log extra names.
     * @param namesFromAll - list of employees from all tab
     * @param namesFromDeps - list of employees from others tabs
     * @return {@link boolean} - returns true if names from departments are present in All tab
     */
    private boolean verifyNamesFromDepArePresentInAll(List<String> namesFromAll, List<String> namesFromDeps) {
        Map<String,Integer> map = new HashMap<>();
        boolean hasMatch = false;
        for (String name : namesFromAll) {
            if (!map.containsKey(name)) map.put(name, 1);
        }
        for (String name : namesFromDeps) {
            if (map.containsKey(name)) {
                hasMatch = true;
                map.put(name, map.get(name) + 1);
            }
            else {
                hasMatch = false;
            }
        }
        for (String employee : map.keySet()) {
            if (map.get(employee) < 2) {
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
