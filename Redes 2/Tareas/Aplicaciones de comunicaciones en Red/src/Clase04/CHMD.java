package Clase04;

import java.net.*;
import java.io.*;

public class CHMD {
    public static void main(String[] args) {
        try {
            DatagramSocket cliente = new DatagramSocket(1234);
            System.out.println("Cliente iniciado, escuchando anuncios...");
            for (;;) {
                DatagramPacket p = new DatagramPacket(new byte[65535], 65535);
                cliente.receive(p);
                String msj = new String(p.getData(), 0, p.getLength());
                System.out.println("Mensaje recibido desde: "
                        + p.getAddress() + ":" + p.getPort()
                        + "Con el mensaje: " + msj);
            }//for
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
