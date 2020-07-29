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
	
	/** The single instance of the event manager object,  following the singleton format*/
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
	 * Gets a event from the list of events by its id number
	 * @param code the id of the desired event
	 * @return the event with the matching id
	 */
	public Event getEventById(int code) {
		for(int i = 0; i < events.size(); i++) {
			if(events.get(i).getId() == code) {
				return events.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Adds a event to Manager
	 * @param event the event to be added
	 * @return true if the event is added successfully, false otherwise
	 */
	public boolean addEventToManager(Event event) {
		if(event == null) {
			System.out.println("event cannot be null");
			return false;
		}
		if(events.contains(event)) {
			System.out.println("This event is already in the list");
			return false;
		}
		return events.add(event);
	}
	
	/**
	 * Removes the event from the Manager
	 * @param event the event to be removed
	 * @return true if the event is removed successfully, false otherwise
	 */
	public boolean removeEventFromManager(Event event) {
		return events.remove(event);
	}
	
	/**
	 * Flushes the event data and resets the event ID counter
	 */
	public void flushEventData() {
		events = new ArrayList<Event>();
		Event.setIdCounter(0);
	}
	
	/**
	 * Gets an ArrayList of event objects contained within the manager
	 * @return the list of events contained within the manager
	 */
	public ArrayList<Event> getEvents(){
		return events;
	}
	
	
	/**
	 * Gets an ArrayList of events on the selected month
	 * @param monthVal the integer value representing the month
	 * @return an ArrayList of event objects with the selected month
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

