package com.github.TheDynamicBandit.robobutler;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

public class RoboButler {

	/** the token of the bot */
	private static String token = "NjkwNDExNzc0NzM3OTA3NzU0.XnRC8w.UD7xu7LfKcUtrIsPnMdg0wKiEjE";
	
	/**
	 * The main method, it logs the bot in and then creates listeners for the right reactions. 
	 * If the right reactions are found, the right message is printed into the channel.
	 * 
	 * @param args command line arguments (not used)
	 */
    public static void main(String[] args) {
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
        
        // Greetings Messages
        ListenerManager<MessageCreateListener> listenerManager1 = api.addMessageCreateListener(event -> {
            if (event.getMessageContent().toLowerCase().contains("good afternoon") && event.getMessageContent().toLowerCase().contains("robobutler")) {
            	String message = "Good Afternoon, Master ";
            	message += event.getMessage().getAuthor().getName();
                event.getChannel().sendMessage(message);
            }
        });
        ListenerManager<MessageCreateListener> listenerManager2 = api.addMessageCreateListener(event -> {
            if (event.getMessageContent().toLowerCase().contains("good morning") && event.getMessageContent().toLowerCase().contains("robobutler")){
            	String message = "Good Morning, Master ";
            	message += event.getMessage().getAuthor().getName();
                event.getChannel().sendMessage(message);
            }
        });
        ListenerManager<MessageCreateListener> listenerManager3 = api.addMessageCreateListener(event -> {
            if ((event.getMessageContent().toLowerCase().contains("sup") || event.getMessageContent().toLowerCase().contains("what's up")) && event.getMessageContent().toLowerCase().contains("robobutler")){
            	String message = "\'Sup\' indeed, Master ";
            	message += event.getMessage().getAuthor().getName();
                event.getChannel().sendMessage(message);
            }
        });
        
    }
}
