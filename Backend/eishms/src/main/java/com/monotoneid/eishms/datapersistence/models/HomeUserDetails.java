package com.monotoneid.eishms.datapersistence.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * CLASS HOMEUSERDETAILS. 
 */

public class HomeUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String userPassword;
    private Collection<? extends GrantedAuthority> authorities;

    /**. */
    public HomeUserDetails(HomeKey homeKey) {
        this.userName = homeKey.getKeyName();
        this.userPassword = homeKey.getUserkey();
        Set<UserType> userTypes = new HashSet<>();
        userTypes.add(homeKey.getUsertype());
        this.authorities = userTypes.stream()
                .map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    /**. */
    public HomeUserDetails(HomeUser homeUser) {
        this.userName = homeUser.getUserName();
        this.userPassword = homeUser.getUserPassword();
        Set<UserType> userTypes = new HashSet<>();
        userTypes.add(homeUser.getUserType());
        this.authorities = userTypes.stream()
                .map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
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
        if (this == o) {
            return true;
        } 
        if (o == null || getClass() != o.getClass()) {
            return false;
        } 
        HomeUserDetails user = (HomeUserDetails) o;
        return Objects.equals(userName, user.userName);
    }
}