package co.uk.bluegumtree.code.java.api.goodreads;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class GoodreadsCommunityTest {

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
	public void testAddUser() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsCommunity community = new GoodreadsCommunity();
		
		// Execute
		community.addUser(this.user);
		
		try {
			
			// Evaluate
			assertEquals(community.getUser(userId), this.user);
			
		} catch (UserNotFoundException e) {		
			e.printStackTrace();
			fail(e.getMessage());
		}		
		
	}

	@Test
	public void testContains_GoodreadsUser() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsCommunity community = new GoodreadsCommunity();		
		community.addUser(this.user);
			
		// Evaluate
		assertTrue(community.contains(this.user.getId()));
		
	}

	@Test
	public void testContains_Long() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsCommunity community = new GoodreadsCommunity();		
		community.addUser(this.user);
			
		// Evaluate
		assertTrue(community.contains(this.user.getId()));
				
	}

	@Test
	public void testGetUser() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsCommunity community = new GoodreadsCommunity();		
		community.addUser(this.user);
			
		try {
			
			// Evaluate
			assertEquals(community.getUser(this.user.getId()), this.user);
			
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

	@Test
	public void testGoodreadsCommunity() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Execute
		GoodreadsCommunity community = new GoodreadsCommunity();
		
		// Evaluate
		assertTrue(community instanceof GoodreadsCommunity);
		
	}

	@Test
	public void testSize() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		GoodreadsCommunity community = new GoodreadsCommunity();		
		assertEquals(community.size(), 0);
		
		community.addUser(this.user);					
		assertEquals(community.size(), 1);
		
		community.addUser(new GoodreadsUser(84302l));
		assertEquals(community.size(), 2);
		
		
	}

}
