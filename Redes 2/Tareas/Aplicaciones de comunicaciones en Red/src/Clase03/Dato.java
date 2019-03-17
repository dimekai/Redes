package Clase03;

/**
 * @author Kaimorts*
 */
import java.io.Serializable;

public class Dato implements Serializable {

    String nombre;
    int edad;
    String telefono;
    transient float sueldo;

    public Dato(String n, int e, String t, float s) {
        this.nombre = n;
        this.edad = e;
        this.telefono = t;
        this.sueldo = s;
    }

    String getNombre() {
        return this.nombre;
    }

    int getEdad() {
        return this.edad;
    }

    String getTelefono() {
        return this.telefono;
    }

    float getSueldo() {
        return this.sueldo;
    }
}
