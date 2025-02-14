package org.example.actividad5_1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Actividad5_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar al usuario tres cadenas de texto
        System.out.print("Ingrese la primera cadena: ");
        String cadena1 = scanner.nextLine();

        System.out.print("Ingrese la segunda cadena: ");
        String cadena2 = scanner.nextLine();

        System.out.print("Ingrese la clave de cifrado: ");
        String clave = scanner.nextLine();

        // Calcular el resumen SHA-256 de las dos cadenas usando la clave como parte del proceso
        byte[] resumen1 = calcularHashSHA256(cadena1, clave);
        byte[] resumen2 = calcularHashSHA256(cadena2, clave);

        // Convertir los resúmenes a formato hexadecimal para su visualización
        String resumenHex1 = convertirHexadecimal(resumen1);
        String resumenHex2 = convertirHexadecimal(resumen2);

        // Mostrar los resultados en consola
        System.out.println("\nResumen de la primera cadena: " + resumenHex1);
        System.out.println("Resumen de la segunda cadena: " + resumenHex2);

        // Comparar los resúmenes generados para verificar si son iguales
        if (resumenHex1.equals(resumenHex2)) {
            System.out.println("Los resúmenes son IGUALES.");
        } else {
            System.out.println("Los resúmenes son DIFERENTES.");
        }

        // Cerrar el scanner para liberar recursos
        scanner.close();
    }

    /**
     * Metodo para calcular el hash SHA-256 de una cadena de texto usando una clave
     */
    public static byte[] calcularHashSHA256(String texto, String clave) {
        try {
            // Crear una instancia del algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(texto.getBytes()); // Añadir el texto en bytes al digest
            return md.digest(clave.getBytes()); // Aplicar la clave y calcular el hash final
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: Algoritmo SHA-256 no disponible.");
            return null;
        }
    }

    /**
     * Metodo para convertir un array de bytes a una cadena hexadecimal
     */
    public static String convertirHexadecimal(byte[] resumen) {
        StringBuilder hex = new StringBuilder();
        for (byte b : resumen) {
            String h = Integer.toHexString(b & 0xFF); // Convertir el byte a su representación hexadecimal
            if (h.length() == 1) {
                hex.append("0"); // Asegurar que tenga siempre dos dígitos
            }
            hex.append(h);
        }
        return hex.toString().toUpperCase(); // Convertir el resultado a mayúsculas para uniformidad
    }
}
