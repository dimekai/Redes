package downloadaccelerator;

//package hello;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Dir extends Remote {
    FileObj itExists(String id, String fileName) throws RemoteException;
}
