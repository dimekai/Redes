package sources;
import java.util.Random;
import java.net.*;
import java.io.*;
class ConexionServidores extends Thread {
	private GestorServidores r;
	private int socketID,puerto;
	private String mensaje,respuesta,disponibilidad;
	public ConexionServidores(GestorServidores r, int socketID,int puerto,String mensaje) {
		this.r = r;
		this.socketID = socketID;
		this.mensaje=mensaje;
		this.puerto=puerto;
		respuesta="";
		disponibilidad="True";

	}
	public void run() {
		r.getConexion(this.socketID); // Get a Conection
		disponibilidad="False";
		try {
			InetAddress srv = InetAddress.getByName("127.0.0.1");
            Socket cl = new Socket(srv,puerto);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
           	System.out.println("El socket #"+socketID+" esperara 2 s.");
			sleep(2000);
            pw.println(mensaje);
            pw.flush();
            for(String linea = br.readLine(); linea != null; linea = br.readLine()){
        		respuesta+=linea;
    		}
            pw.close();
            br.close();
            cl.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			r.liberaConexion(this.socketID);
		}
	}

	public String getMensaje(){
		return respuesta;
	}

	public int getPuerto(){
		return puerto;
	}

	public String getDisponibilidad(){
		return disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad){
		this.disponibilidad=disponibilidad;
	}
}
