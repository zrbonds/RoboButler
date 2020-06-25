package com.github.TheDynamicBandit.NLPAgreement.action;

/**
 * The Keyword class represents a keyword that the InputProcessor looks for along with a weight that 
 * represents how much that keyword indicates that action was intended
 * @author TheDynamicBandit
 *
 */
public class Keyword {

	/** How much the presence of the keyword indicates the associated action */
	private double weight;
	
	/** The keyword (lemmatized) */
	private String lemma;
	
	/**
	 * The constructor of the Keyword object.
	 * @param weight how much the presence of the keyword indicates the associated action
	 * @param lemma the keyword, lemmatized
	 */
	public Keyword(double weight, String lemma) {
		this.weight = weight;
		this.lemma = lemma;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the lemma
	 */
	public String getLemma() {
		return lemma;
	}

	/**
	 * @param lemma the lemma to set
	 */
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
	
	
}
