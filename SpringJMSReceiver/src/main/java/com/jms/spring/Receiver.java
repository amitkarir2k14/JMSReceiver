package com.jms.spring;


import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

@Component
public class Receiver {

    /**
     * Get a copy of the application context
     */
    @Autowired
    ConfigurableApplicationContext context;

    /**
     * When you receive a message, print it out, then shut down the application.
     * Finally, clean up any ActiveMQ server stuff.
     */
    @JmsListener(destination = "mailbox-destination", containerFactory = "connectionFactory")
    public void receiveMessage(MessageImpl message) {
        System.out.println("Received <" + message + ">");
        System.out.println("message id = " + message.getId());
        System.out.println("message string = " + message.getMessage());
        context.close();
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }
}