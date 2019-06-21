package com.monotoneid.eishms.services.databaseManagementSystem;

import com.monotoneid.eishms.dataPersistence.models.HomeUserDetails;
import com.monotoneid.eishms.dataPersistence.models.User;
import com.monotoneid.eishms.dataPersistence.repositories.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    Users userRepository;
 
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
      
        User user = userRepository.findByUserName(username)
                  .orElseThrow(() -> 
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
        );
 
        return new HomeUserDetails(user);
    }
}