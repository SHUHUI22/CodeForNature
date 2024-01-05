/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codefornature;


import java.sql.*;
import java.time.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 *
 * @author ACER
 */
public class Notification {
    private final String username;
    private final String email;

    public Notification(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public long getDelay() {
        // Calculate delay until the next scheduled time
        LocalDateTime scheduledTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12,00)); // 12:00 PM
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(scheduledTime)) {
            scheduledTime = scheduledTime.plusDays(1); // Schedule for the next day if the time has passed for today
        }

        Duration duration = Duration.between(now, scheduledTime);
        return duration.getSeconds();
    }

    public void check() {
        System.out.println("Checking...");
        if (!hasCheckedIn()) {
            System.out.println("Sending reminder...");
            try {
                sendCheckInReminderEmail(email);
            } catch (MessagingException e) {
                System.out.println("Failed to send email: " + e.getMessage());
            }
        }
    }

    public boolean hasCheckedIn() {
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

    public void sendCheckInReminderEmail(String userEmail) throws MessagingException {
        System.out.println("Preparing to send email: ");
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
            Message message=prepareMessage(session,myEmail,userEmail);
            Transport.send(message);
            System.out.println("Email sent successfully!");     
        }

    private static Message prepareMessage(Session session,String myEmail,String userEmail){
        try{
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(userEmail));
            message.setSubject("DAILY CHECK-IN REMINDER");
            message.setText("Hey there, check in and do the trivia now at Code For Nature to earn points.");
            return message;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}