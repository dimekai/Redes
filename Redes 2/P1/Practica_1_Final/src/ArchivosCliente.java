/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 /**
 *
 * @author Natalia
 */
import java.io.*;
import java.net.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ArchivosCliente {

    private DataOutputStream buferSalida;
    private DataInputStream buferEntrada;
    private ObjectInputStream ois;
    private Socket cliente,enviar;
    private ServerSocket ServerRecive;
    private String host = "127.0.0.1", nombreArch, ruta, nombre, DirPadre;
    private int i = 0, puerto = 9876, porcentaje, n;
    private long enviados, tamanioArchivo;
    private long tam, r;
    private char tipo;
    
    Dato d;

    public ArchivosCliente() {

    }

    //tipo 0 envio, tipo 1 descarga
    public ArchivosCliente(File[] archivo) {
        try {
            while (i < archivo.length) {
                nombreArch = (archivo[i]).getName();
                ruta = (archivo[i]).getAbsolutePath();
                tamanioArchivo = (archivo[i]).length();
                if ((archivo[i]).isFile()) {
                    envia(archivo[i], nombreArch, ruta, tamanioArchivo, 'a', "", tipo);
                } else {
                    envia(archivo[i], nombreArch, ruta, tamanioArchivo, 'd', nombreArch, tipo);
                }
                i++;
            }
            System.out.println("\n\nArchivo enviado correctamente.\n");
            buferSalida.close();
            buferEntrada.close();
            cliente.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void recibe(String ruta, File[] DarchivoJF) {

        try {
            
            cliente = new Socket(host, puerto);
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream()); 
            dos.writeInt(DarchivoJF.length);
            dos.flush();
            ServerRecive = new ServerSocket(puerto + 1);
            enviar = ServerRecive.accept();
            ois = new ObjectInputStream(enviar.getInputStream ());
            //buferEntrada = new DataInputStream(enviar.getInputStream ());
            for (int l = 0; l < DarchivoJF.length; l++) {
                
                dos.writeUTF(DarchivoJF[l].getAbsolutePath());
                dos.flush();                
                recibe1(ruta, DarchivoJF[l]);
                
            }
            dos.close();
            //ois.close();
            enviar.close();
            ServerRecive.close();
            cliente.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recibe1(String ruta, File DarchivoJF) {

		try {
		
		d=(Dato)ois.readObject();
		r=0;
                if (d.get_tipo() == 'a') {

                    n=0;
                    porcentaje=0;
                    tam = ois.readLong();
                    if (d.get_dir_padre() == "") {
                        System.out.println("Recibiendo archivo " + d.get_nombre() + "        RUTA DESTINO " + ruta + d.get_dir_padre() + d.get_nombre());
                        buferSalida = new DataOutputStream(new FileOutputStream(ruta+d.get_dir_padre()+d.get_nombre()));
                    } else {
                        System.out.println("Recibiendo archivo " + d.get_nombre() + "        RUTA DESTINO " + ruta + d.get_dir_padre() + "\\" + d.get_nombre());
                        buferSalida = new DataOutputStream(new FileOutputStream(ruta + d.get_dir_padre() + "\\" + d.get_nombre()));
                    }
                    
                    while (r < tam) {
                        byte[] b = new byte[1500];
                        n = ois.read(b);
                        buferSalida.write(b, 0, n);
                        buferSalida.flush();
                        r += n;
                        porcentaje = (int) ((r * 100) / tam);
                        System.out.print("\rRecibido el " + porcentaje + "%");
                    }
                    
                    System.out.println("\nArchivo recibido...\n\n");

                } else {
                    System.out.println("Recibiendo directorio " + d.get_nombre() + "        RUTA DESTINO " + ruta + d.get_dir_padre());
                    File[] f = DarchivoJF.listFiles();
					boolean bol = new File(ruta + d.get_dir_padre() + "\\").mkdir();
                    System.out.println("Directorio creado...\n\n");
                    for (int j = 0; j < f.length; j++) {
                        recibe1(ruta, f[j]);
                    }
                }
		//d=null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void envia(File archivo, String nombreArch, String ruta, long tamanioArchivo, char tipo, String DirPadre, int t) {
        try {
            cliente = new Socket(host, puerto);
            buferSalida = new DataOutputStream(cliente.getOutputStream());
            buferSalida.writeInt(0);
            buferSalida.flush();
            buferSalida.writeChar(tipo);
            buferSalida.flush();
            buferSalida.writeUTF(nombreArch);
            buferSalida.flush();
            buferSalida.writeUTF(DirPadre);
            buferSalida.flush();
            if (tipo == 'a') //Si se va a copiar un archivo
            {
                System.out.println("\n\nInicia la transferencia del archivo " + nombreArch);
                enviados = 0;
                porcentaje = 0;
                buferEntrada = new DataInputStream(new FileInputStream(ruta));
                buferSalida.writeLong(tamanioArchivo);
                buferSalida.flush();
                while (enviados < tamanioArchivo) {
                    byte[] b = new byte[1500];
                    n = buferEntrada.read(b);
                    buferSalida.write(b, 0, n);							//Escribimos desde el byte 0 hasta el byte n
                    buferSalida.flush();
                    enviados += n;
                    porcentaje = (int) ((enviados * 100) / tamanioArchivo);
                    System.out.print("\rEnviados: " + porcentaje + " %");
                }
            } else {
                System.out.println("\n\nInicia la transferencia del directorio " + nombreArch);
                File[] directorio = archivo.listFiles();
                int i = 0;
                while (i < directorio.length) {
                    nombreArch = (directorio[i]).getName();
                    ruta = (directorio[i]).getAbsolutePath();
                    tamanioArchivo = (directorio[i]).length();
                    if ((directorio[i]).isFile()) {
                        envia(directorio[i], nombreArch, ruta, tamanioArchivo, 'a', DirPadre, t);
                    } else {
                        envia(directorio[i], nombreArch, ruta, tamanioArchivo, 'd', DirPadre + "\\" + nombreArch, t);
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
