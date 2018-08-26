package com.branch.automation.task;

import com.branch.automation.task.data.Urls;
import com.branch.automation.task.pages.BranchHomePage;
import com.branch.automation.task.pages.WebinarPage;
import com.branch.automation.task.utils.WebDriverUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

/**
 * Created by Slava on 8/24/18.
 */
public class BranchSearchTest {
    private static final String RESOURCES_TITLE = "Branch Resource Hub";
    private static final String FIRST_NAME = "Iaroslava";
    private static final String LAST_NAME = "Kornachevska";
    private static final String EMAIL = "test@gmail.com";
    private static final String COMPANY = "";
    private static final String THANKS_MESSAGE = "Thanks for your interest in:Debugging and Testing Webinar";
    private static final String SEARCH_ARG = "Debugging and Testing Webinar";
    private static final String SEARCH_RESULT_PAGE =
            "https://branch.io/resources/webinar/?res=Debugging+and+Testing+Webinar";
    private static WebDriver driver;

    /**
     * Initialise web driver and navigates to google.com
     */
    @Before
    public void init() {
        driver = WebDriverUtils.initDriver();
        WebDriverUtils.navigateToURL(driver, Urls.BRANCH_IO.getUrl());
    }

    @Test
    public void verifyTitleOnResourcesTest() {
        String actualTitle = new BranchHomePage(driver)
                .goToResourcesPage().getResourcesTitle();
        Assert.assertEquals("The title on Resource page doesn't match", RESOURCES_TITLE, actualTitle);

    }

    @Test
    public void verifySearchAndNavigateToResultTest() {
        new BranchHomePage(driver)
                .goToResourcesPage().searchFor(SEARCH_ARG);
        Assert.assertEquals("You didn't manage to navigate to searched page",
                SEARCH_RESULT_PAGE, driver.getCurrentUrl());
    }

    // Intentionally making this test fail by leaving required company input filed empty
    @Test
    public void fillOutWebinarFormShouldFailTest() {
        WebinarPage webinarPage = new BranchHomePage(driver)
                .goToResourcesPage()
                .searchFor(SEARCH_ARG)
                .submitWebinarForm(FIRST_NAME, LAST_NAME, EMAIL, COMPANY);
        Assert.assertEquals("You didn't manage to submit webinar form",
                THANKS_MESSAGE, webinarPage.getThanksMessage());
    }

    /**
     * Quit the driver
     */
    @After
    public void tearDown() {
        WebDriverUtils.tearDownBrowser(driver);
    }
}
