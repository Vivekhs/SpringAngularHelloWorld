package com.jwt.common;

import java.io.StringWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.jwt.model.Employee;

public class MailAPI {
	
	 public static void send(final String  from,final String password,String to,String sub,String msg, Employee emp){  
         //Get properties object    
         Properties props = new Properties();    
     	props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
         //get Session   
         Session session = Session.getDefaultInstance(props,    
          new javax.mail.Authenticator() {    
          protected PasswordAuthentication getPasswordAuthentication() {    
          return new PasswordAuthentication(from,password);  
          }    
         });    
         //compose message    
         try {    
          MimeMessage message = new MimeMessage(session);  
          message.setFrom(new InternetAddress(from));
          message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));    
          message.setSubject(sub);    
          /*  first, get and initialize an engine  */
          VelocityEngine ve = new VelocityEngine();
          ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
          ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

          ve.init();
          /*  next, get the Template  */
          Template t = ve.getTemplate( "velocity/email" );///SpringMVCHibernateCRUD/src/main/webapp/WEB-INF/velocity/email.vm
          /*  create a context and add data */
          VelocityContext context = new VelocityContext();
          context.put("emp", emp);
          /* now render the template into a StringWriter */
          StringWriter writer = new StringWriter();
          t.merge( context, writer );
          /* show the World */
          System.out.println( writer.toString() );  
          
          message.setText(writer.toString());    
          //send message  
          Transport.send(message);    
          System.out.println("message sent successfully");    
         } catch (MessagingException e) {throw new RuntimeException(e);}    
            
   }  

}
