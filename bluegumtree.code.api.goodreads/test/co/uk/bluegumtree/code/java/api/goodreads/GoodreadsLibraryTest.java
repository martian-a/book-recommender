package co.uk.bluegumtree.code.java.api.goodreads;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class GoodreadsLibraryTest {

	Long reviewId;
	Long bookId;
	Long userId;
	GoodreadsReview review;
	GoodreadsUser user;	
	GoodreadsBook book;
	
	@Rule
	public TestName name = new TestName();
	
	private GoodreadsLibrary populate(GoodreadsLibrary library, int totalUnrated, int totalUnpopular, int totalMediocre, int totalPopular) {

		long currBookId = 6249l;
		long currReviewId = 7392l;
		int rating = 0;
		int booksRequired = 0;
		
		for (int i = 0; i < 4; i++) {
			
			if (i == 0) {			
				
				// Unrated
				rating = 0; 	
				booksRequired = totalUnrated;
			
			} else if (i == 1) {			
								
				// Unpopular
				rating = 1; 	
				booksRequired = totalUnpopular;
				
			} else if (i == 2) {				
				
				// Mediocre
				rating = 3;
				booksRequired = totalMediocre;
				
			} else {				
				
				// Popular
				rating = 5;
				booksRequired = totalPopular;
				
			}
			
			for (int b = 0; b < booksRequired; b++) {
				
				GoodreadsBook currBook = new GoodreadsBook(currBookId);
				library.addBook(currBook);
				
				for (int r = 0; r < 2; r++) {
					
					GoodreadsReview currReview = new GoodreadsReview(currReviewId);		
					if (rating > 0) {
						currReview.setRating(rating);
					}
					currBook.addReview(currReview);
					
					currReviewId++;
				}

				currBookId++;
			}			
		}
			
		return library;
	}

	@Before
	public void setUp() throws Exception {	
		reviewId = 1234l;
		bookId = 5678l;
		userId = 9101112l;
		review = new GoodreadsReview(reviewId);
		user = new GoodreadsUser(userId);
		book = new GoodreadsBook(bookId);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddBook() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsLibrary library = new GoodreadsLibrary();
		
		// Execute
		library.addBook(this.book);
		
		try {
			
			// Evaluate
			assertEquals(library.getBook(bookId), this.book);
			
		} catch (BookNotFoundException e) {		
			e.printStackTrace();
			fail(e.getMessage());
		}		
	}

	@Test
	public void testContains_GoodreadsBook() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsLibrary library = new GoodreadsLibrary();	
		library.addBook(this.book);
			
		// Evaluate
		assertTrue(library.contains(this.book));
					
	}
	
	@Test
	public void testContains_Long() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsLibrary library = new GoodreadsLibrary();		
		library.addBook(this.book);
			
		// Evaluate
		assertTrue(library.contains(this.book.getId()));
					
	}

	@Test
	public void testGetBook() {
	
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsLibrary library = new GoodreadsLibrary();		
		library.addBook(this.book);
			
		try {
			
			// Evaluate
			assertEquals(library.getBook(this.book.getId()), this.book);
			
		} catch (BookNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}		
		
	}

	@Test
	public void testGetMediocreBooks() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		int totalUnrated = 8;
		int totalUnpopular = 13;
		int totalMediocre = 11;
		int totalPopular = 15;
		GoodreadsLibrary library = this.populate(new GoodreadsLibrary(), totalUnrated, totalUnpopular, totalMediocre, totalPopular);								
		assertEquals(library.size(), (totalUnrated + totalUnpopular + totalMediocre + totalPopular));
		
		// Evaluate		
		GoodreadsBook[] result = {};
		result = (GoodreadsBook[]) library.getMediocreBooks().toArray(result);
		assertEquals(result.length, totalMediocre);
		for (int i = 0; i < result.length; i++) {
			try {
				assertTrue(result[i].getAverageRating() == 3);
			} catch (RatingNotFoundException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}	
		}		
	}

	@Test
	public void testGetPopularBooks() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		int totalUnrated = 4;
		int totalUnpopular = 1;
		int totalMediocre = 14;
		int totalPopular = 9;
		GoodreadsLibrary library = this.populate(new GoodreadsLibrary(), totalUnrated, totalUnpopular, totalMediocre, totalPopular);								
		assertEquals(library.size(), (totalUnrated + totalUnpopular + totalMediocre + totalPopular));
		
		// Evaluate		
		GoodreadsBook[] result = {};
		result = (GoodreadsBook[]) library.getPopularBooks().toArray(result);
		assertEquals(result.length, totalPopular);
		for (int i = 0; i < result.length; i++) {
			try {
				assertTrue(result[i].getAverageRating() > 3);
			} catch (RatingNotFoundException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}	
		}	
	}

	@Test
	public void testGetUnpopularBooks() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		int totalUnrated = 5;
		int totalUnpopular = 12;
		int totalMediocre = 3;
		int totalPopular = 11;
		GoodreadsLibrary library = this.populate(new GoodreadsLibrary(), totalUnrated, totalUnpopular, totalMediocre, totalPopular);								
		assertEquals(library.size(), (totalUnrated + totalUnpopular + totalMediocre + totalPopular));
		
		// Evaluate		
		GoodreadsBook[] result = {};
		result = (GoodreadsBook[]) library.getUnpopularBooks().toArray(result);
		assertEquals(result.length, totalUnpopular);
		for (int i = 0; i < result.length; i++) {
			try {
				assertTrue(result[i].getAverageRating() < 3);
			} catch (RatingNotFoundException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}	
		}	
		
	}

	@Test
	public void testGetUnratedBooks() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		int totalUnrated = 7;
		int totalUnpopular = 17;
		int totalMediocre = 4;
		int totalPopular = 16;
		GoodreadsLibrary library = this.populate(new GoodreadsLibrary(), totalUnrated, totalUnpopular, totalMediocre, totalPopular);								
		assertEquals(library.size(), (totalUnrated + totalUnpopular + totalMediocre + totalPopular));
		
		// Evaluate		
		GoodreadsBook[] result = {};
		result = (GoodreadsBook[]) library.getUnratedBooks().toArray(result);
		assertEquals(result.length, totalUnrated);
		for (int i = 0; i < result.length; i++) {
			try {
				result[i].getAverageRating();
				fail("Should have thrown a RatingNotFoundException");
			} catch (RatingNotFoundException e) {
				assertTrue(true);
			}	
		}			
	}

	@Test
	public void testGoodreadsLibrary() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Execute
		GoodreadsLibrary library = new GoodreadsLibrary();
		
		// Evaluate
		assertTrue(library instanceof GoodreadsLibrary);
		
	}

	@Test
	public void testSize() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		GoodreadsLibrary library = new GoodreadsLibrary();		
		assertEquals(library.size(), 0);
		
		library.addBook(this.book);					
		assertEquals(library.size(), 1);
		
		library.addBook(new GoodreadsBook(84302l));
		assertEquals(library.size(), 2);
		
	}
}
