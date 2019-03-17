package Clase03.Agenda.Clases;

import java.io.Serializable;

/**
 *
 * @author Kaimorts
 */
public class Persona implements Serializable{
    private String Name;
    private String Surname;
    private String Email;
    private String Password;
    private String Phone;
    private String Username;
    private String Birthday;
    
    public Persona(){}

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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String Bithday) {
        this.Birthday = Bithday;
    }
}
