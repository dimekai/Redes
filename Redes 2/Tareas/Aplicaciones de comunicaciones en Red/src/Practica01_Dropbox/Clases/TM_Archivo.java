package Practica01_Dropbox.Clases;

import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**@author Home**/
public class TM_Archivo implements TableModel {
    
    private List<Archivo> archivos;

    public TM_Archivo(List<Archivo> lista) {
        archivos = lista;
    }
    
    @Override
    public int getRowCount() {
        return archivos.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String name_column = "";
        switch(columnIndex){
            case 0:
                name_column = "Name";
                break;
            case 1: 
                name_column = "Path";
                break;
            case 2: 
                name_column = "Size";
                break;
            case 3: 
                name_column = "State";
                break;
        }
        return name_column;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Archivo archivo = archivos.get(rowIndex);
        String value = null;
        switch(columnIndex){
            case 0:
                value = archivo.getFile_name() ;
                break;
            case 1: 
                value = archivo.getFile_path();
                break;
            case 2: 
                value = ""+archivo.getFile_size();
                break;
            case 3: 
                value = archivo.getFile_state();
                break;
        }
        return value;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Archivo archivo = archivos.get(rowIndex);

        switch(columnIndex){
            case 0:
                archivo.setFile_name(aValue.toString()); ;
                break;
            case 1: 
                archivo.setFile_path(aValue.toString()); ;
                break;
            case 2: 
                archivo.setFile_size((long) aValue); ;
                break;
            case 3: 
                archivo.setFile_state(aValue.toString()); ;
                break;
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
    
}
