package org.example.actividad5_4;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.security.KeyStore;

/* Esta clase implementa un servidor SSL que espera conexiones seguras en un puerto específico.
Usa un keystore para autenticar la conexión y responder a los clientes que se conecten.
Se asegura de que la comunicación sea cifrada con TLS.*/


public class ServerSeguro {
    public static void main(String[] args) {
        int puerto = 8443; // Puerto en el que escuchará el servidor SSL

        try {
            // Configuración del protocolo TLS para garantizar compatibilidad
            System.setProperty("jdk.tls.server.protocols", "TLSv1.2,TLSv1.3");

            // Ruta del keystore (archivo que contiene la clave privada y el certificado)
            String keystorePath = "C:/Users/ferna/IdeaProjects/Tema5PSP/src/main/java/org/example/actividad5_4/AlmacenSrv";
            String keystorePassword = "1234567";

            // Cargar el keystore del servidor
            KeyStore ks = KeyStore.getInstance("PKCS12");
            try (FileInputStream fis = new FileInputStream(keystorePath)) {
                ks.load(fis, keystorePassword.toCharArray());
            }

            // Configurar KeyManagerFactory con el keystore cargado
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, keystorePassword.toCharArray());

            // Crear contexto SSL con los KeyManagers
            SSLContext contextoSSL = SSLContext.getInstance("TLS");
            contextoSSL.init(kmf.getKeyManagers(), null, null);

            // Crear el socket SSL seguro a partir del contexto SSL configurado
            SSLServerSocketFactory sfact = contextoSSL.getServerSocketFactory();
            SSLServerSocket servidorSSL = (SSLServerSocket) sfact.createServerSocket(puerto);

            System.out.println("Servidor SSL esperando conexiones en el puerto " + puerto + "...");

            // Bucle infinito para aceptar conexiones de clientes
            while (true) {
                try (SSLSocket clienteSSL = (SSLSocket) servidorSSL.accept();
                     DataInputStream flujoEntrada = new DataInputStream(clienteSSL.getInputStream());
                     DataOutputStream flujoSalida = new DataOutputStream(clienteSSL.getOutputStream())) {

                    System.out.println("Cliente conectado.");

                    // Recibir mensaje del cliente
                    String mensajeCliente = flujoEntrada.readUTF();
                    System.out.println("Mensaje recibido del cliente: " + mensajeCliente);

                    // Enviar respuesta al cliente
                    flujoSalida.writeUTF("Saludos desde el servidor SSL");

                } catch (IOException e) {
                    System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor SSL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
