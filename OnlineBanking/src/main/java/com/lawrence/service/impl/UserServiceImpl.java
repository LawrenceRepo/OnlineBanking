package com.lawrence.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawrence.model.User;
import com.lawrence.repository.RoleRepository;
import com.lawrence.repository.UserRepository;
import com.lawrence.security.UserRole;
import com.lawrence.service.AccountService;
import com.lawrence.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    public User createUser(User user, Set<UserRole> userRoles) {

        log.info("Going to createUser by user : {} and userRoles : {}", user, userRoles);

        try {

            User localUser = userRepository.findByUsername(user.getUsername());

            if (localUser != null) {

                log.info("User with username {} already exist. Nothing will be done. ", user.getUsername());

            } else {

                String encryptedPassword = passwordEncoder.encode(user.getPassword());

                user.setPassword(encryptedPassword);

                for (UserRole userRole : userRoles) {

                    roleRepository.save(userRole.getRole());
                }

                user.getUserRoles().addAll(userRoles);

                user.setPrimaryAccount(accountService.createPrimaryAccount());
                user.setSavingsAccount(accountService.createSavingsAccount());

                localUser = userRepository.save(user);
            }

            return localUser;
            
        } catch (Exception e) {

            log.error("Exception while createUser, err msg : {}", e.getMessage());
        }
        return null;

    }

    public boolean checkUserExists(String username, String email) {

        log.info("Going to checkUserExists by username : {} and email : {}", username, email);

        try {
            return (checkUsernameExists(username) || checkEmailExists(username));
                  
            
        } catch (Exception e) {

            log.error("Exception while checkUserExists, err msg : {}", e.getMessage());
        }
        return false;

    }

    public boolean checkUsernameExists(String username) {

        log.info("Going to checkUsernameExists by username : {}", username);

        try {

            if (null != findByUsername(username)) {
                return true;
            }

        } catch (Exception e) {

            log.error("Exception while checkUsernameExists, err msg : {}", e.getMessage());
        }
        return false;
    }

    public boolean checkEmailExists(String email) {

        log.info("Going to checkEmailExists by email : {}", email);

        try {
            if (null != findByEmail(email)) {
                return true;
            }

        } catch (Exception e) {

            log.error("Exception while checkEmailExists, err msg : {}", e.getMessage());
        }
        return false;
    }

    public List<User> findUserList() {

        log.info("Going to findUserList by appointment");

        List<User> list = new ArrayList<>();

        try {
            list = userRepository.findAll();
            
        } catch (Exception e) {

            log.error("Exception while findUserList, err msg : {}", e.getMessage());
        }
        return list;
    }

    public void save(User user) {

        log.info("Going to save by user : {}", user);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception while save, err msg : {}", e.getMessage());
        }

    }

    public User findByUsername(String username) {

        log.info("Going to findByUsername by username : {}", username);

        try {
            return userRepository.findByUsername(username);
            
        } catch (Exception e) {
            
            log.error("Exception while findByUsername, err msg : {}", e.getMessage());
        }
        return null;

    }

    public User findByEmail(String email) {

        log.info("Going to findByEmail by email : {}", email);

        try {
            return userRepository.findByEmail(email);
            
        } 
        catch (Exception e) {
            log.error("Exception while findByEmail, err msg : {}", e.getMessage());
        }
        return null;
    }

    public User saveUser(User user) {

        log.info("Going to saveUser by user : {}", user);

        try {
            return userRepository.save(user);
            
        } catch (Exception e) {
            log.error("Exception while saveUser, err msg : {}", e.getMessage());
        }
        return null;

    }

    public void enableUser(String username) {

        log.info("Going to enableUser by username : {}", username);

        try {
            User user = findByUsername(username);
            user.setEnabled(true);

            userRepository.save(user); 
        } catch (Exception e) {
            log.error("Exception while enableUser, err msg : {}", e.getMessage());
        }

    }

    public void disableUser(String username) {

        log.info("Going to disableUser by username : {}", username);

        try {

            User user = findByUsername(username);
            user.setEnabled(false);

            userRepository.save(user);
        } catch (Exception e) {
            log.error("Exception while disableUser, err msg : {}", e.getMessage());
        }
    }
}



