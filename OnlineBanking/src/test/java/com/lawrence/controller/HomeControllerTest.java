package com.lawrence.controller;

import java.security.Principal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import com.lawrence.dto.UserDto;
import com.lawrence.model.User;
import com.lawrence.repository.RoleRepository;
import com.lawrence.security.Role;
import com.lawrence.service.UserService;

@RunWith(SpringRunner.class)
public class HomeControllerTest {


    // First we mock our Attributes
    @InjectMocks
    HomeController homeController;

    @Mock
    private UserService userService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    Model model;
    
    @Mock
    private Principal principal;



    @Before
    public void init() {

        MockitoAnnotations.initMocks(homeController);

        ReflectionTestUtils.setField(homeController, "userService", userService);

        ReflectionTestUtils.setField(homeController, "roleRepository", roleRepository);

    }

    @Test
    public void testHome() {

        homeController.home();
    }

    @Test
    public void testIndex() {

        homeController.index();
    }

    @Test
    public void testSignUp() {

        homeController.signup(model);
    }
    
    @Test
    public void testSignupPost_SignUpFlow() {
        
        UserDto userDto = new UserDto();

        Mockito.when(userService.checkUserExists(Mockito.any(), Mockito.any())).thenReturn(true);
        
        Mockito.when(userService.checkEmailExists(Mockito.any())).thenReturn(true);
        
        Mockito.when(userService.checkUsernameExists(Mockito.any())).thenReturn(true);
                
        homeController.signupPost(userDto, model);
    }
    
    
    @Test
    public void testSignupPost_NotSignUpFlow() {
        
        UserDto userDto = new UserDto();
        
        Role role = new Role();

        Mockito.when(userService.checkUserExists(Mockito.any(), Mockito.any())).thenReturn(false);
        
        Mockito.when(roleRepository.findByName(Mockito.any())).thenReturn(role);
        
        homeController.signupPost(userDto, model);
    }   
   
    
    
    @Test
    public void testUserFront() {

        //Principal principal = new Principal();
        User user = new User();
        Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(user);
        
        homeController.userFront(principal, model);
    }    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
