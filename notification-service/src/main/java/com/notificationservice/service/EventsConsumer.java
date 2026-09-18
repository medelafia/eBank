package com.notificationservice.service;


import com.notificationservice.enums.NotificationEventType;
import com.notificationservice.events.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventsConsumer {

    @Autowired
    private MailService mailService;

    @KafkaListener(topics = "notifications-topic" , groupId="notification-group")
    public void consume(NotificationEvent notificationEvent){
        this.mailService.sendMail(notificationEvent.getEmail(),"Banking notification " , notificationEvent.getMessage());
    }
}
