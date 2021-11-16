package com.lawrence.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawrence.model.PrimaryAccount;
import com.lawrence.model.PrimaryTransaction;
import com.lawrence.model.SavingsAccount;
import com.lawrence.model.SavingsTransaction;
import com.lawrence.model.User;
import com.lawrence.repository.PrimaryAccountRepository;
import com.lawrence.repository.SavingsAccountRepository;
import com.lawrence.service.AccountService;
import com.lawrence.service.TransactionService;
import com.lawrence.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service // Service Bean Object 
@Slf4j
public class AccountServiceImpl implements AccountService {

    private static int nextAccountNumber = 11223145;

    @Autowired
    private PrimaryAccountRepository primaryAccountRepository; // Bean Object

    @Autowired
    private SavingsAccountRepository savingsAccountRepository; // Bean Object

    @Autowired
    private UserService userService; // Bean Object

    @Autowired
    private TransactionService transactionService; // Bean Object
    
    static final String ACCOUNT = "Account";
    static final String FINISHED = "Finished";

    @Override
    public PrimaryAccount createPrimaryAccount() {

        log.info("Going to createPrimaryAccount");

        try {

            PrimaryAccount primaryAccount = new PrimaryAccount();

            primaryAccount.setAccountBalance(new BigDecimal("0.0"));
            primaryAccount.setAccountNumber(accountGen());

            primaryAccountRepository.save(primaryAccount);

            return primaryAccountRepository.findByAccountNumber(primaryAccount.getAccountNumber());
        }
        catch (Exception e) {
          
        	log.error("Exception while createPrimaryAccount, err msg : {}", e.getMessage());
        }
        return null;

    }

    @Override
    public SavingsAccount createSavingsAccount() {

        log.info("Going to createSavingsAccount");

        try {

            SavingsAccount savingsAccount = new SavingsAccount();

            savingsAccount.setAccountBalance(new BigDecimal("0.0"));
            savingsAccount.setAccountNumber(accountGen());

            savingsAccountRepository.save(savingsAccount);

            return savingsAccountRepository.findByAccountNumber(savingsAccount.getAccountNumber());
            
        } catch (Exception e) {

            log.error("Exception while createSavingsAccount, err msg : {}", e.getMessage());
        }
        return null;
    }

    @Override
    public void deposit(String accountType, double amount, Principal principal) {

        log.info("Going to deposit by accountType : {}, amount : {} and principal : {}", accountType, amount, principal);

        try { // caZhKFzYzyWMTg9

            User user = userService.findByUsername(principal.getName());

            if (accountType.equalsIgnoreCase("Primary")) {

                PrimaryAccount primaryAccount = user.getPrimaryAccount();

                primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(String.valueOf(amount))));

                primaryAccountRepository.save(primaryAccount);

                Date date = new Date();

                PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account", ACCOUNT, FINISHED, amount, primaryAccount.getAccountBalance(), primaryAccount);

                transactionService.savePrimaryDepositTransaction(primaryTransaction);

            } 
            else if (accountType.equalsIgnoreCase("Savings")) {

                SavingsAccount savingsAccount = user.getSavingsAccount();

                savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(String.valueOf(amount))));

                savingsAccountRepository.save(savingsAccount);

                Date date = new Date();

                SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings Account", ACCOUNT, FINISHED, amount, savingsAccount.getAccountBalance(), savingsAccount);

                transactionService.saveSavingsDepositTransaction(savingsTransaction);
            }
        } catch (Exception e) {

            log.error("Exception while deposit, err msg : {}", e.getMessage());
        }

    }

    @Override
    public void withdraw(String accountType, double amount, Principal principal) {

        log.info("Going to withdraw by accountType : {}, amount : {} and principal : {}", accountType, amount, principal);

        try {
            User user = userService.findByUsername(principal.getName());

            if (accountType.equalsIgnoreCase("Primary")) {

                PrimaryAccount primaryAccount = user.getPrimaryAccount();
                primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(String.valueOf(amount))));

                primaryAccountRepository.save(primaryAccount);

                Date date = new Date();

                PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary Account", ACCOUNT, FINISHED, amount, primaryAccount.getAccountBalance(), primaryAccount);

                transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
                
            } else if (accountType.equalsIgnoreCase("Savings")) {

                SavingsAccount savingsAccount = user.getSavingsAccount();

                savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(String.valueOf(amount))));

                savingsAccountRepository.save(savingsAccount);

                Date date = new Date();

                SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from savings Account", ACCOUNT, FINISHED, amount, savingsAccount.getAccountBalance(), savingsAccount);

                transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
            }

        } catch (Exception e) {

            log.error("Exception while withdraw, err msg : {}", e.getMessage());
        }

    }

    private static int accountGen() {

        return ++nextAccountNumber;
    }

}
