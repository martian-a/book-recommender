package uk.co.bluegumtree.code.java.goodreads.collector.model;

@SuppressWarnings("serial")
public class GoogleSaveException extends DataCollectorSaveException {

	public GoogleSaveException() {
		this("Error saving Google data.");
	}

	public GoogleSaveException(String message) {
		super(message);
	}

}
