package com.reservationsystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminPanelController implements Initializable { 
	
	@FXML
	Button logout;
	
	@FXML
	javafx.scene.control.Button MultipleReservation;
	@FXML
	javafx.scene.control.Button SimpleReservation;
	@FXML
	javafx.scene.control.Button Reservations;
	@FXML
	public void click_add_reservation_button (){
	
		MultipleReservation.setVisible(true);
		SimpleReservation.setVisible(true);
		Reservations.setVisible(false);
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void click_add_multiple_reservation () {
		OpenMultiple thread1 = new OpenMultiple();
	    (new Thread(thread1)).start();
	}
	
	@FXML
	public void click_add_new_users () {
		OpenUsers thread1 = new OpenUsers();
	    (new Thread(thread1)).start();
	}
	
	@FXML
	public void click_add_new_reservation () {
		 OpenReservations thread1 = new OpenReservations();
		    (new Thread(thread1)).start();
	}
	
	public class OpenMultiple implements Runnable {

		@Override
		public void run() {
			open_new_window("MultipleReservationPanel.fxml","Rezerwacja cykliczna");
			
		}
		
	}
	
	public class OpenReservations implements Runnable {

		@Override
		public void run() {
			open_new_window("AddNewUsersReservationPanel.fxml","Akceptacja nowych rezerwacji");
			
		}
		
	}
	
	public class OpenUsers implements Runnable {

		@Override
		public void run() {
			open_new_window("AddNewUsersPanel.fxml","Akceptacja nowych uzytkowników");
			
		}
		
	}
	
	public void logoutAction() throws IOException
	{
		Main.login = "";
		Main.password = "";
		Main.name = "";
		Main.employed = false;
		Parent login_panel_page = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
        Scene login_panel_scene = new Scene(login_panel_page);
        Stage app_stage = (Stage) logout.getScene().getWindow();
        app_stage.setTitle("System Rezerwacji Sal");
        app_stage.setScene(login_panel_scene);
        app_stage.centerOnScreen();
        app_stage.show();
	}

	public static void open_new_window (String FXMLAddress, String Title) {
		
	    Platform.runLater(new Runnable() {
		      @Override public void run() {
		          Parent logged_page = null;
				try {
					logged_page = FXMLLoader.load(getClass().getResource(FXMLAddress));
				} catch (IOException e) {
					e.printStackTrace();
				}
		          Scene logged_page_scene = new Scene(logged_page);
		          Stage app_stage = (Stage) new Stage();
		          app_stage.setTitle(Title);
		          app_stage.setScene(logged_page_scene);
		          app_stage.centerOnScreen();
		          app_stage.show();
		          
		          
		      }
		    });
		  }
	
	

	
}