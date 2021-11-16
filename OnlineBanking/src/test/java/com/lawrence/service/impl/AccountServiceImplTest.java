package com.lawrence.service.impl;

import java.math.BigDecimal;
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

import com.lawrence.model.PrimaryAccount;
import com.lawrence.model.SavingsAccount;
import com.lawrence.model.User;
import com.lawrence.repository.PrimaryAccountRepository;
import com.lawrence.repository.SavingsAccountRepository;
import com.lawrence.service.TransactionService;
import com.lawrence.service.UserService;

@RunWith(SpringRunner.class)
public class AccountServiceImplTest {

	// First we mock our Attributes
	@InjectMocks
	AccountServiceImpl accoutServiceImpl;

	@Mock
	private PrimaryAccountRepository primaryAccountRepository;

	@Mock
	private SavingsAccountRepository savingsAccountRepository;

	@Mock
	private UserService userService;

	@Mock
	private TransactionService transactionService;

	@Mock
	Principal principal;

	// Second we Initialize our Attributes
	@Before
	public void init() {
		MockitoAnnotations.initMocks(accoutServiceImpl);

		ReflectionTestUtils.setField(accoutServiceImpl, "primaryAccountRepository", primaryAccountRepository);

		ReflectionTestUtils.setField(accoutServiceImpl, "savingsAccountRepository", savingsAccountRepository);

		ReflectionTestUtils.setField(accoutServiceImpl, "userService", userService);

		ReflectionTestUtils.setField(accoutServiceImpl, "transactionService", transactionService);
	}

	@Test
	public void testCreatePrimaryAccount() {

		PrimaryAccount primaryAccount = new PrimaryAccount();

		BigDecimal accountBalence = new BigDecimal("879999");
		Integer accountNumber = new Integer("8799");

		primaryAccount.setAccountBalance(accountBalence);
		primaryAccount.setAccountNumber(accountNumber);
		primaryAccount.setId(1212124567L);

		// Mockito.any() or ArgumentMatchers.any()
		Mockito.when(primaryAccountRepository.save(Mockito.any())).thenReturn(primaryAccount);

		Mockito.when(primaryAccountRepository.findByAccountNumber(Mockito.anyInt())).thenReturn(primaryAccount);

		accoutServiceImpl.createPrimaryAccount();
	}

	// Start writing our Test

	@Test
	public void testCreatePrimaryAccountException() {

		Mockito.when(primaryAccountRepository.save(Mockito.any())).thenThrow(new RuntimeException());

		accoutServiceImpl.createPrimaryAccount();
	}

	@Test
	public void testCreateSavingsAccount() {

		SavingsAccount savingsAccount = new SavingsAccount();

		BigDecimal accountBalence = new BigDecimal("879999");
		Integer accountNumber = new Integer("8799");

		savingsAccount.setAccountBalance(accountBalence);
		savingsAccount.setAccountNumber(accountNumber);
		savingsAccount.setId(1212345L);

		Mockito.when(savingsAccountRepository.save(Mockito.any())).thenReturn(savingsAccount);

		accoutServiceImpl.createSavingsAccount();
	}

	@Test
	public void testCreateSavingsAccountException() {

		Mockito.when(savingsAccountRepository.save(Mockito.any())).thenThrow(new RuntimeException());

		accoutServiceImpl.createPrimaryAccount();
	}

	@Test
	public void testDepositForPrimaryAccount() {

		User user = new User();

		PrimaryAccount primaryAccount = new PrimaryAccount();

		BigDecimal accountBalence = new BigDecimal("879999");

		primaryAccount.setAccountBalance(accountBalence);

		user.setPrimaryAccount(primaryAccount);

		Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(user);

		Mockito.when(principal.getName()).thenReturn("abc");

		accoutServiceImpl.deposit("Primary", 1.2, principal);
	}

