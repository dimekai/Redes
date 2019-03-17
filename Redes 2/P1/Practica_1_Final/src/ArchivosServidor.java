/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */
import java.net.*;
import java.io.*;
import static java.lang.Thread.sleep;

public class ArchivosServidor
{
	private ServerSocket servidor;
	private Socket cliente, enviar;
    private int i = 0;
	private int puerto = 9876, n = 0, porcentaje = 0;
	private DataInputStream dis;
	private DataOutputStream dos;
	private String nombre,DirPadre, nombreArch, ruta;
	private long tam, r;
	private char tipo;
    String rutaS="C:\\Users\\kaimo\\Desktop";
    private long enviados, tamanioArchivo;
    String carpeta="";
    private String host = "127.0.0.1";
    private ObjectOutputStream oos;
    private Dato d;

	public ArchivosServidor ()
	{
		try
		{
			servidor = new ServerSocket (puerto);
			System.out.println ("Servicio iniciado... esperando clientes.\n\n");
			for (;;)
			{
				cliente = servidor.accept();
                                System.out.println ("Cliente conectado desde -> " + cliente.getInetAddress () + ":" + cliente.getPort());
                                DataInputStream dis = new DataInputStream(cliente.getInputStream()); 
                                int h=dis.readInt();
                                if(h!=0)
                                {
                                    System.out.println("Tamano del areglo archivos: "+h);
                                    enviar = new Socket(host,puerto+1);
                                    oos = new ObjectOutputStream(enviar.getOutputStream ());
                                    //dos = new DataOutputStream(enviar.getOutputStream ());
                                    for(int k=0;k<h;k++)
                                    {
                                        File d = new File(dis.readUTF());
                                        iniciarEnvio(d);
                                    }
                                    //dos.close ();
                                    //oos.close();
                                    dis.close ();
                                    enviar.close ();
                                }
                                else{
                                    recibe(rutaS);
                                }
			}
		}catch (Exception ex)
		{
			ex.printStackTrace ();
		}
	}

	public void recibe (String ruta)
	{
		r = 0;
		try
		{
                        
			dis = new DataInputStream (cliente.getInputStream ());
			tipo = dis.readChar ();
			nombre = dis.readUTF ();
                        DirPadre = dis.readUTF ();
			if (tipo == 'a')
			{
                            
				tam = dis.readLong ();
				if(DirPadre==""){
                                    System.out.println ("Recibiendo archivo " + nombre +"        RUTA DESTINO "+ruta+DirPadre+nombre);
                                    dos = new DataOutputStream (new FileOutputStream (ruta+DirPadre+nombre));
                                }
                                else{
                                    System.out.println ("Recibiendo archivo " + nombre +"        RUTA DESTINO "+ruta+DirPadre+"\\"+nombre);
                                    dos = new DataOutputStream (new FileOutputStream (ruta+DirPadre+"\\"+nombre));
                                }
				while (r < tam)
				{
					byte [] b = new byte [1500];
					n = dis.read (b);
					dos.write (b, 0, n);
					dos.flush ();
					r += n;
					porcentaje = (int) ((r * 100) / tam);
					System.out.print ("\rRecibido el " + porcentaje + "%");
				}
				System.out.println ("\nArchivo recibido...\n\n");
                        
			}else
			{
				System.out.println ("Recibiendo directorio " + nombre + "        RUTA DESTINO "+ruta+DirPadre);
				boolean bol = new File (ruta+DirPadre+"\\").mkdir ();
                                System.out.println ("Directorio creado...\n\n");
			}

		}catch (Exception e)
		{
			e.printStackTrace ();
		}
	}
        
	public void iniciarEnvio (File archivo)
    {
        try
		{
            //cliente.close();
			System.out.println ("Servicio iniciado... para enviar archivo al cliente.\n\n");
            if (archivo.isFile ())
                envia (archivo , archivo.getName(), archivo.getAbsolutePath (), archivo.length (), 'a', "", tipo);
            else
                envia (archivo , archivo.getName(), archivo.getAbsolutePath (), archivo.length (), 'd', archivo.getName(), tipo);
                System.out.println ("\n\nArchivo enviado correctamente: "+archivo.getName()+".\n");
		}catch (Exception ex)
		{
			ex.printStackTrace ();
		}
    }
        
    public void envia (File archivo, String nombreArch, String ruta, long tamanioArchivo, char tipo, String DirPadre, int t)
    {
            try
            {
                
                    d = new Dato(tipo,nombreArch,DirPadre);
                    oos.writeObject(d);
                    oos.flush();
                    if (tipo == 'a')														//Si se va a copiar un archivo
                    {
                            System.out.println ("\n\nInicia la transferencia del archivo " + d.get_nombre());
                            enviados = 0;
                            porcentaje = 0;
                            dis = new DataInputStream (new FileInputStream (ruta));
                            oos.writeLong (tamanioArchivo);
                            oos.flush ();
                            while (enviados < tamanioArchivo)
                            {
                                    byte [] b = new byte [1500];
                                    n = dis.read(b);
                                    oos.write (b, 0, n);							//Escribimos desde el byte 0 hasta el byte n
                                    oos.flush ();
                                    enviados += n;
                                    porcentaje = (int)((enviados * 100) / tamanioArchivo);
                                    System.out.print ("\rEnviados: " + porcentaje + " %");
                            }
							
							System.out.println("\nArchivo enviado...\n\n");
                                                        oos.reset();
							
                    }else
                    {
                            System.out.println ("\n\nInicia la transferencia del directorio " + nombreArch);
                            File [] directorio = archivo.listFiles();
                            int i = 0;
                            while (i < directorio.length)
                            {
                                    if ((directorio [i]).isFile ()){
                                            envia (directorio [i], directorio [i].getName(), directorio [i].getAbsolutePath(), directorio [i].length(), 'a', DirPadre, t);
                                    }
                                    else{
                                            envia (directorio [i], directorio [i].getName(), directorio [i].getAbsolutePath(), directorio [i].length(), 'd', DirPadre+"\\"+directorio [i].getName(), t);
                                    }
                                    i ++;
                            }
                    }
                    //d=null;
            }catch (Exception e)
            {
                    e.printStackTrace ();
            }
    }
         
	public static void main(String[] args)
	{
               new ArchivosServidor ();
	}
        
}