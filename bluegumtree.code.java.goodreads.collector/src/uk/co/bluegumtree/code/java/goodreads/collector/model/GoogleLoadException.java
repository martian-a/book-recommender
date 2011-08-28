package uk.co.bluegumtree.code.java.goodreads.collector.model;

@SuppressWarnings("serial")
public class GoogleLoadException extends DataCollectorLoadException {

	public GoogleLoadException() {
		this("Error loading Google data.");
	}

	public GoogleLoadException(String message) {
		super(message);
	}

}
