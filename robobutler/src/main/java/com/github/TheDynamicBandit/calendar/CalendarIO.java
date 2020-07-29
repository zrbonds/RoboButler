package com.github.TheDynamicBandit.calendar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.github.TheDynamicBandit.event.Timestamped;;

/**
 * This class handles the creation of a calendar swing panel and saves it to a jpg file
 * on local storage. 
 * @author TheDynamicBandit
 *
 */
public class CalendarIO {
	
	/** The name of the calendar */
	private static String calendarName = "Event Calendar";
	
	/** The name of the calendar file */
	private static String calendarFileName = "calendar_image.png";
	
	/** The number of days in a week */
	private static int weekSize = 7;
	
	/** The number of pixels in the calendar */
	private static int calendarSize = 400;
	
	/** The maximum number of date entries for a 6x7 grid */
	private static int maxDates = 35;
	

	/**
	 * The method that takes a list of meetings and converts it to an image
	 * @param meetings the ArrayList of meetings to be made the calendar
	 * @return the name of the image file
	 */
	public static String createCalendarJpg(ArrayList<? extends Timestamped> meetings, int month, int year) {
		createCalendar(meetings, month, year);
		return calendarFileName;
	}
	
	/**
	 * A helper method that creates a BufferedImage of the calendar with the given specifications
	 * @param meetings the arrayList of meetings to add to the calendar
	 * @param month the month of the calendar
	 * @param year the year of the calendar
	 * @return the BufferedImage of the calendar
	 */
	private static void createCalendar(ArrayList<? extends Timestamped> meetings, int month, int year) {
		LocalDate date = LocalDate.of(year, month, 1);
		int firstDay = date.getDayOfWeek().getValue();
		JFrame frame = new JFrame(calendarName + " - " + date.getMonth().toString());
		frame.setSize(calendarSize, calendarSize);
		
		//We need to ensure the grid is created with the right size to avoid date overflow
		if(firstDay + date.lengthOfMonth() > maxDates) {
			frame.setLayout(new GridLayout(7, 7));
		}
		else {
			frame.setLayout(new GridLayout(6, 7));
		}
		
		//Adds the weekday labels to the frame
		String[] dayNameLabels = {"SU", "M", "T", "W", "TH", "F", "S"};
		for(int i = 0; i < weekSize; i++) {
			JLabel label = new JLabel(dayNameLabels[i]);
			label.setFont(new java.awt.Font("Arial", Font.ITALIC, 15));
			frame.add(label);
		}
		
		//Adds the blank days before the first day of the month
		switch(firstDay) {
		case 7:
			break;
		default:
			for(int i = 0; i < firstDay; i++) {
				JLabel label = new JLabel(" ");
				frame.add(label);
			}
		}
		
		//Gets the dates of the Meetings
		ArrayList<Integer> days = new ArrayList<Integer>();
		for(int i = 0; i < meetings.size(); i++) {
			days.add((Integer)meetings.get(i).getDate().getDayOfMonth());
		}
		
		//Adds the calendar dates to the image, with an indicator for meeting dates
		for(int i = 1; i <= date.lengthOfMonth(); i++) {
			JLabel label = new JLabel("" + i);
			if(days.contains((Integer)i)) {
				label.setForeground(Color.GREEN);
			}
			frame.add(label);
		}
		frame.setVisible(true);
		
		// Create a buffered image 
		BufferedImage buffImage = new BufferedImage(calendarSize, calendarSize, BufferedImage.TYPE_INT_ARGB);
	    
	    // Draw the image on to the buffered image
	    Graphics bGr = buffImage.createGraphics();
        frame.printAll(bGr);
        bGr.dispose();
        
        //Export the image to the file with name stored in calendarFileName
        String outputName = calendarFileName;
		File outputFile = new File(outputName);
		try {
			ImageIO.write(buffImage, "png", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		frame.dispose();
	}
}
