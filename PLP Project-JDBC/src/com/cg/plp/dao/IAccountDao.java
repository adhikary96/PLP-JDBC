package com.cg.plp.dao;

import com.cg.plp.bean.Account;
import com.cg.plp.exception.WalletException;

public interface IAccountDao {
	
	String createAccount(Account account) throws WalletException;
	double showBalance(String mobile) throws WalletException;
	double deposit(String mobile, double amt) throws WalletException;
	double withdraw(String mobile, double amt) throws WalletException;
	boolean fundTransfer(String mobile1, String mobile2, double amount) throws WalletException;
	Account printTransaction(String mobile) throws WalletException;
		

}
