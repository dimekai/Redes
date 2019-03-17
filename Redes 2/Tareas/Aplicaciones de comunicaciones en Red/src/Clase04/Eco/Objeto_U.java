package Clase04.Eco;

/*@author Kaimorts*/
import java.io.Serializable;
import java.util.Arrays;

public class Objeto_U implements Serializable {
    int n;          /*segmentos de datagrama*/
    int total;      /*Peso del mensaje-datos*/
    byte[] msj;     /*Flujo de bytes del mensaje*/

    public Objeto_U(int n, int t, byte[] b) {
        this.n = n;
        this.total = t;
        this.msj = Arrays.copyOf(b, b.length);
    }

    public int getN() {
        return this.n;
    }

    public int getTotal() {
        return this.total;
    }

    public byte[] getB() {
        return this.msj;
    }
}
