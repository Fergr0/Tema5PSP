package org.example.actividad5_2;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Ejemplo5 {
    public static void main(String[] args) {
        try {
            // Crear archivo de salida
            FileOutputStream fileout = new FileOutputStream("DATOS.DAT");
            ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

            // Crear el resumen SHA-256 del texto
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Texto que se va a almacenar en el archivo
            String datos = "En un lugar de la Mancha,"
                    + "de cuyo nombre no quiero acordarme, no ha mucho tiempo "
                    + "que vivía un hidalgo de los de lanza en astillero, "
                    + "adarga antigua, rocín flaco y galgo corredor.";

            byte dataBytes[] = datos.getBytes();

            // Se introduce el texto en bytes al resumen
            md.update(dataBytes);

            // Se calcula el resumen (hash)
            byte resumen[] = md.digest();

            // Escribir los datos en el archivo
            dataOS.writeObject(datos);    // Guardar el texto original
            dataOS.writeObject(resumen);  // Guardar el resumen SHA-256

            // Cerrar los flujos
            dataOS.close();
            fileout.close();

            System.out.println("Archivo DATOS.DAT creado correctamente.");

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

