package com.evi.teamfindercore.messaging;

import com.evi.teamfindercore.messaging.model.Notification;

public interface NotificationMessagingService {

    public void sendNotification(Notification notification);
}
