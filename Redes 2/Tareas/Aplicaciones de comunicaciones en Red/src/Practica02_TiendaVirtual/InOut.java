package Practica02_TiendaVirtual;

import java.io.*;
import java.util.ArrayList;

/*@author kaimorts*/

public class InOut {
    public final String FILE = "registers.txt";
    
    public ArrayList<Producto> read(){
        try {
            String[] register;
            BufferedReader br = null;
            /*--- Read all elements of catalog ---*/
            String eachRegister;
            br = new BufferedReader(new FileReader(FILE));
            ArrayList<String> catalog = new ArrayList<>();
            try {
                while ((eachRegister = br.readLine())!=null) {
                    register = eachRegister.split("\n");
                    for (int i = 0; i < register.length; i++)
                        catalog.add(register[i]);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            register = null;
            
            /*Insert registers inside of catalog of products*/
            ArrayList<Producto> product = new ArrayList<>();
            for (int i = 0; i < catalog.size(); i++) {
                register = catalog.get(i).split("_");
                //for the first line it'll print
                Producto p = new Producto(register[0],register[1],Float.parseFloat(register[2]),
                                         Integer.parseInt(register[4]),Float.parseFloat(register[3]));
                product.add(p);
            }
            /*------------------------------------*/
            return product;
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
