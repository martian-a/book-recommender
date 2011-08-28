package uk.co.bluegumtree.code.java.goodreads.recommender.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import uk.co.bluegumtree.code.java.goodreads.recommender.model.Factor;
import uk.co.bluegumtree.code.java.goodreads.recommender.model.InvalidRatingException;
import uk.co.bluegumtree.code.java.goodreads.recommender.model.Measure;
import uk.co.bluegumtree.code.java.goodreads.recommender.model.PreferencesNotFoundException;
import uk.co.bluegumtree.code.java.goodreads.recommender.model.Recommendation;
import uk.co.bluegumtree.code.java.goodreads.recommender.model.RecommendationSet;
import uk.co.bluegumtree.code.java.goodreads.recommender.model.UserPreferences;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsBook;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsLibrary;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsUser;

public class Recommender {

	public static final String SAVE_DIRECTORY = "recommender/";

	private Profiler profiler;
	private HashSet<GoodreadsBook> books;
	private HashSet<GoodreadsUser> users;

	public Recommender(GoodreadsLibrary currLibrary, Profiler currProfiler) {
		this.profiler = currProfiler;
		this.books = currLibrary.getBooks();
		this.users = this.profiler.getUsers();
	}

	public HashMap<GoodreadsUser, ArrayList<RecommendationSet>> recommend() {

		HashMap<GoodreadsUser, ArrayList<RecommendationSet>> recommendations = new HashMap<GoodreadsUser, ArrayList<RecommendationSet>>();
		for (GoodreadsUser currUser : this.users) {
			try {
				recommendations.put(currUser, this.recommend(currUser));
			} catch (PreferencesNotFoundException e) {
				e.printStackTrace();
			}
		}
		return recommendations;
	}

	/**
	 * Generates a set of recommendations for the user specified based on their
	 * preferences as determined during testing.
	 * 
	 * @param currUser
	 * the user for whom the recommendations are being generated
	 * 
	 * @return a collection of graded recommendations.
	 * 
	 * @throws PreferencesNotFoundException
	 */
	public ArrayList<RecommendationSet> recommend(GoodreadsUser currUser) throws PreferencesNotFoundException {

		// Create a collection to store all the recommendation sets in
		ArrayList<RecommendationSet> allRecommendationSets = new ArrayList<RecommendationSet>();

		// Retrieve this user's preferences
		UserPreferences currPref = this.profiler.getPreferences(currUser);
		System.out.println(currPref.toString());

		for (Factor currFactor : Factor.values()) {

			// Check for the generic ALL factor
			if (currFactor.equals(Factor.ALL)) {

				// Create a collection to store the current collection set
				RecommendationSet currRecommendationSet = new RecommendationSet(currFactor, Measure.OPTIMAL);

				for (GoodreadsBook currBook : this.books) {

					try {

						// Apply all the factors at once, using the optimal
						// measure
						// (a single set of recommendations for the combined
						// effect of applying all the factors)
						Recommendation currBookRecommendation = new Recommendation(currUser, currBook, currPref.apply(currBook));
						currRecommendationSet.add(currBookRecommendation);

					} catch (InvalidRatingException e) {
						e.printStackTrace();
					}
				}

				allRecommendationSets.add(currRecommendationSet);

			} else {

				// Apply each of the measures, in turn, for the the current
				// factor
				// (new set of recommendations for each factor/measure combo)
				for (Measure currMeasure : Measure.values()) {

					if (!currMeasure.equals(Measure.OPTIMAL)) {

						// Create a collection to store the current collection
						// set
						RecommendationSet currRecommendationSet = new RecommendationSet(currFactor, currMeasure);

						for (GoodreadsBook currBook : this.books) {

							try {

								// Apply the current factor and measure combo
								Recommendation currBookRecommendation = new Recommendation(currUser, currBook, currPref.apply(currBook, currFactor, currMeasure));
								currRecommendationSet.add(currBookRecommendation);

							} catch (InvalidRatingException e) {
								e.printStackTrace();
							}
						}

						// Add the current recommendation set to the parent
						// collection
						allRecommendationSets.add(currRecommendationSet);
					}
				}
			}

		}

		return allRecommendationSets;
	}

	public RecommendationSet recommend(GoodreadsUser currUser, Factor currFactor, Measure currMeasure) throws PreferencesNotFoundException {

		RecommendationSet recommendations = new RecommendationSet(currFactor, currMeasure);

		UserPreferences currPref = this.profiler.getPreferences(currUser);

		System.out.println(currPref.toString());

		for (GoodreadsBook currBook : this.books) {

			try {
				Recommendation currBookRecommendation = new Recommendation(currUser, currBook, currPref.apply(currBook, currFactor, currMeasure));
				recommendations.add(currBookRecommendation);
			} catch (InvalidRatingException e) {
				e.printStackTrace();
			}

		}
		return recommendations;
	}

}
