package com.evi.teamfindercore.messaging;

import com.evi.teamfindercore.messaging.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "notification", name = "service", havingValue = "activemq")
public class JmsMessagingService implements NotificationMessagingService {

    private final JmsTemplate jmsTemplate;
    private final Queue queue;

    @Override
    public void sendNotification(Notification notification) {
        jmsTemplate.convertAndSend(queue, notification);
    }
}
