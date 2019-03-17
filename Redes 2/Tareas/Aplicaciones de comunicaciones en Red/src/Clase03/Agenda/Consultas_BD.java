package Clase03.Agenda;
/*@author Kaimorts*/

import Clase03.Agenda.Clases.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Clase03.Agenda.Interfaces.*;

public class Consultas_BD {
    
    Conector_BD DATABASE; 
    String ID;
        
    public Consultas_BD() {
        DATABASE = new Conector_BD();
        DATABASE.getConnection();
    }
    
    public boolean verifyRegister(Persona user) {
        boolean continuar;
        if (("".equals(user.getName())) || ("".equals(user.getSurname())) ||
            ("".equals(user.getEmail())) || ("".equals(user.getPassword())) ||
            ("".equals(user.getBirthday()))) {

            JOptionPane.showMessageDialog(null, "Campos incompletos", "AVISO", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            continuar = false;
        } else {
            continuar = true;
        }
        return continuar;
    }

    public void registro(Persona user) {
        if (verifyRegister(user) == true) {
            try {
                PreparedStatement PSInsert,PS_email;
                Statement st = null, st1=null;
                Connection con = null;
                
                Conector_BD database = new Conector_BD();
                con = database.getConnection();
                st = con.createStatement();

                String SQL_InsertCliente = "INSERT INTO persona(NAME,SURNAME,PHONE,BIRTHDAY,EMAIL,PSSW,PSEUDO_CLAVE)"+
                                            " VALUES(?,?,?,?,?,?,?)";
                
                System.out.println("USERNAME: "+user.getUsername());
                
                PSInsert = con.prepareStatement(SQL_InsertCliente);
                PSInsert.setString(1, user.getName());
                PSInsert.setString(2, user.getSurname());
                PSInsert.setString(3, user.getPhone());
                PSInsert.setString(4, user.getBirthday());
                PSInsert.setString(5, user.getEmail());
                PSInsert.setString(6, user.getPassword());
                PSInsert.setString(7, user.getUsername());
                
                PSInsert.executeUpdate();
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
