package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.WaitUtils;

/**
 * Class for Google Search results
 */
public class GoogleSearchResultPage extends BasePage {

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

    @Override
    protected void waitThePageToLoad() {
        WaitUtils.waitUntil(driver, ExpectedConditions
                .visibilityOf(driver.findElement(By.xpath("//a[@href='https://branch.io/']"))));
    }

}