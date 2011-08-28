package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import java.util.HashSet;
import java.util.TreeMap;

import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsBook;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsFeedback;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsReview;

public class ProfileTitleLength extends Profile {

	public ProfileTitleLength(GoodreadsFeedback currFeedback) {
		super(currFeedback);
	}

	@Override
	public boolean apply(GoodreadsBook currBook) throws ReportNotFoundException {

		String currTitle = currBook.getTitle();

		if (currTitle == null) {
			return false;
		}

		return super.apply(currBook, currTitle.length());
	}

	@Override
	public boolean apply(GoodreadsBook currBook, Measure currMeasure) throws ReportNotFoundException {

		String currTitle = currBook.getTitle();

		if (currTitle == null) {
			return false;
		}

		return super.apply(currBook, currMeasure, currTitle.length());
	}

	/**
	 * Evaluates the title of the current book to see if it's length matches the
	 * value sought.
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

		String currTitle = currBook.getTitle();
		if (currTitle != null && currTitle.length() == sought) {
			return true;
		}
		return false;
	}

	/**
	 * Evaluates the title of the current book to see if it's length falls
	 * within the range sought.
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

		String currTitle = currBook.getTitle();
		if (currTitle != null && (currTitle.length() >= sought[0] && currTitle.length() <= sought[1])) {
			return true;
		}
		return false;
	}

	@Override
	protected void generate() {

		this.reports.add(this.generate(this.reviewsAll, Scope.ALL, "Book Titles (Length, All)"));
		this.reports.add(this.generate(this.reviewsUnpopular, Scope.UNPOPULAR, "Book Titles (Length, Unpopular)"));
		this.reports.add(this.generate(this.reviewsPopular, Scope.POPULAR, "Book Titles (Length, Popular)"));

	}

	private Report generate(HashSet<GoodreadsReview> currReviews, Scope currScope, String currReportTitle) {

		TreeMap<Integer, Integer> currResult = new TreeMap<Integer, Integer>();

		int totalBooksNotFound = 0;
		int totalBookTitlesNotFound = 0;

		for (GoodreadsReview currReview : currReviews) {

			// Get the book that this review is about
			GoodreadsBook currBook = currReview.getBook();

			if (currBook == null) {

				totalBooksNotFound++;

			} else {

				// Get the book's title
				String currTitle = currBook.getTitle();

				if (currTitle == null) {

					totalBookTitlesNotFound++;

				} else {

					// Determine the length of the book's title
					int currLength = currTitle.length();

					// Update the stored totals
					if (currResult.containsKey(currLength)) {
						int newTotal = currResult.get(currLength) + 1;
						currResult.put(currLength, newTotal);
					} else {
						currResult.put(currLength, 1);
					}
				}
			}
		}

		// Create a new report to store this results in.
		Report currReport = new Report(currResult, currScope, currReportTitle);

		// Set meta-data
		currReport.setTotalBooksNotFound(totalBooksNotFound);
		currReport.setTotalPropertyNotFound(totalBookTitlesNotFound, "titles");

		// Determine which measure is most effective
		// and record which one it is in the report
		currReport.setOptimalMeasure(this.determineOptimalMeasure(currResult));

		return currReport;
	}
}
