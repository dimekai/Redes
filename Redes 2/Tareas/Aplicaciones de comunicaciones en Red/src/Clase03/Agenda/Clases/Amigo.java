package Clase03.Agenda.Clases;

import java.io.Serializable;

/**
 *
 * @author Kaimorts
 */
public class Amigo implements Serializable{

    private String Name;
    private String Surname;
    private String Email;
    private String Phone;
    private String Birthday;
    private String Email_amigo;
    
    public Amigo() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }


    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String Bithday) {
        this.Birthday = Bithday;
    }

    public String getEmail_amigo() {
        return Email_amigo;
    }

    public void setEmail_amigo(String Email_amigo) {
        this.Email_amigo = Email_amigo;
    }    
    
}
