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

try
{
    mailService.sendMail(user.getMail());
}
catch(MailException e)
{
    System.out.println(e.getMessage());
}

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
        mail.setText("Testing text body, maybe you are Manager or Admin, how knows...You know notthing John Admin..., Manager is comming...");

        javaMailSender.send(mail);
    }

}