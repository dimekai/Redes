package sources;
import java.util.concurrent.Semaphore;

public class GestorServidores {
	private Semaphore servidores;
	public GestorServidores(int servidoresCount) {
	// Create a semaphore using number of servidores we have
		this.servidores = new Semaphore(servidoresCount);
	}
	public void getConexion(int socketID) {
                System.out.println("getConexion: "+socketID);
		try {
		System.out.println("El socket #" + socketID + " esta intentando obtener una conexion con un servidor.");
		// Acquire a permit for a conection
		servidores.acquire();
		System.out.println("El socket #" + socketID + " consiguio conectarse con un servidor.");
		}
		catch (InterruptedException e) {
		e.printStackTrace();
		}
	}
	public void liberaConexion(int socketID) {
		System.out.println("El socket #" + socketID + " libero la conexion.");
		servidores.release();
	}
	/*public static void main(String[] args) {
	// Create a restaurant with two dining servidores
	Restaurante r = new Restaurante(2);
	// Create five customers
	for (int i = 1; i <= 5; i++) {
	Cliente c = new Cliente(r, i);
	c.start();
	}
	}*/
}