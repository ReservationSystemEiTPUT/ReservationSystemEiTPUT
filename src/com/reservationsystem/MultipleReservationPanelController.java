package com.reservationsystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.concurrent.Callable;

import com.mysql.jdbc.PreparedStatement;
import com.reservationsystem.UserReservationPanelContoller.sizeChanged;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MultipleReservationPanelController {
	
	ObservableList<String> FrequencyList = FXCollections
			.observableArrayList("Tydzień parzysty","Tydzień nieparzysty","Co tydzień");
	@FXML
	private ChoiceBox <String> FrequencyBox;
	ObservableList<String> BuildingList = FXCollections
			.observableArrayList("CW","BM","WE","Mch","Polanka");
	String TableBuilding[] = {"CW","BM","WE","Mch","Polanka"};
	@FXML
	private ChoiceBox <String> BuildingBox;	
	ObservableList<String> YearList = FXCollections
			.observableArrayList("1INZ","2INZ","3INZ","4INZ","1MGR","2MGR");	
	String TableYear [] = {"1INZ","2INZ","3INZ","4INZ","1MGR","2MGR"};
	@FXML
	private ChoiceBox <String> YearBox;
	ObservableList<String> HoursList = FXCollections
			.observableArrayList("8:00-9:30","9:45-11:15","11:45-13:15","13:30-15:00","15:15-16:45","17:00-18:30","18:45-20:00");
	@FXML
	private ChoiceBox <String> HoursBox;
	ObservableList<String> HoursList2 = FXCollections
			.observableArrayList("8:00-11:15","9:45-13:15","11:45-15:00","13:30-16:45","15:15-18:30","17:00-20:00");
	@FXML
	private ChoiceBox <String> HoursBox2;
	@FXML
	private DatePicker DateBox;
	@FXML
	private TextField LecturerBox;
	@FXML
	private TextField SubjectBox;
	@FXML
	private javafx.scene.control.Button AddButton;
	@FXML
	javafx.scene.control.Button CheckButton;
	@FXML
	javafx.scene.control.Button UndoButton;
	@FXML
	Pane MyPane;
	@FXML
	javafx.scene.control.Button YesButton;
	@FXML
	javafx.scene.control.Button NoButton;	
	@FXML
	javafx.scene.control.Button OkButton;	
	
	@FXML
	public void initialize ()
	{
		FrequencyBox.setItems(FrequencyList);
		BuildingBox.setItems(BuildingList);
		YearBox.setItems(YearList);
		HoursBox.setItems(HoursList);
		HoursBox2.setItems(HoursList2);
		HoursBox2.setDisable(true);
		DateBox.setDisable(true);
		GroupBox.setDisable(true);
		RoomsNumberBox.setDisable(true);
		AllYear.setDisable(true);
		MyPane.setVisible(false);
		YesButton.setVisible(false);
		NoButton.setVisible(false);
		OkButton.setVisible(false);
		OkButton1.setVisible(false);
		AddButton.setDisable(true);
		AllYear.setSelected(false);
		DoubleReservation.setSelected(false);
		MyPane.setStyle("-fx-background-color: white;");
		
		AllYear.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue ov,
                Boolean old_val, Boolean new_val) {
            	AddButton.setDisable(true);
            }
        });
		
		DoubleReservation.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue ov,
                Boolean old_val, Boolean new_val) {
            	AddButton.setDisable(true);
            }
        });
		
        BuildingBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				RoomsNumberBox.setDisable(false);
				RoomsValue thread1 = new RoomsValue(TableBuilding[newValue.intValue()]);
				try {
					AddButton.setDisable(true);
					thread1.run();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}); 
        YearBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				GroupBox.setDisable(false);
				AllYear.setDisable(false);
				GroupValue thread1 = new GroupValue(TableYear[newValue.intValue()]);
				try {
					thread1.run();
					AddButton.setDisable(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}); 
        
        FrequencyBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				pick_frequency(newValue.intValue());
				AddButton.setDisable(true);
			}
		}); 
        
        Count.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\d*") || (Integer.parseInt(Count.getText())<20)) {
                    int value = Integer.parseInt(newValue);
    				AddButton.setDisable(true);

                } else {
                    Count.setText(oldValue);
                }
            }
        });
   /*     SubjectBox.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\w*")) {
                    int value = Integer.parseInt(newValue);
    				AddButton.setDisable(true);
                } else {
                    SubjectBox.setText(oldValue);
                }
            }
        });
        LecturerBox.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\w*")) {
                    int value = Integer.parseInt(newValue);
    				AddButton.setDisable(true);
                } else {
                    LecturerBox.setText(oldValue);
                }
            }
        });*/
        SubjectBox.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
          
    				AddButton.setDisable(true);

            }});
        LecturerBox.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
          
    				AddButton.setDisable(true);

            }});
        GroupBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				AddButton.setDisable(true);
			}
		}); 
        
        RoomsNumberBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				AddButton.setDisable(true);
			}
		}); 
        HoursBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				AddButton.setDisable(true);
			}
		}); 
        HoursBox2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				AddButton.setDisable(true);
			}
		}); 
	}
	
	@FXML
	private void click_on_double_reservation() {
		if(DoubleReservation.isSelected()){
			HoursBox.setDisable(true);
			HoursBox2.setDisable(false);
		}
		else {
			HoursBox2.setDisable(true);
			HoursBox.setDisable(false);	
		}
	}
	
	@FXML
	private void click_on_all_years() {
		if(AllYear.isSelected()){
			GroupBox.setDisable(true);
		}
		else
		{
			GroupBox.setDisable(false);
		}
	}
	
	@FXML
	CheckBox DoubleReservation;
	@FXML
	CheckBox AllYear;
	
	@FXML
	private ChoiceBox <String> RoomsNumberBox;
	@FXML
	private ChoiceBox <String> GroupBox;
	
	private class RoomsValue implements Runnable{

		private RoomsValue(String B){
			this.Building = B;
		}
		
		private String Building;
		private ResultSet give_rooms() throws SQLException {
			Connection NewConnection = Main.getConnection();
			PreparedStatement NewPreparedStatement = (PreparedStatement) NewConnection.prepareStatement("select Number from ROOMS where Building=?");
			NewPreparedStatement.setString(1, Building);
			ResultSet NewResult = NewPreparedStatement.executeQuery();
			return NewResult;
		}
		
		private void set_rooms_numbers (ObservableList<String> ListOfRooms) {
			RoomsNumberBox.setItems(ListOfRooms);
		}
		
		private ObservableList<String> return_list_of_room_numbers (ResultSet NewResult) throws SQLException{
			
			ObservableList<String> ListOfRooms = FXCollections.observableArrayList();
			
			while (NewResult.next()) {
				ListOfRooms.add(NewResult.getString("Number"));
			}
			
			return ListOfRooms;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			ResultSet Result;
			try {
				Result = give_rooms();
				set_rooms_numbers(return_list_of_room_numbers(Result));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private class GroupValue implements Runnable{

		private GroupValue(String Y){
			this.Year = Y;
		}
		
		private String Year;
		private ResultSet give_group () throws SQLException{		
			Connection NewConnection = Main.getConnection();		
			PreparedStatement NewPreparedStatement = (PreparedStatement) NewConnection.prepareStatement("select * from GROUPS WHERE Year=?");
			NewPreparedStatement.setString(1, Year);
			ResultSet NewResult = NewPreparedStatement.executeQuery();	
			return NewResult;
		}
		
		private void set_groups (ObservableList<String> ListOfGroup) {
			GroupBox.setItems(ListOfGroup);
		}
		
		private ObservableList<String> return_list_of_group(ResultSet NewResult) throws SQLException{
			
			ObservableList<String> ListOfGroup = FXCollections.observableArrayList();
			
			while (NewResult.next()) {
				ListOfGroup.add(NewResult.getString("Number_of_group"));
			}
			
			return ListOfGroup;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			ResultSet Result;
			try {
				Result = give_group();
				set_groups(return_list_of_group(Result));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void pick_frequency(int Value) {
		DateBox.setDisable(false);
		DateBox.setValue(LocalDate.now().plusDays(1));
		LocalDate myDate1 = LocalDate.parse("2015-12-20");
		LocalDate myDate2 = LocalDate.parse("2016-01-11");
		LocalDate myDate3 = LocalDate.parse("2016-01-17");
		LocalDate myDate4 = LocalDate.parse("2016-01-25");
		LocalDate myDate5 = LocalDate.parse("2016-02-28");
		LocalDate myDate6 = LocalDate.parse("2016-03-07");
		LocalDate myDate7 = LocalDate.parse("2016-03-13");
		LocalDate myDate8 = LocalDate.parse("2016-03-21");
		LocalDate myDate9 = LocalDate.parse("2016-03-27");
		LocalDate myDate10 = LocalDate.parse("2016-04-04");
		LocalDate myDate11 = LocalDate.parse("2016-04-10");
		LocalDate myDate12 = LocalDate.parse("2016-04-18");
		LocalDate myDate13 = LocalDate.parse("2016-04-24");
		LocalDate myDate14 = LocalDate.parse("2016-05-02");
		LocalDate myDate15 = LocalDate.parse("2016-05-08");
		LocalDate myDate16 = LocalDate.parse("2016-05-16");
		LocalDate myDate17 = LocalDate.parse("2016-05-22");	
		LocalDate myDate18 = LocalDate.parse("2016-05-30");
		LocalDate myDate19 = LocalDate.parse("2016-06-05");
		LocalDate myDate20 = LocalDate.parse("2016-06-13");
		LocalDate free1 = LocalDate.parse("2016-03-25");
		LocalDate free2 = LocalDate.parse("2016-03-29");
		LocalDate free3 = LocalDate.parse("2016-05-02");
		LocalDate free4 = LocalDate.parse("2016-05-27");
		LocalDate free5 = LocalDate.parse("2016-05-03");
		LocalDate free6 = LocalDate.parse("2016-05-26");
		LocalDate free7 = LocalDate.parse("2016-05-01");
		LocalDate block = LocalDate.parse("2016-06-17");
		final Callback<DatePicker, DateCell> dayCellFactory = 
	            new Callback<DatePicker, DateCell>() {
	                @Override
	                public DateCell call(final DatePicker datePicker) {
	                    return new DateCell() {
	                        @Override
	                        public void updateItem(LocalDate item, boolean empty) {
	                            super.updateItem(item, empty);
	                           if(Value==2) {
	                            if (item.isBefore(DateBox.getValue()) || item.isEqual(free7) || item.isEqual(free6) || item.isEqual(free5)
	                            		|| item.isEqual(free4) || item.isEqual(free3)|| item.isEqual(free2) || item.isEqual(free1) || 
	                            		(item.isAfter(free1) && item.isBefore(free2)) || item.isAfter(block)){ 
	                                    setDisable(true);
	                                    setStyle("-fx-background-color: #98FFBA;");
	 	                        	   if (item.isBefore(myDate5)) {
		                        		   setDisable(false);
		                        		   setStyle("-fx-background-color: white;");
		                        	   }
	                            }   
	                            }
	                           else if (Value==0){
	                        	   if (item.isBefore(DateBox.getValue()) || item.isEqual(free7) || item.isEqual(free6) || item.isEqual(free5)
		                            		|| item.isEqual(free4) || item.isEqual(free3)|| item.isEqual(free2) || item.isEqual(free1) || 
		                            		(item.isAfter(free1) && item.isBefore(free2)) || item.isAfter(block) || (item.isAfter(myDate1) && item.isBefore(myDate2))
		                            		|| (item.isAfter(myDate3) && item.isBefore(myDate4)) || (item.isAfter(myDate5) && item.isBefore(myDate6))
		                            		|| (item.isAfter(myDate7) && item.isBefore(myDate8)) || (item.isAfter(myDate9) && item.isBefore(myDate10))
		                            		|| (item.isAfter(myDate11) && item.isBefore(myDate12)) || (item.isAfter(myDate13) && item.isBefore(myDate14))
		                            		|| (item.isAfter(myDate15) && item.isBefore(myDate16)) || (item.isAfter(myDate17) && item.isBefore(myDate18))
		                            		|| (item.isAfter(myDate19) && item.isBefore(myDate20))){
	                                    setDisable(true);
	                                    setStyle("-fx-background-color: #98FFBA;");
	 	                        	   if (item.isBefore(myDate5)) {
		                        		   setDisable(false);
		                        		   setStyle("-fx-background-color: white;");
		                        	   }
	                            }
	                           }
	                           else {
	                        	   if (item.isBefore(DateBox.getValue()) || item.isEqual(free7) || item.isEqual(free6) || item.isEqual(free5)
		                            		|| item.isEqual(free4) || item.isEqual(free3)|| item.isEqual(free2) || item.isEqual(free1) || 
		                            		(item.isAfter(free1) && item.isBefore(free2)) || item.isAfter(block) || !((item.isAfter(myDate1) && item.isBefore(myDate2))
		                            		|| (item.isAfter(myDate3) && item.isBefore(myDate4)) || (item.isAfter(myDate5) && item.isBefore(myDate6))
		                            		|| (item.isAfter(myDate7) && item.isBefore(myDate8)) || (item.isAfter(myDate9) && item.isBefore(myDate10))
		                            		|| (item.isAfter(myDate11) && item.isBefore(myDate12)) || (item.isAfter(myDate13) && item.isBefore(myDate14))
		                            		|| (item.isAfter(myDate15) && item.isBefore(myDate16)) || (item.isAfter(myDate17) && item.isBefore(myDate18))
		                            		|| (item.isAfter(myDate19) && item.isBefore(myDate20))) ) {
	                                    setDisable(true);
	                                    setStyle("-fx-background-color: #98FFBA;");
	                            }
	                        	   if (item.isBefore(myDate5)) {
	                        		   setDisable(false);
	                        		   setStyle("-fx-background-color: white;");
	                        	   }
	                           }
	                    }
	                };
	            }
	        };
	        DateBox.setDayCellFactory(dayCellFactory);	
		}
	

	private boolean check_if_fields_are_not_null() {
		
		return (((DoubleReservation.isSelected())?HoursBox2.getValue()==null :HoursBox.getValue()==null) || FrequencyBox.getValue()==null || RoomsNumberBox.getValue()==null || 
				BuildingBox.getValue()==null || SubjectBox.getText()=="" || LecturerBox.getText()==null ||
				YearBox.getValue()==null ||((AllYear.isSelected())? YearBox.getValue()==null :GroupBox.getValue()==null )|| DateBox.getValue()==null) ? false : true;
	}
	
	
	@FXML
	javafx.scene.control.Label LabelField;
	
	private void say_that_fields_are_empty () {
		MyPane.setVisible(true);
		OkButton.setVisible(true);
		LabelField.setText("Uzupełnij wszystkie pola, aby dodać rezerwację!");
		CheckButton.setDisable(true);
		UndoButton.setDisable(true);
	}
	
	@FXML
	private void say_ok() {
		CheckButton.setDisable(false);
		UndoButton.setDisable(false);
		MyPane.setVisible(false);
		OkButton.setVisible(false);
	}
	
	private void ask_what_next() {
		MyPane.setVisible(true);
		YesButton.setVisible(true);
		NoButton.setVisible(true);
		LabelField.setText("Czy chcesz dodać kolejną rezerwację?");
		AddButton.setDisable(true);
		CheckButton.setDisable(true);
		UndoButton.setDisable(true);
	}
	
	@FXML
	private void click_on_yes() {
		CheckButton.setDisable(false);
		UndoButton.setDisable(false);
		MyPane.setVisible(false);
		OkButton.setVisible(false);
		HoursBox2.setDisable(true);
		DateBox.setDisable(true);
		DateBox.setValue(null);
		FrequencyBox.setValue(null);
		HoursBox.setValue(null);
		HoursBox2.setValue(null);
		LecturerBox.setText(null);
		SubjectBox.setText(null);
		YearBox.setValue(null);
		GroupBox.setValue(null);
		RoomsNumberBox.setValue(null);
		BuildingBox.setValue(null);
		Count.setText(null);
		initialize();
	}
	
	private void close() throws IOException {
    	Parent login_panel_page = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
        Scene login_panel_scene = new Scene(login_panel_page);
        Stage app_stage = (Stage) AddButton.getScene().getWindow();
        app_stage.setTitle("Panel Administratora");
        app_stage.setScene(login_panel_scene);
        app_stage.centerOnScreen();
        app_stage.show();
	}
	
	@FXML
	private void say_no() throws IOException {
		close();
	}
	
	@FXML
	private void click_undo() throws IOException {
		close();
	}
	
	
	@FXML
	TextField Count;
	
	private String [] hours_to_check(String Hour) {
		String [] HoursValues = new String[2];
		switch(Hour){
		case "8:00-11:15" : {HoursValues[0] = "8:00-9:30"; HoursValues[1] = "9:45-11:15"; break;}
		case "9:45-13:15" : {HoursValues[0] = "9:45-11:15"; HoursValues[1] = "11:45-13:15"; break;}
		case "11:45-15:00" : {HoursValues[0] = "11:45-13:15"; HoursValues[1] = "13:30-15:00"; break;}
		case "13:30-16:45" : {HoursValues[0] = "13:30-15:00"; HoursValues[1] = "15:15-16:45"; break;}
		case "15:15-18:30" : {HoursValues[0] = "15:15-16:45"; HoursValues[1] = "17:00-18:30"; break;}
		case "17:00-20:00" : {HoursValues[0] = "17:00-18:30"; HoursValues[1] = "18:45-20:00"; break;}		
		}

		return HoursValues;
	}
	
	private class CheckReservation implements Callable {
	
	private int is_any_reservation() throws SQLException {
		String HourValue,HourValue2 = null;
		boolean isSelected = DoubleReservation.isSelected();
		if(isSelected)
		{
			String Hours[] = hours_to_check(HoursBox2.getValue());
			HourValue = Hours[0];
			HourValue2 = Hours[1];
		}
		else {HourValue = HoursBox.getValue();}
		LocalDate DateValue = DateBox.getValue();
		String BuildingValue = BuildingBox.getValue();
		String RoomValue = RoomsNumberBox.getValue();
		Connection con = Main.getConnection();
		for(int i = 0; i < (Integer.parseInt(Count.getText()))*2;i++) {
			String DateValue1 = DateValue.plusWeeks(i).toString();
		    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("SELECT COUNT(*) FROM RESERVATIONS where "
					+ "Date=? and Hour=? and Building=? and Room=? and Type_of_reservation='multiple'");
		    MyStatement.setString(1, DateValue1);
		    MyStatement.setString(2, HourValue);
		    MyStatement.setString(3, BuildingValue);
		    MyStatement.setString(4, RoomValue);
			ResultSet Result = MyStatement.executeQuery();
			Result.next();
			if (Result.getInt(1)==1){return 1;}
			
			if(isSelected){
				PreparedStatement MyStatement1 = (PreparedStatement) con.prepareStatement("SELECT COUNT(*) FROM RESERVATIONS where "
						+ "Date=? and Hour=? and Building=? and Room=? and Type_of_reservation='multiple'");
			    MyStatement1.setString(1, DateValue1);
			    MyStatement1.setString(2, HourValue2);
			    MyStatement1.setString(3, BuildingValue);
			    MyStatement1.setString(4, RoomValue);
				Result = MyStatement.executeQuery();
				Result.next();
				if (Result.getInt(1)==1){return 1;}
			}
		}
		return 0;
	}
	
	@Override
	public Object call() throws SQLException {
		// TODO Auto-generated method stub
		return is_any_reservation();
		
	}	
}
	
	@FXML
	javafx.scene.control.Button OkButton1;
	
	@FXML
	private void say_about_check() throws SQLException {
		if (check_if_fields_are_not_null()) {
		CheckReservation thread1 = new CheckReservation();
		int Result = (int) thread1.call();
		MyPane.setVisible(true);
		OkButton1.setVisible(true);
		AddButton.setDisable(true);
		CheckButton.setDisable(true);
		UndoButton.setDisable(true);
		if(Result==0) {
			LabelField.setText("Nie istnieje jeszcze taka rezerwacja");
			AddButton.setDisable(false);
		}
		else {
			LabelField.setText("Istnieje rezerwacja o danych parametrach!");
			AddButton.setDisable(true);
		}
		}
		else
		{
			say_that_fields_are_empty();
		}
	}
	
	@FXML
	private void say_ok1(){
		MyPane.setVisible(false);
		OkButton1.setVisible(false);
		CheckButton.setDisable(false);
		UndoButton.setDisable(false);
	}
	
	public class WhatID implements Callable {

		private String return_id() throws SQLException {
			Connection NewConnection = Main.getConnection();
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
	
	private class AddReservation implements Runnable {
		
		private void add_reservation() throws Exception {
			String HoursBoxValue,HoursBoxValue2 = null;
			boolean isSelected = DoubleReservation.isSelected();
			if(isSelected)
			{
				String [] Hours = new String[2];
				Hours = hours_to_check(HoursBox2.getValue());
				HoursBoxValue = Hours[0];
				HoursBoxValue2 = Hours[1];
			}
			else {HoursBoxValue = HoursBox.getValue();}
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
			String GroupBoxValue = (AllYear.isSelected()) ? "All" : GroupBox.getValue();
			LocalDate DateBoxValue = DateBox.getValue();
			DayOfWeek DayValue = DateBoxValue.getDayOfWeek();
			String Day = DayValue.toString();
			Connection con = Main.getConnection();
			if(EvenValue=="yes" && OddValue=="yes") {
			for(int i = 0; i < Integer.parseInt(Count.getText());i++) {
				String DateValue1 = DateBoxValue.plusWeeks(i).toString();
				PreparedStatement MyStatement7 = (PreparedStatement) con.prepareStatement("INSERT INTO"
						+ " DELETE_RESERVATIONS (SELECT * FROM RESERVATIONS WHERE Date=? and Hour=? "
						+ "and Building=? and Room=?  and User!='Admin')");
				MyStatement7.setString(1, DateValue1);
				MyStatement7.setString(2, HoursBoxValue);
				MyStatement7.setString(3, BuildingBoxValue);
				MyStatement7.setString(4, RoomsNumberBoxValue);
				MyStatement7.executeUpdate();
				MyStatement7.executeUpdate();
				PreparedStatement MyStatement4 = (PreparedStatement) con.prepareStatement("update DELETE_RESERVATIONS SET SMS='no' "
						+ "where SMS='yes'");
				MyStatement4.executeUpdate();
				PreparedStatement MyStatement1 = (PreparedStatement) con.prepareStatement("delete from "
				    		+ "NEW_RESERVATIONS where Date=? and Hour=? and Building=? and Room=?");
				MyStatement1.setString(1, DateValue1);
				MyStatement1.setString(2, HoursBoxValue);
		        MyStatement1.setString(3, BuildingBoxValue);
			    MyStatement1.setString(4, RoomsNumberBoxValue);				    
			    WhatID thread1 = new WhatID();
			    String id = (String) thread1.call();
			    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("insert into "
			    		+ "RESERVATIONS value (?,?,?,?,?,?,?,?,'multiple',?,?,?,?,'Admin','no');");
			    MyStatement.setString(1, id);
			    MyStatement.setString(2, DateValue1);
			    MyStatement.setString(3, HoursBoxValue);
			    MyStatement.setString(4, Day);
			    MyStatement.setString(5, EvenValue);
			    MyStatement.setString(6, OddValue);
			    MyStatement.setString(7, RoomsNumberBoxValue);
			    MyStatement.setString(8, BuildingBoxValue);
				MyStatement.setString(9,SubjectBoxValue);
				MyStatement.setString(10,LecturerBoxValue);
				MyStatement.setString(11,YearBoxValue);
				MyStatement.setString(12,GroupBoxValue);
				MyStatement.execute();
				if(isSelected)
				{
				PreparedStatement MyStatement5 = (PreparedStatement) con.prepareStatement("INSERT INTO"
							+ " DELETE_RESERVATIONS (SELECT * FROM RESERVATIONS WHERE Date=? and Hour=? "
							+ "and Building=? and Room=?  and User!='Admin')");
				MyStatement5.setString(1, DateValue1);
				MyStatement5.setString(2, HoursBoxValue);
				MyStatement5.setString(3, BuildingBoxValue);
				MyStatement5.setString(4, RoomsNumberBoxValue);
				MyStatement5.executeUpdate();
				MyStatement5.executeUpdate();
				PreparedStatement MyStatement6 = (PreparedStatement) con.prepareStatement("update DELETE_RESERVATIONS SET SMS='no' "
						+ "where SMS='yes'");
				MyStatement6.executeUpdate();
				PreparedStatement MyStatement2 = (PreparedStatement) con.prepareStatement("delete from "
				    		+ "NEW_RESERVATIONS where Date=? and Hour=? and Building=? and Room=?");
				MyStatement2.setString(1, DateValue1);
				MyStatement2.setString(2, HoursBoxValue2);
		        MyStatement2.setString(3, BuildingBoxValue);
			    MyStatement2.setString(4, RoomsNumberBoxValue);				    
				MyStatement2.executeUpdate();
				
				WhatID thread2 = new WhatID();
				id = (String) thread2.call();
				PreparedStatement MyStatement3 = (PreparedStatement) con.prepareStatement("insert into "
				    		+ "RESERVATIONS value (?,?,?,?,?,?,?,?,'multiple',?,?,?,?,'Admin','yes');");
				MyStatement3.setString(1, id);
			    MyStatement3.setString(2, DateValue1);
			    MyStatement3.setString(3, HoursBoxValue2);
			    MyStatement3.setString(4, Day);
			    MyStatement3.setString(5, EvenValue);
			    MyStatement3.setString(6, OddValue);
			    MyStatement3.setString(7, RoomsNumberBoxValue);
			    MyStatement3.setString(8, BuildingBoxValue);
				MyStatement3.setString(9,SubjectBoxValue);
				MyStatement3.setString(10,LecturerBoxValue);
				MyStatement3.setString(11,YearBoxValue);
				MyStatement3.setString(12,GroupBoxValue);
				MyStatement3.execute();
				}}
			}
			else {
			for(int i = 0; i < (Integer.parseInt(Count.getText()))*2;i+=2) {
				String DateValue1 = DateBoxValue.plusWeeks(i).toString();
				PreparedStatement MyStatement1 = (PreparedStatement) con.prepareStatement("delete from "
				    		+ "NEW_RESERVATIONS where Date=? and Hour=? and Building=? and Room=?");
				MyStatement1.setString(1, DateValue1);
				MyStatement1.setString(2, HoursBoxValue);
		        MyStatement1.setString(3, BuildingBoxValue);
			    MyStatement1.setString(4, RoomsNumberBoxValue);				    
				MyStatement1.executeUpdate();
				WhatID thread1 = new WhatID();
				String id = (String) thread1.call();
			    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("insert into RESERVATIONS value (?,?,?,?,?,?,?,?,'multiple',?,?,?,?,'Admin','no');");
			    MyStatement.setString(1, id);
			    MyStatement.setString(2, DateValue1);
			    MyStatement.setString(3, HoursBoxValue);
			    MyStatement.setString(4, Day);
			    MyStatement.setString(5, EvenValue);
			    MyStatement.setString(6, OddValue);
			    MyStatement.setString(7, RoomsNumberBoxValue);
			    MyStatement.setString(8, BuildingBoxValue);
				MyStatement.setString(9,SubjectBoxValue);
				MyStatement.setString(10,LecturerBoxValue);
				MyStatement.setString(11,YearBoxValue);
				MyStatement.setString(12,GroupBoxValue);
				MyStatement.execute();
				if(isSelected)
				{
					PreparedStatement MyStatement2 = (PreparedStatement) con.prepareStatement("delete from "
				    		+ "NEW_RESERVATIONS where Date=? and Hour=? and Building=? and Room=?");
				MyStatement2.setString(1, DateValue1);
				MyStatement2.setString(2, HoursBoxValue2);
		        MyStatement2.setString(3, BuildingBoxValue);
			    MyStatement2.setString(4, RoomsNumberBoxValue);				    
				MyStatement2.executeUpdate();
				WhatID thread2 = new WhatID();
				id = (String) thread2.call();
			    PreparedStatement MyStatement3 = (PreparedStatement) con.prepareStatement("insert into RESERVATIONS value (?,?,?,?,?,?,?,?,'multiple',?,?,?,?,'Admin','no');");
			    MyStatement3.setString(1, id);
			    MyStatement3.setString(2, DateValue1);
			    MyStatement3.setString(3, HoursBoxValue2);
			    MyStatement3.setString(4, Day);
			    MyStatement3.setString(5, EvenValue);
			    MyStatement3.setString(6, OddValue);
			    MyStatement3.setString(7, RoomsNumberBoxValue);
			    MyStatement3.setString(8, BuildingBoxValue);
				MyStatement3.setString(9,SubjectBoxValue);
				MyStatement3.setString(10,LecturerBoxValue);
				MyStatement3.setString(11,YearBoxValue);
				MyStatement3.setString(12,GroupBoxValue);
				MyStatement3.execute();
				}
			}}
			
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				add_reservation();
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
	public void click_on_add_buton() throws SQLException{
		
		if (check_if_fields_are_not_null()) {
			AddReservation thread1 = new AddReservation();
			thread1.run();
			ask_what_next();
		}
		else {
			say_that_fields_are_empty();
		}
	}
	
	}