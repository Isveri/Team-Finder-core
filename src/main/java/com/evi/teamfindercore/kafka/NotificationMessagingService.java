package com.evi.teamfindercore.kafka;

import com.evi.teamfindercore.kafka.model.Notification;

public interface NotificationMessagingService {

    public void sendNotification(Notification notification);
}
