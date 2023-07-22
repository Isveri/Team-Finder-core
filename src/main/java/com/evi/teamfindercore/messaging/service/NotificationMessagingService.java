package com.evi.teamfindercore.messaging.service;

import com.evi.teamfindercore.messaging.model.Notification;

public interface NotificationMessagingService {

    public void sendNotification(Notification notification);
}
