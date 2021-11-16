package com.lawrence.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
import com.lawrence.dto.RecipientDto;
import com.lawrence.model.Recipient;
import com.lawrence.model.User;
import com.lawrence.service.TransactionService;
import com.lawrence.service.UserService;

@RunWith(SpringRunner.class)
public class TransferControllerTest {

    @InjectMocks
    TransferController transferController;

    @Mock
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @Mock
    Model model;

    @Mock
    Principal principal;
    
    @Mock
    RecipientDto recipientDto;
    

    @Before
    public void init() {

        MockitoAnnotations.initMocks(transferController);

        ReflectionTestUtils.setField(transferController, "userService", userService);

        ReflectionTestUtils.setField(transferController, "transactionService", transactionService);
    }

    @Test
    public void testBetweenAccounts() {
        transferController.betweenAccounts(model);
    }

 
    
    @Test
    public void testBetweenAccountsPost() throws Exception {
        
        User user = new User();
        
        Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(user);
        
        transferController.betweenAccountsPost("Lawrence", "Sunil", "1000", principal);
       
    }
    
    
    @Test
    public void testRecipient() {

        Recipient recipient = new Recipient();
        
        List<Recipient> recipientList = new ArrayList<>();
        recipientList.add(recipient);

        Mockito.when(transactionService.findRecipientList(Mockito.any())).thenReturn(recipientList);

        transferController.recipient(model, principal);     
    }
    

    @Test
    public void testRecipientPost() {
        
        User user = new User();
        
        Recipient recipient = new Recipient();
        
        Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(user);
        
        Mockito.when(transactionService.saveRecipient(Mockito.any())).thenReturn(recipient);
        
        transferController.recipientPost(recipientDto, principal);
    }
    
    
    @Test
    public void testRecipientEdit() {
        
        Recipient recipient = new Recipient();
        List<Recipient> recipientList = new ArrayList<>();
        recipientList.add(recipient);
        
        Mockito.when(transactionService.findRecipientList(Mockito.any())).thenReturn(recipientList);
        
        Mockito.when(transactionService.findRecipientByName(Mockito.any())).thenReturn(recipient);
        
        transferController.recipientEdit("Lawrence", model, principal);
       
    }    
    
    
    @Test
    public void testRecipientDelete() {
        
        Recipient recipient = new Recipient();
        List<Recipient> recipientList = new ArrayList<>();
        recipientList.add(recipient);
       
        Mockito.when(transactionService.findRecipientList(Mockito.any())).thenReturn(recipientList);
        
        transferController.recipientDelete("Lawrence", model, principal);
    }  
    
    
    @Test
    public void testToSomeoneElse() {
        
        Recipient recipient = new Recipient();
        List<Recipient> recipientList = new ArrayList<>();
        recipientList.add(recipient);
       
        Mockito.when(transactionService.findRecipientList(Mockito.any())).thenReturn(recipientList);
        
        transferController.toSomeoneElse(model, principal);
       
    } 
    
    
    @Test
    public void testToSomeoneElsePost() {
        
        User user = new User();
        
        Recipient recipient = new Recipient();
        
        Mockito.when(transactionService.saveRecipient(Mockito.any())).thenReturn(recipient);
        
        Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(user);
        
        transferController.toSomeoneElsePost("lawrenec", "Primary", "2000", principal);
    }

}
