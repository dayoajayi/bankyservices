package com.bankyconsulting.notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRespository
        extends JpaRepository<Notification, Integer> {
}
