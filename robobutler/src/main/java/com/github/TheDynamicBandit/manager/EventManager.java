package com.github.TheDynamicBandit.manager;

import java.util.ArrayList;

import com.github.TheDynamicBandit.event.Event;


/**
 * The EventManager object keeps track of the events added to RoboButler's schedule.
 * @author TheDynamicBandit
 *
 */
public class EventManager {

	/** The list of stored events */
	private ArrayList<Event> events; 
	
	/** The single instance of the meeting manager object,  following the singleton format*/
	private static EventManager instance;
	
	/**
	 * The constructor, singleton pattern
	 */
	private EventManager() {
		if(events == null) {
			events = new ArrayList<Event>();
		}
	}
	
	/**
	 * gets the static instance of the GreatArchive
	 * @return the static instance of the GreatArchive
	 */
	public static EventManager getInstance() {
		if(instance == null) {
			instance = new EventManager();
		}
		return instance;
	}
	
	/**
	 * Gets a meeting from the list of meetings by its id number
	 * @param code the id of the desired meeting
	 * @return the Meeting with the matching id
	 */
	public Event getMeetingById(int code) {
		for(int i = 0; i < events.size(); i++) {
			if(events.get(i).getId() == code) {
				return events.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Adds a meeting to Manager
	 * @param meeting the meeting to be added
	 * @return true if the meeting is added successfully, false otherwise
	 */
	public boolean addMeetingToManager(Event event) {
		if(event == null) {
			System.out.println("Meeting cannot be null");
			return false;
		}
		if(events.contains(event)) {
			System.out.println("This meeting is already in the list");
			return false;
		}
		return events.add(event);
	}
	
	/**
	 * Removes the meeting from the Manager
	 * @param meeting the meeting to be removed
	 * @return true if the meeting is removed successfully, false otherwise
	 */
	public boolean removeMeetingFromManager(Event event) {
		return events.remove(event);
	}
	
	/**
	 * Flushes the meeting data and resets the meeting ID counter
	 */
	public void flushMeetingData() {
		events = new ArrayList<Event>();
		Event.setIdCounter(0);
	}
	
	/**
	 * Gets an ArrayList of meeting objects contained within the manager
	 * @return the list of meetings contained within the manager
	 */
	public ArrayList<Event> getEvents(){
		return events;
	}
	
	
	/**
	 * Gets an ArrayList of meetings on the selected month
	 * @param monthVal the integer value representing the month
	 * @return an ArrayList of Meeting objects with the selected month
	 */
	public ArrayList<Event> getEventsByMonth(int monthVal){
		ArrayList<Event> monthEvents = new ArrayList<Event>();
		for(int i = 0; i < events.size(); i++) {
			if(events.get(i).getDate().getMonthValue() == monthVal) {
				monthEvents.add(events.get(i));
			}
		}
		return monthEvents;
	}
}

