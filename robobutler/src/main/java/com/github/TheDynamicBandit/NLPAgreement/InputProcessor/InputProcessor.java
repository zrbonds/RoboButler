package com.github.TheDynamicBandit.NLPAgreement.InputProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;

import com.github.TheDynamicBandit.NLPAgreement.action.Action;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;

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
		String[] tokens = whitespaceTokenize(sentence);
		String[] lemmas = tokensToLemmas(tokens);
		
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
	 * Uses the whitespace tokenizer to split a sentence up into tokens
	 * @param sentence the sentence to split up
	 * @return the array of tokens
	 */
	public static String[] whitespaceTokenize(String sentence) {
		WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
		return tokenizer.tokenize(sentence);
	}
	
	/**
	 * Takes the whitespace tokenized array and converts it to the word lemmas
	 * @param tokens the whitespace tokenized sentence
	 * @return an array of lemmas
	 */
	public static String[] tokensToLemmas(String[] tokens) {
		try {
			InputStream posModelIn = new FileInputStream("en-pos-maxent.bin");
			try {
				POSModel posModel = new POSModel(posModelIn);
				POSTaggerME posTagger = new POSTaggerME(posModel);
				String[] tags = posTagger.tag(tokens);
				InputStream dictLemmatizer = new FileInputStream("en-lemmatizer.txt");
				DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
				String[] lemmas = lemmatizer.lemmatize(tokens, tags);
				return lemmas;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
