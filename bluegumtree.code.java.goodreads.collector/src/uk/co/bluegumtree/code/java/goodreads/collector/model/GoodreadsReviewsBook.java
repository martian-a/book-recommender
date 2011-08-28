package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import uk.co.bluegumtree.code.java.goodreads.collector.controller.GoodreadsDataCollector;
import co.uk.bluegumtree.code.java.util.Logger;
import co.uk.bluegumtree.code.java.util.XmlReader;
import co.uk.bluegumtree.code.java.util.XmlWriteException;

public class GoodreadsReviewsBook extends GoodreadsObject {

	public static final String saveDirectory = "reviews" + File.separator + "book";
	public static final int MAX_REVIEWS = 10;

	protected GoodreadsBook book;
	private HashSet<GoodreadsUser> reviewers;

	public GoodreadsReviewsBook(GoodreadsBook currBook) {
		this.book = currBook;
		this.reviewers = new HashSet<GoodreadsUser>();
	}

	@Override
	public String getFilename() throws DataCollectorException {

		if (this.book == null) {
			throw new DataCollectorException("Missing or invalid book");
		}

		Long bookId = this.book.getId();
		if (bookId == null) {
			throw new DataCollectorException("Missing or invalid ID");
		}

		return super.getFilename() + saveDirectory + File.separator + bookId;
	}

	public String getReviewCoverage() {
		return this.getReviewCoverage(this.getTotalReviewsRetrieved(), this.getTotalReviews());
	}

	public String getReviewCoverage(int retrieved, int total) {
		return "Total reviews retrieved: " + retrieved + "/" + total + ".";
	}

	public HashSet<Long> getReviewerIds() {

		HashSet<Long> reviewerIds = new HashSet<Long>();

		if (this.xml == null) {
			try {
				this.load();
			} catch (DataCollectorLoadException e) {
				e.printStackTrace();
			}
		}

		if (this.xml == null) {
			try {
				this.update();
			} catch (DataCollectorUpdateException e1) {
				e1.printStackTrace();
			}
		}

		if (this.xml == null) {
			System.err.println("Missing XML. Unable to compile list of reviewer IDs for book: " + this.book.id);
			return reviewerIds;
		}

		System.out.println("Compiling a list of reviewer IDs for book: " + this.book.id);
		System.out.println("...from " + this.xml.size() + " files.");

		int totalReviewers = 0;
		for (Document currXml : this.xml) {

			NodeList reviewerIdNodeList = XmlReader.readNodeList(currXml, "GoodreadsResponse/book/reviews/review/user/id");
			totalReviewers = totalReviewers + reviewerIdNodeList.getLength();
			this.log("..." + totalReviewers + " reviewers found so far.");

			for (int i = 0; i < reviewerIdNodeList.getLength(); i++) {
				Long currReviewerId = XmlReader.readLong(reviewerIdNodeList.item(i), ".");

				if (currReviewerId != null) {
					reviewerIds.add(currReviewerId);
				}
			}
		}
		this.log("Book " + this.book.getId() + " has been reviewed by " + reviewerIds.size() + " people.");

		return reviewerIds;
	}

	public HashSet<GoodreadsUser> getReviewers() {
		return this.reviewers;
	}

	public int getTotalReviews() {
		if (this.xml != null) {
			return XmlReader.readInteger(this.xml.get(0), "GoodreadsResponse/book/reviews/@total");
		}
		return 0;
	}

	public int getTotalReviewsRetrieved() {

		int totalReviewsRetrieved = 0;
		if (this.xml == null) {
			return totalReviewsRetrieved;
		}

		for (Document currXml : this.xml) {
			int currTotal = this.getTotalReviewsRetrieved(currXml);
			if (currTotal > totalReviewsRetrieved) {
				totalReviewsRetrieved = currTotal;
			}
		}
		return totalReviewsRetrieved;
	}

	public int getTotalReviewsRetrieved(Document currXml) {
		if (this.xml != null) {
			return XmlReader.readInteger(currXml, "GoodreadsResponse/book/reviews/@end");
		}
		return 0;
	}

