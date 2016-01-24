package com.reservationsystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;

import com.mysql.jdbc.PreparedStatement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AddNewUsersPanelController {
	
	public static ResultSet get_result_set_for_my_query (String Query) throws SQLException{
		Connection NewConnection = Main.getConnection();
		PreparedStatement NewPreparedStatement = (PreparedStatement) NewConnection
				.prepareStatement(Query);
		ResultSet Result = NewPreparedStatement.executeQuery();
		return Result;
	}
	
	public static void get_result_set_for_my_update(String Query) throws SQLException {
		Connection NewConnection = Main.getConnection();
		PreparedStatement NewPreparedStatement = (PreparedStatement) NewConnection
				.prepareStatement(Query);
		NewPreparedStatement.executeUpdate();
	}
	@FXML
    private TableView<DataToTable> MyTable1 = new TableView<DataToTable>();
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
		ResultSet Result = get_result_set_for_my_query("SELECT * FROM NEW_USERS");
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
		ResultSet Result = get_result_set_for_my_query("SELECT COUNT(*) FROM NEW_USERS");
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
    
    public class SetData implements Runnable {
	private void set_data_to_table() throws Exception {
    	GetCountFromUsers thread1 = new GetCountFromUsers();
    	int cout_of_rows = (int) (thread1.call());
    	SelectFromUsers thread2 = new SelectFromUsers();
		ResultSet Result = thread2.call();
		
		for (int i = 0 ; i < cout_of_rows; i++){
		give_data_from_users_to_table(Result);
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
		AskPane2.setStyle("-fx-background-color: white;");
        NameColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("NameValue"));
        SurnameColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("SurnameValue"));
        LoginColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("LoginValue"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,String>("EmailValue"));
        CheckBoxColumn.setCellValueFactory(new PropertyValueFactory<DataToTable,CheckBox>("CheckBoxValue"));

        
        SetData thread1 = new SetData();
		thread1.run();
        MyTable1.getColumns().addAll(NameColumn, SurnameColumn, LoginColumn,EmailColumn,CheckBoxColumn);
        MyTable1.setPlaceholder(new Label("Brak u¿ytkowników"));

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
	Button AddButton;
	@FXML
	Pane AskPane1;
	@FXML
	Pane AskPane2;
	
	public class DeleteReservation implements Runnable {
	private void delete_user_from_db(int index) throws SQLException {
		Connection con = Main.getConnection();
	    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("delete from "
	    		+ "NEW_USERS where Login=?");
	    MyStatement.setString(1, LoginColumn.getCellData(index));
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
			delete_user_from_db(index);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	@FXML 
	Label MyLabel;
	public class AddUser implements Runnable {
		private int check_login(int index) throws SQLException {
			Connection con = Main.getConnection();
		    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("select COUNT(*) from USERS where "
		    		+ "Login=?");
		    MyStatement.setString(1, LoginColumn.getCellData(index));
			ResultSet Result = MyStatement.executeQuery();
			Result.next();
			return Result.getInt(1);
		}
		private void insert_user_from_db(int index) throws SQLException {
			int checkLogin = check_login(index);
			if(checkLogin==0){
			Connection con = Main.getConnection();
		    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("insert into USERS select * FROM "
		    		+ "NEW_USERS d where Login=?");
		    MyStatement.setString(1, LoginColumn.getCellData(index));
			MyStatement.executeUpdate();
			con.close();
			}
			else
			{
				MyLabel.setText("Login zajêty! Czey chcesz wróciæ do wszystkich kont?");
				ask_what_next();
			}
			}
		private int index;
		private AddUser(int index) {
			this.index = index;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				insert_user_from_db(index);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	
	public class AddGrant implements Runnable {
		
		private String get_password(int index) throws SQLException{
			Connection con = Main.getConnection();
			PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("SELECT "
					+ "AES_DECRYPT(Password,UNHEX('F3229A0B371ED2D9441B830D21A390C3')) from "
					+ "NEW_USERS WHERE LOGIN =?");
			 MyStatement.setString(1, LoginColumn.getCellData(index));
			 ResultSet Password = MyStatement.executeQuery();
			 Password.next();
			 return Password.getString(1);
		}
		
		private void add_user_from_db(int index) throws SQLException {
			Connection con = Main.getConnection();
			String Login = LoginColumn.getCellData(index);
			String Password = get_password(index);
		    PreparedStatement MyStatement = (PreparedStatement) con.prepareStatement("CREATE USER ?@'%' identified by ?");
		    MyStatement.setString(1, Login);
		    MyStatement.setString(2, Password);
		    PreparedStatement MyStatement2 = (PreparedStatement) con.prepareStatement("GRANT SELECT ON RESERVATIONS TO ?@'%'");
		    MyStatement2.setString(1, Login);
		    PreparedStatement MyStatement3 = (PreparedStatement) con.prepareStatement("GRANT SELECT ON ROOMS TO ?@'%'");
		    MyStatement3.setString(1, Login);		    
		    PreparedStatement MyStatement4 = (PreparedStatement) con.prepareStatement("GRANT SELECT, UPDATE ON users_settings TO ?@'%'");
		    MyStatement4.setString(1, Login);
		    PreparedStatement MyStatement5 = (PreparedStatement) con.prepareStatement("GRANT SELECT, INSERT ON users_reservations TO ?@'%'");
		    MyStatement5.setString(1, Login);
		    PreparedStatement MyStatement6 = (PreparedStatement) con.prepareStatement("GRANT SELECT, INSERT ON users_deleted_reservations TO ?@'%'");
		    MyStatement6.setString(1, Login);
		    MyStatement.executeUpdate();
		    MyStatement2.executeUpdate();
		    MyStatement3.executeUpdate();
		    MyStatement4.executeUpdate();		    
		    MyStatement5.executeUpdate();
		    MyStatement6.executeUpdate();

			con.close();
			}
		private int index;
		private AddGrant(int index) {
			this.index = index;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				add_user_from_db(index);
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
		AddButton.setDisable(false);
		MyTable1.setVisible(false);
		Data.clear();
		SetData thread1 = new SetData();
	    thread1.run();
		MyTable1.setVisible(true);
	}
	
	@FXML
	public void click_on_delete_button () throws Exception {
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
		AddButton.setDisable(true);
		UndoButton.setDisable(true);
		MyLabel.setText("Czy chcesz dodaæ/usun¹æ kolejne konta?");
		ask_what_next();
	}
	
	@FXML
	public void click_on_add_button () throws Exception {
		GetCountFromUsers thread1 = new GetCountFromUsers();
    	int cout_of_rows = (int) (thread1.call());
		CheckBox Check; 
		for (int i = 0;i < cout_of_rows;i++)
		{
			Check = (CheckBox) CheckBoxColumn.getCellData(i);
			if(Check.isSelected()) {
				AddUser thread3 = new AddUser(i);
				thread3.run();
				AddGrant thread4 = new AddGrant(i);
				thread4.run();
				DeleteReservation thread2 = new DeleteReservation(i);
				thread2.run();
			}
		}
		DeleteButton.setDisable(true);
		AddButton.setDisable(true);
		UndoButton.setDisable(true);
		MyLabel.setText("Czy chcesz dodaæ/usun¹æ kolejne konta?");
		ask_what_next();
	}	
	
	@FXML
	Button UndoButton;
	
	@FXML
	public void click_on_undo_button() throws IOException {
		close();
	}
}