package org.demoshop39fs.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.demoshop39fs.entity.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMailSender {

    private final MailCreateUtil mailCreateUtil;
    private final JavaMailSender javaMailSender;

    public void sendEmail(User user, String confirmationCode){
        String html = mailCreateUtil.createConfirmationMail(user.getFirstName(), user.getLastName(), confirmationCode);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try{
            helper.setTo(user.getEmail());
            helper.setSubject("Registration Confirmation");
            helper.setText(html,true);
        } catch (MessagingException e){
            throw new IllegalStateException("Error while sending email", e);
        }

        javaMailSender.send(message);

    }

}
