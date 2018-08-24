package com.branch.automation.task.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Web driver com.branch.automation.task.utils used to create web driver, navigate to needed url, quit.
 */
public class WebDriverUtils {
    private WebDriverUtils() {
    }

    /**
     * Initialized Web driver instance, maximize the current window
     * @return
     */
    public static WebDriver initDriver() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Navigates to passed url
     * @param driver {@link WebDriver} WebDriver instance initialized in initDriver()
     * @param url {@link String} url value WebDriver will use to navigate to
     */
    public static void navigateToURL(WebDriver driver, String url) {
        driver.navigate().to(url);
    }

    /**
     * Quit WebDriver
     * @param driver {@WebDriver} WebDriver instance initialized in initDriver()
     */
    public static void tearDownBrowser(WebDriver driver) {
        driver.quit();
    }

}
