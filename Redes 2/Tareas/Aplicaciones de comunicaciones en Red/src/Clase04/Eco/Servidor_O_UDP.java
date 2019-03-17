package Clase04.Eco;
/*@author Kaimorts*/

import java.io.*;
import java.net.*;

public class Servidor_O_UDP {
  public static void main(String[] args){
        try{
        int puerto = 8000;
        int aux = 0;
        String msj = "";
      
        DatagramSocket s = new DatagramSocket(puerto);
        System.out.println("Servidor UDP iniciado en el puerto "+s.getLocalPort());
        System.out.println("Recibiendo datos...");
        for(;;){
            DatagramPacket dp = new DatagramPacket(new byte[65100],65100);
            s.receive(dp);
            System.out.println("Datagrama recibido... extrayendo informacion");
            System.out.println("Host remoto: "+dp.getAddress().getHostAddress()+" : "+dp.getPort());
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dp.getData()));
            Objeto_U objeto;

            try{         
                objeto = (Objeto_U)ois.readObject();
                System.out.println("Datos del paquete:");
                msj += new String (objeto.getB());                    
            }   
            catch(EOFException eof){

            }
                msj = msj+"_ECHO";
                System.out.println("mensaje recibido: " + msj);
                ois.close();
                if (ois != null)
                    ois.close();
            
            msj="";
        }//for
        //s.close();
    }catch(Exception e){
        System.err.println(e);
    }
        System.out.println("___________");
        System.out.println("Termina el contenido del datagrama...");
  }//main
}//class