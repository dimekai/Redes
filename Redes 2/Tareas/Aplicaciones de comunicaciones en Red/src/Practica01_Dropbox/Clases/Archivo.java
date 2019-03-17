package Practica01_Dropbox.Clases;

import java.io.Serializable;

/**@author Home*/
public class Archivo implements Serializable{
    private String file_name;
    private String file_path;
    private long file_size;
    private String file_state;
    
    public Archivo(){}
    
    public Archivo(String file_name, String file_path, long file_size) {
        this.file_name = file_name;
        this.file_path = file_path;
        this.file_size = file_size;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public String getFile_state() {
        return file_state;
    }

    public void setFile_state(String file_state) {
        this.file_state = file_state;
    }
    
    
}
