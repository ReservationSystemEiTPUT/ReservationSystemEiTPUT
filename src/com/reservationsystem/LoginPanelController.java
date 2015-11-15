package com.reservationsystem;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;




public class LoginPanelController implements Initializable{
 
	@FXML
	ProgressBar loadingBar;
	
	@FXML
	TextField loginField;
	
	@FXML
	PasswordField passwordField;
	
	@FXML
	Text incorrectLogin;
	
	@FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("App.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         
        app_stage.setScene(home_page_scene);
        app_stage.centerOnScreen();
        app_stage.show();
        
    }
	
	public void loginButton() {

	    loadingBar.setVisible(true);
	    databaseAccess thread1 = new databaseAccess();
	    (new Thread(thread1)).start();
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	public class databaseAccess implements Runnable {

		@Override
		public void run() {
			Connection connection = null;
	        int wasError=0;
		    try {
				Class.forName("com.mysql.jdbc.Driver");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return;
			}

			try {
				connection = DriverManager
				.getConnection("jdbc:mysql://51.254.206.180",loginField.getText(),passwordField.getText());
			} catch (SQLException e) {
				loadingBar.setVisible(false);
				
				if (e.getErrorCode() == 1045)
					incorrectLogin.setVisible(true);
				
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;
			}

			if (connection != null) {
				System.out.println("You made it, take control your database now!");
				
			} else {
				
				System.out.println("Failed to make connection!");

			}
			
		}
     

	}
		
}


	
