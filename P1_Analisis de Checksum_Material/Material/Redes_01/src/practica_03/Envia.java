package practica_03;

/*@author Jesus Kaimorts Díaz Medina*/

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;  
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;  
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jnetpcap.Pcap;  
import org.jnetpcap.PcapAddr;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapIf;  
import org.jnetpcap.PcapSockAddr;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import practica_01.Checksum;
  
public class Envia extends JFrame{ 
    private static JPanel contentPane;
    private static JTextField textField;
    private static JTextArea textArea;
    
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
   /*-------- JFileChooser Interface to select a File*/
        /*Create JFileChooser Object*/
            JFileChooser fc = new JFileChooser();
            String path="",nombre="";
            long tamanio;           
        /*Open the window and save the user's selection*/
            int selection = fc.showOpenDialog(contentPane);
        /*If user clicked in accept*/
            if (selection == JFileChooser.APPROVE_OPTION) {                                                       
                //Selected the File
                File fichero=fc.getSelectedFile();
      
                //Enter the path of the selected file in the text field
                path = fichero.getAbsolutePath();
                System.out.println("PATH: "+ path);
        
                tamanio = fichero.length();
                nombre = fichero.getName();
                System.out.println("Tamanio: "+tamanio);
                System.out.println("Nombre: "+nombre);                
            } 
      
    int ACK =1; //Simulation to sending data.
    int cont = 0;
      
