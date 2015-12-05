package com.reservationsystem;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

public class AddNewUsersPanelController {

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
			
			int ISize = get_count_of_records();
			CheckBox CheckBoxUserData[] = new CheckBox[ISize];
			ResultSet Result = get_data_from_new_users();
			
			for (int i = 0 ; i < ISize; i++){
			String Data = give_data_from_new_user(Result);
			CheckBoxUserData[i] = new CheckBox(Data);
			NewPane.add(CheckBoxUserData[i],0, i+2);
			}
			
		}
		
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
		
		public void get_result_set_for_my_execute(String Query, String Query1, String Query2, String Query3, String Query4) throws SQLException{
			Connection NewConnection = Main.getConnection();
			Statement NewPreparedStatement = (Statement) NewConnection
					.createStatement();
			System.out.print(Query);
			NewPreparedStatement.execute(Query);
			NewPreparedStatement.execute(Query1);
			NewPreparedStatement.execute(Query2);
			NewPreparedStatement.execute(Query3);
			NewPreparedStatement.execute(Query4);
			
		}
		
		
		private int get_count_of_records() throws SQLException {
			ResultSet Result = get_result_set_for_my_query("SELECT COUNT(*) FROM NEW_USERS");
			Result.next();
			
			return Result.getInt(1);
		}
		
		private ResultSet get_data_from_new_users () throws SQLException {
			ResultSet Result = get_result_set_for_my_query("SELECT * FROM NEW_USERS");
			return Result;
		}
		
		
		private String give_data_from_new_user (ResultSet Result) throws SQLException {
			
			Result.next();
			String Data = null;
			String Login = Result.getString("Login");
			String Name = Result.getString("Name");
			String Surname = Result.getString("Surname");
			Data = Login + " " + Name + " " + Surname;
			return  Data;
		}
		
		private String get_password(String Login) throws SQLException{
			ResultSet Password = get_result_set_for_my_query("SELECT AES_DECRYPT(Password,UNHEX('F3229A0B371ED2D9441B830D21A390C3')) from NEW_USERS WHERE LOGIN = '" + Login + "';");
			Password.next();
			return Password.getString(1);
		}
		
		private String get_password_from_user(String Login) throws SQLException{
			ResultSet Password = get_result_set_for_my_query("select Password from USERS where Login='" + Login + "'");
			Password.next();
			return Password.getString(1);
		}
		
		private void add_user_to_db(String Login) throws SQLException {
			String Pass = get_password(Login);
	     	//get_result_set_for_my_update("UPDATE NEW_USERS SET Password='" + Pass + "' WHERE Login='" + Login + "'");
	     	get_result_set_for_my_update("insert into USERS select * FROM NEW_USERS d where Login='" + Login + "';");
			String Pass1 = get_password(Login);
			add_user_as_admin(Login, Pass);
			delete_user_from_db(Login);
		}
		
		private void add_user_as_admin(String Login, String Password) throws SQLException {

			get_result_set_for_my_execute("CREATE USER '" + Login + "'@'%' identified by '" + Password + "';",
					"GRANT SELECT ON RESERVATIONS TO '" + Login + "'@'%';","GRANT SELECT ON ROOMS TO '" + Login + "'@'%';",
					"GRANT SELECT, UPDATE ON users_settings TO '" + Login + "'@'%';","GRANT SELECT, INSERT ON users_reservations TO '" + Login + "'@'%';");
		}
		
		private void delete_user_from_db(String Login) throws SQLException {
			get_result_set_for_my_update("delete from NEW_USERS where Login='" + Login + "'");
			AdminPanelController.open_new_window("AddNewUsersPanel.fxml","Akceptacja nowych uzytkowników");
		}
		
		@FXML
		public void click_on_add_button() throws SQLException {
			
			int ISize = get_count_of_records();
			CheckBox Check; 
			for (int i = 2;i < ISize + 2;i++)
			{
				Check = (CheckBox) NewPane.getChildren().get(i);
				if(Check.isSelected()) {
					String LoginName = Check.getText();
					String LoginNameArray[] = LoginName.split(" ", 2);
					add_user_to_db(LoginNameArray[0]);
				}
			}
		}
		
		@FXML
		public void click_on_delete_button() throws SQLException {
			int ISize = get_count_of_records();
			CheckBox Check; 
			for (int i = 2;i < ISize + 2;i++)
			{
				Check = (CheckBox) NewPane.getChildren().get(i);
				if(Check.isSelected()) {
					String LoginName = Check.getText();
					String LoginNameArray[] = LoginName.split(" ", 2);
					delete_user_from_db(LoginNameArray[0]);
				}
			}
		}
	
}