package com.reservationsystem;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;



public class UserPanelController implements Initializable{

	@FXML
	Label welcomeLabel;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		welcomeLabel.setText("Witaj " + Main.name + "!");
		
	}
	
	@FXML
    private void handleNewReservationButton(ActionEvent event) throws IOException {
	
        System.out.println("You clicked me!");
        Parent create_new_account_page = FXMLLoader.load(getClass().getResource("UserReservationPanel.fxml"));
        Scene create_new_account_scene = new Scene(create_new_account_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setTitle("Nowa rezerwacja");
        app_stage.setScene(create_new_account_scene);
        app_stage.centerOnScreen();
        app_stage.show();
        
		
    }
	
	@FXML
    private void handleUserSettingsButton(ActionEvent event) throws IOException {
		Parent create_new_account_page = FXMLLoader.load(getClass().getResource("UserSettings.fxml"));
        Scene create_new_account_scene = new Scene(create_new_account_page);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setTitle("Ustawienia konta");
        app_stage.setScene(create_new_account_scene);
        app_stage.centerOnScreen();
        app_stage.show();
	}
	
	@FXML
	private void handleCancelButton() throws IOException
	{
			Main.login = "";
			Main.password = "";
			Main.name = "";
			Main.employed = false;
			Parent login_panel_page = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
	        Scene login_panel_scene = new Scene(login_panel_page);
	        Stage app_stage = (Stage) welcomeLabel.getScene().getWindow();
	        app_stage.setTitle("System Rezerwacji Sal");
	        app_stage.setScene(login_panel_scene);
	        app_stage.centerOnScreen();
	        app_stage.show();
	}

}


	
