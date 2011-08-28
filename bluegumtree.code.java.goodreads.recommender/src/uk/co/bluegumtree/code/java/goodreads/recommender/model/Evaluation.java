package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.TransformerFactoryImpl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uk.co.bluegumtree.code.java.goodreads.recommender.controller.GoodreadsRecommender;
import co.uk.bluegumtree.code.java.api.goodreads.BookNotFoundException;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsBook;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsLibrary;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsUser;
import co.uk.bluegumtree.code.java.api.goodreads.ReviewNotFoundException;
import co.uk.bluegumtree.code.java.util.Location;
import co.uk.bluegumtree.code.java.util.Logger;
import co.uk.bluegumtree.code.java.util.ResourceResolver;
import co.uk.bluegumtree.code.java.util.Table;
import co.uk.bluegumtree.code.java.util.Table.Align;
import co.uk.bluegumtree.code.java.util.XmlWriter;

/**
 * Collects together the evaluations of the recommendations made for a specific
 * user.
 * 
 * @author Sheila Ellen Thomson
 * 
 */
public class Evaluation {

	/**
	 * The user whose recommendations are being evaluated.
	 */
	private GoodreadsUser user;

	/**
	 * The evaluations of each recommendation, or lack of.
	 */
	private RecommendationSet results;

	/**
	 * The (rated) books from which the generations were generated
	 */
	private GoodreadsLibrary library;

	public Evaluation(GoodreadsUser currUser, GoodreadsLibrary currBooks, RecommendationSet currRecommendations) {
		this.user = currUser;
		this.results = currRecommendations;
		this.library = currBooks;
		this.evaluate();
	}

	private void evaluate() {

		HashSet<Recommendation> allRecommendations = this.results.getRecommendations();
		for (Recommendation currRecommendation : allRecommendations) {

			try {

				// Retrieve the rating that the user actually gave the book
				// and add it to the recommendation object
				GoodreadsBook ratedBook = this.library.getBook(currRecommendation.getBook().getId());
				currRecommendation.setRating(ratedBook.getReview(this.user).getRating());

			} catch (ReviewNotFoundException e) {
				Logger.log("Attempt to evaluate a book that the current user (" + this.user.getId() + ") hasn't yet read.");
			} catch (InvalidRatingException e) {
				Logger.log("Attempted to set an invalid rating value.");
			} catch (BookNotFoundException e) {
				Logger.log("Failed to find book " + currRecommendation.getBook().getId() + " in the evaluation library.");
			}
		}
	}

