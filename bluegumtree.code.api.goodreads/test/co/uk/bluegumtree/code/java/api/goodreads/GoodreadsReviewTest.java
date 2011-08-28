package co.uk.bluegumtree.code.java.api.goodreads;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class GoodreadsReviewTest {

	Long reviewId;
	Long bookId;
	Long userId;
	GoodreadsBook book;
	GoodreadsUser user;	
	
	@Rule
	public TestName name = new TestName();
	
	@Before
	public void setUp() throws Exception {	
		reviewId = 1234l;
		bookId = 5678l;
		userId = 9101112l;
		book = new GoodreadsBook(bookId);
		user = new GoodreadsUser(userId);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetBook() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsReview currReview = new GoodreadsReview(this.reviewId);	
		currReview.setBook(this.book);	
		
		// Execute
		GoodreadsBook result = currReview.getBook();
				
		// Evaluate		
		assertEquals(result, this.book);			
		
	}

	@Test
	public void testGetId() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsReview result = new GoodreadsReview(reviewId);
		
		// Evaluate
		assertEquals(result.getId(), reviewId);		
		
	}

	@Test
	public void testGetRating() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		int currRating = 5;
		GoodreadsReview currReview = new GoodreadsReview(reviewId);
		currReview.setRating(currRating);
		
		// Execute
		int result = currReview.getRating();
				
		// Evaluate
		assertEquals(result, currRating);		
		
	}

	@Test
	public void testGetUser() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsReview currReview = new GoodreadsReview(this.reviewId);	
		currReview.setUser(this.user);		
		
		// Execute
		GoodreadsUser result = currReview.getUser();
				
		// Evaluate		
		assertEquals(result, this.user);	
		
	}

	@Test
	public void testGoodreadsReview() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Execute
		GoodreadsReview result = new GoodreadsReview(reviewId);
		
		// Evaluate
		assertTrue(result instanceof GoodreadsReview);
		
	}

	@Test
	public void testSetBook() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsReview currReview = new GoodreadsReview(this.reviewId);	
				
		// Execute
		currReview.setBook(this.book);		
				
		// Evaluate
		GoodreadsBook result = currReview.getBook();
		assertEquals(result, this.book);			
		
	}

	@Test
	public void testSetRating() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		int currRating = 5;
		GoodreadsReview currReview = new GoodreadsReview(reviewId);
				
		// Execute
		currReview.setRating(currRating);		
				
		// Evaluate
		int result = currReview.getRating();
		assertEquals(result, currRating);		
		
	}

	@Test
	public void testSetUser() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsReview currReview = new GoodreadsReview(this.reviewId);	
				
		// Execute
		currReview.setUser(this.user);		
				
		// Evaluate
		GoodreadsUser result = currReview.getUser();
		assertEquals(result, this.user);	
		
	}

}
