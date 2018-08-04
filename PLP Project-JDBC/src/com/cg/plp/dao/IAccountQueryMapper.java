package com.cg.plp.dao;

public interface IAccountQueryMapper {
	
	public String INSERT_QUERY = "INSERT INTO ACCOUNT VALUES(?, ?, ?, ?, ?)";
	public String UPDATE_BALANCE_QUERY = "UPDATE ACCOUNT SET BALANCE=?, MODDATE = ? WHERE MOBILE=?";
	public String GET_ALL_ACCOUNT_INFO = "SELECT * FROM ACCOUNT";
	public String GET_ACC_MOBILE_NO = "SELECT MOBILE FROM ACCOUNT";
	

}
