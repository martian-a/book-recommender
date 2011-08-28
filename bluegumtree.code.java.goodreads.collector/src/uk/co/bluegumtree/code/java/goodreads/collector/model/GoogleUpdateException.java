package uk.co.bluegumtree.code.java.goodreads.collector.model;

@SuppressWarnings("serial")
public class GoogleUpdateException extends DataCollectorUpdateException {

	public GoogleUpdateException() {
		this("Error updating Google data.");
	}

	public GoogleUpdateException(String message) {
		super(message);
	}

}
