package Practica01_Dropbox.Server_Cliente;

import Practica01_Dropbox.Clases.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JFileChooser;

/**
 * @author Kaimorts
 */
public class Cliente {

    public Cliente() {
        try{
            int pto = 8888, referencia_n = 0, porcentaje = 0;
            String host = "127.0.0.1";

            JFileChooser jf = new JFileChooser();

            /*Establecemos en primer plano la selección*/
            jf.requestFocus();

            /*Habilitamos la opción para seleccionar múltiples archivos*/
            jf.setMultiSelectionEnabled(true);
            int opcion = jf.showOpenDialog(null);

            /*Obtenemos la selección del usuario*/
            if (opcion == JFileChooser.APPROVE_OPTION) {

                /*Creamos un arreglo de los archivos seleccionados*/
                File[] files = jf.getSelectedFiles();

                System.out.println("|\t # OF FILES:\t" + files.length + " |");

                /*Obtenemos los datos del archivo*/
                Archivo[] archivo = new Archivo[files.length];
                long datos_enviados = 0;

                /*Recorremos todos los archivos para subir*/
                for (int i = 0; i < files.length; i++) {
                    
                    /*Creamos Socket de la conexión del Cliente*/
                    Socket cliente = new Socket(host, pto);
                    
                    /*Limpiamos*/
                    datos_enviados = 0;
                    porcentaje = 0;
                    
                    archivo[i] = new Archivo(files[i].getName(),
                                             files[i].getAbsolutePath(),
                                             files[i].length());
                    
                    System.out.println("------ FILE #"+(i+1)+"--------");
                    System.out.println("Name: "+archivo[i].getFile_name());
                    System.out.println("Path: "+archivo[i].getFile_path());
                    System.out.println("Tam: "+archivo[i].getFile_size()+" bytes of data");
                    System.out.println("------------------------------");
                    
                    /*Asumiendo que un cliente está conectado*/
                    System.out.println("Conexión establecida...\n"+
                                "Iniciando transferencia de archivo: "+archivo[i].getFile_path());
                    
                    ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());

                    oos.writeObject(archivo[i]);
                    oos.flush();
                    
                    System.out.println("Objeto enviado...");
                    
                    /*Se recupera la cantidad de bytes transmitidos a partir del archivo.
		      Recordando que el máximo tamaño de estos es de 1500.*/
                    byte[] b = new byte[1500];
                    while(datos_enviados < archivo[i].getFile_size()){ /*While de transferencia*/
                        
                        /*Establecemos la referencia a partir de la cantidad de bytes
                        que hemos leído del paquete*/
                        referencia_n = ois.read(b);
                        
                        /*Mantenemos el control de los datos enviados, para reccorrer
                        la referencia a traves de todo el archivo*/
                        datos_enviados += referencia_n;
                        
                        /*Evitar uso de memoria excesivo: Establecemos un rango*/
                        oos.write(b, 0, referencia_n);
                        oos.flush();
                        
                        porcentaje = (int) ((datos_enviados * 100) / archivo[i].getFile_size());
                        
                        if (porcentaje%5==0) 
                            System.out.println("\r Transmitiendo el " + porcentaje + "% del archivo");
                        
                    } /*Cierra while de transferencia*/
                    
                    System.out.println("Archivo transmitiendo");
                    
                    ois.close();
                    oos.close();
                    cliente.close();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}