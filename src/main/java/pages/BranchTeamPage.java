package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.WaitUtils;

import java.util.List;

/**
 * Describes Branch Team page
 */
public class BranchTeamPage extends BasePage {
    @FindBy(xpath = "//div[@class='row row-centered']/div[@style='display: inline-block;']")
    private List<WebElement> allEmployeesShowInPage;

    @FindBy(xpath = "//ul[@class='team-categories']/li")
    private List<WebElement> departmentsTabs;

    public BranchTeamPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Returns total number of employees shown in the first tab.
     * Explicitly clicking the first tab to make sure it is selected.
     *
     * @return {@link int} - total number of employees in first "All" tab.
     */
    public int getAllEmployees() {
        departmentsTabs.get(0).click();
        return allEmployeesShowInPage.size();
    }

    /**
     * Counts the sum and returns total number of employees within all departments. Method navigates to each tab
     * except first (All) one.
     * List of employees for each tab get collected by using "inline-block;"
     * value for style attribute as, it has "inline-block;" value only if person is shown in the page.
     *
     * @return {@link int} - total number of employees in all departments.
     */
    public int getEmployeesInOtherDepartments() {
        int employeesInCurrentDep;
        int total = 0;
        for (int i = 1; i < departmentsTabs.size(); i++) {
            departmentsTabs.get(i).click();
            employeesInCurrentDep = allEmployeesShowInPage.size();
            LOGGER.info("Employees in " + departmentsTabs.get(i).getText() + " - " + employeesInCurrentDep);
            total = total + employeesInCurrentDep;
        }
        return total;
    }

    @Override
    protected void waitThePageToLoad() {
        WaitUtils.waitUntil(driver, ExpectedConditions.visibilityOf(driver
                .findElement(By.xpath("//ul[@class='team-categories']/li"))));
    }
}