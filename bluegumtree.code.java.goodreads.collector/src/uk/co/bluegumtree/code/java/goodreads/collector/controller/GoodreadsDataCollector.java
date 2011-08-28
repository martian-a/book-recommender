package uk.co.bluegumtree.code.java.goodreads.collector.controller;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import uk.co.bluegumtree.code.java.goodreads.collector.model.DataCollectorException;
import uk.co.bluegumtree.code.java.goodreads.collector.model.DataCollectorLoadException;
import uk.co.bluegumtree.code.java.goodreads.collector.model.DataCollectorUpdateException;
import uk.co.bluegumtree.code.java.goodreads.collector.model.GoodreadsBook;
import uk.co.bluegumtree.code.java.goodreads.collector.model.GoodreadsUpdateException;
import uk.co.bluegumtree.code.java.goodreads.collector.model.GoodreadsUser;
import uk.co.bluegumtree.code.java.goodreads.collector.model.GoogleBook;
import uk.co.bluegumtree.code.java.goodreads.collector.model.IsbnException;
import uk.co.bluegumtree.code.java.goodreads.collector.model.Mode;
import uk.co.bluegumtree.code.java.goodreads.collector.model.OpenLibraryBook;
import co.uk.bluegumtree.code.java.util.ArgsInterpreter;
import co.uk.bluegumtree.code.java.util.Location;
import co.uk.bluegumtree.code.java.util.Logger;

/**
 * Downloads data from the 
 * <ul>
 * <li>Goodreads API: http://www.goodreads.com/api/</li>
 * <li>GData Book API: http://www.google.com/books/feeds/</li>
 * <li>OpenLibrary API: http://openlibrary.org/api/</li>
 * </ul>
 * 
 * @author Sheila Thomson
 * 
 */
public class GoodreadsDataCollector {

	/**
	 * Options that will be considered valid if passed in from the command-line
	 * 
	 * -up = force update
	 * -v  = verbose
	 * -u  = username
	 * -i  = Goodreads user id
	 * -d  = depth to spider to ! WARNING: even a depth of 1 may take years...
	 */
	private static final String[] VALID_OPTION_SWITCHES = { "-up", "-v", "-u", "-d", "-i" };
	
	private static final Mode DEFAULT_MODE = Mode.LOAD;
	private static final String DEFAULT_PRIMARY_USERNAME = "sheilaellen";
	private static final int DEFAULT_MAX_SPIDER = 0;
	private static final int DEFAULT_MAX_REVIEWERS_SPIDERED = 10;

	public static boolean verbose;
	public static Mode mode;
	private int depth;

	private HashSet<GoodreadsBook> books;
	private HashSet<GoodreadsBook> booksSpidered;
	private HashSet<GoodreadsUser> reviewersSpidered;

	/**
	 * Collects data intended for use with the Book Recommender.
	 * By default it will download book and review data for Goodreads user sheilaellen.
	 * This can be overriden by supplying an alternative Goodreads username (-u anotheruser) or ID (-i 1193852) as a parameter at runtime.
	 */
	public GoodreadsDataCollector(HashMap<String, String> options) {

		// Set verbosity
		if (options.containsKey("-v")) {
			verbose = true;
		} else {
			verbose = false;
		}

		// Set mode
		if (options.containsKey("-up")) {
			mode = Mode.UPDATE;
		} else {
			mode = GoodreadsDataCollector.DEFAULT_MODE;
		}

		// Set depth
		if (options.containsKey("-d")) {

			this.depth = 0;

			String specifiedDepth = options.get("-d");
			if (specifiedDepth == null || specifiedDepth.trim() == "") {
				this.depth = DEFAULT_MAX_SPIDER;
			} else {
				this.depth = Integer.parseInt(specifiedDepth.trim());
			}

		} else {
			this.depth = 0;
		}

		// Set primary user's details to default values
		String primaryUsername = DEFAULT_PRIMARY_USERNAME;
		Long primaryUserId = null;

		// Override default primaryUsername with custom value,
		// if supplied
		if (options.containsKey("-u") || options.containsKey("-i")) {

			// If both an ID and Username are provided,
			// use the username and ignore the ID
			// TODO: add in a check to see if both are for the same person; if
			// not, throw a fatal exception or request user to re-enter JUST
			// ONE, correctly
			if (options.containsKey("-u")) {

				// Use user supplied username instead of default value
				primaryUsername = options.get("-u");

			} else {

				// User user supplied ID instead of default value
				primaryUserId = Long.parseLong(options.get("-i"));
			}
		}

		// Create a GoodreadsUser object representing the primary user
		GoodreadsUser primaryUser = null;
		try {

			if (primaryUserId != null) {

				// A Goodreads User ID has been supplied,
				primaryUser = new GoodreadsUser(primaryUserId);
				primaryUsername = primaryUser.getUsername();

			} else {

				// Goodreads User ID unknown, default to Goodreads username
				// instead
				primaryUser = new GoodreadsUser(primaryUsername);
			}

		} catch (GoodreadsUpdateException e) {
			e.printStackTrace();
		} catch (DataCollectorException e) {
			e.printStackTrace();
		}

		// If it wasn't possible to create the primaryUser,
		// terminate the program.
		if (primaryUser == null) {
			System.exit(1);
		}

		// Display confirmation of who the primary user is
		System.out.println("Verbose: " + String.valueOf(verbose));
		System.out.println("Mode: " + String.valueOf(mode) + "\n");
		System.out.println("Primary User: " + primaryUsername + "\n");

		// Initialise core collections
		this.books = new HashSet<GoodreadsBook>();
		this.booksSpidered = new HashSet<GoodreadsBook>();
		this.reviewersSpidered = new HashSet<GoodreadsUser>();

		/*
		 * Begin data collection. Get data on the primary user
		 */
		System.out.println("Loading primary user");
		try {

			primaryUser.load();

		} catch (DataCollectorLoadException e) {

			// Failed to both load data
			// from both local and remote sources
			e.printStackTrace();

			// Terminate the program
			// as we have no data to work with
			System.exit(1);
		}

		/*
		 * Data collection complete. Print a profile of the primary User
		 */
		System.out.println(primaryUser.toString());

		/**
		 * SPIDER IS GO!
		 */
		this.spider(primaryUser, 0);

		// Loop through the global book list and
		// retrieve data further data on them
		// from the Google Book API
		for (GoodreadsBook origBook : this.books) {

			try {

				new GoogleBook(origBook.getIsbn13());

			} catch (IsbnException e) {

				try {
					new GoogleBook(origBook.getIsbn10());
				} catch (IsbnException e1) {
					Logger.log(e1);
				} catch (DataCollectorUpdateException e1) {
					Logger.log(e1);
				}

			} catch (DataCollectorUpdateException e) {
				Logger.log(e);
			}

			try {

				new OpenLibraryBook(origBook.getIsbn13());

			} catch (IsbnException e1) {

				try {
					new OpenLibraryBook(origBook.getIsbn10());
				} catch (IsbnException e2) {
					Logger.log(e1);
				} catch (DataCollectorUpdateException e2) {
					Logger.log(e1);
				}

			} catch (DataCollectorUpdateException e1) {
				Logger.log(e1);
			}

		}
		moveDirectory(primaryUser);
	}

