package com.github.TheDynamicBandit.NLPAgreement.action;

import java.util.ArrayList;

/**
 * The Action abstract class represents any Action that could be taken by a
 * Discord Bot
 * @author TheDynamicBandit
 *
 */
public abstract class Action implements Executable {

	/** The keywords associated with the action */
	ArrayList<Keyword> keywords;
	
	/** The degree to which an action is confident about its execution */
	double confidenceValue;
	
	/**
	 * The constructor for an action object
	 */
	public Action() {
		keywords = new ArrayList<Keyword>();
		resetConfidenceValue();
	}
	
	
	/**
	 * Gets the list of keywords
	 * @return the list of keywords
	 */
	public ArrayList<Keyword> getKeywords() {
		return keywords;
	}
	
	/**
	 * A value to update the confidence value in the string
	 * @param lemmas the lemmas of the 
	 */
	public void updateConfidenceValue(String[] lemmas) {
		// Create an ArrayBasedList for easier access
		ArrayList<String> lemmaList = new ArrayList<String>();
		for(int i = 0; i < lemmas.length; i++) {
			lemmaList.add(lemmas[i]);
		}
		// For each keyword in the list
		for(Keyword keyword : keywords) {
			// If the lemma list contains the value
			if(lemmaList.contains(keyword.getLemma())) {
				// Increment the confidence value
				confidenceValue += keyword.getWeight();
			}
		}
	}
	
	/**
	 * Resets the confidence value to 0
	 */
	public void resetConfidenceValue() {
		confidenceValue = 0;
	}
	
	/**
	 * Returns the confidence value
	 * @return the confidence value 
	 */
	public double getConfidenceValue() {
		return confidenceValue;
	}
}
