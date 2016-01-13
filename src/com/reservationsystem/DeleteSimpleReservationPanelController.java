package com.reservationsystem;

import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Group;

public class DeleteSimpleReservationPanelController {
	@FXML
    private TableView<DataToTable> MyTable = new TableView<DataToTable>();
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
		ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT * FROM RESERVATIONS WHERE Type_of_reservation='single'");
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
		ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT COUNT(*) FROM RESERVATIONS where Type_of_reservation='single'");
		Result.next();
		System.out.print(Result.getInt(1));
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
        MyTable.setItems(Data);
        
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
	public void initialize() throws Exception {
		
		AskPane1.setVisible(false);
		AskPane2.setVisible(false);
		AskPane2.setStyle("-fx-background-color: white;");
        DateColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("DateValue"));
        HourColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("HourValue"));
        BuildingColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("BuildingValue"));
        RoomsColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("RoomsValue"));
        UserColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("UserValue"));
        CheckBoxColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,CheckBox>("CheckBoxValue"));

        
        SetData thread1 = new SetData();
		thread1.run();
        MyTable.getColumns().addAll(DateColumn, HourColumn, BuildingColumn,RoomsColumn,UserColumn);

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
	private void delete_reservation_from_db(int index) throws SQLException {
		Connection con = Main.getConnection();
	    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("delete from "
	    		+ "RESERVATIONS where Date=? and Hour=? and Building=? and Room=? and User=?");
	    MyStatement.setString(1, DateColumn.getCellData(index));
	    MyStatement.setString(2, HourColumn.getCellData(index));
	    MyStatement.setString(3, BuildingColumn.getCellData(index));
	    MyStatement.setString(4, RoomsColumn.getCellData(index));
	    MyStatement.setString(5, UserColumn.getCellData(index));
	    
	    System.out.print(MyStatement);
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	
	private void ask_what_next() {
		AskPane1.setVisible(true);
		AskPane2.setVisible(true);
	}
	
	@FXML 
	Button YesButton;
	@FXML
	Button NoButton;
	
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
		AskPane2.setVisible(false);
		DeleteButton.setDisable(false);
		UndoButton.setDisable(false);
		MyTable.setVisible(false);
		Data.clear();
		SetData thread1 = new SetData();
	    thread1.run();
		MyTable.setVisible(true);
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
		UndoButton.setDisable(true);
		ask_what_next();
	}
	
	@FXML
	Button UndoButton;
	
	@FXML
	public void click_on_undo_button() throws IOException {
		close();
	}
	}