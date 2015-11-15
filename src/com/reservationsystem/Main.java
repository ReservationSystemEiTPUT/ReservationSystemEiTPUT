package com.reservationsystem;
	
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	public static Connection con = null;
	public static String login;
	public static String password;
	
	@Override
	public void start(Stage primaryStage) throws IOException  {
		Platform.setImplicitExit(false);

		Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
		Scene scene = new Scene(root);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("DUPNA APLIKACJA");
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
		//primaryStage.setResizable(false);
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
			.getConnection("jdbc:mysql://51.254.206.180/ROOMS_RESERVATION",login,password);
		} catch (SQLException e) {
			
			if (e.getErrorCode() == 1045)
		
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}

		if (con != null) {
			System.out.println("You made it, take control your database now!");
			return con;
		    
		} else {
			
			System.out.println("Failed to make connection!");

		}
		return null;
	}
	
	
}
