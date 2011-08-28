package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import uk.co.bluegumtree.code.java.goodreads.collector.controller.GoodreadsDataCollector;
import co.uk.bluegumtree.code.java.util.Location;
import co.uk.bluegumtree.code.java.util.Logger;
import co.uk.bluegumtree.code.java.util.XmlReader;
import co.uk.bluegumtree.code.java.util.XmlWriteException;

public class GoodreadsReviewsUser extends GoodreadsObject {

	public static final String saveDirectory = "reviews" + File.separator + "user";
	public static final int MAX_REVIEWS = 600;

	protected GoodreadsUser owner;
	private HashSet<GoodreadsBook> books;

	public GoodreadsReviewsUser(GoodreadsUser currOwner) {
		this.owner = currOwner;
		this.books = new HashSet<GoodreadsBook>();
	}

	public HashSet<GoodreadsBook> getBooks() {
		return this.books;
	}

	@Override
	public String getFilename() throws DataCollectorException {

		if (this.owner == null) {
			throw new DataCollectorException("Missing or invalid owner");
		}

		Long ownerId = this.owner.getId();
		if (ownerId == null) {
			throw new DataCollectorException("Missing or invalid ID");
		}

		return super.getFilename() + saveDirectory + File.separator + ownerId;
	}

	public String getSaveLocation() throws DataCollectorException {
		return Location.getSaveDir(GoodreadsObject.saveDirectory + this.getFilename() + "_0.xml");
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

		this.log("Loading reviews.");

		// Includes fallback to forced update if load is unsuccessful
		// and final check that XML isn't null
		super.load();

		this.loadBooks();
	}

	public void loadBooks() {

		this.log("Loading books");

		for (Document currXml : this.xml) {

			NodeList bookIds = XmlReader.readNodeList(currXml, "GoodreadsResponse/reviews/review/book/id");
			int totalBooks = bookIds.getLength();

			for (int i = 0; i < bookIds.getLength(); i++) {

				this.log("Loading book " + (i + 1) + " of " + totalBooks);

				// Get the book's Goodreads ID number
				Long currBookId = XmlReader.readLong(bookIds.item(i), ".");

				// Create a new GoodreadsBook
				GoodreadsBook currBook = new GoodreadsBook(currBookId);
				String currIsbn = null;
				if (currBook.getIsbn10() == null) {
					currIsbn = XmlReader.readString(currXml, "GoodreadsResponse/reviews/review/book[id = " + currBookId + "]/isbn");
					if (currIsbn != null && currIsbn.trim().length() == 10) {
						currBook.setIsbn10(currIsbn);
					}
				}
				if (currBook.getIsbn13() == null) {
					currIsbn = XmlReader.readString(currXml, "GoodreadsResponse/reviews/review/book[id = " + currBookId + "]/isbn13");
					if (currIsbn != null && currIsbn.trim().length() == 13) {
						currBook.setIsbn13(currIsbn);
					}
				}

				try {

					currBook.load();
					this.books.add(currBook);

				} catch (DataCollectorLoadException e) {

					this.log("Failed to load book.");

				}
			}
		}
	}

	@Override
	public void update() throws DataCollectorUpdateException {

		super.update();
		Long ownerId = this.owner.getId();

		if (ownerId == null) {
			throw new GoodreadsUpdateException("Missing or invalid User ID");
		}

		HashMap<String, String> currParameters = new HashMap<String, String>();
		currParameters.put("v", "2");
		currParameters.put("id", String.valueOf(ownerId));
		GoodreadsApiRequest currRequest = new GoodreadsApiRequest("", currParameters, "review/list.xml");

		int reviewsRetrieved = 0;
		int totalReviews = 0;
		int currPage = 0;
		while (reviewsRetrieved <= totalReviews && reviewsRetrieved < MAX_REVIEWS) {

			currPage++;
			currParameters.put("page", String.valueOf(currPage));

			try {

				this.setXml(super.update(this.owner, currRequest).getXml());

				totalReviews = XmlReader.readInteger(this.getXml().get(0), "GoodreadsResponse/reviews/@total");
				reviewsRetrieved = reviewsRetrieved + XmlReader.readInteger(this.getXml().get(0), "count(GoodreadsResponse/reviews/review)");
				System.out.println("Retrieved " + reviewsRetrieved + "/" + totalReviews);

			} catch (XmlWriteException e) {
				e.printStackTrace();
				throw new GoodreadsUpdateException(e.getMessage());
			}
		}

		try {
			this.save();
		} catch (DataCollectorSaveException e) {
			e.printStackTrace();
		}

		this.updateBooks();
	}

	/**
	 * Download an up-to-date XML listing of the books reviewed by this user
	 * 
	 * @throws GoodreadsUpdateException
	 */
	public void updateBooks() throws DataCollectorUpdateException {

		if (this.xml == null) {
			this.update();
		}

		if (this.books != null) {

			for (GoodreadsBook currBook : this.books) {
				currBook.update();
			}
		}
	}
}
