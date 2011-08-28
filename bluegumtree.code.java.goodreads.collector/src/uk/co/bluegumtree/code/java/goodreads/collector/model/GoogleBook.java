package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.io.File;
import java.util.HashMap;

import co.uk.bluegumtree.code.java.util.XmlWriteException;

public class GoogleBook extends GoogleObject {

	public static final String saveDirectory = "books";

	private String isbn10;
	private String isbn13;

	public GoogleBook(String currIsbn) throws IsbnException, DataCollectorUpdateException {

		if (!this.validateIsbn(currIsbn)) {
			throw new IsbnException("Unable to create GoogleBook: a valid ISBN-10 or ISBN-13 is required.");
		}

		if (currIsbn.length() == 13) {
			this.isbn13 = currIsbn;
		} else {
			this.isbn10 = currIsbn;
		}

		this.update();
	}

	@Override
	public String getFilename() throws DataCollectorException {
		try {
			return super.getFilename() + saveDirectory + File.separator + this.getIsbn();
		} catch (IsbnException e) {
			throw new DataCollectorException("Unable to build book's filename as it doesn't have a valid ISBN.");
		}
	}

	public String getIsbn() throws IsbnException {

		// If the ISBN-13 is valid, return it
		if (this.validateIsbn(this.isbn13)) {
			return this.isbn13;
		}

		// Otherwise, if the ISBN-10 is valid return it
		if (this.validateIsbn(this.isbn10)) {
			return this.isbn10;
		}

		throw new IsbnException("ISBN not found.");
	}

	public void setIsbn10(String currIsbn) throws IsbnException {
		if (this.validateIsbn(currIsbn) && currIsbn.length() == 10) {
			this.isbn10 = currIsbn;
		} else {
			throw new IsbnException("Invalid ISBN-10 value supplied.");
		}
	}

	public void setIsbn13(String currIsbn) throws IsbnException {
		if (this.validateIsbn(currIsbn) && currIsbn.length() == 13) {
			this.isbn13 = currIsbn;
		} else {
			throw new IsbnException("Invalid ISBN-13 value supplied.");
		}
	}

	@Override
	public void update() throws DataCollectorUpdateException {

		String isbn = null;
		try {
			isbn = this.getIsbn();
		} catch (IsbnException e) {
			throw new GoogleUpdateException("Unable to update book as it doesn't have a valid ISBN.");
		}

		System.out.println("Isbn: " + isbn);

		// we use the Google books search on the isbn and assume the first
		// response is the book we want
		super.update();

		HashMap<String, String> currParameters = new HashMap<String, String>();
		currParameters.put("q", isbn);
		GoogleApiRequest currRequest = new GoogleApiRequest("", currParameters, "volumes");

		try {
			this.setXml(super.update(currRequest).getXml());
		} catch (XmlWriteException e) {
			e.printStackTrace();
			throw new GoogleUpdateException(e.getMessage());
		}

		try {
			this.save();
		} catch (DataCollectorSaveException e) {
			e.printStackTrace();
		}
	}

	private boolean validateIsbn(String currIsbn) {

		// Check that it's not null
		if (currIsbn == null) {
			return false;
		}

		// Check that it's the correct length
		int isbnLength = currIsbn.length();
		if (isbnLength != 13 && isbnLength != 10) {
			return false;
		}

		return true;
	}
}
