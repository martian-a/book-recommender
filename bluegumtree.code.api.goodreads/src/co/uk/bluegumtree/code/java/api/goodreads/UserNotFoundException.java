package co.uk.bluegumtree.code.java.api.goodreads;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {

	private Long id;

	public UserNotFoundException() {
		this("User not found");
	}

	public UserNotFoundException(Long currId) {
		this();
		this.id = currId;
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public Long getId() {
		return this.id;
	}
}
