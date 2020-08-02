package com.github.TheDynamicBandit.NLPAgreement.action;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


import org.javacord.api.event.message.MessageCreateEvent;

import com.github.TheDynamicBandit.NLPAgreement.CoreNLPUtil.CoreNLPUtil;
import com.github.TheDynamicBandit.event.Event;
import com.github.TheDynamicBandit.manager.EventManager;
import com.github.TheDynamicBandit.reminder.Reminder;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.time.TimeAnnotations;


/**
 * The AddEventAction adds an event to the manager with the time specified in
 * the message to the calendar
 * 
 * @author TheDynamicBandit
 *
 */
public class AddEventAction extends Action {

	/** The weight of the add word */
	private static double addWeight = 4.0;

	/** The weight of the event word */
	private static double eventWeight = 3.0;

	/** The weight of the remind word */
	private static double remindWeight = 8.0;

	/** The weight of the reminder word */
	private static double reminderWeight = 8.0;

	/** The weight of the set word */
	private static double setWeight = 3.0;
	
	/** The weight of the alert word */
	private static double alertWeight = 5.0;

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
		getKeywords().add(new Keyword(alertWeight, "alert"));
	}
	
	/**
	 * A private method for handling issues in the code
	 */
	public void errorHandler(MessageCreateEvent event, String errorDescription) {
		event.getChannel().sendMessage("I'm sorry Master " + event.getMessageAuthor().getName()
				+ ", something has gone horribly, horribly wrong.");
		System.out.println(errorDescription);
	}

	@Override
	public void execute(MessageCreateEvent event, EventManager manager) {
		//// Lets build a date
		LocalDateTime reminderDate = LocalDateTime.now();
		// First we need to use CoreNLP tools to extract anything 
		CoreDocument document = CoreNLPUtil.getDateTimeDoc(event.getMessageContent());
		List<CoreEntityMention> entityList = document.entityMentions();
		// Build the date
		boolean foundDate = false;
		LocalTime time = null;
		for(CoreEntityMention cem : entityList) {
			if(cem.entityType().equals("DATE")) {
				String value = cem.coreMap().get(TimeAnnotations.TimexAnnotation.class).value();
				if(value != null && value.charAt(0) == 'X') {
					value = LocalDate.now().getYear() + value.substring(4);
					
				}
				reminderDate = LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				foundDate = true;
			}
			if(cem.entityType().equals("TIME")) {
				// The substring(1) is to get rid of the T
				time = LocalTime.parse(cem.coreMap().get(TimeAnnotations.TimexAnnotation.class).value().substring(1), DateTimeFormatter.ISO_LOCAL_TIME);
			}
		}
		if(!foundDate) {
			if(time != null) {
				reminderDate = LocalDateTime.of(LocalDate.now(), time);
			}
			else {
				event.getChannel().sendMessage("I'm afraid you'll have to be more specific about the time.");
				return;
			}
		}
		
		// Now we're done parsing the date
		// Create a new Event
		Event reminderEvent = new Event(reminderDate, event.getMessageContent());
		// Add it to the manager
		manager.addEventToManager(reminderEvent);
		// Create a reminder, using a substring of the message
		new Reminder(reminderDate, event.getMessage().getUserAuthor().get(), event.getChannel(), event.getMessageContent().substring(12));
		
		// Let the user know the event has been added
		event.getChannel().sendMessage("I've added it to your schedule, Master " + event.getMessage().getUserAuthor().get().getName() + ".");
	}

	
}
