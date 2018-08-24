package com.branch.automation.task.pages;

import com.branch.automation.task.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Class describes branch.io home page
 */
public class BranchHomePage extends BasePage {

    public static final String INTRODUCTION_XPATH = "//p[@class='copy']";

    @FindBy(xpath = "//a[@data-element-tag='team']")
    private WebElement teamLink;

    @FindBy(xpath = "//a[@class='root-link' and @href='/resources/']")
    private WebElement resourcesLink;

    public BranchHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Goes down to the bottom of home page find team link and navigates to Team page
     * @return {@link BranchTeamPage} - instance for the navigated page
     */
    public BranchTeamPage goToTeamPage() {
        teamLink.sendKeys(Keys.PAGE_DOWN);
        teamLink.click();
        LOGGER.info("Went down to the home page, clicked on team link");
        return new BranchTeamPage(driver);
    }

    /**
     * Goes down to the home page find team link and navigates to Resources page
     * @return {@link BranchResourcesPage} - instance for the navigated page
     */
    public BranchResourcesPage goToResourcesPage() {
        resourcesLink.click();
        LOGGER.info("Navigating to resources page");
        return new BranchResourcesPage(driver);
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
