package com.reservationsystem;

import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import com.mysql.jdbc.PreparedStatement;
import com.reservationsystem.CreatingAccountPanelController.databaseAccess;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Group;

public class AddNewUsersReservationPanelController {
	@FXML
    private TableView<DataToTable> MyTable1 = new TableView<DataToTable>();
    private final ObservableList<DataToTable> Data =
        FXCollections.observableArrayList(); 

    @FXML
    TableColumn<DataToTable, String> DateColumn;
    @FXML
    TableColumn<DataToTable, String>  HourColumn;
    @FXML
    TableColumn<DataToTable, String>  BuildingColumn;
    @FXML
    TableColumn<DataToTable, String>  RoomsColumn;
    @FXML
    TableColumn<DataToTable, String>  UserColumn;
    @FXML
    TableColumn<DataToTable, CheckBox> CheckBoxColumn;
    
    public class SelectFromReservation implements Callable<ResultSet> {
    private ResultSet get_data_from_reservation () throws SQLException {
		ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT * FROM NEW_RESERVATIONS");
		return Result;
	}

	@Override
	public ResultSet call() throws Exception {
		// TODO Auto-generated method stub
		return get_data_from_reservation();
	}
    }
    
    public class GetCountFromReservation implements Callable {
	private int get_count_of_reservations() throws SQLException {
		ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT COUNT(*) FROM NEW_RESERVATIONS");
		Result.next();
		return Result.getInt(1);
	}

	@Override
	public  Object call() throws Exception {
		// TODO Auto-generated method stub
		return get_count_of_reservations();
	}
    }
	private void give_data_from_reservation_to_table(ResultSet Result) throws SQLException, IOException {
		
		Result.next();
		String Date1 = Result.getDate("Date").toString();
		String Hour = Result.getString("Hour");
		String Building = Result.getString("Building");
		String Room = Result.getString("Room");
		String User = Result.getString("User");
        Data.add(new DataToTable(Date1,Hour,Building,Room,User));
	}
    
