package com.reservationsystem;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;
import com.reservationsystem.LoginPanelController.databaseAccess;


public class AppController {
	@FXML 
	public TextField username;
	@FXML 
	public TextField password;
	@FXML 
	public TextField wyswietlacz;
    
	public void wpisywanie() throws SQLException
	{
		 databaseAccess thread1 = new databaseAccess();
		    (new Thread(thread1)).start();
	
	}
	
	
	public class databaseAccess implements Runnable {

		@Override
		public void run() {
			Connection con = Main.getConnection();
			java.sql.Statement st = null;
			try {
				st = con.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
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
}
	

