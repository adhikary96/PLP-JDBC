package com.cg.plp.service;

import java.util.Collection;

import com.cg.plp.bean.Account;
import com.cg.plp.dao.AccountDao;
import com.cg.plp.dao.IAccountDao;
import com.cg.plp.exception.WalletException;

public class AccountService implements IAccountService {
	IAccountDao accountDao = new AccountDao();

	@Override
	public String createAccount(Account account) throws WalletException {
		// TODO Auto-generated method stub		
		return accountDao.createAccount(account);
	}
	/*@Override
	public Account getAccountDetails(String mobile) throws WalletException {
		// TODO Auto-generated method stub
		return accountDao.getAccountDetails(mobile);
	}
	*/
	
	@Override
	public boolean validateAccount(Account acc) throws WalletException{
		if(validateMobile(acc.getMobile()) && validateName(acc.getName()) 
				&& validateEmail(acc.getEmail()) && validateBalance(acc.getBalance()))
			return true;
		return false;
	}

		public boolean validateMobile(String mobile) throws WalletException {
			if (!mobile.matches("\\d{10}"))
				throw new WalletException("Mobile number should contain 10 digits");
			return true;
		}
		public boolean validateName(String name) throws WalletException {
			if(name.isEmpty() || name == null)
				throw new WalletException("Name cannot be empty");
			else
				if (!name.matches("[A-Za-z]{2,}"))
					throw new WalletException("Name must contain only alphabets");
			return true;
		}
		public boolean validateEmail(String email) throws WalletException {
			if (!email.matches("[A-Za-z0-9]{3,}@{1}[a-z]{2,}\\.com"))
				throw new WalletException("Invalid Email ID");
			return true;
		}
		public boolean validateBalance(double balance) throws WalletException {
			if (balance <= 0)
				throw new WalletException("Balance must be a number greater than zero");
			return true;
		}

	@Override
	public double showBalance(String mobile) throws WalletException {
		// TODO Auto-generated method stub
		
		if(validateMobile(mobile)){
			Account acc = accountDao.printTransaction(mobile);
			if(validateAccount(acc))
				throw new WalletException("Account Does not Exist");
		}
		return accountDao.showBalance(mobile);
	}

	@Override
	public double deposit(String mobile, double amt) throws WalletException {
		// TODO Auto-generated method stub
		return accountDao.deposit(mobile, amt);
	}

	@Override
	public double withdraw(String mobile, double amt) throws WalletException {
		// TODO Auto-generated method stub
		Account acc = accountDao.printTransaction(mobile);
		if(amt<acc.getBalance())
			throw new WalletException("Account balance is Low");
		return accountDao.withdraw(mobile, amt);
	}

	@Override
	public boolean fundTransfer(String mobile1, String mobile2, double amount) throws WalletException {
		// TODO Auto-generated method stub
		if(!validateMobile(mobile1) || !validateMobile(mobile2))
			throw new WalletException("Mobile number should contain 10 digits");
		if(amount < 0)
			throw new WalletException("Amount must be a number greater than zero");
		return accountDao.fundTransfer(mobile1, mobile2, amount);
	}

	@Override
	public Account printTransaction(String mobile) throws WalletException {
		// TODO Auto-generated method stub
		return accountDao.printTransaction(mobile);
	}
	

	
	
	
	

}
