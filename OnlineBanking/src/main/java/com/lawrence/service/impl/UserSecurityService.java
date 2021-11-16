package com.lawrence.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.lawrence.model.User;
import com.lawrence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Going to loadUserByUsername by username : {}", username);

        try {

            User user = userRepository.findByUsername(username);

            if (null == user) {

                log.warn("Username {} not found", username);

                throw new UsernameNotFoundException("Username " + username + " not found");
            }
            return user;
            
        } catch (Exception e) {

            log.error("Exception while loadUserByUsername, err msg : {}", e.getMessage());
        }
        return null;

    }
}
