package Clase03;

/**
 * @author Kaimorts*
 */
import java.net.*;
import java.io.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            int pto = 8888;
            String dst = "127.0.0.1";
            ServerSocket servidor = new ServerSocket(pto);
            System.out.println("Servicio iniciado...\nEsperando clientes...\n");

            for (;;) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado desde "
                        + cliente.getInetAddress() + ":"
                        + cliente.getPort());
                
                ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

                Dato d1 = (Dato) ois.readObject();
                System.out.println("Objeto recibido con los datos\nNombre:\t" + d1.getNombre()
                        + "\nEdad:\t" + d1.getEdad()
                        + "\nTelefono:\t" + d1.getTelefono()
                        + "\nSueldo(en miles):\t" + d1.getSueldo());

                Dato d2 = new Dato("Pepe", 25, "53333333", 10.8f);
                oos.writeObject(d2);
                oos.flush();
                System.out.println("Objeto enviado.");
                ois.close();
                oos.close();
                cliente.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
