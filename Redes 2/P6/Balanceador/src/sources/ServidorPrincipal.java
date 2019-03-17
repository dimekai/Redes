package sources;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ServidorPrincipal {

    public int pto;
    public ServerSocket ss;

    public ServidorPrincipal(int puerto,String index) throws Exception {
        pto=puerto;
        this.ss = new ServerSocket(pto);
        System.out.println("Servidor iniciado");
        System.out.println(ss.getInetAddress());
        for (;;) {
            System.out.println("Esperando Cliente....");
            Socket accept = ss.accept();
            new Manejador(accept,index).start();
        }
    }

    public static void main(String[] args) throws Exception {
        int puerto=0;
        String index="";
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Introduce el puerto por el que se ejecutara este servidor:");
            puerto=Integer.parseInt(br.readLine());
            System.out.println("Introduce el nombre de la pagina index con todo y extension para este servidor:");
            index=br.readLine();
        }catch (Exception e) {
            
        }
        ServidorPrincipal brisa = new ServidorPrincipal(puerto,index);
    }

}