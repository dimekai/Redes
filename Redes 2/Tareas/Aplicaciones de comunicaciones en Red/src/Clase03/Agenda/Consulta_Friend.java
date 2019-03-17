package Clase03.Agenda;

import Clase03.Agenda.Clases.Amigo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Clase03.Agenda.Interfaces.*;

/*@author Kaimorts*/
public class Consulta_Friend {
    
    String TABLA = "AMIGO";
    Conector_BD database; 
    Connection cn;
    
    public Consulta_Friend() {
        database = new Conector_BD();
        cn = database.getConnection();
    }
    
    public void addFriend(Amigo user) {
        if (verifyRegister(user) == true) {
            try {
                PreparedStatement PSInsert,PS_email;
                Statement st = null, st1=null;
                Connection con = null;
                
                Conector_BD database = new Conector_BD();
                con = database.getConnection();
                st = con.createStatement();
                
                String SQL_InsertCliente = "INSERT INTO AMIGO(EMAIL_AMIGO,NAME,SURNAME,PHONE,BIRTHDAY,EMAIL)"+
                                            " VALUES(?,?,?,?,?,?)";
                
                PSInsert = con.prepareStatement(SQL_InsertCliente);
                PSInsert.setString(1, user.getEmail_amigo());
                PSInsert.setString(2, user.getName());
                PSInsert.setString(3, user.getSurname());
                PSInsert.setString(4, user.getPhone());
                PSInsert.setString(5, user.getBirthday());
                PSInsert.setString(6, user.getEmail());
                
                PSInsert.executeUpdate();
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public String whatID(String email_contact){
        String SQL_getID = "SELECT EMAIL FROM PERSONA WHERE EMAIL='"+email_contact+"'";
        String ID="";
        Statement st;
        try{
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL_getID);
            while(rs.next()){
                ID = rs.getString(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return ID;
    }
    
    public boolean verifyRegister(Amigo friend) {
        boolean continuar;
        if (("".equals(friend.getName())) || ("".equals(friend.getSurname())) ||
            ("".equals(friend.getEmail())) || ("".equals(friend.getBirthday())) ||
            ("".equals(friend.getPhone())) ) {

            JOptionPane.showMessageDialog(null, "Campos incompletos", "AVISO", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            continuar = false;
        } else {
            continuar = true;
        }
        return continuar;
    }
}
