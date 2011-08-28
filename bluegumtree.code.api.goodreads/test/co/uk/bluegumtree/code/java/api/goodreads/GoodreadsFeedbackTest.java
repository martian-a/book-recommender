package co.uk.bluegumtree.code.java.api.goodreads;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class GoodreadsFeedbackTest {

	Long reviewId;
	Long bookId;
	Long userId;
	GoodreadsReview review;
	GoodreadsUser user;	
	GoodreadsBook book;
	
	@Rule
	public TestName name = new TestName();
	
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
	public void testAddReview() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsFeedback feedback = new GoodreadsFeedback();
		
		// Execute
		feedback.addReview(this.review);
		
		try {
			
			// Evaluate
			assertEquals(feedback.getReview(reviewId), this.review);
			
		} catch (ReviewNotFoundException e) {		
			e.printStackTrace();
			fail(e.getMessage());
		}	
		
	}

	@Test
	public void testContains_GoodreadsReview() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsFeedback feedback = new GoodreadsFeedback();		
		feedback.addReview(this.review);
			
		// Evaluate
		assertTrue(feedback.contains(this.review));		
		
		
	}

	@Test
	public void testContains_Long() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsFeedback feedback = new GoodreadsFeedback();		
		feedback.addReview(this.review);
			
		// Evaluate
		assertTrue(feedback.contains(this.review.getId()));		
		
	}

	@Test
	public void testGetReview() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsFeedback feedback = new GoodreadsFeedback();		
		feedback.addReview(this.review);
			
		try {
			
			// Evaluate
			assertEquals(feedback.getReview(this.review.getId()), this.review);
			
		} catch (ReviewNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}	
		
	}

	@Test
	public void testGoodreadsFeedback() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Execute
		GoodreadsFeedback feedback = new GoodreadsFeedback();
		
		// Evaluate
		assertTrue(feedback instanceof GoodreadsFeedback);
		
	}

	@Test
	public void testSize() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		GoodreadsFeedback feedback = new GoodreadsFeedback();		
		assertEquals(feedback.size(), 0);
		
		feedback.addReview(this.review);					
		assertEquals(feedback.size(), 1);
		
		feedback.addReview(new GoodreadsReview(84302l));
		assertEquals(feedback.size(), 2);
		
	}

}