	@Override
	public void load() throws DataCollectorLoadException {

		// Check whether an update should be forced.
		if (GoodreadsDataCollector.mode.equals(Mode.UPDATE)) {
			try {

				// Force an update prior to loading
				this.update();

			} catch (DataCollectorUpdateException e) {

				// Forced update failed
				Logger.log(e);
			}
		}

		// Includes fallback to forced update if load is unsuccessful
		// and final check that XML isn't null
		super.load();

		int totalReviews = this.getTotalReviews();
		int reviewsRetrieved = this.getTotalReviewsRetrieved();

		if (reviewsRetrieved < totalReviews) {
			try {
				this.update(this.xml.size());
			} catch (DataCollectorUpdateException e) {
				e.printStackTrace();
			}
		} else {
			this.log(this.getReviewCoverage(reviewsRetrieved, totalReviews));
		}
		this.loadReviewers();
	}

	public void loadReviewers() throws DataCollectorLoadException {

		HashSet<Long> reviewerIds = this.getReviewerIds();

		for (Long reviewerId : reviewerIds) {

			GoodreadsUser reviewer = new GoodreadsUser(reviewerId);

			reviewer.load();
			this.reviewers.add(reviewer);
		}
	}

	@Override
	public void update() throws DataCollectorUpdateException {
		super.update();
		this.update(0);
	}

	public void update(int startPage) throws DataCollectorUpdateException {

		Long bookId = this.book.getId();

		if (bookId == null) {
			throw new GoodreadsUpdateException("Missing or invalid User ID");
		}

		HashMap<String, String> currParameters = new HashMap<String, String>();
		currParameters.put("id", bookId.toString());
		currParameters.put("format", "xml");

		int totalReviews = 0;
		int reviewsRetrieved = 0;
		int currPage = startPage;
		int errorCount = 0;
		while (true) {

			currPage++;
			currParameters.put("page", String.valueOf(currPage));
			GoodreadsApiRequest currRequest = new GoodreadsApiRequest("", currParameters, "book/show");

			try {

				// Submit the request
				Document currXml = super.update(currRequest).getXml();

				if (currPage == 1) {
					this.xml = new ArrayList<Document>();
				}

				this.xml.add(currXml);

				if (totalReviews < 1) {
					totalReviews = this.getTotalReviews();
					this.log("Total reviews: " + totalReviews);
					this.log("Estimated time to download: " + ((totalReviews / 20) / 60) + " mins.");
				}

				reviewsRetrieved = this.getTotalReviewsRetrieved(currXml);
				this.log("..." + this.getReviewCoverage(reviewsRetrieved, totalReviews) + " (approx. " + (((totalReviews - reviewsRetrieved) / 20) / 60) + " mins remaining).");

			} catch (XmlWriteException e) {
				e.printStackTrace();
				errorCount++;
			}

			if (reviewsRetrieved >= totalReviews || reviewsRetrieved >= MAX_REVIEWS || errorCount > 3) {
				try {
					this.save();
				} catch (DataCollectorSaveException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}

	public void updateReviewers() {

		System.out.println("Updating reviewers of book: " + this.book.id);

		if (this.xml != null) {

			this.reviewers = new HashSet<GoodreadsUser>();

			for (Document currXml : this.xml) {

				NodeList reviewerIds = XmlReader.readNodeList(currXml, "GoodreadsResponse/book/reviews/review/user/id");
				int totalReviewers = reviewerIds.getLength();

				for (int i = 0; i < reviewerIds.getLength(); i++) {

					System.out.println("\nUpdating " + (i + 1) + " of " + totalReviewers + ".");

					Long reviewerId = XmlReader.readLong(reviewerIds.item(i), ".");
					GoodreadsUser reviewer = new GoodreadsUser(reviewerId);

					try {

						reviewer.update();
						reviewer.save();
						this.reviewers.add(reviewer);

					} catch (DataCollectorSaveException e) {
						e.printStackTrace();
					} catch (DataCollectorUpdateException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
