package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import co.uk.bluegumtree.code.java.util.Formatter;
import co.uk.bluegumtree.code.java.util.XmlWriter;

public class Report {

	private Scope scope;
	private String title;
	private TreeMap<Integer, Integer> data;
	private int totalBooksNotFound;
	private int totalPropertyNotFound;
	private String propertyNotFoundName;
	private Measure optimalMeasure;

	public Report(TreeMap<Integer, Integer> currData, Scope currScope, String currTitle) {
		this.data = currData;
		this.scope = currScope;
		this.title = currTitle;
	}

	public TreeMap<Integer, Integer> getData() {
		return this.data;
	}

	public Measure getOptimalMeasure() {
		return this.optimalMeasure;
	}

	public Scope getScope() {
		return this.scope;
	}

	public String getTitle() {
		return this.title;
	}

	public int getTotalBooksNotFound() {
		return this.totalBooksNotFound;
	}

	public int getTotalPropertyNotFound() {
		return this.totalPropertyNotFound;
	}

	public String getTotalPropertyNotFoundName() {
		return this.propertyNotFoundName;
	}

	public void setOptimalMeasure(Measure currMeasure) {
		this.optimalMeasure = currMeasure;
	}

	public void setTitle(String currTitle) {
		this.title = currTitle;
	}

	public void setTotalBooksNotFound(int currTotal) {
		this.totalBooksNotFound = currTotal;
	}

	public void setTotalPropertyNotFound(int currTotal, String currPropertyName) {
		this.totalPropertyNotFound = currTotal;
		this.propertyNotFoundName = currPropertyName;
	}

	public String statsToString() {

		// Sort the key-value pairs into ascending order by value
		TreeMap<Integer, Integer> currData = Profile.sortByValue(this.data);

		// Create an instance of the Apache Maths DescriptiveStatistics object
		DescriptiveStatistics stats = new DescriptiveStatistics();

		// Populate the DescriptiveStatistics object
		Set<Integer> keys = currData.keySet();
		for (Integer key : keys) {
			int value = currData.get(key);
			for (int i = 0; i < value; i++) {
				stats.addValue(key);
			}
		}

		// Calculate the mean (Apache)
		int mean = (int) stats.getMean();

		// Calculate the mode (custom)
		int mode = Profile.getMode(currData);

		// Calculate the median (Apache)
		int median = (int) stats.getPercentile(50);

		// Calculate the standard deviation (Apache)
		int standardDeviation = (int) stats.getStandardDeviation();

		/*
		 * Calculate potentially useful ranges
		 */

		// Using standard deviation (Apache)
		int rangeMin = mean - standardDeviation;
		int rangeMax = mean + standardDeviation;

		// Using the mean and mode (custom)
		int[] range = Profile.getRange(currData);

		// Build the output string
		String out = "\n";
		out = out + "Mode: " + mode + "\n";
		out = out + "Mean: " + mean + "\n";
		out = out + "Median: " + median + "\n";
		out = out + "Standard Deviation: " + standardDeviation + "\n";
		out = out + "Range (Standard Deviation): " + rangeMin + " - " + rangeMax + "\n";
		out = out + "Range (Mean/Mode): " + range[0] + " - " + range[1] + "\n";

		// If this is reporting on popular books,
		// include the name of the optimal measure
		// if (this.scope.equals(Scope.POPULAR)) {
		out = out + "Optimal measure: " + this.optimalMeasure + "\n";
		// }

		// Add extra spacing.
		out = out + "\n";

		return out;

	}

