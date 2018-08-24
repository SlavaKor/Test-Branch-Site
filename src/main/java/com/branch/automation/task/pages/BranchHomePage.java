package com.branch.automation.task.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.branch.automation.task.utils.WaitUtils;

/**
 * Class describes branch.io home page
 */
public class BranchHomePage extends BasePage {

    public static final String TEAM_XPATH = "//a[@data-element-tag='team']";
    public static final String INTRODUCTION_XPATH = "//p[@class='copy']";
    public BranchHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Goes down to the home page find team link and navigates to Team page
     * @return {@link BranchTeamPage} - instance for the navigated page
     */
    public BranchTeamPage goToTeamPage() {
        WebElement teamLink = driver.findElement(By.xpath(TEAM_XPATH));
        teamLink.sendKeys(Keys.PAGE_DOWN);
        teamLink.click();
        LOGGER.info("Went down to the home page, clicked on team link");
        return new BranchTeamPage(driver);
    }

    /**
     * Override this wait method to wait for the slow loading element on the page -
     * introduction message
     */
    @Override
    protected void waitThePageToLoad() {
        WaitUtils.waitUntil(driver, ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath(INTRODUCTION_XPATH))));
    }
}
