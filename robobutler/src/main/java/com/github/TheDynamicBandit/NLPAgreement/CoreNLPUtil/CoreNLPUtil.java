package com.github.TheDynamicBandit.NLPAgreement.CoreNLPUtil;

import java.util.Properties;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.time.TimeAnnotator;

/**
 * The CoreNLPUtil simplifies commonly used methods and
 * processes in the CoreNLP library
 * @author TheDynamicBandit
 *
 */
public class CoreNLPUtil {

	public static CoreDocument getDateTimeDoc(String message) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		CoreDocument document = pipeline.processToCoreDocument(message);
		Properties cleanProps = new Properties();
		
		// Add any properties we may need
		
		pipeline.addAnnotator(new TimeAnnotator("sutime", cleanProps));
		pipeline.annotate(document);
		return document;
	}
	
	public static CoreDocument getLemmaDoc(String message) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		CoreDocument document = pipeline.processToCoreDocument(message);
		
		// Add any properties we may need
		pipeline.annotate(document);
		return document;
	}
}
