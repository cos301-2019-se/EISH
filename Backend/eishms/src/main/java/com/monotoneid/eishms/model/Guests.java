package com.monotoneid.eishms.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="guests")
@Table(name="guests")
@EntityListeners(AuditingEntityListener.class)
public class Guests{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id", columnDefinition ="serial" ,updatable = false,nullable = false )
    private long  guest_id;

    @ManyToOne
    @JoinColumn(name="resident_id")
    private Residents resident;
    
    @Column(name="guest_name", columnDefinition = "text", updatable = true,nullable = false)
    private String guest_name;

    @Column(name="guest_email", columnDefinition = "text", updatable = true,nullable = false)
    private String guest_email;

    @Column(name="expiry_date", columnDefinition = "bigint", updatable = true,nullable = false)
    private long expiry_date;
    //getters
    public long getGuestId(){
        return this.guest_id;
    }
    public Residents getResident(){
        return this.resident;
    }
    public String getGuestName(){
        return guest_name;
    }
    public String getGuestEmail(){
        return this.guest_email;
    }
    public long getGuestExpiryDate(){
        return this.expiry_date; 
    }
    //setters
    public void setGuestName(String gn){
        this.guest_name=gn;
    }
    public void setGuestEmail(String ge){
        this.guest_email=ge;
    }
    public void setExpiryDate(long ed){
        this.expiry_date=ed;
    }
    
}