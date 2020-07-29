package com.github.TheDynamicBandit.robobutler;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import com.github.TheDynamicBandit.event.Event;
import com.github.TheDynamicBandit.manager.EventManager;
import com.github.TheDynamicBandit.reminder.Reminder;

/**
 * RoboButler Bartimus, at your service.
 * @author TheDynamicBandit
 *
 */
public class RoboButler {

	/** the token of the bot */
	private static String token = "NzEzOTE3MjEwNTI3MDA2NzYy.XsnNeA.w-HEuY-dDcS4UdKyfzVO5vI3bXQ";
	
	/** the event manager object */
	private static EventManager manager = EventManager.getInstance();
	
	/**
	 * The main method, it logs the bot in and then creates listeners for the right reactions. 
	 * If the right reactions are found, the right message is printed into the channel.
	 * 
	 * @param args command line arguments (not used)
	 */
    public static void main(String[] args) {
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        
        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
        
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
        
        // Reminder Functionality
        // Format:
        // !Remind MM/DD/YYYY HH:MM description
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().startsWith("!Remind")) {
            	String[] arguments = event.getMessageContent().split(" ");
            	//Create and add the meeting
            	String[] dates = arguments[1].split("/");
            	int month = Integer.parseInt(dates[0]);
            	int day = Integer.parseInt(dates[1]);
            	int year = Integer.parseInt(dates[2]);
            	String[] times = arguments[2].split(":");
            	int hour = Integer.parseInt(times[0]);
            	int min = Integer.parseInt(times[1]);
            	
            	//Parsing the message from the remainder of the String[] arguments
            	StringBuffer buffer = new StringBuffer();
            	for(int i = 3; i < arguments.length; i++) {
            		buffer.append(arguments[i]);
            		if(i < arguments.length - 1) {
            			buffer.append(" ");
            		}
            	}
            	String description = buffer.toString();
            	Event eventRemind = new Event(day, month, year, hour, min, description);
            	if(manager.addEventToManager(eventRemind)) {
            		String message = "Reminder created.";
            		new Reminder(eventRemind.getDate(), event.getMessage().getUserAuthor().get(), event.getChannel(), "\"" + eventRemind.getDescription() + "\"");
            		event.getChannel().sendMessage(message);
            	}
            }
        });
        
        
    }
}
