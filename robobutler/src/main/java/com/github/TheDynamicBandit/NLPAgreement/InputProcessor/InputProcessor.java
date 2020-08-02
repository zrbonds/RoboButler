package com.github.TheDynamicBandit.NLPAgreement.InputProcessor;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

import com.github.TheDynamicBandit.NLPAgreement.CoreNLPUtil.CoreNLPUtil;
import com.github.TheDynamicBandit.NLPAgreement.action.Action;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;

/**
 * The input processor converts the event message to an Action object
 * @author TheDynamicBandit
 *
 */
public class InputProcessor {

	/**
	 * The main input processor command
	 * @param event the event that triggered RoboButler's response
	 * @return the action object with the highest confidence value
	 */
	public static Action processInput(MessageCreateEvent event, ArrayList<Action> actions) {
		// If there are no provided actions, there's no action to return
		if(actions.size() == 0) {
			return null;
		}
		
		// Tokenize the event command
		String sentence = event.getMessageContent();
		String[] lemmas = sentenceToLemmas(sentence);
		
		// Update the confidence values of the actions
		for(Action action: actions) {
			action.updateConfidenceValue(lemmas);
		}
		
		// Gets the action with the highest confidence value
		Action bestAction = actions.get(0);
		for(Action action: actions) {
			if(action.getConfidenceValue() > bestAction.getConfidenceValue()) {
				bestAction = action;
			}
		}
		
		// Resets the confidence value to 0 for further use
		for(Action action : actions) {
			action.resetConfidenceValue();
		}
		return bestAction;
	}
	
	/**
	 * Converts a sentence to lemmas
	 * @param sentence the sentence to be lemmatized
	 * @return an array of lemmas
	 */
	public static String[] sentenceToLemmas(String sentence) {
		CoreDocument doc = CoreNLPUtil.getLemmaDoc(sentence);
		String[] lemmas = new String[doc.tokens().size()];
		int placementIndex = 0;
		for(CoreLabel label : doc.tokens()) {
			lemmas[placementIndex++] = label.lemma();
		}
		return lemmas;
	}
}
