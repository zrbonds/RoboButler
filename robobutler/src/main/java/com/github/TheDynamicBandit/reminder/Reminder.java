package com.github.TheDynamicBandit.reminder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

/**
 * This reminder class schedules and sends a reminder to a given user
 * @author TheDynamicBandit
 *
 */
public class Reminder {

	/** A timer to keep track of the event */
	private Timer timer;
	
	private String messageToSend;
	
	/**
	 * The constructor of the user object
	 * @param time the time the user is to be messaged
	 * @param user the user to message
	 * @param channel the channel to send the message to
	 * @param messageToSend the message to send at the reminder time
	 */
	public Reminder(LocalDateTime time, User user, TextChannel channel, String messageToSend) {
		this.messageToSend = messageToSend;
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); 
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/New_York"));
		ZonedDateTime nextRun = now.withYear(time.getYear()).withMonth(time.getMonthValue()).withDayOfMonth(time.getDayOfMonth()).withHour(time.getHour()).withMinute(time.getMinute()).withSecond(0);
//		System.out.println("nextRun " + nextRun);
		Duration duration = Duration.between(now, nextRun);
//		System.out.println("Duration " + duration);
		long initalDelay = duration.getSeconds();
//		System.out.println("Initial Delay " + initalDelay);
		scheduler.schedule(new RemindTask(user, channel),
			    initalDelay,
			    TimeUnit.SECONDS);
	}
	
	
	
	/**
	 * The task to be executed by this reminder class
	 * @author TheDynamicBandit
	 *
	 */
	class RemindTask extends TimerTask {

		/** The user to message */
		private User user;
		
		/** The channel to message */
		private TextChannel channel;
		
		/** 
		 * The constructor of the RemindTask object
		 * @param user the user to message
		 * @param channel the channel to send the message in
		 */
		public RemindTask(User user, TextChannel channel) {
			if(user == null) {
				throw new IllegalArgumentException("User cannot be null.");
			}
			this.user = user;
			this.channel = channel;
		}
		
		/**
		 * Sends a user a message to the channel
		 */
		@Override
		public void run() {
			String id = "" + user.getId();
			String message = "<@" + id + "> " + messageToSend;
			channel.sendMessage(message);
			timer.cancel();
		}
	}
}
