package org.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {
    private final By title = By.xpath("//h6[normalize-space()='Dashboard']");
    private final By pim = By.cssSelector("a[href$='/pim/viewPimModule']");

    public DashboardPage(WebDriver driver) {
        super(driver);
        waitForVisibility(title);
    }

    public EmployeeListPage openPim() {
        click(pim);
        return new EmployeeListPage(driver);
    }
}
