package org.orangehrm.helpers;

import org.orangehrm.models.EmployeeData;
import java.security.SecureRandom;

public final class UniqueDataHelper {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz";

    private UniqueDataHelper() { }

    public static EmployeeData uniqueEmployee(EmployeeData base) {
        // Un solo sufijo por fila del DataProvider, compartido por los tres campos.
        StringBuilder suffix = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            suffix.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        if (base.getEmployeeId() == null || base.getEmployeeId().length() != 1) {
            throw new IllegalArgumentException("employeeId en JSON debe ser un prefijo de un caracter");
        }
        if (!"Enabled".equals(base.getStatus()) && !"Disabled".equals(base.getStatus())) {
            throw new IllegalArgumentException("status debe ser Enabled o Disabled");
        }
        return new EmployeeData(base.getFirstName() + "_" + suffix, base.getMiddleName(),
                base.getLastName(), base.getEmployeeId() + suffix,
                base.getUsername() + "_" + suffix, base.getPassword(), base.getStatus());
    }
}
