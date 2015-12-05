package com.reservationsystem;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddNewUsersReservationsPanelController {
	
	@FXML
	javafx.scene.control.ScrollPane MyScroll;
	@FXML
	GridPane NewPane;
	@FXML
	javafx.scene.control.Button AddButton;
	@FXML
	javafx.scene.control.Button DeleteButton;
	
	private int get_count_of_reservations() throws SQLException {
		ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT COUNT(*) FROM NEW_RESERVATIONS");
		Result.next();
		return Result.getInt(1);
	}
	
	private ResultSet get_data_from_new_reservation () throws SQLException {
		ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT * FROM NEW_RESERVATIONS");
		return Result;
	}
	
	
	private String give_data_from_new_reservation(ResultSet Result) throws SQLException {
		
		Result.next();
		String Data = null;
		java.sql.Date Date1 = Result.getDate("Date");
		String Hour = Result.getString("Hour");
		String Building = Result.getString("Building");
		String Room = Result.getString("Room");
		String User = Result.getString("User");
		String Day = Result.getString("Day");
		Data =  Date1 + " " + " " + Day + " " + Hour + " " + Building + " " + Room + " " + User;
		return  Data;
	}
	
	public void initialize () throws SQLException {
		
		MyScroll.setContent(NewPane);
		
		int ISize = get_count_of_reservations();
		CheckBox CheckBoxUserData[] = new CheckBox[ISize];
		ResultSet Result = get_data_from_new_reservation();
		
		for (int i = 0 ; i < ISize; i++){
		String Data = give_data_from_new_reservation(Result);
		CheckBoxUserData[i] = new CheckBox(Data);
		NewPane.add(CheckBoxUserData[i],0, i+2);
		}
	}
	
	private String set_query_to_db(String ReservationArray[]){
		String Query = "insert into RESERVATIONS value ('" + ReservationArray[0] + 
				"','" + ReservationArray[3] + "','" + ReservationArray[2] + 
				"','no','no','" + ReservationArray[5] + "','" + 
				ReservationArray[4] + "','single','no','no','no','no','" 
				+ ReservationArray[6] + "','no');";
		return Query;
	}
	
	private void add_reservations_to_db(String ReservationArray[]) throws SQLException {
		String Query = set_query_to_db(ReservationArray);
		AddNewUsersPanelController.get_result_set_for_my_update(Query);
		delete_reservations_from_db(ReservationArray);
	}
	
	private void delete_reservations_from_db(String ReservationArray[]) throws SQLException {
			String Query = "delete from NEW_RESERVATIONS where (Date='" + ReservationArray[0] + 
					"' and Hour='" + ReservationArray[3] + "' and Building='" + ReservationArray[4] +
					"' and Room='" + ReservationArray[5] + "' and User='" + ReservationArray[6] + "');";
			//System.out.print(Query);
			AddNewUsersPanelController.get_result_set_for_my_update(Query);
			Stage stage = (Stage) AddButton.getScene().getWindow();
			stage.close();
			//AdminPanelController.open_new_window("AddNewUsersReservationsPanel.fxml","Zarzadzanie rezerwacjami cyklicznymi");
			//OpenPanel myPanel = new OpenPanel();
		
	}
	
	public class OpenPanel implements Runnable {

		@Override
		public void run() {
			AdminPanelController.open_new_window("AddNewUsersReservationsPanel.fxml","Zarzadzanie rezerwacjami cyklicznymi");

			
		
		
	}
	}
	
	@FXML
	public void click_on_add_button() throws SQLException {
		
		int ISize = get_count_of_reservations();
		CheckBox Check; 
		for (int i = 2;i < ISize + 2;i++)
		{
			Check = (CheckBox) NewPane.getChildren().get(i);
			if(Check.isSelected()) {
				String ReservationName = Check.getText();
				String ReservationNameArray[] = ReservationName.split(" ", 9);
				add_reservations_to_db(ReservationNameArray);
			}
		}
	}
	
	@FXML
	public void click_on_delete_button() throws SQLException {
		int ISize = get_count_of_reservations();
		CheckBox Check; 
		for (int i = 2;i < ISize + 2;i++)
		{
			Check = (CheckBox) NewPane.getChildren().get(i);
			if(Check.isSelected()) {
				String ReservationName = Check.getText();
				String ReservationNameArray[] = ReservationName.split(" ", 9);
				delete_reservations_from_db(ReservationNameArray);
			}
		}
	}
	
}