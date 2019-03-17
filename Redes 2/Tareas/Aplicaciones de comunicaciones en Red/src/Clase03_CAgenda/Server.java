package Clase03_CAgenda;

import java.net.*;
import java.io.*;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*@author kaimorts*/
public class Server {
    public static void main(String[] args) throws Exception {
        String SQL_QUERY="";
        int PUERTO = 8888;
        ServerSocket server = new ServerSocket(PUERTO);
        server.setReuseAddress(true);
        System.out.println("SERVER STARTED...");
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd");
        
        while(true){
            Socket cliente = server.accept();
            try {
                System.out.println("|---- CONNECTED CLIENT ----|");
                System.out.println("| FROM:\t"+cliente.getInetAddress());
                System.out.println("| PORT:\t"+cliente.getPort());
                
                ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
                
                Persona person = (Persona)ois.readObject();
                System.out.println(">---- OBJETO RECIBIDO ----<");
                System.out.println("| NAME: \t"+person.getFirstName());
                System.out.println("| LASTNAME: \t"+person.getLastName());
                System.out.println("| PHONE: \t"+person.getPhone());
                System.out.println("| EMAIL: \t"+person.getEmail());
                
                /*PREPARING RESPONSE*/
                String firstName="";        String lastName="";
                String phone="";            String birthdate="";
                String email="";            int action=0;
                
                /*GETTING OPTION OF CLIENT*/
                int option = person.getAction();
                Connector_DB db = new Connector_DB();
                db.conectar();
                
                switch(option){
                    case 1: /*INSERT*/
                        System.out.println("|------ INSERT ------|");
                        SQL_QUERY = "INSERT INTO PERSONS(FIRSTNAME,LASTNAME,PHONE,BIRTHDAY,EMAIL) VALUES(";
                        db.execute(SQL_QUERY+person.getFirstName()+","+
                                             person.getLastName()+","+
                                             person.getPhone()+","+
                                             person.getBirthdate()+","+
                                             person.getEmail()+");");
                        System.out.println("INSERTED PERSON");
                        System.out.println("|-------------------|");
                        break;
                    case 2: /*SELECT*/
                        System.out.println("|------ CONSULTING ------|");
                        SQL_QUERY = "SELECT * FROM PERSONS WHERE EMAIL = '"
                                                        +person.getEmail()+"'";
                        ResultSet rs = db.consult(SQL_QUERY);
                        
                        if (rs.next()) {
                            firstName = rs.getString("FIRSTNAME");
                            lastName = rs.getString("LASTNAME");
                            phone = rs.getString("PHONE");
                            birthdate = rs.getString("BIRTHDAY");
                            email = rs.getString("EMAIL");
                        }
                        
                        /*SEND RESPONSE*/
                        Persona person02 = new Persona(firstName,lastName,phone,
                                                       birthdate,email,action);
                        LocalDateTime now = LocalDateTime.now();
                        System.out.println("TODAY IS: " + dtf.format(now));
                        String sb = birthdate;
                        String date = sb.substring(5, 10);
                        
                        if (date.equals(dtf.format(now)))
                            person02.setB_day();
                        
                        oos.writeObject(person02);
                        oos.flush();
                        System.out.println("| -> RESPONSE SENT");
                        System.out.println("|------------------------|");
                        break;
                    case 3: /*UPDATE*/
                        System.out.println("|------ UPDATE ------|");
                        SQL_QUERY = "UPDATE PERSONS SET FIRSTNAME ='"+ person.getFirstName()
                                                      +"',LASTNAME ='"+person.getLastName()
                                                      +"',PHONE ='"+person.getPhone()
                                                      +"',BIRTHDAY ='"+person.getBirthdate()
                                                      +"',EMAIL ='"+person.getEmail()+
                                    "' WHERE EMAIL ='"+person.getEmail()+"';";
                        db.execute(SQL_QUERY);
                        System.out.println("REGISTER UPDATE");
                        System.out.println("|------------------------|");
                        break;
                    case 4: /*DELETE*/
                        System.out.println("|------- DELETE ----------|");
                        SQL_QUERY = "DELETE FROM PERSONS WHERE EMAIL ='"+person.getEmail()+"'";
                        db.execute(SQL_QUERY);
                        System.out.println("| REGISTER DELETED");
                        System.out.println("|-------------------------|");
                        break;
                    case 5: /*SELECT * FROM*/
                        System.out.println("|------- SELECT ALL -------|");
                        SQL_QUERY = "SELECT * FROM PERSONS";
                        ResultSet SELECT_ALL = db.consult(SQL_QUERY);
                        List<String> emails = new ArrayList<String>();
                        String msj = "";
                        int aux=1;
                        while(SELECT_ALL.next() == true){
                            msj+=aux + ".-" + SELECT_ALL.getString("EMAIL") + "\n";
                            aux++;
                        }
                        System.out.println(msj);
                        oos.writeUTF(msj);
                        oos.flush();
                        System.out.println("|--------------------------|");
                        break;
                }
            } catch (Exception e) {
                cliente.close();
                e.printStackTrace();
            }
            cliente.close();
        }
    }
}
