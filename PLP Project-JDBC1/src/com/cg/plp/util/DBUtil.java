package com.cg.plp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cg.plp.exception.WalletException;

public class DBUtil {
	static String url="jdbc:oracle:thin:@localhost:1521:XE";
	static String connUrl="oracle.jdbc.driver.OracleDriver";
	
	public static Connection getConnection() throws WalletException{
		
		try {
			Class.forName(connUrl);
			return DriverManager.getConnection(url,"system","orcl11g");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new WalletException(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new WalletException(e.getMessage());
		}		
	}
}
