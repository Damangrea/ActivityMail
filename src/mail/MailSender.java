/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import com.sun.mail.smtp.SMTPTransport;
import java.security.Security;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
/**
 *
 * @author Damangrea
 */
public class MailSender {
    Properties props;
    MimeMessage msg;
    Session session;

    public MailSender() {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        props = System.getProperties();
        props.setProperty("mail.smtp.host", "ironport-tbs-1.packet-systems.com");
        props.setProperty("mail.smtp.port", "25");
    }
    public void setAddress(String sFrom,String sTo)
    {
        try {
            if(msg == null)
            {
                Session session = Session.getInstance(props, null);
                msg = new MimeMessage(session);
            }
            msg.setFrom(new InternetAddress(sFrom));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sTo, false));
        } catch (MessagingException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setAddress(String sFrom,String sTo,String sCC)
    {
        try {
            if(msg == null)
            {
                Session session = Session.getInstance(props, null);
                msg = new MimeMessage(session);
            }
            msg.setFrom(new InternetAddress(sFrom));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sTo, false));
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(sCC, false));
        } catch (MessagingException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setMessage(String sSubject,String sContent)
    {
        try {
            if(msg == null)
            {
                session = Session.getInstance(props, null);
                msg = new MimeMessage(session);
            }
            msg.setSubject(sSubject);
            msg.setText(sContent, "utf-8");
        } catch (MessagingException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void send()
    {
        try {
            SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
            t.connect("ironport-tbs-1.packet-systems.com", "it.support@packet-systems.com", "");
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String [] args)
   {    
        try {
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            
            // Get a Properties object
            Properties props = System.getProperties();
//            props.setProperty("mail.smtps.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.host", "ironport-tbs-1.packet-systems.com");
            props.setProperty("mail.smtp.port", "25");
            //props.setProperty("mail.smtps.auth", "true");
            
            /*
            If set to false, the QUIT command is sent and the connection is immediately closed. If set
            to true (the default), causes the transport to wait for the response to the QUIT command.
            
            ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
            http://forum.java.sun.com/thread.jspa?threadID=5205249
            smtpsend.java - demo program from javamail
            */
//            props.put("mail.smtps.quitwait", "false");
            
            Session session = Session.getInstance(props, null);
            
            // -- Create a new message --
            final MimeMessage msg = new MimeMessage(session);
            
            // -- Set the FROM and TO fields --
//            msg.setFrom(new InternetAddress("damangrea@gmail.com"));
            msg.setFrom(new InternetAddress("it.support@packet-systems.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dmg_tb@yahoo.co.id,damangrea.balanrayoga@packet-systems.com", false));
            
//        if (ccEmail.length() > 0) {
//            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
//        }
            
            msg.setSubject("ini titlenya");
            msg.setText("ini isi \n dari email \n yang dikirim", "utf-8");
//            msg.setSentDate(new Date());
            
            SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
            
//            t.connect("smtp.gmail.com", "damangrea", "dian313131");
            t.connect("ironport-tbs-1.packet-systems.com", "it.support@packet-systems.com", "");
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        } catch (MessagingException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
