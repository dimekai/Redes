package Clase03.Agenda;

/*@author Kaimorts*/
import java.sql.*;

public class Conector_BD {
    String PUERTO = "3306";
    String HOST = "localhost";
    String PROTOCOLO = "jdbc:mysql://" + HOST + ":" + PUERTO + "/";
    String DATA_BASE = "AGENDA_REDES2";
    String PASSWORD = "root";
    String USER = "root";

    public Connection getConnection() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(PROTOCOLO + DATA_BASE, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conexion;
    }
}
