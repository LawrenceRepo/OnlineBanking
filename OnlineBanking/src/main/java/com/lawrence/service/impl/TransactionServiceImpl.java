package com.lawrence.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.lawrence.service.TransactionService;
import com.lawrence.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private UserService userService;

	@Autowired
	private PrimaryTransactionRepository primaryTransactionRepository;

	@Autowired
	private SavingsTransactionRepository savingsTransactionRepository;

	@Autowired
	private PrimaryAccountRepository primaryAccountRepository;

	@Autowired
	private SavingsAccountRepository savingsAccountRepository;

	@Autowired
	private RecipientRepository recipientRepository;

	static final String FINISHED = "Finished";

	static final String PRIMARY = "Primary";

	static final String SAVINGS = "Savings";

	public List<PrimaryTransaction> findPrimaryTransactionList(String username) {

		log.info("Going to findPrimaryTransactionList by username : {}", username);

		List<PrimaryTransaction> primaryTransactionList = new ArrayList<>();

		try {

			User user = userService.findByUsername(username);

			primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList();

		} catch (Exception e) {

			log.error("Exception while findPrimaryTransactionList, err msg : {}", e.getMessage());
		}
		return primaryTransactionList;

	}

	public List<SavingsTransaction> findSavingsTransactionList(String username) {

		log.info("Going to findSavingsTransactionList by username : {}", username);

		List<SavingsTransaction> savingsTransactionList = new ArrayList<>();

		try {
			User user = userService.findByUsername(username);

			savingsTransactionList = user.getSavingsAccount().getSavingsTransactionList();

		} catch (Exception e) {

			log.error("Exception while findSavingsTransactionList, err msg : {}", e.getMessage());
		}
		return savingsTransactionList;

	}

	public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {

		log.info("Going to savePrimaryDepositTransaction by primaryTransaction : {}", primaryTransaction);

		try {
			primaryTransactionRepository.save(primaryTransaction);
		} catch (Exception e) {

			log.error("Exception while savePrimaryDepositTransaction, err msg : {}", e.getMessage());
		}

	}

	public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {

		log.info("Going to saveSavingsDepositTransaction by savingsTransaction : {}", savingsTransaction);

		try {
			savingsTransactionRepository.save(savingsTransaction);

		} catch (Exception e) {

			log.error("Exception while saveSavingsDepositTransaction, err msg : {}", e.getMessage());
		}

	}

	public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {

		log.info("Going to saveSavingsWithdrawTransaction by savingsTransaction : {}", savingsTransaction);

		try {
			savingsTransactionRepository.save(savingsTransaction);

		} catch (PersistenceException e) {

			log.error("Exception while saveSavingsWithdrawTransaction, err msg : {}", e.getMessage());
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////////////

	public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {

		log.info("Going to savePrimaryWithdrawTransaction by primaryTransaction : {}", primaryTransaction);

		try {
			primaryTransactionRepository.save(primaryTransaction);

		} catch (Exception e) {

			log.error("Exception while savePrimaryWithdrawTransaction, err msg : {}", e.getMessage());
		}

	}

	public List<Recipient> findRecipientList(Principal principal) {

		log.info("Going to findRecipientList by principal : {}", principal);

		List<Recipient> recipientList = new ArrayList<>();

		try {

			String username = principal.getName();

			recipientList = recipientRepository.findAll().stream()
					.filter(recipient -> username.equals(recipient.getUser().getUsername()))
					.collect(Collectors.toList());
		} catch (Exception e) {

			log.error("Exception while findRecipientList, err msg : {}", e.getMessage());
		}
		return recipientList;

	}

	public Recipient saveRecipient(Recipient recipient) {

		log.info("Going to saveRecipient by recipient : {}", recipient);

		try {
			return recipientRepository.save(recipient);
		} catch (Exception e) {

			log.error("Exception while saveRecipient, err msg : {}", e.getMessage());
		}
		return null;
	}

	public Recipient findRecipientByName(String recipientName) {

		log.info("Going to findRecipientByName by recipientName : {}", recipientName);

		try {
			return recipientRepository.findByName(recipientName);
		} catch (Exception e) {

			log.error("Exception while findRecipientByName, err msg : {}", e.getMessage());
		}
		return null;

	}

	public void deleteRecipientByName(String recipientName) {

		log.info("Going to deleteRecipientByName by recipientName : {}", recipientName);

		try {

			recipientRepository.deleteByName(recipientName);

		} catch (Exception e) {

			log.error("Exception while deleteRecipientByName, err msg : {}", e.getMessage());
		}
		// return type not required because it is void
	}

	public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws PersistenceException {

		log.info(
				"Going to betweenAccountsTransfer by transferFrom : {}, transferTo : {}, amount : {}, primaryAccount : {} and savingsAccount : {}",
				transferFrom, transferTo, amount, primaryAccount, savingsAccount);

		try {

			if (transferFrom.equalsIgnoreCase(PRIMARY) && transferTo.equalsIgnoreCase(SAVINGS)) {

				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
				savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));

				primaryAccountRepository.save(primaryAccount);
				savingsAccountRepository.save(savingsAccount);

				Date date = new Date();

				PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
						"Between account transfer from " + transferFrom + " to " + transferTo, "Account", FINISHED,
						Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);

				primaryTransactionRepository.save(primaryTransaction);
				
			} else if (transferFrom.equalsIgnoreCase(SAVINGS) && transferTo.equalsIgnoreCase(PRIMARY)) {

				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
				savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));

				primaryAccountRepository.save(primaryAccount);
				savingsAccountRepository.save(savingsAccount);

				Date date = new Date();

				SavingsTransaction savingsTransaction = new SavingsTransaction(date,
						"Between account transfer from " + transferFrom + " to " + transferTo, "Transfer", FINISHED,
						Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
				
				savingsTransactionRepository.save(savingsTransaction);
			} else {

				throw new Exception("Invalid Transfer");
			}
		} catch (Exception e) {

			log.error("Exception while betweenAccountsTransfer, err msg : {}", e.getMessage());
		}

	}

	public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) {

		log.info(
				"Going to toSomeoneElseTransfer by recipient : {}, accountType : {}, amount : {}, primaryAccount : {} and savingsAccount : {}",

				recipient, accountType, amount, primaryAccount, savingsAccount);

		try {

			if (accountType.equalsIgnoreCase(PRIMARY)) {

				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));

				primaryAccountRepository.save(primaryAccount);

				Date date = new Date();

				PrimaryTransaction primaryTransaction = new PrimaryTransaction(date,
						"Transfer to recipient " + recipient.getName(), "Transfer", FINISHED,
						Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);

				primaryTransactionRepository.save(primaryTransaction);
				
			} else if (accountType.equalsIgnoreCase(SAVINGS)) {

				savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));

				savingsAccountRepository.save(savingsAccount);

				Date date = new Date();

				SavingsTransaction savingsTransaction = new SavingsTransaction(date,
						"Transfer to recipient " + recipient.getName(), "Transfer", FINISHED,
						Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);

				savingsTransactionRepository.save(savingsTransaction);
			}

		} catch (Exception e) {

			log.error("Exception while toSomeoneElseTransfer, err msg : {}", e.getMessage());
		}

	}
}
