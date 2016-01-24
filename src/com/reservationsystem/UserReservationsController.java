package com.reservationsystem;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;




public class UserReservationsController implements Initializable{

	@FXML
	Button cancel;
	
	@FXML
	ChoiceBox<String> typeOfReservation;
	
    @FXML
    private TableView <Reservation> userReservations;
    
    @FXML
    private Pane waitPane;
    
    @FXML
    Label label;
    
    
    ObservableList<String> types = FXCollections.observableArrayList("Oczekuj¹ce na zaakceptowanie", "Odrzucone", "Przysz³e", "Przesz³e");
    
    @FXML
    private TableColumn< Reservation, String > dateColumn;
    
    @FXML
    private TableColumn<Reservation , String> hourColumn;
    
    @FXML
    private TableColumn<Reservation , String> dayColumn;
    
    @FXML
    private TableColumn<Reservation , String> buildingColumn;
    
    @FXML
    private TableColumn<Reservation , String> roomColumn;
    
	ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		label.setFont(Font.font("Segoe UI", 16));
		userReservations.setVisible(false);
	    typeOfReservation.setItems(types);
	    typeOfReservation.setValue("Przysz³e");
		dateColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("date"));
	    buildingColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("building"));
	    dayColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("day"));
	    roomColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("room"));
	    hourColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("hour"));
	    userReservations.setItems(reservations);
	    userReservations.setPlaceholder(new Label("Brak rezerwacji"));
	    acceptedReservations task_accepted = new acceptedReservations();
	    new Thread(task_accepted).start();
	    
	    typeOfReservation.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				switch (newValue.intValue())
				{
				case 0:
					//userReservations.getSelectionModel().clearSelection();
					typeOfReservation.setDisable(true);
					userReservations.setVisible(false);
					waitPane.setVisible(true);
					waitingReservations task_waiting= new waitingReservations();
				    new Thread(task_waiting).start();
					break;
				case 1:
					//userReservations.getSelectionModel().clearSelection();
					typeOfReservation.setDisable(true);
					userReservations.setVisible(false);
					waitPane.setVisible(true);
					deletedReservations task_deleted = new deletedReservations();
				    new Thread(task_deleted).start();
					break;
				case 2:
					typeOfReservation.setDisable(true);
					userReservations.setVisible(false);
					waitPane.setVisible(true);
					acceptedReservations task_accepted = new acceptedReservations();
				    new Thread(task_accepted).start();
					break;
				case 3:
					typeOfReservation.setDisable(true);
					userReservations.setVisible(false);
					waitPane.setVisible(true);
					oldReservations task_old = new oldReservations();
				    new Thread(task_old).start();
					break;
				default:
					break;
			
				}
	               
			}
		}); 
	    
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
	 
	 
	 String[] daysTable = {"Niedziela", "Poniedzia³ek", "Wtorek", "Œroda", "Czwartek", "Pi¹tek", "Sobota"};
	 
	 public class acceptedReservations extends Task{
		    
		    @Override
		    protected Object call() throws Exception {
		    	reservations.clear();
				Connection con = Main.getConnection();
				Date date = Date.valueOf(LocalDate.now());
				PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT Date, Hour, Day, Room, Building FROM RESERVATIONS WHERE DATE >= ? AND USER = ? ");
				stmt.setDate(1, date);
				stmt.setString(2, Main.login);
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					Reservation e = new Reservation(rs.getDate("DATE").toString(),daysTable[rs.getDate("DATE").getDay()],rs.getString("HOUR"),rs.getString("BUILDING"), rs.getString("ROOM"));
					reservations.add(e);
				}
				
				Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                    	waitPane.setVisible(false);
                    	userReservations.setVisible(true);
                    	userReservations.setItems(reservations);
    					typeOfReservation.setDisable(false);
                    }
                });
				con.close();
				return null;
		        
		    }
		}
	 
	 public class waitingReservations extends Task{
		    
		    @Override
		    protected Object call() throws Exception {
		    	reservations.clear();
				Connection con = Main.getConnection();
				PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT Date, Hour, Day, Room, Building FROM users_reservations");
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					Reservation e = new Reservation(rs.getDate("DATE").toString(),daysTable[rs.getDate("DATE").getDay()],rs.getString("HOUR"),rs.getString("BUILDING"), rs.getString("ROOM"));
					reservations.add(e);
				}
				
				Platform.runLater(new Runnable(){
                 @Override
                 public void run() {
                 	waitPane.setVisible(false);
                 	userReservations.setVisible(true);
                 	userReservations.setItems(reservations);
 					typeOfReservation.setDisable(false);
                 }
             });
				con.close();
				return null;
		        
		    }
		}
	 
	 public class deletedReservations extends Task{
		    
		    @Override
		    protected Object call() throws Exception {
		    	reservations.clear();
				Connection con = Main.getConnection();
				PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT Date, Hour, Day, Room, Building FROM users_deleted_reservations ");
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					Reservation e = new Reservation(rs.getDate("DATE").toString(),daysTable[rs.getDate("DATE").getDay()],rs.getString("HOUR"),rs.getString("BUILDING"), rs.getString("ROOM"));
					reservations.add(e);
				}
				
				Platform.runLater(new Runnable(){
              @Override
              public void run() {
             	waitPane.setVisible(false);
              	userReservations.setVisible(true);
					typeOfReservation.setDisable(false);
              	userReservations.setItems(reservations);
              }
          });
				con.close();
				return null;
		        
		    }
		}
	 
	 public class oldReservations extends Task{
		    
		    @Override
		    protected Object call() throws Exception {
		    	reservations.clear();
				Connection con = Main.getConnection();
				Date date = Date.valueOf(LocalDate.now());
				PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT Date, Hour, Day, Room, Building FROM RESERVATIONS WHERE DATE < ? AND USER = ? ");
				stmt.setDate(1, date);
				stmt.setString(2, Main.login);
				ResultSet rs = stmt.executeQuery();
				
				while (rs.next()) {
					Reservation e = new Reservation(rs.getDate("DATE").toString(),daysTable[rs.getDate("DATE").getDay()],rs.getString("HOUR"),rs.getString("BUILDING"), rs.getString("ROOM"));
					reservations.add(e);
				}
				
				Platform.runLater(new Runnable(){
                 @Override
                 public void run() {
                	waitPane.setVisible(false);
                 	userReservations.setVisible(true);
					typeOfReservation.setDisable(false);
                 	userReservations.setItems(reservations);
                 }
             });
				con.close();
				return null;
		        
		    }
		}

}



	
