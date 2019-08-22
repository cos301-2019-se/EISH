package com.monotoneid.eishms.datapersistence.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *CLASS NOTIFICATION MODEL. 
 */
@Entity(name = "notification")
@Table(name = "notification")
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({
    @TypeDef(
    name = "pgsql_enum",
    typeClass = PostgreSQLEnumType.class
)})

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificationid", 
            columnDefinition = "serial", 
            updatable = false, 
            nullable = false)
    private long notificationId;

    @Column(name = "notificationdescription", 
            columnDefinition = "text",
            updatable = true, 
            unique = false, 
            nullable = false)
    private String notificationDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "notificationstatetype", 
        columnDefinition = "notificationStateType", 
        updatable = true, 
        nullable = false)
    @Type(type = "pgsql_enum")
    private NotificationStateType notificationStateType;

    @Enumerated(EnumType.STRING)
    @Column(name = "notificationprioritytype", 
        columnDefinition = "notificationPriorityType", 
        updatable = true, 
        nullable = false)
    @Type(type = "pgsql_enum")
    private NotificationPriorityType notificationPriorityType;

    @Column(name = "notificationtimestamp", 
            columnDefinition = "TIMESTAMP", 
            insertable = true, 
            updatable = true, 
            nullable = false)
    private Timestamp notificationTimestamp;

    public Notification() {}

     /**. */
     public Notification(String newNotificationDescription,
        String newNotificationPriorityType,
        String newNotificationStateType,
        Timestamp newNotificationTimeStamp) {
            setNotificationDescription(newNotificationDescription);
            setNotificationPriorityType(notificationPriorityType
                .valueOf(newNotificationPriorityType));
            setNotificationStateType(notificationStateType
                .valueOf(newNotificationStateType));    
            setNotificationTimeStamp(newNotificationTimeStamp);
     }
     
    public long getNotificationId() {
        return notificationId;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public NotificationStateType getNotificationStateType() {
        return notificationStateType;
    }

    public NotificationPriorityType getNotificationPriorityType() {
        return notificationPriorityType;
    }

    public Timestamp getNotificationTimeStamp() {
        return notificationTimestamp;
    }

    public void setNotificationDescription(String newNotificationDescription) {
        this.notificationDescription = newNotificationDescription;
    }

    public void setNotificationStateType(NotificationStateType newNotificationStateType) {
        this.notificationStateType = newNotificationStateType;
    }

    public void setNotificationPriorityType(NotificationPriorityType newNotificationPriorityType) {
        this.notificationPriorityType = newNotificationPriorityType;
    }

    public void setNotificationTimeStamp(Timestamp newNotificationTimestamp) {
        this.notificationTimestamp = newNotificationTimestamp;
    }

}