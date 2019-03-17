package Clase03.Agenda.Interfaces;

import java.io.Serializable;

/*
 * @author Kaimorts
 */
public class SessionNow implements Serializable{
    private String email_now;
    private String user_now;

    public String getEmail_now() {
        return email_now;
    }

    public void setEmail_now(String email_now) {
        this.email_now = email_now;
    }

    public String getUser_now() {
        return user_now;
    }

    public void setUser_now(String user_now) {
        this.user_now = user_now;
    }
}
