package com.reservationsystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

public class AdminMultipleReservationPanelController {

	
	public static void click_to_add_button_in_multiple_reservation_panel ()
	{
		AdminPanelController.open_new_window("AdminMultipleReservationPanel.fxml","Zarz¹dzanie rezerwacjami cyklicznymi");
		
	}
	
	@FXML
	javafx.scene.control.ScrollPane MyScroll;
	@FXML
	GridPane NewPane;
	@FXML
	javafx.scene.control.Button AddButton;
	@FXML
	javafx.scene.control.Button DeleteButton;
	
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
	
		private int get_count_of_reservations() throws SQLException {
			ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT COUNT(*) FROM NEW_RESERVATIONS_ADMIN");
			Result.next();
			return Result.getInt(1);
		}
		
		private ResultSet get_data_from_new_reservation () throws SQLException {
			ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT * FROM NEW_RESERVATIONS_ADMIN");
			return Result;
		}
		
		
		private String give_data_from_new_reservation(ResultSet Result) throws SQLException {
			
			Result.next();
			String Data = null;
			java.sql.Date Date1 = Result.getDate("Date");
			String Hour = Result.getString("Hour");
			String Building = Result.getString("Building");
			String Room = Result.getString("Room");
			String Subject = Result.getString("Subject");
			String Lecturer = Result.getString("Lecturer");
			String Group = Result.getString("Number_of_Group");
			String Year = Result.getString("Year");
			String Frequency = (Result.getString("Even").equals("yes") && Result.getString("Odd").equals("yes")) 
					? "Co tydzień" : (Result.getString("Odd").equals("yes") ? "Tydzień nieparzysty" : "Tydzień parzysty");
			Data =  Hour + " " + Building + " " + Room + " " + Subject + " " + Lecturer + " " + Year + 
					" " + Group + " " + Date1 + " " + Frequency;
			return  Data;
		}
		
		private LocalDate convert_string_to_date (String DataString) {
			LocalDate LD = LocalDate.parse(DataString);
		    return LD;
		}
		
		private String set_query_to_db(String ReservationArray[],LocalDate date1, String AboutFrequency) {
			String Query = "insert into RESERVATIONS value ('" + date1 + 
					"','" + ReservationArray[0] + "','" + date1.getDayOfWeek() + 
					"'," + AboutFrequency +",'" + ReservationArray[2] + "','" + 
					ReservationArray[1] + "','multiple','" + ReservationArray[3] + 
					"','" + ReservationArray[4] + "','" + ReservationArray[5] + 
					"','" + ReservationArray[6] + "','Admin','no')";
			return Query;
		}
		
		private void add_multiple_reservation_to_db(String ReservationArray[]) throws SQLException {
			LocalDate Date1 = convert_string_to_date(ReservationArray[7]);
				if (ReservationArray[8].equals("Tydzień parzysty")) {
					for (int i = 0; i < 8; i++) {
							String Query = set_query_to_db(ReservationArray,Date1,"'yes','no'");
							Date1 = Date1.plusWeeks(2);
							AddNewUsersPanelController.get_result_set_for_my_update(Query);
					}
				}
				if (ReservationArray[8].equals("Tydzień nieparzysty")) {
					for (int i = 0; i < 8; i++) {
						    String Query = set_query_to_db(ReservationArray,Date1,"'no','yes'");
							Date1 = Date1.plusWeeks(2);
							AddNewUsersPanelController.get_result_set_for_my_update(Query);
					}
				}
				 if (ReservationArray[8].equals("Co tydzień")) {
					for (int i = 0; i < 16; i++) {
							String Query = set_query_to_db(ReservationArray,Date1,"'yes','yes'");
							Date1 = Date1.plusWeeks(1);
							AddNewUsersPanelController.get_result_set_for_my_update(Query);
							//System.out.print(Query);
					}
				}
		}
		
		private void add_reservation_to_db(String ReservationArray[]) throws SQLException {
			add_multiple_reservation_to_db(ReservationArray);
			delete_reservation_from_db (ReservationArray);
		}
		
		private void delete_reservation_from_db(String ReservationArray[]) throws SQLException {
			String Query = "delete from NEW_RESERVATIONS_ADMIN where (Hour='" + ReservationArray[0] + 
					"' and Building='" + ReservationArray[1] + "' and Room='" + ReservationArray[2] +
					"' and Subject='" + ReservationArray[3] + "' and Lecturer='" + ReservationArray[4] +
					"' and Number_of_group='" + ReservationArray[6] + "' and Year='" + ReservationArray[5] + "');";
			AddNewUsersPanelController.get_result_set_for_my_update(Query);
			AdminPanelController.open_new_window("AdminMultipleReservationPanel.fxml","Zarządzanie rezerwacjami cyklicznymi");
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
					add_reservation_to_db(ReservationNameArray);
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
				    String ReservationNameArray[] = ReservationName.split(" ", 7);
					delete_reservation_from_db(ReservationNameArray);
				}
			}
		}
		
}