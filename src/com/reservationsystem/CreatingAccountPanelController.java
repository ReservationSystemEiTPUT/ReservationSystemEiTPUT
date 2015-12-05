package com.reservationsystem;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

public class CreatingAccountPanelController implements Initializable{

	@FXML
	Label phoneNumberLabel;
	
	@FXML
	TextField phoneNumberField;
	
	@FXML
	CheckBox phoneNumberCheckbox;
	
	@FXML
	PasswordField passwordField;
	
	@FXML
	PasswordField rePasswordField;
	
	@FXML
	Label studentNotification;
	
	@FXML
	Label employeeNotification;
	
	@FXML
	Label samePasswordLabel;
	
	@FXML
	TextField indexNo;
	
	@FXML
	CheckBox employeeCheckbox;
	
	@FXML
	Label indexNoLabel;
	
	@FXML
	TextField nameField;
	
	@FXML
	TextField surnameField;
	
	@FXML
	TextField mailField;
	
	@FXML
	Label fillAllFields;
	
	@FXML
	Label incorrectMailLabel;
	
	@FXML
	Label incorrectPhoneNumberLabel;
	
	@FXML
	Label passwordLengthLabel;
	
	@FXML
	Label incorrectPasswordLabel;
	
	@FXML
	Label incorrectIndexNumberLabel;
	
	@FXML
	Label connectionFailedLabel;
	
	@FXML
	Button cancelButton;
	
	@FXML
	Button createButton;
	
	@FXML
	Label accounCreatedSuccessful;
	
	@FXML
	Label indexRegistered;
	
	@FXML
	Label mailRegistered;
	
	@FXML
	Label phoneRegistered;
	
