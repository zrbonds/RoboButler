package com.github.TheDynamicBandit.NLPAgreement.InputProcessor;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputProcessorTest {

	private String sentence = "Get me a schedule of meetings.";
	private String[] tokens = {"Get", "me", "a", "schedule", "of", "meetings"};
	
	@Test
	public void testWhitespaceTokenize() {
		String[] tokens = InputProcessor.whitespaceTokenize(sentence);
		
	}

	@Test
	public void testTokensToLemmas() {
		fail("Not yet implemented");
	}

}
