package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Web driver utils used to create web driver, navigate to needed url, quit.
 */
public class WebDriverUtils {
    private WebDriverUtils() {
    }

    public static WebDriver initDriver() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public static void navigateToURL(WebDriver driver, String url) {
        driver.navigate().to(url);
    }

    public static void tearDownBrowser(WebDriver driver) {
        driver.quit();
    }

}
