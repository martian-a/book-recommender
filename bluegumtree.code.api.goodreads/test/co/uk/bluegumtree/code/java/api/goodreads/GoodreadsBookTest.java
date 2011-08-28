package co.uk.bluegumtree.code.java.api.goodreads;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class GoodreadsBookTest {

	Long reviewId;
	Long bookId;
	Long userId;
	GoodreadsReview review;
	GoodreadsUser user;	
	
	@Rule
	public TestName name = new TestName();
	
	@Before
	public void setUp() throws Exception {	
		reviewId = 1234l;
		bookId = 5678l;
		userId = 9101112l;
		review = new GoodreadsReview(reviewId);
		user = new GoodreadsUser(userId);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddReview() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsBook currBook = new GoodreadsBook(bookId);	
		int originalTotal = currBook.getTotalReviews();
				
		// Execute
		currBook.addReview(this.review);		
				
		try {

			// Evaluate
			assertSame(currBook.getReview(this.review.getId()), this.review);
			assertEquals(currBook.getTotalReviews(), (originalTotal + 1));
			
		} catch (ReviewNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}		
	}

	@Test
	public void testGetAverageRating() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		
		GoodreadsReview review1 = new GoodreadsReview(1234l);
		review1.setRating(0);
		currBook.addReview(review1);
		
		GoodreadsReview review2 = new GoodreadsReview(2345l);
		review2.setRating(1);
		currBook.addReview(review2);
		
		GoodreadsReview review3 = new GoodreadsReview(3456l);
		review3.setRating(2);
		currBook.addReview(review3);
		
		GoodreadsReview review4 = new GoodreadsReview(4567l);
		review4.setRating(3);
		currBook.addReview(review4);
		
		GoodreadsReview review5 = new GoodreadsReview(5678l);
		review5.setRating(4);
		currBook.addReview(review5);
		
		GoodreadsReview review6 = new GoodreadsReview(6789l);
		review6.setRating(5);
		currBook.addReview(review6);
				
		try {

			// Execute
			int result = currBook.getAverageRating();

			// Evaluate
			assertEquals(result, ((0 + 1 + 2 + 3 + 4 + 5)/6));
			
		} catch (RatingNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetId() {
		
		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		Long currId = 9876l;
		GoodreadsBook currBook = new GoodreadsBook(currId);			
		
		// Evaluate
		assertEquals(currBook.getId(), currId);
			
	}
	
	@Test
	public void testGetIsbn10() {
		
		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		String currIsbn10 = "1555940145";
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setIsbn10(currIsbn10);
		
		// Evaluate
		assertEquals(currBook.getIsbn10(), currIsbn10);
		
	}
	
	@Test
	public void testGetIsbn13() {
		
		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		String currIsbn13 = "9781555940140";
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setIsbn10(currIsbn13);
		
		// Evaluate
		assertEquals(currBook.getIsbn10(), currIsbn13);		
		
	}

	@Test
	public void testGetLanguage() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		Language currLang = Language.gr_eng;
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setLanguage(currLang);
		
		// Evaluate
		assertEquals(currBook.getLanguage(), currLang);		
		
	}


	@Test
	public void testGetOriginalLanguage() {
		
		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		Language currLang = Language.gr_eng;
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setOriginalLanguage(currLang);
		
		// Evaluate
		assertEquals(currBook.getOriginalLanguage(), currLang);			
		
	}

	@Test
	public void testGetOriginalPublicationYear() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		Integer currYear = 1899;
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setOriginalPublicationYear(currYear);
		
		// Evaluate
		assertEquals(currBook.getOriginalPublicationYear(), currYear);			
		
	}

	@Test
	public void testGetPublicationYear() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		Integer currYear = 2011;
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setOriginalPublicationYear(currYear);
		
		// Evaluate
		assertEquals(currBook.getOriginalPublicationYear(), currYear);	
		
	}

	@Test
	public void testGetReview() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		assertEquals(currBook.getTotalReviews(), 0);
		currBook.addReview(this.review);		
				
		try {

			// Evaluate
			assertSame(currBook.getReview(this.review.getId()), this.review);
			assertEquals(currBook.getTotalReviews(), 1);
			
		} catch (ReviewNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		// Create and add multiple reviews

		Long reviewId1 = 5678l;
		GoodreadsReview review1 = new GoodreadsReview(reviewId1);
		currBook.addReview(review1);
		
		Long reviewId2 = 2345l;
		GoodreadsReview review2 = new GoodreadsReview(reviewId2);
		currBook.addReview(review2);
		
		Long reviewId3 = 3456l;
		GoodreadsReview review3 = new GoodreadsReview(reviewId3);
		currBook.addReview(review3);
		
		Long reviewId4 = 4567l;
		GoodreadsReview review4 = new GoodreadsReview(reviewId4);
		currBook.addReview(review4);								
		
		try {

			// Evaluate
			assertEquals(currBook.getTotalReviews(), 5);
			assertTrue(currBook.getReview(reviewId4) instanceof GoodreadsReview);
			assertSame(currBook.getReview(reviewId4), review4);
			assertSame(currBook.getReview(reviewId3), review3);
			assertSame(currBook.getReview(reviewId2), review2);
			assertSame(currBook.getReview(reviewId1), review1);
			
			assertSame(currBook.getReview(this.review.getId()), this.review);
						
		} catch (ReviewNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}		
		
	}

	@Test
	public void testGetReviews() {
		
		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		GoodreadsBook currBook = new GoodreadsBook(bookId);			
		
		// Evaluate
		assertEquals(currBook.getTotalReviews(), 0);
		
		// Create multiple reviews and store them in a collection
		GoodreadsFeedback collection = new GoodreadsFeedback();

		Long reviewId1 = 1234l;
		GoodreadsReview review1 = new GoodreadsReview(reviewId1);
		collection.addReview(review1);
		
		Long reviewId2 = 2345l;
		GoodreadsReview review2 = new GoodreadsReview(reviewId2);
		collection.addReview(review2);
		
		Long reviewId3 = 3456l;
		GoodreadsReview review3 = new GoodreadsReview(reviewId3);
		collection.addReview(review3);
		
		Long reviewId4 = 4567l;
		GoodreadsReview review4 = new GoodreadsReview(reviewId4);
		collection.addReview(review4);
				
		// Check that the collection contains all the reviews
		assertTrue(collection.contains(review1));
		assertTrue(collection.contains(review2));
		assertTrue(collection.contains(review3));
		assertTrue(collection.contains(review4));
		
		// Set the book's review collection to the collection just created
		currBook.setReviews(collection);
		
		// Execute
		GoodreadsFeedback result = currBook.getReviews();
		
		// Evaluate
		assertEquals(currBook.getTotalReviews(), 4);
		assertSame(result, collection);
		assertTrue(result.contains(review1));
		assertTrue(result.contains(review2));
		assertTrue(result.contains(review3));
		assertTrue(result.contains(review4));
		
	}

	@Test
	public void testGetTitle() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		String currTitle = "On Basilisk Station";
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setTitle(currTitle);
		
		// Evaluate
		assertEquals(currBook.getTitle(), currTitle);
		
	}

	@Test
	public void testGetTotalReviews() {
		
		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		GoodreadsBook currBook = new GoodreadsBook(bookId);			
		
		// Evaluate
		assertEquals(currBook.getTotalReviews(), 0);
		
		// Change scene
		currBook.addReview(this.review);
		
		// Evaluate
		assertEquals(currBook.getTotalReviews(), 1);
		
	}

	@Test
	public void testGoodreadsBook() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Execute
		GoodreadsBook result = new GoodreadsBook(bookId);
		
		// Evaluate
		assertTrue(result instanceof GoodreadsBook);
		
	}

	@Test
	public void testSetIsbn10() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		String currIsbn10 = "1555940145";
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setIsbn10(currIsbn10);
		
		// Evaluate
		assertEquals(currBook.getIsbn10(), currIsbn10);
		
	}

	@Test
	public void testSetIsbn13() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		String currIsbn13 = "9781555940140";
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setIsbn10(currIsbn13);
		
		// Evaluate
		assertEquals(currBook.getIsbn10(), currIsbn13);	
		
	}

	@Test
	public void testSetLanguage_Language() {
		
		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		Language currLang = Language.gr_eng;
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		
		currBook.setLanguage(currLang);
		
		// Evaluate
		assertEquals(currBook.getLanguage(), currLang);			
		
	}

	@Test
	public void testSetLanguage_String() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		String currLang = "eng";
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setLanguage(currLang);
		
		// Evaluate
		assertEquals(currBook.getLanguage(), Language.gr_eng);	
		
	}

	/*
	@Test
	public void testSetOriginalLanguage_Integer() {
		
		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		int currLang = 1;
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setOriginalLanguage(currLang);
		
		// Evaluate
		assertEquals(currBook.getLanguage(), Language.gr_eng);	
		
	}

	@Test
	public void testSetOriginalLanguage_Language() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		Language currLang = Language.gr_eng;
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setOriginalLanguage(currLang);
		
		// Evaluate
		assertEquals(currBook.getLanguage(), Language.gr_eng);	
		
	}
	*/

	@Test
	public void testSetOriginalPublicationYear() {
		
		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		Integer currYear = 1899;
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setOriginalPublicationYear(currYear);
		
		// Evaluate
		assertEquals(currBook.getOriginalPublicationYear(), currYear);	
		
	}

	@Test
	public void testSetPublicationYear() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		Integer currYear = 2011;
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setOriginalPublicationYear(currYear);
		
		// Evaluate
		assertEquals(currBook.getOriginalPublicationYear(), currYear);	
		
	}

	@Test
	public void testSetReviews() {
	
		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		GoodreadsBook currBook = new GoodreadsBook(bookId);			
		
		// Evaluate
		assertEquals(currBook.getTotalReviews(), 0);
		
		// Create multiple reviews and store them in a collection
		GoodreadsFeedback collection = new GoodreadsFeedback();

		Long reviewId1 = 1234l;
		GoodreadsReview review1 = new GoodreadsReview(reviewId1);
		collection.addReview(review1);
		
		Long reviewId2 = 2345l;
		GoodreadsReview review2 = new GoodreadsReview(reviewId2);
		collection.addReview(review2);
		
		Long reviewId3 = 3456l;
		GoodreadsReview review3 = new GoodreadsReview(reviewId3);
		collection.addReview(review3);
		
		Long reviewId4 = 4567l;
		GoodreadsReview review4 = new GoodreadsReview(reviewId4);
		collection.addReview(review4);
				
		// Check that the collection contains all the reviews
		assertTrue(collection.contains(review1));
		assertTrue(collection.contains(review2));
		assertTrue(collection.contains(review3));
		assertTrue(collection.contains(review4));
		
		// Execute
		currBook.setReviews(collection);
		
		// Evaluate
		GoodreadsFeedback result = currBook.getReviews();
		
		assertEquals(currBook.getTotalReviews(), 4);
		assertSame(result, collection);
		assertTrue(result.contains(review1));
		assertTrue(result.contains(review2));
		assertTrue(result.contains(review3));
		assertTrue(result.contains(review4));
	}

	@Test
	public void testSetTitle() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		String currTitle = "On Basilisk Station";
		GoodreadsBook currBook = new GoodreadsBook(bookId);
		currBook.setTitle(currTitle);
		
		// Evaluate
		assertEquals(currBook.getTitle(), currTitle);	
		
	}

}
