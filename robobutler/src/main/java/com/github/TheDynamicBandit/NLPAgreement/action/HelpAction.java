package com.github.TheDynamicBandit.NLPAgreement.action;

import org.javacord.api.event.message.MessageCreateEvent;

/**
 * The Help action lists all of the 
 * @author zrbonds
 *
 */
public class HelpAction extends Action {

	/** The weight of the help word */
	public static double helpWeight = 3.0;
	
	/** The weight of the show word */
	public static double showWeight = 1.0;
	
	/** The weight of the command word */
	public static double commandWeight = 5.0;
	
	/** The weight of the do word */
	public static double doWeight = 2.0;
	
	/**
	 * The constructor for the HelpAction, adds in all of its keywords
	 */
	public HelpAction() {
		super();
		getKeywords().add(new Keyword(helpWeight, "help"));
		getKeywords().add(new Keyword(showWeight, "show"));
		getKeywords().add(new Keyword(commandWeight,"command"));
		getKeywords().add(new Keyword(doWeight,"do"));
	}
	
	@Override
	public void execute(MessageCreateEvent event) {
		event.getChannel().sendMessage("Here's what I can do");
	}

}
