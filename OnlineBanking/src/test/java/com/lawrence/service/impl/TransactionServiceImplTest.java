package com.lawrence.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.lawrence.model.PrimaryTransaction;
import com.lawrence.model.Recipient;
import com.lawrence.model.SavingsAccount;
import com.lawrence.model.SavingsTransaction;
import com.lawrence.model.User;
import com.lawrence.repository.PrimaryAccountRepository;
import com.lawrence.repository.PrimaryTransactionRepository;
import com.lawrence.repository.RecipientRepository;
import com.lawrence.repository.SavingsAccountRepository;
import com.lawrence.repository.SavingsTransactionRepository;
import com.lawrence.service.UserService;

@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {

	@InjectMocks
	TransactionServiceImpl transactionServiceImpl;

	@Mock
	private UserService userService;

	@Mock
	private PrimaryTransactionRepository primaryTransactionRepository;

	@Mock
	private SavingsTransactionRepository savingsTransactionRepository;

	@Mock
	private PrimaryAccountRepository primaryAccountRepository;

	@Mock
	private SavingsAccountRepository savingsAccountRepository;

	@Mock
	private RecipientRepository recipientRepository;

	@Mock
	Principal principal;

	@InjectMocks
	AccountServiceImpl accoutServiceImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(transactionServiceImpl);

		ReflectionTestUtils.setField(transactionServiceImpl, "userService", userService);

		ReflectionTestUtils.setField(transactionServiceImpl, "primaryTransactionRepository",
				primaryTransactionRepository);

		ReflectionTestUtils.setField(transactionServiceImpl, "savingsTransactionRepository",
				savingsTransactionRepository);

		ReflectionTestUtils.setField(transactionServiceImpl, "primaryAccountRepository", primaryAccountRepository);

		ReflectionTestUtils.setField(transactionServiceImpl, "userService", userService);

		ReflectionTestUtils.setField(transactionServiceImpl, "savingsAccountRepository", savingsAccountRepository);

		ReflectionTestUtils.setField(transactionServiceImpl, "recipientRepository", recipientRepository);

	}

	@Test
	public void testFindPrimaryTransactionList() {

		User user = new User();
		PrimaryAccount primaryAccount = new PrimaryAccount();
		PrimaryTransaction primaryTransaction = new PrimaryTransaction();
		List<PrimaryTransaction> primaryTransactionList = new ArrayList<>();

		primaryTransactionList.add(primaryTransaction);

		primaryAccount.setPrimaryTransactionList(primaryTransactionList);

		user.setPrimaryAccount(primaryAccount);

		Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(user);

		transactionServiceImpl.findPrimaryTransactionList("lawrence");
	}

	@Test
	public void testFindPrimaryTransactionListException() {

		Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(null);

		transactionServiceImpl.findPrimaryTransactionList("lawrence");
	}

	@Test
	public void testFindSavingsTransactionList() {

		User user = new User();
		SavingsAccount savingsAccount = new SavingsAccount();
		SavingsTransaction savingsTransaction = new SavingsTransaction();
		List<SavingsTransaction> savingsTransactionList = new ArrayList<>();

		savingsTransactionList.add(savingsTransaction);

		savingsAccount.setSavingsTransactionList(savingsTransactionList);

		user.setSavingsAccount(savingsAccount);

		Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(user);

		transactionServiceImpl.findSavingsTransactionList("Law");
	}

	@Test
	public void testFindSavingsTransactionListException() {

		Mockito.when(userService.findByUsername(Mockito.any())).thenReturn(null);

		transactionServiceImpl.findSavingsTransactionList("Law");
	}

	@Test
	public void testSavePrimaryDepositTransaction() {

		// primaryTransactionRepository.save(primaryTransaction); Have been Mocked below

		PrimaryTransaction primaryTransaction = new PrimaryTransaction();

		Mockito.when(primaryTransactionRepository.save(Mockito.any())).thenReturn(primaryTransaction);

		transactionServiceImpl.savePrimaryDepositTransaction(primaryTransaction);
	}

	@Test
	public void testSavePrimaryDepositTransactionException() {

		PrimaryTransaction primaryTransaction = new PrimaryTransaction();

		Mockito.when(primaryTransactionRepository.save(Mockito.any())).thenReturn(new RuntimeException());

		transactionServiceImpl.savePrimaryDepositTransaction(primaryTransaction);
	}

	@Test
	public void testSaveSavingsDepositTransaction() {

		// savingsTransactionRepository.save(savingsTransaction); Have been Mocked below

		SavingsTransaction savingsTransaction = new SavingsTransaction();

		Mockito.when(savingsTransactionRepository.save(Mockito.any())).thenReturn(savingsTransaction);

		transactionServiceImpl.saveSavingsDepositTransaction(savingsTransaction);

	}

	@Test
	public void testSaveSavingsDepositTransactionException() {

		SavingsTransaction savingsTransaction = new SavingsTransaction();

		Mockito.when(savingsTransactionRepository.save(Mockito.any())).thenReturn(new RuntimeException());

		transactionServiceImpl.saveSavingsDepositTransaction(savingsTransaction);

	}

	@Test
	public void testSaveSavingsWithdrawTransaction() {

		// savingsTransactionRepository.save(savingsTransaction); Have been Mocked below

		SavingsTransaction savingsTransaction = new SavingsTransaction();

		Mockito.when(savingsTransactionRepository.save(Mockito.any())).thenReturn(savingsTransaction);

		transactionServiceImpl.saveSavingsWithdrawTransaction(savingsTransaction);
	}

	@Test
	public void testSaveSavingsWithdrawTransactionException() {

		// savingsTransactionRepository.save(savingsTransaction);

		SavingsTransaction savingsTransaction = new SavingsTransaction();

		Mockito.when(savingsTransactionRepository.save(Mockito.any())).thenReturn(new RuntimeException());

		transactionServiceImpl.saveSavingsWithdrawTransaction(savingsTransaction);
	}

	@Test
	public void testSavePrimaryWithdrawTransaction() {

		PrimaryTransaction primaryTransaction = new PrimaryTransaction();

		Mockito.when(primaryTransactionRepository.save(Mockito.any())).thenReturn(primaryTransaction);

		transactionServiceImpl.savePrimaryWithdrawTransaction(primaryTransaction);
	}

	@Test
	public void testSavePrimaryWithdrawTransactionException() {

		PrimaryTransaction primaryTransaction = new PrimaryTransaction();

		Mockito.when(primaryTransactionRepository.save(Mockito.any())).thenReturn(new RuntimeException());

		transactionServiceImpl.savePrimaryWithdrawTransaction(primaryTransaction);
	}

	@Test
	public void testFindrecipientlist() {

		Mockito.when(principal.getName()).thenReturn("abc");

		Recipient recipient = new Recipient();
		User user = new User();
		user.setUsername("abc");

		recipient.setUser(user);

		List<Recipient> recipientList = new ArrayList<>();
		recipientList.add(recipient);

		Mockito.when(recipientRepository.findAll()).thenReturn(recipientList);

		transactionServiceImpl.findRecipientList(principal);
	}

	@Test
	public void testFindrecipientlistException() {

		Mockito.when(principal.getName()).thenReturn("abc");

		Mockito.when(recipientRepository.findAll()).thenThrow(new RuntimeException());

		transactionServiceImpl.findRecipientList(principal);
	}

	@Test
	public void testSaveRecipient() {

		Recipient recipient = new Recipient();
		User user = new User();
		user.setUsername("abc");

		Mockito.when(recipientRepository.save(Mockito.any())).thenReturn(recipient);
		transactionServiceImpl.saveRecipient(recipient);
	}

	@Test
	public void testSaveRecipientException() {

		Recipient recipient = new Recipient();
		User user = new User();
		user.setUsername("abc");

		Mockito.when(recipientRepository.save(Mockito.any())).thenThrow(new RuntimeException());

		transactionServiceImpl.saveRecipient(recipient);
	}

	@Test
	public void testFindRecipientByName() {

		Recipient recipient = new Recipient();

		Mockito.when(recipientRepository.findByName(Mockito.anyString())).thenReturn(recipient);

		transactionServiceImpl.findRecipientByName("lawrence");

	}

	@Test
	public void testFindRecipientByNameException() {

		Mockito.when(recipientRepository.findByName(Mockito.anyString())).thenThrow(new RuntimeException());

		transactionServiceImpl.findRecipientByName("lawrence");

	}

	@Test
	public void testDeleteRecipientByName() {

		transactionServiceImpl.deleteRecipientByName("lawrence");
	}

	@Test
	public void testBetweenAccountsTransferToPrimaryAccount() throws Exception {

		BigDecimal primaryAccountAccountBalence = new BigDecimal("9999");
		BigDecimal savingsAccountAccountBalence = new BigDecimal("1000");
		PrimaryTransaction primaryTransaction = new PrimaryTransaction();

		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(primaryAccountAccountBalence);

		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(savingsAccountAccountBalence);

		Mockito.when(primaryAccountRepository.save(Mockito.any())).thenReturn(primaryAccount);
		Mockito.when(savingsAccountRepository.save(Mockito.any())).thenReturn(savingsAccount);

		// primaryTransactionRepository.save(primaryTransaction); -> Mocked below
		Mockito.when(primaryTransactionRepository.save(Mockito.any())).thenReturn(primaryTransaction);

		transactionServiceImpl.betweenAccountsTransfer("Primary", "Savings", "2300", primaryAccount, savingsAccount);
	}

	@Test
	public void testBetweenAccountsTransferToSavingsAccount() throws Exception {

		BigDecimal primaryAccountAccountBalence = new BigDecimal("9999");
		BigDecimal savingsAccountAccountBalence = new BigDecimal("1000");
		SavingsTransaction savingsTransaction = new SavingsTransaction();

		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(primaryAccountAccountBalence);

		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(savingsAccountAccountBalence);

		Mockito.when(primaryAccountRepository.save(Mockito.any())).thenReturn(primaryAccount);
		Mockito.when(savingsAccountRepository.save(Mockito.any())).thenReturn(savingsAccount);

		// savingsTransactionRepository.save(savingsTransaction); -> Mocked below
		Mockito.when(savingsTransactionRepository.save(Mockito.any())).thenReturn(savingsTransaction);

		transactionServiceImpl.betweenAccountsTransfer("Savings", "Primary", "300", primaryAccount, savingsAccount);
	}

	@Test
	public void testBetweenAccountsTransferToInvalidSavingsAccount() throws Exception {

		PrimaryAccount primaryAccount = new PrimaryAccount();

		SavingsAccount savingsAccount = new SavingsAccount();

		transactionServiceImpl.betweenAccountsTransfer("SavingWrong", "PrimaryWrong", "300", primaryAccount,
				savingsAccount);
	}

	@Test
	public void testToPrimaryAccountSomeoneElseTransfer() {

		BigDecimal primaryAccountAccountBalence = new BigDecimal("9999");
		BigDecimal savingsAccountAccountBalence = new BigDecimal("1000");
		Recipient recipient = new Recipient();
		PrimaryTransaction primaryTransaction = new PrimaryTransaction();

		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(primaryAccountAccountBalence);

		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(savingsAccountAccountBalence);

		Mockito.when(primaryAccountRepository.save(Mockito.any())).thenReturn(primaryAccount);
		Mockito.when(savingsAccountRepository.save(Mockito.any())).thenReturn(savingsAccount);

		Mockito.when(primaryTransactionRepository.save(Mockito.any())).thenReturn(primaryTransaction);

		transactionServiceImpl.toSomeoneElseTransfer(recipient, "Primary", "1000", primaryAccount, savingsAccount);
	}

	@Test
	public void testToSavingAccountSomeoneElseTransfer() {

		BigDecimal primaryAccountAccountBalence = new BigDecimal("9999");
		BigDecimal savingsAccountAccountBalence = new BigDecimal("1000");
		SavingsTransaction savingsTransaction = new SavingsTransaction();
		Recipient recipient = new Recipient();

		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(primaryAccountAccountBalence);

		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(savingsAccountAccountBalence);

		Mockito.when(primaryAccountRepository.save(Mockito.any())).thenReturn(primaryAccount);
		Mockito.when(savingsAccountRepository.save(Mockito.any())).thenReturn(savingsAccount);

		// savingsTransactionRepository.save(savingsTransaction); -> Mocked below
		Mockito.when(savingsTransactionRepository.save(Mockito.any())).thenReturn(savingsTransaction);

		transactionServiceImpl.toSomeoneElseTransfer(recipient, "Savings", "100", primaryAccount, savingsAccount);
	}

	@Test
	public void testToSavingAccountSomeoneElseTransferException() throws Exception {
		Recipient recipient = new Recipient();

		PrimaryAccount primaryAccount = new PrimaryAccount();

		SavingsAccount savingsAccount = new SavingsAccount();

		Mockito.when(savingsAccountRepository.save(Mockito.any())).thenThrow(new RuntimeException());

		transactionServiceImpl.toSomeoneElseTransfer(recipient, "Savings", "100", primaryAccount, savingsAccount);
	}

}
