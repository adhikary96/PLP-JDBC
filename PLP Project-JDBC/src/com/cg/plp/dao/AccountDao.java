package com.cg.plp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cg.plp.bean.Account;
import com.cg.plp.exception.WalletException;
import com.cg.plp.util.DBUtil;

public class AccountDao implements IAccountDao {
	
	public String createAccount(Account account) throws WalletException{
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(IAccountQueryMapper.INSERT_QUERY);
			statement.setString(1, account.getName());
			statement.setString(2, account.getMobile());
			statement.setString(3, account.getEmail());
			statement.setDouble(4, account.getBalance());
			statement.setString(5,account.getDate());
			
			int res = statement.executeUpdate();
			if(res==1){
				Statement stat = connection.createStatement();
				ResultSet rs = stat.executeQuery(IAccountQueryMapper.GET_ACC_MOBILE_NO);
				if(rs != null)
					rs.next();
				String id = rs.getString(1);
				
				return id;
			}
			else
				throw new WalletException("Unable to insert Account details");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new WalletException(e.getMessage());
		}
	}
	
	
	
	@Override
	public double showBalance(String mobile) throws WalletException {
		// TODO Auto-generated method stub
		Account acc = getAccountDetails(mobile);
		return acc.getBalance();
	}

	@Override
	public double deposit(String mobile, double amt) throws WalletException {
		// TODO Auto-generated method stub
		Account acc = getAccountDetails(mobile);
		double finalBal = acc.getBalance() + amt; 
		acc.setBalance(finalBal);
		acc.setDate(""+ new java.util.Date());
		
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement stat = connection.prepareStatement(IAccountQueryMapper.UPDATE_BALANCE_QUERY);
			stat.setDouble(1, acc.getBalance());
			stat.setString(2, acc.getDate());
			stat.setString(3, mobile);
			int res = stat.executeUpdate();
			 return acc.getBalance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new WalletException(e.getMessage());
		}
		
	}

	@Override
	public double withdraw(String mobile, double amt) throws WalletException {
		// TODO Auto-generated method stub
		Account acc = getAccountDetails(mobile);
		double finalBal = acc.getBalance() - amt; 
		acc.setBalance(finalBal);
		acc.setDate(""+ new java.util.Date());
		
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement stat = connection.prepareStatement(IAccountQueryMapper.UPDATE_BALANCE_QUERY);
			stat.setDouble(1, acc.getBalance());
			stat.setString(2, acc.getDate());
			stat.setString(3, mobile);
			int res = stat.executeUpdate();
			 return acc.getBalance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new WalletException(e.getMessage());
		}
	}

	@Override
	public boolean fundTransfer(String mobile1, String mobile2, double amount) throws WalletException {
		// TODO Auto-generated method stub
		Account acc1 = getAccountDetails(mobile1);
		Account acc2 = getAccountDetails(mobile2);
		
		if(acc1 == null || acc2 == null)
			throw new WalletException("Account doesnot exist. Amount can't be transferred");
		
		if(amount > acc1.getBalance())
			throw new WalletException("Account balance is low");
		double bal = acc1.getBalance()-amount;			//withdraw from mobile1
		acc1.setBalance(bal);
		double bal1 = acc2.getBalance()+amount;
		acc2.setBalance(bal1);		//deposit in mobile2
		
		Connection connection = DBUtil.getConnection();
		try {
			PreparedStatement stat = connection.prepareStatement(IAccountQueryMapper.UPDATE_BALANCE_QUERY);
			stat.setDouble(1, acc1.getBalance());
			stat.setString(2, acc1.getDate());
			stat.setString(3, mobile1);
			int res1 = stat.executeUpdate();
			
			PreparedStatement stat1 = connection.prepareStatement(IAccountQueryMapper.UPDATE_BALANCE_QUERY);
			stat.setDouble(1, acc2.getBalance());
			stat.setString(2, acc2.getDate());
			stat.setString(3, mobile2);
			int res2 = stat1.executeUpdate();
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new WalletException(e.getMessage());
		}
		return true;
	}

	@Override
	public Account printTransaction(String mobile) throws WalletException {
		// TODO Auto-generated method stub
		Account account = getAccountDetails(mobile);
		
		return account;
	}

	private Account getAccountDetails(String mobile) throws WalletException {
		// TODO Auto-generated method stub
		Connection connection = DBUtil.getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(IAccountQueryMapper.GET_ALL_ACCOUNT_INFO);
			
			Account acc = null;
			while(resultSet.next()){
				//System.out.println("Val: "+resultSet.getString(3));
				if(resultSet.getString(2).equals(mobile)){
					acc = new Account();
					acc.setName(resultSet.getString(1));
					acc.setMobile(resultSet.getString(2));
					acc.setEmail(resultSet.getString(3));
					acc.setBalance(resultSet.getDouble(4));
					acc.setDate(resultSet.getString(5));
					
					return acc;
				}
			}
			if(resultSet != null){
				resultSet.next();
			}
			throw new WalletException("Account Does not exist");
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new WalletException(e.getMessage());
		}
	}
	

}
