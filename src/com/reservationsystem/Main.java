package com.reservationsystem;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		
	    Parent root = FXMLLoader.load(getClass().getResource("LoginPanel.fxml"));
	    Scene scenka = new Scene(root);
	    primaryStage = new Stage();
	    primaryStage.initStyle(StageStyle.UNDECORATED);
	    primaryStage.setScene(scenka);
        primaryStage.show();
        primaryStage.setResizable(false);
       
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		System.out.println("HELLO");
	}
}
