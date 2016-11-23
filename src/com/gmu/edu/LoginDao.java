package com.gmu.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDao 
{
	public String loginUser(Login login) throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g","adasari2","eecooc");
		String sql="select email,password from userdetails where email=?";
		Statement statement=con.createStatement();
		ResultSet resultSet=statement.executeQuery(sql);
		String email=null;
		String password=null;
		while(resultSet.next())
		{
			 email=resultSet.getString("email");
			 password=resultSet.getString("password");
			 System.out.println(email+""+password);
		}
		if(email.equals(login.getEmail()) && password.equals(login.getPassword()))
			return "SUCCESS";
		else
			return "FAIL";
	}
}
