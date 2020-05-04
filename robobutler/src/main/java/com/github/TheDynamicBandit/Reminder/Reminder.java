package com.github.TheDynamicBandit.Reminder;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * A reminder object for the RoboButler class. 
 * @author TheDynamicBandit
 *
 */
public class Reminder {
	
	/** the Timer object, keeps track of elapsed time */
	private Timer timer;
	
	public Reminder(int time, TimeUnit unit) {
		timer = new Timer();
		timer.schedule(new ReminderTask(), time);
	}

	/**
	 * The TimerTask that sends
	 * @author TheDynamicBandit
	 *
	 */
	class ReminderTask extends TimerTask {

		@Override
		public void run() {
			//TODO activity
            timer.cancel(); //Terminate the timer thread
		}
    
    }
}
