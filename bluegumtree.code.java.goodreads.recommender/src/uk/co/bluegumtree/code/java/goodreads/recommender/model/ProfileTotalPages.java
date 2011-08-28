package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import java.util.HashSet;
import java.util.TreeMap;

import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsBook;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsFeedback;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsReview;

public class ProfileTotalPages extends Profile {

	public ProfileTotalPages(GoodreadsFeedback currFeedback) {
		super(currFeedback);
	}

	@Override
	public boolean apply(GoodreadsBook currBook) throws ReportNotFoundException {
		return super.apply(currBook, currBook.getTotalPages());
	}

	@Override
	public boolean apply(GoodreadsBook currBook, Measure currMeasure) throws ReportNotFoundException {
		return super.apply(currBook, currMeasure, currBook.getTotalPages());
	}

	/**
	 * Evaluates the total length of the current book to see if the number of
	 * pages matches the value sought.
	 * 
	 * @param currBook
	 * the book currently being evaluated.
	 * 
	 * @param sought
	 * the desired length
	 * 
	 * @return true if the length of the book's title matches the value sought;
	 * false if not.
	 */
	@Override
	protected boolean evaluateAttribute(GoodreadsBook currBook, int sought) {

		Integer currTotalPages = currBook.getTotalPages();
		if (currTotalPages != null && currTotalPages.equals(sought)) {
			return true;
		}
		return false;
	}

	/**
	 * Evaluates the total length of the current book to see if the number of
	 * pages falls within the range sought.
	 * 
	 * @param currBook
	 * the book currently being evaluated.
	 * 
	 * @param minSought
	 * the lower bound of the desired length
	 * 
	 * @param maxSought
	 * the upper bound of the desired length
	 * 
	 * @return true if the length of the book's title matches the value sought;
	 * false if not.
	 */
	@Override
	protected boolean evaluateAttribute(GoodreadsBook currBook, int[] sought) {

		Integer currLength = currBook.getTotalPages();
		if (currLength != null && (currLength >= sought[0] && currLength <= sought[1])) {
			return true;
		}
		return false;
	}

	@Override
	protected void generate() {

		this.reports.add(this.generate(this.reviewsAll, Scope.ALL, "Total Pages (Edition, All)"));
		this.reports.add(this.generate(this.reviewsPopular, Scope.POPULAR, "Total Pages (Edition, Popular)"));
		this.reports.add(this.generate(this.reviewsUnpopular, Scope.UNPOPULAR, "Total Pages (Edition, Unpopular)"));

	}

	private Report generate(HashSet<GoodreadsReview> currReviews, Scope currScope, String currReportTitle) {

		TreeMap<Integer, Integer> currResult = new TreeMap<Integer, Integer>();

		int totalBooksNotFound = 0;
		int totalBookLengthNotFound = 0;

		for (GoodreadsReview currReview : currReviews) {

			// Get the book that this review is about
			GoodreadsBook currBook = currReview.getBook();

			if (currBook == null) {

				totalBooksNotFound++;

			} else {

				// Get the total pages of this copy of the book
				Integer currTotalPages = currBook.getTotalPages();

				if (currTotalPages == null) {

					totalBookLengthNotFound++;

				} else {

					// Update the stored totals
					if (currResult.containsKey(currTotalPages)) {
						int newTotal = currResult.get(currTotalPages) + 1;
						currResult.put(currTotalPages, newTotal);
					} else {
						currResult.put(currTotalPages, 1);
					}
				}
			}
		}
		Report currReport = new Report(currResult, currScope, currReportTitle);

		currReport.setTotalBooksNotFound(totalBooksNotFound);
		currReport.setTotalPropertyNotFound(totalBookLengthNotFound, "pages");

		// Determine which measure is most effective
		// and record which one it is in the report
		currReport.setOptimalMeasure(this.determineOptimalMeasure(currResult));

		return currReport;
	}

}
