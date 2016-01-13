package com.reservationsystem;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	public static Connection con = null;
	public static String login;
	public static String password;
	public static String name;
	public static boolean employed;
	@Override
	public void start(Stage primaryStage) throws IOException  {
		Platform.setImplicitExit(false);
		System.out.println("WERSJA JAVAFX: " + com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());
		Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
		Scene scene = new Scene(root);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("System Rezerwacji Sal");
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
		//primaryStage.setResizable(false);
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	//funkcja do otrzymywania po³¹czenia z baz¹ danych
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
		
			//System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}

		if (con != null) {
			//System.out.println("You made it, take control your database now!");
			return con;
		    
		} else {
			
			//System.out.println("Failed to make connection!");

		}
		return null;
	}
	
	public static long hashText (String text)
	{
		long hash = 5834;
		for (int i = 0; i < text.length(); i++) {
		    hash = hash*179 + text.charAt(i);
		}
		System.out.println(hash);
		return hash;
	}
	
}
