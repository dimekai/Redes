package Clase02.multiplesFiles;
/**@author Kaimorts */

import java.net.*;
import java.io.*;

public class Recibe {

    public static void main(String[] args) {
        try {
            int pto = 1234, referencia_n = 0, porcentaje = 0;

            /*Creamos el Socket del Servidor*/
            ServerSocket server = new ServerSocket(pto);
            System.out.println("|------------- SERVIDOR -------------|");
            System.out.println("Servicio iniciando... esperando archivos");

            /*Creamos un ciclo infinito para recibir las peticiones
		que se hacen por el usuarios, y cachar el archivo enviado*/
            while (true) {
                Socket cliente = server.accept();

                /*Como recibiremos archivos, creamos un flujo de entrada*/
                DataInputStream dis = new DataInputStream(cliente.getInputStream());
                String nombre = dis.readUTF();
                long tam = dis.readLong();

                DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
                
                System.out.println("-----------------------------");
                System.out.println("Preparing to receive file...");
                System.out.println("Name:\t"+nombre);
                System.out.println("From:\t"+cliente.getInetAddress());
                System.out.println("With:\t"+tam+" bytes of data");
                System.out.println("-----------------------------");
                
                long recibidos = 0;

                /*Estará reconstruyendo el archivo enviado, a partir de la cantidad de bytes enviados*/
                while (recibidos < tam) {
                    byte[] b = new byte[1500];
                    referencia_n = dis.read(b);
                    recibidos += referencia_n;

                    dos.write(b, 0, referencia_n);
                    dos.flush();
                    porcentaje = (int) ((recibidos * 100) / tam);
                    if (porcentaje%5==0) 
                        System.out.println("Se ha recibido el " + porcentaje + "%");
                    
                }//cierra segundo while
                System.out.println("Archivo Recibido");
                dos.close();
                dis.close();
                cliente.close();
            }//cierra el primer while
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}