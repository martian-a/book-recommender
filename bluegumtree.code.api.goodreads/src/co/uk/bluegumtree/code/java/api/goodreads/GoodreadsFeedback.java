package co.uk.bluegumtree.code.java.api.goodreads;

import java.util.HashSet;

public class GoodreadsFeedback {

	private HashSet<GoodreadsReview> reviews;

	public GoodreadsFeedback() {
		this.reviews = new HashSet<GoodreadsReview>();
	}

	public void addReview(GoodreadsReview currReview) {
		try {
			this.getReview(currReview.getId()).merge(currReview);
		} catch (InvalidIdException e) {
			e.printStackTrace();
			this.reviews.add(currReview);
		} catch (ReviewNotFoundException e) {
			this.reviews.add(currReview);
		}
	}

	public void addReviews(GoodreadsFeedback currFeedback) {
		HashSet<GoodreadsReview> incomingReviews = currFeedback.getReviews();
		for (GoodreadsReview currReview : incomingReviews) {
			this.addReview(currReview);
		}
	}

	public boolean contains(GoodreadsReview currReview) {
		return this.contains(currReview.getId());
	}

	public boolean contains(Long currId) {
		for (GoodreadsReview currReview : this.reviews) {
			if (currReview.getId().equals(currId)) {
				return true;
			}
		}
		return false;
	}

	public int getAverageRating() throws RatingNotFoundException {
		int totalRatings = 0;
		int sumOfRatings = 0;
		for (GoodreadsReview currReview : this.reviews) {
			Integer currRating = currReview.getRating();
			if (currRating != null) {
				sumOfRatings = sumOfRatings + currRating;
				totalRatings++;
			}
		}
		if (totalRatings == 0) {
			throw new RatingNotFoundException();
		}
		return sumOfRatings / totalRatings;
	}

	public GoodreadsReview getReview(GoodreadsBook currBook, GoodreadsUser currUser) throws ReviewNotFoundException {
		for (GoodreadsReview currReview : this.reviews) {
			if (currReview.getBook().equals(currBook) && currReview.getUser().equals(currUser)) {
				return currReview;
			}
		}
		throw new ReviewNotFoundException();
	}

	public GoodreadsReview getReview(Long currId) throws ReviewNotFoundException {
		for (GoodreadsReview currReview : this.reviews) {
			if (currReview.getId().equals(currId)) {
				return currReview;
			}
		}
		throw new ReviewNotFoundException(currId);
	}

	public HashSet<GoodreadsReview> getReviews() {
		return this.reviews;
	}

	public HashSet<GoodreadsReview> getReviews(int minRating, int maxRating) {

		HashSet<GoodreadsReview> currSet = new HashSet<GoodreadsReview>();

		for (GoodreadsReview currReview : this.reviews) {
			Integer currRating = currReview.getRating();
			if (currRating != null && (currRating >= minRating && currRating <= maxRating)) {
				currSet.add(currReview);
			}
		}
		return currSet;
	}

	public int getTotalReviews() {
		return this.reviews.size();
	}

	public int size() {
		return this.reviews.size();
	}
}
