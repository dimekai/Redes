package Practica01_Dropbox.Server_Cliente;

import Practica01_Dropbox.Clases.Archivo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/** @author Kaimorts*/
public class Server {
    public static void main(String[] args) {
        try {
            int pto = 8888;
            String dst = "127.0.0.1";
            ServerSocket servidor = new ServerSocket(pto);
            System.out.println("Servicio iniciado...\nEsperando clientes...\n");

            for (;;) {
                /*---- Un cliente se conecta ----*/
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado desde "
                        + cliente.getInetAddress() + ":"
                        + cliente.getPort());
                /*------------------------------*/
                
                /*----- Establecemos los flujos de entrada/salida ------*/
                ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
                /*-----------------------------------------------------*/
                
                
                /*--------Instanciamos un objeto de la clase que usaremos---------*/
                Archivo archivo = (Archivo) ois.readObject();
                System.out.println("|\tObjeto recibido con los datos\t|"
                        + "\n| Name:\t" + archivo.getFile_name()
                        + "\n| Path:\t" + archivo.getFile_path()
                        + "\n| Size:\t" + archivo.getFile_size());

                oos.flush();
                System.out.println("Objeto Recibido.");
                
                long recibidos = 0;

//                /*Estará reconstruyendo el archivo enviado, a partir de la cantidad de bytes enviados*/
//                while (recibidos < tam) {
//                    byte[] b = new byte[1500];
//                    referencia_n = dis.read(b);
//                    recibidos += referencia_n;
//
//                    dos.write(b, 0, referencia_n);
//                    dos.flush();
//                    porcentaje = (int) ((recibidos * 100) / tam);
//                    if (porcentaje%5==0) 
//                        System.out.println("Se ha recibido el " + porcentaje + "%");
//                    
//                }//cierra segundo while
                
                ois.close();
                oos.close();
                cliente.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
