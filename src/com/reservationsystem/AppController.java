package com.reservationsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.mysql.jdbc.Statement;


public class AppController implements Initializable{
	@FXML 
	public TextField username;
	@FXML 
	public TextField password;
	@FXML 
	public TextField wyswietlacz;
    
	@FXML 
	public DatePicker DatePick;
	
	public void wpisywanie() throws SQLException
	{
		Connection con = Main.getConnection();
		Statement myStatement = (Statement) con.createStatement();
		System.out.println("BBYY");
		String userName = "piewew3w2";
		String cos= "CREATE USER '" + userName + "'@'%' IDENTIFIED BY 'password';";
		String cos2 = "GRANT SELECT ON ROOMS_RESERVATION.* TO '" + userName + "'@'%';";
		myStatement.execute(cos);
		myStatement.execute(cos2);
		System.out.println("HELLO");
	
	}
	
	
	public class databaseAccess implements Runnable {

		@Override
		public void run() {
			Connection con = Main.getConnection(); 
			java.sql.Statement st = null;
			try {
				st = con.createStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			String sql = ("SELECT * FROM ROOMS ;");
			ResultSet rs = null ;
			try {
				rs = st.executeQuery(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				 while (rs.next()) {
				System.out.print(rs.getString("Building"));
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
	public void actionnn()
	{
		LocalDate localDate = DatePick.getValue();
		Date date = Date.valueOf(localDate);	
		System.out.println(date);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		username.setText("HEEEEEJ");
		DatePick.setValue(LocalDate.now().plusDays(1));
		final Callback<DatePicker, DateCell> dayCellFactory = 
	            new Callback<DatePicker, DateCell>() {
	                @Override
	                public DateCell call(final DatePicker datePicker) {
	                    return new DateCell() {
	                        @Override
	                        public void updateItem(LocalDate item, boolean empty) {
	                            super.updateItem(item, empty);
	                           
	                            if (item.isBefore(DatePick.getValue()) || item.isAfter(DatePick.getValue().plusDays(15))) {
	                                    setDisable(true);
	                                    setStyle("-fx-background-color: #ffc0cb;");
	                            }   
	                    }
	                };
	            }
	        };
	        DatePick.setDayCellFactory(dayCellFactory);
	        
		
	}
}
	

