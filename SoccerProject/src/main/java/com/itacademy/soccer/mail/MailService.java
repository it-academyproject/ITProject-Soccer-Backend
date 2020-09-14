package com.itacademy.soccer.mail;

import com.itacademy.soccer.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// More info => https://www.youtube.com/watch?v=MgOdvqvF6gk

// Auxiliar info:
//  https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-email

/*

* IN APPLICATION.PROPERTIES (MAVEN) ADD:

# Mail SetUp
spring.mail.host = smtp.example.com
spring.mail.username = example@emailserver.com
spring.mail.password = xxxxxxxxxxxx
spring.mail.port = 587
spring.mail.properties.mail.smtp.starttls.enable = true // Needed SSL for send emails

* IN USER CONTROLLER ADD:

@Autowired
MailService mailService;

and in POST Register (saveNewManager/saveNewAdmin):

try
{
    mailService.sendMail(user.getMail());
}
catch(MailException e)
{
    System.out.println(e.getMessage());
}

* IN POM.XML ADD:

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
    <version>2.3.2.RELEASE</version>
</dependency>

IMPORTANT !! ERROR SSL (tested in localhost):

Mail server connection failed; nested exception is javax.mail.MessagingException: Can't send command to SMTP host;
  nested exception is:
	javax.net.ssl.SSLHandshakeException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target. Failed messages: javax.mail.MessagingException: Can't send command to SMTP host;
  nested exception is:
	javax.net.ssl.SSLHandshakeException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target

*/

@Service
public class MailService {
	
    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(User user) throws MailException {
        // Send Email
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("example@gmail.com");
        mail.setSubject("Testing for Mail Manager/Admin");
        mail.setText("Testing text body, maybe you are Manager or Admin. You're new club well be created automatically. Team Name:" + user.getEmail() +" FC. You can change the name in afters patches.");


        javaMailSender.send(mail);
    }

}
