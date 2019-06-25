package com.monotoneid.eishms.services.databaseManagementSystem;

import com.monotoneid.eishms.dataPersistence.models.HomeUserDetails;

import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.HomeKey;
import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.repositories.HomeKeys;
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

    @Autowired
    HomeKeys homekeys;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HomeKey homekey;
        
        if ((homekey = homekeys.findByKeyName(username)) != null)
            return new HomeUserDetails(homekey);

        List<HomeUser> userList = userRepository.findAll(); 
        HomeUser user = null;
        
        for (int i=0; i < userList.size(); i++) {
            if (userList.get(i).getUserName().matches(username)) {
                user = userList.get(i);
                break;
            }
        }
        
        return new HomeUserDetails(user);
    }
 
   
}