	boolean employee;
	boolean smsNotification;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		employee = false;
		smsNotification = false;
		indexNo.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(6));
		phoneNumberField.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(9));
		//nameField.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(50));
	//	surnameField.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(50));
	}
	
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
	
	public void checkPasswords() {
		   	if(!passwordField.getText().equals(rePasswordField.getText())) {
				samePasswordLabel.setVisible(true);
				return;
			}
	    samePasswordLabel.setVisible(false);
	    
	}
	
    public void setPhoneNumber() {
    	if (phoneNumberCheckbox.isSelected()){
    		smsNotification = true;
    		phoneNumberLabel.setVisible(true);
    		phoneNumberField.setVisible(true);
    		
    	} else {
    		smsNotification = false;
    		phoneNumberLabel.setVisible(false);
    		phoneNumberField.setVisible(false);
    	}
    }
    
    public void registerEmployee() {
    	if (employeeCheckbox.isSelected()){
    		employee = true;
    		indexNo.setVisible(false);
    		indexNoLabel.setVisible(false);
    		studentNotification.setVisible(false);
    		employeeNotification.setVisible(true);
    	} else {
    		employee = false;
    		indexNo.setVisible(true);
    		indexNoLabel.setVisible(true);
    		studentNotification.setVisible(true);
    		employeeNotification.setVisible(false);

    	}
    }
	
    public void cancel(ActionEvent event) throws IOException {
    	Parent create_new_account_page = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
        Scene create_new_account_scene = new Scene(create_new_account_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setTitle("System Rezerwacji Sal");
        app_stage.setScene(create_new_account_scene);
        app_stage.centerOnScreen();
        app_stage.show();
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
    
    public EventHandler<KeyEvent> letter_Validation(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField txt_TextField = (TextField) e.getSource();                
                if (txt_TextField.getText().length() >= max_Lengh) {                    
                    e.consume();
                }
                if(e.getCharacter().matches("[A-Za-z]") || e.getCharacter().matches("-")){ 
                }else{
                    e.consume();
                }
            }
        };
    }    
    
    public void clearAllBorders()
    {
    	nameField.setStyle("-fx-border-color: transparent");
    	surnameField.setStyle("-fx-border-color: transparent");
    	indexNo.setStyle("-fx-border-color: transparent");
    	mailField.setStyle("-fx-border-color: transparent");
    	phoneNumberField.setStyle("-fx-border-color: transparent");
    	passwordField.setStyle("-fx-border-color: transparent");
    	rePasswordField.setStyle("-fx-border-color: transparent");
		fillAllFields.setVisible(false);
		incorrectMailLabel.setVisible(false);
		incorrectPasswordLabel.setVisible(false);
		incorrectPhoneNumberLabel.setVisible(false);
		incorrectPasswordLabel.setVisible(false);
		incorrectIndexNumberLabel.setVisible(false);
		indexRegistered.setVisible(false);
		phoneRegistered.setVisible(false);
		mailRegistered.setVisible(false);

    }
    
    public boolean passwordValidation()
    {
    	if (!samePasswordLabel.isVisible() && passwordField.getLength() > 7)
    		return true;
		passwordField.setStyle("-fx-border-color: red");
		rePasswordField.setStyle("-fx-border-color: red");
		passwordLengthLabel.setVisible(false);
		incorrectPasswordLabel.setVisible(true);
     return false;	
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
    	if (!smsNotification)
    		return true;
    	int firstDigit = Character.getNumericValue(phoneNumberField.getText().charAt(0));
    	System.out.println(firstDigit);
    	if(firstDigit > 4 && firstDigit < 9 && phoneNumberField.getLength() == 9)
    		return true;
    	phoneNumberField.setStyle("-fx-border-color: red");
    	incorrectPhoneNumberLabel.setVisible(true);
    	return false;
    }
    
    public boolean indexNumberValidation()
    {
    	if (employee)
    		return true;
    	if (indexNo.getLength() < 5) {
    		incorrectIndexNumberLabel.setVisible(true);
    		indexNo.setStyle("-fx-border-color: red");
    		return false;
    	}
    	return true;
    }
    
 
    
    public void createAccount()
    {
    	createButton.setDisable(true);
    	cancelButton.setDisable(true);
    	nameField.getScene().setCursor(Cursor.WAIT);
    	clearAllBorders();
    	boolean somethingEmpty = false;
    	if(nameField.getText().isEmpty()){
    		nameField.setStyle("-fx-border-color: red");
    		somethingEmpty = true;
    	}
    	if(surnameField.getText().isEmpty()){
    		surnameField.setStyle("-fx-border-color: red");
    		somethingEmpty = true;
    	}
    	if(indexNo.getText().isEmpty() && !employee){
    		indexNo.setStyle("-fx-border-color: red");
    		somethingEmpty = true;
    	}
    	if(mailField.getText().isEmpty()){
    		mailField.setStyle("-fx-border-color: red");
    		somethingEmpty = true;
    	}
    	if(phoneNumberField.getText().isEmpty() && smsNotification){
    		phoneNumberField.setStyle("-fx-border-color: red");
    		somethingEmpty = true;
    	}
    	if(passwordField.getText().isEmpty()){
    		passwordField.setStyle("-fx-border-color: red");
    		somethingEmpty = true;
    	}
    	if(rePasswordField.getText().isEmpty()){
    		rePasswordField.setStyle("-fx-border-color: red");
    		somethingEmpty = true;
    	}
    	
    	
    	
    	boolean allFieldsCorrect = false;
    	if (somethingEmpty)
    	{
    		fillAllFields.setVisible(true);
    	} else {
    		allFieldsCorrect = mailValidation() & phoneNumberValidation() & passwordValidation() & indexNumberValidation();
    	}
    	
    	
    	if (allFieldsCorrect){
    	    databaseAccess thread1 = new databaseAccess();
    	    (new Thread(thread1)).start();
    	} else {
        	createButton.setDisable(false);
        	cancelButton.setDisable(false);
        	nameField.getScene().setCursor(Cursor.DEFAULT);
    	}
    	
    	
    	
    }
    
    

    public class databaseAccess implements Runnable {

		@Override
		public void run() {
			boolean created = false;
			Connection con = null;
		    try {
				Class.forName("com.mysql.jdbc.Driver");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return;
			}

			try {
				con = DriverManager
				.getConnection("jdbc:mysql://51.254.206.180/ROOMS_RESERVATION?useUnicode=true&characterEncoding=UTF-8","testUser","PASSWORD");	//user tylko do tworzenia kont
			} catch (SQLException e) {
				System.out.println("Connection Failed! Check output console");
				connectionFailedLabel.setVisible(true);
				connectionFailedLabel.getScene().setCursor(Cursor.DEFAULT);
				return;
			}
			
			String phoneNumber = "000000000";
			if(!phoneNumberField.getText().isEmpty()) phoneNumber = phoneNumberField.getText();
			String employeeStr = "NO";
			String login = indexNo.getText();
			int indexNumber;
			if(employee){
				employeeStr = "YES";
				indexNumber = 00000;
				login = nameField.getText().toLowerCase() + "." + surnameField.getText().toLowerCase();
				login = login.replace("-", "");			
				login = login.replace("¹", "a");
				login = login.replace("ê", "e");
				login = login.replace("¿", "z");
				login = login.replace("Ÿ", "z");
				login = login.replace("³", "l");
				login = login.replace("ó", "o");
				login = login.replace("ñ", "n");
				login = login.replace("œ", "s");
				login = login.replace("æ", "c");
				System.out.print(login);
			} else {
				indexNumber = Integer.parseInt(indexNo.getText());
			}
			
			boolean uniqueValues = true;
			try {
			PreparedStatement isIndex = (PreparedStatement) con.prepareStatement("SELECT Login FROM NEW_USERS WHERE Login = ?");
			isIndex.setString(1, login);
			ResultSet rs = isIndex.executeQuery();
			
			if (rs.next())
			{
				System.out.println("HHEEH");
				indexRegistered.setVisible(true);
				uniqueValues = false;
				
			}
			isIndex = (PreparedStatement) con.prepareStatement("SELECT Login FROM USERS WHERE Login = ?");
			isIndex.setString(1, login);
			rs = isIndex.executeQuery();
			if (rs.next())
			{
				indexRegistered.setVisible(true);
				uniqueValues = false;
			}
			
			PreparedStatement isMail = (PreparedStatement) con.prepareStatement("SELECT Email FROM NEW_USERS WHERE Email = ?");
			isMail.setString(1,mailField.getText());
			rs = isMail.executeQuery();
			if (rs.next())
			{
		    	mailField.setStyle("-fx-border-color: red");
				mailRegistered.setVisible(true);
				uniqueValues = false;
			}
			isMail = (PreparedStatement) con.prepareStatement("SELECT Email FROM USERS WHERE Email = ?");
			isMail.setString(1,mailField.getText());
			rs = isMail.executeQuery();
			if (rs.next())
			{
		    	mailField.setStyle("-fx-border-color: red");
				mailRegistered.setVisible(true);
				uniqueValues = false;
			}
			if (phoneNumberCheckbox.isSelected())
			{
			PreparedStatement isPhone = (PreparedStatement) con.prepareStatement("SELECT Telephone_number FROM NEW_USERS WHERE Telephone_number = ?");
			isPhone.setInt(1,Integer.parseInt(phoneNumber));
			rs = isPhone.executeQuery();
			if (rs.next())
			{
		    	phoneNumberField.setStyle("-fx-border-color: red");
				phoneRegistered.setVisible(true);
				uniqueValues = false;
			}
			isPhone = (PreparedStatement) con.prepareStatement("SELECT Telephone_number FROM USERS WHERE Telephone_number = ?");
			isPhone.setInt(1,Integer.parseInt(phoneNumber));
			rs = isPhone.executeQuery();
			if (rs.next())
			{
		    	phoneNumberField.setStyle("-fx-border-color: red");
				phoneRegistered.setVisible(true);
				uniqueValues = false;
			}
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			if (uniqueValues == true) {
			
            try {
				PreparedStatement stmt = (PreparedStatement) con.prepareStatement("INSERT INTO NEW_USERS VALUES(?,?,?,?,?,?,?,AES_ENCRYPT(?,UNHEX('F3229A0B371ED2D9441B830D21A390C3')),'NIE')");
				stmt.setString(1,login);
				stmt.setString(2,nameField.getText());
				stmt.setString(3,surnameField.getText());
				stmt.setString(4,mailField.getText());
				stmt.setInt(5,Integer.parseInt(phoneNumber));
				stmt.setString(6,employeeStr);
				stmt.setInt(7,indexNumber);
				stmt.setString(8,passwordField.getText());
				PreparedStatement stmt2 = (PreparedStatement) con.prepareStatement("SET NAMES utf8");
				stmt2.execute();
			    stmt.execute();
				created = true;
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			}
            
        	createButton.setDisable(false);
        	cancelButton.setDisable(false);
			connectionFailedLabel.getScene().setCursor(Cursor.DEFAULT); //and 
			if (created == true) {
            accounCreatedSuccessful.setVisible(true);
		            try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            changeScene();
		   }

		}
	}
    
    public void changeScene()
	 {
	    Platform.runLater(new Runnable() {
	      @Override public void run() {
	          Parent logged_page = null;
			try {
				logged_page = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	          Scene logged_page_scene = new Scene(logged_page);
	          Stage app_stage = (Stage) nameField.getScene().getWindow();
	          
	          app_stage.setScene(logged_page_scene);
	          app_stage.centerOnScreen();
	          app_stage.show();
	          
	          
	      }
	    });
	  }
}


		



	
