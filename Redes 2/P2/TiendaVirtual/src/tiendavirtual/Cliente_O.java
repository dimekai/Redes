/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiendavirtual;

import java.net.*;
import java.io.*;
import java.util.LinkedList;
/**
 *
 * @author
 */
public class Cliente_O {
    
LinkedList<Producto> recibeCatalogo() throws Exception{
        
        Socket cl = new Socket("localhost",3000);
        System.out.println("Conexion con servidor exitosa");

        //enviamos identificador
        PrintWriter printWriter = new PrintWriter(cl.getOutputStream());
        printWriter.write("A\n");
        printWriter.flush();
        printWriter.close();
        
        Socket cl1 = new Socket("localhost",3000);
        //leemos el catalogo de productos
        ObjectInputStream ois = new ObjectInputStream(cl1.getInputStream());                
        LinkedList<Producto> list = new LinkedList();

        try{    
            while(true){  
                list.add((Producto)ois.readObject());
            }                   
        }catch(EOFException e){}
        
        ois.close();
        return list;
    }
    
    void enviaCompra(LinkedList<Producto> list) throws Exception{
        
        Socket cl = new Socket("localhost",3000);
        System.out.println("Conexion con servidor exitosa");

        //enviamos identificador
        PrintWriter printWriter = new PrintWriter(cl.getOutputStream());
        printWriter.write("B\n");
        printWriter.flush();
        printWriter.close();
        
        Socket cl1 = new Socket("localhost",3000);

        ObjectOutputStream oos = new ObjectOutputStream(cl1.getOutputStream());       
        System.out.println(list.size());
        for (Producto producto: list) {
            oos.writeObject(producto);
            oos.flush();
        }     
        oos.close();
    }
    
//    public static void main(String[] args)throws Exception{
//        Cliente_O cliente = new Cliente_O();
//        cliente.recibecatalogo();
//    }
}
