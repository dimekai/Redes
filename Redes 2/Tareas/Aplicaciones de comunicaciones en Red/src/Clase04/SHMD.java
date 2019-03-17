package Clase04;
/*SERVIDOR DE HOLA MUNDO DE DATAGRAMA*/

import java.net.*;
import java.io.*;

public class SHMD {
    public static void main(String[] args) {
        try {
            DatagramSocket s = new DatagramSocket(5678);
            /*  DatagramSocket s = new DatagramSocket();
		Puede o no estar ligado el server a un puerto.
		aunque el cliente si debe de estar ligado.
             */

            System.out.println("Servicio iniciado... preparando mensaje...");
            String msj = "HOLA MUNDO CON DATAGRAMAS...";
            byte[] b = msj.getBytes();
            InetAddress dst = null;

            try {
                dst = InetAddress.getByName("localhost");
            } catch (UnknownHostException u) {
                u.printStackTrace();
            }

            DatagramPacket p = new DatagramPacket(b, b.length, dst, 1234);
            for (;;) {
                s.send(p);
                /*Envio datagrama especificado*/
                try {
                    /*Despues de mandar datagrama, espero para no saturar*/
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException ie) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
