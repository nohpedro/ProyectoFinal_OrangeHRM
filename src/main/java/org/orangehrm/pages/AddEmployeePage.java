package org.orangehrm.pages;

import org.orangehrm.models.EmployeeData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddEmployeePage extends BasePage {
    private final By firstName = By.name("firstName");
    private final By middleName = By.name("middleName");
    private final By lastName = By.name("lastName");
    private final By employeeId = inputWithLabel("Employee Id");
    private final By createLoginDetails = By.cssSelector(".oxd-switch-wrapper label");
    private final By loginCheckbox = By.cssSelector(".oxd-switch-wrapper input[type='checkbox']");
    private final By username = inputWithLabel("Username");
    private final By password = inputWithLabel("Password");
    private final By confirmPassword = inputWithLabel("Confirm Password");
    private final By saveButton = By.cssSelector("button[type='submit']");
    private final By employeeList = By.linkText("Employee List");

    private static By inputWithLabel(String label) {
        return By.xpath("//label[normalize-space()='" + label + "']/parent::div/following-sibling::div//input");
    }

    private By statusLabel(String status) {
        return By.xpath("//label[normalize-space()='" + status + "' and input[@type='radio']]");
    }

    private By statusRadio(String status) {
        return By.xpath("//label[normalize-space()='" + status + "']/input[@type='radio']");
    }

    public AddEmployeePage(WebDriver driver) {
        super(driver);
        waitForVisibility(firstName);
    }

    public void createEmployee(EmployeeData employee) {
        write(firstName, employee.getFirstName());
        write(middleName, employee.getMiddleName());
        write(lastName, employee.getLastName());
        write(employeeId, employee.getEmployeeId());
        click(createLoginDetails);
        wait.until(ExpectedConditions.elementToBeSelected(loginCheckbox));
        write(username, employee.getUsername());
        write(password, employee.getPassword());
        write(confirmPassword, employee.getPassword());
        click(statusLabel(employee.getStatus()));
        wait.until(ExpectedConditions.elementToBeSelected(statusRadio(employee.getStatus())));
        click(saveButton);
        // Solo esperamos el fin del alta; no completamos Personal Details.
        wait.until(ExpectedConditions.urlContains("/pim/viewPersonalDetails/empNumber/"));
    }

    public EmployeeListPage openEmployeeList() {
        click(employeeList);
        return new EmployeeListPage(driver);
    }
}
