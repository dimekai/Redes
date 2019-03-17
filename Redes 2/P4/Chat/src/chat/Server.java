package chat;

import java.net.*;
import java.io.*; 
import java.util.ArrayList;
import java.util.List; 
public class Server extends Thread{
    public static final String MCAST_ADDR = "227.0.0.1";//dir clase D valida, grupo al que nos vamos a unir
    public static final int MCAST_PORT = 9013;
    public static final int DGRAM_BUF_LEN = 512;
    public List<String> conectados = new ArrayList<String>();
    public String conect="";    
        private void addUser(String msg){
            String[] x=msg.split(">");
            conectados.add(x[1].trim());
            conect="<conectados>, ";
            for(int y=0;y<conectados.size();y++){
                conect+=conectados.get(y)+",";
            }
            conect.replace(" ", "");
        }        
        public void deleteUser(String msg){
            conectados.remove(msg.split(">")[1].trim());
            conect="<conectados>, ";
            for(int y=0;y<conectados.size();y++){
                conect+=conectados.get(y)+",";
            }        
            conect.replace(" ", "");
        }        
	public void run(){
            String msg = ""; // se cambiara para poner la ip de la maquina con lo siguiente
            InetAddress group = null;
            try{
                //msg=InetAddress.getLocalHost().getHostAddress();
                group = InetAddress.getByName(MCAST_ADDR); //se trata de resolver dir multicast  		
            }catch(UnknownHostException e){
                e.printStackTrace();
                System.exit(1);
            }
	for(;;){
            try{
                byte[] buf = new byte[DGRAM_BUF_LEN];//crea arreglo de bytes 
    		MulticastSocket socket = new MulticastSocket(MCAST_PORT); //Creamos Socket multicast
    		socket.joinGroup(group); //Nos unimos a la direccion multicast. Se configura para escuchar el paquete
                DatagramPacket recv = new DatagramPacket(buf,buf.length);//crea el datagram packet a recibir
                socket.receive(recv);// ya se tiene el datagram packet
                String inicio = new String(recv.getData());
                
                if(inicio.contains("<inicio>")){
                    addUser(inicio);
                }else if(inicio.contains("<fin>"))
                    deleteUser(inicio);
                                
    		DatagramPacket packet = new DatagramPacket(conect.getBytes(),conect.length(),group,MCAST_PORT);
    		System.out.println("Enviando: " + conect+"  con un TTL = "+socket.getTimeToLive());
    		socket.send(packet);
    		socket.close();    		
            }catch(IOException e){
    		e.printStackTrace();
    		System.exit(2);
            }

            try{
                Thread.sleep(1000*5);
            }catch(InterruptedException ie){}
        }//for;;
/*****************termina Loop***************************/    	
    }//run
    	
    public static void main(String[] args) {
	try{
	    Server mc2 = new Server();
	    mc2.start();
            System.out.println("Servidor iniciado. Esperando clientes...");
	}catch(Exception e){
            e.printStackTrace();
        }
    }//main
}//class
