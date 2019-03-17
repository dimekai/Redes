package Clase06;

/**
 *
 * @author axel
 */
class Contador{
    private int vcuenta;
    public Contador(){
        vcuenta=0;
    }//constructor Contador
    
    public Contador(int i){
        vcuenta=i;
    }//constructor Contador
    
    public synchronized void incrementa(){
        int cuenta = vcuenta;   /**/
        try{
            Thread.sleep(5);     /*Cambio de contexto*/
        }catch(InterruptedException ie){}//catch
        cuenta=cuenta+1;    /*Este valor tmp lo tiene el hilo que se ejecuta
                             pero no todos los hilos*/
        vcuenta=cuenta;
    }//incrementa
    
    public synchronized int getCuenta(){
        return vcuenta;
    }//getCuenta()
}//Contador

public class MetodoSinc implements Runnable {
Contador cont;
int incrementos;

public MetodoSinc(Contador cont, int c){
    this.cont = cont;
    this.incrementos = c;
}//constructor

public void run(){
    for(int i=1;i<=incrementos;i++){
        cont.incrementa();
    }//for
}//run
public static void main(String[] args)throws Exception{
    Contador c = new Contador();
    Runnable r = new MetodoSinc(c,10);
    System.out.println("Comienza la cuenta de hilos..");
    Thread t1 = new Thread(r);
    Thread t2 = new Thread(r);
    Thread t3 = new Thread(r);
    t1.start(); t2.start(); t3.start(); /*Los pone en la cola de espera*/
    t1.join(); t2.join(); t3.join();    /*Detiene el main, y espera hasta quue ejecute t1,t2 y t3.*/
    System.out.println("El valor de la cuenta es: "+c.getCuenta());
}//main

}//class

