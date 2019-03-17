package Clase03_CAgenda;

import java.sql.*;

/*@author kaimorts*/
public class Connector_DB {
    private Connection conexion;
    String PUERTO = "3306";
    String HOST = "localhost";
    String PROTOCOLO = "jdbc:mysql://" + HOST + ":" + PUERTO + "/";
    String DATA_BASE = "AGENDA_REDES2";
    String PASSWORD = "root";
    String USER = "root";
    
    public Connector_DB conectar(){
        Connection conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(PROTOCOLO + DATA_BASE, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return this;
    }
    
    public Connection getConexion() {
            return conexion;
    }    
    public void setConexion(Connection conexion) {
            this.conexion = conexion;
    }
    
    public boolean execute(String SQL_QUERY){
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            sentencia.executeUpdate(SQL_QUERY);
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
            return true;
    }
    
    public ResultSet consult(String sql) {
        ResultSet resultado;
        try {
            Statement sentence = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentence.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }        return resultado;
    }
}
