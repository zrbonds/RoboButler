package com.github.TheDynamicBandit.NLPAgreement.action;

import org.javacord.api.event.message.MessageCreateEvent;

import com.github.TheDynamicBandit.manager.EventManager;

/**
 * The executable interface applies to any class that can execute a command based on a message create event
 * @author TheDynamicBandit
 *
 */
public interface Executable {

	/**
	 * Executes the activity that the action represents
	 * @param event the message event that triggered the action
	 */
	public void execute(MessageCreateEvent event, EventManager manager);
}
