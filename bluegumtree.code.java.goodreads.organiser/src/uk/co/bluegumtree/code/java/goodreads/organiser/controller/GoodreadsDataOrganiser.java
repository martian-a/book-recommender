package uk.co.bluegumtree.code.java.goodreads.organiser.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.TransformerFactoryImpl;

import org.w3c.dom.Document;

import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsException;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsLoadException;
import co.uk.bluegumtree.code.java.util.ArgsInterpreter;
import co.uk.bluegumtree.code.java.util.Location;
import co.uk.bluegumtree.code.java.util.Logger;
import co.uk.bluegumtree.code.java.util.Organiser;
import co.uk.bluegumtree.code.java.util.ResourceResolver;
import co.uk.bluegumtree.code.java.util.XmlReadException;
import co.uk.bluegumtree.code.java.util.XmlReader;
import co.uk.bluegumtree.code.java.util.XmlWriter;

/**
 * Processes data collected for use with the Book Recommender.
 * Merges data from multiple sources, splits review lists up into individual review files,
 * filters out reviews/books that can't be positively identified as fiction and 
 * divides each user's library into two sets: testing and training.
 * 
 * @author Sheila Ellen Thomson
 *
 */
public class GoodreadsDataOrganiser {

	/**
	 * Options that will be considered valid if passed in from the command-line
	 * 
	 * -v  = verbose
	 * -i  = Goodreads user id
	 */
	private static final String[] VALID_OPTION_SWITCHES = { "-v", "-i" };

	public static final String DATA_ROOT = "goodreads/db/";
	public static final String DATA_DIRECTORY_ORGANISE = DATA_ROOT + "organise/";
	public static final String DATA_DIRECTORY_IN = DATA_DIRECTORY_ORGANISE + "in/";
	public static final String DATA_DIRECTORY_OUT = DATA_DIRECTORY_ORGANISE + "out/";

	public static final String DATA_DIRECTORY_GOODREADS = "goodreads/";
	public static final String DATA_DIRECTORY_GOOGLE = "google/";
	public static final String DATA_DIRECTORY_OPENLIBRARY = "openlibrary/";

	public static final String DATA_DIRECTORY_USERS = "users/";
	public static final String DATA_DIRECTORY_BOOKS = "books/";
	public static final String DATA_DIRECTORY_REVIEWS = "reviews/";
	public static final String DATA_DIRECTORY_REVIEWS_BOOK = DATA_DIRECTORY_REVIEWS + "book/";
	public static final String DATA_DIRECTORY_REVIEWS_USER = DATA_DIRECTORY_REVIEWS + "user/";

	public static boolean verbose;
	public Logger logger;
	public String dataIn;
	public String dataOut;

	public static String goodreadsIn;
	
	/**
	 * @param options
	 * A white-listed collection of validated command-line switches and their values.
	 */
	public GoodreadsDataOrganiser(HashMap<String, String> options) {

		this.logger = new Logger();

		// Set verbosity
		if (options.containsKey("-v")) {
			verbose = true;
		} else {
			verbose = false;
		}

		this.dataOut = Location.getSaveDir(DATA_DIRECTORY_OUT);
		this.dataIn = Location.getSaveDir(DATA_DIRECTORY_IN);

		// Specify ID to use for source and output directories
		if (options.containsKey("-i")) {
			this.dataIn = Location.getSaveDir(DATA_DIRECTORY_ORGANISE) + options.get("-i") + File.separator;
			this.dataOut = this.dataOut + options.get("-i") + File.separator;
		}

		// Execute
		this.organise();
	}

	/**
	 * Divides the user's reviews into two sets: testing and training.
	 * 
	 * @param currDirectory
	 * the destination directory
	 * 
	 * @param source
	 * the directory to be divided
	 * 
	 * @throws GoodreadsLoadException
	 */
	private void divideFiles(String currDirectory, String source) throws GoodreadsLoadException {

		ArrayList<File> allFiles = this.getSortedFiles(source);

		String trainingDirectory = currDirectory + "training/";
		File trainingDirectoryFile = new File(trainingDirectory);
		if (!trainingDirectoryFile.exists()) {
			trainingDirectoryFile.mkdir();
		}

		String testingDirectory = currDirectory + "testing/";
		File testingDirectoryFile = new File(testingDirectory);
		if (!testingDirectoryFile.exists()) {
			testingDirectoryFile.mkdir();
		}

		int halfway = allFiles.size() / 2;
		for (int i = 0; i < allFiles.size(); i++) {

			File currFile = allFiles.get(i);
			File newFile = null;
			if (i < halfway) {
				newFile = new File(trainingDirectory + currFile.getName());
			} else {
				newFile = new File(testingDirectory + currFile.getName());
			}

			// Move the file to its new location
			this.log("Moving file from " + currFile.getAbsolutePath() + "\n...to " + newFile.getAbsolutePath());
			currFile.renameTo(newFile);
		}

		Organiser.delete(new File(source), true);
	}

