package org.orangehrm.helpers;

import com.google.gson.Gson;
import org.orangehrm.models.EmployeeData;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class JsonTestDataHelper {
    private JsonTestDataHelper() { }

    public static EmployeeData[] readEmployees(String resource) throws IOException {
        InputStream input = JsonTestDataHelper.class.getClassLoader().getResourceAsStream(resource);
        if (input == null) throw new IOException("No se encontro el archivo de datos: " + resource);
        try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            EmployeeData[] employees = new Gson().fromJson(reader, EmployeeData[].class);
            if (employees == null || employees.length == 0) {
                throw new IOException("El archivo no contiene empleados: " + resource);
            }
            return employees;
        }
    }
}
