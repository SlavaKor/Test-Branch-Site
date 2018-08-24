package com.branch.automation.task.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.branch.automation.task.utils.WaitUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describes state and behavior of Branch Team page
 */
public class BranchTeamPage extends BasePage {
    private static final String BY_NAME_XPATH = "//div[@class='row row-centered']" +
            "/div[@style='display: inline-block;']//h2[contains(text(),'%s')]";
    private static final String DEPARTMENT_XPATH = "following-sibling::h4";
    private static final String TEAM_CATEGORIES_XPATH = "//ul[@class='team-categories']/li";

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
     * Returns total number of employees shown in the "All" tab.
     * Explicitly clicking the first tab to make sure it is selected.
     *
     * @return {@link int} - total number of employees in first "All" tab.
     */
    public int getEmployeesNumberFromAllTab() {
        departmentsTabs.get(0).click();
        return allEmployeesShownInPage.size();
    }

    /**
     * Get list of employees shown in currently selected tab.
     * @return list of employees shown in current screen
     */
    public List<WebElement> getEmployeesNamesShownInPage() {
        return allEmployeesNamesShownInPage;
    }

    /**
     * Returns list of employees shown in the "All" tab.
     * Explicitly clicking the first tab to make sure it is selected.
     *
     * @return {@link List<WebElement>} - list of employees in first "All" tab.
     */
    public List<WebElement> getEmployeesFromAllTab() {
        departmentsTabs.get(0).click();
        return allEmployeesShownInPage;
    }

    /**
     * Get list of all departments
     * @return {@link} list of departments web elements
     */
    public List<WebElement> getDepartments() {
        return departmentsTabs;
    }

    /**
     * Get web element with specified employee name
     * As names are returned in uppercase, it has to be converted in
     * regular view used in xpath
     * @param fullName {@link String} employee full name
     * @return {@WebElement} - returns web element for selected name
     */
    public WebElement getEmployeeByName(String fullName) {
        fullName = toUpperCaseFirstLetter(fullName);
        return driver.findElement(By.xpath(String.format(BY_NAME_XPATH, fullName)));
    }

    /**
     * I KNOW :))) this actually converts upper-case names to normal view
     * just with couple hardcoded exceptions, sorry for that :(.
     * If I have time I will change it to make xpath case insensitive
     * as it didn't work with solutions I found so far :((
     * @param fullName {@link String} employee's name
     * @return {@link String} eployee's name in lower-case format this first letter in upper-case
     */
    public String toUpperCaseFirstLetter(String fullName) {
        if (fullName.equals("PATRICK VAN DER STEEN")) return "Patrick van der Steen"; // sorry for hardcode
        if (fullName.equals("PETER LABERGE")) return "Peter LaBerge"; // just xpath case sensitive
        if (fullName.equals("SOJAN PR")) return "Sojan PR";
        String result = "";
        String[] splitName = fullName.split(" ");
        for (int i = 0; i < splitName.length; i++) {
            char[] lowered = splitName[i].toLowerCase().toCharArray();
            for (int j = 0; j < lowered.length - 1; j++) {
                if (!Character.isLetter(lowered[j])) lowered[j + 1] = Character.toUpperCase(lowered[j + 1]);
                else lowered[0] = Character.toUpperCase(lowered[0]);
            }
            if (i < splitName.length - 1) result = result + new String(lowered) + " ";
                else result = result + new String(lowered);
        }
        return result;
    }

    /**
     * Get employees names from All tab.
     * Explicitly clicking the first tab to make sure it is selected.
     * @return {@link List<String>} - list of employees names
     */
    public List<String> getEmployeesNamesFromAllTab() {
        List<String> listOfNames = new ArrayList<>();
        departmentsTabs.get(0).click();
        for (int i = 0; i < allEmployeesNamesShownInPage.size(); i++) {
            listOfNames.add(allEmployeesNamesShownInPage.get(i).getText());
        }
        return listOfNames;
    }

    /**
     * Get employees names from current tab.
     * Explicitly clicking the exact tab for it.
     * @return {@link List<String>} - list of employees names
     */
    public List<String> getEmployeesNamesFromCurrentTab(int current) {
        List<String> listOfNames = new ArrayList<>();
        departmentsTabs.get(current).click();
        for (int i = 0; i < allEmployeesNamesShownInPage.size(); i++) {
            listOfNames.add(allEmployeesNamesShownInPage.get(i).getText());
        }
        return listOfNames;
    }

    /**
     * Navigates to each tab and collects employees' names
     * @return list of employees names
     */
    public List<String> getEmployeesNamesFromOthersTabs() {
        List<String> listOfNames = new ArrayList<>();
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
     * Get list of web elements of employees names shown in the current tab
     * @return - list of employees names
     */
    public List<WebElement> getAllEmployeesNamesShownInPage() {
        return allEmployeesNamesShownInPage;
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

    /**
     * Put every employee in map to create corresponding pair: name -> department
     * The map will be used in testing departments matching
     * @return {@link Map<String, String>} employee name with corresponding department
     */
    public Map<String, String> getEmpWithDep() {
        Map<String, String> map = new HashMap<>();
        for (String name : getEmployeesNamesFromAllTab()) {
            String dep = getEmployeeByName(name).findElement(By.xpath(DEPARTMENT_XPATH)).getText();
            if (!map.containsKey(name)) {
                map.put(name, dep);
            }
        }
        LOGGER.info("Number of pair: employees - department added to map: " + map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            LOGGER.info(entry.getKey() + ":" + entry.getValue());
        }
        return map;
    }

    /**
     * Override this wait method to wait for the slow loading element on the page
     */
    @Override
    protected void waitThePageToLoad() {
        WaitUtils.waitUntil(driver, ExpectedConditions.visibilityOf(driver
                .findElement(By.xpath(TEAM_CATEGORIES_XPATH))));
    }
}
