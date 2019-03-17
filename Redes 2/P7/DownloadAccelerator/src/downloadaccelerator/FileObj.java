/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadaccelerator;
import java.io.File;
import java.io.Serializable;

/**
 *
 * @author andressaldana
 */
public class FileObj implements Serializable{
    File file = null;
    String checksum = "";
    String id = "";
    
    public FileObj(File file,String checksum,String id){
        this.file = file;
        this.checksum = checksum;
        this.id = id;
    }
    
    public File getFile(){
        return this.file;
    }
    
    public String getChecksum(){
        return this.checksum;
    }
    
    public String getId(){
        return this.id;
    }
}
