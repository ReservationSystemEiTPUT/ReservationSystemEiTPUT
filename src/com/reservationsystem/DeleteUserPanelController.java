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
import com.reservationsystem.DeleteSimpleReservationPanelController.DataToTable;
import com.reservationsystem.DeleteSimpleReservationPanelController.DeleteReservation;
import com.reservationsystem.DeleteSimpleReservationPanelController.GetCountFromReservation;
import com.reservationsystem.DeleteSimpleReservationPanelController.SelectFromReservation;
import com.reservationsystem.DeleteSimpleReservationPanelController.SetData;

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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Group;

public class DeleteUserPanelController {
	
	@FXML
    private TableView<DataToTable> MyTable = new TableView<DataToTable>();
    private final ObservableList<DataToTable> Data =
        FXCollections.observableArrayList(); 

    @FXML
    TableColumn<DataToTable, String> NameColumn;
    @FXML
    TableColumn<DataToTable, String>  SurnameColumn;
    @FXML
    TableColumn<DataToTable, String>  LoginColumn;
    @FXML
    TableColumn<DataToTable, String>  EmailColumn;
    @FXML
    TableColumn<DataToTable, CheckBox> CheckBoxColumn;
    
    public class SelectFromUsers implements Callable<ResultSet> {
    private ResultSet get_data_from_users () throws SQLException {
		ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT * FROM USERS");
		return Result;
	}

	@Override
	public ResultSet call() throws Exception {
		// TODO Auto-generated method stub
		return get_data_from_users();
	}
    }
    
    public class GetCountFromUsers implements Callable {
	private int get_count_of_users() throws SQLException {
		ResultSet Result = AddNewUsersPanelController.get_result_set_for_my_query("SELECT COUNT(*) FROM USERS");
		Result.next();
		return Result.getInt(1);
	}

	@Override
	public  Object call() throws Exception {
		// TODO Auto-generated method stub
		return get_count_of_users();
	}
    }
	private void give_data_from_users_to_table(ResultSet Result) throws SQLException, IOException {
		
		Result.next();
		String Name = Result.getString("Name");
		String Surname = Result.getString("Surname");
		String Login = Result.getString("Login");
		String Email = Result.getString("Email");

        Data.add(new DataToTable(Name,Surname,Login,Email));
	}
    
    @SuppressWarnings("unchecked")
    public class SetData implements Runnable {
	private void set_data_to_table() throws Exception {
    	GetCountFromUsers thread1 = new GetCountFromUsers();
    	int cout_of_rows = (int) (thread1.call());
    	SelectFromUsers thread2 = new SelectFromUsers();
		ResultSet Result = thread2.call();
		
		for (int i = 0 ; i < cout_of_rows; i++){
		give_data_from_users_to_table(Result);
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
	public void initialize() throws Exception {
		
		AskPane1.setVisible(false);
		AskPane2.setVisible(false);
		AskPane2.setStyle("-fx-background-color: white;");
        NameColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("NameValue"));
        SurnameColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("SurnameValue"));
        LoginColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("LoginValue"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("EmailValue"));
        CheckBoxColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,CheckBox>("CheckBoxValue"));

        
        SetData thread1 = new SetData();
		thread1.run();
        MyTable.getColumns().addAll(NameColumn, SurnameColumn, LoginColumn,EmailColumn,CheckBoxColumn);
        MyTable.setPlaceholder(new Label("Brak użytkowników"));

	}	
	
	public static class DataToTable {
		private final SimpleStringProperty NameValue;
        private final SimpleStringProperty SurnameValue;
        private final SimpleStringProperty LoginValue;
        private final SimpleStringProperty EmailValue;
        private final CheckBox CheckBoxValue;

        private DataToTable (String fName, String fSurname, String fLogin, String fEmail) {
            this.NameValue = new SimpleStringProperty(fName);
            this.SurnameValue = new SimpleStringProperty(fSurname);
            this.LoginValue = new SimpleStringProperty(fLogin);
            this.EmailValue = new SimpleStringProperty(fEmail);
            this.CheckBoxValue = new CheckBox();
        }
        
        public void setNameValue(String nameValue) {
        	NameValue.set(nameValue);
        }
        public String getNameValue () {
        	return NameValue.get();
        }
        public void setSurnameValue (String surnameValue){
        	SurnameValue.set(surnameValue);
        }
        public String getSurnameValue (){
        	return SurnameValue.get();
        }
        public void setLoginValue (String loginValue){
        	LoginValue.set(loginValue);
        }
        public String getLoginValue (){
        	return LoginValue.get();
        }
        public void setEmailValue (String emailValue){
        	EmailValue.set(emailValue);
        }
        public String getEmailValue (){
        	return EmailValue.get();
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
	    		+ "USERS where Login=?");
	    MyStatement.setString(1, LoginColumn.getCellData(index));
	    String Update = "drop user '" + LoginColumn.getCellData(index) + "'";
	    PreparedStatement MyStatement1 = (PreparedStatement) con.prepareStatement(Update);
	    MyStatement.setString(1, LoginColumn.getCellData(index));
		MyStatement.executeUpdate();
		MyStatement1.executeUpdate();
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
		ProgressPane.setVisible(false);
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
		ProgressPane.setVisible(true);
		GetCountFromUsers thread1 = new GetCountFromUsers();
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
	
	@FXML
	ProgressIndicator ProgressBar; 
	@FXML
	Pane ProgressPane;
	
}