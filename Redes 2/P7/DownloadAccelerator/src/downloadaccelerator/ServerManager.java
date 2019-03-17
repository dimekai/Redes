/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadaccelerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andressaldana
 */
public class ServerManager {
    public static void main(String[] args ) throws IOException, InterruptedException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Numero de servidores:");
        //number of servers
        int nos = Integer.parseInt(br.readLine());
                 
        MulticastServer ms [] = new MulticastServer[nos];
        for(int i = 0; i < nos; i++){
            ms[i] = new MulticastServer(9990+i);
            ms[i].start();                  
        }    
    }
}
