package com.gmu.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDao 
{
	public String loginUser(Login login) throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g","adasari2","eecooc");
		String sql="select password from userdetails where email=?";
		PreparedStatement preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1, login.getEmail());
		ResultSet resultSet=preparedStatement.executeQuery(sql);
		
		String password=null;
		while(resultSet.next())
		{
			 
			 password=resultSet.getString("password");
			 System.out.println(password);
		}
		if(password.equals(login.getPassword()))
			return "SUCCESS";
		else
			return "FAIL";
	}
}