	private ArrayList<File> getDirectories(String source, boolean drillDown) throws GoodreadsLoadException {
		return this.getFiles(source, drillDown, true);
	}

	private ArrayList<File> getFiles(String source) throws GoodreadsLoadException {
		return this.getFiles(source, false);
	}

	private ArrayList<File> getFiles(String source, boolean drillDown) throws GoodreadsLoadException {
		return this.getFiles(source, drillDown, false);
	}

	private ArrayList<File> getFiles(String source, boolean drillDown, boolean getDirectories) throws GoodreadsLoadException {

		ArrayList<File> files = new ArrayList<File>();

		File currDirectory = new File(source);

		if (currDirectory.isDirectory()) {

			String internalNames[] = currDirectory.list();

			for (int i = 0; i < internalNames.length; i++) {
				File currFile = new File(currDirectory.getAbsolutePath() + File.separator + internalNames[i]);

				if (currFile.exists() && ((currFile.isFile() && getDirectories == false) || (currFile.isDirectory() && getDirectories == true))) {
					files.add(currFile);
				} else if (drillDown == true && currFile.exists() && currFile.isDirectory()) {
					if (getDirectories == false) {
						files.addAll(this.getFiles(currFile.getAbsolutePath(), true));
					} else {
						files.addAll(this.getDirectories(currFile.getAbsolutePath(), true));
					}
				}
			}
		}

		return files;
	}

	private ArrayList<File> getSortedFiles(String source) throws GoodreadsLoadException {

		ArrayList<File> files = this.getFiles(source);

		// TODO: Sort
		return files;
	}

	private void log(Exception e) {
		Logger.log(e);
	}

	private void log(String message) {
		Logger.log(message);
	}

	private void organise() {

		try {

			// Retrieve a list of the user directories in the dataIn directory
			ArrayList<File> userDirectories = this.getDirectories(this.dataIn, false);

			// Process each in turn
			for (File currUserDirectory : userDirectories) {

				// Ignore the out directory
				if (!currUserDirectory.getName().equals("out")) {

					// Process the current user directory
					this.organise(currUserDirectory.getName() + File.separator);

				}
			}

		} catch (GoodreadsLoadException e) {

			Logger.log(e);
			System.err.println("No data found at " + this.dataIn);

			// Exit the application
			System.exit(1);

		}

	}

	/**
	 * Passes XML files into an XSL Transformation where data is merged, filtered and broken down into smaller entities.
	 * @param currDirectoryName
	 */
	private void organise(String currDirectoryName) {

		InputStream xslFile = null;

		// Determine path to source data directory
		String source = null;

		// Organise users
		source = DATA_DIRECTORY_REVIEWS_USER;
		xslFile = this.getClass().getResourceAsStream(File.separator + "xsl" + File.separator + "reviews.xsl");
		try {
			this.organise(currDirectoryName, source, xslFile);
		} catch (TransformerConfigurationException e) {
			this.log(e);
		} catch (GoodreadsLoadException e) {
			this.log(e);
		} catch (MalformedURLException e) {
			this.log(e);
		} catch (TransformerException e) {
			this.log(e);
		}

		// Organise books
		source = DATA_DIRECTORY_BOOKS;
		xslFile = this.getClass().getResourceAsStream(File.separator + "xsl" + File.separator + "books.xsl");
		try {
			this.organise(currDirectoryName, source, xslFile);
		} catch (TransformerConfigurationException e) {
			this.log(e);
		} catch (GoodreadsLoadException e) {
			this.log(e);
		} catch (MalformedURLException e) {
			this.log(e);
		} catch (TransformerException e) {
			this.log(e);
		}

		// Organise users
		source = DATA_DIRECTORY_USERS;
		xslFile = this.getClass().getResourceAsStream(File.separator + "xsl" + File.separator + "users.xsl");
		try {
			this.organise(currDirectoryName, source, xslFile);
		} catch (TransformerConfigurationException e) {
			this.log(e);
		} catch (GoodreadsLoadException e) {
			this.log(e);
		} catch (MalformedURLException e) {
			this.log(e);
		} catch (TransformerException e) {
			this.log(e);
		}

		/*
		 * Divide up each each user's reviews, putting half of each category,
		 * except unrated, into training and the other half into testing.
		 */

		System.out.println("Dividing reviews.");

		// Retrieve a list of the user directories in the reviews directory
		String reviewsRoot = this.dataOut + currDirectoryName + "reviews/";

		try {

			ArrayList<File> userDirectories = this.getDirectories(reviewsRoot, false);

			for (File currUserDirectory : userDirectories) {

				if (!currUserDirectory.getName().equals("testing") && !currUserDirectory.getName().equals("training")) {

					try {
						System.out.println("...written by user " + this.parseId(currUserDirectory));
					} catch (GoodreadsException e1) {
						this.log(e1);
					}

					String userRoot = currUserDirectory.getCanonicalPath();
					userRoot = userRoot + "/";

					try {
						System.out.println("...mediocre books,");
						this.divideFiles(reviewsRoot, userRoot + "mediocre/");
					} catch (GoodreadsLoadException e) {
						this.log(e);
					}
					try {
						System.out.println("...popular books,");
						this.divideFiles(reviewsRoot, userRoot + "popular/");
					} catch (GoodreadsLoadException e) {
						this.log(e);
					}
					try {
						System.out.println("...unpopular books,");
						this.divideFiles(reviewsRoot, userRoot + "unpopular/");
					} catch (GoodreadsLoadException e) {
						this.log(e);
					}

					Organiser.delete(currUserDirectory, true);
				}
			}
		} catch (GoodreadsLoadException e) {
			this.log(e);
		} catch (IOException e) {
			this.log(e);
		}
		System.out.print("\nComplete.\n\n");
	}

