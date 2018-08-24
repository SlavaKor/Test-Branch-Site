package com.branch.automation.task.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Expected conditions to wait for the com.branch.automation.task.pages to load
 */
public class WaitUtils {
    private WaitUtils() {
    }

    private final static long TIMEOUT = 30;

    /**
     * Waits while all "wait" option on page are executed to load the page
     *
     * @param driver {@link WebDriver}
     * @param timeOutInSeconds {@link long} time to wait
     * @param expectedConditions{@link ExpectedCondition...} list of expected conditions to wait for
     */
    public static void waitUntil(WebDriver driver, long timeOutInSeconds, ExpectedCondition... expectedConditions) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        for (ExpectedCondition<WebElement> condition : expectedConditions) {
            wait.until(condition);
        }
    }

    /**
     * Waits while all "wait" option on page are executed to load the page, with timeout = 30 seconds
     *
     * @param driver {@link WebDriver}
     * @param expectedConditions {@link ExpectedCondition...} list of expected conditions to wait for
     */
    public static void waitUntil(WebDriver driver, ExpectedCondition... expectedConditions) {
        waitUntil(driver, TIMEOUT, expectedConditions);
    }
}
