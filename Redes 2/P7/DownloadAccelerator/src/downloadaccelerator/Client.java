package downloadaccelerator;

//package hello;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public Client() {     
    }
    
    public FileObj SearchFile(String port,String filename){
        String [] args = {};
        String host = (args.length < 1) ? null : args[0];
        FileObj file = null;
	try {		
	    Registry registry = LocateRegistry.getRegistry(host);		    
	    Dir stub = (Dir) registry.lookup("Dir");
            file = stub.itExists(port,filename);
            //System.out.println("Checksum: "+file.checksum+" Name:"+file.file.getName()+" Server:"+file.id);	   
            
	} catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}
        return file;
    }

    public static void main(String[] args) {

    }
}
