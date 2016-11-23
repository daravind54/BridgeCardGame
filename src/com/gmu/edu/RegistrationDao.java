package com.gmu.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationDao 
{
	public void registerUser(Registration registration) throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g","adasari2","eecooc");
		String sql="insert into userdetails VALUES (?, ?, ?, ?)";
		PreparedStatement preparedStatement=con.prepareStatement(sql);
		preparedStatement.setString(1, registration.getName());
		preparedStatement.setString(2, registration.getCity());
		preparedStatement.setString(3, registration.getEmail());
		preparedStatement.setString(4, registration.getPassword());
		preparedStatement.executeUpdate();
		System.out.println("Record is inserted into Database");
	}
}
