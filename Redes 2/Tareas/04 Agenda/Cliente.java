import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Cliente{
    public static void main(String args[]){
        try{
            int pto = 8888;
            String dst = "127.0.0.1";
            Scanner reader = new Scanner(System.in);
                        
            while(true){
                
                Socket cl = new Socket(dst,pto);            

                System.out.println(" \n\n Conexion establecida, Elija una opcion: \n"
                        + "1.-Insertar Persona \n"
                        + "2.-Actualizar Persona \n"
                        + "3.-Consultar Persona \n"
                        + "4.-Eliminar Persona \n"
                        + "5.-Consultar agenda \n"
                        + "6.-Salir");
                int op = Integer.parseInt(reader.next());        

                String firstName = "", lastName = "", phone = "", birthDate = "", email = "";

                if(op ==1){
                    //caso en que insertamos un usuario
                    System.out.println("Ingresa su nombre: ");
                    firstName = reader.next();
                    System.out.println("Ingresa su apellido paterno: ");
                    lastName = reader.next();
                    System.out.println("Ingresa su numero de telefono: ");
                    phone = reader.next();
                    System.out.println("Ingresa su fecha de nacimiento (yyyy-mm-dd): ");
                    birthDate = reader.next();
                    System.out.println("Ingresa su email: ");
                    email = reader.next();
                    
                    ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());

                    Person p1  = new Person(firstName,lastName,phone,birthDate,email,1);        
                    oos.writeObject(p1);
                    oos.flush();

                    ois.close();
                    oos.close();
                    cl.close();
                }
                else if(op == 2){
                    //actualizacion de usuario
                    System.out.println("Ingresa correo (identificador): ");
                    email = reader.next();

                    ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());

                    Person p1  = new Person(firstName,lastName,phone,birthDate,email,2); 
                    oos.writeObject(p1);
                    oos.flush();

                    //recibiendo los valores actuales y en espera de nuevos valores a actualizar
                    String newfirstName, newlastName, newphone, newbirthDate;

                    Person p2 = (Person)ois.readObject();
                    System.out.println("Nombre actual: " + p2.getFirstName());
                    newfirstName = reader.next();
                    System.out.println("Apellido actual: " + p2.getLastName());
                    newlastName = reader.next();
                    System.out.println("Telefono actual: " + p2.getPhone());
                    newphone = reader.next();
                    System.out.println("Fecha de nacimiento actual: " + p2.getBirthDate());
                    newbirthDate = reader.next();

                    if(newfirstName.equals("-")){
                        newfirstName = p2.getFirstName();
                    }
                    if(newlastName.equals("-")){
                        newlastName = p2.getLastName();
                    }
                    if(newphone.equals("-")){
                        newphone = p2.getPhone();
                    }
                    if(newbirthDate.equals("-")){
                        newbirthDate = p2.getBirthDate();
                    }

                    Socket cl2 = new Socket(dst,pto);
                    //mandando los campos actualizados
                    Person p1v2  = new Person(newfirstName,newlastName,newphone,newbirthDate,p2.getEmail(),3);           
                    ObjectOutputStream oos2 = new ObjectOutputStream(cl2.getOutputStream());
                    ObjectInputStream ois2 = new ObjectInputStream(cl2.getInputStream());
                    oos2.writeObject(p1v2);
                    oos2.flush();

                    ois2.close();
                    oos2.close();
                    ois.close();
                    oos.close();
                    cl.close();
                    cl2.close();
                }
                else if(op == 3){
                    //consulta de usuario
                    System.out.println("Ingresa correo: ");
                    email = reader.next();

                    ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());

                    Person p1  = new Person(firstName,lastName,phone,birthDate,email,2); 
                    oos.writeObject(p1);
                    oos.flush();
                    
                    //recibiendo la info de la persona
                    Person p2 = (Person)ois.readObject();
                    System.out.println("Nombre: " + p2.getFirstName() +
                    "\nApellido: "+ p2.getLastName() + "\nTelefono: " + p2.getPhone() + "\nDia de nacimiento: " + p2.getBirthDate() + "\nCorreo: " + p2.getEmail());
                    if(p2.getbDday() == true){
                        System.out.println("Es su cumpleaños!!!");                        
                    }
                    ois.close();
                    oos.close();
                    cl.close();               
                }
                else if (op == 4){
                    //borrado de usuario
                    System.out.println("Ingresa correo: ");
                    email = reader.next();

                    ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());

                    Person p1  = new Person(firstName,lastName,phone,birthDate,email,4); 
                    oos.writeObject(p1);
                    oos.flush();

                    ois.close();
                    oos.close();
                    cl.close();
                }
                else if(op == 5){
                    //select all
                    ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());

                    Person p1  = new Person(firstName,lastName,phone,birthDate,email,5); 
                    oos.writeObject(p1);
                    oos.flush();
                    String msj = ois.readUTF();
                    System.out.print("\n" + msj);
                }
                else{
                    cl.close();
                    break;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
