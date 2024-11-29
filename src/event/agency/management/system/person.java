package event.agency.management.system;


import java.util.ArrayList;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class person {
    private String name;
    private String phoneNo;
    private String Email;
    private String age;
    private String password;

    public int id ;

    private int personTypeID;


    ArrayList<person> Persons = new ArrayList<person>();


    public person(String name, String phoneNo, String email, String age, String password, int id,int personTypeID) {
        this.name = name;
        this.phoneNo = phoneNo;
        Email = email;
        this.age = age;
        this.password = password;
        this.personTypeID = personTypeID;
    }

    public person(){


    }



    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void AddPerson(person p){
        Persons.add(p);
    }


    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPersonTypeID() {
        return personTypeID;
    }

    public void setPersonTypeID(int personTypeID) {
        this.personTypeID = personTypeID;
    }

    public  void setId(int Id){
        this.id = Id;
    }

    public int getId(){
        return this.id;
    }
}