	private Element statsToXml(Document currDocument) throws ParserConfigurationException {

		// Sort the key-value pairs into ascending order by value
		TreeMap<Integer, Integer> currData = Profile.sortByValue(this.data);

		// Create an instance of the Apache Maths DescriptiveStatistics object
		DescriptiveStatistics stats = new DescriptiveStatistics();

		// Populate the DescriptiveStatistics object
		Set<Integer> keys = currData.keySet();
		for (Integer key : keys) {
			int value = currData.get(key);
			for (int i = 0; i < value; i++) {
				stats.addValue(key);
			}
		}

		// Calculate the mean (Apache)
		int mean = (int) stats.getMean();

		// Calculate the mode (custom)
		int mode = Profile.getMode(currData);

		// Calculate the median (Apache)
		int median = (int) stats.getPercentile(50);

		// Calculate the standard deviation (Apache)
		int standardDeviation = (int) stats.getStandardDeviation();

		/*
		 * Calculate potentially useful ranges
		 */

		// Using standard deviation (Apache)
		int rangeMin = mean - standardDeviation;
		int rangeMax = mean + standardDeviation;

		// Using the mean and mode (custom)
		int[] range = Profile.getRange(currData);

		/*
		 * Build and populate the XML
		 */

		// Create the root element, <statistics />
		Element rootElement = XmlWriter.toElement(currDocument, "statistics");

		// Append the mode to the root element, <mode />
		Element modeElement = XmlWriter.toElement(currDocument, "measure", String.valueOf(mode));
		modeElement.setAttribute("name", "mode");
		if (this.optimalMeasure.equals(Measure.MODE)) {
			modeElement.setAttribute("optimal", "true");
		}
		rootElement.appendChild(modeElement);

		// Append the mean to the root element, <mean />
		Element meanElement = XmlWriter.toElement(currDocument, "measure", String.valueOf(mean));
		meanElement.setAttribute("name", "mean");
		if (this.optimalMeasure.equals(Measure.MEAN)) {
			meanElement.setAttribute("optimal", "true");
		}
		rootElement.appendChild(meanElement);

		// Append the median to the root element, <median />
		Element medianElement = XmlWriter.toElement(currDocument, "measure", String.valueOf(median));
		medianElement.setAttribute("name", "median");
		if (this.optimalMeasure.equals(Measure.MEDIAN)) {
			medianElement.setAttribute("optimal", "true");
		}
		rootElement.appendChild(medianElement);

		// Append the ranges to the root element, <range />
		for (int i = 0; i < 2; i++) {

			Element rangeElement = XmlWriter.toElement(currDocument, "measure");
			rangeElement.setAttribute("name", "range");
			Element lowerBoundElement = XmlWriter.toElement(currDocument, "min");
			Element upperBoundElement = XmlWriter.toElement(currDocument, "max");
			if (i == 0) {

				// Standard deviation range, <range
				// standardDeviation="true"><min /><max /></range>
				rangeElement.setAttribute("standardDeviation", "true");
				lowerBoundElement.setTextContent(String.valueOf(rangeMin));
				upperBoundElement.setTextContent(String.valueOf(rangeMax));

			} else {

				// Custom range, <range standardDeviation="false"><min /><max
				// /></range>
				rangeElement.setAttribute("standardDeviation", "false");
				if (this.optimalMeasure.equals(Measure.RANGE)) {
					rangeElement.setAttribute("optimal", "true");
				}
				lowerBoundElement.setTextContent(String.valueOf(range[0]));
				upperBoundElement.setTextContent(String.valueOf(range[1]));

			}

			rangeElement.appendChild(lowerBoundElement);
			rangeElement.appendChild(upperBoundElement);
			rootElement.appendChild(rangeElement);
		}

		currDocument.getDocumentElement().appendChild(rootElement);

		return rootElement;

	}

	public String toGraph() {

		String out = "";

		// Sort the key-value pairs into ascending order by value
		TreeMap<Integer, Integer> currData = Profile.sortByValue(this.data);

		// Retrieve a list of the keys
		Set<Integer> keys = currData.keySet();

		// Determine the length of the longest key
		int maxKeyLength = 0;
		for (Object currKey : keys) {
			int currKeyLength = currKey.toString().length();
			if (currKeyLength > maxKeyLength) {
				maxKeyLength = currKeyLength;
			}
		}

		// Add the total number of keys to the graph string
		out = out + currData.size() + " values\n\n";

		// Process the key-value pairs in key order,
		// to build each bar in the graph
		for (Object key : currData.keySet()) {

			// Prepend the value of the key to the bar
			int totalOccurrences = currData.get(key);
			out = out + Formatter.alignLeft(key.toString(), maxKeyLength) + " : ";

			// Build the bar
			String bar = "";
			for (int i = 0; i < totalOccurrences; i++) {
				bar = bar + "\u2591";
			}

			// Append the value for the total occurrences to the end of the bar
			out = out + bar + " (" + totalOccurrences + ")\n";

		}

		return out + "\n";
	}

	@Override
	public String toString() {

		String out = "\n\n";

		out = out + this.getTitle() + "\n\n";

		if (this.data.size() < 1) {
			return out + "No data to profile.\n\n\n";
		}

		out = out + this.toGraph();
		out = out + this.statsToString();

		out = out + "Total books not found: " + this.totalBooksNotFound + "\n";
		out = out + "Total " + this.propertyNotFoundName + " not found: " + this.totalPropertyNotFound + "\n";

		out = out + "\n\n";

		return out;

	}

	public Document toXml() throws ParserConfigurationException {

		// Create an XML document
		Document currDocument = XmlWriter.createXmlDocument();

		// Create the root element, <profile />
		Element rootElement = XmlWriter.toElement(currDocument, "profile");
		rootElement.setAttribute("scope", this.scope.toString());

		// Append the root element to the document
		currDocument.appendChild(rootElement);

		// Append the title to the root element, <title />
		rootElement.appendChild(XmlWriter.toElement(currDocument, "title", this.title));

		// Create a wrapper element for the data, <data />
		Element dataElement = XmlWriter.toElement(currDocument, "data");

		// Loop through all the key value data pairs, <value @occurrences />
		Set<Integer> keys = this.data.keySet();
		for (Integer currKey : keys) {

			Element dataRowElement = XmlWriter.toElement(currDocument, "value", currKey.toString());
			dataRowElement.setAttribute("occurrences", this.data.get(currKey).toString());

			// Append this row to the data element
			dataElement.appendChild(dataRowElement);
		}

		// Append the data to the root element
		rootElement.appendChild(dataElement);

		// Build and append the XML for the statistics
		this.statsToXml(currDocument);

		return currDocument;
	}
}