	public double getAccuracy() {

		double accuracy = 0.0;

		double totalRecommendations = this.results.size();
		double totalAccurateRecommendations = 0;

		HashSet<Recommendation> currResults = this.results.getRecommendations();
		for (Recommendation currResult : currResults) {
			if (currResult.getAccurate()) {
				totalAccurateRecommendations++;
			}
		}

		if (totalAccurateRecommendations > 0) {
			accuracy = (totalAccurateRecommendations / totalRecommendations) * 100;
		}

		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(accuracy));
	}

	/**
	 * @return the collection of results of the recommendations for this user
	 */
	public RecommendationSet getResults() {
		return this.results;
	}

	/**
	 * @return the user whose recommendations are being evaluated
	 */
	public GoodreadsUser getUser() {
		return this.user;
	}

	public void save() {

		Factor currFactor = this.results.getFactor();
		Measure currMeasure = this.results.getMeasure();

		String currSaveLocation = Location.getSaveDir(GoodreadsRecommender.SAVE_DIRECTORY + this.user.getId() + File.separator + "results" + File.separator);

		try {

			String filename = currFactor.getFilename() + "_" + currMeasure.toString() + ".xml";
			Document currDocument = this.toXml();
			File xmlFile = new File(currSaveLocation + filename);

			XmlWriter.write(currDocument, currSaveLocation + filename);
			this.toHtml(xmlFile);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (InvalidFactorException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Generates an HTML view of this evaluation.
	 */
	private void toHtml(File xmlFile) throws TransformerConfigurationException {

		// Set the XSL file
		InputStream xslFile = this.getClass().getResourceAsStream(File.separator + "xsl" + File.separator + "evaluation.xsl");

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
		transformer.setParameter("saveLocation", Location.getSaveDir(GoodreadsRecommender.SAVE_DIRECTORY));
		transformer.setErrorListener(new Logger());

		// Transform currFile (XML) using xslFile (XSL)
		try {
			transformer.transform(new StreamSource(xmlFile), new StreamResult(System.out));
		} catch (TransformerException e) {
			Logger.log(e);
		}

	}

	@Override
	public String toString() {

		GoodreadsUser currUser = this.getUser();

		String out = "\n\n";

		String tableTitle = "Recommendations for user: " + currUser.getUsername() + " [" + currUser.getId() + "]\nUsing " + this.results.getFactor() + " and " + this.results.getMeasure() + ".\n\n";

		ArrayList<String[]> tableRows = new ArrayList<String[]>();
		String[] headerRow = { "Title", "ID", "Recommended", "Grade", "Rating", "Valid" };
		tableRows.add(headerRow);

		HashSet<Recommendation> allEvaluations = this.results.getRecommendations();

		for (Recommendation currResult : allEvaluations) {

			GoodreadsBook currBook = currResult.getBook();

			String[] currRow = new String[6];
			currRow[0] = currBook.getTitle();
			currRow[1] = String.valueOf(currBook.getId());
			currRow[2] = String.valueOf(currResult.isRecommended());
			currRow[3] = String.valueOf(currResult.getGrade());
			currRow[4] = String.valueOf(currResult.getRating());
			currRow[5] = String.valueOf(currResult.getAccurate());

			tableRows.add(currRow);
		}

		Table currTable = new Table(tableRows, 1, 1);
		currTable.setTitle(tableTitle);
		currTable.format(new Align[] { Align.LEFT, Align.LEFT, Align.CENTRE, Align.CENTRE, Align.CENTRE, Align.CENTRE });

		return out + currTable.toString() + "\nOverall Accuracy: " + this.getAccuracy() + "\n\n";
	}

	public Document toXml() throws ParserConfigurationException {

		// Create an XML document
		Document currDocument = XmlWriter.createXmlDocument();

		// Create the root element, <user />
		Element rootElement = XmlWriter.toElement(currDocument, "user");
		rootElement.setAttribute("id", this.user.getId().toString());

		// Append the root element to the document
		currDocument.appendChild(rootElement);

		// Create a wrapper element for the books
		// and append it to the user element, <books />
		Element booksElement = XmlWriter.append(currDocument, rootElement, "books");

		// Create a wrapper element for the recommendations
		// and append it to the user element, <recommendations />
		Element recommendationsElement = XmlWriter.append(currDocument, rootElement, "recommendations");
		recommendationsElement.setAttribute("accuracy", String.valueOf(this.getAccuracy()));
		recommendationsElement.setAttribute("factor", this.getResults().getFactor().toString());
		recommendationsElement.setAttribute("measure", this.getResults().getMeasure().toString());

		// Loop through all the evaluations
		HashSet<Recommendation> allEvaluations = this.getResults().getRecommendations();

		for (Recommendation currResult : allEvaluations) {

			GoodreadsBook currBook = currResult.getBook();

			// Append the XML for the current book to books, <books><book
			// /></books>
			Element currBookElement = currBook.toXml(currDocument, booksElement);
			currBookElement.setAttribute("rating", String.valueOf(currResult.getRating()));

			Element currRecommendationElement = XmlWriter.append(currDocument, recommendationsElement, "recommendation", currResult.getGrade());
			currRecommendationElement.setAttribute("book", String.valueOf(currBook.getId()));
			currRecommendationElement.setAttribute("accurate", String.valueOf(currResult.getAccurate()));
		}

		return currDocument;
	}

}
