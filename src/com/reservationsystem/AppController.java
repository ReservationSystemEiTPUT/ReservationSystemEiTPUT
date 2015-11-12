package com.reservationsystem;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class AppController {
	@FXML 
	public TextField username;
	@FXML 
	public TextField password;
	@FXML 
	public TextField wyswietlacz;
	public void loginClicked() 
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("JEEEE");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
		Connection connection = null;

		try {
			connection = DriverManager
			.getConnection("jdbc:mysql://51.254.206.180:3306/ROOMS_RESERVATION",username.getText(),password.getText());
		} catch (SQLException e) {
			wyswietlacz.setText("ZJEBA£EŒ");
			System.out.println("Connection Failed! Check output console");
			//e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			wyswietlacz.setText("UDA£O CI SIÊ KURWA");
		} else {
			wyswietlacz.setText("ZJEBA£EŒ");
			System.out.println("Failed to make connection!");

		}
	}
	
	public void wpisywanie()
	{
	}
}