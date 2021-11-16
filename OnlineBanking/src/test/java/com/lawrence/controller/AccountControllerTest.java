package com.lawrence.controller;

import java.math.BigDecimal;
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
import com.lawrence.model.PrimaryAccount;
import com.lawrence.model.PrimaryTransaction;
import com.lawrence.model.SavingsAccount;
import com.lawrence.model.SavingsTransaction;
import com.lawrence.model.User;
import com.lawrence.service.AccountService;
import com.lawrence.service.TransactionService;
import com.lawrence.service.UserService;

@RunWith(SpringRunner.class)
public class AccountControllerTest {

    // First we mock our Attributes
    @InjectMocks
    AccountController accountController;

    @Mock
    private UserService userService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;


    @Mock
    Principal principal;
    

    @Mock
    Model model;

    
    // Second we Initialize our Attributes
    @Before
    public void init() {
        
        accountController = new AccountController();
        
        MockitoAnnotations.initMocks(accountController);

        ReflectionTestUtils.setField(accountController, "transactionService", transactionService);

        ReflectionTestUtils.setField(accountController, "userService", userService);

        ReflectionTestUtils.setField(accountController, "accountService", accountService);

    }
    
    @Test
    public void testPrimaryAccount() {
        
        PrimaryTransaction primaryTransaction = new PrimaryTransaction();
        
        BigDecimal accountBalence = new BigDecimal("879999");
        Integer accountNumber = new Integer("8799");
        
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(accountBalence);
        primaryAccount.setAccountNumber(accountNumber);
        primaryAccount.setId(1212124567L);
        
        List<PrimaryTransaction> primaryTransactionList = new ArrayList<>();
        
        primaryTransactionList.add(primaryTransaction);
        
        Mockito.when(transactionService.findPrimaryTransactionList(Mockito.anyString())).thenReturn(primaryTransactionList);
        
        
        User user = new User();      
        user.setPrimaryAccount(primaryAccount);
        
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(user);
        
        Mockito.when(principal.getName()).thenReturn("Lawrence");
        
        
        accountController.primaryAccount(model, principal);
        
    }
    
    @Test
    public void testSavingsAccount() {
        
        SavingsTransaction savingsTransaction = new SavingsTransaction();
        
        BigDecimal accountBalence = new BigDecimal("879999");
        Integer accountNumber = new Integer("8799");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        
        savingsAccount.setAccountBalance(accountBalence);
        savingsAccount.setAccountNumber(accountNumber);
        savingsAccount.setId(1212124567L);
        
        List<SavingsTransaction> savingsTransactionList = new ArrayList<>();
        
        savingsTransactionList.add(savingsTransaction);
        
        Mockito.when(transactionService.findSavingsTransactionList(Mockito.anyString())).thenReturn(savingsTransactionList);
        
        
        User user = new User();      
        user.setSavingsAccount(savingsAccount);
        
        Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(user);
        
        Mockito.when(principal.getName()).thenReturn("Lawrence");
        
        
        accountController.savingsAccount(model, principal);
        
    }
    
  @Test
  public void testDeposit() {
      
      accountController.deposit(model);
  }
  
  
  @Test
  public void testDepositPOST() {
      
      Mockito.when(principal.getName()).thenReturn("Lawrence");
      
      accountController.depositPOST("1000", "primaryAccount", principal);
  }
  
  
  @Test
  public void testWithdraw() {
      
      accountController.withdraw(model);
  }

  
  @Test
  public void testWithdrawPOST() {
      
      accountController.withdrawPOST("500", "primaryAccount", principal);
  }


}

//@Test
//public void test() {
//    
//}