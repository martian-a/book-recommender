package uk.co.bluegumtree.code.java.goodreads.collector.model;

@SuppressWarnings("serial")
public class GoodreadsSaveException extends DataCollectorException {

	public GoodreadsSaveException() {
		this("Error Saving.");
	}

	public GoodreadsSaveException(String message) {
		super(message);
	}

}
