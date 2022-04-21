package mykola.tsupryk.vehiclemarketplacespring.service;

import mykola.tsupryk.vehiclemarketplacespring.entity.model.Message;

import javax.mail.MessagingException;

public interface MessageService {

    void sendMessage (String from, String to, String subject, String text) throws MessagingException;
}
