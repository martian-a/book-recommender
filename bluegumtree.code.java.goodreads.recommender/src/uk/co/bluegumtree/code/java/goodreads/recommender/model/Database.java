package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import co.uk.bluegumtree.code.java.api.goodreads.BookNotFoundException;
import co.uk.bluegumtree.code.java.api.goodreads.Gender;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsAuthor;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsBook;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsCommunity;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsFeedback;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsLibrary;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsLoadException;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsReview;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsUser;
import co.uk.bluegumtree.code.java.api.goodreads.ReviewNotFoundException;
import co.uk.bluegumtree.code.java.api.goodreads.UserNotFoundException;
import co.uk.bluegumtree.code.java.util.Logger;
import co.uk.bluegumtree.code.java.util.XmlReadException;
import co.uk.bluegumtree.code.java.util.XmlReader;

public class Database {

	public static final String DATA_DIRECTORY_USERS = "users/";
	public static final String DATA_DIRECTORY_WORKS = "works/";
	public static final String DATA_DIRECTORY_REVIEWS_TRAINING = "reviews/training/";
	public static final String DATA_DIRECTORY_REVIEWS_TESTING = "reviews/testing/";

	private GoodreadsLibrary library;
	private GoodreadsLibrary trainingLibrary;
	private GoodreadsLibrary testingLibrary;
	private GoodreadsLibrary evaluationLibrary;
	private GoodreadsFeedback trainingFeedback;
	private GoodreadsFeedback testingFeedback;
	private GoodreadsFeedback evaluationFeedback;
	private GoodreadsFeedback feedback;
	private GoodreadsCommunity people;
	private Mode mode;
	private String sourceDataDirectory;

	public Database(String currDataDirectory) throws GoodreadsLoadException {
		this.sourceDataDirectory = currDataDirectory;
		this.trainingLibrary = new GoodreadsLibrary();
		this.testingLibrary = new GoodreadsLibrary();
		this.evaluationLibrary = new GoodreadsLibrary();
		this.trainingFeedback = new GoodreadsFeedback();
		this.testingFeedback = new GoodreadsFeedback();
		this.evaluationFeedback = new GoodreadsFeedback();
		this.people = new GoodreadsCommunity();
		this.mode = Mode.TRAINING;
		this.setFeedback();
	}

	private GoodreadsBook getBook(Long currId) throws BookNotFoundException {
		return this.getBook(currId, true);
	}

	private GoodreadsBook getBook(Long currId, boolean create) throws BookNotFoundException {

		GoodreadsBook currBook = null;

		/*
		 * Check whether a book with this ID already exists in the library.
		 */
		if (this.library.contains(currId)) {
			try {
				currBook = this.library.getBook(currId);
			} catch (BookNotFoundException e) {
				Logger.log("...book " + currId + " doesn't exist in the library yet.");
			}
		}

		if (create == false && currBook == null) {
			throw new BookNotFoundException();
		}

		/*
		 * Book either doesn't yet exist in the library or attempt to fetch it
		 * failed. Create a new book with the current ID and add it to the
		 * library.
		 */
		if (create == true && (currBook == null || !currBook.getId().equals(currId))) {
			Logger.log("...creating record for book " + currId + " and adding it to the library");
			currBook = new GoodreadsBook(currId);
			this.library.addBook(currBook);
		}

		return currBook;
	}

	public GoodreadsLibrary getBooks() {
		return this.library;
	}

	public GoodreadsCommunity getCommunity() {
		return this.people;
	}

	public GoodreadsFeedback getFeedback() {
		return this.feedback;
	}

	private ArrayList<File> getFiles(String source) throws GoodreadsLoadException {

		ArrayList<File> files = new ArrayList<File>();

		File currDirectory = new File(source);

		Logger.log("Checking " + source);
		Logger.log("Checking " + currDirectory.getName());

		if (currDirectory.isDirectory()) {

			Logger.log("Processing " + currDirectory.getName());

			String[] internalNames = currDirectory.list();

			for (int i = 0; i < internalNames.length; i++) {
				File currFile = new File(currDirectory.getAbsolutePath() + File.separator + internalNames[i]);

				if (currFile.exists() && currFile.isFile()) {
					files.add(currFile);
				}
			}
		}

		return files;
	}

	public Mode getMode() {
		return this.mode;
	}

	public HashSet<GoodreadsUser> getUsers() {
		return this.people.getUsers();
	}

