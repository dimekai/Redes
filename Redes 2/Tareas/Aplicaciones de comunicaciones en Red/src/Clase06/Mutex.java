package Clase06;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author axel
 */
public class Mutex implements Runnable{
    int cont;
    ReentrantLock rl; /*exclusión mutua*/
    
    public Mutex(){
        this.cont=0;
        rl = new ReentrantLock();
    }//constructor
    
    int getCont(){
        return this.cont;
    }//getCont
    
    public void run(){
        System.out.println("Comienza mutex...");
        //rl.lock();      /*Inicio bloque de exclusión mutua*/
        int tmp = cont;  
        try{
            Thread.sleep(100);
        }catch(InterruptedException ie){ }//catch
        try{
            tmp++;
            cont=tmp;
        }catch(Exception e){
        }finally{
               //rl.unlock(); si no se libera el candado, se cicla.
        }//finally
    }//run
    
    public static void main(String[] args){
        try{
            Mutex m = new Mutex();
            Thread t1 = new Thread(m); /*Todos comparten el contexto*/
            Thread t2 = new Thread(m);
            Thread t3 = new Thread(m);
            Thread t4 = new Thread(m);
            Thread t5 = new Thread(m);
            t1.start(); t2.start(); t3.start(); t4.start(); t5.start();
            t1.join(); t2.join(); t3.join(); t4.join(); t5.join();
            System.out.println("Cont: "+m.getCont());
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }//main
}//class

