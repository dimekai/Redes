import java.net.*;
import java.io.*;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

    public static void main(String[] args)throws Exception{
        ServerSocket ss = new ServerSocket(8888);
        ss.setReuseAddress(true);
        System.out.println("Servidor iniciado");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd");
    
            while(true){
                Socket cl = ss.accept();
                try{
                System.out.println("Cliente conectado desde "+ cl.getInetAddress() +" en el puerto " + cl.getPort());
            
                ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
                
                Person p1 = (Person)ois.readObject();
                System.out.println("objeto recibido con los datos: \n " + p1.getFirstName() +
                 " "+ p1.getLastName() + " " + p1.getPhone() + " " + p1.getBirthDate() + " " + p1.getEmail());

                //preparando respuesta
                String firstName = "", lastName = "", phone = "", birthDate = "", email = "";
                int accion = 0;

                //vemos que accion se va a ejecutar
                int op = p1.getAction();
                ConexionDB db = new ConexionDB();
                db.conectar();
                
                    switch (op) {
                        //caso en que insertamos un usuario
                        case 1:
                                System.out.println("insercion");
                                db.ejecutar("insert into persons(firstname, lastname, phone, birthday, email) values('" +
                                p1.getFirstName() + "','" + p1.getLastName() + "','" + p1.getPhone() +
                                "','" + p1.getBirthDate() + "','" + p1.getEmail() + "');");
                                System.out.println("Insercion exitosa");
                                break;
                        //caso de consulta
                        case 2:
                                System.out.println("consulta");
                                ResultSet rs = db.consultar("select * from persons where email = '" + p1.getEmail()+
                                 "'");
                                if (rs.next() ){
                                firstName = rs.getString("firstname");
                                lastName = rs.getString("lastname");
                                phone = rs.getString("phone");
                                birthDate = rs.getString("birthday");
                                email = rs.getString("email");
                                }
                                //enviamos la respuesta
                                Person p2  = new Person(firstName,lastName,phone,birthDate,email,accion); 
                                //checando si es su party
                                LocalDateTime now = LocalDateTime.now();
                                System.out.println("Hoy es: " + dtf.format(now));
                                String sb = birthDate;
                                String date = sb.substring(5, 10);

                                if (date.equals(dtf.format(now))){                               
                                    p2.setbdDay();
                                }   

                                oos.writeObject(p2);
                                oos.flush();
                                System.out.println("Respuesta enviada");
                                break;
                        //caso de actualizacion
                        case 3:
                                System.out.println("actualizacion");
                                db.ejecutar("update persons set firstname = '" +
                                p1.getFirstName() + "', lastname = '" + p1.getLastName() + "', phone = '" + p1.getPhone() +
                                "', birthday = '" + p1.getBirthDate() + "', email = '" + p1.getEmail() + "' where email = '" + p1.getEmail() + "';");
                                System.out.println("Actualizacion exitosa");
                                break;
                        //caso de borrado
                        case 4:
                                System.out.println("borrado");
                                db.ejecutar("delete from persons where email = '" + p1.getEmail()+ "'");
                                break;
                        //caso de seleccion de todo
                        case 5:
                                System.out.println("seleccionar todo");
                                ResultSet selall = db.consultar("select * from persons");
                                List<String> emails = new ArrayList<String>();
                                String msj = "";
                                int aux=1;
                                while(selall.next() == true){
                                    msj+=aux + ".-" + selall.getString("email") + "\n";
                                    aux++;
                                }
                                System.out.println(msj);
                                oos.writeUTF(msj);
                                oos.flush();
                    }
                }catch(Exception e){
                    cl.close();
                    e.printStackTrace();
                    
                }
                cl.close();          
            }
    }
}