	public void load() throws GoodreadsLoadException {

		/*
		 * Process the reviews
		 * 
		 * Will also add each book reviewed to the library and each user who's
		 * written a review to the community
		 */
		System.out.println("Loading reviews.");
		this.loadReviews();

		/*
		 * Process the books
		 */
		System.out.println("Loading books.");
		this.loadBooks();

		/*
		 * Process the users
		 */
		System.out.println("Loading users.");
		this.loadUsers();

		/*
		 * Reconcile
		 */
		System.out.println("Reconciling data.");
		this.reconcile();

		// Print out a summary of the library
		System.out.println("Data loading complete.\n\n");
		System.out.println("= TRAINING LIBRARY =\n\n" + this.trainingLibrary.getLibraryProfile());
		System.out.println("= TESTING LIBRARY =\n\n" + this.testingLibrary.getLibraryProfile());
		System.out.println("= EVALUATION LIBRARY =\n\n" + this.evaluationLibrary.getLibraryProfile());
	}

	private GoodreadsAuthor loadAuthor(Document currXml, GoodreadsBook currBook, Long authorId) {

		Long currId = currBook.getId();
		GoodreadsAuthor currAuthor = new GoodreadsAuthor(authorId);

		String authorName = XmlReader.readString(currXml, "//book[id = " + currId + "]/author/author[id = '" + authorId + "']/name");
		if (authorName != null && authorName.trim().length() > 0) {
			currAuthor.setName(authorName);
		}

		return currAuthor;
	}

	private HashSet<GoodreadsAuthor> loadAuthors(Document currXml, GoodreadsBook currBook) {

		Long bookId = currBook.getId();
		HashSet<GoodreadsAuthor> authors = new HashSet<GoodreadsAuthor>();
		NodeList authorIds = XmlReader.readNodeList(currXml, "//book[id = " + bookId + "]/author/author/id");

		for (int i = 0; i < authorIds.getLength(); i++) {
			authors.add(this.loadAuthor(currXml, currBook, Long.parseLong(authorIds.item(i).toString())));
		}

		return authors;
	}

	private void loadBook(Document currXml) throws BookNotFoundException {

		// Retrieve the ID of this book
		Long currId = XmlReader.readLong(currXml, "user/review/book/id");
		Logger.log("Processing book: " + currId);

		// Get the book associated with this ID from the library
		GoodreadsBook currBook = this.getBook(currId);

		/*
		 * Update the data on this book
		 */

		// Old-style ISBN (10)
		if (currBook.getIsbn10() == null) {
			currBook.setIsbn10(XmlReader.readString(currXml, "//book[id = " + currId + "]/isbn"));
			if (currBook.getIsbn10().equals("")) {
				currBook.setIsbn10(null);
			}
		}

		// New-style ISBN (13)
		if (currBook.getIsbn13() == null) {
			currBook.setIsbn13(XmlReader.readString(currXml, "//book[id = " + currId + "]/isbn13"));
			if (currBook.getIsbn13().equals("")) {
				currBook.setIsbn13(null);
			}
		}

		// Book title
		if (currBook.getTitle() == null) {
			currBook.setTitle(XmlReader.readString(currXml, "//book[@source = 'google']/title").trim());
			if (currBook.getTitle().equals("")) {
				currBook.setTitle(XmlReader.readString(currXml, "//book[id = " + currId + "]/title"));
				if (currBook.getTitle().equals("")) {
					currBook.setTitle(null);
				}
			}
		}

		// Author
		if (currBook.getAuthors() == null) {
			currBook.setAuthors(this.loadAuthors(currXml, currBook));
		}

		// Total number of pages
		if (currBook.getTotalPages() == null) {
			currBook.setTotalPages(XmlReader.readInteger(currXml, "//book[id = " + currId + "]/num_pages"));
		}

		// Year of publication (edition)
		if (currBook.getPublicationYear() == null) {
			currBook.setPublicationYear(XmlReader.readInteger(currXml, "//book[id = " + currId + "]/publication_year"));
		}

		// Language (edition)
		if (currBook.getLanguage() == null) {
			String value = XmlReader.readString(currXml, "//book[id = " + currId + "]/language_code");
			if (!value.equals("")) {
				currBook.setLanguage(value);
			}
		}
	}

