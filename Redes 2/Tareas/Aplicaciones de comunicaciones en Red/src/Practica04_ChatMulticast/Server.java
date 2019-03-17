package Practica04_ChatMulticast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/*@author kaimorts*/
public class Server {
    public static final int PTO_SERVER = 1234;  /*Servidor*/
    public static final int PTO_CLIENT = 5678;  /*Cliente*/
    public static final String DIR_GPO = "228.1.1.1";  /*Direccion de grupo*/
    
    public static void main(String[] args) {
        try{
            MulticastSocket server = new MulticastSocket(PTO_SERVER);
            server.setTimeToLive(225);      /*Saltos para llegar lo más lejos posible*/
            server.setReuseAddress(true);   /*Direccion reutilizada*/
            System.out.println("Sever Chat Multicast started ...");
            
            InetAddress GPO = null;
            try {
                GPO = InetAddress.getByName(DIR_GPO);
            } catch (UnknownHostException e) {
            }
            
                        
        }catch(Exception e){
            
        }
    }
}
