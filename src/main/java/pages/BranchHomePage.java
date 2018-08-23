package pages;

import org.openqa.selenium.WebDriver;

/**
 * Class describes branch.io home page
 */
public class BranchHomePage extends BasePage {
    public BranchHomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void waitThePageToLoad() {
        //WaitUtils.waitUntil(driver, ExpectedConditions.visibilityOf(driver.findElement(By.id("lst-ib"))));
    }
}

