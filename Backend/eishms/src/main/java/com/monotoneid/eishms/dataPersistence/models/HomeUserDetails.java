package com.monotoneid.eishms.dataPersistence.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.monotoneid.eishms.dataPersistence.models.*;

public class HomeUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private long userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userLocationTopic;
    private Collection<? extends GrantedAuthority> authorities;

    public HomeUserDetails(HomeUser homeUser) {
        this.userId = homeUser.getUserId();
        this.userName = homeUser.getUserName();
        this.userEmail = homeUser.getUserEmail();
        this.userLocationTopic = homeUser.getUserLocationTopic();
        this.userPassword = homeUser.getUserPassword();
        //this.authorities = new ArrayList<>();
        Set<UserType> userTypes = new HashSet<>();
        userTypes.add(homeUser.getUserType());
        this.authorities = userTypes.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    public Long getUserId() {
        return userId;
    }
 
    public String getEmail() {
        return userEmail;
    }

    public String getUserLocationTopic() {
        return userLocationTopic;
    }
 
    @Override
    public String getUsername() {
        return userName;
    }
 
    @Override
    public String getPassword() {
        return userPassword;
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        HomeUserDetails user = (HomeUserDetails) o;
        return Objects.equals(userId, user.userId);
    }
}