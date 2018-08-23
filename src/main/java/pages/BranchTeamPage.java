package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.WaitUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes Branch Team page
 */
public class BranchTeamPage extends BasePage {
    @FindBy(xpath = "//div[@class='row row-centered']/div[@style='display: inline-block;']")
    private List<WebElement> allEmployeesShownInPage;

    @FindBy(xpath = "//div[@class='row row-centered']/div[@style='display: inline-block;']//h2")
    private List<WebElement> allEmployeesNamesShownInPage;

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
    public int getEmployeesFromAllTab() {
        departmentsTabs.get(0).click();
        return allEmployeesShownInPage.size();
    }

    /**
     * Get employees names from All tab.
     * Explicitly clicking the first tab to make sure it is selected.
     * @return {@link ArrayList<String>} - list of employees names
     */
    public ArrayList<String> getEmployeesNamesFromAllTab() {
        ArrayList<String> listOfNames = new ArrayList<>();
        departmentsTabs.get(0).click();
        for (int i = 0; i < allEmployeesNamesShownInPage.size(); i++) {
            listOfNames.add(allEmployeesNamesShownInPage.get(i).getText());
            LOGGER.info("Name "+ i + " - " + listOfNames.get(i));
        }
        return listOfNames;
    }

    public ArrayList<String> getEmployeesNamesFromOthersTabs() {
        ArrayList<String> listOfNames = new ArrayList<>();
        for (int i = 1; i < departmentsTabs.size(); i++) {
            departmentsTabs.get(i).click();
            for (int j = 0; j < allEmployeesNamesShownInPage.size(); j++) {
                listOfNames.add(allEmployeesNamesShownInPage.get(j).getText());
                LOGGER.info("Employees in " + departmentsTabs.get(i).getText() +  " - " + listOfNames.get(j));
            }
        }
        return listOfNames;
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
            employeesInCurrentDep = allEmployeesShownInPage.size();
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
