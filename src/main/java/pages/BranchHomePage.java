package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.WaitUtils;

/**
 * Class describes branch.io home page
 */
public class BranchHomePage extends BasePage {

    public BranchHomePage(WebDriver driver) {
        super(driver);
    }

    public BranchTeamPage goToTeamPage() {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        WebElement teamLink = driver.findElement(By.xpath("//a[@data-element-tag='team']"));
        teamLink.sendKeys(Keys.PAGE_DOWN);
        teamLink.click();
        LOGGER.info("Clicked on team link");
        return new BranchTeamPage(driver);
    }

    @Override
    protected void waitThePageToLoad() {
        WaitUtils.waitUntil(driver, ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath("//p[@class='copy']"))));
    }
}
