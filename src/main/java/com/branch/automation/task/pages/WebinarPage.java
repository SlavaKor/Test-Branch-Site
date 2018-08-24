package com.branch.automation.task.pages;

import com.branch.automation.task.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Slava on 8/24/18.
 */
public class WebinarPage extends BasePage{
    private static final String FORM_ID = "mktoForm_1106";

    @FindBy(id = "FirstName")
    private WebElement firstNameInput;

    @FindBy(id = "LastName")
    private WebElement lastNameInput;

    @FindBy(id = "Email")
    private WebElement emailInput;

    @FindBy(id = "Country")
    private WebElement countryInput;

    @FindBy(xpath = "//option[@value='United States']")
    private WebElement countryFromDropDown;

    @FindBy(id = "Company")
    private WebElement companyInput;

    @FindBy(xpath = "//div[@class='mktoButtonRow']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@class='bds-h4 ty-past']")
    private WebElement thanksMessageFirstPart;

    @FindBy(xpath = "//div[@class='bds-h4 ty-past ty-resource-title']")
    private WebElement thanksMessageSecondPart;

    public WebinarPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Fill out the registration form and submit it
     * @param firstName {@link String} - required filed for user's first name
     * @param lastName {@link String} - required filed for user's last name
     * @param email {@link String} - required filed for user's email
     * @param company {@link String} - required filed for user's company
     * @return {@link WebinarPage} - required context as there is no navigation out of current page.
     */
    public WebinarPage submitWebinarForm(String firstName, String lastName, String email, String company) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        emailInput.sendKeys(email);
        countryInput.click();
        countryFromDropDown.click();
        companyInput.sendKeys(company);
        submitButton.click();
        return this;
    }

    /**
     * Gets concatenated thanks message which appears upon successful registration to event
     * @return {@String} thanks message
     */
    public String getThanksMessage() {
        return thanksMessageFirstPart.getText() + thanksMessageSecondPart.getText();
    }

    /**
     * Override this wait method to wait for the slow loading element on the page
     */
    @Override
    protected void waitThePageToLoad() {
        WaitUtils.waitUntil(driver, ExpectedConditions
                .visibilityOf(driver.findElement(By.id(FORM_ID))));
    }
}
