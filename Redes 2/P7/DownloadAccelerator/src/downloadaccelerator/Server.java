package downloadaccelerator;

//package hello;
import static downloadaccelerator.MD5Checksum.getMD5Checksum;
import java.io.File;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Dir {

    public Server() {
    }

    public FileObj itExists(String id, String fileName) throws RemoteException{
        
        File folder = new File(System.getProperty("user.dir")+"/"+id);
        File[] listOfFiles = folder.listFiles();
        File file = null;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(listOfFiles[i].getName().contains(fileName)){
                    file = new File(System.getProperty("user.dir")+"/"+id+"/"+listOfFiles[i].getName());
                }
            } else if (listOfFiles[i].isDirectory()) {
            }
        }
        try{
            String hash = getMD5Checksum(file.getAbsolutePath());
            FileObj obj = new FileObj(file,hash,id);
            return obj;
        }catch(Exception e){
            //Logger.getLogger(MulticastServer.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("No existe el archivo");
            return null;
        }
        
    }

    public static void main(String args[]) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099); //puerto default del rmiregistry
            System.out.println("RMI registry ready.");
        } catch (Exception e) {
            System.out.println("Exception starting RMI registry:");
            e.printStackTrace();
        }//catch

        try {
            System.setProperty("java.rmi.server.codebase", "http://8.25.100.18/clases/"); ///file:///f:\\redes2\\RMI\\RMI2
            Server obj = new Server();
            Dir stub = (Dir) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Dir", stub);

            System.err.println("Servidor listo...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
