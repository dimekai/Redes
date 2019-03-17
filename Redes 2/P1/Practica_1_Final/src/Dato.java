import java.io.Serializable;

public class Dato implements Serializable 
{
    
    char tipo;
    String nombre;
    String dir_padre;
    
    public Dato(char tipo,String nombre,String dir_padre)
    {
        this.tipo=tipo;
        this.nombre=nombre;
        this.dir_padre=dir_padre;
    }
    
    char get_tipo()
    {
        return this.tipo;
    }
    
    String get_nombre()
    {
        return this.nombre;
    }
    
    String get_dir_padre()
    {
        return this.dir_padre;
    }
    
}
