package Clase03.Agenda.Interfaces;

/**
 * @author Kaimorts*
 */
import Clase03.Agenda.Clases.Amigo;
import Clase03.Agenda.Clases.Persona;
import Clase03.Agenda.Conector_BD;
import Clase03.Agenda.Consulta_Friend;
import Clase03.Agenda.Consultas_BD;
import Clase03.Dato;
import java.net.*;
import java.io.*;

public class Servidor {

    public static void main(String[] args) {
        try {
            /*Establecemos objeto para conectar con la BD y
            para establecer las consultas e inserciones*/
            
            /*------------------------------------------------*/
            int pto = 8888;
            String dst = "127.0.0.1";
            ServerSocket servidor = new ServerSocket(pto);
            System.out.println("Servicio iniciado...\nEsperando clientes...\n");
            System.out.println("_____________________________________________");
            for (;;) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado desde "
                        + cliente.getInetAddress() + ":"
                        + cliente.getPort());
                
                ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
                
                Object type = ois.readObject();
                
                if (( type instanceof Persona) == true) {
                    oos.flush();
                    Consultas_BD query = new Consultas_BD();
                    Persona usuario = (Persona)type;
                    System.out.println("|--------------------------------------|");
                    System.out.println("OBJETO USUARIO recibido con los datos:");
                    System.out.println("Name:\t" + usuario.getName()
                            + "\nSurname:\t" + usuario.getSurname()
                            + "\nPhone:\t" + usuario.getPhone()
                            + "\nEmail(en miles):\t" + usuario.getEmail()
                            + "\nBirthday:\t" + usuario.getBirthday());
                    query.registro(usuario);
                    oos.flush();
                    System.out.println("|--------------------------------------|");
                    System.out.println("Objeto enviado.");
                    System.out.println("Usuario almacenado en la Base de Datos\n");

                    SessionNow session = new SessionNow();
                    session.setEmail_now(usuario.getEmail());
                    session.setUser_now(usuario.getUsername());
                    
                    System.out.println("|--------- DATOS DE SESION ----------|");
                    System.out.println("Usuario actual: ");
                    System.out.println("Username:\t" + session.getUser_now());
                    System.out.println("Email:\t" + session.getEmail_now());
                    oos.writeObject(session);
                    oos.flush();
                    System.out.println("Sesión enviada");
                    System.out.println("|------------------------------------|");
                    ois.close();
                    oos.close();
                }else if( (type instanceof Amigo)==true){
                    oos.flush();
                    Consulta_Friend query = new Consulta_Friend();
                    Amigo amigo = (Amigo)type;
                    System.out.println("|-------- INFO DE AMIGO A AGREGAR --------|");
                    System.out.println("OBJETO AMIGO con los datos.");
                    System.out.println("Name:\t" + amigo.getName()
                            + "\nSurname:\t" + amigo.getSurname()
                            + "\nPhone:\t" + amigo.getPhone()
                            + "\nEmail(en miles):\t" + amigo.getEmail()
                            + "\nBirthday:\t" + amigo.getBirthday());
                    query.addFriend(amigo);
                    System.out.println("|-----------------------------------------|");
                    System.out.println("Amigo registrado");
                }
                cliente.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
