package uk.co.bluegumtree.code.java.goodreads.recommender.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import uk.co.bluegumtree.code.java.goodreads.recommender.model.Database;
import uk.co.bluegumtree.code.java.goodreads.recommender.model.Mode;
import uk.co.bluegumtree.code.java.goodreads.recommender.model.RecommendationSet;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsLoadException;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsUser;
import co.uk.bluegumtree.code.java.util.ArgsInterpreter;
import co.uk.bluegumtree.code.java.util.Location;
import co.uk.bluegumtree.code.java.util.Logger;
import co.uk.bluegumtree.code.java.util.Organiser;

public class GoodreadsRecommender {

	private static final String[] VALID_OPTION_SWITCHES = { "-v", "-id" };

	public static final String DATA_DIRECTORY = "goodreads/db/";
	public static final String DEFAULT_SET_DIRECTORY = "out/";
	public static final String SAVE_DIRECTORY = "goodreads/db/recommendations/" + DEFAULT_SET_DIRECTORY;
	public static final String DEFAULT_SOURCE_DATA_DIRECTORY = DATA_DIRECTORY + "organise/" + DEFAULT_SET_DIRECTORY;

	public static boolean verbose;
	private Database db;
	private Profiler profiler;
	private Recommender recommender;
	private Assessor assessor;
	private String sourceDataDirectory;

	/**
	 * The main controller. Marshals the loading of data, analysis, production
	 * of recommendations and evaluation of those recommendations.
	 * 
	 * @param options
	 * Parameters that have been passed to the application at runtime.
	 */
	public GoodreadsRecommender(HashMap<String, String> options) {

		// Set verbosity
		System.out.println("Initialising...");
		if (options.containsKey("-v")) {
			verbose = true;
		} else {
			verbose = false;
		}

		// Set data directory
		System.out.println("Setting source data directory...");
		this.sourceDataDirectory = Location.getSaveDir(DEFAULT_SOURCE_DATA_DIRECTORY);
		HashSet<File> sourceDataDirectories = null;

		// Set user directory, if specified
		if (options.containsKey("-id")) {

			this.sourceDataDirectory = this.sourceDataDirectory + options.get("-id") + File.separator;

			sourceDataDirectories = new HashSet<File>();
			sourceDataDirectories.add(new File(this.sourceDataDirectory));

		} else {

			try {
				sourceDataDirectories = Organiser.getDirectories(new File(this.sourceDataDirectory));
			} catch (FileNotFoundException e) {
				Logger.log(e);
			}
		}

		if (sourceDataDirectories == null) {
			System.err.println("No data found.");
			System.exit(1);
		}

		for (File currDirectory : sourceDataDirectories) {

			try {
				this.process(currDirectory);
			} catch (IOException e) {
				Logger.log(e);
			}
		}

	}

	public void process(File currDirectory) throws IOException {

		// Load the data
		this.db = null;
		try {

			this.db = new Database(currDirectory.getCanonicalPath() + File.separator);
			this.db.load();

		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}

		// Train
		try {
			this.db.setMode(Mode.TRAINING);
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}
		this.profiler = new Profiler(this.db.getUsers());
		this.profiler.profile();

		// Test
		try {
			this.db.setMode(Mode.TESTING);
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}

		this.recommender = new Recommender(this.db.getBooks(), this.profiler);
		HashMap<GoodreadsUser, ArrayList<RecommendationSet>> recommendations = this.recommender.recommend();

		// Assess
		try {
			this.db.setMode(Mode.EVALUATION);
		} catch (GoodreadsLoadException e) {
			e.printStackTrace();
		}

		this.assessor = new Assessor(recommendations, this.db.getBooks());

	}

	/**
	 * @param args
	 * a String array of parameters that may be passed through to the
	 * application at runtime.
	 */
	public static void main(String[] args) {
		new GoodreadsRecommender(ArgsInterpreter.interpret(args, VALID_OPTION_SWITCHES));
	}
}
