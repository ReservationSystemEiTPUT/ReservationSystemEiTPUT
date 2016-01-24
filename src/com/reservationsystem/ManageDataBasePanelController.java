package com.reservationsystem;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ManageDataBasePanelController implements Initializable {
	
	@FXML
	Pane AskPane2;
	@FXML
	Button YesButton1;
	@FXML
	Button NoButton1;
	@FXML
	Label Label1;
	@FXML
	Button Reservations;
	
	private void are_you_sure1() {
		AskPane2.setVisible(true);
		YesButton1.setVisible(true);
		NoButton1.setVisible(true);
		Label1.setVisible(true);
	}
	
	@FXML
	public void say_yes_button1() {
		AskPane2.setVisible(false);
		YesButton1.setVisible(false);
		NoButton1.setVisible(false);
		Label1.setVisible(false);
		DeleteReservation thread1 = new DeleteReservation();
		thread1.run();
		Reservations.setDisable(true);
	}
	
	@FXML
	public void say_no_button1() {
		AskPane2.setVisible(false);
		YesButton1.setVisible(false);
		NoButton1.setVisible(false);
		Label1.setVisible(false);
	}
	private class DeleteReservation implements Runnable {

		private String make_statement() {
			return "delete from RESERVATIONS";
		}
		
		private void execute() {
			 String MyUpdate = make_statement();
			 try {
				AddNewUsersPanelController.get_result_set_for_my_update(MyUpdate);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			execute();
		}
		
	}
	@FXML
	public void click_delete_reservations_table () {
		are_you_sure1();
	}
	@FXML
	Button YesButton2;
	@FXML
	Button NoButton2;
	@FXML
	Button NewReservations;
	
	private void are_you_sure2() {
		AskPane2.setVisible(true);
		YesButton2.setVisible(true);
		NoButton2.setVisible(true);
		Label1.setVisible(true);
	}
	
	@FXML
	public void say_yes_button2() {
		AskPane2.setVisible(false);
		YesButton2.setVisible(false);
		NoButton2.setVisible(false);
		Label1.setVisible(false);
		DeleteNewReservation thread1 = new DeleteNewReservation();
		thread1.run();
		NewReservations.setDisable(true);
	}
	
	@FXML
	public void say_no_button2() {
		AskPane2.setVisible(false);
		YesButton2.setVisible(false);
		NoButton2.setVisible(false);
		Label1.setVisible(false);
	}
	private class DeleteNewReservation implements Runnable {

		private String make_statement() {
			return "delete from NEW_RESERVATIONS";
		}
		
		private void execute() {
			 String MyUpdate = make_statement();
			 try {
				AddNewUsersPanelController.get_result_set_for_my_update(MyUpdate);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			execute();
		}
		
	}
	@FXML
	public void click_delete_new_reservations_table () {
		are_you_sure2();
	}
	
	@FXML
	Button YesButton3;
	@FXML
	Button NoButton3;
	@FXML
	Button Users;
	
	private void are_you_sure3() {
		AskPane2.setVisible(true);
		YesButton3.setVisible(true);
		NoButton3.setVisible(true);
		Label1.setVisible(true);
	}
	
	@FXML
	public void say_yes_button3() throws SQLException {
		AskPane2.setVisible(false);
		YesButton3.setVisible(false);
		NoButton3.setVisible(false);
		Label1.setVisible(false);
		SelectUsers thread1 = new SelectUsers();
		ResultSet Result = thread1.call();
		DeleteUsers thread2 = new DeleteUsers(Result);
		thread2.run();
		Users.setDisable(true);
	}
	
	@FXML
	public void say_no_button3() {
		AskPane2.setVisible(false);
		YesButton3.setVisible(false);
		NoButton3.setVisible(false);
		Label1.setVisible(false);
	}
	private class SelectUsers implements Callable<ResultSet> {

		private String make_statement_to_select() {
			return "select name from USERS";
		}
		
		private ResultSet execute_select() throws SQLException {
			String MyQuery = make_statement_to_select();
			return AddNewUsersPanelController.get_result_set_for_my_query(MyQuery);

		}
		
		@Override
		public ResultSet call() throws SQLException {
			// TODO Auto-generated method stub
				return execute_select();

		}
		
	}
	private class DeleteUsers implements Runnable {

		private ResultSet Result;
		
		private DeleteUsers(ResultSet result) {this.Result = result;}
		
		private String make_statement_to_delete(ResultSet Result) throws SQLException {
			String result2 = Result.getString("name");
			return "drop user '" + result2 + "'";
		}
		
		private void execute_delete() throws SQLException {
			
			while(Result.next()) {
			String MyUpdate = make_statement_to_delete(Result);
			AddNewUsersPanelController.get_result_set_for_my_update(MyUpdate);
			}
			AddNewUsersPanelController.get_result_set_for_my_update("delete from USERS");
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				execute_delete();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	@FXML
	public void click_delete_users () {
		are_you_sure3();
	}
	@FXML
	Button YesButton4;
	@FXML
	Button NoButton4;
	@FXML
	Button NewUsers;
	
	private void are_you_sure4() {
		AskPane2.setVisible(true);
		YesButton4.setVisible(true);
		NoButton4.setVisible(true);
		Label1.setVisible(true);
	}
	
	@FXML
	public void say_yes_button4() {
		AskPane2.setVisible(false);
		YesButton4.setVisible(false);
		NoButton4.setVisible(false);
		Label1.setVisible(false);
		DeleteNewUsers thread1 = new DeleteNewUsers();
		thread1.run();
		NewUsers.setDisable(true);
	}
	
	@FXML
	public void say_no_button4() {
		AskPane2.setVisible(false);
		YesButton4.setVisible(false);
		NoButton4.setVisible(false);
		Label1.setVisible(false);
	}
	private class DeleteNewUsers implements Runnable {

		private String make_statement() {
			return "delete from NEW_USERS";
		}
		
		private void execute() {
			 String MyUpdate = make_statement();
			 try {
				AddNewUsersPanelController.get_result_set_for_my_update(MyUpdate);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			execute();
		}
		
	}
	@FXML
	public void click_delete_new_users () {
		are_you_sure4();
	}
	
	@FXML
	Button logout;
	@FXML
	private void logoutAction() throws IOException {
		Parent login_panel_page = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
        Scene login_panel_scene = new Scene(login_panel_page);
        Stage app_stage = (Stage) logout.getScene().getWindow();
        app_stage.setTitle("Panel Administratora");
        app_stage.setScene(login_panel_scene);
        app_stage.centerOnScreen();
        app_stage.show();
	}
	
	private class Setup1 implements Callable {

		private int setup() throws SQLException {
			ResultSet Reservations = AddNewUsersPanelController
					.get_result_set_for_my_query("select count(*) from RESERVATIONS");
			Reservations.next(); 
			int result = Reservations.getInt(1);
			return result;
		}
		
		@Override
		public Object call() throws SQLException {
			return setup();
		}
		
	}
	
	private class Setup2 implements Callable {

		private int setup() throws SQLException {
			ResultSet Reservations = AddNewUsersPanelController
					.get_result_set_for_my_query("select count(*) from NEW_RESERVATIONS");
			Reservations.next(); 
			int result = Reservations.getInt(1);
			return result;
		}
		
		@Override
		public Object call() throws SQLException {
			return setup();
		}
		
	}
	private class Setup3 implements Callable {

		private int setup() throws SQLException {
			ResultSet Reservations = AddNewUsersPanelController
					.get_result_set_for_my_query("select count(*) from USERS");
			Reservations.next(); 
			int result = Reservations.getInt(1);
			return result;
		}
		
		@Override
		public Object call() throws SQLException {
			return setup();
		}
		
	}
	private class Setup4 implements Callable {

		private int setup() throws SQLException {
			ResultSet Reservations = AddNewUsersPanelController
					.get_result_set_for_my_query("select count(*) from NEW_USERS");
			Reservations.next(); 
			int result = Reservations.getInt(1);
			return result;
		}
		
		@Override
		public Object call() throws SQLException {
			return setup();
		}
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		AskPane2.setVisible(false);
		AskPane2.setStyle("-fx-background-color: white;");
		int result1 =0 ,result2=0,result3=0,result4 = 0;
		Setup1 thread1 = new Setup1();
		try {
			result1 = (int) thread1.call();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Setup2 thread2 = new Setup2();
		try {
			result2 = (int) thread2.call();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Setup3 thread3 = new Setup3();
		try {
			result3 = (int) thread3.call();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Setup4 thread4 = new Setup4();
		try {
			result4 = (int) thread4.call();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(result1==0){Reservations.setDisable(true);}
		if(result2==0){NewReservations.setDisable(true);}
		if(result3==0){Users.setDisable(true);}
		if(result4==0){NewUsers.setDisable(true);}
		
		}
		
	
}