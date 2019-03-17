/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadaccelerator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author andressaldana
 */
public class MulticastServer extends Thread{
    int port;
    String checksum,filename;
    
    public MulticastServer(int port){
        this.port = port;
    }
    
    static byte[] getfilePart(String part, String total, String filename, int port) throws IOException{  
        Path path = Paths.get(System.getProperty("user.dir")+"/"+port+"/"+filename);
        byte[] data = Files.readAllBytes(path);
        
        //gets the portion to be downloaded
        int start = divide("1",total,data.length);
        int end = divide(part,total,data.length);
        byte[] filePart = new byte[end];
        //start on 0
        if(part.equals("1")){
            System.arraycopy(data, 0, filePart, 0, end);
            //System.out.println("0-"+(end-1));
        }
        //end
        else if(part.equals(total)){
            System.arraycopy(data, start*(Integer.parseInt(total)-1), filePart, 0, end);
            //System.out.println(start*(Integer.parseInt(total)-1)+"-"+(data.length-1));
        }
        //middle
        else{
            System.arraycopy(data, start*(Integer.parseInt(part)-1), filePart, 0, end);
            //System.out.println(start*(Integer.parseInt(part)-1)+"-"+((start*(Integer.parseInt(part)))-1));
        }
        return filePart;
    }
    
    static int divide(String part, String total, int size){
        int x = size;
        int y = Integer.parseInt(total);
        int div = x/y;
        if(y == Integer.parseInt(part)){
            return x-(div*(y-1));
        }
        else{
            return div;
        }     
    }
    
    static String getFilename(String str){
        String aux = str.split("\t")[0];
        return aux;
    }
    
    static String getTotal(String str){
        String aux = str.split("\t")[1];
        return aux;
    }
    
    static String getPart(String str){
        String aux = str.split("\t")[2];
        return aux;
    } 
    
    public void run(){      
        System.setProperty("java.net.preferIPv4Stack", "true");
        InetAddress gpo=null;
        //RMI client
        Client RMIClient = new Client();
        //carries the found file
        FileObj file = null;
        //message to client
        String msjsend = "";
        
        try{
            //Setting up server
            MulticastSocket s= new MulticastSocket(port);
            s.setReuseAddress(true);
            s.setTimeToLive(0);
            try{
                gpo = InetAddress.getByName("228.1.1.1");
            }catch(UnknownHostException u){
                System.err.println("Direccion no valida");
            }
            s.joinGroup(gpo);
            System.out.println(port+" Esperando por mensaje...");
            //waiting for packages
            String msj = "";
            while(true){
                //validating exit option
                if(msj.equals("salir")) break;
                //else...
                DatagramPacket p = new DatagramPacket(new byte[150],150);
                s.receive(p);
                msj = new String(p.getData()).trim();
                System.out.println(port+" busqueda recibida: "+msj);
                //Searching for existing file
                if(RMIClient.SearchFile(""+port,msj) != null){
                    file = RMIClient.SearchFile(""+port,msj);
                    this.checksum = file.checksum;
                    this.filename = file.id;
                    msjsend = file.checksum+"\t"+file.file.getName()+"\t"+file.id;
                    System.out.println(port+": el archivo "+msj+" existe");
                }
                //if not exists...
                else{
                    System.out.println(port+": archivo "+msj+" no existente");
                    msjsend = "___"+port;
                }
                //client response
                byte[] b  = msjsend.getBytes();
                DatagramPacket psend = new DatagramPacket(b,b.length,gpo,9999);
                s.send(psend);
                
                //client request for download
                DatagramPacket precv = new DatagramPacket(new byte[150],150);
                s.receive(precv);
                msj = new String(precv.getData()).trim();
                
                //hard reset
                if(msj.equals("end")){
                    System.out.println("hard reset");
                    continue;
                }
                //download anything
                else if(msj.equals("___")){
                    System.out.println(port+": Sin peticion de descarga, bye");
                    byte[] bdnd  = ("___"+port).getBytes();
                    DatagramPacket psend_dnd = new DatagramPacket(bdnd,bdnd.length,gpo,9999);
                    s.send(psend_dnd);            
                }
                //download file
                else{
                    System.out.println("peticion de descarga: "+msj); 
                    //System.out.println(getPart(msj)+" "+getTotal(msj)+" "+getFilename(msj)+" "+port);
                    byte[] bdnd  = getfilePart(getPart(msj),getTotal(msj),getFilename(msj),port);
                    byte[] bsize = (""+bdnd.length+"\t"+getPart(msj)+"\t"+port+"\t"+getTotal(msj)).getBytes();
                    DatagramPacket psend_size = new DatagramPacket(bsize,bsize.length,gpo,9999);
                    s.send(psend_size);  
                    System.out.println(port+" Tamaño enviado: "+bdnd.length+"\t"+getPart(msj)+"\t"+port+"\t"+getTotal(msj));
                    
                    //wait for size sending
                    System.out.println(port+" esperando para enviar parte");
                    DatagramPacket pwait = new DatagramPacket(new byte[300],300);
                    s.receive(pwait);
                                    
                    DatagramPacket psend_dnd = new DatagramPacket(bdnd,bdnd.length,gpo,9999);
                    System.out.println(port+" parte enviada");                    
                    s.send(psend_dnd); 
                    s.close();
                }        
            }
            
        }catch(Exception e){
            Logger.getLogger(MulticastServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static void main(String[] args ) throws IOException, InterruptedException{
        byte[] a = getfilePart("1", "2", "aa.txt", 9990);
        byte[] b = getfilePart("2", "2", "aa.txt", 9990);
        
        try (FileOutputStream fos = new FileOutputStream("/Users/andressaldana/Documents/Github/Net-Applications/DownloadAccelerator/9990/c.txt")) {
            fos.write(a);
            fos.write(b);
        }
    }
}
