# Validación de entrega

Ejecutada el **27 de septiembre de 2026**, zona America/La_Paz.
El comando final **mvn clean test** terminó a las **17:43:49 -04:00** con **BUILD SUCCESS**.

```text
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
Total time: 01:35 min
```

## Ejecuciones reales

| Ejecución | Casos | Resultado | Evidencia |
|---|---:|---|---|
| Chrome individual | 2 | 2 aprobados | `evidencias/chrome/maven.log` y `testng-results.xml` |
| Firefox individual, después de corregir sincronización | 2 | 2 aprobados | `evidencias/firefox/maven.log` y `testng-results.xml` |
| Suite completa mediante mvn clean test | 4 | 4 aprobados | `evidencias/suite-completa/maven-clean-test.log` y `testng-results.xml` |

Entorno: Windows, JDK Temurin 11.0.32.1, Maven 3.9.11,
Chrome for Testing 154.0.8037.57 y Firefox 156.0.1. Las pruebas Java se ejecutaron
con ventanas visibles. Las capturas finales están junto a cada reporte.

## Identidades verificadas en la suite completa

| Navegador | Employee ID | Primer nombre generado | Estado de usuario | Resultado |
|---|---|---|---|---|
| Chrome | Q1ue6m8u26 | Natalia_1ue6m8u26 | Enabled | PASS |
| Chrome | M1z83ba039 | Diego_1z83ba039 | Disabled | PASS |
| Firefox | Qvxluemtwt | Natalia_vxluemtwt | Enabled | PASS |
| Firefox | Mxrf2du8e3 | Diego_xrf2du8e3 | Disabled | PASS |

En cada caso se completaron nombre, segundo nombre, apellido, ID, switch de creación
de usuario, username, contraseña, confirmación y estado; se guardó y se buscó el ID.
La aserción comprobó el ID y los nombres exactos en una única fila de la grilla.
No se editaron Personal Details ni otros módulos posteriores al alta.

## Problema encontrado y corregido

En la primera ejecución de Firefox, la validación asíncrona del ID mostraba
`.oxd-form-loader`, que interceptaba el clic del switch Create Login Details.
BasePage ahora espera la desaparición de las capas de carga y reevalúa el clic si
el elemento queda interceptado o es reemplazado por el renderizado. Se utilizan
WebDriverWait y condiciones del DOM; no hay sleeps ni clics con JavaScript.
Después de la corrección pasaron Firefox individual y la suite completa.

## Criterios de terminación

- [x] Maven compila desde una limpieza completa.
- [x] Login con las credenciales públicas de la pantalla.
- [x] Navegación a PIM.
- [x] Alta de empleado con Create Login Details, username e ID generados.
- [x] Contraseña y confirmación completadas; estados Enabled y Disabled seleccionados.
- [x] Regreso al listado, búsqueda y comprobación exacta en grilla.
- [x] Assert en el test de negocio y aprobado.
- [x] DataProvider ejecuta ambos empleados en cada navegador.
- [x] Chrome y Firefox funcionan.
- [x] testng.xml produce cuatro casos y mvn clean test los ejecuta.
- [x] Test de negocio sin localizadores ni acceso al DOM.
- [x] Page Objects sin assertions ni dependencia de TestNG.
- [x] Datos de empleados externos al test.
- [x] Sin Thread.sleep.
- [x] 230 archivos originales revisados conservan sus hashes SHA-256.

La revisión estática está registrada en `evidencias/revision-estatica.txt`.

Selenium emite un aviso por la versión CDP de Chrome y SLF4J avisa que no tiene
proveedor de logging. Ninguno impidió las ejecuciones: este proyecto utiliza
WebDriver estándar, no comandos CDP. Se mantuvieron las versiones del curso.

Los resultados documentan esta ejecución; la demo compartida puede resetear sus
datos posteriormente. Los reportes y capturas de `evidencias/` permanecen aunque
una nueva ejecución elimine `target/` mediante Maven clean.
