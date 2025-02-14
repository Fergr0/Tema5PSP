package org.example.actividad5_2;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


/*
 * Esta clase se encarga de leer el archivo "DATOS.DAT" y comprobar si ha sido modificado.
 * Para eso, compara el resumen SHA-256 guardado en el archivo con uno nuevo que se genera
 * al momento de la lectura.
 *
 * Si abrimos el archivo "DATOS.DAT" y cambiamos algo (por ejemplo, modificamos una letra),
 * el resumen que se genera al leerlo será distinto al que estaba guardado.
 * Como resultado, el programa se dará cuenta del cambio y mostrará el mensaje:
 *
 *      "DATOS NO VÁLIDOS. El archivo ha sido modificado."
 *
 * Esto sirve para detectar si alguien alteró el contenido del archivo después de haberlo creado.
 */



public class Ejemplo6 {
    public static void main(String[] args) {
        try {
            // Abrir el archivo para lectura
            FileInputStream filein = new FileInputStream("DATOS.DAT");
            ObjectInputStream dataIS = new ObjectInputStream(filein);

            // Leer el String almacenado en el archivo
            String datos = (String) dataIS.readObject();

            // Leer el resumen almacenado en el archivo
            byte[] resumenOriginal = (byte[]) dataIS.readObject();

            // Cerrar los flujos de entrada
            dataIS.close();
            filein.close();

            // Calcular nuevamente el resumen SHA-256 del texto leído
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] dataBytes = datos.getBytes();
            md.update(dataBytes);
            byte[] resumenActual = md.digest();

            // Comparar los resúmenes
            if (MessageDigest.isEqual(resumenOriginal, resumenActual)) {
                System.out.println("DATOS VÁLIDOS.");
            } else {
                System.out.println("DATOS NO VÁLIDOS. El archivo ha sido modificado.");
            }

        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
