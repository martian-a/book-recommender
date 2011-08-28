package co.uk.bluegumtree.code.java.api.goodreads;

import java.util.HashSet;

import co.uk.bluegumtree.code.java.util.Formatter;
import co.uk.bluegumtree.code.java.util.Logger;

public class GoodreadsLibrary {

	private HashSet<GoodreadsBook> books;

	public GoodreadsLibrary() {
		this.books = new HashSet<GoodreadsBook>();
	}

	public void addBook(GoodreadsBook currBook) {

		// Retrieve existing record, if one exists
		try {

			GoodreadsBook existingRecord = this.getBook(currBook.getId());

			try {
				existingRecord.merge(currBook);
			} catch (InvalidIdException e) {
				e.printStackTrace();
				this.books.add(currBook);
			}

		} catch (BookNotFoundException e) {
			this.books.add(currBook);
		}

	}

	public boolean contains(GoodreadsBook currBook) {
		return this.contains(currBook.getId());
	}

	public boolean contains(Long currId) {
		for (GoodreadsBook currBook : this.books) {
			if (currBook.getId().equals(currId)) {
				return true;
			}
		}
		return false;
	}

	public GoodreadsBook getBook(Long currId) throws BookNotFoundException {
		for (GoodreadsBook currBook : this.books) {
			if (currBook.getId().equals(currId)) {
				return currBook;
			}
		}
		throw new BookNotFoundException(currId);
	}

	public HashSet<GoodreadsBook> getBooks() {
		return this.books;
	}

	public String getLibraryProfile() {

		int popularBooks = this.getPopularBooks().size();
		int mediocreBooks = this.getMediocreBooks().size();
		int unpopularBooks = this.getUnpopularBooks().size();
		int unratedBooks = this.getUnratedBooks().size();
		int totalBooks = popularBooks + mediocreBooks + unpopularBooks + unratedBooks;

		String out = "";
		out = out + "Popular Books: " + popularBooks + "\n";
		out = out + "Mediocre Books: " + mediocreBooks + "\n";
		out = out + "Unpopular Books: " + unpopularBooks + "\n";
		out = out + "Unrated Books: " + unratedBooks + "\n";
		out = out + "Total Books: " + totalBooks + "\n";

		return out;
	}

	public HashSet<GoodreadsBook> getMediocreBooks() {
		HashSet<GoodreadsBook> subset = new HashSet<GoodreadsBook>();
		for (GoodreadsBook currBook : this.books) {
			try {
				if (currBook.getAverageRating() == 3) {
					subset.add(currBook);
				}
			} catch (RatingNotFoundException e) {
				Logger.log("Rating not found for book " + currBook.getId() + ".");
			}
		}
		return subset;
	}

	public HashSet<GoodreadsBook> getPopularBooks() {
		HashSet<GoodreadsBook> subset = new HashSet<GoodreadsBook>();
		for (GoodreadsBook currBook : this.books) {
			try {
				if (currBook.getAverageRating() > 3) {
					subset.add(currBook);
				}
			} catch (RatingNotFoundException e) {
				Logger.log("Rating not found for book " + currBook.getId() + ".");
			}
		}
		return subset;
	}

	public HashSet<GoodreadsBook> getUnpopularBooks() {
		HashSet<GoodreadsBook> subset = new HashSet<GoodreadsBook>();
		for (GoodreadsBook currBook : this.books) {
			try {
				if (currBook.getAverageRating() < 3) {
					subset.add(currBook);
				}
			} catch (RatingNotFoundException e) {
				Logger.log("Rating not found for book " + currBook.getId() + ".");
			}
		}
		return subset;
	}

	public HashSet<GoodreadsBook> getUnratedBooks() {
		HashSet<GoodreadsBook> subset = new HashSet<GoodreadsBook>();
		for (GoodreadsBook currBook : this.books) {
			try {
				currBook.getAverageRating();
			} catch (RatingNotFoundException e) {
				subset.add(currBook);
			}
		}
		return subset;
	}

	public int size() {
		return this.books.size();
	}

	public String toStringBookDetails() {

		String out = "";
		for (GoodreadsBook currBook : this.books) {
			out = out + currBook.toString() + "\n";
		}
		return out;
	}

	public String toStringBookList() {

		String out = "";
		int i = 0;
		String totalBooks = String.valueOf(this.books.size());
		for (GoodreadsBook currBook : this.books) {
			i++;
			out = out + Formatter.alignRight((i + ". "), (totalBooks.length() + 2)) + currBook.getTitle() + " [" + currBook.getId().toString() + "]. " + currBook.getOriginalPublicationYear() + "\n";
		}
		return out;
	}
}
