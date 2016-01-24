package com.reservationsystem;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import com.mysql.jdbc.PreparedStatement;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Callback;



public class UserReservationPanelContoller implements Initializable{

	@FXML
	DatePicker dateBox;
	
	@FXML
	ChoiceBox<String> hourChoice;
	
	@FXML
	ChoiceBox<String> sizeChoice;
	
	@FXML
	ChoiceBox<String> buildingChoice;
	
	@FXML
	ChoiceBox<String> roomChoice;
	
	@FXML
	Label reservationSucceed;
	
	@FXML
	Button create;
	
	@FXML
	Button cancel;
	
	@FXML
	Label noRoomsNotification;
	
	@FXML
	Label reservationUnSucceed;
	
	ObservableList<String> HoursList = FXCollections
			.observableArrayList("8:00-9:30","9:45-11:15","11:45-13:15","13:30-15:00","15:15-16:45","17:00-18:30","18:45-20:15");
	
	ObservableList<String> buildingsList = FXCollections
			.observableArrayList();
	
	ObservableList<String> roomsList = FXCollections
			.observableArrayList();
	
	ObservableList<String> sizeList = FXCollections
			.observableArrayList("Dowolna", "Do 15 osób", "Do 30 osób");
	int[] sizeChoiceArray = {1000,16,31};
	
	String[] daysTable = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		create.setDisable(true);
		hourChoice.setItems(HoursList);
		roomChoice.setDisable(true);
		sizeChoice.setDisable(true);
		buildingChoice.setDisable(true);
		sizeChoice.setItems(sizeList);
		hourChoice.getSelectionModel().clearSelection();		
		LocalDate day = LocalDate.now().plusDays(1);
		dateBox.setValue(day);
		
		final Callback<DatePicker, DateCell> dayCellFactory = 
	            new Callback<DatePicker, DateCell>() {
	                @Override
	                public DateCell call(final DatePicker datePicker) {
	                    return new DateCell() {
	                        @Override
	                        public void updateItem(LocalDate item, boolean empty) {
	                            super.updateItem(item, empty);
	                           
	                            if (item.isBefore(day) || item.isAfter(day.plusDays(13))) {
	                                    setDisable(true);
	                                    setStyle("-fx-background-color: #ffc0cb;");
	                            }   
	                    }
	                };
	            }
	        };
	        dateBox.setDayCellFactory(dayCellFactory);
	        