	@Test
	public void testDepositForSavingAccount() {

		Mockito.when(principal.getName()).thenReturn("abc");

		SavingsAccount savingsAccount = new SavingsAccount();
		BigDecimal accountBalence = new BigDecimal("879999");
		savingsAccount.setAccountBalance(accountBalence);

		User user = new User();
		user.setSavingsAccount(savingsAccount);

		// savingsAccountRepository.save(savingsAccount); Have been mocked below
		Mockito.when(savingsAccountRepository.save(Mockito.any())).thenReturn(savingsAccount);

		Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(user);

		accoutServiceImpl.deposit("Savings", 120003.344, principal);
	}

	@Test
	public void testDepositForSavingAccountException() {

		Mockito.when(principal.getName()).thenReturn("abc");

		Mockito.when(userService.findByUsername(Mockito.any())).thenThrow(new RuntimeException());

		accoutServiceImpl.deposit("Savings", 120003.344, principal);
	}

	@Test
	public void testDepositForInvalidSavingAccount() {

		Mockito.when(principal.getName()).thenReturn("abc");

		SavingsAccount savingsAccount = new SavingsAccount();
		BigDecimal accountBalence = new BigDecimal("879999");
		savingsAccount.setAccountBalance(accountBalence);

		User user = new User();
		user.setSavingsAccount(savingsAccount);

		// savingsAccountRepository.save(savingsAccount); Have been mocked below
		// Mockito.when(savingsAccountRepository.save(Mockito.any())).thenReturn(savingsAccount);

		Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(user);

		accoutServiceImpl.deposit("_", 120003.344, principal);
	}

	@Test
	public void testDepositForInvalidPrimaryAndSavingsAccount() {

		User user = new User();

		PrimaryAccount primaryAccount = new PrimaryAccount();
		BigDecimal accountBalence = new BigDecimal("83799999");

		primaryAccount.setAccountBalance(accountBalence);
		user.setPrimaryAccount(primaryAccount);

		Mockito.when(principal.getName()).thenReturn("sunil");
		Mockito.when(userService.

				findByUsername(Mockito.anyString())).thenReturn(user);

		accoutServiceImpl.deposit("", 1290000.90, principal);
	}

	@Test
	public void testWithdrawForPrimaryAccount() {

		User user = new User();

		PrimaryAccount primaryAccount = new PrimaryAccount();

		BigDecimal accountBalence = new BigDecimal("879999");

		primaryAccount.setAccountBalance(accountBalence);

		user.setPrimaryAccount(primaryAccount);

		Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(user);

		Mockito.when(principal.getName()).thenReturn("abc");

		accoutServiceImpl.withdraw("Primary", 129444.45, principal);
	}

	@Test
	public void testWithdrawForSavingAccount() {

		Mockito.when(principal.getName()).thenReturn("abc");

		SavingsAccount savingsAccount = new SavingsAccount();
		BigDecimal accountBalence = new BigDecimal("879999");
		savingsAccount.setAccountBalance(accountBalence);

		User user = new User();
		user.setSavingsAccount(savingsAccount);

		// savingsAccountRepository.save(savingsAccount); Have been mocked below
		Mockito.when(savingsAccountRepository.save(Mockito.any())).thenReturn(savingsAccount);

		Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(user);

		accoutServiceImpl.withdraw("Savings", 120003.344, principal);
	}

	@Test
	public void testWithdrawException() {

		Mockito.when(principal.getName()).thenReturn("abc");

		Mockito.when(userService.findByUsername(Mockito.any())).thenThrow(new RuntimeException());

		accoutServiceImpl.withdraw("Savings", 120003.344, principal);
	}

	@Test
	public void testWithdrawException123() {

		Mockito.when(savingsAccountRepository.save(Mockito.any())).thenThrow(new RuntimeException());

		accoutServiceImpl.createSavingsAccount();
	}

	@Test
	public void testWithdrawForInvalidPrimaryAndSavingsAccount() {

		User user = new User();

		PrimaryAccount primaryAccount = new PrimaryAccount();
		BigDecimal accountBalence = new BigDecimal("83799999");

		primaryAccount.setAccountBalance(accountBalence);
		user.setPrimaryAccount(primaryAccount);

		Mockito.when(principal.getName()).thenReturn("sunil");
		Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(user);

		accoutServiceImpl.withdraw("", 1290000.90, principal);
	}

}
