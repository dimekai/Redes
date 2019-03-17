/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloadaccelerator;

import java.io.File;
import java.io.Serializable;
import downloadaccelerator.MD5Checksum;
import static downloadaccelerator.MD5Checksum.getMD5Checksum;
/**
 *
 * @author andressaldana
 */
public class FileList implements Serializable{    
    
    public FileObj itExists(String id, String fileName){
        
        File folder = new File(System.getProperty("user.dir")+"/"+id);
        File[] listOfFiles = folder.listFiles();
        File file = null;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(listOfFiles[i].getName().contains(fileName)){
                    file = new File(System.getProperty("user.dir")+"/"+id+"/"+listOfFiles[i].getName());
                }
            } else if (listOfFiles[i].isDirectory()) {
            }
        }
        try{
            String hash = getMD5Checksum(System.getProperty("user.dir")+"/"+id);
            FileObj obj = new FileObj(file,hash,id);
            return obj;
        }catch(Exception e){
            System.err.println("Error al generar el hash");
            return null;
        }
        
    }
    public static void main(String args[]){
        FileList fl = new FileList();
        //File file = fl.itExists("12.455.67","prin");
        //System.out.println(file.length());
    }
}