	/**
	 * Spiders out from the currUser to the depth specified, 
	 * using "people who have also reviewed this book" as the pathway.
	 * 
	 * Seriously flawed as the number of users, reviews and books grow exponentially
	 * and even at a depth of 1 the process may take several months to complete.
	 *  
	 * @param currUser
	 * the user who is currently being processed
	 * 
	 * @param currDepth
	 * the number of nodes radiating out from the currUser that the spider should travel to.
	 */
	private void spider(GoodreadsUser currUser, int currDepth) {

		/*
		 * Get review data submitted by the primary user
		 */
		try {

			currUser.loadReviews();

		} catch (DataCollectorLoadException e) {
			Logger.log(e);
		}

		// The data required for this user has been gathered
		// Add them to the global reviewersSpidered list so that they're not
		// processed again.
		this.reviewersSpidered.add(currUser);

		// Retrieve a list of all the books reviewed by this user
		HashSet<GoodreadsBook> currBooks = currUser.getBooks();

		// Add the full list to the global list
		// of books for which profile's have been downloaded
		// (but not necessarily spidered)
		this.books.addAll(currBooks);

		if (currDepth < this.depth) {

			// Loop through all the books in the collection supplied
			for (GoodreadsBook currBook : currBooks) {

				// Only process this book if it hasn't already been processed.
				if (!this.booksSpidered.contains(currBook)) {

					try {

						// Retrieve a list of all the other users who have
						// reviewed this book.
						HashSet<GoodreadsUser> reviewers = currBook.getReviewers();

						// Loop through all the users who have reviewed this
						// book
						for (GoodreadsUser currReviewer : reviewers) {

							// Check whether we've reached the maximum number of
							// reviewers to spider
							if (this.reviewersSpidered.size() < DEFAULT_MAX_REVIEWERS_SPIDERED) {

								// If this user hasn't already been processed...
								if (!this.reviewersSpidered.contains(currReviewer)) {

									// Send this user to the spider!
									currDepth++;
									this.spider(currReviewer, currDepth);

								}
							}
						}

					} catch (DataCollectorLoadException e) {

						// Unable to retrieve list of users who have reviewed
						// this book
						Logger.log(e);
					}

					// The list of users who have reviewed this book has been
					// retrieved for processing.
					// Add this book to the global list of books that have been
					// spidered.
					this.booksSpidered.add(currBook);

					// Check whether we've reached the maximum number of
					// reviewers to spider
					if (this.reviewersSpidered.size() < DEFAULT_MAX_REVIEWERS_SPIDERED) {

						return;

					}
				}
			}
		}
	}

	/**
	 * Where it all begins.
	 * 
	 * @param args
	 * a String array of information to be passed through to this application
	 */
	public static void main(String[] args) {
		new GoodreadsDataCollector(ArgsInterpreter.interpret(args, VALID_OPTION_SWITCHES));
	}

	/**
	 * Moves the downloaded data into a subdirectory named using the primaryUser's Goodreads ID.
	 * 
	 * @param currUser
	 * the user whose directory the data is to be moved to.
	 */
	private static void moveDirectory(GoodreadsUser currUser) {
	
		String organiseIn = Location.getSaveDir("goodreads/db/organise/in/");
		String currDestination = organiseIn + currUser.getId() + "/";
		
		System.out.println("Copying " + organiseIn + "goodreads/" + " to " + currDestination + "goodreads/");
		
		new File(organiseIn + currUser.getId() + File.separator).mkdir();
		new File(organiseIn + "goodreads/").renameTo(new File(currDestination + "goodreads/"));
		new File(organiseIn + "google/").renameTo(new File(currDestination + "google/"));
		new File(organiseIn + "openlibrary/").renameTo(new File(currDestination + "openLibrary/"));
		System.out.println("...complete");
	}
}
