package co.uk.bluegumtree.code.java.api.goodreads;

@SuppressWarnings("serial")
public class GoodreadsLoadException extends Exception {

	public GoodreadsLoadException() {
		super("Error loading.");
	}

	public GoodreadsLoadException(String message) {
		super(message);
	}

}
