package uk.co.bluegumtree.code.java.goodreads.collector.model;

@SuppressWarnings("serial")
public class GoodreadsUpdateException extends DataCollectorUpdateException {

	public GoodreadsUpdateException() {
		this("Error updating.");
	}

	public GoodreadsUpdateException(String message) {
		super(message);
	}

}
