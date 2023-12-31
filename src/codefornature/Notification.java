/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codefornature;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ACER
 */
public class Notification {
    private String username;
    private String email;
    public Notification(String username,String email){
        this.username=username;
        this.email=email;
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::check,getDelay(), 1, TimeUnit.DAYS);
    }
    
    public boolean hasCheckedIn(){
        String SUrl,SUser,SPass,query,lastCheckInDate;
        SUrl="jdbc:mysql://localhost:3306/user";
        SUser="root";
        SPass="";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(SUrl,SUser,SPass);
            Statement st=con.createStatement();
            query="SELECT * FROM checkin WHERE username='"+username+"'"; 
            ResultSet rs=st.executeQuery(query);
            LocalDate today=LocalDate.now();
            while(rs.next()){
                lastCheckInDate=rs.getString("date");
                LocalDate lastCheckIn = LocalDate.parse(lastCheckInDate);
                if(lastCheckIn.equals(today)){
                    return true;
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public long getDelay(){
        LocalDateTime scheduledTime;
        LocalDateTime now=LocalDateTime.now();
        int dayOfMonth=now.getDayOfMonth();
        if(dayOfMonth<31){
            scheduledTime=LocalDateTime.of(now.getYear(),now.getMonth(),dayOfMonth+1,14,41,0);
        }
        else{
            scheduledTime=LocalDateTime.of(now.getYear(),now.getMonth().plus(1),1,14,41,0);
        }
        Duration duration=Duration.between(now,scheduledTime);
        return duration.getSeconds();
    }
    
    public void check(){
        if (!hasCheckedIn()) {
            System.out.println("yes");
            try {
                sendCheckInReminderEmail(email);
            } 
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Thanks");
        }
    }
    public void sendCheckInReminderEmail(String userEmail){
        Properties properties=new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        String myEmail="usersh22sh@gmail.com";
        String myPassword="pbmh paad evvt czva";
        Session session=Session.getInstance(properties,new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myEmail,myPassword);
            }
        });
        try {
        Message message = prepareMessage(session, myEmail, userEmail);
        if (message != null) {
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } else {
            System.out.println("Failed to prepare the message.");
        }
    } catch (MessagingException e) {
        e.printStackTrace();
        System.out.println("Failed to send email to " + userEmail + ": " + e.getMessage());
    } catch (Exception ex) {
        ex.printStackTrace();
        System.out.println("An unexpected error occurred: " + ex.getMessage());
    }
}

    
    public MimeMessage prepareMessage(Session session,String myEmail,String userEmail) throws MessagingException{
        MimeMessage message=new MimeMessage(session);
        try{
            
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(userEmail));
            message.setSubject("DAILY CHECK-IN REMINDER");
            message.setText("Hey there, check in and do the trivia now at Code For Nature to earn points.");
            //return message;
        }
        catch (MessagingException e) {
            e.printStackTrace();
            throw new MessagingException("Failed to prepare the message: " + e.getMessage());
        } 
        return message;
    }
}
