package com.branch.automation.task.pages;

import com.branch.automation.task.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Slava on 8/24/18.
 */
public class BranchResourcesPage extends BasePage {
    private static final String SEARCH_XPATH = "//input[@placeholder='Search']";
    private static final String DROP_DOWN_XPATH = "//div[@class='search-dropdown absolute']";

    @FindBy(xpath = "//h1[@class='bds-h3 bds-txt-white bds-txt-light']")
    private WebElement resourcesTitle;

    @FindBy(xpath = "//input[@placeholder='Search']")
    private WebElement searchInput;

    public BranchResourcesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Get title stirng from resources page
     * @return {@String} returns title
     */
    public String getResourcesTitle() {
        return resourcesTitle.getText();
    }

    /**
     *
     */
    public WebinarPage searchFor(String arg) {
        searchInput.sendKeys(arg);
        driver.findElement(By.xpath(DROP_DOWN_XPATH)).click();
        return new WebinarPage(driver);
    }

    /**
     * Override this wait method to wait for the slow loading element on the page -
     * introduction message
     */
    @Override
    protected void waitThePageToLoad() {
        WaitUtils.waitUntil(driver, ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath(SEARCH_XPATH))));
    }
}
