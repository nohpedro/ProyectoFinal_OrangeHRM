package org.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    // Localizadores observados en el DOM publico.
    private final By username = By.name("username");
    private final By password = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By demoUsername = By.xpath("//p[starts-with(normalize-space(), 'Username :')]");
    private final By demoPassword = By.xpath("//p[starts-with(normalize-space(), 'Password :')]");

    public LoginPage(WebDriver driver) {
        super(driver);
        waitForVisibility(username);
    }

    // Acciones: las credenciales se leen del aviso publico de la demo.
    public DashboardPage loginAsDemoAdministrator() {
        String user = getText(demoUsername).split(":", 2)[1].trim();
        String pass = getText(demoPassword).split(":", 2)[1].trim();
        return login(user, pass);
    }

    public DashboardPage login(String user, String pass) {
        write(username, user);
        write(password, pass);
        click(loginButton);
        return new DashboardPage(driver);
    }
}
