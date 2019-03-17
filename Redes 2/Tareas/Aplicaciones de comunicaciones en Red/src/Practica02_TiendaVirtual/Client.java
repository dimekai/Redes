package Practica02_TiendaVirtual;
/*@author kaimorts*/
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Client {
    private final String DIR = "localhost";
    private final int PTO = 3000;
    
    public ArrayList<Producto> receiveCatalog() throws Exception{
        Socket cliente = new Socket(DIR,PTO);
        System.out.println("Connected with server successfully");
        
        /*--- Send ID ---*/
        PrintWriter pw = new PrintWriter(cliente.getOutputStream());
        pw.write("A\n");
        pw.flush();
        pw.close();
        /*---------------*/
        
        Socket cl = new Socket(DIR,PTO);
        /*--- Read catalog of products ---*/
        ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
        ArrayList<Producto> catalog = new ArrayList<>();
        try {
            while (true) {                
                catalog.add((Producto)ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        ois.close();
        return catalog;
        /*--------------------------------*/
    }
    
    public void purchaseSent(ArrayList<Producto> catalog) throws Exception{
        /*Establish connection to the server*/
        Socket cliente = new Socket(DIR,PTO);
        System.out.println("Connected with server successfully");
        /*---------------------------------*/
        
        /*------ Sent ID of purchase ------*/
        PrintWriter pw = new PrintWriter(cliente.getOutputStream());
        pw.write("B\n");
        pw.flush();
        pw.close();
        /*---------------------------------*/
        
        /*Get those selected products*/
        ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
        System.out.println("|-------------------------|");
        System.out.println("| # of Products: "+catalog.size()+" |");
        System.out.println("|-------------------------|");
        for (int i = 0; i < catalog.size(); i++) {
            oos.writeObject((Producto)catalog.get(i));
            oos.flush();
        }
        oos.close();
    }
}
