import java.io.*;
public class Person implements Serializable {
	
	String firstname;
	String lastname;
	String phone;
	String birthdate;
	String email;
	boolean bday;
	int action;

	public Person(String fn, String ln, String p, String bd, String e, int a){
		this.firstname = fn;
		this.lastname = ln;
		this.phone = p;
		this.birthdate = bd;
		this.email = e;
		this.action = a;
                this.bday = false;
	}
	String getFirstName(){
		return this.firstname;
	}	
	String getLastName(){
		return this.lastname;
	}
	String getPhone(){
		return this.phone;
	}
	String getBirthDate(){
		return this.birthdate;
	}
	String getEmail(){
		return this.email;
	}
	int getAction(){
		return this.action;
	}
	boolean getbDday(){
    	return this.bday;
	}
	void setFirstName(String firstName){
		this.firstname = firstName;
	}	
	void setlastName(String lastName){
		this.lastname = lastName;
	}	
	void setPhone(String phone){
		this.phone = phone;
	}	
	void setBirthDate(String birthDate){
		this.birthdate = birthDate;
	}	
	void setEmail(String email){
		this.email = email;
        }
        void setbdDay(){
                this.bday = true;
        }
}