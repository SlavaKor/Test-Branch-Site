package com.branch.automation.task.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.branch.automation.task.utils.WaitUtils;

import java.util.logging.Logger;

/**
 * Class for Google Search page
 */
public class GoogleSearchHomePage extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(GoogleSearchHomePage.class.getName());
    private static final String SEARCH_XPATH = "//input[@title='Search']";
    @FindBy(xpath = "//input[@title='Search']")
    private WebElement searchInput;

    @FindBy(xpath = "//input[@value='Google Search']")
    private WebElement googleSearchBtn;

    public GoogleSearchHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Performs search for the method's argument
     *
     * @param searchArg {@link String} the name of the element to search for
     * @return {@link GoogleSearchResultPage} returns Google page with search results
     */
    public GoogleSearchResultPage searchFor(String searchArg) {
        searchInput.sendKeys(searchArg);
        googleSearchBtn.click();
        LOGGER.info("Searching for " + searchArg);
        return new GoogleSearchResultPage(driver);
    }

    /**
     * Override this wait method to wait for the slow loading element on the page
     */
    @Override
    protected void waitThePageToLoad() {
        WaitUtils.waitUntil(driver, ExpectedConditions.visibilityOf(driver
                .findElement(By.xpath(SEARCH_XPATH))));
    }
}

