package com.gmu.edu;

public class RegistrationDao 
{
	public void registerUser(Registration registration) throws ClassNotFoundException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g","adasari2","psaduh");
	}
}
