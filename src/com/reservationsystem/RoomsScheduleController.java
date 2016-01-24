package com.reservationsystem;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;





public class RoomsScheduleController implements Initializable{

	LocalDate currentDay;
	LocalDate maxDay;
	LocalDate minDay;
	
	List<ObservableList<Rooms>> dataList = new ArrayList<ObservableList<Rooms>>();
	List<TableView<Rooms>> tableList = new ArrayList<TableView<Rooms>>();
	List<boolean[]> settedIndex = new ArrayList<boolean[]>();
	ObservableList<Hours> hourData = FXCollections.observableArrayList();
	ObservableList<Rooms> mondayData = FXCollections.observableArrayList();
	ObservableList<Rooms> tuesdayData = FXCollections.observableArrayList();
	ObservableList<Rooms> wednesdayData = FXCollections.observableArrayList();
	ObservableList<Rooms> thursdayData = FXCollections.observableArrayList();
	ObservableList<Rooms> fridayData = FXCollections.observableArrayList();
	ObservableList<Rooms> saturdayData = FXCollections.observableArrayList();
	ObservableList<Rooms> sundayData = FXCollections.observableArrayList();
	ObservableList<String> buildingsList = FXCollections.observableArrayList();
	ObservableList<String> roomsList = FXCollections.observableArrayList();
	
	@FXML
	private Label dateLabel;
	
	@FXML
	private Pane allTable;
	
	@FXML
	private Pane waitPane;
	
	@FXML
	private Button leftButton;
	
	@FXML
	private Button rightButton;
	
	@FXML
	private ChoiceBox<String> buildingChoice;
	
	@FXML
	private ChoiceBox<String> roomChoice;
	
	@FXML
    private TableView <Hours> hourTable;
	
	@FXML
    private TableView <Rooms> mondayTable;
	
	@FXML
    private TableView <Rooms> tuesdayTable;
	
	@FXML
    private TableView <Rooms> wednesdayTable;
	
	@FXML
    private TableView <Rooms> thursdayTable;
	
	@FXML
    private TableView <Rooms> fridayTable;
	
	@FXML
    private TableView <Rooms> saturdayTable;
	
	@FXML
    private TableView <Rooms> sundayTable;
	
	@FXML
    private TableColumn< Hours, VBox > hourColumn;
	
	@FXML
    private TableColumn <Rooms, VBox> mondayColumn;
	
	@FXML
    private TableColumn <Rooms, VBox> tuesdayColumn;
	
	@FXML
    private TableColumn <Rooms, VBox> wednesdayColumn;

	@FXML
    private TableColumn <Rooms, VBox> thursdayColumn;
	
	@FXML
    private TableColumn <Rooms, VBox> fridayColumn;
	
	@FXML
    private TableColumn <Rooms, VBox> saturdayColumn;
	
	@FXML
    private TableColumn <Rooms, VBox> sundayColumn;
	
	@FXML
	private Button cancel;
	
	public void upDateAction()
	{
		
		if (currentDay.plusDays(7).isBefore(maxDay))
		{
			currentDay = currentDay.plusDays(7);
			dateLabel.setText(currentDay.with(DayOfWeek.MONDAY).toString() + " - " + currentDay.with(DayOfWeek.SUNDAY).toString());
			clearAll();
			allTable.setVisible(false);
			waitPane.setVisible(true);
			buildingChoice.setDisable(true);
			roomChoice.setDisable(true);
			rightButton.setDisable(true);
			leftButton.setDisable(true);	
			dayChanged task = new dayChanged(currentDay);
			new Thread(task).start();
		}
		
	}
	
	public void clearAll()
	{
		for (int i = 0; i < 7; i++)
		{
			dataList.get(i).clear();
		}
	}
	
	public void downDateAction()
	{
		if (currentDay.minusDays(7).isAfter(minDay))
		{
			currentDay = currentDay.minusDays(7);
			dateLabel.setText(currentDay.with(DayOfWeek.MONDAY).toString() + " - " + currentDay.with(DayOfWeek.SUNDAY).toString());
			clearAll();
			allTable.setVisible(false);
			waitPane.setVisible(true);
			buildingChoice.setDisable(true);
			roomChoice.setDisable(true);
			rightButton.setDisable(true);
			leftButton.setDisable(true);	
			dayChanged task = new dayChanged(currentDay);
			new Thread(task).start();
		}
		
	}
	