	        sizeChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					if (newValue.intValue() != -1){
						
						reservationUnSucceed.setVisible(false);
						noRoomsNotification.setVisible(false);
	    		    	buildingChoice.getSelectionModel().clearSelection();
					    roomChoice.getSelectionModel().clearSelection();
					    if (!roomChoice.getItems().isEmpty())
					       roomChoice.getItems().clear();
					    if (!buildingChoice.getItems().isEmpty())
						   roomChoice.getItems().clear();
					    
	    		    	roomChoice.setDisable(true);
	    		    	buildingChoice.setDisable(true);
						sizeChanged task = new sizeChanged(sizeChoiceArray[newValue.intValue()]);
			            new Thread(task).start(); //2
					}
				}
			}); 
	        
	        hourChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					reservationUnSucceed.setVisible(false);
					noRoomsNotification.setVisible(false);
					sizeChoice.setDisable(false);
					buildingChoice.setDisable(true);
					roomChoice.setDisable(true);
				}
			});
	        
	        buildingChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number> () {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					if (newValue.intValue() != -1){
					reservationUnSucceed.setVisible(false);
					roomChoice.getSelectionModel().clearSelection();
					roomChoice.getItems().clear();
					buildingChanged task = new buildingChanged (buildingChoice.getItems().get(newValue.intValue()));
					new Thread(task).start();
					}
				}
			});
	        
	        roomChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number> () {

				@Override
				
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					create.setDisable(false);
					reservationUnSucceed.setVisible(false);
					
				}
			});
	       
	        
	       
	}
	
	public void createReservation() {
		createButton task = new createButton();
		new Thread(task).start();
	}
	

	public class WhatID implements Callable {

		private String return_id() throws SQLException {
			Connection NewConnection = Main.getConnection();
			PreparedStatement MyStatement = (PreparedStatement) NewConnection
					.prepareStatement("select id from RESERVATIONS ORDER BY ID DESC LIMIT 1");
			ResultSet MyResult = MyStatement.executeQuery();
			MyResult.next();
			int result = MyResult.getInt(1);
			NewConnection.close();
			return Integer.toString(result + 1);
		}
		
		@Override
		public Object call() throws Exception {
			// TODO Auto-generated method stub
			return return_id();
		}
		
	}
	
	public class createButton extends Task {

		@Override
		protected Object call() throws Exception {
			boolean created = false;
			create.getScene().setCursor(Cursor.WAIT);
			LocalDate localDate = dateBox.getValue();
			Date date = Date.valueOf(localDate);
			Connection con = Main.getConnection();
		    PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT * FROM RESERVATIONS WHERE DATE = ? AND ROOM = ? AND BUILDING = ? AND HOUR = ? ");
		    stmt.setDate(1, date);
		    stmt.setString(2, roomsList.get(roomChoice.getSelectionModel().getSelectedIndex()));
		    stmt.setString(3, buildingChoice.getValue());
		    stmt.setString(4, HoursList.get(hourChoice.getSelectionModel().getSelectedIndex()));
		    ResultSet rs = stmt.executeQuery();
		   
		    if (!rs.next()){
		    
				stmt = (PreparedStatement) con.prepareStatement("INSERT INTO users_reservations VALUES(?,?,?,?,?,?,?)");
				WhatID thread1 = new WhatID();
                String id = (String) thread1.call();
                stmt.setString(1,id);
				stmt.setDate(2,date);
				stmt.setString(3,HoursList.get(hourChoice.getSelectionModel().getSelectedIndex()));
				stmt.setString(4, daysTable[date.getDay()]);
				stmt.setString(5, roomsList.get(roomChoice.getSelectionModel().getSelectedIndex()));
				stmt.setString(6, buildingChoice.getValue());
				stmt.setString(7, Main.login);
				stmt.execute();
				reservationSucceed.setVisible(true);
				created = true;
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            changeScene();
	            
		    }
		    else
		    {
		    	reservationUnSucceed.setVisible(true);

		    }
			
		    create.getScene().setCursor(Cursor.DEFAULT);
		    con.close();
			return null;
		}
		
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
	          Stage app_stage = (Stage) create.getScene().getWindow();
	          app_stage.setTitle("Panel u¿ytkownika");
	          app_stage.setScene(user_panel_scene);
	          app_stage.centerOnScreen();
	          app_stage.show();
	          
	          
	      }
	    });
	  }
	
	public void cancelAction(ActionEvent event) throws IOException
	{
	    	changeScene();
	}
	
	public void onDateChange()
	{
		reservationUnSucceed.setVisible(false);
		noRoomsNotification.setVisible(false);
		hourChoice.setDisable(false);
		hourChoice.getSelectionModel().clearSelection();
		sizeChoice.setDisable(true);
		buildingChoice.setDisable(true);
	    roomChoice.setDisable(true);
	}
	
	 public class sizeChanged extends Task{
		 
		 public int newSize;
		 public String newHour;
		 public sizeChanged(int n_size) {
			 newSize = n_size;
			 newHour = HoursList.get(hourChoice.getSelectionModel().getSelectedIndex());
		 }
		    
		    @Override
		    protected Object call() throws Exception {
		    	//roomsList.clear();
		    	ObservableList<String> buildingsList2 = FXCollections
		    			.observableArrayList();
		    	LocalDate localDate = dateBox.getValue();
				Date date = Date.valueOf(localDate);	
				Connection con = Main.getConnection();
				try {
					PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT DISTINCT BUILDING FROM ROOMS WHERE BUILDING NOT IN (SELECT "
							+ "DISTINCT BUILDING FROM RESERVATIONS WHERE DATE = ? AND HOUR = ?) AND Size < ? ;");
					stmt.setDate(1, date);
					stmt.setString(2, newHour);
					stmt.setInt(3, newSize);
					ResultSet rs = stmt.executeQuery();
					
				    while (rs.next())
				    {
				    	String building = rs.getString("BUILDING");
				    	if (Main.employed || !building.equals("CW"))
				    		buildingsList2.add(building);
				    	
				    }

     			    stmt = (PreparedStatement) con.prepareStatement("SELECT DISTINCT BUILDING FROM RESERVATIONS WHERE DATE = ? AND HOUR = ? ;");
				    stmt.setDate(1, date);
					stmt.setString(2, newHour);
				    rs = stmt.executeQuery();

				    while(rs.next())
				    {
				    	String building = rs.getString("BUILDING");
				    	PreparedStatement stmt2 = (PreparedStatement) con.prepareStatement("SELECT BUILDING FROM ROOMS WHERE NUMBER NOT IN (SELECT ROOM FROM "
				    			+ "RESERVATIONS WHERE BUILDING = ? AND DATE = ? AND HOUR = ?) AND SIZE < ? AND BUILDING = ?");
				    	stmt2.setString(1, building);
				    	stmt2.setDate(2, date);
				    	stmt2.setString(3, newHour);
				    	stmt2.setInt(4, newSize);
				    	stmt2.setString(5, building);
				    	ResultSet rs2 = stmt2.executeQuery();
				    	
				    	if (rs2.next())
				    	{
				    		if (Main.employed || !building.equals("CW"))
					    	buildingsList2.add(building);
				    	}
				    	//buildingsList = buildingsList.sorted();

				    }

				    con.close();

		
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                    	buildingChoice.setItems(buildingsList2);
                    	buildingChoice.setDisable(false);
                    	buildingChoice.getSelectionModel().clearSelection();
                    }
                });
		        return null;
		        
		    }
		}
	 
	 public class buildingChanged extends Task{
		   
		 public String newBuilding;
		 private ObservableList<String> freeBuildings;
		 public buildingChanged(String new_b) {
			 newBuilding = new_b;
		 }
		    
		    @Override
		    protected Object call() throws Exception {
		    	LocalDate localDate = dateBox.getValue();
				Date date = Date.valueOf(localDate);	
				Connection con = Main.getConnection();
				roomsList.clear();
			PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT NUMBER FROM ROOMS WHERE NUMBER NOT IN (SELECT ROOM FROM RESERVATIONS"
						+ " WHERE BUILDING = ? AND DATE = ? AND HOUR = ?) AND SIZE < ? AND BUILDING = ? ");
				stmt.setString(1, newBuilding);
				stmt.setDate(2, date);
				stmt.setString(3, HoursList.get(hourChoice.getSelectionModel().getSelectedIndex()));
				stmt.setInt(4, sizeChoiceArray[sizeChoice.getSelectionModel().getSelectedIndex()] );
				stmt.setString(5, newBuilding);
				ResultSet rs = stmt.executeQuery();
				while (rs.next())
				{
					roomsList.add(Integer.toString(rs.getInt("NUMBER")));
				}
				
				Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                    	roomChoice.setDisable(false);
                    	roomChoice.getSelectionModel().clearSelection();
                    	roomChoice.setItems(roomsList);
                    }
                });
				con.close();
		        return null;
		        
		    }
		}


}

	
