package downloadaccelerator;


import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Usuario
 */
public class Mensajes implements Serializable{
    String m;
    int v;
    
    public Mensajes(String m, int v){
        this.m=m;
        this.v=v;
    }
    public static String getMensaje(){
        return "Un mensaje corto";
    }
}
