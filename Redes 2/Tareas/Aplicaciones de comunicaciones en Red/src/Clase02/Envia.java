package Clase02;

import java.net.*;
import java.io.*;
import javax.swing.JFileChooser;

public class Envia {

    public static void main(String[] args) {
        try {
            int pto = 1234, referencia_n = 0, porcentaje = 0;
            String host = "127.0.0.1";

            JFileChooser jf = new JFileChooser();

            /*A razón de que hay varios procesos, 
			establecemos que la accion de seleccion 
			desde el JFileChooser se vaya a primer plano*/
            jf.requestFocus();
            
            
            /*Si deseamos seleccionar más de un archivo, usaremos
			jf.setMultiSelectionEnabled(true);*/
            int opcion = jf.showOpenDialog(null);
            
            /*Cachamos lo que hemos seleccionado */
            if (opcion == JFileChooser.APPROVE_OPTION) {

                File f = jf.getSelectedFile();
                /*Obtenemos archivo seleccionado*/
 /*Si se desea seleccionar multiples archivos utilizamos:
				File[] f = jf.getSelectedFiles();
				De esta manera, deberá haber un for, para que vaya leyendo archivo 
				por archivo*/

                String nombre = f.getName();
                long tam = f.length(), datos_enviados = 0;
                String path = f.getAbsolutePath();

                /*Creamos el Socket de conexión del cliente*/
                Socket cliente = new Socket(host, pto);
                /*Asumiendo que un cliente ya esta conectado*/
                System.out.println("Conexion establecida...\n"
                        + "Inicia transferencia de archivo: " + path);

                /*Establecemos los flujos de entrada y salida del archivo*/
                DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
                DataInputStream dis = new DataInputStream(new FileInputStream(path));

                /*Esto está en el flujo de salida del cliente y en 
				el flujo de entrada del servidor*/
                dos.writeUTF(nombre);
                dos.flush();
                dos.writeLong(tam);
                dos.flush();

                /*Vamos a recuperar la cantidad de bytes trasmitidos a partir del archivo.
				Recordando que el máximo tamaño de estos es de 1500.*/
                byte[] b = new byte[1500];
                while (datos_enviados < tam) {
                    referencia_n = dis.read(b);
                    datos_enviados += referencia_n;
                    /*Irá recorriendo, hasta cubrir todo el archivo*/

 /*Para evitar uso innecesario de memoria al escribir, 
					establecemos un rango de memoria, a partir del cual iniciará y acabará el ciclo*/
                    dos.write(b, 0, referencia_n);
                    dos.flush();
                    porcentaje = (int) ((datos_enviados * 100) / tam);

                    System.out.println("\r Transmitiendo el " + porcentaje + "% del archivo");

                }//Cierra while

                System.out.println("Archivo transmitiendo");
                dis.close();
                dos.close();
                cliente.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
