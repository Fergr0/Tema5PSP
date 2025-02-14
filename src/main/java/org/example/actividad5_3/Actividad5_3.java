package org.example.actividad5_3;

import java.io.*;
import java.security.*;
import java.security.spec.*;

/*
 * Esta clase genera un par de claves DSA (pública y privada) y las guarda en archivos.
 * La clave privada se almacena en "Clave.privada" en formato PKCS8,
 * y la clave pública en "Clave.publica" en formato X.509.
 */

public class Actividad5_3 {
    public static void main(String[] args) {
        try {
            // Generar un par de claves DSA
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            keyGen.initialize(2048); // Tamaño de clave DSA
            KeyPair par = keyGen.generateKeyPair();

            // Obtener claves privada y pública
            PrivateKey clavePrivada = par.getPrivate();
            PublicKey clavePublica = par.getPublic();

            // Guardar la clave privada en un archivo en formato PKCS8
            PKCS8EncodedKeySpec pk8Spec = new PKCS8EncodedKeySpec(clavePrivada.getEncoded());
            FileOutputStream outPriv = new FileOutputStream("Clave.privada");
            outPriv.write(pk8Spec.getEncoded());
            outPriv.close();

            // Guardar la clave pública en un archivo en formato X.509
            X509EncodedKeySpec pk509Spec = new X509EncodedKeySpec(clavePublica.getEncoded());
            FileOutputStream outPub = new FileOutputStream("Clave.publica");
            outPub.write(pk509Spec.getEncoded());
            outPub.close();

            System.out.println("Claves DSA generadas y almacenadas en los archivos 'Clave.publica' y 'Clave.privada'.");

        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }
}
