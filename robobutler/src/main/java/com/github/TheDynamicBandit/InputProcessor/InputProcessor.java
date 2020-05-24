package com.github.TheDynamicBandit.InputProcessor;

import java.util.ArrayList;

import opennlp.tools.tokenize.WhitespaceTokenizer;

/**
 * The InputProcessor class uses NLP to take the input and attempts to convert it to a command. 
 * @author TheDynamicBandit
 *
 */
public class InputProcessor {
	
	/** How high the value has to be for the input processor to be confident*/
	private final static int CONFIDENCE_THRESHOLD = 5;
	
	/** The case for a calendar creation */
	public final static int CALENDAR_CASE = 1;
	
	/** The case for a creating a meeting */
	public final static int MEETING_CASE = 2;
	
	/** The case for getting command help */
	public final static int HELP_CASE = 3;
	
	/** The case for getting the list of meetings */
	public final static int LIST_CASE = 4;
	
	/** The case for getting the list of players for a meeting */
	public final static int PLAYER_CASE = 5;
	
	
	/**
	 * Processes the input String into a command.
	 * @param input the sentence that is to be implemented
	 * @return an integer value representing the use case
	 */
	public static int ProcessInput(String input) {
		//Tokenizes the input sentence and stores it in an ArrayList of String objects
		WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
		String[] tokens = tokenizer.tokenize(input);
		ArrayList<String> tokensArray = new ArrayList<String>();
		for(int i = 0; i < tokens.length; i++) {
			tokensArray.add(tokens[i]);
		}
		int caseValue = -1;
		
		////Heavy logic to attempt to discern what the user meant
		//Presence of keywords
		boolean schedule = tokensArray.contains("schedule") || tokensArray.contains("Schedule");
		boolean calendar = tokensArray.contains("calendar") || tokensArray.contains("Calendar");
		boolean meeting = tokensArray.contains("meeting") || tokensArray.contains("Meeting");
		boolean create = tokensArray.contains("create") || tokensArray.contains("Create");
		boolean make = tokensArray.contains("make") || tokensArray.contains("Make");
		boolean list = tokensArray.contains("list") || tokensArray.contains("List");
		boolean set = tokensArray.contains("set") || tokensArray.contains("Set");
		boolean help =  tokensArray.contains("help") || tokensArray.contains("Help");
		boolean commands = tokensArray.contains("command") || tokensArray.contains("Command");
		boolean get = tokensArray.contains("get") || tokensArray.contains("Get");
		boolean add = tokensArray.contains("add") || tokensArray.contains("Add");
		
		//Running total values
		int calendarValue = 0;
		int meetingValue = 0;
		int listValue = 0;
		int helpValue = 0;
		int playerValue = 0;
		
		//Increments these total values based on what keywords are found
		if(schedule) {
			calendarValue += 1;
			meetingValue += 1;
		}
		if(help || commands) {
			helpValue += 15;
		}
		if(calendar) {
			calendarValue += 5;
			meetingValue += 3;
		}
		if(meeting) {
			meetingValue += 3;
			listValue += 3;
		}
		if(list) {
			listValue += 5;
		}
		if(add || make || set) {
			meetingValue += 2;
		}
		if(create) {
			meetingValue += 3;
			calendarValue += 2;
		}
		if(get) {
			calendarValue += 2;
			listValue += 2;
		}
		
		//Set the case value equal to the value with the greatest value, or -1 if none are above the threshold
		int[] vals = {calendarValue, meetingValue, listValue, helpValue, playerValue};
		int maxVal = 0;
		for(int i = 0; i < vals.length; i++) {
			if(vals[i] > maxVal) {
				maxVal = vals[i];
			}
		}
		if(maxVal == calendarValue) {
			caseValue = CALENDAR_CASE;
		}
		else if(maxVal == meetingValue)  {
			caseValue = MEETING_CASE;
		}
		else if(maxVal == listValue) {
			caseValue = LIST_CASE;
		}
		else if(maxVal == helpValue) {
			caseValue = HELP_CASE;
		}
		else if(maxVal == playerValue) {
			caseValue = PLAYER_CASE;
		}
		if(maxVal < CONFIDENCE_THRESHOLD) {
			caseValue = -1;
		}
		
		return caseValue;
	}

}
