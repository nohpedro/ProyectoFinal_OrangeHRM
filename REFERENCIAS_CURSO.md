# Análisis de las clases 1 a 9

Se recorrieron las carpetas originales y sus subcarpetas antes de crear el proyecto.
Se identificaron estos proyectos Maven y la alternativa Playwright de clase 9:

| Clase | Proyectos y contenido observado | Aplicación al proyecto final |
|---|---|---|
| 1 | Class1: calculator, employee, student; campos privados, constructores y getters. | EmployeeData representa los datos sin ponerlos en el test. |
| 2 | Class2: tests de Calculator, Company, Employee y Student con TestNG y Assert. | Aserción en el método de prueba. |
| 3 | Clase3/clase3, Class3.1 y Class3.2: pruebas, servicios separados e inyección de dependencias; Mockito en los dos últimos. | Separación de responsabilidades; no se agrega Mockito a una prueba de navegador. |
| 4 | Class4: BaseTest, ChromeDriver, BeforeMethod y AfterMethod; login y Wikipedia. | Ciclo de navegador en una base de pruebas. |
| 5 | Class5_Fligths, Class5_Fligths_PO y Class5_Login_PO: páginas, constructores con driver, BasePage abstracta y waitForVisibility. | Cinco Page Objects y herencia de BasePage. |
| 6 | Class6_Fligths_PO_TestNG, Class6_Login_PO_TestNG y Class6-The-Internet: pruebas con páginas, ExpectedConditions, WebDriverWait y suite. | Acciones que devuelven páginas, esperas explícitas y TestNG. |
| 7 | Class7: BasePage y BaseTest abstractas; switch para ChromeDriver/FirefoxDriver. | Bases abstractas y creación de ambos navegadores. |
| 8 | Class8_Login, Class8_Login_EjerciciosResueltos y Class8_Login_Report: flujo de negocio entre páginas, variantes de reporting. | Flujo legible entre páginas; se usan reportes estándar de TestNG, sin ExtentReports. |
| 9 | Ecommerce-pageobject: pages, models, helpers, conf.BaseTest, JsonTestDataHelper, Credentials, DataProvider y suite-regression.xml. También existen Ecommerce-screenplay y Ecommerce-playwright. | Referencia principal de paquetes, Gson, modelo de datos, DataProvider y parámetro browser. Se mantiene Page Object Model. |

## Archivos que guiaron decisiones concretas

- `Class5/Class5_Fligths_PO/src/main/java/pages/BasePage.java`: base abstracta y waitForVisibility.
- `Class6/Class6_Login_PO_TestNG/src/test/java/login/LoginTests.java`: login devuelve ProductsPage y el test hace Assert.
- `Class6/Class6_Fligths_PO_TestNG/src/test/java/search/SearchTests.java`: buscar mediante una página y comprobar un resultado desde el test.
- `Class6/Class6-The-Internet/src/main/java/pages/DynamicLoadingExample1Page.java`: WebDriverWait y ExpectedConditions para contenido dinámico.
- `Class7/src/test/java/base/BaseTest.java`: switch de navegadores y quit en AfterMethod.
- `Class7/src/main/java/pages/BasePage.java`: herencia y driver protegido.
- `Class9/Ecommerce-pageobject/src/main/java/org/ecommerce/pages/LoginPage.java`: localizadores privados y acciones separadas, retorno de otra página.
- `Class9/Ecommerce-pageobject/src/main/java/org/ecommerce/helpers/JsonTestDataHelper.java`: Gson para convertir JSON en objetos.
- `Class9/Ecommerce-pageobject/src/main/java/org/ecommerce/models/Credentials.java`: campos coincidentes con claves JSON y getters.
- `Class9/Ecommerce-pageobject/src/test/java/login/LoginTest.java`: DataProvider que inyecta un objeto por ejecución.
- `Class9/Ecommerce-pageobject/src/test/java/conf/BaseTest.java` y `suite-regression.xml`: Parameters/Optional y dos bloques test con browser.
- `Class9/Ecommerce-pageobject/pom.xml`: Java 11, Selenium 4.48.0, TestNG 7.12.0 y Gson 2.14.0.

## Ajustes necesarios sin cambiar la arquitectura enseñada

- Surefire explícito para que Maven ejecute la suite, sin depender del IDE.
- Datos en `src/test/resources` y lectura desde classpath: Maven los copia y funciona desde terminal.
- Helper JSON estático sencillo con cierre del Reader; no hace falta un singleton con estado.
- Helpers de click y escritura incluyen esperas, tal como pide la rúbrica.
- Captura estándar Selenium en AfterMethod y reportes TestNG, sin dependencia de reporting adicional.
- Identidad única compartida por alta y búsqueda porque la demo es pública.
- No se copian assertions presentes en alguna página antigua ni Thread.sleep de cierres anteriores.
- No se copian drivers binarios del curso: Selenium Manager resuelve las versiones correspondientes.

Los proyectos originales se conservaron. El código nuevo está en ProyectoFinal_OrangeHRM;
las herramientas locales y archivos temporales de inspección están en la carpeta hermana `.tools`.
