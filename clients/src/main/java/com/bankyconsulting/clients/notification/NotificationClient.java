package com.bankyconsulting.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "notification")
public class NotificationClient {

    @GetMapping(path = "api/v1/notification")
    public void sendNotification(NotificationRequest notificationRequest) {

    }
}
