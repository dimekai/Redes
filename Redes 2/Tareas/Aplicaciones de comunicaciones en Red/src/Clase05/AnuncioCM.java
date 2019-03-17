package Clase05;
/*AnunucioSM*/

import java.net.*;
import java.io.*;

/*Se necesita un Socket de aunucio: Multicast*/
 /*Se necesita el puerto del socket de flujo: Busqueda*/
 /*Se necesita el puerto del socket de flujo: Transferencia*/

public class AnuncioCM {

    public static void main(String[] args) {
        try {

            int pto_02 = 5678;      /*pto_01: Servidor: pto_02:Cliente*/
            String dir = "228.1.1.1";   /*Direccion de grupo clase D*/
            MulticastSocket c = new MulticastSocket(pto_02);
            c.setTimeToLive(255);   /*255 saltos para llegar lo mas lejos posible*/
            c.setReuseAddress(true);    /*Multicaste debe estar habilitada. Dir reutilizada.*/
            System.out.println("Cliente de anuncio iniciado...");

            InetAddress gpo = null;

            try {
                gpo = InetAddress.getByName(dir);
            } catch (UnknownHostException u) {
                System.err.println("Dir. Multicast no válido");
                System.exit(1);
            }

            c.joinGroup(gpo);
            /*Mapea dirección multicast a mac y genera reporte IGMP para transmitir por segmento de red*/
            for (;;) {
                DatagramPacket p = new DatagramPacket(new byte[1024],1024, gpo, pto_02); /*Solo le llegaran anuncios a cliente*/
                c.receive(p);                /*Recibe anunucio*/
                
                
                System.out.println("Direccion: "+p.getAddress());
                String anuncio = new String(p.getData(),0,p.getLength());
                System.out.println("Anuncio: "+anuncio);
                
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//main
}//class
