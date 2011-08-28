package co.uk.bluegumtree.code.java.api.goodreads;

public class GoodreadsReview {

	private Long id;
	private GoodreadsBook book;
	private GoodreadsUser user;
	private Integer rating;

	public GoodreadsReview(long currId) {
		this.id = currId;
		this.rating = null;
	}

	public GoodreadsBook getBook() {
		return this.book;
	}

	public Long getId() {
		return this.id;
	}

	public Integer getRating() {
		return this.rating;
	}

	public GoodreadsUser getUser() {
		return this.user;
	}

	public void merge(GoodreadsReview currReview) throws InvalidIdException {
		if (!this.id.equals(currReview.getId())) {
			throw new InvalidIdException("Review IDs do not match.");
		}

		if (this.book == null) {
			this.setBook(currReview.getBook());
		}

		if (this.user == null) {
			this.setUser(currReview.getUser());
		}

		if (this.rating == null) {
			this.setRating(currReview.getRating());
		}
	}

	public void setBook(GoodreadsBook currBook) {
		this.book = currBook;
	}

	public void setRating(int currRating) {
		this.rating = currRating;
	}

	public void setUser(GoodreadsUser currUser) {
		this.user = currUser;
	}
}
