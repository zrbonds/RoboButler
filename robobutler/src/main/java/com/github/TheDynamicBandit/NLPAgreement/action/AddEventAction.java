package com.github.TheDynamicBandit.NLPAgreement.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javacord.api.event.message.MessageCreateEvent;

import com.github.TheDynamicBandit.event.Event;
import com.github.TheDynamicBandit.manager.EventManager;
import com.github.TheDynamicBandit.reminder.Reminder;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

/**
 * The AddEventAction adds an event to the manager with the time specified in
 * the message to the calendar
 * 
 * @author TheDynamicBandit
 *
 */
public class AddEventAction extends Action {

	/** The weight of the add word */
	public static double addWeight = 4.0;

	/** The weight of the event word */
	public static double eventWeight = 3.0;

	/** The weight of the remind word */
	public static double remindWeight = 5.0;

	/** The weight of the reminder word */
	public static double reminderWeight = 5.0;

	/** The weight of the set word */
	public static double setWeight = 3.0;

	/**
	 * The constructor for the HelpAction, adds in all of its keywords
	 */
	public AddEventAction() {
		super();
		getKeywords().add(new Keyword(addWeight, "add"));
		getKeywords().add(new Keyword(eventWeight, "event"));
		getKeywords().add(new Keyword(remindWeight, "remind"));
		getKeywords().add(new Keyword(reminderWeight, "reminder"));
		getKeywords().add(new Keyword(setWeight, "set"));
	}
	
	/**
	 * A private method for handling issues in the code
	 */
	private void errorHandler(MessageCreateEvent event, String errorDescription) {
		event.getChannel().sendMessage("I'm sorry Master " + event.getMessageAuthor().getName()
				+ ", something has gone horribly, horribly wrong.");
		System.out.println(errorDescription);
	}

	@Override
	public void execute(MessageCreateEvent event, EventManager manager) {
		// Try to find a date
		String[] dates = dateExtractor(event.getMessageContent());
		if(dates == null) {
			errorHandler(event, "OpenNLP critical failure");
			return;
		}
		// Resolve any date conflicts or lack of dates
		if(dates.length == 0) {
			errorHandler(event, "No dates returned by dateExtractor");
			return;
		}
		// Parse the date into a local date time
		LocalDateTime reminderDate = null;
		try {
			reminderDate = LocalDateTime.parse(dates[0]);
		}
		catch(DateTimeParseException e) {
			errorHandler(event, "Date returned by dateExtractor not successfully parsed");
			return;
		}
		if(reminderDate == null) {
			errorHandler(event, "Date returned by dateExtractor not successfully parsed");
			return;
		}
		// Try to get a time 
		String[] times = timeExtractor(event.getMessageContent());
		if(times != null && times.length != 0) {
			LocalTime time = LocalTime.parse(times[0]);
			reminderDate = LocalDateTime.of(reminderDate.toLocalDate(), time);
		}
		
		// Now we're done parsing the date
		// Create a new Event
		Event reminderEvent = new Event(reminderDate, event.getMessageContent());
		// Add it to the manager
		manager.addEventToManager(reminderEvent);
		// Create a reminder
		new Reminder(reminderDate, event.getMessage().getUserAuthor().get(), event.getChannel(), event.getMessageContent());
	}

	/**
	 * A helper method to extract the date from the Message String. Made temporarily public for testing purposes.
	 * 
	 * @param message The message from which to extract a date
	 * @return The LocalDateTime associated with the message
	 * Citation: https://stackoverflow.com/questions/27182040/how-to-detect-dates-with-opennlp
	 */
	public String[] dateExtractor(String message) {
		// Try to build the models, let us know if something goes fudgy
		TokenizerModel model1 = null;
		TokenNameFinderModel model2 = null;
		InputStream modelIn1 = null;
		InputStream modelIn2 = null;
		try {
			modelIn1 = new FileInputStream("src/en-ner-date.bin");
			modelIn2 = new FileInputStream("src/en-token.bin");
			model1 = new TokenizerModel(modelIn2);
			model2 = new TokenNameFinderModel(modelIn1);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (modelIn1 != null) {
				try {
					modelIn1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (modelIn2 != null) {
				try {
					modelIn2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// If nothing goes horribly, horribly wrong, set up our tokenizer and nameFinder
		Tokenizer tokenizer = new TokenizerME(model1);
		NameFinderME nameFinder = new NameFinderME(model2);

		// Get our tokens
		String tokens[] = tokenizer.tokenize(message);

		// Send it to an array of Span objects
		Span nameSpans[] = nameFinder.find(tokens);

		// And convert those spans to strings
		String[] dates = Span.spansToStrings(nameSpans, tokens);
		return dates;
	}
	
	/**
	 * A helper method to extract the time from the Message String. Made temporarily public for testing purposes.
	 * 
	 * @param message The message from which to extract a time
	 * @return The String[] of possible times
	 */
	public String[] timeExtractor(String message) {
		// Try to build the models, let us know if something goes fudgy
		TokenizerModel tokenModel = null;
		TokenNameFinderModel nameModel = null;
		InputStream modelIn1 = null;
		InputStream modelIn2 = null;
		try {
			modelIn1 = new FileInputStream("src/en-ner-time.bin");
			modelIn2 = new FileInputStream("src/en-token.bin");
			tokenModel = new TokenizerModel(modelIn2);
			nameModel = new TokenNameFinderModel(modelIn1);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (modelIn1 != null) {
				try {
					modelIn1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (modelIn2 != null) {
				try {
					modelIn2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// If nothing goes horribly, horribly wrong, set up our tokenizer and nameFinder
		Tokenizer tokenizer = new TokenizerME(tokenModel);
		NameFinderME nameFinder = new NameFinderME(nameModel);

		// Get our tokens
		String tokens[] = tokenizer.tokenize(message);

		// Send it to an array of Span objects
		Span nameSpans[] = nameFinder.find(tokens);

		// And convert those spans to strings
		String[] times = Span.spansToStrings(nameSpans, tokens);
		return times;
	}
}
