package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import org.w3c.dom.Document;

import uk.co.bluegumtree.code.java.goodreads.collector.controller.GoodreadsDataCollector;
import co.uk.bluegumtree.code.java.util.Logger;
import co.uk.bluegumtree.code.java.util.XmlReader;
import co.uk.bluegumtree.code.java.util.XmlWriteException;

public class GoodreadsBook extends GoodreadsObject {

	public static final String saveDirectory = "books";

	protected Long id;
	private String isbn10;
	private String isbn13;
	private GoodreadsReviewsBook reviewsBook;

	public GoodreadsBook(Long currBookId) {
		this.id = currBookId;
		this.isbn10 = null;
		this.isbn13 = null;
	}

	public boolean equals(GoodreadsBook anotherBook) {

		if (anotherBook == null || anotherBook.getId() == null) {
			return false;
		}

		if (anotherBook.getId().equals(this.id)) {
			return true;
		}

		return false;
	}

	@Override
	public String getFilename() throws DataCollectorException {

		if (this.id == null) {
			throw new DataCollectorException("Missing Id");
		}

		return super.getFilename() + saveDirectory + File.separator + this.id;
	}

	public Long getId() {
		return this.id;
	}

	public String getIsbn() {

		// If the ISBN-13 is known, provide it
		if (this.isbn13 != null) {
			return this.isbn13;
		}

		// Otherwise, default to the old ISBN
		return this.isbn10;
	}

	public String getIsbn10() {
		return this.isbn10;
	}

	public String getIsbn13() {
		return this.isbn13;
	}

	public HashSet<GoodreadsUser> getReviewers() throws DataCollectorLoadException {
		if (this.reviewsBook == null) {
			this.reviewsBook = new GoodreadsReviewsBook(this);
			this.reviewsBook.load();
		}
		return this.reviewsBook.getReviewers();
	}

	public GoodreadsReviewsBook getReviewsBook() {
		return this.reviewsBook;
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

		if (this.xml != null) {

			for (Document currXml : this.xml) {

				Long currId = XmlReader.readLong(currXml, "GoodreadsResponse/book/id");
				if (currId != null) {
					this.id = currId;
				}

				String currIsbn = null;
				if (this.getIsbn10() == null) {
					currIsbn = XmlReader.readString(currXml, "GoodreadsResponse//book[id = " + this.id + "]/isbn");
					if (currIsbn != null && currIsbn.trim().length() == 10) {
						this.setIsbn10(currIsbn);
					}
				}
				if (this.getIsbn13() == null) {
					currIsbn = XmlReader.readString(currXml, "GoodreadsResponse//book[id = " + this.id + "]/isbn13");
					if (currIsbn != null && currIsbn.trim().length() == 13) {
						this.setIsbn13(currIsbn);
					}
				}
			}
		}
	}

	public void setIsbn10(String currIsbn) {
		this.isbn10 = currIsbn;
	}

	public void setIsbn13(String currIsbn) {
		this.isbn13 = currIsbn;
	}

	@Override
	public void update() throws DataCollectorUpdateException {

		super.update();
		if (this.getIsbn() == null || this.getIsbn().equals("")) {
			throw new GoodreadsUpdateException("Unable to update book as it doesn't have an ISBN.");
		}

		HashMap<String, String> currParameters = new HashMap<String, String>();
		currParameters.put("q", String.valueOf(this.getIsbn()));
		GoodreadsApiRequest currRequest = new GoodreadsApiRequest("", currParameters, "search/index.xml");

		try {
			this.setXml(super.update(currRequest).getXml());
		} catch (XmlWriteException e) {
			throw new DataCollectorUpdateException(e.getMessage());
		}

		try {
			this.save();
		} catch (DataCollectorSaveException e) {
			throw new DataCollectorUpdateException(e.getMessage());
		}
	}

	public void updateReviewsBook() throws DataCollectorUpdateException {

		this.reviewsBook = new GoodreadsReviewsBook(this);
		this.reviewsBook.update();

		try {
			this.reviewsBook.save();
		} catch (DataCollectorSaveException e) {
			throw new DataCollectorUpdateException(e.getMessage());
		}
	}
}
