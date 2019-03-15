/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_03;
/*@author Home */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;
import java.nio.BufferUnderflowException;
import java.util.Arrays;
import java.util.jar.Pack200;
import practica_01.*;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.tcpip.*;
import org.jnetpcap.protocol.network.*;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.protocol.network.Arp;
import org.jnetpcap.protocol.lan.IEEE802dot2;
import org.jnetpcap.protocol.lan.IEEE802dot3;

public class Captura {

    /**
     * Main startup method
     *
     * @param args ignored
     */
    private static String asString(final byte[] mac) {
        final StringBuilder buf = new StringBuilder();
        for (byte b : mac) {
            if (buf.length() != 0) {
                buf.append(':');
            }
            if (b >= 0 && b < 16) {
                buf.append('0');
            }
            buf.append(Integer.toHexString((b < 0) ? b + 256 : b).toUpperCase());
        }

        return buf.toString();
    }

    public static void main(String[] args) {

//                Checksum trama = new Checksum();
        List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
        StringBuilder errbuf = new StringBuilder(); // For any error msgs

        /**
         * *************************************************************************
         * First get a list of devices on this system
         * ************************************************************************
         */
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s", errbuf
                    .toString());
            return;
        }

        System.out.println("Network devices found:");

        int i = 0;
        try {
            for (PcapIf device : alldevs) {
                String description
                        = (device.getDescription() != null) ? device.getDescription()
                        : "No description available";
                final byte[] mac = device.getHardwareAddress();
                String dir_mac = (mac == null) ? "No tiene direccion MAC" : asString(mac);
                System.out.printf("#%d: %s [%s] MAC:[%s]\n", i++, device.getName(), description, dir_mac);

            }//for

            PcapIf device = alldevs.get(1); // We know we have atleast 1 device
            System.out
                    .printf("\nChoosing '%s' on your behalf:\n",
                            (device.getDescription() != null) ? device.getDescription()
                            : device.getName());

            /**
             * *************************************************************************
             * Second we open up the selected device
             * ************************************************************************
             */
            /*"snaplen" is short for 'snapshot length', as it refers to the amount of actual data captured from each packet passing through the specified network interface.
                64*1024 = 65536 bytes; campo len en Ethernet(16 bits) tam mÃ¡x de trama */
            int snaplen = 64 * 1024;           // Capture all packets, no trucation
            int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
            int timeout = 10 * 1000;           // 10 seconds in millis
            Pcap pcap
                    = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

            if (pcap == null) {
                System.err.printf("Error while opening device for capture: "
                        + errbuf.toString());
                return;
            }//if

            /**
             * ******F I L T R O*******
             */
            PcapBpfProgram filter = new PcapBpfProgram();
            String expression = ""; // "port 80";
            int optimize = 0; // 1 means true, 0 means false
            int netmask = 0;
            int r2 = pcap.compile(filter, expression, optimize, netmask);
            if (r2 != Pcap.OK) {
                System.out.println("Filter error: " + pcap.getErr());
            }//if
            pcap.setFilter(filter);
            /**
             * *************
             */

            /**
             * *************************************************************************
             * Third we create a packet handler which will receive packets from
             * the libpcap loop.
             * ********************************************************************
             */
            PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {

                public void nextPacket(PcapPacket packet, String user) {

                    System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n",
                            new Date(packet.getCaptureHeader().timestampInMillis()),
                            packet.getCaptureHeader().caplen(), // Length actually captured
                            packet.getCaptureHeader().wirelen(), // Original length
                            user // User supplied object
                    );

                    /**
                     * ****Desencapsulado*******
                     */
                    for (int i = 0; i < packet.size(); i++) {
                        System.out.printf("%02X ", packet.getUByte(i));
                        if (i % 16 == 15) {
                            System.out.println("");
                        }
                    }
                    System.out.println("\n\nEncabezado: " + packet.toHexdump());
                    

                    /*-------------------------Verificacion de ETHERNET-------------------------*/
                    int tipoIP;
                    //tipoIP = (int) ((packet.getUByte(12)*(Math.pow(2, 8))) + packet.getUByte(13));
                    tipoIP = (packet.getUByte(12) << 8 | packet.getUByte(13));
                    System.out.printf("IP = %02X \n",(byte)tipoIP);
                    /*--------------------------------------------------------------------------*/

                    /*-------------LONGITUD DEL PAQUETE (PDU DE IP)-----*/
                    byte[] longitudTrama = new byte[2];
                    longitudTrama[0] = (byte) (packet.getUByte(16));
                    longitudTrama[1] = (byte) (packet.getUByte(17));
                    /*--------------------------------------------------*/
                    
                    /*------------------------CHECKSUM DE LA TRAMA--------------------------*/
                    byte[] checksum = {(byte)packet.getUByte(24),(byte)packet.getUByte(25)};
                    /*---------------------------------------------------------------------*/
                    

                    if (tipoIP == 0x086DD) {  //IPV6
                        System.out.println("IPV6: No contiene Checksum");
                    } else if (tipoIP == 0x0800) { //IPV4 
                        System.out.println("IPV4");
                        
                        /*Capturar bytes del encabezado IP. 
                        Va del byte 14-33, son 20 bytes------------*/
                        byte[] IP = new byte[20];
                        for (int j = 14,i=0; j < 34;j++) 
                        {
                            IP[i]=(byte)packet.getUByte(j);
                            
                            if(j==24 || j==25){  //Lugar del Checksum
                                IP[i] = (byte)0x00;
                            }
                            i++;
                        }
                        /*Obtener Checksum*/
                        long checksumIP = Checksum.calculateChecksum(IP);
                        System.out.printf("|-- Valor de Checksum IP: %02X --|\n",checksumIP);
                        
                        /*------------------------------*/
                        int protocol = (packet.getUByte(23));
                        System.out.printf("\tProtocolo: %02X\n",(byte)protocol);

                        if ((byte)protocol == 0x06) //PROTOCOLO TCP
                        {
                            System.out.printf("PROTOCOLO TCP: %02X\n",(byte)protocol);
                            
                            System.out.println("---PROTOCOLO TCP/IP---");
                            
                            /*---------------------IHL: 45---------------------*/
                            byte IHL_limpio = (byte) (packet.getUByte(14) & 0x0000000F);
                            int IHL = (IHL_limpio * 4);           //Longitud del encabezado 
                            /*Solamente se requiere la longitud del encabezado: 5
                              La otra parte especifica la versión: 4*/
                            /*---------------------------------------------*/

                            /*---------------------PDU de IP---------------------*/
                            int longitud = (int) ((packet.getUByte(16) * (Math.pow(2, 8))) + (packet.getUByte(17)));
                            /*---------------------------------------------------*/
                            
                            /*------------Longitud que irá al Pseudo Encabezado---------*/
                            int longitudTotal = longitud - IHL;
                            System.out.println("Longitud: "+longitud+"\nIHL: "+IHL);
                            /*----------------------------------------------------------*/
                            
                            //Reservado para la longitud de encabezado (TCP O UDP)
                            byte[] pseudoEncabezado = new byte[IHL];

                            for (int j = 0; j < 4; j++) {
                                /*----------------------IP ORIGEN----------------------*/
                                pseudoEncabezado[j] = (byte) (packet.getUByte(26 + j));
                                /*-----------------------------------------------------*/

                                /*---------------------IP DESTINO---------------------*/
                                pseudoEncabezado[j + 4] = (byte) (packet.getUByte(30 + j));
                                /*----------------------------------------------------*/
                            }
                            pseudoEncabezado[8] = 0x00;

                            /*-------------------PROTOCOLO (0x06)------------------*/
                            pseudoEncabezado[9] = (byte) (packet.getUByte(23));
                            /*----------------------------------------------------*/
                            
                            /*-------------------LONGITUD-------------------------*/
                            pseudoEncabezado[10] = (byte)packet.getUByte(16);
                            pseudoEncabezado[11] = (byte)(packet.getUByte(17)-IHL);
                            /*---------------------------------------------------*/
                            
                            /*El pseudoEncabezado representa la primer mitad para obtener el Checksum*/
                            
                            /*---La segunda mitad es representada por el PDU_Trans---*/
                            byte[] PDU_Trans = new byte[longitudTotal];
                            for (int k = 34,q=0; k < 34+longitudTotal; k++,q++) 
                            {
                                PDU_Trans[q]=(byte)packet.getUByte(k);
                            }
                            /*------------------------------------------------------*/
                            
                            /*-----------UNION de PDU_Trans con pseudoEncabezado----*/
                            int length_pseudoE   = pseudoEncabezado.length;
                            int length_PDU_Trans = PDU_Trans.length;
                            
                            byte[] encabezadoFinal;
                            encabezadoFinal= new byte[length_pseudoE + length_PDU_Trans];
                            
                            System.arraycopy(pseudoEncabezado, 0, encabezadoFinal, 0, length_pseudoE);
                            System.arraycopy(PDU_Trans, 0, encabezadoFinal, length_pseudoE, length_PDU_Trans);
                            
                            long checksumTCP = Checksum.calculateChecksum(encabezadoFinal);
                            System.out.printf("Checksum del TCP: %04X \n",checksumTCP);
                            /*------------------------------------------------------*/
                        }else if ((byte)protocol == 0x11){ //PROTOCOLO UDP
                            System.out.printf("\tPROTOCOLO UDP: %02X\n",(byte)protocol);
                            
                            /*---------------------IHL: 45---------------------*/
                            byte IHL_limpio = (byte) (packet.getUByte(14) & 0x0000000F);
                            int IHL = (IHL_limpio * 4);           //Longitud del encabezado 
                            /*---------------------------------------------*/

                            /*---------------------PDU de IP---------------------*/
                            int longitud = (int) ((packet.getUByte(16) * (Math.pow(2, 8))) + (packet.getUByte(17)));
                            /*---------------------------------------------------*/
                            
                            /*------------Longitud que irá al Pseudo Encabezado---------*/
                            int longitudTotal = longitud - IHL;
                            /*----------------------------------------------------------*/
                            
                            //Reservado para la longitud de encabezado (TCP O UDP)
                            byte[] pseudoEncabezado = new byte[IHL];

                            for (int j = 0; j < 4; j++) {
                                /*----------------------IP ORIGEN----------------------*/
                                pseudoEncabezado[j] = (byte) (packet.getUByte(26 + j));
                                /*-----------------------------------------------------*/

                                /*---------------------IP DESTINO---------------------*/
                                pseudoEncabezado[j + 4] = (byte) (packet.getUByte(30 + j));
                                /*----------------------------------------------------*/
                            }
                            pseudoEncabezado[8] = 0x00;

                            /*-------------------PROTOCOLO (0x11)------------------*/
                            pseudoEncabezado[9] = (byte) (packet.getUByte(23));
                            /*----------------------------------------------------*/
                            
                            /*-------------------LONGITUD-------------------------*/
                            pseudoEncabezado[10] = (byte)packet.getUByte(16);
                            pseudoEncabezado[11] = (byte)(packet.getUByte(17)-IHL);
                            /*---------------------------------------------------*/
                            
                            /*El pseudoEncabezado representa la primer mitad para obtener el Checksum*/
                            
                            /*---La segunda mitad es representada por el PDU_Trans---*/
                            byte[] PDU_Trans = new byte[longitudTotal];
                            for (int k = 34,q=0; k < 34+longitudTotal; k++,q++) 
                            {
                                PDU_Trans[q]=(byte)packet.getUByte(k);
                            }
                            /*------------------------------------------------------*/
                            
                            /*-----------UNION de PDU_Trans con pseudoEncabezado----*/
                            int length_pseudoE   = pseudoEncabezado.length;
                            int length_PDU_Trans = PDU_Trans.length;
                            
                            byte[] encabezadoFinal;
                            encabezadoFinal= new byte[length_pseudoE + length_PDU_Trans];
                            
                            System.arraycopy(pseudoEncabezado, 0, encabezadoFinal, 0, length_pseudoE);
                            System.arraycopy(PDU_Trans, 0, encabezadoFinal, length_pseudoE, length_PDU_Trans);
                            
                            long checksumUDP = Checksum.calculateChecksum(encabezadoFinal);
                            System.out.printf("Checksum del UCP: %04X \n",checksumUDP);
                            /*------------------------------------------------------*/

                        }

                    }
                }
            };

            /**
             * *************************************************************************
             * Fourth we enter the loop and tell it to capture 10 packets. The
             * loop method does a mapping of pcap.datalink() DLT value to
             * JProtocol ID, which is needed by JScanner. The scanner scans the
             * packet buffer and decodes the headers. The mapping is done
             * automatically, although a variation on the loop method exists
             * that allows the programmer to sepecify exactly which protocol ID
             * to use as the data link type for this pcap interface.
             * ************************************************************************
             */
            pcap.loop(10, jpacketHandler, "jNetPcap rocks!");

            /**
             * *************************************************************************
             * Last thing to do is close the pcap handle
             * ************************************************************************
             */
            pcap.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
