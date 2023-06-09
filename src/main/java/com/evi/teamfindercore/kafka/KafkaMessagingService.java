package com.evi.teamfindercore.kafka;

import com.evi.teamfindercore.kafka.model.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaMessagingService implements NotificationMessagingService{

    private final KafkaTemplate<String,Notification> kafkaTemplate;

    @Override
    public void sendNotification(Notification notification) {
        kafkaTemplate.sendDefault(notification);

    }
}
