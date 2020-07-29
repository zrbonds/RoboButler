package com.github.TheDynamicBandit.event;

import java.time.LocalDateTime;

/**
 * The Timestamped interface covers any class from which
 * a date can be extracted, using the LocalDateTime format
 * @author TheDynamicBandit
 *
 */
public interface Timestamped {

	/**
	 * Gets the time associated with the class
	 * @return the time associated with the class
	 */
	public LocalDateTime getDate();
}
