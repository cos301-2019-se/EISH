package com.monotoneid.eishms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name="residents")
@Table(name="residents")
@EntityListeners(AuditingEntityListener.class)
public class Residents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resident_id", columnDefinition ="serial" ,updatable = false,nullable = false )
    private long  resident_id;

    @Column(name="resident_name", columnDefinition = "text", updatable = true,nullable = false)
    private String resident_name;

    @Column(name="resident_email", columnDefinition = "text", updatable = true,nullable = false)
    private String resident_email;

    @Column(name="resident_password", columnDefinition = "text", updatable = true,nullable = false)
    private String resident_password;

    @OneToMany(mappedBy = "resident")
    private List<Guests> listofvisitors = new ArrayList<Guests>();

    @OneToMany(mappedBy = "resident")
    private List<HomeOccupation> listofHomeOccupation =new ArrayList<HomeOccupation>();


    //getters
    public long getResidentId(){
        return this.resident_id;
    }
    public String getResidentName(){
        return this.resident_name;
    }
    public String getResidentEmail(){
        return this.resident_email;
    }
    public String getResidentPassword(){
        return this.resident_password;
    }
    public List<Guests> getGuests(){
        return this.listofvisitors;
    }
    public List<HomeOccupation>getHomeOccupation(){
        return this.listofHomeOccupation;
    }
    //setters
    public void setResidentName(String rn){
        this.resident_name=rn;
    }
    public void setResidentEmail(String re){
        this.resident_email=re;
    }
    public void setResidentPassword(String rp){
        this.resident_password=rp;
    }
}