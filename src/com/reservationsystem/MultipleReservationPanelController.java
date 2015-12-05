package com.reservationsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.mysql.jdbc.PreparedStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class MultipleReservationPanelController {
	
	ObservableList<String> DayOfTheWeekList = FXCollections
	.observableArrayList("Poniedziałek","Wtorek","Środa","Czwartek","Piątek","Sobota","Niedziela");
	@FXML
	private ChoiceBox <String> DayOfTheWeekBox;	
	ObservableList<String> FrequencyList = FXCollections
			.observableArrayList("Tydzień parzysty","Tydzień nieparzysty","Co tydzień");
	@FXML
	private ChoiceBox <String> FrequencyBox;
	ObservableList<String> BuildingList = FXCollections
			.observableArrayList("CW","BM","WE","Mch","Polanka");	
	@FXML
	private ChoiceBox <String> BuildingBox;	
	ObservableList<String> YearList = FXCollections
			.observableArrayList("1I","2I","3I","4I","1M","2M");	
	@FXML
	private ChoiceBox <String> YearBox;
	ObservableList<String> HoursList = FXCollections
			.observableArrayList("8:00-9:30","9:45-11:15","11:45-13:15","13:30-15:00","15:15-16:45","17:00-18:30","18:45-20:00");
	@FXML
	private ChoiceBox <String> HoursBox;
	@FXML
	private DatePicker DateBox;
	@FXML
	private TextField LecturerBox;
	@FXML
	private TextField SubjectBox;
	@FXML
	private javafx.scene.control.Button AddButton;
	@FXML
	private javafx.scene.control.Button AddMoreButton;
	
	
	@FXML
	public void initialize ()
	{
		DayOfTheWeekBox.setItems(DayOfTheWeekList);
		FrequencyBox.setItems(FrequencyList);
		BuildingBox.setItems(BuildingList);
		YearBox.setItems(YearList);
		HoursBox.setItems(HoursList);
	}
	
	@FXML
	private ChoiceBox <String> RoomsNumberBox;
	@FXML
	private ChoiceBox <String> GroupBox;
	
	@FXML 
	private ResultSet return_result_set_of_select_number_from_rooms (String BuildingBoxStatus) throws SQLException{
		
		Connection NewConnection = Main.getConnection();
		PreparedStatement NewPreparedStatement = (PreparedStatement) NewConnection.prepareStatement("select Number from ROOMS where Building= ? ");
		NewPreparedStatement.setString(1, BuildingBoxStatus);
		ResultSet NewResult = NewPreparedStatement.executeQuery();
		return NewResult;
	}
	
	@FXML
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
	
	@FXML
	private void click_on_building_box() throws SQLException {
		String BuildingBoxStatus = BuildingBox.getValue() ;
		
		if (BuildingBoxStatus!=null){
			
			ResultSet NewResult = return_result_set_of_select_number_from_rooms(BuildingBoxStatus);		
			ObservableList<String> ListOfRooms = return_list_of_room_numbers(NewResult);
			set_rooms_numbers(ListOfRooms);
		}
   	}
	
	@FXML
	private ResultSet return_result_set_of_select_group_from_years (String YearBoxStatus) throws SQLException{
		
		Connection NewConnection = Main.getConnection();
		
		PreparedStatement NewPreparedStatement = (PreparedStatement) NewConnection.prepareStatement("select * from 1I");
		
		switch(YearBoxStatus){
		case "1I" : break;
		case "2I" : NewPreparedStatement = (PreparedStatement) NewConnection.prepareStatement("select * from 2I");break;
		case "3I" : NewPreparedStatement = (PreparedStatement) NewConnection.prepareStatement("select * from 3I");break;
		case "4I" : NewPreparedStatement = (PreparedStatement) NewConnection.prepareStatement("select * from 4I");break;
		case "1M" : NewPreparedStatement = (PreparedStatement) NewConnection.prepareStatement("select * from 1M");break;
		case "2M" : NewPreparedStatement = (PreparedStatement) NewConnection.prepareStatement("select * from 2M");break;
		}
		
		ResultSet NewResult = NewPreparedStatement.executeQuery();
	
		return NewResult;
	}
	
	@FXML
	private ObservableList<String> return_list_of_group(ResultSet NewResult) throws SQLException{
		
		ObservableList<String> ListOfGroup = FXCollections.observableArrayList();
		
		while (NewResult.next()) {
			ListOfGroup.add(NewResult.getString("Name_of_group"));
		}
		
		return ListOfGroup;
	}

	
	@FXML
	private void set_groups (ObservableList<String> ListOfGroup) {
		GroupBox.setItems(ListOfGroup);
	}
	
	
	@FXML
	private void click_on_year_box() throws SQLException {
		String YearBoxStatus = YearBox.getValue() ;

		if (YearBoxStatus!=null){
			
			ResultSet NewResult = return_result_set_of_select_group_from_years(YearBoxStatus);		
			ObservableList<String> ListOfGroup = return_list_of_group(NewResult);
			set_groups(ListOfGroup);
		}
   	}

	private boolean check_if_fields_are_not_null() {
		
		return (HoursBox.getValue()==null || FrequencyBox.getValue()==null || RoomsNumberBox.getValue()==null || 
				BuildingBox.getValue()==null || SubjectBox.getText()=="" || LecturerBox.getText()==null ||
				YearBox.getValue()==null || GroupBox.getValue()==null || DateBox.getValue()==null) ? false : true;
	}
	
	private PreparedStatement set_sql_query_insert_to_reservation () throws SQLException{
		String HoursBoxValue = HoursBox.getValue();
		String FrequencyBoxValue = FrequencyBox.getValue();
		String EvenValue = (FrequencyBoxValue=="Tydzień parzysty") ? "yes" 
				: ((FrequencyBoxValue=="Tydzień nieparzysty") ? "no" : "yes");
		String OddValue = (FrequencyBoxValue=="Tydzień nieparzysty") ? "yes" 
				: ((FrequencyBoxValue=="Tydzień parzysty") ? "no" : "yes");
		String RoomsNumberBoxValue = RoomsNumberBox.getValue();
		String BuildingBoxValue = BuildingBox.getValue();
		String SubjectBoxValue = SubjectBox.getText();
		String LecturerBoxValue = LecturerBox.getText();
		String YearBoxValue = YearBox.getValue();
		String GroupBoxValue = GroupBox.getValue();

		LocalDate DateBoxValue = DateBox.getValue();
		//Date Mydate = Date.valueOf(DateBoxValue);
		
		Connection NewConnection = Main.getConnection();
		PreparedStatement NewPreparedStatement = (PreparedStatement) NewConnection.prepareStatement("insert into NEW_RESERVATIONS_ADMIN value (?,?,?,?,?,?,'multiple',?,?,?,?)");
		NewPreparedStatement.setString(1,DateBoxValue.toString());
		NewPreparedStatement.setString(2,HoursBoxValue);
		NewPreparedStatement.setString(3,EvenValue);
		NewPreparedStatement.setString(4,OddValue);
		NewPreparedStatement.setString(5,RoomsNumberBoxValue);
		NewPreparedStatement.setString(6,BuildingBoxValue);
		NewPreparedStatement.setString(7,SubjectBoxValue);
		NewPreparedStatement.setString(8,LecturerBoxValue);
		NewPreparedStatement.setString(9,YearBoxValue);
		NewPreparedStatement.setString(10,GroupBoxValue);
		return NewPreparedStatement;
	}
	
	private void add_reservation_to_admin_table(PreparedStatement SomeStatement) throws SQLException{
		SomeStatement.execute();
	}
	
	@FXML
	javafx.scene.control.Label LabelField;
	
	private void say_that_fields_are_empty () {
		LabelField.setVisible(true);
		LabelField.setText("Uzupełnij wszystkie pola!");
	}
	
	private void say_that_fields_are_ok () {
		LabelField.setVisible(false);
		LabelField.setText(null);
	}
	
	public void click_on_add_buton() throws SQLException{
		
		if (check_if_fields_are_not_null()) {
		say_that_fields_are_ok();
	
		add_reservation_to_admin_table(set_sql_query_insert_to_reservation());
		AdminMultipleReservationPanelController.click_to_add_button_in_multiple_reservation_panel();
		
		}
		else {
			say_that_fields_are_empty();
		}
	}

	}