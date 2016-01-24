package com.reservationsystem;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	Text databaseError;
	
	@FXML
	Button loginButton;
	
	boolean isAdmin;
	
	@FXML
	ImageView logoView;
	
	
	@FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
	
        Parent create_new_account_page = FXMLLoader.load(getClass().getResource("CreatingAccountPanel.fxml"));
        Scene create_new_account_scene = new Scene(create_new_account_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setTitle("Rejestracja konta użytkownika");
        app_stage.setScene(create_new_account_scene);
        app_stage.centerOnScreen();
        app_stage.show();
        
		
    }
	

	public void loginButton() {
        incorrectLogin.setVisible(false);
        databaseError.setVisible(false);
	    loadingBar.setVisible(true);
	    databaseAccess thread1 = new databaseAccess();
	    (new Thread(thread1)).start();
		
	}
	
	public void setKeyPressed (KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER))
		{
			loginButton();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		isAdmin=false;
	    incorrectLogin.setStyle("-fx-font-weight: 500;");
        Image image = new Image(this.getClass().getResourceAsStream("logoput.jpg"));
        logoView.setImage(image);
	}
	
	public boolean isAdmin() throws SQLException {
		
		Connection con = Main.getConnection();
		PreparedStatement stmt1 = (PreparedStatement) con.prepareStatement("SHOW TABLES");
		stmt1.execute();
		PreparedStatement stmt2 = (PreparedStatement) con.prepareStatement("SELECT FOUND_ROWS()");
		ResultSet rs = stmt2.executeQuery();
		int rowsNumber = 0;
		if (rs.next())
		{
			rowsNumber = rs.getInt(1);
		}
		
		PreparedStatement stmt3 = (PreparedStatement) con.prepareStatement("SELECT name, employed from users_settings");
	    rs = stmt3.executeQuery();
	    String employed = "";
	    if (rs.next()){
	      Main.name = rs.getString("name");
	      employed = rs.getString("employed");
	    } else {
	      Main.name = "";
	    }
	    if (employed.equals("NO"))
	    	Main.employed = false;
	    else
	    	Main.employed = true;
		con.close();
		if (rowsNumber > 3 && rowsNumber < 6)
			return false;
		else
			return true;
		
	}
	public class databaseAccess implements Runnable {

		@Override
		public void run() {
			Connection con = null;
		    try {
				Class.forName("com.mysql.jdbc.Driver");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return;
			}

			try {
				con = DriverManager
				.getConnection("jdbc:mysql://51.254.206.180",loginField.getText(),passwordField.getText());
			} catch (SQLException e) {
				loadingBar.setVisible(false);
				
				if (e.getErrorCode() == 1045)
					incorrectLogin.setVisible(true);
				else 
					databaseError.setVisible(true);
			
				e.printStackTrace();
				return;
			}

			if (con != null) {
				Main.login = loginField.getText();
				Main.password = passwordField.getText();
				boolean admin = false;
				try {
					admin = isAdmin();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				loginCorrect(admin);
			    
			} else {
				

			}
			
			
		}
	}

	public void loginCorrect(boolean admin)
	 {
	    Platform.runLater(new Runnable() {
	      @Override public void run() {
	          Parent logged_page = null;
			try {
				if (admin){
				logged_page = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
				}
				else
				{
				logged_page = FXMLLoader.load(getClass().getResource("UserPanel.fxml"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	          Scene logged_page_scene = new Scene(logged_page);
	          Stage app_stage = (Stage) loadingBar.getScene().getWindow();
	          app_stage.setScene(logged_page_scene);
	          app_stage.centerOnScreen();
	          app_stage.show();
	      }
	    });
	  }
	 
	
}
