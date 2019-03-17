package Clase02.multiplesFiles;

/** @author Kaimorts **/

import java.net.*;
import java.io.*;
import javax.swing.JFileChooser;

public class Enviado {
    public static void main(String[] args) {
        try {
            int pto = 1234, referencia_n = 0, porcentaje = 0;
            String host = "127.0.0.1";

            JFileChooser jf = new JFileChooser();

            /*Establecemos en primer plano la seleccion*/
            jf.requestFocus();

            /*Seleccionaremos múltiples archivos*/
            jf.setMultiSelectionEnabled(true);
            int opcion = jf.showOpenDialog(null);

            /*Obtenemos archivo seleccionado*/
            if (opcion == JFileChooser.APPROVE_OPTION) {
                
                /*Creamos arreglo de los archivos seleccionados*/
                File[] file = jf.getSelectedFiles();
                
                System.out.println("# of Files: "+file.length);
                
                String[] nombre_file = new String[file.length];
                String[] path_file = new String[file.length];
                long[] tam_file = new long[file.length];
                long datos_enviados = 0;
                
                /*Recorremos todos los archivos a enviar*/
                for (int i = 0; i < file.length; i++) { /*Inicia for del i-ésimo archivo*/
                    
                    /*Creamos Socket de la conexión del Cliente*/
                    Socket cliente = new Socket(host,pto);
                    
                    /*Limpiamos*/
                    datos_enviados = 0; 
                    porcentaje = 0;
                    
                    nombre_file[i] = file[i].getName();
                    path_file[i] = file[i].getAbsolutePath();
                    tam_file[i] = file[i].length();
                    
                    System.out.println("------ FILE #"+(i+1)+"--------");
                    System.out.println("Name: "+nombre_file[i]);
                    System.out.println("Path: "+path_file[i]);
                    System.out.println("Tam: "+tam_file[i]+" bytes of data");
                    System.out.println("------------------------------");
                    
                    
                    /*Asumiendo que un cliente está conectado*/
                    System.out.println("Conexión establecida...\n"+
                                "Iniciando transferencia de archivo: "+path_file[i]);
                    
                    /*Establecemos los flujos de entrada y de salida del archivo*/
                    DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
                    DataInputStream dis = new DataInputStream(new FileInputStream(path_file[i]));
                    
                    /*Esto estará en el flujo de salida del Cliente y
                                  en el flujo de entrada del Servidor*/
                    dos.writeUTF(nombre_file[i]);
                    dos.flush();
                    dos.writeLong(tam_file[i]);
                    dos.flush();
                    
                    /*Se recupera la cantidad de bytes transmitidos a partir del archivo.
		      Recordando que el máximo tamaño de estos es de 1500.*/
                    byte[] b = new byte[1500];
                    while(datos_enviados < tam_file[i]){ /*While de transferencia*/
                        
                        /*Establecemos la referencia a partir de la cantidad de bytes
                        que hemos leído del paquete*/
                        referencia_n = dis.read(b);
                        
                        /*Mantenemos el control de los datos enviados, para reccorrer
                        la referencia a traves de todo el archivo*/
                        datos_enviados += referencia_n;
                        
                        /*Evitar uso de memoria excesivo: Establecemos un rango*/
                        dos.write(b, 0, referencia_n);
                        dos.flush();
                        
                        porcentaje = (int) ((datos_enviados * 100) / tam_file[i]);
                        
                        if (porcentaje%5==0) 
                            System.out.println("\r Transmitiendo el " + porcentaje + "% del archivo");
                        
                    } /*Cierra while de transferencia*/
                    
                    System.out.println("Archivo transmitiendo");
                    dis.close();
                    dos.close();
                    cliente.close();
                }/*Cierra for del i-ésimo archivo*/
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
