import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.BranchTeamPage;
import pages.GoogleSearchHomePage;
import utils.Urls;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Tests the flow: search for url under test (branch.io), click that url
 * Scrolls the page down till found team link
 */
public class BranchTeamTest {
    private static final Logger LOGGER = Logger.getLogger(BranchTeamTest.class.getName());
    private static WebDriver driver;

    /**
     * Initialise web driver and navigates to google.com
     */
    @Before
    public void init() {
        driver = WebDriverUtils.initDriver();
        WebDriverUtils.navigateToURL(driver, Urls.GOOGLE_SEARCH);
    }

    @Test
    public void compareTotalEmployeesNumberTest() {
        BranchTeamPage teamPage = searchAndNavigateToTeamPage();
        int employeesFromAllTab = teamPage.getEmployeesFromAllTab();
        int employeesFromOtherTabs = teamPage.getEmployeesInOtherDepartments();
        Assert.assertEquals("The number of employees from All tab does not match with the total number from other tabs",
                employeesFromAllTab, employeesFromOtherTabs);
    }

    @Test
    public void compareTotalEmployeesNamesTest() {
        BranchTeamPage teamPage = searchAndNavigateToTeamPage();
        ArrayList<String> employeesNamesFromAllTab = teamPage.getEmployeesNamesFromAllTab();
        ArrayList<String> employeesNamesFromOtherTab = teamPage.getEmployeesNamesFromOthersTabs();
        Assert.assertTrue("The names of employees from All tab does not match with the names from other tabs",
                verifyEmployeesMatching(employeesNamesFromAllTab, employeesNamesFromOtherTab));
    }

    private boolean verifyEmployeesMatching(ArrayList<String> all, ArrayList<String> others) {
        Map<String,Integer> map = new HashMap<>();
        boolean hasMatch = true;
        for (String employeeName : all) {
            if (!map.containsKey(employeeName)) map.put(employeeName, 1);
        }
        for (String employeeName : others) {
            if (map.containsKey(employeeName)) map.put(employeeName, map.get(employeeName) + 1);
        }
        for (String employee : map.keySet()) {
            if (map.get(employee) > 1) {
            }
            else {
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
                .searchFor(Urls.BRANCH_HOME)
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
