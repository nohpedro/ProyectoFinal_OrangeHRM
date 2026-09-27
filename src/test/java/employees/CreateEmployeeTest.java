package employees;

import base.BaseTest;
import org.orangehrm.helpers.JsonTestDataHelper;
import org.orangehrm.helpers.UniqueDataHelper;
import org.orangehrm.models.EmployeeData;
import org.orangehrm.pages.AddEmployeePage;
import org.orangehrm.pages.DashboardPage;
import org.orangehrm.pages.EmployeeListPage;
import org.orangehrm.pages.LoginPage;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class CreateEmployeeTest extends BaseTest {
    @DataProvider(name = "employees")
    public Object[][] employees() throws IOException {
        EmployeeData[] employees = JsonTestDataHelper.readEmployees("testdata/employees.json");
        Object[][] data = new Object[employees.length][1];
        for (int i = 0; i < employees.length; i++) {
            data[i][0] = UniqueDataHelper.uniqueEmployee(employees[i]);
        }
        return data;
    }

    @Test(dataProvider = "employees", description = "Crear empleado con usuario y encontrarlo en PIM")
    public void createEmployeeAndFindInList(EmployeeData employee) {
        Reporter.log("Empleado de esta ejecucion: " + employee, true);
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboard = loginPage.loginAsDemoAdministrator();
        EmployeeListPage employeeList = dashboard.openPim();
        AddEmployeePage addEmployee = employeeList.openAddEmployee();
        addEmployee.createEmployee(employee);
        employeeList = addEmployee.openEmployeeList();
        employeeList.searchEmployee(employee);

        Assert.assertTrue(employeeList.isEmployeeDisplayed(employee),
                "El empleado creado no aparece con su ID y nombre completos: " + employee);
    }
}
