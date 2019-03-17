package Practica02_TiendaVirtual;

import java.io.Serializable;

/*@author kaimorts*/
public class Producto implements Serializable {
    
    private String nombre;
    private String descripcion;
    private float precio;
    private int existencia;
    private float descuento;
    
    public Producto(){}

    public Producto(String nombre, String descripcion, float precio, int existencia, float descuento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencia = existencia;
        this.descuento = descuento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }    
}
