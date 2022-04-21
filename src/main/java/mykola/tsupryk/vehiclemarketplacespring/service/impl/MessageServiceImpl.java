package mykola.tsupryk.vehiclemarketplacespring.service.impl;

import mykola.tsupryk.vehiclemarketplacespring.entity.model.Message;
import mykola.tsupryk.vehiclemarketplacespring.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;


@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Override
    public void sendMessage (String from, String to, String subject, String text) throws MessagingException {
        Message message = new Message();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        checkMail(message);


        MimeMessageHelper mailMessage = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
//        message.setFrom(javaMailSender.getJavaMailProperties().getProperty("from"));
        mailMessage.setFrom(message.getFrom());
        mailMessage.setTo(message.getTo());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getText());

        if (!StringUtils.isBlank((CharSequence) message.getSentDate())) {
            message.setSentDate(new Date());
            mailMessage.setSentDate(message.getSentDate());
        }

        javaMailSender.send(mailMessage.getMimeMessage());
    }

    private void checkMail(Message message) {
        if (StringUtils.isBlank(message.getTo())) {
            throw new RuntimeException ("Owner's email can't be empty");
        }
        if (StringUtils.isBlank(message.getSubject())) {
            throw new RuntimeException ("Subject can't be empty");
        }
        if (StringUtils.isBlank(message.getText())) {
            throw new RuntimeException ("Email text is empty");
        }
    }


}




