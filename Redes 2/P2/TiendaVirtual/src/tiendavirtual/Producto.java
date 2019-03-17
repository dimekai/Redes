/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiendavirtual;
import java.io.Serializable;
import java.util.LinkedList;
/**
 *
 * @author asofia97
 */
public class Producto implements Serializable{
    String nombre;
    String descripcion;
    float precio;
    int existencia;
    float descuento;
    String imagenes;
    
    public Producto(String nombre, String descripcion, float precio,int existencia, float descuento, String imagenes){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.descuento = descuento;
        this.existencia = existencia;
        this.imagenes = imagenes;
    }
    
    String getNombre(){
        return this.nombre;
    }
    
    String getDescripcion(){
        return this.descripcion;
    }
    
    float getPrecio(){
        return this.precio;
    }
    
    int getExistencia(){
        return this.existencia;
    }
    
    float getDescuento(){
        return this.descuento;
    }    
    
    String getCadenaImagenes(){
        return this.imagenes;
    }
    
    String[] getImagenes(){
        String[] arr = this.imagenes.split("-");
        return arr;
    }
            
    void setExistencia(int existencia){
        this.existencia = existencia;
    }
}
