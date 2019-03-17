package Clase03;

/**@author Kaimorts**/

import java.net.*;
import java.io.*;

public class Cliente {

    public static void main(String[] args) {
        try {
            int pto = 8888;
            String dst = "127.0.0.1";
            Socket cliente = new Socket(dst, pto);
            System.out.println("Conexión establecida...\nProduciendo Objeto...\n");

            Dato d1 = new Dato("Juan", 20, "55555555", 20.5f);
            System.out.println("Se enviara objeto con los datos");
            System.out.println("Nombre:\t" + d1.getNombre()
                    + "\nEdad:\t" + d1.getEdad()
                    + "\nTelefono:\t" + d1.getTelefono()
                    + "\nSueldo(en miles):\t" + d1.getSueldo());

            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

            oos.writeObject(d1);
            oos.flush();

            System.out.println("Objeto enviado... preparando para recibir un objeto");
            Dato d2 = (Dato) ois.readObject();
            System.out.println("Nombre:\t" + d1.getNombre()
                    + "\nEdad:\t" + d1.getEdad()
                    + "\nTelefono:\t" + d1.getTelefono()
                    + "\nSueldo(en miles):\t" + d1.getSueldo());
            ois.close();
            oos.close();
            cliente.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