	private void loadBooks() {

		/*
		 * Load training library
		 */

		// Load book data from training reviews
		System.out.println("...training data");
		try {
			this.setMode(Mode.TRAINING);
			this.loadBooks(this.sourceDataDirectory + DATA_DIRECTORY_REVIEWS_TRAINING);
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}

		// Load additional book data from works
		try {
			this.loadBooks(this.sourceDataDirectory + DATA_DIRECTORY_WORKS);
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}

		/*
		 * Load testing library
		 */
		System.out.println("...testing data");
		// Load book data from testing reviews
		try {
			this.setMode(Mode.TESTING);
			this.loadBooks(this.sourceDataDirectory + DATA_DIRECTORY_REVIEWS_TESTING);
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}

		// Load additional book data from works
		try {
			this.loadBooks(this.sourceDataDirectory + DATA_DIRECTORY_WORKS);
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}

		/*
		 * Load evaluation library
		 */
		System.out.println("...evaluation data");
		try {

			this.setMode(Mode.EVALUATION);

			// Load book data from training reviews
			/*
			 * try {
			 * this.loadBooks(Location.getSaveDir(this.sourceDataDirectory) +
			 * DATA_DIRECTORY_REVIEWS_TRAINING); } catch (GoodreadsLoadException
			 * e) { Logger.log(e); }
			 */

			// Load book data from testing reviews
			try {
				this.loadBooks(this.sourceDataDirectory + DATA_DIRECTORY_REVIEWS_TESTING);
			} catch (GoodreadsLoadException e) {
				Logger.log(e);
			}

			// Load additional book data from works
			try {
				this.loadBooks(this.sourceDataDirectory + DATA_DIRECTORY_WORKS);
			} catch (GoodreadsLoadException e) {
				Logger.log(e);
			}

		} catch (GoodreadsLoadException e) {
			e.printStackTrace();
		}

		// Set mode back to Training
		try {
			this.setMode(Mode.TRAINING);
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}
	}

	private void loadBooks(String source) throws GoodreadsLoadException {

		ArrayList<File> files = this.getFiles(source);

		Logger.log("Total files: " + files.size());

		for (int i = 0; i < files.size(); i++) {

			File currFile = files.get(i);

			try {

				// Read the XML file that contains book data
				Document currXml = XmlReader.read(currFile);

				// Check which document this is
				String rootElementName = currXml.getDocumentElement().getNodeName();

				if (rootElementName.equals("combined")) {

					// Document contains work data
					this.loadWorks(currXml);

				} else {

					// Document contains book data
					this.loadBook(currXml);

				}

			} catch (XmlReadException e) {
				Logger.log(e);
			} catch (IOException e) {
				Logger.log(e);
			} catch (BookNotFoundException e) {
				Logger.log(e);
			}
		}

		Logger.log("Total library: " + this.library.size());

	}

	private void loadReviews() {

		// Load training reviews
		System.out.println("...training data");
		try {
			this.setMode(Mode.TRAINING);
			this.loadReviews(this.sourceDataDirectory + DATA_DIRECTORY_REVIEWS_TRAINING);
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}

		// Load testing reviews
		System.out.println("...testing data");
		try {
			this.setMode(Mode.TESTING);
			this.loadReviews(this.sourceDataDirectory + DATA_DIRECTORY_REVIEWS_TESTING);
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}

		// Load all reviews (for evaluation)
		System.out.println("...evaluation data");
		try {
			this.setMode(Mode.EVALUATION);

			try {
				this.loadReviews(this.sourceDataDirectory + DATA_DIRECTORY_REVIEWS_TESTING);
			} catch (GoodreadsLoadException e2) {
				Logger.log(e2);
			}
			/*
			 * try {
			 * this.loadReviews(Location.getSaveDir(this.sourceDataDirectory) +
			 * DATA_DIRECTORY_REVIEWS_TRAINING); } catch (GoodreadsLoadException
			 * e2) { Logger.log(e2); }
			 */
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}

		// Reset mode to default (Training)
		try {
			this.setMode(Mode.TRAINING);
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}
	}

