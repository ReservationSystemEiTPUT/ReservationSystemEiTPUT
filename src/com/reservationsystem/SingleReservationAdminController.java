package com.reservationsystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.Callable;

import com.mysql.jdbc.PreparedStatement;
import com.reservationsystem.AddNewUsersReservationPanelController.WhatID;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SingleReservationAdminController {
	
	@FXML
	ObservableList<String> BuildingList = FXCollections
	.observableArrayList("CW","BM","WE","Mch","Polanka");
	String BuildingTable[] = {"CW","BM","WE","Mch","Polanka"};
	String HoursTable[] = {"8:00-9:30","9:45-11:15","11:45-13:15","13:30-15:00","15:15-16:45","17:00-18:30","18:45-20:00"};
	@FXML
	ObservableList<String> RoomsList = FXCollections
			.observableArrayList();
	@FXML
	private ChoiceBox <String> BuildingBox;	
	ObservableList<String> HoursList = FXCollections
			.observableArrayList("8:00-9:30","9:45-11:15","11:45-13:15","13:30-15:00","15:15-16:45","17:00-18:30","18:45-20:00");
	
	@FXML
	private ChoiceBox <String> HoursBox;
	@FXML
	private DatePicker DateBox;
	@FXML
	Pane MyPane;
	@FXML
	private javafx.scene.control.Button AddButton;
	@FXML
	private ChoiceBox <String> RoomsNumberBox;
	@FXML
	Button YesButton;
	@FXML
	Button NoButton;
	@FXML
	public void initialize ()
	{
	    MyPane.setStyle("-fx-background-color: white;");
		DateBox.setValue(LocalDate.now().plusDays(1));
		MyPane.setVisible(false);
		OkButton.setVisible(false);
		YesButton.setVisible(false);
		NoButton.setVisible(false);
		final Callback<DatePicker, DateCell> dayCellFactory = 
	            new Callback<DatePicker, DateCell>() {
	                @Override
	                public DateCell call(final DatePicker datePicker) {
	                    return new DateCell() {
	                        @Override
	                        public void updateItem(LocalDate item, boolean empty) {
	                            super.updateItem(item, empty);
	                           
	                            if (item.isBefore(DateBox.getValue())) {
	                                    setDisable(true);
	                                    setStyle("-fx-background-color: #98FFBA;");
	                            }   
	                    }
	                };
	            }
	        }; 
	        BuildingBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					RoomsNumberBox.setDisable(false);
					PrepareStatementToGetRooms thread1 = new PrepareStatementToGetRooms(BuildingTable[newValue.intValue()]);
					ResultSet MyResult;
					try {
						MyResult = thread1.call();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						MyResult = thread1.call();
						ObservableList<String> MyList;
						MyList = return_list_of_room_numbers(MyResult);
						set_rooms_numbers(MyList);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});  
	        HoursBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					RoomsNumberBox.setDisable(false);
					PrepareStatementToGetRooms thread1 = new PrepareStatementToGetRooms(BuildingTable[newValue.intValue()]);
					ResultSet MyResult;
					try {
						MyResult = thread1.call();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						MyResult = thread1.call();
						ObservableList<String> MyList;
						MyList = return_list_of_room_numbers(MyResult);
						set_rooms_numbers(MyList);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}); 
	        
	        DateBox.setDayCellFactory(dayCellFactory);
	        RoomsNumberBox.setValue(null);
	        BuildingBox.setValue(null);
	        HoursBox.setValue(null);
			HoursBox.setItems(HoursList);
			BuildingBox.setItems(BuildingList);

	}
	
	
	private boolean check_if_fields_are_not_null() {
		return (HoursBox.getValue()==null || 
				BuildingBox.getValue()==null || DateBox.getValue()==null) ? false : true;
	}
	
	private boolean check_if_fields_are_not_null1() {
		
		return (HoursBox.getValue()==null || 
				BuildingBox.getValue()==null || DateBox.getValue()==null 
				|| RoomsNumberBox.getValue()==null) ? false : true;
	}
	
	@FXML
	javafx.scene.control.Label LabelField;
	@FXML
	Button OkButton;
	
	private void say_that_fields_are_empty () {
		MyPane.setVisible(true);
		LabelField.setText("Uzupełnij wszystkie pola!");
		OkButton.setVisible(true);
		AddButton.setDisable(true);
		UndoButton.setDisable(true);
		RoomsNumberBox.setVisible(false);
	}
	
	@FXML
	private void say_ok () {
		MyPane.setVisible(false);
		OkButton.setVisible(false);
		AddButton.setDisable(false);
		UndoButton.setDisable(false);
		RoomsNumberBox.setVisible(true);
	}

	
	private class PrepareStatementToGetRooms implements Callable <ResultSet>{
	String BuildingBox;
	private ResultSet prepare_statement_to_get_rooms () throws SQLException
	{
		Connection NewConnection = Main.getConnection();
		PreparedStatement NewPreparedStatement = (PreparedStatement) NewConnection
				.prepareStatement("select Number from ROOMS where Building = ? "
						+ "and Number not in (select Room from RESERVATIONS where "
						+ "Date = ? and Hour = ?)");
		NewPreparedStatement.setString(1, BuildingBox);
		NewPreparedStatement.setString(2, DateBox.getValue().toString());
		NewPreparedStatement.setString(3, HoursBox.getValue());
		ResultSet NewResult = NewPreparedStatement.executeQuery();
		return NewResult;
		
	}
	private PrepareStatementToGetRooms(String b) 
	{
		this.BuildingBox=b; 
	}
	@Override
	public ResultSet call() throws Exception {
		// TODO Auto-generated method stub
		return prepare_statement_to_get_rooms();
	}
	}
	
	private ObservableList<String> return_list_of_room_numbers (ResultSet NewResult) throws SQLException{
		
		ObservableList<String> ListOfRooms = FXCollections.observableArrayList();
		
		while (NewResult.next()) {
			ListOfRooms.add(NewResult.getString("Number"));
		}
		return ListOfRooms;
	}
	
	@FXML
	private void set_rooms_numbers (ObservableList<String> ListOfRooms) {
		RoomsNumberBox.setItems(ListOfRooms);
	}
	
	public class WhatID implements Callable {

		private String return_id() throws SQLException {
			Connection NewConnection = Main.getConnection();
			PreparedStatement MyStatement2 = (PreparedStatement) NewConnection
					.prepareStatement("select COUNT(*) FROM RESERVATIONS");
			ResultSet MyResult2 = MyStatement2.executeQuery();
			MyResult2.next();
			int isEmpty = MyResult2.getInt(1);
			if(isEmpty==0) { return Integer.toString(1);}
			PreparedStatement MyStatement = (PreparedStatement) NewConnection
					.prepareStatement("select id from RESERVATIONS ORDER BY ID DESC LIMIT 1");
			ResultSet MyResult = MyStatement.executeQuery();
			MyResult.next();

			int result = MyResult.getInt(1);
			return Integer.toString(result + 1);
		}
		
		@Override
		public Object call() throws Exception {
			// TODO Auto-generated method stub
			return return_id();
		}
		
	}
	private class PrepareStatementToAddReservation implements Runnable {
	private void prepare_statement_to_add_reservation() throws Exception {
		Connection NewConnection = Main.getConnection();
		WhatID thread1 = new WhatID();
		String id = (String) thread1.call();
		PreparedStatement NewPreparedStatement = (PreparedStatement) NewConnection
				.prepareStatement("insert into RESERVATIONS value (?,?,?,?,'no','no',?,?,'single','no','no','no','no','Admin','yes');");
		NewPreparedStatement.setString(1, id);
		NewPreparedStatement.setString(2, DateBox.getValue().toString());
		NewPreparedStatement.setString(3, HoursBox.getValue());
		NewPreparedStatement.setString(4, DateBox.getValue().getDayOfWeek().toString());
		NewPreparedStatement.setString(5, RoomsNumberBox.getValue());
		NewPreparedStatement.setString(6, BuildingBox.getValue());

		NewPreparedStatement.execute();
		//NewConnection.close();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			prepare_statement_to_add_reservation();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	@FXML
	private void propose_new_reservation() {
		MyPane.setVisible(true);
		YesButton.setVisible(true);
		NoButton.setVisible(true);
		LabelField.setText("Czy chcesz dodać kolejną rezerwację?");
		AddButton.setDisable(true);
		UndoButton.setDisable(true);
		RoomsNumberBox.setVisible(false);
	}
	
	
    @FXML
    private void click_add_button() throws SQLException {
    	if (check_if_fields_are_not_null1()) {   
  
    		PrepareStatementToAddReservation thread2 = new PrepareStatementToAddReservation();
    		thread2.run();
    		propose_new_reservation();
    		
    	}
    	
    	else
    	{
    		say_that_fields_are_empty();
    	}
    }
    
    @FXML
    private void click_on_yes_button () {
    	MyPane.setVisible(false);
    	YesButton.setVisible(false);
    	NoButton.setVisible(false);
    	DateBox.setValue(null);
    	BuildingBox.setValue(null);
    	RoomsNumberBox.setValue(null);
    	AddButton.setDisable(false);
		UndoButton.setDisable(false);
		RoomsNumberBox.setVisible(true);

    	initialize();
    }
    
    private void close() throws IOException{
   		Parent login_panel_page = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
        Scene login_panel_scene = new Scene(login_panel_page);
        Stage app_stage = (Stage) AddButton.getScene().getWindow();
        app_stage.setTitle("Panel Administratora");
        app_stage.setScene(login_panel_scene);
        app_stage.centerOnScreen();
        app_stage.show();
    }
    
    @FXML
    private void click_on_no_button () throws IOException {
    	close();
    }
    
    @FXML
    Button UndoButton;
    
	@FXML
	private void click_undo() throws IOException {
		close();
	}
	public class CheckReservation implements Callable {
		
		private int select_count() throws SQLException {
			    Connection con = Main.getConnection();
			    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("SELECT COUNT(*) FROM RESERVATIONS where "
						+ "Date=? and Hour=? and Building=? and Room=?");
			    MyStatement.setString(1, DateBox.getValue().toString());
			    MyStatement.setString(2, HoursBox.getValue());
			    MyStatement.setString(3, BuildingBox.getValue());
			    MyStatement.setString(4, RoomsNumberBox.getValue());
				ResultSet Result = MyStatement.executeQuery();
				Result.next();
				return Result.getInt(1);
		}
			

		@Override
		public Object call() throws SQLException {
			return select_count();
		}
		}
}