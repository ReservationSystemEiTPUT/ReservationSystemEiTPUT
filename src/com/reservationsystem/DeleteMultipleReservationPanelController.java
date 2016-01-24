package com.reservationsystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import com.mysql.jdbc.PreparedStatement;

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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DeleteMultipleReservationPanelController implements Initializable {
		
	@FXML 
	TableView<DataToTable1> MyTable;
	private final ObservableList<DataToTable1> Data =
		        FXCollections.observableArrayList(); 

    @FXML
    TableColumn<DataToTable1, String> DateColumn;
    @FXML
    TableColumn<DataToTable1, String>  HourColumn;
    @FXML
    TableColumn<DataToTable1, String> FrequencyColumn;
    @FXML
    TableColumn<DataToTable1, String>  BuildingColumn;
    @FXML
    TableColumn<DataToTable1, String>  RoomsColumn;
    @FXML
    TableColumn<DataToTable1, CheckBox> CheckBoxColumn;
    @FXML
    TableColumn<DataToTable1, String> SubjectColumn;
    @FXML
    TableColumn<DataToTable1, String> LecturerColumn;
	@FXML
	TableColumn<DataToTable1, String> YearColumn;
	@FXML
	TableColumn<DataToTable1, String> GroupColumn;
    
    
    public class DataToTable1 {
    	private final SimpleStringProperty DateValue;
        private final SimpleStringProperty HourValue;
        private final SimpleStringProperty BuildingValue;
        private final SimpleStringProperty RoomsValue;
        private final SimpleStringProperty SubjectValue;
        private final SimpleStringProperty LecturerValue;
        private final SimpleStringProperty FrequencyValue;
        private final SimpleStringProperty YearValue;
        private final SimpleStringProperty GroupValue;
        private final CheckBox CheckBoxValue;
        
        private DataToTable1 (String fDate, String fHour,String fFrequency,String fBuilding, String fRooms,String fSubject,
        		String fLecturer, String fYear, String fGroup)
        {
        	this.DateValue = new SimpleStringProperty(fDate);
        	this.HourValue = new SimpleStringProperty(fHour);
        	this.FrequencyValue = new SimpleStringProperty(fFrequency);
        	this.BuildingValue = new SimpleStringProperty(fBuilding);
        	this.RoomsValue = new SimpleStringProperty(fRooms);
        	this.SubjectValue = new SimpleStringProperty(fSubject);
        	this.LecturerValue = new SimpleStringProperty(fLecturer);
        	this.YearValue = new SimpleStringProperty(fYear);
        	this.GroupValue = new SimpleStringProperty(fGroup);
        	CheckBoxValue = new CheckBox();
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
        public String getFrequencyValue (){
        	return FrequencyValue.get();
        }
        public void setFrequencyValue (String frequencyValue){
        	FrequencyValue.set(frequencyValue);
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
        public void setSubjectValue (String subjectValue){
        	SubjectValue.set(subjectValue);
        }
        public String getSubjectValue (){
        	return SubjectValue.get();
        }
        public void setLecturerValue (String lecturerValue){
        	LecturerValue.set(lecturerValue);
        }
        public String getLecturerValue (){
        	return LecturerValue.get();
        }
        public CheckBox getCheckBoxValue () {
        	return CheckBoxValue;
        }
        public void setYearValue (String yearValue){
        	YearValue.set(yearValue);
        }
        public String getYearValue (){
        	return YearValue.get();
        }
        public void setGroupValue (String groupValue){
        	GroupValue.set(groupValue);
        }
        public String getGroupValue (){
        	return GroupValue.get();
        }
    }

    private class SelectFromReservation implements Callable<ResultSet> {
        private ResultSet get_data_from_reservation () throws SQLException {
    		ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT * FROM RESERVATIONS WHERE Type_of_reservation='multiple'");
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
    		ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT COUNT(*) FROM RESERVATIONS where Type_of_reservation='multiple'");
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
    		String Frequency = "";
    		String Even = Result.getString("Even");
    		String Odd = Result.getString("Odd");
    		if (Even.equals("yes") && Odd.equals("yes")){
    		   Frequency = "Co tydzieñ";
    		}
    		else if (Even.equals("no") && Odd.equals("yes")) {
    			Frequency = "Tygodnie nieparzyste";
    		}
    		else {
    			Frequency = "Tygodnie parzyste";
    		}
    		String Building = Result.getString("Building");
    		String Room = Result.getString("Room");
    		String Subject = Result.getString("Subject");
    		String Lecturer = Result.getString("Lecturer");
    		String Year = Result.getString("Year");
    		String Group = Result.getString("Number_of_group");

            Data.add(new DataToTable1(Date1,Hour,Frequency,Building,Room,Subject,Lecturer,Year,Group));
    	}
        
        @SuppressWarnings("unchecked")
        private class SetData implements Runnable {
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
    
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		AskPane1.setVisible(false);
		AskPane2.setVisible(false);
		AskPane2.setStyle("-fx-background-color: white;");
		DateColumn.setCellValueFactory(new PropertyValueFactory<DataToTable1,String>("DateValue"));
        HourColumn.setCellValueFactory(new PropertyValueFactory<DataToTable1,String>("HourValue"));
        FrequencyColumn.setCellValueFactory(new PropertyValueFactory<DataToTable1,String>("FrequencyValue"));
        BuildingColumn.setCellValueFactory(new PropertyValueFactory<DataToTable1,String>("BuildingValue"));
        RoomsColumn.setCellValueFactory(new PropertyValueFactory<DataToTable1,String>("RoomsValue"));
        SubjectColumn.setCellValueFactory(new PropertyValueFactory<DataToTable1,String>("SubjectValue"));
        LecturerColumn.setCellValueFactory(new PropertyValueFactory<DataToTable1,String>("LecturerValue"));
        CheckBoxColumn.setCellValueFactory(new PropertyValueFactory<DataToTable1,CheckBox>("CheckBoxValue"));
        YearColumn.setCellValueFactory(new PropertyValueFactory<DataToTable1,String>("YearValue"));
        GroupColumn.setCellValueFactory(new PropertyValueFactory<DataToTable1,String>("GroupValue"));

        Resume.setText("Rozdziel");
        SetResumeData thread1 = new SetResumeData();
		thread1.run();
        MyTable.getColumns().addAll(DateColumn, HourColumn,FrequencyColumn, BuildingColumn,RoomsColumn,SubjectColumn,LecturerColumn,YearColumn,GroupColumn);
        MyTable.setPlaceholder(new Label("Brak rezerwacji"));

	}
	
	
	
	@FXML
	Button DeleteButton;
	@FXML
	Pane AskPane1;
	@FXML
	Pane AskPane2;
	
	private class DeleteReservation implements Runnable {
		private void delete_reservation_from_db(int index) throws SQLException {
			Connection con = Main.getConnection();
		    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("delete from "
		    		+ "RESERVATIONS where Date=? and Hour=? and Building=? and Room=? and Subject=? and "
		    		+ "Lecturer=?");
		    MyStatement.setString(1, DateColumn.getCellData(index));
		    MyStatement.setString(2, HourColumn.getCellData(index));
		    MyStatement.setString(3, BuildingColumn.getCellData(index));
		    MyStatement.setString(4, RoomsColumn.getCellData(index));
		    MyStatement.setString(5, SubjectColumn.getCellData(index));
		    MyStatement.setString(6, LecturerColumn.getCellData(index));
		    
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
	
	private class DeleteReservation1 implements Runnable {
		private void delete_reservation_from_db(int index) throws SQLException {
			Connection con = Main.getConnection();
		    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("delete from "
		    		+ "RESERVATIONS where Hour=? and Building=? and Room=? and Subject=? and "
		    		+ "Lecturer=?");
		    MyStatement.setString(1, HourColumn.getCellData(index));
		    MyStatement.setString(2, BuildingColumn.getCellData(index));
		    MyStatement.setString(3, RoomsColumn.getCellData(index));
		    MyStatement.setString(4, SubjectColumn.getCellData(index));
		    MyStatement.setString(5, LecturerColumn.getCellData(index));
		    
		    System.out.print(MyStatement);
			MyStatement.executeUpdate();
			}
		private int index;
		private DeleteReservation1(int index) {
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
			ProgressPane.setVisible(false);
			AskPane1.setVisible(true);
			AskPane2.setVisible(true);
			DeleteButton.setDisable(true);
			Resume.setDisable(true);
			UndoButton.setDisable(true);
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
		public void say_yes_button() throws Exception {		
			AskPane1.setVisible(false);
			AskPane2.setVisible(false);
			DeleteButton.setDisable(false);
			Resume.setDisable(false);
			UndoButton.setDisable(false);
			Resume.setText("Rozdziel");
			MyTable.setVisible(false);
			Data.clear();
			SetResumeData thread1 = new SetResumeData();
		    thread1.run();
			MyTable.setVisible(true);
		}
		
		@FXML
		public void click_on_delete_button () throws Exception {
			ProgressPane.setVisible(true);
			if(Resume.getText().equals("Podsumuj")){

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
			}
			if(Resume.getText().equals("Rozdziel")){
				CheckBox Check; 
				for (int i = 0;i < help;i++)
				{
					Check = (CheckBox) CheckBoxColumn.getCellData(i);
					if(Check.isSelected()) {
						DeleteReservation1 thread2 = new DeleteReservation1(i);
						thread2.run();
					}
				}
			}
			DeleteButton.setDisable(true);
			Resume.setDisable(true);
			UndoButton.setDisable(true);
			ask_what_next();
		}
		
		class GetResultOfReservations implements Callable<ResultSet> {

			private ResultSet get_result_of_the_same_reservation() throws SQLException {
				String MyQuery = "select Date,Hour,Even,Odd,Room,Building,Type_of_reservation,"
						+ "Subject,Lecturer,Year,Number_of_group,count(*) as "
						+ "c from RESERVATIONS group by Hour,Even,Odd,Room,Building,Type_of_reservation,Subject,Lecturer"
						+ " having c >0 order by c desc";
				ResultSet Result = AddNewUsersPanelController
						.get_result_set_for_my_query(MyQuery);
				return Result;
			}
			@Override
			public ResultSet call() throws Exception {
				// TODO Auto-generated method stub
				return get_result_of_the_same_reservation();
			}
			
		}
		
		public int help = 0;
		
		class SetResumeData implements Runnable {
			
			private void execute_add(ResultSet Result) throws SQLException {
	    		if (Result.getString("Type_of_reservation").equals("multiple")) {
	    			help++;
	    			String Date1 = Result.getDate("Date").toString() + "(" + Result.getString("c") + ")";
	        		String Hour = Result.getString("Hour");
	        		String Frequency = "";
	        		String Even = Result.getString("Even");
	        		String Odd = Result.getString("Odd");
	        		if (Even.equals("yes") && Odd.equals("yes")){
	        		   Frequency = "Co tydzień";
	        		}
	        		else if (Even.equals("no") && Odd.equals("yes")) {
	        			Frequency = "Tygodnie nieparzyste";
	        		}
	        		else {
	        			Frequency = "Tygodnie parzyste";
	        		}
	        		String Building = Result.getString("Building");
	        		String Room = Result.getString("Room");
	        		String Subject = Result.getString("Subject");
	        		String Lecturer = Result.getString("Lecturer");
	        		String Year = Result.getString("Year");
	        		String Group1 = Result.getString("Number_of_group");
	        		String Group = (Group1.equals("All")) ? "Cały rok" : Group1;
	        		
	                Data.add(new DataToTable1(Date1,Hour,Frequency,Building,Room,Subject,Lecturer,Year,Group));

	    		}
			}
			
			private void set_data_to_table() throws Exception {
		    	GetResultOfReservations thread2 = new GetResultOfReservations();
		    	ResultSet Result =(ResultSet) (thread2.call());
		    	
		    	while (Result.next())
		    	{
		    		execute_add(Result);
		    	}

		    	
			}
			@Override
			public void run() {
				try {
					set_data_to_table();
					MyTable.setItems(Data);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		@FXML
		Button Resume;
		@FXML
		Button UndoButton;
		
		@FXML
		public void click_on_resume_buton() {
			ProgressPane.setVisible(true);
			if(Resume.getText().equals("Podsumuj")){
			MyTable.setVisible(false);
			Resume.setText("Rozdziel");
			Data.clear();
			SetResumeData thread1 = new SetResumeData();
			thread1.run();
			MyTable.setVisible(true);
			ProgressPane.setVisible(false);
			}
			else {
				Resume.setText("Podsumuj");
				MyTable.setVisible(false);
				Data.clear();
				SetData thread1 = new SetData();
			    thread1.run();
				MyTable.setVisible(true);
				ProgressPane.setVisible(false);
			}
		}
		
		@FXML
		public void click_on_undo_button() throws IOException {
			close();
		}
		@FXML
		ProgressIndicator ProgressBar; 
		@FXML
		Pane ProgressPane;
}