package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsBook;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsFeedback;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsReview;

public abstract class Profile {

	protected GoodreadsFeedback feedback;

	protected HashSet<GoodreadsReview> reviewsAll;
	protected HashSet<GoodreadsReview> reviewsPopular;
	protected HashSet<GoodreadsReview> reviewsUnpopular;

	protected HashSet<Report> reports;

	public Profile(GoodreadsFeedback currFeedback) {

		this.feedback = currFeedback;

		this.reviewsAll = this.feedback.getReviews();
		this.reviewsPopular = this.feedback.getReviews(4, 5);
		this.reviewsUnpopular = this.feedback.getReviews(1, 2);

		this.reports = new HashSet<Report>();

		this.generate();
	}

	public abstract boolean apply(GoodreadsBook currBook) throws ReportNotFoundException;

	public boolean apply(GoodreadsBook currBook, Integer actualValue) throws ReportNotFoundException {

		// Retrieve the name of the measure that was optimal for
		// identifying popular books, for this factor
		Measure optimalMeasure = this.getReport(Scope.POPULAR).getOptimalMeasure();
		// Measure optimalMeasure = Measure.MODE;

		return this.apply(currBook, optimalMeasure, actualValue);
	}

	public abstract boolean apply(GoodreadsBook currBook, Measure currMeasure) throws ReportNotFoundException;

	public boolean apply(GoodreadsBook currBook, Measure currMeasure, Integer actualValue) throws ReportNotFoundException {

		if (actualValue == null) {
			return false;
		}

		/*
		 * Assess the likelihood that the owner of this profile will enjoy the
		 * book specified, using the optimal measure for this factor.
		 */

		// Optimal measure == RANGE
		if (currMeasure.equals(Measure.RANGE)) {

			int[] currRange = this.getRange(Scope.POPULAR);
			if (actualValue >= currRange[0] && actualValue <= currRange[1]) {
				return true;
			}

		}

		// Retrieve the value sought for the optimal measure
		Integer sought = null;
		if (currMeasure.equals(Measure.MEAN)) {

			// Optimal measure == MEAN
			sought = this.getMean(Scope.POPULAR);

		} else if (currMeasure.equals(Measure.MEDIAN)) {

			// Optimal measure == MEDIAN
			sought = this.getMedian(Scope.POPULAR);

		} else if (currMeasure.equals(Measure.MODE)) {

			// Optimal measure == MODE
			sought = this.getMode(Scope.POPULAR);

		}

		// Check whether the equivalent value for this book matches the value of
		// the optimal measure
		if (actualValue.equals(sought)) {
			return true;
		}

		return false;
	}

	private double assessSignificanceOfMeasure(int sought) {

		double totalMatches = 0;
		for (GoodreadsReview currReview : this.reviewsPopular) {

			if (this.evaluateAttribute(currReview.getBook(), sought)) {
				totalMatches = totalMatches + 1;
			}
		}

		double percentage = 0;
		if (totalMatches > 0) {
			percentage = (totalMatches / this.reviewsPopular.size()) * 100;
		}

		return percentage;
	}

	private double assessSignificanceOfMeasure(int[] sought) {

		double totalMatches = 0;
		for (GoodreadsReview currReview : this.reviewsPopular) {

			if (this.evaluateAttribute(currReview.getBook(), sought)) {
				totalMatches++;
			}
		}

		double percentage = 0;
		if (totalMatches > 0) {
			percentage = (totalMatches / this.reviewsPopular.size()) * 100;
		}

		return percentage;
	}

	/**
	 * Evaluates the effectiveness of each measure in correctly identifying
	 * popular books. The most effective measure is the one that will be used
	 * when this factor profile is applied.
	 * 
	 * @param currData
	 * the data set on popular books
	 * 
	 * @throws ReportNotFoundException
	 */
	protected Measure determineOptimalMeasure(TreeMap<Integer, Integer> currData) {

		// Assign the responses to a TreeMap, along with their Measure name
		// The values will automatically be kept in ascending order by the key
		// (double).
		TreeMap<Double, Measure> measures = new TreeMap<Double, Measure>();
		measures.put(this.assessSignificanceOfMeasure(getMean(currData)), Measure.MEAN);
		measures.put(this.assessSignificanceOfMeasure(getMode(currData)), Measure.MODE);
		measures.put(this.assessSignificanceOfMeasure(getMedian(currData)), Measure.MEDIAN);
		measures.put(this.assessSignificanceOfMeasure(getRange(currData)), Measure.RANGE);

		return measures.lastEntry().getValue();
	}

	/**
	 * Evaluates the value of the attribute that's the focus of the current
	 * factor, to see if matches the value sought. For example, where the factor
	 * is title length, the attribute is book title and if the length of the
	 * title matches the value sought then the method will return true;
	 * otherwise the method will return false.
	 * 
	 * @param currBook
	 * the book currently being evaluated.
	 * 
	 * @param sought
	 * the value sought in the evaluation.
	 * 
	 * @return true if the value is met, false if not.
	 */
	protected abstract boolean evaluateAttribute(GoodreadsBook currBook, int sought);

