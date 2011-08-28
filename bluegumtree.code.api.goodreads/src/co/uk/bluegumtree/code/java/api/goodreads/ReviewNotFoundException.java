package co.uk.bluegumtree.code.java.api.goodreads;

@SuppressWarnings("serial")
public class ReviewNotFoundException extends Exception {

	private Long id;

	public ReviewNotFoundException() {
		this("Review not found");
	}

	public ReviewNotFoundException(Long currId) {
		this();
		this.id = currId;
	}

	public ReviewNotFoundException(String message) {
		super(message);
	}

	public Long getId() {
		return this.id;
	}
}
