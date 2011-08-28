package uk.co.bluegumtree.code.java.goodreads.collector.model;

@SuppressWarnings("serial")
public class IsbnException extends Exception {

	public IsbnException() {
		this("Missing or invalid ISBN.");
	}

	public IsbnException(String message) {
		super(message);
	}

}