	/**
	 * Evaluates the value of the attribute that's the focus of the current
	 * factor, to see if it falls within the range sought. For example, where
	 * the factor is title length, the attribute is book title and if the length
	 * of the title falls within the range sought then the method will return
	 * true; otherwise the method will return false.
	 * 
	 * @param currBook
	 * the book currently being evaluated.
	 * 
	 * @param sought
	 * an array containing the lower and upper bounds of the range of values
	 * sought in the evaluation.
	 * 
	 * @return true if the value is met, false if not.
	 */
	protected abstract boolean evaluateAttribute(GoodreadsBook currBook, int[] sought);

	/**
	 * Generates the profile and assigns the findings to a report, including the
	 * determination of which Measure will be used whenever this profile is
	 * applied.
	 */
	protected abstract void generate();

	public int getMean() {
		return this.getMean(Scope.ALL);
	}

	public int getMean(Scope currScope) {

		int mean = 0;
		for (Report currReport : this.reports) {
			if (currReport.getScope() == currScope) {
				mean = getMean(currReport.getData());
			}
		}
		return mean;
	}

	public int getMedian() {
		return this.getMedian(Scope.ALL);
	}

	public int getMedian(Scope currScope) {

		int median = 0;
		for (Report currReport : this.reports) {
			if (currReport.getScope() == currScope) {
				median = getMedian(currReport.getData());
			}
		}
		return median;
	}

	public int getMode() {
		return this.getMode(Scope.ALL);
	}

	public int getMode(Scope currScope) {

		int mode = 0;
		for (Report currReport : this.reports) {
			if (currReport.getScope() == currScope) {
				mode = getMode(currReport.getData());
			}
		}
		return mode;
	}

	public int[] getRange() {
		return this.getRange(Scope.ALL);
	}

	public int[] getRange(Scope currScope) {

		int[] range = { 0, 0 };
		for (Report currReport : this.reports) {
			if (currReport.getScope() == currScope) {
				range = getRange(currReport.getData());
			}
		}
		return range;
	}

	public Report getReport(Scope currScope) throws ReportNotFoundException {

		for (Report currReport : this.reports) {
			if (currReport.getScope() == currScope) {
				return currReport;
			}
		}
		throw new ReportNotFoundException();
	}

	@Override
	public String toString() {

		String out = "";
		for (Report currReport : this.reports) {
			out = out + currReport.toString() + "\n";
		}
		return out;
	}

	public static int getMean(TreeMap<Integer, Integer> data) {
		int mean = 0;
		if (data != null && data.size() > 0) {

			int sum = getSum(data);
			int totalOccurrences = getTotalOccurrences(data);
			if (sum > 0 && totalOccurrences > 0) {
				mean = sum / totalOccurrences;
			}
		}
		return mean;
	}

	public static int getMedian(TreeMap<Integer, Integer> data) {

		int median = 0;
		if (data != null && data.size() > 0) {

			// Get a DescriptiveStatistics instance
			DescriptiveStatistics stats = new DescriptiveStatistics();

			// Add the data from the TreeMap
			Set<Integer> keys = data.keySet();
			for (Integer key : keys) {
				int value = data.get(key);
				for (int i = 0; i < value; i++) {
					stats.addValue(key);
				}
			}

			median = (int) stats.getPercentile(50);

		}

		return median;
	}

	public static int getMode(TreeMap<Integer, Integer> data) {
		int mode = 0;
		if (data != null && data.size() > 0) {
			TreeMap<Integer, Integer> sortedByValue = sortByValue(data);
			mode = sortedByValue.firstKey();
		}
		return mode;
	}

	public static int[] getRange(TreeMap<Integer, Integer> data) {

		int[] range = new int[2];
		int mode = 0;
		int mean = 0;

		if (data != null && data.size() > 0) {

			mean = getMode(data);
			mode = getMean(data);

			if (mean > mode) {
				range[0] = mode;
				range[1] = mean;
			} else {
				range[0] = mean;
				range[1] = mode;
			}
		}
		return range;
	}

	public static int getSum(TreeMap<Integer, Integer> data) {
		int sum = 0;
		if (data != null && data.size() > 0) {
			Set<Integer> keys = data.keySet();
			for (Integer key : keys) {
				sum = sum + (key * data.get(key));
			}
		}
		return sum;
	}

	public static int getTotalOccurrences(TreeMap<Integer, Integer> data) {
		int total = 0;
		if (data != null && data.size() > 0) {
			Collection<Integer> values = data.values();
			for (Integer currValue : values) {
				total = total + currValue;
			}
		}
		return total;
	}

	/**
	 * Sorts a collection of key value pairs into ascending order by value.
	 * 
	 * @param data
	 * the collection to be sorted.
	 * 
	 * @return the collection sorted into ascending order by value.
	 */
	public static TreeMap<Integer, Integer> sortByValue(TreeMap<Integer, Integer> data) {

		ComparatorValueIntegerInteger comparator = new ComparatorValueIntegerInteger(data);
		TreeMap<Integer, Integer> sortedByValue = new TreeMap<Integer, Integer>(comparator);
		sortedByValue.putAll(data);

		return sortedByValue;
	}
}
