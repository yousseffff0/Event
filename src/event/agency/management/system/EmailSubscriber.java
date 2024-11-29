package event.agency.management.system;

import javax.mail.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javax.mail.internet.*;

public class EmailSubscriber implements Observer{
    private String name;
    private String email;

    private int id;

    database db = new database();

    public EmailSubscriber(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public EmailSubscriber(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public EmailSubscriber() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void sendMail(String subscriberEmail,String text){
         String AdminEmail = "youssefahmad1973@gmail.com";
         String AdminPassword = "wnjoyiysdhazikdr";

        String customerEmail = subscriberEmail;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port","587");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(AdminEmail,AdminPassword);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress (AdminEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(customerEmail));
            message.setSubject("Eventak");
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e){
            e.printStackTrace();
        }

    }

    @Override
    public void Update(String message){
        ArrayList<String> emails = new ArrayList<String>();
        System.out.println(
                  "Received an email that says : " + message
        );
        emails = db.getSubscriberEmail();
        for (String s: emails) {
            sendMail(s,message);

        }

    }
}
