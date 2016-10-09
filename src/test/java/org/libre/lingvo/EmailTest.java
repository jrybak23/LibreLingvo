package org.libre.lingvo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by igorek2312 on 28.09.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmailTest {
    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void testSendMail(){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("libre-lingvo@example.com");
        message.setTo("rybakigor1996@gmail.com");
        message.setSubject("test!");
        message.setText("Hello Igor!!!");
        mailSender.send(message);
    }
}
