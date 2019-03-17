package Clase03.Echo;

/**
 *
 * @author kaimo
 */
import jdk.nashorn.internal.ir.WhileNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Sevidor {
    public static void main(String []args){
        try{
            ServerSocket s=new ServerSocket(1234);
            System.out.println("Servicio iniciado.... esperando clientes");
            for(;;){
                Socket cl = s.accept();
                PrintWriter pw= new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));

                //cl.setSoLinger(true,5000);
                System.out.println("Cliente conectado desde "+cl.getInetAddress()+":"+cl.getPort());

                BufferedReader br2=new BufferedReader(new InputStreamReader(cl.getInputStream()));
                String datos="";
                while (true) {
                   datos=br2.readLine();
                    if(datos.compareToIgnoreCase("salir")==0) {
                        break;
                    }else {
                        System.out.println("Recibiendo datos: " + datos);
                        String msj = "Echo: " + datos;
                        pw.println(msj);//print write +"\n\r"
                        pw.flush();
                    }}
                pw.close();
                br2.close();
                cl.close();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

