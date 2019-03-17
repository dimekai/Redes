import java.sql.*;

public class ConexionDB {
    
    private Connection conexion;   
   
    String PUERTO = "3306";
    String HOST = "localhost";
    String PROTOCOLO = "jdbc:mysql://" + HOST + ":" + PUERTO + "/";
    String DATA_BASE = "AGENDA_REDES2";
    String PASSWORD = "root";
    String USER = "root";

    public ConexionDB conectar() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(PROTOCOLO + DATA_BASE, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conexion;
    }

    
    public Connection getConexion() {
            return conexion;
    }    
    public void setConexion(Connection conexion) {
            this.conexion = conexion;
    }
    
    //este metodo se encarga de realizar inserts,update y delete (sin retorno)
    public boolean ejecutar(String sql) {
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            sentencia.executeUpdate(sql);
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }        return true;
    }
    public ResultSet consultar(String sql) {
        ResultSet resultado;
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }        return resultado;
    }

}