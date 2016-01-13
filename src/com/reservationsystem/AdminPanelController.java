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
	javafx.scene.control.Button DeleteReservations;
	@FXML
	javafx.scene.control.Button DeleteMultipleReservations;
	@FXML
	javafx.scene.control.Button DeleteSimpleReservations;
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
		thread1.run();
	}
	
	@FXML
	public void click_add_simple_reservation () {
		OpenSimple thread1 = new OpenSimple();
		thread1.run();
	}
	
	
	@FXML
	public void click_delete_simple_reservation() {
		OpenDeleteSimple thread1 = new OpenDeleteSimple();
		thread1.run();
	}
	
	
	@FXML
	public void click_delete_reservation () {
		DeleteReservations.setVisible(false);
		DeleteSimpleReservations.setVisible(true);
		DeleteMultipleReservations.setVisible(true);
	}
	
	@FXML
	public void click_add_new_users () {
		OpenUsers thread1 = new OpenUsers();
		thread1.run();
	}
	
	@FXML
	public void click_add_new_reservation () {
		 OpenReservations thread1 = new OpenReservations();
		  thread1.run();
	}
	
	public class OpenMultiple implements Runnable {

		private void change_view() throws IOException {
			Parent login_panel_page = FXMLLoader.load(getClass().getResource("MultipleReservationPanel.fxml"));
            Scene login_panel_scene = new Scene(login_panel_page);
            Stage app_stage = (Stage) logout.getScene().getWindow();
            app_stage.setTitle("Rezerwacje cykliczne");
            app_stage.setScene(login_panel_scene);
            app_stage.centerOnScreen();
            app_stage.show();
		}
		@Override
		public void run(){
			// TODO Auto-generated method stub
			try {
				change_view();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public class OpenSimple implements Runnable {

		private void change_view() throws IOException {
			Parent login_panel_page = FXMLLoader.load(getClass().getResource("SingleReservationAdminPanel.fxml"));
            Scene login_panel_scene = new Scene(login_panel_page);
            Stage app_stage = (Stage) logout.getScene().getWindow();
            app_stage.setTitle("Rezerwacje pojedyncze");
            app_stage.setScene(login_panel_scene);
            app_stage.centerOnScreen();
            app_stage.show();
		}
		@Override
		public void run(){
			// TODO Auto-generated method stub
			try {
				change_view();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public class OpenReservations implements Runnable {

		private void change_view() throws IOException {
			Parent login_panel_page = FXMLLoader.load(getClass().getResource("AddNewUsersReservationPanel.fxml"));
            Scene login_panel_scene = new Scene(login_panel_page);
            Stage app_stage = (Stage) logout.getScene().getWindow();
            app_stage.setTitle("Rezerwacje u¿ytkowników");
            app_stage.setScene(login_panel_scene);
            app_stage.centerOnScreen();
            app_stage.show();
		}
		@Override
		public void run(){
			// TODO Auto-generated method stub
			try {
				change_view();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public class OpenUsers implements Runnable {

		private void change_view() throws IOException {
			Parent login_panel_page = FXMLLoader.load(getClass().getResource("AddNewUsersPanel.fxml"));
            Scene login_panel_scene = new Scene(login_panel_page);
            Stage app_stage = (Stage) logout.getScene().getWindow();
            app_stage.setTitle("Nowe konta u¿ytkowników");
            app_stage.setScene(login_panel_scene);
            app_stage.centerOnScreen();
            app_stage.show();
		}
		@Override
		public void run(){
			// TODO Auto-generated method stub
			try {
				change_view();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class OpenDeleteSimple implements Runnable {
		
		private void change_view() throws IOException {
			Parent login_panel_page = FXMLLoader.load(getClass().getResource("DeleteSimpleReservationPanel.fxml"));
            Scene login_panel_scene = new Scene(login_panel_page);
            Stage app_stage = (Stage) logout.getScene().getWindow();
            app_stage.setTitle("Rezerwacje pojedyncze");
            app_stage.setScene(login_panel_scene);
            app_stage.centerOnScreen();
            app_stage.show();
		}
		@Override
		public void run(){
			// TODO Auto-generated method stub
			try {
				change_view();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class OpenDeleteMultiple implements Runnable {
		private void change_view() throws IOException {
			Parent login_panel_page = FXMLLoader.load(getClass().getResource("DeleteMultipleReservationPanel.fxml"));
            Scene login_panel_scene = new Scene(login_panel_page);
            Stage app_stage = (Stage) logout.getScene().getWindow();
            app_stage.setTitle("Rezerwacje cykliczne");
            app_stage.setScene(login_panel_scene);
            app_stage.centerOnScreen();
            app_stage.show();
		}
		@Override
		public void run(){
			// TODO Auto-generated method stub
			try {
				change_view();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void click_delete_multiple_reservation(){
		OpenDeleteMultiple thread1 = new OpenDeleteMultiple();
		thread1.run();
	}
	
	public class OpenDatabase implements Runnable {

		private void change_view() throws IOException {
			Parent login_panel_page = FXMLLoader.load(getClass().getResource("ManageDataBasePanel.fxml"));
            Scene login_panel_scene = new Scene(login_panel_page);
            Stage app_stage = (Stage) logout.getScene().getWindow();
            app_stage.setTitle("Baza danych");
            app_stage.setScene(login_panel_scene);
            app_stage.centerOnScreen();
            app_stage.show();
		}
		@Override
		public void run(){
			// TODO Auto-generated method stub
			try {
				change_view();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@FXML
	public void click_database() {
		OpenDatabase thread1 = new OpenDatabase();
		thread1.run();
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
	
	public class OpenDeleteUser implements Runnable {
		private void change_view() throws IOException {
			Parent login_panel_page = FXMLLoader.load(getClass().getResource("DeleteUserPanel.fxml"));
            Scene login_panel_scene = new Scene(login_panel_page);
            Stage app_stage = (Stage) logout.getScene().getWindow();
            app_stage.setTitle("Konta u¿ytkowników");
            app_stage.setScene(login_panel_scene);
            app_stage.centerOnScreen();
            app_stage.show();
		}
		@Override
		public void run(){
			// TODO Auto-generated method stub
			try {
				change_view();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void click_on_delete_user(){
		OpenDeleteUser thread1 = new OpenDeleteUser();
		thread1.run();
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