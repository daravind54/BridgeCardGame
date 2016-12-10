package com.gmu.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gmu.util.BCrypt;

public class LoginDao {
	public String loginUser(Login login) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g", "bkumari",
				"coaree");
		String sql = "select password from userdetails where email=?";
		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.setString(1, login.getEmail());
		ResultSet resultSet = preparedStatement.executeQuery();

		String passwordHash = null;
		while (resultSet.next()) {
			passwordHash = resultSet.getString("password");
		}
		System.out.println(">>logindao hashed pwd is " + passwordHash);

		if (BCrypt.checkpw(login.getPassword(), passwordHash))
			return "SUCCESS";
		else
			return "FAIL";
	}
}
