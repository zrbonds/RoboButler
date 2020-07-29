package com.github.TheDynamicBandit.robobutler;

import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import com.github.TheDynamicBandit.NLPAgreement.InputProcessor.InputProcessor;
import com.github.TheDynamicBandit.NLPAgreement.action.Action;
import com.github.TheDynamicBandit.NLPAgreement.action.HelpAction;
import com.github.TheDynamicBandit.manager.EventManager;

/**
 * RoboButler Bartimus, at your service.
 * @author TheDynamicBandit
 *
 */
public class RoboButlerNLP {

	/** the token of the bot */
	private static String token = "NzEzOTE3MjEwNTI3MDA2NzYy.XsnNeA.w-HEuY-dDcS4UdKyfzVO5vI3bXQ";
	
	/** the event manager object */
	private static EventManager manager = EventManager.getInstance();
	
	/** the list of actions that RoboButler knows how to do */
	private static ArrayList<Action> actions;
	
	/**
	 * The main method, it logs the bot in and then creates listeners for the right reactions. 
	 * If the right reactions are found, the right message is printed into the channel.
	 * 
	 * @param args command line arguments (not used)
	 */
    public static void main(String[] args) {
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
        
        // Set up all the actions that RoboButler knows
        actions = new ArrayList<Action>();
        actions.add(new HelpAction());
        
        // Greetings Messages
        // Useful line:
        // ListenerManager<MessageCreateListener> listenerManager1 = 
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().toLowerCase().contains("good afternoon") && event.getMessageContent().toLowerCase().contains("robobutler")) {
            	String message = "Good Afternoon, Master ";
            	message += event.getMessage().getAuthor().getName();
                event.getChannel().sendMessage(message);
            }
        });
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().toLowerCase().contains("good morning") && event.getMessageContent().toLowerCase().contains("robobutler")){
            	String message = "Good Morning, Master ";
            	message += event.getMessage().getAuthor().getName();
                event.getChannel().sendMessage(message);
            }
        });
        api.addMessageCreateListener(event -> {
            if ((event.getMessageContent().toLowerCase().contains("sup") || event.getMessageContent().toLowerCase().contains("what's up")) && event.getMessageContent().toLowerCase().contains("robobutler")){
            	String message = "\'Sup\' indeed, Master ";
            	message += event.getMessage().getAuthor().getName();
                event.getChannel().sendMessage(message);
            }
        });
        
        // The main NLP command, called by !Assistant
        api.addMessageCreateListener(event -> {
        	if(event.getMessageContent().startsWith("!Assistant")) {
        		Action action = InputProcessor.processInput(event, actions);
        		action.execute(event, manager);
        	}
        });
    }
}
        