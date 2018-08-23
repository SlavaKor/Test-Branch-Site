import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.BranchTeamPage;
import pages.GoogleSearchHomePage;
import utils.Urls;
import utils.WebDriverUtils;

/**
 * Tests the flow: search for url under test (branch.io), click that url
 * Scrolls the page down till found team link
 */
public class BranchTeamTest {
    public static final String EXPECTED_URL = "https://branch.io/";
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
    public void compareTotalEmployeesNumberTest() throws InterruptedException {
        BranchTeamPage teamPage = searchAndNavigateToTeamPage();
        int employeesFromAllTab = teamPage.getEmployeesFromAllTab();
        int employeesFromOtherTabs = teamPage.getEmployeesInOtherDepartments();
        Assert.assertEquals("The number of employees from All tab does not match with the total number from other tabs",
                employeesFromAllTab, employeesFromOtherTabs);
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
