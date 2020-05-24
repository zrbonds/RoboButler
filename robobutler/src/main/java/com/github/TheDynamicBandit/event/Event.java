package com.github.TheDynamicBandit.event;

import java.time.LocalDateTime;

/**
 * The Event class defines any event that RoboButler would want to remind 
 * @author TheDynamicBandit
 *
 */
public class Event {
	
	/** The date of the meeting */
	private LocalDateTime date;
	
	/** The identification number for the meeting */
	private int id;
	
	/** The id counter for the meeting */
	public static int idcounter;
	
	/** The description of the event */
	private String description;
	
	/**
	 * The event constructor.
	 * @param day the day of the meeting
	 * @param month the month of the meeting
	 * @param year the year of the meeting
	 * @param hour the hour of the meeting
	 * @param min the min of the meeting
	 * @param description the description of the meeting
	 */
	public Event(int day, int month, int year, int hour, int min, String description) {
		date = LocalDateTime.of(year, month, day, hour, min);
		this.id = idcounter;
		idcounter++;
		if(description == null || description.equals("")) {
			this.description = "";
		}
		else{
			this.description = description;
		}
	}
	
	/**
	 * The constructor of the event object, using the LocalDateTime
	 * @param date the date the event takes place
	 * @param description a description of the event
	 */
	public Event(LocalDateTime date, String description) {
		if(description == null || description.equals("")) {
			this.description = "";
		}
		this.date = date;
	}

	/**
	 * Gets the date of the event, with LocalDateTime class
	 * @return the date of the event, with LocalDateTime class
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Gets the description of the event
	 * @return the description of the event
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Gets the id for the event
	 * @return the id for the event
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Resets the event counter to the specified value
	 * @param counter the specified value to set the counter to
	 */
	public static void setIdCounter(int counter) {
		idcounter = counter;
	}
}