	private void loadReviews(String source) throws GoodreadsLoadException {

		ArrayList<File> files = this.getFiles(source);

		Logger.log("Total files: " + files.size());

		GoodreadsReview currReview = null;
		for (int i = 0; i < files.size(); i++) {

			File currFile = files.get(i);
			try {

				// Read an XML file that contains review data
				Document currXml = XmlReader.read(currFile);

				// Retrieve the ID of this review
				Long currId = XmlReader.readLong(currXml, "user/review/id");
				Logger.log("Processing review: " + currId);

				/*
				 * Check whether a review with this ID already exists in the
				 * library.
				 */
				if (this.library.contains(currId)) {
					try {
						currReview = this.feedback.getReview(currId);
					} catch (ReviewNotFoundException e) {
						Logger.log(e);
					}
				}

				/*
				 * Review either doesn't yet exist in the library or attempt to
				 * fetch it failed. Create a new Review with the current ID and
				 * add it to the library.
				 */
				if (currReview == null || !currReview.getId().equals(currId)) {
					currReview = new GoodreadsReview(currId);
					this.feedback.addReview(currReview);
				}

				// Retrieve the rating from the review
				if (currReview.getRating() == null) {
					int rating = XmlReader.readInteger(currXml, "user/review[id = " + currId + "]/rating");
					if (rating > 0) {
						currReview.setRating(rating);
					}
				}

				if ((this.mode == Mode.TRAINING || this.mode == Mode.EVALUATION) && currReview.getBook() == null) {

					Long bookId = XmlReader.readLong(currXml, "user/review[id = " + currId + "]/book/id");

					if (bookId != null) {

						Logger.log("Retrieving book from library: " + bookId);
						GoodreadsBook currBook = null;
						try {

							// Retrieve book from library, if it's in there
							currBook = this.library.getBook(bookId);

						} catch (BookNotFoundException e) {

							// New book
							Logger.log("...new book, adding to library");
							currBook = new GoodreadsBook(bookId);
							this.library.addBook(currBook);

						}

						if (currBook != null) {
							Logger.log("Setting book: " + bookId);
							currReview.setBook(currBook);
						} else {
							System.err.println("Unable to set book " + bookId + " for review " + currId);
						}

					} else {
						System.err.println("Unable to determine ID of book that this review is about.");
					}
				}

				if ((this.mode == Mode.TRAINING || this.mode == Mode.EVALUATION) && currReview.getUser() == null) {

					Long userId = XmlReader.readLong(currXml, "user[review/id = " + currId + "]/id");

					if (userId != null) {

						Logger.log("Retrieving user from community: " + userId);
						GoodreadsUser currUser = null;
						try {
							currUser = this.people.getUser(userId);
						} catch (UserNotFoundException e) {
							Logger.log(e);
							currUser = new GoodreadsUser(userId);
							this.people.addUser(currUser);
						}

						if (currUser != null) {
							Logger.log("Setting user: " + userId);
							currReview.setUser(currUser);
						} else {
							System.err.println("Unable to set user " + userId + " for review " + currId);
						}

					} else {
						System.err.println("Unable to determine ID of user who wrote review " + currId + ".");
					}
				}

			} catch (XmlReadException e) {
				Logger.log(e);
			} catch (IOException e) {
				Logger.log(e);
			}
		}

		Logger.log("Total reviews: " + this.feedback.size());

	}

	private void loadUsers() {

		// Load users from users
		try {
			this.loadUsers(this.sourceDataDirectory + DATA_DIRECTORY_USERS);
		} catch (GoodreadsLoadException e) {
			Logger.log(e);
		}

	}

	private void loadUsers(String source) throws GoodreadsLoadException {

		ArrayList<File> files = this.getFiles(source);

		Logger.log("Total files: " + files.size());

		GoodreadsUser currUser = null;
		Long prevId = null;
		for (int i = 0; i < files.size(); i++) {

			File currFile = files.get(i);
			try {

				// Read an XML file that contains user data
				Document currXml = XmlReader.read(currFile);

				// Extract a list of the IDs of each user in the XML file
				NodeList userIds = XmlReader.readNodeList(currXml, "user/id");

				for (int b = 0; b < userIds.getLength(); b++) {

					// Get the ID of the next user in the list of IDs
					Long currId = XmlReader.readLong(userIds.item(b), ".");

					if (currId != null) {

						if (prevId == null || !prevId.equals(currId)) {

							/*
							 * Check whether a user with this ID already exists
							 * in the community.
							 */
							if (this.people.contains(currId)) {
								try {
									currUser = this.people.getUser(currId);
								} catch (UserNotFoundException e) {
									Logger.log(e);
								}
							}

							/*
							 * User either doesn't yet exist in the community or
							 * attempt to fetch it failed. Create a new user
							 * with the current ID and add it to the community.
							 */
							if (currUser == null || !currUser.getId().equals(currId)) {
								currUser = new GoodreadsUser(currId);
								this.people.addUser(currUser);
							}

							Logger.log("Processing user: " + currId);
						}

						if (currUser.getName() == null) {
							currUser.setName(XmlReader.readString(currXml, "user[id = " + currId + "][name != ''][1]/name"));
						}

						if (currUser.getUsername() == null) {
							currUser.setUsername(XmlReader.readString(currXml, "user[id = " + currId + "][username != ''][1]/username"));
						}

						if (currUser.getAge() == null) {
							currUser.setAge(XmlReader.readInteger(currXml, "user[id = " + currId + "][age != ''][1]/age"));
						}

						if (currUser.getGender() == null) {
							String value = XmlReader.readString(currXml, "user[id = " + currId + "][gender != ''][1]/gender");
							if (value != null && !value.equals("")) {
								currUser.setGender(Gender.valueOf(value));
							}
						}

						if (currUser.getLocation() == null) {
							currUser.setLocation(XmlReader.readString(currXml, "user[id = " + currId + "][location != ''][1]/location"));
						}
					}
					prevId = currId;
				}

			} catch (XmlReadException e) {
				Logger.log(e);
			} catch (IOException e) {
				Logger.log(e);
			}

		}

		Logger.log("Total users: " + this.people.size());
	}

