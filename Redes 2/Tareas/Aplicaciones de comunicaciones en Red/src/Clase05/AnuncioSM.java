/*AnunucioSM*/

import java.net.*;
import java.io.*;

/*Se necesita un Socket de aunucio: Multicast*/
 /*Se necesita el puerto del socket de flujo: Busqueda*/
 /*Se necesita el puerto del socket de flujo: Transferencia*/
public class AnuncioSM {

    public static void main(String[] args) {
        try {
            Thread thread_clien = new Thread();
            int pto_01 = 1234, pto_02 = 5678;
            /*pto_01: Servidor: pto_02:Cliente*/
            String dir = "228.1.1.1";
            /*Direccion de grupo clase D*/
            MulticastSocket s = new MulticastSocket(pto_01);
            s.setTimeToLive(255);
            /*255 saltos para llegar lo mas lejos posible*/
            s.setReuseAddress(true);
            /*Multicaste debe estar habilitada. Dir reutilizada.*/
            System.out.println("Servicios de anuncio iniciado...");

            String msj = "Un mensaje multicast";
            byte b[] = msj.getBytes();
            InetAddress gpo = null;

            try {
                gpo = InetAddress.getByName(dir);
            } catch (UnknownHostException u) {
                System.err.println("Dir. Multicast no válido");
                System.exit(1);
            }

            s.joinGroup(gpo);
            /*Mapea dirección multicast a mac y genera reporte IGMP para transmitir por segmento de red*/
            for (;;) {
                DatagramPacket p = new DatagramPacket(b, b.length, gpo, pto_02);
                /*Solo le llegaran anuncios a cliente*/
                s.send(p);
                /*Mandar anunucio*/

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
