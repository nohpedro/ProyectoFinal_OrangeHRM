# Proyecto final: OrangeHRM

Una sola prueba de negocio inicia sesión como administrador de la demo, abre PIM,
crea un empleado con **Create Login Details**, vuelve al listado, busca por Employee ID
y comprueba ID, primer/segundo nombre y apellido en una única fila.

## Ejecutar

Con JDK 11 o superior y Maven 3.9 en PATH, desde esta carpeta:

```powershell
mvn clean test
```

En este equipo se prepararon herramientas portátiles en `../.tools`, porque Java y
Maven no estaban disponibles en PATH. Para usarlas sin modificar Windows:

```powershell
mvn test -Dtest=employees.CreateEmployeeTest -Dbrowser=chrome
mvn test -Dtest=employees.CreateEmployeeTest -Dbrowser=firefox
```

Opcionalmente, `mvn clean test -Dheadless=true` ejecuta sin ventanas visibles.
La suite normal se ejecuta secuencialmente y **sin headless**.

## Estructura y clases

```text
ProyectoFinal_OrangeHRM/
├── pom.xml
├── testng.xml
├── activar-entorno.ps1
├── src/main/java/org/orangehrm/
│   ├── pages/
│   │   ├── BasePage.java
│   │   ├── LoginPage.java
│   │   ├── DashboardPage.java
│   │   ├── EmployeeListPage.java
│   │   └── AddEmployeePage.java
│   ├── models/EmployeeData.java
│   └── helpers/
│       ├── JsonTestDataHelper.java
│       └── UniqueDataHelper.java
├── src/test/java/
│   ├── base/BaseTest.java
│   └── employees/CreateEmployeeTest.java
├── src/test/resources/testdata/employees.json
└── evidencias/
```

| Clase | Responsabilidad |
|---|---|
| `BasePage` | Base abstracta con WebDriver y WebDriverWait de 30 segundos; visibilidad, elemento clickeable, click, escritura y texto. |
| `LoginPage` | Lee las credenciales públicas mostradas en el login y devuelve DashboardPage al iniciar sesión. |
| `DashboardPage` | Espera el Dashboard y abre PIM. |
| `EmployeeListPage` | Abre el alta, busca por ID y devuelve boolean al verificar la fila completa. |
| `AddEmployeePage` | Completa nombres, ID y datos de acceso; selecciona estado, guarda y permite volver al listado. |
| `EmployeeData` | Representa una fila del JSON con getters; su toString no expone la contraseña. |
| `JsonTestDataHelper` | Lee el recurso JSON UTF-8 mediante Gson y cierra el archivo automáticamente. |
| `UniqueDataHelper` | Produce una copia del empleado con la identidad generada para esa ejecución. |
| `BaseTest` | Crea Chrome/Firefox en BeforeMethod, maximiza y navega; guarda captura y cierra en AfterMethod(alwaysRun=true). |
| `CreateEmployeeTest` | Contiene el DataProvider y un único método de prueba con pasos de negocio y aserción final. |

Los localizadores viven en los Page Objects. Las páginas no importan TestNG ni hacen
aserciones. El test de negocio no accede al DOM. No se utiliza Thread.sleep.

## DataProvider y datos únicos

`employees()` lee los dos objetos de `testdata/employees.json`. Para cada uno llama
**una sola vez** a `UniqueDataHelper.uniqueEmployee` y retorna un `Object[][]`, con un
EmployeeData por fila. TestNG invoca la misma prueba una vez por fila.

El helper genera un sufijo aleatorio de nueve caracteres alfanuméricos con SecureRandom
(36^9 combinaciones). Ese mismo sufijo se reutiliza en nombre, username e ID:

```text
JSON:       Natalia            nataliaqa            Q
Ejecución:  Natalia_p3yu9g5ji   nataliaqa_p3yu9g5ji   Qp3yu9g5ji
```

El ID del JSON es un prefijo de un carácter, por lo que el ID final tiene diez.
El nombre y username del JSON deben ser bases cortas: con el sufijo deben respetar
los límites del sitio. Los dos ejemplos entregados ya fueron ejercitados.
El azar hace muy improbable una colisión, pero no es una garantía matemática;
si la demo rechaza un duplicado, el caso falla, sin ocultarlo ni reintentar el alta.
Nunca se genera otra identidad al buscar. No se modifica el JSON original.

El primer empleado usa `Enabled` y el segundo `Disabled`. La contraseña se toma del
JSON y se escribe también en su confirmación. Son datos ficticios para la demo pública.


## Evidencia y alcance

Los reportes de cada nueva ejecución quedan en `target/surefire-reports/` y las capturas
en `target/screenshots/`. Si falla el caso también se guarda el HTML para diagnóstico.
`mvn clean` elimina target; las evidencias de entrega se conservan aparte en `evidencias/`.
Consulta `VALIDACION.md` para el resultado concreto de las ejecuciones de entrega.

La demo es compartida: puede resetear o alterar datos. Cada ejecución crea empleados
ficticios nuevos y no elimina datos existentes. No se automatizan contacto, cargo,
salario, dependencias, fotografía ni edición posterior de Personal Details.

Consulta `REFERENCIAS_CURSO.md` para la trazabilidad con los proyectos originales.
