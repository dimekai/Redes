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
public class Servidor_O {
    
    public static void main(String[] args)throws Exception{
        
        /*trae todos los objetos del inventorio*/
        LinkedList<Producto> list;
        Reader r = new Reader();
        list= r.Read();
        
        ServerSocket ss = new ServerSocket(3000);
        ss.setReuseAddress(true);
        System.out.println("Servidor iniciado");
        while(true){
            Socket cl = ss.accept();
            System.out.println("Cliente conectado desde "+ cl.getInetAddress() +" en el puerto " + cl.getPort());
            
            //recibe identificador
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(cl.getInputStream()));
            String op = bufferedReader.readLine();
            bufferedReader.close();
            
            //
            if(op.equals("A")){
                //envio de catalogo de productos
                Socket cl1 = ss.accept();
                System.out.println("enviando productos...");
                ObjectOutputStream oos = new ObjectOutputStream(cl1.getOutputStream());       

                for(Producto producto: list) {
                    oos.writeObject(producto);
                    oos.flush();
                }
                oos.close();
            }
            else{
                Socket cl1 = ss.accept();
                System.out.println("actualizando productos...");
                ObjectInputStream ois = new ObjectInputStream(cl1.getInputStream());                
                LinkedList<Producto> newlist = new LinkedList();

                try{    
                    while(true){  
                        newlist.add((Producto)ois.readObject());
                    }                   
                }catch(EOFException e){}
                
                Reader writer = new Reader();
                writer.Write(newlist);
            }           
        }
    }
}