	private void loadWorks(Document currXml) {

		XmlReader.readLong(currXml, "combined/work/id");

		// Get the books associated with this work
		HashSet<GoodreadsBook> workBooks = new HashSet<GoodreadsBook>();
		NodeList bookIdsAsNodeList = XmlReader.readNodeList(currXml, "//books/book/@id");
		for (int i = 0; i < bookIdsAsNodeList.getLength(); i++) {

			// Get the ID of the next book in the list of IDs
			Long currId = XmlReader.readLong(bookIdsAsNodeList.item(i), ".");

			// Get the book associated with this ID from the library
			// and add it to the list of books associated with this work
			try {
				GoodreadsBook currBook = this.getBook(currId, false);
				workBooks.add(currBook);
			} catch (BookNotFoundException e) {
				Logger.log("Book " + currId + " doesn't exist in the library and hasn't been added. (Mode: " + this.mode.name() + ")");
			}
		}

		// Loop through all the books associated with this work
		// and update each with data from the work
		for (GoodreadsBook currBook : workBooks) {

			// Original year of publication
			if (currBook.getOriginalPublicationYear() == null) {
				currBook.setOriginalPublicationYear(XmlReader.readInteger(currXml, "//work/original_publication_year"));
			}

			// Original month of publication
			if (currBook.getOriginalPublicationMonth() == null) {
				currBook.setOriginalPublicationMonth(XmlReader.readInteger(currXml, "//work/original_publication_month"));
			}

			// Original Language
			if (currBook.getOriginalLanguage() == null) {
				currBook.setOriginalLanguage(XmlReader.readInteger(currXml, "//work/original_language_id"));
			}

		}
	}

	public void reconcile() {
		this.reconcile(this.trainingFeedback.getReviews());
		this.reconcile(this.evaluationFeedback.getReviews());
	}

	public void reconcile(HashSet<GoodreadsReview> currReviews) {

		for (GoodreadsReview currReview : currReviews) {
			GoodreadsBook currBook = currReview.getBook();
			GoodreadsUser currUser = currReview.getUser();

			if (currBook != null) {
				currBook.addReview(currReview);
			}

			if (currUser != null) {
				currUser.addReview(currReview);

				if (currBook != null) {
					currUser.addBook(currBook);
				}
			}
		}
	}

	private void setFeedback() throws GoodreadsLoadException {
		if (this.mode == Mode.TRAINING) {
			this.feedback = this.trainingFeedback;
		}
		if (this.mode == Mode.TESTING) {
			this.feedback = this.testingFeedback;
		}
		if (this.mode == Mode.EVALUATION) {
			this.feedback = this.evaluationFeedback;
		}
		if (this.feedback == null) {
			throw new GoodreadsLoadException("Mode not recognised.");
		}
	}

	private void setLibrary() throws GoodreadsLoadException {
		if (this.mode == Mode.TRAINING) {
			this.library = this.trainingLibrary;
		}
		if (this.mode == Mode.TESTING) {
			this.library = this.testingLibrary;
		}
		if (this.mode == Mode.EVALUATION) {
			this.library = this.evaluationLibrary;
		}
		if (this.library == null) {
			throw new GoodreadsLoadException("Mode not recognised.");
		}
	}

	public void setMode(Mode currMode) throws GoodreadsLoadException {
		this.mode = currMode;
		this.setFeedback();
		this.setLibrary();
	}
}
