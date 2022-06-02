package mykola.tsupryk.vehiclemarketplacespring.controller;


import lombok.extern.slf4j.Slf4j;
import mykola.tsupryk.vehiclemarketplacespring.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@Slf4j
@RestController
@RequestMapping("/email")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/send")
    public void sendEmail (@RequestParam String from, @RequestParam String to, @RequestParam String subject, @RequestParam String text) throws MessagingException {
        log.info("In sendEmail MessageController - message: from '{}', to '{}", from, to);
        messageService.sendMessage(from, to, subject, text);
    }



}
