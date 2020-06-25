package com.github.TheDynamicBandit.NLPAgreement.InputProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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

	public static Action processInput(MessageCreateEvent event) {
		
		return null;
	}
	
	public static String[] whitespaceTokenize(String sentence) {
		WhitespaceTokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
		return tokenizer.tokenize(sentence);
	}
	
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