	private void organise(String currDirectoryName, String source, InputStream xslFile) throws GoodreadsLoadException, MalformedURLException, TransformerException {

		String dataInGoodreads = this.dataIn + currDirectoryName + DATA_DIRECTORY_GOODREADS;
		String dataInGoogle = this.dataIn + currDirectoryName + DATA_DIRECTORY_GOOGLE;
		String dataInOpenLibrary = this.dataIn + currDirectoryName + DATA_DIRECTORY_OPENLIBRARY;
		String tempLocation = this.dataOut + currDirectoryName + "temp/";

		// Create an instance of Saxon
		TransformerFactoryImpl factory = new TransformerFactoryImpl();

		// Create a resource resolver, for resolving
		// URIs from within XSL stylesheets
		ResourceResolver resourceResolver = new ResourceResolver();

		// Set the resource resolver on the FACTORY
		factory.setURIResolver(resourceResolver);

		// Create a transformer instance and load the XSL file
		Transformer transformer = factory.newTransformer(new StreamSource(xslFile));

		// Pass parameters through to the XSL file
		transformer.setParameter("dataRoot", dataInGoodreads);
		transformer.setParameter("saveLocation", tempLocation);
		transformer.setParameter("dataRootGoogle", dataInGoogle);
		transformer.setParameter("dataRootOpenLibrary", dataInOpenLibrary);
		transformer.setErrorListener(this.logger);

		// Build a list of the files to be processed
		ArrayList<File> files = this.getFiles(dataInGoodreads + source);

		System.out.println("Organising " + source);
		System.out.println("Total files: " + files.size());
		System.out.print("Processing...");

		for (int i = 0; i < files.size(); i++) {

			File currFile = files.get(i);
			System.out.print(".");

			if (source == DATA_DIRECTORY_REVIEWS_USER) {

				try {
					String userId = this.parseId(currFile);
					transformer.setParameter("focalUserId", userId);
				} catch (GoodreadsException e) {
					this.log(e);
				}

			} else if (source == DATA_DIRECTORY_BOOKS) {

				try {
					String bookId = this.parseId(currFile);
					transformer.setParameter("focalBookId", bookId);
				} catch (GoodreadsException e) {
					this.log(e);
				}

			}

			// Transform currFile (XML) using xslFile (XSL)
			try {
				transformer.transform(new StreamSource(currFile), new StreamResult(System.out));

				// Move the resulting files up a level, out of the temporary
				// director
				ArrayList<File> results = this.getFiles(tempLocation, true);
				for (File currResult : results) {
					Document in = XmlReader.read(currResult);
					String currLocation = currResult.getAbsolutePath();
					String newLocation = this.dataOut + currDirectoryName + (String) currLocation.subSequence(tempLocation.length(), currLocation.length());
					XmlWriter.write(in, newLocation);

					// Delete temporary file
					Organiser.delete(new File(currLocation), false);
				}

			} catch (TransformerException e) {
				this.log(e);
			} catch (XmlReadException e) {
				this.log(e);
			} catch (IOException e) {
				this.log(e);
			}

		}

		Organiser.delete(new File(tempLocation), true);
		System.out.print("\nComplete.\n\n");

	}

	private String parseId(File currFile) throws GoodreadsException {
		String id = currFile.getName().split("_")[0];
		if (id.equals("")) {
			id = currFile.getName().split(".")[0];
		}
		if (id != "") {
			return id;
		}
		throw new GoodreadsException("Failed to extract ID from " + currFile.getName());
	}

	public static void main(String[] args) {
		new GoodreadsDataOrganiser(ArgsInterpreter.interpret(args, VALID_OPTION_SWITCHES));
	}
}