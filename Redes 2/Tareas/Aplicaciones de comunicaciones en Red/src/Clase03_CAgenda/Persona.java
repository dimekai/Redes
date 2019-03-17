package Clase03_CAgenda;

import java.io.Serializable;

/*@author kaimorts*/
public class Persona implements Serializable{
    private String firstName;
    private String lastName;
    private String phone;
    private String birthdate;
    private String email;
    private boolean b_day;
    public int action;
    
    public Persona(String fName,String lName, String phone,
                   String bDate, String email, int action){
        
        this.firstName = fName;
        this.lastName = lName;
        this.phone = phone;
        this.birthdate = bDate;
        this.email = email;
        this.b_day = false;
        this.action = action;
    }
    
    public Persona(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isB_day() {
        return this.b_day;
    }

    public void setB_day() {
        this.b_day = true;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
