package uk.co.bluegumtree.code.java.goodreads.collector.model;

@SuppressWarnings("serial")
public class GoodreadsMissingIdException extends DataCollectorException {

	public GoodreadsMissingIdException() {
		this("Missing ID.");
	}

	public GoodreadsMissingIdException(String message) {
		super(message);
	}

}
