package co.uk.bluegumtree.code.java.api.goodreads;

@SuppressWarnings("serial")
public class BookNotFoundException extends Exception {

	private Long id;

	public BookNotFoundException() {
		this("Book not found");
	}

	public BookNotFoundException(Long currId) {
		this();
		this.id = currId;
	}

	public BookNotFoundException(String message) {
		super(message);
	}

	public Long getId() {
		return this.id;
	}
}