    @SuppressWarnings("unchecked")
    public class SetData implements Runnable {
	private void set_data_to_table() throws Exception {
    	GetCountFromReservation thread1 = new GetCountFromReservation();
    	int cout_of_rows = (int) (thread1.call());
    	SelectFromReservation thread2 = new SelectFromReservation();
		ResultSet Result = thread2.call();
		
		for (int i = 0 ; i < cout_of_rows; i++){
		give_data_from_reservation_to_table(Result);
		}
        MyTable1.setItems(Data);
        
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			set_data_to_table();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    }
	@SuppressWarnings("unchecked")
	public void initialize() throws Exception {
		
		AskPane1.setVisible(false);
		AskPane2.setVisible(false);
		AskPane3.setVisible(false);

		AskPane2.setStyle("-fx-background-color: white;");
		AskPane3.setStyle("-fx-background-color: white;");

        DateColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("DateValue"));
        HourColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("HourValue"));
        BuildingColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("BuildingValue"));
        RoomsColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("RoomsValue"));
        UserColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("UserValue"));
        CheckBoxColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,CheckBox>("CheckBoxValue"));

        
        SetData thread1 = new SetData();
		thread1.run();
        MyTable1.getColumns().addAll(DateColumn, HourColumn, BuildingColumn,RoomsColumn,UserColumn);
        MyTable1.setPlaceholder(new Label("Brak rezerwacji"));
	}
	
	public static class DataToTable {
		private final SimpleStringProperty DateValue;
        private final SimpleStringProperty HourValue;
        private final SimpleStringProperty BuildingValue;
        private final SimpleStringProperty RoomsValue;
        private final SimpleStringProperty UserValue;
        private final CheckBox CheckBoxValue;

        private DataToTable (String fDate, String fHour, String fBuilding, String fRooms,String fUser) {
            this.DateValue = new SimpleStringProperty(fDate);
            this.HourValue = new SimpleStringProperty(fHour);
            this.BuildingValue = new SimpleStringProperty(fBuilding);
            this.RoomsValue = new SimpleStringProperty(fRooms);
            this.UserValue = new SimpleStringProperty(fUser);
            this.CheckBoxValue = new CheckBox();
        }
        
        public void setDateValue(String dateValue) {
        	DateValue.set(dateValue);
        }
        public String getDateValue () {
        	return DateValue.get();
        }
        public void setHourValue (String hourValue){
        	HourValue.set(hourValue);
        }
        public String getHourValue (){
        	return HourValue.get();
        }
        public void setBuildingValue (String buildingValue){
        	BuildingValue.set(buildingValue);
        }
        public String getBuildingValue (){
        	return BuildingValue.get();
        }
        public void setRoomsValue (String roomsValue){
        	RoomsValue.set(roomsValue);
        }
        public String getRoomsValue (){
        	return RoomsValue.get();
        }
        public void setUserValue (String userValue){
        	UserValue.set(userValue);
        }
        public String getUserValue (){
        	return UserValue.get();
        }
        public CheckBox getCheckBoxValue () {
        	return CheckBoxValue;
        }
	}
	
	@FXML
	Button DeleteButton;
	@FXML
	Pane AskPane1;
	@FXML
	Pane AskPane2;
	
	public class DeleteReservation implements Runnable {
	private void delete_reservation_from_db(int index) throws Exception {
		WhatID thread1 = new WhatID();
		String id = (String) thread1.call();
		Connection con = Main.getConnection();
		PreparedStatement MyStatement1 = (PreparedStatement) con.prepareStatement("insert into DELETE_RESERVATIONS value "
				+ "(?,?,?,?,'no','no',?,?,'single','no','no','no','no',?,'no')");
		MyStatement1.setString(1, id);
		MyStatement1.setString(2, DateColumn.getCellData(index));
		MyStatement1.setString(3, HourColumn.getCellData(index));
	    LocalDate Day = LocalDate.parse(DateColumn.getCellData(index));
	    DayOfWeek Day1 = Day.getDayOfWeek();
	    MyStatement1.setString(4, Day1.toString());
		MyStatement1.setString(6, BuildingColumn.getCellData(index));
		MyStatement1.setString(5, RoomsColumn.getCellData(index));
		MyStatement1.setString(7, UserColumn.getCellData(index));
		MyStatement1.executeUpdate();
		MyStatement1.executeUpdate();
	    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("delete from "
	    		+ "NEW_RESERVATIONS where Date=? and Hour=? and Building=? and Room=? and User=?");
	    MyStatement.setString(1, DateColumn.getCellData(index));
	    MyStatement.setString(2, HourColumn.getCellData(index));
	    MyStatement.setString(3, BuildingColumn.getCellData(index));
	    MyStatement.setString(4, RoomsColumn.getCellData(index));
	    MyStatement.setString(5, UserColumn.getCellData(index));
	    
		MyStatement.executeUpdate();
		con.close();
		}
	private int index;
	private DeleteReservation(int index) {
		this.index = index;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			delete_reservation_from_db(index);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
	
	public class AddReservation implements Runnable {
	private void insert_reservation(int index) throws Exception {
		Connection NewConnection = Main.getConnection();
		WhatID thread1 = new WhatID();
		String id = (String) thread1.call();
		PreparedStatement MyStatement = (PreparedStatement) NewConnection
				.prepareStatement("insert into RESERVATIONS value (?,?,?,?,'no','no',?,?,'single','no','no','no','no',?,'no');");
	    MyStatement.setString(1, id);
	    MyStatement.setString(2, DateColumn.getCellData(index));
	    MyStatement.setString(3, HourColumn.getCellData(index));
	    String DayOfTheWeek = DateColumn.getCellData(index);
	    LocalDate Day = LocalDate.parse(DayOfTheWeek);
	    DayOfWeek Day1 = Day.getDayOfWeek();
	    MyStatement.setString(4, Day1.toString());
	    MyStatement.setString(5, RoomsColumn.getCellData(index));
	    MyStatement.setString(6, BuildingColumn.getCellData(index));
	    MyStatement.setString(7, UserColumn.getCellData(index));
		MyStatement.execute();
		}
	private int index;
	private AddReservation (int index) {
		this.index = index;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			insert_reservation(index);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	
	public class CheckReservation implements Callable {
		
	private int select_count(int index) throws SQLException {
		    Connection con = Main.getConnection();
		    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("SELECT COUNT(*) FROM RESERVATIONS where "
					+ "Date=? and Hour=? and Building=? and Room=?");
		    MyStatement.setString(1, DateColumn.getCellData(index));
		    MyStatement.setString(2, HourColumn.getCellData(index));
		    MyStatement.setString(3, BuildingColumn.getCellData(index));
		    MyStatement.setString(4, RoomsColumn.getCellData(index));
			ResultSet Result = MyStatement.executeQuery();
			Result.next();
			return Result.getInt(1);
	}
		

	private int index;
	private CheckReservation (int index) {
		this.index = index;
	}

	@Override
	public Object call() throws SQLException {
		return select_count(index);
	}
	}
	
	private void ask_what_next() {
		AskPane1.setVisible(true);
		AskPane2.setVisible(true);
		DeleteButton.setDisable(true);
		AddButton.setDisable(true);
		UndoButton.setDisable(true);
	}
	
	private void ask_what_next1() {
		AskPane1.setVisible(true);
		AskPane3.setVisible(true);
		DeleteButton.setDisable(true);
		AddButton.setDisable(true);
		UndoButton.setDisable(true);
	}
	@FXML
	private void say_ok() {
		AskPane1.setVisible(false);
		AskPane3.setVisible(false);
		DeleteButton.setDisable(false);
		AddButton.setDisable(false);
		UndoButton.setDisable(false);
		MyTable1.setVisible(false);
		Data.clear();
		SetData thread1 = new SetData();
	    thread1.run();
		MyTable1.setVisible(true);
	}
	
	@FXML 
	Button YesButton;
	@FXML
	Button NoButton;
	@FXML
	Label Label1;
	
    private void close() throws IOException{
    		Parent login_panel_page = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
            Scene login_panel_scene = new Scene(login_panel_page);
            Stage app_stage = (Stage) DeleteButton.getScene().getWindow();
            app_stage.setTitle("Panel Administratora");
            app_stage.setScene(login_panel_scene);
            app_stage.centerOnScreen();
            app_stage.show();
    }
	
	@FXML
	public void say_no_button() throws IOException {
		close();
	}
	
	@FXML
	public void say_yes_button() throws SQLException, IOException {	
		AskPane1.setVisible(false);
		Label1.setText("Czy chcesz usunąć lub dodać kolejne rezerwacje?");
		AskPane2.setVisible(false);
		DeleteButton.setDisable(false);
		AddButton.setDisable(false);
		UndoButton.setDisable(false);
		MyTable1.setVisible(false);
		Data.clear();
		SetData thread1 = new SetData();
	    thread1.run();
		MyTable1.setVisible(true);
	}
	
	@FXML
	public void click_on_delete_button () throws Exception {
		GetCountFromReservation thread1 = new GetCountFromReservation();
    	int cout_of_rows = (int) (thread1.call());
		CheckBox Check; 
		for (int i = 0;i < cout_of_rows;i++)
		{
			Check = (CheckBox) CheckBoxColumn.getCellData(i);
			if(Check.isSelected()) {
				DeleteReservation thread2 = new DeleteReservation(i);
				thread2.run();
			}
		}
		DeleteButton.setDisable(true);
		AddButton.setDisable(true);
		UndoButton.setDisable(true);
		ask_what_next();
	}
	
	@FXML
	Button AddButton;
	
	@FXML
	public void click_on_add_button() throws Exception {
		GetCountFromReservation thread1 = new GetCountFromReservation();
    	int cout_of_rows = (int) (thread1.call());
		CheckBox Check; 
		DeleteButton.setDisable(true);
		AddButton.setDisable(true);
		UndoButton.setDisable(true);
		for (int i = 0;i < cout_of_rows;i++)
		{
			Check = (CheckBox) CheckBoxColumn.getCellData(i);
			if(Check.isSelected()) {
				
				CheckReservation thread2 = new CheckReservation(i);
				int statement = (int) thread2.call();
				if(statement==0){
					AddReservation thread3 = new AddReservation(i);
					thread3.run();
					DeleteReservation thread4 = new DeleteReservation(i);
					thread4.run();
					ask_what_next();
				}
				else
				{
					DeleteReservation thread4 = new DeleteReservation(i);
					thread4.run();
					ask_what_next1();
				}
			}
		}
	
	}
	@FXML
	Label Label2;
	@FXML
	Pane AskPane3;
	@FXML
	Button OkButton;
	@FXML
	Button UndoButton;
	
	@FXML
	public void click_on_undo_button() throws IOException {
		close();
	}
	}