package com.github.TheDynamicBandit.NLPAgreement.action;

import static org.junit.Assert.*;

import org.junit.Test;

public class AddEventActionTest {

	@Test
	public void testDateExtractor() {
		AddEventAction e = new AddEventAction();
		String[] dates = e.dateExtractor("Create a reminder 7/31/2020");
		assertTrue(dates != null);
	}
	
	@Test
	public void testTimeExtractor() {
		AddEventAction e = new AddEventAction();
		String[] times = e.timeExtractor("My birthday is 10 minutes from now. The time is midnight. Set a reminder for January 1st, at 11:30am");
		assertTrue(times != null);
	}

}
