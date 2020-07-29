package com.github.TheDynamicBandit.NLPAgreement.action;

import static org.junit.Assert.*;

import org.junit.Test;

public class AddEventActionTest {

	@Test
	public void testDateExtractor() {
		AddEventAction e = new AddEventAction();
		String[] dates = e.dateExtractor("January 21st, 2001 is my birthday");
		assertTrue(dates != null);
	}

}
