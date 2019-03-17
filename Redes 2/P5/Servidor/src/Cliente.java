import java.util.concurrent.Semaphore;

public class Cliente {
    private Semaphore conexiones;
    
    public Cliente(){
        conexiones= new Semaphore(5);
    }
    
    public void getConexion(){
        try{
            conexiones.acquire();
           
            
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        
    }
    
    public void deconectar(){
        conexiones.release();
    }
}
