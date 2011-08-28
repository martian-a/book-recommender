package uk.co.bluegumtree.code.java.goodreads.collector.model;

@SuppressWarnings("serial")
public class GoodreadsLoadException extends DataCollectorException {

	public GoodreadsLoadException() {
		this("Error loading.");
	}

	public GoodreadsLoadException(String message) {
		super(message);
	}

}
