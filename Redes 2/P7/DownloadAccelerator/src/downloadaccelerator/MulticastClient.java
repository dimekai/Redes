/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadaccelerator;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import sun.net.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author andressaldana
 */
public class MulticastClient {
    
    static String getChecksum(String str){
        String aux = str.split("\t")[0];
        return aux;
    }    
 
    static String getFilename(String str){
        String aux = str.split("\t")[1];
        return aux;
    }
    
    static String getPort(String str){
        String aux = str.split("\t")[2];
        return aux;
    }
    
    static String getPart(String str){
        String aux = str.split("\t")[1];
        return aux;
    }
    
    static String getLength(String str){
        String aux = str.split("\t")[0];
        return aux;
    }
    
    static String getTotal(String str){
        String aux = str.split("\t")[2];
        return aux;
    }
    
    
    public static void main(String[] args ) throws IOException{
        System.setProperty("java.net.preferIPv4Stack", "true");
        InetAddress gpo=null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Numero de servidores:");
        //number of servers
        int nos = Integer.parseInt(br.readLine());
        
        try{
            //Server config
            MulticastSocket cl= new MulticastSocket(9999);
            cl.setReuseAddress(true);
            cl.setTimeToLive(0);        
            try{
                gpo = InetAddress.getByName("228.1.1.1");
            }catch(UnknownHostException u){
                System.err.println("Direccion no valida");
            }
            cl.joinGroup(gpo);
            
            System.out.println("Unido al grupo");
            //Messages to recieve
            String msjrecv = "";
            //Sending mesages to server
            String msj = "";
            //The ones that have something to download
            LinkedList<String> ll1 = new LinkedList();
            //The ones that don't have something to download
            LinkedList<String> ll2 = new LinkedList();    
            //Download info
            LinkedList<String> ll3 = new LinkedList(); 
            
            while(true){
                //Clear search
                ll1.clear();
                ll2.clear();
                
                System.out.print("Ingresa busqueda: ");
                msj = br.readLine();
                
                //validating quit option
                if(msj.equals("salir")) break;
                
                byte[] b = msj.getBytes();
                
                for(int i = 0; i < nos; i++){
                    //Sending to all servers
                    DatagramPacket p = new DatagramPacket(b,b.length,gpo,9990+i);
                    cl.send(p);          
                }
                //saves just the first correct coincidence
                boolean aux = true;
                for(int i = 0; i < nos; i++){
                    //Responses
                    DatagramPacket precv = new DatagramPacket(new byte[150],150);
                    cl.receive(precv);
                    msjrecv = new String(precv.getData()).trim();
                    
                    //saving null response to server
                    if(msjrecv.contains("___")){
                        ll2.add(msjrecv.replace("___",""));
                        System.out.println("Sin conincidencia en puerto: "+ msjrecv.replace("___",""));
                    }
                    //saving servers with the file
                    else{
                        //first element                      
                        if(aux){
                            ll1.add(msjrecv);
                            aux = false;
                        }                        
                        else if(getChecksum(msjrecv).equals(getChecksum(ll1.get(0))) && getFilename(msjrecv).equals(getFilename(ll1.get(0)))){
                            ll1.add(msjrecv);
                        }
                    }
                }
                //Looking for download, sending the part of download and how much are in total
                String msj2;
                if(ll1.size()>0){  
                    //to download
                    int i = 1;
                    for(String item : ll1){
                        msj2 = getFilename(item)+"\t"+ll1.size()+"\t"+i;
                        b = msj2.getBytes();
                        DatagramPacket p = new DatagramPacket(b,b.length,gpo,Integer.parseInt(getPort(item)));
                        cl.send(p);
                        i++;
                    }
                    //to ignore
                    for(String item : ll2){
                        b = "___".getBytes();
                        DatagramPacket p = new DatagramPacket(b,b.length,gpo,Integer.parseInt(item));
                        cl.send(p);
                    }
                }
                //no download, hard reset
                else{
                    for(int i = 0; i < nos; i++){
                        System.out.println("hard reset");
                        b = "end".getBytes();
                        DatagramPacket p = new DatagramPacket(b,b.length,gpo,9990+i);
                        cl.send(p);
                    }
                    System.err.println("Sin coincidencias.");
                    continue;
                }
                     
                //recieve file size
                
                for(int i = 0; i < nos; i++){
                    //Responses         
                    DatagramPacket precv = new DatagramPacket(new byte[1024],1024);
                    cl.receive(precv);
                    System.out.println("Descarga");
                    //no  file recieved
                    if(new String(precv.getData()).trim().contains("___")){
                        /*DatagramPacket pnull = new DatagramPacket(new byte[1024],1024);
                        cl.receive(pnull);*/
                        System.out.println("Sin descarga desde puerto "+new String(precv.getData()).trim().replace("___",""));
                    }
                    //File size recieved
                    else{
                        //size and number part
                        int size = Integer.parseInt(getLength(new String(precv.getData()).trim()));
                        String part = getPart(new String(precv.getData()).trim());
                        String total = getTotal(new String(precv.getData()).trim());
                        ll3.add(size+"\t"+part+"\t"+total);                     
                        System.out.println(getPort(new String(precv.getData()).trim()) +": tamaño recibido, esperando descarga");
                        ///
                        DatagramPacket p = new DatagramPacket(b,b.length,gpo,Integer.parseInt(getPort(new String(precv.getData()).trim())));
                        cl.send(p);
                    }
                }
               
                try (FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+getFilename(ll1.get(0)))) {
                    for(String item: ll3){
                        DatagramPacket pfile = new DatagramPacket(new byte[Integer.parseInt(getLength(item))],Integer.parseInt(getLength(item)));
                        cl.receive(pfile);
                        fos.write(pfile.getData());
                        System.err.println("fin del ciclo");
                    }           
                }
                catch(IndexOutOfBoundsException e){
                    
                }
                
                
            }
            cl.close();
                
        }catch(Exception e){
            Logger.getLogger(MulticastServer.class.getName()).log(Level.SEVERE, null, e);
        }//catch
    }//main
}
