package com.reservationsystem;

import java.sql.Date;

public class Reservation {
	private String date;
	private String day;
	private String hour;
	private String building;
	private String room;
	
	public Reservation () {
		this.setDate("");
		this.setDay("");
		this.setHour("");
		this.setBuilding("");
		this.setRoom("");
	}
	
	public Reservation(String date, String day, String hour, String building, String room)
	{
		this.setDate(date);
		this.setDay(day);
		this.setHour(hour);
		this.setBuilding(building);
		this.setRoom(room);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
	
	
}
