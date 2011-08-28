package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import java.util.HashSet;

public class RecommendationSet {

	/**
	 * The recommendations that make up this set.
	 */
	private HashSet<Recommendation> recommendations;

	/**
	 * The factor/s used to generate the recommendation
	 */
	private Factor factor;

	/**
	 * The measure/s used to generate the recommendation
	 */
	private Measure measure;

	public RecommendationSet(Factor currFactor, Measure currMeasure) {
		this.setRecommendations(new HashSet<Recommendation>(), currFactor, currMeasure);
	}

	public void add(Recommendation currRecommendation) {
		this.recommendations.add(currRecommendation);
	}

	public Factor getFactor() {
		return this.factor;
	}

	public Measure getMeasure() {
		return this.measure;
	}

	public HashSet<Recommendation> getRecommendations() {
		return this.recommendations;
	}

	public void setRecommendations(HashSet<Recommendation> currRecommendations, Factor currFactor, Measure currMeasure) {
		this.recommendations = currRecommendations;
		this.factor = currFactor;
		this.measure = currMeasure;
	}

	public int size() {
		return this.recommendations.size();
	}
}