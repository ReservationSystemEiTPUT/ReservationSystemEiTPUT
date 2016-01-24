package com.reservationsystem;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static Connection con = null;
	public static String login;
	public static String password;
	public static String name;
	public static boolean employed;
	@Override
	public void start(Stage primaryStage) throws IOException  {
		Platform.setImplicitExit(false);
		Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("System Rezerwacji Sal");
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static Connection getConnection ()
	{
		Connection con = null;
	    try {
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		try {
			con = DriverManager
			.getConnection("jdbc:mysql://51.254.206.180/ROOMS_RESERVATION?useUnicode=true&characterEncoding=UTF-8",login,password);
		} catch (SQLException e) {
			
			if (e.getErrorCode() == 1045)
		
			e.printStackTrace();
			return null;
		}

		if (con != null) {
			return con;
		    
		} else {
			
		}
		return null;
	}
	
	
}
