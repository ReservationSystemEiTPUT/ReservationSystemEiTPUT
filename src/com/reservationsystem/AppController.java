package com.reservationsystem;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AppController {
	@FXML 
	public TextField nazwaUzytkownika;
	@FXML 
	public TextField wyswietlacz;
	
	public void loginClicked() 
	{
	 System.out.println("Helloo");
	}
	
	public void wpisywanie()
	{
	wyswietlacz.setText(nazwaUzytkownika.getText());
	}
}