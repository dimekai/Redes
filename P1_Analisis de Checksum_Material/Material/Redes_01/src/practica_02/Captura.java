package practica_02;

/*@author Home*/
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapAddr;
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
	 * @param args
	 *          ignored
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
            Pcap pcap=null;
               try{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));   
		List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
		StringBuilder errbuf = new StringBuilder(); // For any error msgs
                System.out.println("[0]-->Realizar captura de paquetes al vuelo");
                System.out.println("[1]-->Cargar traza de captura desde archivo");
                System.out.print("\nElige una de las opciones:");
                int opcion = Integer.parseInt(br.readLine());
                if (opcion==1){
                    
                    /////////////////////////lee archivo//////////////////////////
                //String fname = "archivo.pcap";
                String fname = "paquetes3.pcap";
                pcap = Pcap.openOffline(fname, errbuf);
                if (pcap == null) {
                  System.err.printf("Error while opening device for capture: "+ errbuf.toString());
                  return;
                 }//if
                } else if(opcion==0){
		/***************************************************************************
		 * First get a list of devices on this system
		 **************************************************************************/
		int r = Pcap.findAllDevs(alldevs, errbuf);
		if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
			System.err.printf("Can't read list of devices, error is %s", errbuf
			    .toString());
			return;
		}

		System.out.println("Network devices found:");

		int i = 0;
		for (PcapIf device : alldevs) {
			String description =
			    (device.getDescription() != null) ? device.getDescription()
			        : "No description available";
                        final byte[] mac = device.getHardwareAddress();
			String dir_mac = (mac==null)?"No tiene direccion MAC":asString(mac);
                        System.out.printf("#%d: %s [%s] MAC:[%s]\n", i++, device.getName(), description, dir_mac);
                        List<PcapAddr> direcciones = device.getAddresses();
                        for(PcapAddr direccion:direcciones){
                            System.out.println(direccion.getAddr().toString());
                        }//foreach

		}//for
                
                System.out.print("\nEscribe el número de interfaz a utilizar:");
                int interfaz = Integer.parseInt(br.readLine());
		PcapIf device = alldevs.get(interfaz); // We know we have atleast 1 device
		System.out
		    .printf("\nChoosing '%s' on your behalf:\n",
		        (device.getDescription() != null) ? device.getDescription()
		            : device.getName());
                
		/***************************************************************************
		 * Second we open up the selected device
		 **************************************************************************/
                /*"snaplen" is short for 'snapshot length', as it refers to the amount of actual data captured from each packet passing through the specified network interface.
                64*1024 = 65536 bytes; campo len en Ethernet(16 bits) tam máx de trama */

		int snaplen = 64 * 1024;           // Capture all packets, no trucation
		int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
		int timeout = 10 * 1000;           // 10 seconds in millis

                
                pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

		if (pcap == null) {
			System.err.printf("Error while opening device for capture: "
			    + errbuf.toString());
			return;
		}//if
                  
                       /********F I L T R O********/
            PcapBpfProgram filter = new PcapBpfProgram();
            String expression =""; // "port 80";
            int optimize = 0; // 1 means true, 0 means false
            int netmask = 0;
            int r2 = pcap.compile(filter, expression, optimize, netmask);
            if (r2 != Pcap.OK) {
                System.out.println("Filter error: " + pcap.getErr());
            }//if
            pcap.setFilter(filter);
                /****************/
            }//else if

		/***************************************************************************
		 * Third we create a packet handler which will receive packets from the
		 * libpcap loop.
		 **********************************************************************/
		PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {

			public void nextPacket(PcapPacket packet, String user) {

				System.out.printf("\n\nPaquete recibido el %s caplen=%-4d longitud=%-4d %s\n\n",
				    new Date(packet.getCaptureHeader().timestampInMillis()),
				    packet.getCaptureHeader().caplen(),  // Length actually captured
				    packet.getCaptureHeader().wirelen(), // Original length
				    user                                 // User supplied object
				    );
                                
                                
                                /******Desencapsulado********/
                                for(int i=0;i<packet.size();i++){
                                System.out.printf("%02X ",packet.getUByte(i));
                                
                                if(i%16==15)
                                    System.out.println("");
                                }//if
                                
                                int longitud = (packet.getUByte(12)*256)+packet.getUByte(13);
                                System.out.printf("\nLongitud: %d (%04X)",longitud,longitud );
                                if(longitud<1500){ //Longitud
                                    System.out.println("--->Trama IEEE802.3");
                                    System.out.printf(" |-->MAC Destino: %02X:%02X:%02X:%02X:%02X:%02X",packet.getUByte(0),packet.getUByte(1),packet.getUByte(2),packet.getUByte(3),packet.getUByte(4),packet.getUByte(5));
                                    System.out.printf("\n |-->MAC Origen: %02X:%02X:%02X:%02X:%02X:%02X",packet.getUByte(6),packet.getUByte(7),packet.getUByte(8),packet.getUByte(9),packet.getUByte(10),packet.getUByte(11));
                                    System.out.printf("\n |-->DSAP: %02X",packet.getUByte(14));
                                    //System.out.println(packet.getUByte(15)& 0x00000001);
                                    int ssap = packet.getUByte(15)& 0x00000001;
                                    String c_r = (ssap==1)?"Respuesta":(ssap==0)?"Comando":"Otro";
                                    System.out.printf("\n |-->SSAP: %02X   %s",packet.getUByte(15), c_r);
                                    
                                    int lengthTrama = packet.getUByte(12)<<8 | packet.getUByte(13);
                                    
                                    String campoControl="",campoControl_2="";
                                    String campoControlInv="",campoControlInv_2="",campoControl_General="";
                                    String codigo="";              //Codigo
                                    String orden, respuesta;       //Trama U
                                    String nR;                     //Número de acuse
                                    String nS;                     //Número de secuencia
                                    String nRinv="";               //Numero de acuse invertido
                                    String nSinv="";			   //Número de secuencia invertido
                                    
                                    if (lengthTrama<=3) {   //TRAMA MODO NORMAL
                                        
                                        System.out.print("\n\n |-------TRAMA MODO NORMAL -------|");
                                        campoControl = String.format("%8s", Integer.toBinaryString((byte)(packet.getUByte(16)) & 0xFF)).replace(' ', '0');
                                        System.out.print("\n  |-->C.Control : "+campoControl);
                                        for (int i = campoControl.length()-1; i>=0 ;i--) {      //Invertir campo de control
                                            campoControlInv = campoControlInv + campoControl.charAt(i);
                                        }
                                        System.out.println("\n  |-->C.Control Inv: "+campoControlInv);
                                        
                                        if (campoControlInv.charAt(0)=='1') 
                                        {
                                            if (campoControlInv.charAt(1)=='0') //Trama S
                                            {
                                                System.out.print("\n\t |------TRAMA S------|");
                                                codigo = controlFlujo_S(codigoS_normal(campoControlInv));
                                                nR = numeroAcuse_SeI(campoControlInv);
                                                System.out.print("\nCódigo S: \t"+codigo);
                                                System.out.println("\nNúmero de acuse N(R): \t"+nR); //Falta invertir y calcular en Decimal
                                                
                                                //Invertir acuse para calcular valor
                                                for (int x=nR.length()-1;x>=0;x--)
                                                    nRinv = nRinv + nR.charAt(x);
                                                
                                                System.out.print("\nN(R) inv:\t"+nRinv);
                                                System.out.print("\nN(S) inv Dec:\t"+Integer.parseInt(nRinv,2));
                                                
                                                nRinv="";//Limpiar numero de acuse

                                            }else{ //Trama U
                                                System.out.print("\n\t |------TRAMA U------|");
                                                codigo = codigoU_normal(campoControlInv);
                                                System.out.print("\nCódigo U: \t"+codigoU_normal(campoControlInv));
                                                orden = ordenU(codigo);
                                                System.out.println("\nOrden U: \t"+orden);
                                                respuesta = respuestaU(codigo);
                                                System.out.println("Respuesta U: \t"+respuesta);
                                            }
                                        }else{ // Trama de I.
                                            System.out.print("\n\t |------TRAMA I------|");
                                            nR = numeroAcuse_SeI(campoControlInv);
                                            nS = numeroSecuencia_I(campoControlInv);
                                            System.out.println("\n\t |->Número de acuse N(R):\t"+nR); //Falta invertir y calcular en Decimal
                                            System.out.println("\n\t |->Número de secuencia N(S):\t"+nS); //Falta invertir y calcular en Decimal
                                        
                                            //Invertir numero de acuse
                                            for (int x=nR.length()-1;x>=0;x--)
                                                nRinv = nRinv + nR.charAt(x);
                                            
                                            //Invertir numero de secuencia
                                            for (int x=nS.length()-1;x>=0;x--)
                                                    nSinv = nSinv + nS.charAt(x);
                                            
                                            System.out.print("\nN(S) inv:\t"+nSinv);
                                            System.out.print("\nN(S) inv Dec:\t"+Integer.parseInt(nSinv,2));
                                            System.out.print("\nN(R) inv:\t"+nRinv);
                                            System.out.print("\nN(R) inv Dec:\t"+Integer.parseInt(nRinv,2));
                                            
                                            nRinv="";//Limpiar numero de acuse
                                            nSinv="";//Limpiar numero de secuencia
                                        }
                                    }else{  //Longitud de trama >3: TRAMA MODO EXTENDIDO
                                        System.out.print("\n\n |-----------TRAMA MODO EXTENDIDO-----------|");
                                        campoControl = String.format("%8s", Integer.toBinaryString((byte)(packet.getUByte(16)) & 0xFF)).replace(' ', '0');
                                        campoControl_2 = String.format("%8s", Integer.toBinaryString((byte)(packet.getUByte(17)) & 0xFF)).replace(' ', '0');
                                        
                                        System.out.print("\n  |-->C.Control : \t"+campoControl+"-"+campoControl_2);
                                        
                                        for (int i = campoControl.length()-1; i>=0 ;i--)     //Invertir campo de control
                                            campoControlInv = campoControlInv + campoControl.charAt(i);
                                        
                                        for (int i = campoControl_2.length()-1; i>=0 ;i--)      //Invertir campo de control
                                            campoControlInv_2 = campoControlInv_2 + campoControl_2.charAt(i);
                                        
                                        campoControl_General = campoControlInv+campoControlInv_2;
                                        System.out.print("\n  |-->C.Control Inv : \t"+campoControlInv+"-"+campoControlInv_2);
                                        
                                        if (campoControl_General.charAt(0)=='1') 
                                        {
                                            if (campoControl_General.charAt(1)=='0') //Trama S
                                            {
                                                System.out.print("\n\n\t |------TRAMA S------|");
                                                codigo = controlFlujo_S(codigoS_normal(campoControl_General));
                                                nR = numeroAcuse_SeI_Extendida(campoControl_General);
                                                System.out.print("\n Código: \t"+codigo);
                                                System.out.print("\n N(R): \t\t"+nR);
                                                
                                                //Invetir número de acuse
                                                for (int x=nR.length()-1;x>=0;x--)
                                                    nRinv = nRinv + nR.charAt(x);
                                                
                                                System.out.print("\nN(R) inv:\t"+nRinv);
                                                System.out.print("\nN(S) inv Dec:\t"+Integer.parseInt(nRinv,2));
                                                nRinv="";//Limpiar numero de acuse
                                                
                                            }else{  //Trama U
                                                System.out.print("\n\t |------TRAMA U------|"); //lo mismo
                                                
                                            }
                                        }else{      //Trama I
                                            System.out.print("\n\n\t |------TRAMA I------|");
                                            nS = numeroSecuencia_I_Extendida(campoControl_General);
                                            nR = numeroAcuse_SeI_Extendida(campoControl_General);
                                            System.out.print("\nN(S):\t"+nS);
                                            System.out.print("\nN(R):\t"+nR);
                                            
                                            //Invertir numero de acuse
                                            for (int x=nR.length()-1;x>=0;x--)
                                                nRinv = nRinv + nR.charAt(x);
                                            
                                            //Invertir numero de secuencia
                                            for (int x=nS.length()-1;x>=0;x--)
                                                nSinv = nSinv + nS.charAt(x);
                                            
                                            System.out.print("\nN(S) inv:\t"+nSinv);
                                            System.out.print("\nN(S) inv Dec:\t"+Integer.parseInt(nSinv,2));
                                            System.out.print("\nN(R) inv:\t"+nRinv);
                                            System.out.print("\nN(S) inv Dec:\t"+Integer.parseInt(nRinv,2));
                                        }
                                        
                                    }                                    
                                } else if(longitud>=1500){  //Tipo
                                    System.out.println("-->Trama ETHERNET");
                                }else
                                {
                                    
                                }
                                
                                
                                //System.out.println("\n\nEncabezado: "+ packet.toHexdump());
      

			}
		};


		/***************************************************************************
		 * Fourth we enter the loop and tell it to capture 10 packets. The loop
		 * method does a mapping of pcap.datalink() DLT value to JProtocol ID, which
		 * is needed by JScanner. The scanner scans the packet buffer and decodes
		 * the headers. The mapping is done automatically, although a variation on
		 * the loop method exists that allows the programmer to sepecify exactly
		 * which protocol ID to use as the data link type for this pcap interface.
		 **************************************************************************/
		pcap.loop(-1, jpacketHandler, " ");

		/***************************************************************************
		 * Last thing to do is close the pcap handle
		 **************************************************************************/
		pcap.close();
                }catch(IOException e){e.printStackTrace();}
	}
        
