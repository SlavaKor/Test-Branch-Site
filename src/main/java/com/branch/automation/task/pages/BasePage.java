package com.branch.automation.task.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import com.branch.automation.task.utils.WaitUtils;

/**
 * Parent class for com.branch.automation.task.pages. Has all necessary functionality for new page.
 * Uses constructor with wait and Page Factory init method.
 * Each new page should extend this class
 */
public class BasePage {
    protected static final Logger LOGGER = Logger.getLogger(BasePage.class.getName());

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        waitThePageToLoad();
        PageFactory.initElements(driver, this);
    }

    /**
     * General wait used in each page, uses javascript readyState value to wait for
     */
    protected void waitThePageToLoad() {
        ExpectedCondition pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WaitUtils.waitUntil(driver, pageLoadCondition);
    }
}