	public void clearSettedIndex()
	{
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; i < 7; i++)
			{
				settedIndex.get(i)[j] = false;
			}
		}
	}
	
	public class Rooms {
		private VBox box;
		private Label firstLabel;
		private Label secondLabel;
		private Label thirdLabel;
		
		public Rooms (int type, String subject, String lecturer, String group, String year ) {
			box = new VBox();
			if (type == 1)
			{
				firstLabel = createLabel("#ffcc99",subject);
		        secondLabel = createLabel("#ffb366",lecturer);
		        thirdLabel = createLabel("#ff9933",year + " " + group);
				box.getChildren().addAll(firstLabel,secondLabel,thirdLabel);
				return;
			}
			if (type == 2)
			{
				firstLabel = createLabel("#8cd98c", "REZERWACJA");
				secondLabel = createLabel("#66cc66", "INDYWIDUALNA");
				box.getChildren().addAll(firstLabel,secondLabel);
				return;
			}
			if (type == 3)
			{
				firstLabel = createLabel("#ffffff","");
				secondLabel = createLabel("#ffffff","");
				box.getChildren().addAll(firstLabel,secondLabel);
				return;
			}
		}
		
		/*private final Label createLabel(String color) {
            Label label = new Label();
            VBox.setVgrow(label, Priority.ALWAYS);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setStyle("-fx-background-color: "+color+" ;");
            label.setAlignment(Pos.CENTER);
            return label ;
        }*/
		private final Label createLabel(String color,String temp) {
            Label label = new Label();
            VBox.setVgrow(label, Priority.ALWAYS);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setMaxHeight(Double.MAX_VALUE);
            label.setStyle("-fx-background-color: "+color+" ;");
            label.setAlignment(Pos.CENTER);
            label.setText(temp);
            return label ;
        }
		
		
		public VBox getBox() {
			return this.box;
		}
		
		public void setBox(VBox a) {
			this.box = a;
		}
	}
	
	public class Hours {
		private VBox box;
		private Label startHour;
		private Label endHour;
		
		public Hours (String start, String end) {
			box = new VBox();
			startHour = createLabel("#99ddff",start);
	        endHour = createLabel("#66ccff",end);
			box.getChildren().addAll(startHour, endHour);
			//box.setPrefSize(80, 50); //tutaj dałem komentarz;
			
		}
		
		private final Label createLabel(String color,String temp) {
            Label label = new Label();
            VBox.setVgrow(label, Priority.ALWAYS);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setMaxHeight(Double.MAX_VALUE);
            label.setStyle("-fx-background-color: "+color+" ;");
            label.setAlignment(Pos.CENTER);
            label.setText(temp);
            return label ;
        }
		
		
		public VBox getBox() {
			return this.box;
		}
		
		public void setBox(VBox a) {
			this.box = a;
		}
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		dateLabel.setFont(Font.font("Segoe UI", 16));
		rightButton.setDisable(true);
		leftButton.setDisable(true);
		dateLabel.setAlignment(Pos.CENTER);
		waitPane.setVisible(false);
		//allTable.setVisible(false);
		//allTable.setVisible(true);
		roomChoice.setDisable(true);
		currentDay = LocalDate.now();
		maxDay = currentDay.plusDays(35);
		minDay = currentDay.minusDays(35);
		dateLabel.setText(currentDay.with(DayOfWeek.MONDAY).toString() + " - " + currentDay.with(DayOfWeek.SUNDAY).toString());
		dataList.add(mondayData);
		dataList.add(tuesdayData);
		dataList.add(wednesdayData);
		dataList.add(thursdayData);
		dataList.add(fridayData);
		dataList.add(saturdayData);
		dataList.add(sundayData);
		tableList.add(mondayTable);
		tableList.add(tuesdayTable);
		tableList.add(wednesdayTable);
		tableList.add(thursdayTable);
		tableList.add(fridayTable);
		tableList.add(saturdayTable);
		tableList.add(sundayTable);
		hourColumn.setSortable(false);
		mondayColumn.setSortable(false);
		tuesdayColumn.setSortable(false);
		wednesdayColumn.setSortable(false);
		thursdayColumn.setSortable(false);
		fridayColumn.setSortable(false);
		saturdayColumn.setSortable(false);
		sundayColumn.setSortable(false);
		
		
		
        buildingChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number> () {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resetDate();
                rightButton.setDisable(true);
        		leftButton.setDisable(true);
                roomChoice.setDisable(true);
                waitPane.setVisible(false);
                allTable.setVisible(false);
				roomChoice.getSelectionModel().clearSelection();
				roomChoice.getItems().clear();
				
                buildingChanged task = new buildingChanged (buildingsList.get(newValue.intValue()));
				new Thread(task).start();
			}
		});
        
        
      
        
        roomChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number> () {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() != -1 )
				{
				clearAll();
				resetDate();
				allTable.setVisible(false);
				waitPane.setVisible(true);
				buildingChoice.setDisable(true);
				roomChoice.setDisable(true);
				String room = roomsList.get(newValue.intValue());
				roomSelected task = new roomSelected(room);
				new Thread(task).start();
				}
			}
		});
		
        for (int i = 0; i < 7; i++){
        	settedIndex.add(new boolean[7]);
        }
		
		hourData.add(new Hours("8:00","9:30"));
		hourData.add(new Hours("9:45","11:15"));
		hourData.add(new Hours("11:45","13:15"));
		hourData.add(new Hours("13:30","15:00"));
		hourData.add(new Hours("15:15","16:45"));
		hourData.add(new Hours("17:00","18:30"));
		hourData.add(new Hours("18:45","20:15"));
		hourColumn.setCellValueFactory(new PropertyValueFactory<Hours, VBox>("box"));
		mondayColumn.setCellValueFactory(new PropertyValueFactory<Rooms, VBox>("box"));
		tuesdayColumn.setCellValueFactory(new PropertyValueFactory<Rooms, VBox>("box"));
		wednesdayColumn.setCellValueFactory(new PropertyValueFactory<Rooms, VBox>("box"));
		thursdayColumn.setCellValueFactory(new PropertyValueFactory<Rooms, VBox>("box"));
		fridayColumn.setCellValueFactory(new PropertyValueFactory<Rooms, VBox>("box"));
		saturdayColumn.setCellValueFactory(new PropertyValueFactory<Rooms, VBox>("box"));
		sundayColumn.setCellValueFactory(new PropertyValueFactory<Rooms, VBox>("box"));

		
		
		hourTable.setFixedCellSize(70.0);
		mondayTable.setFixedCellSize(70.0);
		tuesdayTable.setFixedCellSize(70.0);
		wednesdayTable.setFixedCellSize(70.0);
		thursdayTable.setFixedCellSize(70.0);
		fridayTable.setFixedCellSize(70.0);
		saturdayTable.setFixedCellSize(70.0);
		sundayTable.setFixedCellSize(70.0);

		getBuildings task = new getBuildings();
		new Thread(task).start();
		
		hourTable.setItems(hourData);
		//mondayTable.setItems(mondayData);
		//thursdayTable.setItems(thursdayData);
		//hourTable.refresh();

		
	}
	
	 public class buildingChanged extends Task{
		   
		 public String newBuilding;
		 public buildingChanged(String new_b) {
			 newBuilding = new_b;
		 }
		    
		    @Override
		    protected Object call() throws Exception {
            	
				Connection con = Main.getConnection();
				roomsList.clear();
     			PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT NUMBER FROM ROOMS WHERE BUILDING = ?;");
				stmt.setString(1,newBuilding);
				ResultSet rs = stmt.executeQuery();
				while (rs.next())
				{
					roomsList.add(Integer.toString(rs.getInt("NUMBER")));
				}
				
				Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                    	roomChoice.setDisable(false);
                    	//roomChoice.getSelectionModel().clearSelection();
                    	roomChoice.setItems(roomsList);
                    }
                });
				con.close();
		        return null;
		        
		    }
		}
	 
	 
	  public void resetDate()
      {
			currentDay = LocalDate.now();
			dateLabel.setText(currentDay.with(DayOfWeek.MONDAY).toString() + " - " + currentDay.with(DayOfWeek.SUNDAY).toString());
      }
	 
	  public int indexMapper(String a){
		  switch (a) {
		    case "8:00-9:30" :
		    	return 0;
		    case "9:45-11:15" :
		    	return 1;
		    case "11:45-13:15" :
		    	return 2;
		    case "13:30-15:00" :
		    	return 3;
		    case "15:15-16:45" :
		    	return 4;
		    case "17:00-18:30" :
		    	return 5;
		    case "18:45-20:15" :
		    	return 6;
		  }
		  return 0;
		}
	  
	  
	  public class dayChanged extends Task{
		   
			 public LocalDate newDay;
			 public dayChanged (LocalDate new_d) {
				 newDay = new_d;
			 }
			    
			    @Override
			    protected Object call() throws Exception {
			    	for (int x = 0; x < 7; x++)
					{
						for (int y = 0; y < 7; y++)
						{
								dataList.get(x).add(y,new Rooms(3,"","","",""));
						}
					}
			    	Connection con = Main.getConnection();
					LocalDate currDay = newDay.with(DayOfWeek.MONDAY);
					for (int i = 0; i < 7; i++){
						PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT Hour, Subject, Lecturer, Year, Number_of_group, User FROM RESERVATIONS WHERE BUILDING = ? AND ROOM = ? AND DATE = ?;");
						stmt.setString(1, buildingChoice.getValue());
						stmt.setString(2, roomChoice.getValue());
						stmt.setDate(3,Date.valueOf(currDay));
						ResultSet rs = null;
						try {
						rs = stmt.executeQuery();
						} catch (SQLException e)
						{
							e.printStackTrace();
						}
						currDay = currDay.plusDays(1);
						while (rs.next())
						{	
							int index = indexMapper(rs.getString("Hour"));
							dataList.get(i).remove(index);
							if (rs.getString("User").equals("Admin"))
							{
								dataList.get(i).add(index, new Rooms(1,rs.getString("Subject"),rs.getString("Lecturer"),rs.getString("Number_of_group"),rs.getString("Year")));
							}
							else
							{
								dataList.get(i).add(index, new Rooms(2,"","","",""));
							}
							settedIndex.get(i)[index] = true;
							
						}		
					}
					
					con.close();
					
					Platform.runLater(new Runnable(){
	                    @Override
	                    public void run() {
	                    	buildingChoice.setDisable(false);
	        				roomChoice.setDisable(false);
	        				rightButton.setDisable(false);
	        				leftButton.setDisable(false);
	                    	waitPane.setVisible(false);
	                    	allTable.setVisible(true);
	                    	for (int i = 0; i < 7; i++)
	                    	{
	                    		tableList.get(i).setItems(dataList.get(i));
	                    	}
	                    	
	                    	
	                    	
	                    	
	                    }
	                });
				return null;
			}
			    
		 }
	  
	 public class roomSelected extends Task{
		   
		 public String newRoom;
		 public roomSelected (String new_r) {
			 newRoom = new_r;
		 }
		    
		    @Override
		    protected Object call() throws Exception {
		    	clearSettedIndex();
				Connection con = Main.getConnection();
				LocalDate currDay = currentDay.with(DayOfWeek.MONDAY);
				
				for (int x = 0; x < 7; x++)
				{
					for (int y = 0; y < 7; y++)
					{
							dataList.get(x).add(y,new Rooms(3,"","","",""));
					}
				}
				
				for (int i = 0; i < 7; i++){
					PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT Hour, Subject, Lecturer, Year, Number_of_group, User FROM RESERVATIONS WHERE BUILDING = ? AND ROOM = ? AND DATE = ?;");
					stmt.setString(1, buildingsList.get(buildingChoice.getSelectionModel().getSelectedIndex()));
					stmt.setString(2, newRoom);
					stmt.setDate(3,Date.valueOf(currDay));
					ResultSet rs = null;
					try {
					rs = stmt.executeQuery();
					} catch (SQLException e)
					{
						e.printStackTrace();
					}
					currDay = currDay.plusDays(1);
					while (rs.next())
					{	
						
						
						int index = indexMapper(rs.getString("Hour"));
						dataList.get(i).remove(index);
						if (rs.getString("User").equals("Admin"))
						{
							dataList.get(i).add(index, new Rooms(1,rs.getString("Subject"),rs.getString("Lecturer"),rs.getString("Number_of_group"),rs.getString("Year")));
						}
						else
						{
							dataList.get(i).add(index, new Rooms(2,"","","",""));
						}
						settedIndex.get(i)[index] = true;
						
					}		
				}
				
				con.close();
				
				Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                    	buildingChoice.setDisable(false);
        				roomChoice.setDisable(false);
                    	waitPane.setVisible(false);
                    	allTable.setVisible(true);
                    	rightButton.setDisable(false);
        				leftButton.setDisable(false);
                    	for (int i = 0; i < 7; i++)
                    	{
                    		tableList.get(i).setItems(dataList.get(i));
                    	}
                    	
                    	
                    	
                    	
                    }
                });
			return null;
		}
		    
	 }
	
	public class getBuildings extends Task{
		 
		 public int newSize;
		 public String newHour;
		 
		    @Override
		    protected Object call() throws Exception {
		    	buildingsList.clear();

		    	Connection con = Main.getConnection();
				try {
					PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT DISTINCT BUILDING FROM ROOMS;");
					ResultSet rs = stmt.executeQuery();
				    while (rs.next())
				    {
				    	buildingsList.add(rs.getString("BUILDING"));
				    }
				    con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Platform.runLater(new Runnable(){
                   @Override
                   public void run() {
                   	
                   	buildingChoice.setItems(buildingsList);
                   	buildingChoice.setDisable(false);
                   	buildingChoice.getSelectionModel().clearSelection();
                   }
               });
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
	          Stage app_stage = (Stage) cancel.getScene().getWindow();
	          
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

	
}


	
