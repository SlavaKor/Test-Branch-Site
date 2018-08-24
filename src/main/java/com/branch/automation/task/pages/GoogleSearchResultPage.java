package com.branch.automation.task.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.branch.automation.task.utils.WaitUtils;

/**
 * Class for Google Search results
 */
public class GoogleSearchResultPage extends BasePage {
    private static final String BRANCH_XPATH = "//a[@href='https://branch.io/']";

    @FindBy(xpath = "//a[@href='https://branch.io/']")
    private WebElement branchLink;

    public GoogleSearchResultPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to branch.io home page
     *
     * @return {@link BranchHomePage} return new BranchHomePage instance
     */
    public BranchHomePage goToBranchHomePage() {
        branchLink.click();
        LOGGER.info("Clicked on branch.io link");
        return new BranchHomePage(driver);
    }

    /**
     * Override this wait method to wait for the slow loading element on the page
     */
    @Override
    protected void waitThePageToLoad() {
        WaitUtils.waitUntil(driver, ExpectedConditions
                .visibilityOf(driver.findElement(By.xpath(BRANCH_XPATH))));
    }

}