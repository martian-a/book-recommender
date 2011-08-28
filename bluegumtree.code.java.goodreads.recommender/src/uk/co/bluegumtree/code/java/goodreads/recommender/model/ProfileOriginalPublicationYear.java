package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import java.util.HashSet;
import java.util.TreeMap;

import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsBook;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsFeedback;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsReview;

public class ProfileOriginalPublicationYear extends Profile {

	public ProfileOriginalPublicationYear(GoodreadsFeedback currFeedback) {
		super(currFeedback);
	}

	@Override
	public boolean apply(GoodreadsBook currBook) throws ReportNotFoundException {
		return super.apply(currBook, currBook.getOriginalPublicationYear());
	}

	@Override
	public boolean apply(GoodreadsBook currBook, Measure currMeasure) throws ReportNotFoundException {
		return super.apply(currBook, currMeasure, currBook.getOriginalPublicationYear());
	}

	/**
	 * Evaluates the original publication year of the current book to see if it
	 * matches the year sought.
	 * 
	 * @param currBook
	 * the book currently being evaluated.
	 * 
	 * @param sought
	 * the year sought
	 * 
	 * @return true if the book's year of original publication matches the value
	 * sought; false if not.
	 */
	@Override
	protected boolean evaluateAttribute(GoodreadsBook currBook, int sought) {

		Integer currYear = currBook.getOriginalPublicationYear();
		if (currYear != null && currYear.equals(sought)) {
			return true;
		}
		return false;
	}

	/**
	 * Evaluates the original publication year of the current book to see if it
	 * falls within the range sought.
	 * 
	 * @param currBook
	 * the book currently being evaluated.
	 * 
	 * @param minSought
	 * the lower bound of the range of years
	 * 
	 * @param maxSought
	 * the upper bound of the range of years
	 * 
	 * @return true if the length of the book's original publication year
	 * matches the year sought; false if not.
	 */
	@Override
	protected boolean evaluateAttribute(GoodreadsBook currBook, int[] sought) {

		Integer currLength = currBook.getOriginalPublicationYear();
		if (currLength != null && (currLength >= sought[0] && currLength <= sought[1])) {
			return true;
		}
		return false;
	}

	@Override
	protected void generate() {

		this.reports.add(this.generate(this.reviewsAll, Scope.ALL, "Publication Year (Original, All)"));
		this.reports.add(this.generate(this.reviewsPopular, Scope.POPULAR, "Publication Year (Original, Popular)"));
		this.reports.add(this.generate(this.reviewsUnpopular, Scope.UNPOPULAR, "Publication Year (Original, Unpopular)"));

	}

	private Report generate(HashSet<GoodreadsReview> currReviews, Scope currScope, String currReportTitle) {

		TreeMap<Integer, Integer> currResult = new TreeMap<Integer, Integer>();

		int totalBooksNotFound = 0;
		int totalYearsNotFound = 0;

		for (GoodreadsReview currReview : currReviews) {

			// Get the book that this review is about
			GoodreadsBook currBook = currReview.getBook();

			if (currBook == null) {

				totalBooksNotFound++;

			} else {

				// Get the book's original publication year
				Integer currYear = currBook.getOriginalPublicationYear();

				if (currYear == null) {

					totalYearsNotFound++;

				} else {

					// Update the stored totals
					if (currResult.containsKey(currYear)) {
						int newTotal = currResult.get(currYear) + 1;
						currResult.put(currYear, newTotal);
					} else {
						currResult.put(currYear, 1);
					}
				}
			}
		}

		Report currReport = new Report(currResult, currScope, currReportTitle);

		currReport.setTotalBooksNotFound(totalBooksNotFound);
		currReport.setTotalPropertyNotFound(totalYearsNotFound, "years");

		// Determine which measure is most effective
		// and record which one it is in the report
		currReport.setOptimalMeasure(this.determineOptimalMeasure(currResult));

		return currReport;
	}

}