//        public static String codigoS_normal(String campoControl){
//            String codigo;
//            
//            
//            return codigo;
//        }
//      
        public static String codigoS_normal(String campoControl)
        {
            String codigo=""+campoControl.charAt(2)+campoControl.charAt(3);
            return codigo;
        }
        
        public static String controlFlujo_S(String codigoS_normal){
            String controlFlujo="";
            
            switch(codigoS_normal){
                case "00":  //Listo para recibir.
                    controlFlujo = "RR";
                    break;
                    
                case "01":  //Rechazado.
                    controlFlujo = "REI";
                    break;
                    
                case "10":  //No listo para recibir.
                    controlFlujo = "RNR";
                    break;
                    
                case "11":  //Rechazo selectivo.
                    controlFlujo = "SREJ";
                    break;
            }
            
            return controlFlujo;
        }
        
        public static String numeroAcuse_SeI(String campoControl)
        {
            String nR = campoControl.charAt(5)+campoControl.charAt(6)+
                        campoControl.charAt(7)+"";
            return nR;
        }
        
        public static String numeroSecuencia_I(String campoControl)
        {
            String nS=""+campoControl.charAt(1)+campoControl.charAt(2)
                        +campoControl.charAt(3);
            return nS;
        }
        
        public static String codigoU_normal(String campoControl){
            String codigo = ""+campoControl.charAt(2)+campoControl.charAt(3)+
                               campoControl.charAt(5)+campoControl.charAt(6)+
                               campoControl.charAt(7);
            return codigo;
        }
        
        public static String ordenU(String codigo){
            String orden="";
            switch(codigo){
                case "00001": orden = "SNRM";   break;
                case "11011": orden = "SNRME";  break;
                case "11000": orden = "SARM";   break;
                case "11010": orden = "SARME";  break;
                case "11100": orden = "SABM";   break;
                case "11110": orden = "SABME";  break;
                case "00000": orden = "UI";     break;
                case "00110": orden = "-";      break;
                case "00010": orden = "DISC";   break;
                case "10000": orden = "SIM";    break;
                case "00100": orden = "UP";     break;
                case "11001": orden = "RSET";   break;
                case "11101": orden = "XID";    break;
            }
            return orden;            
        }
        
        public static String respuestaU(String codigo){
            String respuesta="";
            switch(codigo){
                case "00001": respuesta = "-";  break;
                case "11011": respuesta = "-";  break;
                case "11000": respuesta = "DM"; break;
                case "11010": respuesta = "-";  break;
                case "11100": respuesta = "-";  break;
                case "11110": respuesta = "-";  break;
                case "00000": respuesta = "UI"; break;
                case "00110": respuesta = "UA"; break;
                case "00010": respuesta = "RD"; break;
                case "10000": respuesta = "RIM";    break;
                case "00100": respuesta = "-";  break;
                case "11001": respuesta = "-";  break;
                case "11101": respuesta = "XID";    break;
            }
            return respuesta;            
        }

        public static String numeroAcuse_SeI_Extendida(String campoControl_General){
            String nR = "";
            for (int i = 9; i < campoControl_General.length(); i++) 
                nR = nR + campoControl_General.charAt(i);
            
            return nR;
        }
        
        public static String numeroSecuencia_I_Extendida(String campoControl_General){
            String nS="";
            
            for (int i = 1; i < 8; i++)
                nS = nS + campoControl_General.charAt(i);
            
            return nS;
        }

}