    List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs  
    StringBuilder errbuf = new StringBuilder(); // For any error msgs  
    String ip_interfaz="";
   /***************************************************************************
		 * First get a list of devices on this system
		 **************************************************************************/
		int r = Pcap.findAllDevs(alldevs, errbuf);
		if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
			System.err.printf("Can't read list of devices, error is %s", errbuf
			    .toString());
			return;
		}

		System.out.println("Dispositivos encontrados:");
		int i = 0;
                try{
		for (PcapIf device : alldevs) {
			String description =
			    (device.getDescription() != null) ? device.getDescription()
			        : "No description available";
                        final byte[] mac = device.getHardwareAddress();
			String dir_mac = (mac==null)?"No tiene direccion MAC":asString(mac);
                        System.out.printf("\n#%d: %s [%s] MAC:[%s]\n", i++, device.getName(), description, dir_mac);
                        Iterator<PcapAddr> it = device.getAddresses().iterator();
                        while(it.hasNext()){
                            PcapAddr dir = it.next();//dir, familia, mascara,bc
                            PcapSockAddr direccion =dir.getAddr();
                            byte[]d_ip = direccion.getData();
                            int familia=direccion.getFamily();
                            int[]ipv4 = new int[4];
                            if(familia==org.jnetpcap.PcapSockAddr.AF_INET){
                                ipv4[0]=((int)d_ip[0]<0)?((int)d_ip[0])+256:(int)d_ip[0];
                                ipv4[1]=((int)d_ip[1]<0)?((int)d_ip[1])+256:(int)d_ip[1];
                                ipv4[2]=((int)d_ip[2]<0)?((int)d_ip[2])+256:(int)d_ip[2];
                                ipv4[3]=((int)d_ip[3]<0)?((int)d_ip[3])+256:(int)d_ip[3];
                                
                                System.out.println("\nIP4->"+ipv4[0]+"."+ipv4[1]+"."+ipv4[2]+"."+ipv4[3]);
                            }else if(familia==org.jnetpcap.PcapSockAddr.AF_INET6){
                                System.out.print("\nIP6-> ");
                                for(int z=0;z<d_ip.length;z++)
                                    System.out.printf("%02X:",d_ip[z]);
                            }//if
                        }//while
		}//for
                }catch(IOException io){
                  io.printStackTrace();
                }//catch
   try{
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       System.out.println("\nElije la interfaz de red:");
       int interfaz = Integer.parseInt(br.readLine());
       PcapIf device = alldevs.get(interfaz); // We know we have atleast 1 device  
       /******************************************************/
        Iterator<PcapAddr> it1 = device.getAddresses().iterator();
        while(it1.hasNext()){
         PcapAddr dir = it1.next();//dir, familia, mascara,bc
         PcapSockAddr direccion1 =dir.getAddr();
         byte[]d_ip = direccion1.getData(); //esta sera la ip origen
         int familia=direccion1.getFamily();
         int[]ipv4_1 = new int[4];
         if(familia==org.jnetpcap.PcapSockAddr.AF_INET){
            ipv4_1[0]=((int)d_ip[0]<0)?((int)d_ip[0])+256:(int)d_ip[0];
            ipv4_1[1]=((int)d_ip[1]<0)?((int)d_ip[1])+256:(int)d_ip[1];
            ipv4_1[2]=((int)d_ip[2]<0)?((int)d_ip[2])+256:(int)d_ip[2];
            ipv4_1[3]=((int)d_ip[3]<0)?((int)d_ip[3])+256:(int)d_ip[3];
            ip_interfaz = ipv4_1[0]+"."+ipv4_1[1]+"."+ipv4_1[2]+"."+ipv4_1[3];  
            System.out.println("\nInterfaz que se usara:"+ip_interfaz);
           }
        }
       /******************************************************/
       
       
        /*Catch the file was selected.*/
        //DataInputStream in = new DataInputStream(new FileInputStream(path));
        Path pathLocalization = Paths.get(path);
        File archivo = new File(path);
        FileReader archivoLeer = new FileReader(archivo);
        BufferedReader bR = new BufferedReader(archivoLeer);
        
        
       /*------------------------------------------------*/
       
       System.out.print("MAC ORIGEN: ");   
       byte[] MACo = device.getHardwareAddress();
       for(int j=0;j<MACo.length;j++)
       System.out.printf("%02X ",MACo[j]); 
        
    /***************************************** 
     * Second we open a network interface 
     *****************************************/
    
    System.out.println(" ");
    
    while(ACK==1){  //Help to continuously send data
 
        int snaplen = 1000 * 1024; // Capture all packets, no trucation  
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets  
        int timeout = 10 * 1000; // 10 seconds in millis  
        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf); 
        
        /******************************************************* 
         * Third we create our crude packet we will transmit out 
         * This creates a broadcast packet 
        *******************************************************/  
        byte[] trama = new byte[1000];
   
        for(int k=0;k<MACo.length;k++){   //Catching MAC_O
            trama[k] = (byte) 0xff;
            trama[k+6]=MACo[k];
        }// end for to catching MAC_O
        
        int counter=0;
        //int string = 0;  //Get bytes of the File

        byte[] buffer = Files.readAllBytes(pathLocalization);  //Obtain path's value in bytes
        int tam = buffer.length;
        
        System.out.println("Tamaño de buffer"+tam);
        
        for (int j = 0; j < tam; j++)      //Content in Transport Layer (TCP)
            trama[34+j] = buffer[j];
        
        String string;
        String temp = "",texto="";
        
        while( (string = bR.readLine())!= null)
        {
            temp = temp + string;
        }
        texto = temp;
        System.out.println("Texto: "+texto);
        
        

//NetworkInterface n = NetworkInterface.getByIndex(3);
////NetworkInterface n = NetworkInterface.getByName("eth3");
//System.out.println("iiiiiii: "+device.getDescription());
//NetworkInterface n = NetworkInterface.getByName(device.getDescription());        
//Enumeration ee = n.getInetAddresses();
//InetAddress IPorigen=InetAddress.getByName(ip_interfaz);
//    while (ee.hasMoreElements())
//    {
//        InetAddress ii = (InetAddress) ee.nextElement();
//        System.out.println("IP: "+ii.getHostAddress());
//        if(ii instanceof java.net.Inet4Address)
//            IPorigen = ii;
//    }
//    /////////////////////////////////////////////////////

    trama[12]= (byte) 0x16; //tipo sin asignar
    trama[13]= (byte) 0x01; //tipo sin asignar rfc 1340
    
    /* TAKE A LENGTH OF 20 */ //USING TCP/IP PROTOCOL
    trama[14] = (byte)0x45; /*Version and IHL*/
    trama[15] = (byte)0x00; /*Type of service*/
    
    trama[16] = (byte)0x01; /* Total Length 01*/
    trama[17] = (byte)0xf4; /* Total Length 02*/
    
    trama[18] = (byte)0x0a; /* ID 01*/
    trama[19] = (byte)0x1c; /* ID 02*/
    
    /*OFFSET AND FLAGS*/
    if(cont==0)
    {
        trama[20]=(byte)0x00;
        cont++;
    }else{
        trama[20]=(byte)0x01;
    }
    /**/
    
    trama[22]=(byte)0xff;   /*Time to Live (TTL)*/
    trama[23]=(byte)0x06;   /*Protocolo*/
 
    trama[24]=(byte)0x00;  /*Checksum 01*/
    trama[25]=(byte)0x00;  /*Checksum 02*/

    trama[26]=(byte)0xa8;    /*IP Origen 01*/
    trama[27]=(byte)0xb0;    /*IP Origen 02*/
    trama[28]=(byte)0x03;    /*IP Origen 03*/
    trama[29]=(byte)0x19;    /*IP Origen 04*/
 
    trama[30]=(byte)0xa8;    /*IP Destino 01*/
    trama[31]=(byte)0xb0;    /*IP Destino 02*/
    trama[32]=(byte)0x03;    /*IP Destino 03*/
    trama[33]=(byte)0x6c;    /*IP Destino 04*/
        


//    String mensaje= "Beto, soy Kai";
//    byte[]buf = mensaje.getBytes();
//    int tam = buf.length;
//    if(tam<50){
//    for(int c=0;c<tam;c++)
//        trama[14+c]=buf[c];
//    }else{
//        System.out.println("El mensaje es muy largo..maximo 50 bytes");
//        System.exit(1);
//    }  

        
    /*CALCULATING THE CHECKSUM*/
    Checksum check = new Checksum();
    long resultado = check.Operating(trama[14],trama[15],trama[16],
                                     trama[17],trama[18],trama[19],
                                     trama[20],trama[21],trama[22],
                                     trama[23],trama[26],trama[27],
                                     trama[28],trama[29],trama[30],
                                     trama[31],trama[32],trama[33]);
    
    trama[24]=(byte)(resultado/256);
    trama[25]=(byte)(resultado%256);
                
    
    /*------------------------*/
    
    //Arrays.fill(a, (byte) 0xff);  
    ByteBuffer b = ByteBuffer.wrap(trama);    
    
    /********F I L T R O********/
            PcapBpfProgram filter = new PcapBpfProgram();
            String expression ="ether proto 0x1601"; // "port 80";
            int optimize = 0; // 1 means true, 0 means false
            int netmask = 0;
            int r2 = pcap.compile(filter, expression, optimize, netmask);
            if (r2 != Pcap.OK) {
                System.out.println("Filter error: " + pcap.getErr());
            }//if
    
    pcap.setFilter(filter);
    PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {
			
    public void nextPacket(PcapPacket packet, String user) {
        System.out.printf("Paquete recibido el %s bytes capturados=%-4d tam original=%-4d %s\n",
	new Date(packet.getCaptureHeader().timestampInMillis()),
	packet.getCaptureHeader().caplen(),  // Length actually captured
	packet.getCaptureHeader().wirelen(), // Original length
	user                                 // User supplied object
	);
        /******Desencapsulado********/
        System.out.println("MAC destino:");
        for(int i=0;i<6;i++){
            System.out.printf("%02X ",packet.getUByte(i));
        }
        System.out.println("");
        System.out.println("MAC origen:");
        for(int i=6;i<12;i++){
            System.out.printf("%02X ",packet.getUByte(i));
        }
        System.out.println("");
        System.out.println("Tipo:");
        for(int i=12;i<14;i++){
            System.out.printf("%02X ",packet.getUByte(i));
        }
        int tipo = (packet.getUByte(12)*256)+packet.getUByte(13);
        System.out.printf("Tipo= %d",tipo);
        if(tipo==5633){ //0x1601
            System.out.println("\n****************Este es mi mensaje que mande\n y los datos del mensaje son:");
            byte[]t = packet.getByteArray(14, 50);
            for(int k=0;k<t.length;k++)
                System.out.printf("%02X ",t[k]);
            
            String datos = new String(t);
            System.out.println("\n"+datos);

            for(int l=0;l<packet.size();l++){
                System.out.printf("%02X ",packet.getUByte(l));
                if(l%16==15)
                System.out.println("");
            }
        }//if
        //System.out.println("\n\nEncabezado: "+ packet.toHexdump());
      }
    };
    
    //pcap.loop(1, jpacketHandler, "");
    /******************************************************* 
     * Fourth We send our packet off using open device 
     *******************************************************/  
    for(int zz=0;zz<10;zz++){
        //if (pcap.sendPacket(b) != Pcap.OK) {  
//        if(pcap.inject(b)<0){
            if (pcap.sendPacket(trama) != Pcap.OK) {  
          System.err.println(pcap.getErr());  
        }
            System.out.println("Envie un paquete******");
        try{
            Thread.sleep(500);
        }catch(InterruptedException e){}
        pcap.loop(1, jpacketHandler, "");
    }//for
    /******************************************************** 
     * Lastly we close 
     ********************************************************/  
    pcap.close();
        
    }
    
   }catch(Exception e){
       e.printStackTrace();
   }//catch
  }
    
} 

