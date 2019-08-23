package com.monotoneid.eishms.datapersistence.repositories;

import com.monotoneid.eishms.datapersistence.models.Notification;
import com.monotoneid.eishms.datapersistence.models.NotificationStateType;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *CLASS NOTIFICATIONS. 
 */
@Repository()

public interface Notifications extends JpaRepository<Notification,Long> {
    @Query(value = "select * from notification where notificationstatetype='NOTIFICATION_UNREAD'", nativeQuery = true)
    Optional<List<Notification>> findByNotificationStateTypeUnread();

    @Query(value = "select * from notification where notificationstatetype='NOTIFICATION_READ'", nativeQuery = true)
    Optional<List<Notification>> findByNotificationStateTypeRead();    
}