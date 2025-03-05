package org.example.actividad5_4;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.security.KeyStore;
/* Esta clase es un cliente SSL que se conecta a un servidor seguro.
Usa un truststore para verificar la identidad del servidor antes de comunicarse.
Envía un mensaje y recibe una respuesta cifrada mediante TLS.*/

public class ClienteSeguro {
    public static void main(String[] args) {
        String host = "localhost"; // Dirección del servidor SSL
        int puerto = 8443; // Puerto del servidor SSL, debe coincidir con el configurado en el servidor

        try {
            // Configuración del protocolo TLS para garantizar compatibilidad con el servidor
            System.setProperty("jdk.tls.client.protocols", "TLSv1.2,TLSv1.3");

            // Ruta del truststore (almacén de certificados en los que el cliente confía)
            String truststorePath = "C:/Users/ferna/IdeaProjects/Tema5PSP/src/main/java/org/example/actividad5_4/CliCertConfianza";
            String truststorePassword = "890123";

            // Cargar el truststore del cliente
            KeyStore ts = KeyStore.getInstance("PKCS12");
            try (FileInputStream fis = new FileInputStream(truststorePath)) {
                ts.load(fis, truststorePassword.toCharArray());
            }

            // Configurar TrustManagerFactory con el truststore cargado
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);

            // Crear contexto SSL con los TrustManagers
            SSLContext contextoSSL = SSLContext.getInstance("TLS");
            contextoSSL.init(null, tmf.getTrustManagers(), null);

            // Crear el socket SSL seguro a partir del contexto SSL configurado
            SSLSocketFactory sfact = contextoSSL.getSocketFactory();
            SSLSocket clienteSSL = (SSLSocket) sfact.createSocket(host, puerto);

            System.out.println("Cliente SSL conectado al servidor...");

            // Comunicación con el servidor SSL
            try (DataOutputStream flujoSalida = new DataOutputStream(clienteSSL.getOutputStream());
                 DataInputStream flujoEntrada = new DataInputStream(clienteSSL.getInputStream())) {

                // Enviar mensaje al servidor
                flujoSalida.writeUTF("Hola, antonioo SSL!");

                // Recibir respuesta del servidor
                System.out.println("Respuesta del servidor: " + flujoEntrada.readUTF());

            } catch (IOException e) {
                System.err.println("Error en la comunicación con el servidor: " + e.getMessage());
                e.printStackTrace();
            }

            // Cerrar la conexión con el servidor
            clienteSSL.close();

        } catch (Exception e) {
            System.err.println("Error en el cliente SSL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
