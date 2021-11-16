package com.lawrence.service;

import java.security.Principal;

import com.lawrence.model.PrimaryAccount;
import com.lawrence.model.SavingsAccount;

public interface AccountService {

	PrimaryAccount createPrimaryAccount();

	SavingsAccount createSavingsAccount();

	void deposit(String accountType, double amount, Principal principal);

	void withdraw(String accountType, double amount, Principal principal);

}
