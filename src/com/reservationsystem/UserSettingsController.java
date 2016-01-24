package com.reservationsystem;


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
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class UserSettingsController implements Initializable{

	@FXML
	Label mailInDbLabel;
	
	@FXML
	Label phoneInDbLabel;
	
	@FXML
	ProgressIndicator progressIndicator;
	
	@FXML
	Label mailLabel;
	
	@FXML
	Label phoneLabel;
	
	@FXML
	CheckBox passwordCB;
	
	@FXML
	TextField phoneField;
	
	@FXML
	TextField mailField;
	
	@FXML
	PasswordField oldPassword;
	
	@FXML
	PasswordField newPassword;
	
	@FXML
	PasswordField reNewPassword;
	
	@FXML
	Pane passPane;
	
	@FXML
	Pane mainPane;
	
	@FXML
	Button cancel;
	
	@FXML
	Label toShortPasswordLabel;
	
	@FXML
	Label identicalPasswordsLabel;
	
	@FXML 
	Label minPasswordLabel;
	
	@FXML
	Button applyChanges;
	
	@FXML
	CheckBox noPhoneNumberCB;
	
	@FXML
	Label incorrectMailLabel;
	
	@FXML
	Label incorrectPhoneLabel;
	
	@FXML
	Label incorrectOldPasswordLabel;
	
	@FXML
	Label successLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		progressIndicator.setStyle("-fx-background-color: #FFFFFF;");
		phoneField.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(9));
		progressIndicator.setVisible(true);
		mainPane.setVisible(false);
		passPane.setVisible(false);
		GetSettings task = new GetSettings();
		new Thread(task).start();
		
	}


    public EventHandler<KeyEvent> numeric_Validation(final Integer max_Lenght) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField txt_TextField = (TextField) e.getSource();                
                if (txt_TextField.getText().length() >= max_Lenght) {                    
                    e.consume();
                }
                if(e.getCharacter().matches("[0-9.]")){ 
                    if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")){
                        e.consume();
                    }else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")){
                        e.consume(); 
                    }
                }else{
                    e.consume();
                }
            }
        };
    }  
	
	public void phoneCbAction()
	{
		if (noPhoneNumberCB.isSelected())
			phoneField.setDisable(true);
		else
			phoneField.setDisable(false);
	}
	
	public void changePasswordAction()
	{
		if (passwordCB.isSelected())
			passPane.setVisible(true);
		else
			passPane.setVisible(false);
	
	}
	
	 public void changeScene()
	 {
	    Platform.runLater(new Runnable() {
	      @Override public void run() {
	          Parent user_panel_page = null;
			try {
				user_panel_page = FXMLLoader.load(getClass().getResource("UserPanel.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	          Scene user_panel_scene = new Scene(user_panel_page);
	          Stage app_stage = (Stage) cancel.getScene().getWindow();
	          app_stage.setTitle("Panel u¿ytkownika");
	          app_stage.setScene(user_panel_scene);
	          app_stage.centerOnScreen();
	          app_stage.show();
	          
	          
	      }
	    });
	  }
	 
	 public void cancelAction()
	 {
		 changeScene();
	 }
	 
	 
	
	 public boolean isValidEmailAddress(String email) {
		        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		        java.util.regex.Matcher m = p.matcher(email);
		        return m.matches();
	 } 
	 
	 public void applyChangesAction()
	 {
     	 phoneField.getScene().setCursor(Cursor.WAIT);
		 clearAll();
		 boolean allFieldsCorrect = mailValidation() & phoneNumberValidation() & passwordValidation();
		 if (allFieldsCorrect)
		 {
			 ApplyChanges task = new ApplyChanges();
			 new Thread(task).start();
		 }
     	phoneField.getScene().setCursor(Cursor.DEFAULT);
	 }
	 
	 public void clearAll()
	 {
		 phoneInDbLabel.setVisible(false);
		 mailInDbLabel.setVisible(false);
		 toShortPasswordLabel.setVisible(false);
		 identicalPasswordsLabel.setVisible(false);
		 incorrectOldPasswordLabel.setVisible(false);
		 incorrectPhoneLabel.setVisible(false);
		 incorrectMailLabel.setVisible(false);
		 oldPassword.setStyle("-fx-border-color: transparent");
		 newPassword.setStyle("-fx-border-color: transparent");
		 reNewPassword.setStyle("-fx-border-color: transparent");
		 phoneField.setStyle("-fx-border-color: transparent");
		 mailField.setStyle("-fx-border-color: transparent");
	 }
	 
	 
	 public boolean mailValidation()
	 {
	    	if (isValidEmailAddress(mailField.getText()))
	    			return true;
	    	mailField.setStyle("-fx-border-color: red");
	    	incorrectMailLabel.setVisible(true);
	    	return false;
	    	
	 }
	 
	 public boolean phoneNumberValidation()
	    {
	    	if (noPhoneNumberCB.isSelected())
	    		return true;
	    	int firstDigit = Character.getNumericValue(phoneField.getText().charAt(0));
	    	if(firstDigit > 4 && firstDigit < 9 && phoneField.getLength() == 9)
	    		return true;
	    	phoneField.setStyle("-fx-border-color: red");
	    	incorrectPhoneLabel.setVisible(true);
	    	return false;
	    }
	 
	 public boolean passwordValidation()
	    {
		 if(passwordCB.isSelected()){
		 	if(oldPassword.getText().equals(Main.password) && newPassword.getText().equals(reNewPassword.getText()) && newPassword.getText().length() > 7)
		 	{
		 	 	return true;
		 	}
		 	
	    	if (!oldPassword.getText().equals(Main.password))
	    	{
	    		oldPassword.setStyle("-fx-border-color: red");
		 		incorrectOldPasswordLabel.setVisible(true);
	    		return false;
	    	}
	    	if (newPassword.getText().length() < 7)
	    	{
	    		minPasswordLabel.setVisible(false);
	    		newPassword.setStyle("-fx-border-color: red");
		 		reNewPassword.setStyle("-fx-border-color: red");
		 		toShortPasswordLabel.setVisible(true);
	    	}
	    	
	    	if (!newPassword.getText().equals(reNewPassword.getText()))
	    	{
	    		identicalPasswordsLabel.setVisible(true);
	    		newPassword.setStyle("-fx-border-color: red");
		 		reNewPassword.setStyle("-fx-border-color: red");
	    	}
	    	return false;
			
		 }
		 
	     return true;	
	    }
	 
	 public void checkPasswords() {
		   	if(!newPassword.getText().equals(reNewPassword.getText())) {
				identicalPasswordsLabel.setVisible(true);
				return;
			}
	    identicalPasswordsLabel.setVisible(false);
	    
	}
	 
	public class ApplyChanges extends Task{

		@Override
		protected Object call() throws Exception {
			applyChanges.setDisable(true);
			cancel.setDisable(true);
			Connection con = null;
			boolean allUnique = true;
		    try {
				Class.forName("com.mysql.jdbc.Driver");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			try {
				con = DriverManager
				.getConnection("jdbc:mysql://51.254.206.180/ROOMS_RESERVATION?useUnicode=true&characterEncoding=UTF-8","testUser","PASSWORD");	//user tylko do tworzenia kont
			} catch (SQLException e) {
				e.printStackTrace();
			}
			PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM USERS WHERE EMAIL = ? AND NOT LOGIN = ?");
			stmt.setString(1, mailField.getText());
			stmt.setString(2, Main.login);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next())
			{
				mailInDbLabel.setVisible(true);
				mailField.setStyle("-fx-border-color: red");
				allUnique = false;
			}
			if (!noPhoneNumberCB.isSelected())
			{
				stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM USERS WHERE TELEPHONE_NUMBER = ? AND NOT LOGIN = ?");
				stmt.setInt(1, Integer.parseInt(phoneField.getText()));
				stmt.setString(2, Main.login);
				rs = stmt.executeQuery();
				if (rs.next())
				{
					allUnique = false;
					phoneInDbLabel.setVisible(true);
					phoneField.setStyle("-fx-border-color: red");
				}
				
			}
			con.close();
			
			boolean success = false;
			if (allUnique)
			{
				int phoneNo = 0;
				if (!noPhoneNumberCB.isSelected())
					phoneNo = Integer.parseInt(phoneField.getText());
				Connection con2 = Main.getConnection();
				if(passwordCB.isSelected())
				{
					PreparedStatement stmt2 = (PreparedStatement) con2.prepareStatement("UPDATE users_settings SET EMAIL = ?,TELEPHONE_NUMBER = ?");
					stmt2.setString(1, mailField.getText());
					stmt2.setInt(2, phoneNo);
					stmt2.execute();
					String sql_s = "SET PASSWORD = PASSWORD('" + newPassword.getText() + "');";
					Statement stmt_p = (Statement) con2.createStatement();
					stmt_p.execute(sql_s);
					Main.password = newPassword.getText();
					success = true;
					
				}
				else
				{
					PreparedStatement stmt2 = (PreparedStatement) con2.prepareStatement("UPDATE users_settings SET EMAIL = ?,TELEPHONE_NUMBER = ?");
					stmt2.setString(1, mailField.getText());
					stmt2.setInt(2, phoneNo);
					stmt2.execute();
					success = true;
				}
				con2.close();
			}
			
			
			if(success)
			{
				successLabel.setVisible(true);
				Thread.sleep(700);
				Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                    	phoneField.getScene().setCursor(Cursor.DEFAULT);
                    	applyChanges.setDisable(false);
                    	cancel.setDisable(false);
                    	cancelAction();
                    }
                });
			}
			applyChanges.setDisable(false);
			cancel.setDisable(false);
        	phoneField.getScene().setCursor(Cursor.DEFAULT);
			return null;
		}
		
	}
	 
	public class GetSettings extends Task{
		   String mail;
		   int phoneNumber;
		   public GetSettings()
		   {
			   mail = "";
			   phoneNumber = 0;
		   }
		
		    
		    @Override
		    protected Object call() throws Exception {
		    	Connection con = null;
		    	try {
		    	con = Main.getConnection();
		    	PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT email, telephone_number FROM users_settings");
		    	ResultSet rs = stmt.executeQuery();
		    	while (rs.next())
		    	{
		    		mail = rs.getString(1);
		    		phoneNumber = rs.getInt(2);
		    	}
		    	} catch (SQLException e) {
			    	Thread.sleep(300);
			    	changeScene();
		    	}
		    	
		    	Thread.sleep(300);
		    	Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                    	mailField.setText(mail);
                    	if (phoneNumber == 0)
                    	{
                    	phoneField.setDisable(true);
                    	noPhoneNumberCB.setSelected(true);
                    	}
                    	else
                    	{
                    		phoneField.setText(Integer.toString(phoneNumber));
                    	}
                    	progressIndicator.setVisible(false);
                    	mainPane.setVisible(true);
                    }
                });

		    	con.close();
		    	
		    	
		        return null;
		        
		    }
		}

}


	
