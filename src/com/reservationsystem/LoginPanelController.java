package com.reservationsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;

import java.sql.DriverManager;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import javafx.scene.Node;


public class LoginPanelController {
	
	public void handleCreateAccountButton(ActionEvent event) throws IOException {
		Parent CreateAccountPannel = FXMLLoader.load(getClass().getResource("App.fxml"));
	    Scene create_account_scene = new Scene(CreateAccountPannel);
	    Stage app_stage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
	    app_stage.setScene(create_account_scene);
	    app_stage.show();
	}
	
	public void helloooo () {
		System.out.print("HEEEL");
	}

	
		
}
	
