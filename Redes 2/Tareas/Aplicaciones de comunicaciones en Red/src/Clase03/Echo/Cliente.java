
package Clase03.Echo;

/**
 *
 * @author kaimo
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args){

        try{
            int pto=1234;
            BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Escribe la ip del servidor");
            String dir=br1.readLine();
            System.out.println("Intentando conectar con servidor");
            Socket cl=new Socket(dir,pto);
            System.out.println("Conexion establecida");
            PrintWriter pw;
            BufferedReader br2;
            String datos="";
            String mensaje="";
            pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            while(true) {
                System.out.println("Ingresa tu mensaje:");
                mensaje= br1.readLine();
                                //Instacia de objetos

                pw.println(mensaje);
                pw.flush();
                if(mensaje.compareToIgnoreCase("salir")==0) {
                    break;
                }else{
                    System.out.println("no es salir");
                datos = br2.readLine();
                System.out.println("Datos recibidos : " + datos);
            }
            }
            br1.close();
            br2.close();
            cl.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}