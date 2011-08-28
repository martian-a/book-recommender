package uk.co.bluegumtree.code.java.goodreads.recommender.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.TransformerFactoryImpl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uk.co.bluegumtree.code.java.goodreads.recommender.model.Evaluation;
import uk.co.bluegumtree.code.java.goodreads.recommender.model.EvaluationNotFoundException;
import uk.co.bluegumtree.code.java.goodreads.recommender.model.RecommendationSet;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsLibrary;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsUser;
import co.uk.bluegumtree.code.java.util.Location;
import co.uk.bluegumtree.code.java.util.Logger;
import co.uk.bluegumtree.code.java.util.Organiser;
import co.uk.bluegumtree.code.java.util.ResourceResolver;
import co.uk.bluegumtree.code.java.util.XmlWriter;

public class Assessor {

	// private HashMap<GoodreadsUser, HashSet<GoodreadsBook>> recommendations;
	// private GoodreadsLibrary library;
	private ArrayList<Evaluation> assessment;

	public Assessor(HashMap<GoodreadsUser, ArrayList<RecommendationSet>> currRecommendations, GoodreadsLibrary currLibrary) {
		// this.recommendations = currRecommendations;
		// this.library = currLibrary;

		this.assessment = new ArrayList<Evaluation>();
		this.assess(currLibrary, currRecommendations);
	}

	/**
	 * Evaluates the effectiveness of the recommender
	 */
	public void assess(GoodreadsLibrary currLibrary, HashMap<GoodreadsUser, ArrayList<RecommendationSet>> allRecommendations) {

		Set<GoodreadsUser> users = allRecommendations.keySet();
		for (GoodreadsUser currUser : users) {

			ArrayList<RecommendationSet> allCurrUserRecommendationSets = allRecommendations.get(currUser);

			for (RecommendationSet currRecommendationSet : allCurrUserRecommendationSets) {

				Evaluation currEvaluation = new Evaluation(currUser, currLibrary, currRecommendationSet);

				// Add the result for this user to assessment
				this.assessment.add(currEvaluation);

				currEvaluation.save();
				System.out.println(currEvaluation.toString());
			}

			try {
				this.merge(currUser);

			} catch (TransformerConfigurationException e) {
				Logger.log(e);
			}

		}

		// Merge all of the individual user XML files into one
		try {
			this.merge();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Evaluation getEvaluation(GoodreadsUser currUser) throws EvaluationNotFoundException {

		// Loop through all the evaluation records, looking for one for the user
		// specified
		for (Evaluation currEvaluation : this.assessment) {

			// Evaluation record found, return it.
			if (currEvaluation.getUser().equals(currUser)) {
				return currEvaluation;
			}
		}

		// A record wasn't found for the user specified
		throw new EvaluationNotFoundException("Failed to find a evaluation record for user: " + currUser.getId());
	}

	private void merge() throws ParserConfigurationException, IOException, TransformerConfigurationException {

		System.out.println("Merging results with all users results...");
		HashSet<File> userDirectories = Organiser.getDirectories(new File(Location.getSaveDir(GoodreadsRecommender.SAVE_DIRECTORY)));

		Document masterDocument = XmlWriter.createXmlDocument();
		Element rootElement = masterDocument.createElement("directory");
		masterDocument.appendChild(rootElement);

		for (File currUserDirectory : userDirectories) {

			File currFile = new File(currUserDirectory.getCanonicalPath() + "/results/merged.xml");

			if (currFile.exists()) {

				Element currElement = XmlWriter.append(masterDocument, rootElement, "file");
				currElement.setAttribute("src", currFile.getCanonicalPath());
			}
		}

		String saveLocation = Location.getSaveDir(GoodreadsRecommender.SAVE_DIRECTORY);

		File mergeList = new File(saveLocation + "merge_list.xml");
		XmlWriter.write(masterDocument, mergeList);

		InputStream xslFile = this.getClass().getResourceAsStream(File.separator + "xsl" + File.separator + "merge_evaluations_all_users.xsl");

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
		String pathToMergedXml = saveLocation + "merged_all_users.xml";
		transformer.setParameter("saveLocation", pathToMergedXml);
		transformer.setErrorListener(new Logger());

		// Transform currFile (XML) using xslFile (XSL)
		try {

			transformer.transform(new StreamSource(mergeList), new StreamResult(System.out));
			System.out.println("...complete.");

		} catch (TransformerException e) {
			Logger.log(e);
		}

		/*
		 * Create an HTML view of the combined data
		 */
		System.out.println("Creating HTML overview of data...");

		// Load the XSL file
		xslFile = this.getClass().getResourceAsStream(File.separator + "xsl" + File.separator + "view_evaluations_all_users.xsl");
		transformer = factory.newTransformer(new StreamSource(xslFile));

		// Pass parameters through to the XSL file
		transformer.setParameter("saveLocation", saveLocation + "merged_all_users.html");
		transformer.setErrorListener(new Logger());

		// Transform currFile (XML) using xslFile (XSL)
		try {

			transformer.transform(new StreamSource(pathToMergedXml), new StreamResult(System.out));
			System.out.println("...complete.");

		} catch (TransformerException e) {
			Logger.log(e);
		}
	}

	private void merge(GoodreadsUser currUser) throws TransformerConfigurationException {

		String dataLocation = Location.getSaveDir(GoodreadsRecommender.SAVE_DIRECTORY + currUser.getId() + File.separator + "results" + File.separator);
		String saveLocation = dataLocation;

		InputStream xslFile = this.getClass().getResourceAsStream(File.separator + "xsl" + File.separator + "merge_evaluations.xsl");

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
		transformer.setParameter("dataLocation", dataLocation);
		transformer.setParameter("saveLocation", saveLocation);
		transformer.setErrorListener(new Logger());

		// Build a list of the files to be processed
		File xmlFile = new File(dataLocation + "all_OPTIMAL.xml");

		System.out.println("Merging results...");

		// Transform currFile (XML) using xslFile (XSL)
		try {

			transformer.transform(new StreamSource(xmlFile), new StreamResult(System.out));
			System.out.println("...complete.");

		} catch (TransformerException e) {
			Logger.log(e);
		}

	}

	/**
	 * Saves XML and HTML views of each evaluation in this assessment.
	 */
	public void save() {

		// Loop through all the evaluation records
		for (Evaluation currEvaluation : this.assessment) {

			currEvaluation.save();

		}
	}

	@Override
	public String toString() {

		String out = "\n\n";

		// Loop through all the evaluation records
		for (Evaluation currEvaluation : this.assessment) {

			out = out + currEvaluation.toString();

		}

		return out;
	}

}
