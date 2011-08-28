package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsBook;
import co.uk.bluegumtree.code.java.api.goodreads.GoodreadsUser;

/**
 * An evaluation record of a recommendation.
 * 
 * @author Sheila Ellen Thomson
 * 
 */
public class Recommendation {

	/**
	 * The person for whom the recommendation is being made
	 */
	private GoodreadsUser user;

	/**
	 * The book for which a recommendation was requested
	 */
	private GoodreadsBook book;

	/**
	 * The grade awarded to the book as a result of applying the profile.
	 */
	private Integer grade;

	/**
	 * The actual rating
	 */
	private int rating;

	/**
	 * Constructs a new instance of ResultsBook.
	 * 
	 * @param currBook
	 * the book for which a rating was requested
	 * 
	 * @param currRating
	 * the actual rating awarded by the user
	 * 
	 * @param currRecommended
	 * the recommendation to read (true) or not (false)
	 * 
	 * @throws InvalidRatingException
	 * if the book is unrated
	 */
	public Recommendation(GoodreadsUser currUser, GoodreadsBook currBook, Integer currGrade) throws InvalidRatingException {

		this.user = currUser;
		this.book = currBook;
		this.grade = currGrade;

	}

	/**
	 * Evaluates the accuracy of the recommendation (or lack of).
	 * 
	 * @return true if the book was recommended and enjoyed or if the book
	 * wasn't enjoyed nor recommended; returns false if the book was recommended
	 * but not enjoyed or wasn't recommended but was enjoyed.
	 */
	public boolean getAccurate() {

		// If the book was recommended and the user enjoyed reading it,
		// the recommendation was accurate
		if (this.isRecommended() == true && this.rating > 3) {
			return true;
		}

		// If the book wasn't recommended but the user didn't enjoy reading it
		// anyway,
		// the recommendation was accurate
		if (this.isRecommended() == false && this.rating < 4) {
			return true;
		}

		// Otherwise, we need to do better.
		return false;
	}

	/**
	 * @return the book that is the focus of the evaluation
	 */
	public GoodreadsBook getBook() {
		return this.book;
	}

	public Integer getGrade() {
		return this.grade;
	}

	/**
	 * @return the actual rating that the user gave this book
	 */
	public Integer getRating() {
		return this.rating;
	}

	public GoodreadsUser getUser() {
		return this.user;
	}

	/**
	 * @return true if this book was recommended, false if it wasn't
	 */
	public boolean isRecommended() {
		if (this.grade != null && this.grade > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @param currRating
	 * the value of the rating
	 * 
	 * @throws InvalidRatingException
	 * if the rating is null
	 */
	public void setRating(Integer currRating) throws InvalidRatingException {
		if (currRating == null) {
			throw new InvalidRatingException();
		} else {
			this.rating = currRating;
		}
	}
}
