package com.gmu.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginDao 
{
	public String loginUser(Login login) throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g","adasari2","eecooc");
		String sql="select * from userdetails";
	}
}
