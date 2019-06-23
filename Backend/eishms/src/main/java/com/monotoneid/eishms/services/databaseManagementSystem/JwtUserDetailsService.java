package com.monotoneid.eishms.services.databaseManagementSystem;

import com.monotoneid.eishms.dataPersistence.models.HomeUserDetails;

import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.repositories.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    Users userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<HomeUser> userList = userRepository.findAll(); 
        HomeUser user = null;
        
        for (int i=0; i < userList.size(); i++) {
            if (userList.get(i).getUserName().matches(username)) {
                user = userList.get(i);
                break;
            }
        }
        //System.out.println(user);
        return new HomeUserDetails(user);
    }
 
   
}