package com.lawrence.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.lawrence.model.PrimaryAccount;
import com.lawrence.model.SavingsAccount;
import com.lawrence.model.User;
import com.lawrence.repository.RoleRepository;
import com.lawrence.repository.UserRepository;
import com.lawrence.security.Role;
import com.lawrence.security.UserRole;
import com.lawrence.service.AccountService;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AccountService accountService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(userServiceImpl);

        ReflectionTestUtils.setField(userServiceImpl, "userRepository", userRepository);

        ReflectionTestUtils.setField(userServiceImpl, "roleRepository", roleRepository);

        ReflectionTestUtils.setField(userServiceImpl, "passwordEncoder", passwordEncoder);

        ReflectionTestUtils.setField(userServiceImpl, "accountService", accountService);
    }

    @Test
    public void testCreateNonNullUser() {

        User user = new User();
        user.setUsername("law");

        Set<UserRole> userRoles = new HashSet<>();

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(user);

        // User com.lawrence.service.impl.UserServiceImpl.createUser(User user, Set<UserRole> userRoles)
        userServiceImpl.createUser(user, userRoles);

    }

    @Test
    public void testCreateNullUser() {

        User user = new User();
        user.setUsername("law");

        Role role = new Role();

        UserRole userRole = new UserRole();
        userRole.setRole(role);

        PrimaryAccount primaryAccount = new PrimaryAccount();
        SavingsAccount savingsAccount = new SavingsAccount();

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(userRole);

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(null);

        // String encryptedPassword = passwordEncoder.encode(user.getPassword()); Have mocked below

        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("123abc");

        // roleRepository.save(ur.getRole()); Have been mocked below
        Mockito.when(roleRepository.save(Mockito.any())).thenReturn(role);

        // user.setPrimaryAccount(accountService.createPrimaryAccount());
        Mockito.when(accountService.createPrimaryAccount()).thenReturn(primaryAccount);

        // user.setSavingsAccount(accountService.createSavingsAccount());
        Mockito.when(accountService.createSavingsAccount()).thenReturn(savingsAccount);

        // localUser = userRepository.save(user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        userServiceImpl.createUser(user, userRoles);

    }

    @Test
    public void testCreateUserException() {

        User user = new User();
        user.setUsername("law");

        Set<UserRole> userRoles = new HashSet<>();

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenThrow(new RuntimeException());

        userServiceImpl.createUser(user, userRoles);

    }
    
    @Test
    public void testCheckUsernameExists() {

        User user = new User();
        user.setUsername("lawrence");

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(user);

        userServiceImpl.checkUserExists("lawrence", "lawrence@yahoo.de");

    }

    @Test
    public void testcheckEmailExists() {

        User user = new User();
        user.setEmail("lawrence@yahoo.de");

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(user);

        userServiceImpl.checkUserExists("lawrence", "lawrence@yahoo.de");

    }
    
    //////////////////////////
    
    @Test
    public void testCreateUserExceptions() {

        User user = new User();
        user.setUsername("law");

        Set<UserRole> userRoles = new HashSet<>();

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenThrow(new RuntimeException());

        userServiceImpl.createUser(user, userRoles);
        
    }
    
    @Test
    public void testCheckUsernameExistsException() {

        User user = new User();
        user.setUsername("lawrence");

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenThrow(new RuntimeException());

        userServiceImpl.checkUserExists("lawrence", "lawrence@yahoo.de");

    }

    @Test
    public void testcheckEmailExistsException() {

        User user = new User();
        user.setEmail("lawrence@yahoo.de");

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenThrow(new RuntimeException());

        userServiceImpl.checkUserExists("lawrence", "lawrence@yahoo.de");

    }
    
    /////////////////////////
    
    @Test
    public void testcheckUsernameAndEmailException() {
        
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenThrow(new RuntimeException());
        
        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenThrow(new RuntimeException());

        userServiceImpl.checkUserExists("lawrence", "lawrence@yahoo.de");

    }
    
    
    @Test
    public void testEnableUser() {

        User user = new User();
        user.setUsername("rudolf");

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(user);

        // localUser = userRepository.save(user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        userServiceImpl.enableUser("rudolf");

    }
    
    
    @Test
    public void testEnableUserException() {

        User user = new User();
        user.setUsername("rudolf");

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenThrow(new RuntimeException());

        // localUser = userRepository.save(user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        userServiceImpl.enableUser("rudolf");

    }
    
    
    @Test
    public void testDisableUser() {

        User user = new User();
        user.setUsername("rudolf");

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(user);

        // localUser = userRepository.save(user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        userServiceImpl.disableUser("rudolf");

    }
    
    
    @Test
    public void testDisableUserException() {

        User user = new User();
        user.setUsername("rudolf");

        // User localUser = userRepository.findByUsername(user.getUsername()); Have been mocked below

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenThrow(new RuntimeException());

        // localUser = userRepository.save(user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        userServiceImpl.disableUser("rudolf");

    }
    
    @Test
    public void testSavedUser() {

        User user = new User();
        user.setUsername("rudolf");

        // localUser = userRepository.save(user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        User userResponse =   userServiceImpl.saveUser(user);    
        
        Assert.assertNotNull(userResponse);
      //Assert.assertnotnull(object);
      //checking given mock class object should not be null.


    }
    
    
    @Test
    public void testSavedUserException() {

        User user = new User();
        user.setUsername("rudolf");

        // localUser = userRepository.save(user);
        Mockito.when(userRepository.save(Mockito.any())).thenThrow(new RuntimeException());

        userServiceImpl.saveUser(user);

    }
    
    
    @Test
    public void testSave() {

        User user = new User();
        user.setUsername("rudolf");

        // localUser = userRepository.save(user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

         userServiceImpl.save(user);   

    }
    
    
    @Test
    public void testSaveException() {

        User user = new User();
        user.setUsername("rudolf");

        // localUser = userRepository.save(user);
        Mockito.when(userRepository.save(Mockito.any())).thenThrow(new RuntimeException());

         userServiceImpl.save(user);   

    }
    
    @Test
    public void testFindUserList() {
        
        List<User> list = new ArrayList<>();
        
         Mockito.when(userRepository.findAll()).thenReturn(list);
         
         userServiceImpl.findUserList();
    }
    
    @Test
    public void testFindUserListException() {
             
         Mockito.when(userRepository.findAll()).thenThrow(new RuntimeException());
         
         userServiceImpl.findUserList();
    }
}


//Assert.assertnotnull(object);
//checking given mock class object should not be null.
