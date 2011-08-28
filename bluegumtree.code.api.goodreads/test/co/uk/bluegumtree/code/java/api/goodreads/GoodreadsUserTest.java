package co.uk.bluegumtree.code.java.api.goodreads;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class GoodreadsUserTest {

	Long reviewId;
	Long bookId;
	Long userId;
	GoodreadsReview review;
	GoodreadsBook book;	
	
	@Rule
	public TestName name = new TestName();
	
	@Before
	public void setUp() throws Exception {	
		reviewId = 1234l;
		bookId = 5678l;
		userId = 9101112l;
		review = new GoodreadsReview(reviewId);
		book = new GoodreadsBook(bookId);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAge() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		Integer currAge = 117;
		GoodreadsUser currUser = new GoodreadsUser(userId);
		currUser.setAge(currAge);
		
		// Evaluate
		assertEquals(currUser.getAge(), currAge);	
		
	}

	@Test
	public void testGetGender() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		Gender currGender = Gender.female;
		GoodreadsUser currUser = new GoodreadsUser(userId);
		currUser.setGender(currGender);
		
		// Evaluate
		assertEquals(currUser.getGender(), currGender);	
		
	}

	@Test
	public void testGetId() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		GoodreadsUser currUser = new GoodreadsUser(userId);
		
		// Evaluate
		assertEquals(currUser.getId(), userId);			
		
	}

	@Test
	public void testGetInterests() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		String currInterests = "Intergalactic travel";
		GoodreadsUser currUser = new GoodreadsUser(userId);
		currUser.setInterests(currInterests);
		
		// Evaluate
		assertEquals(currUser.getInterests(), currInterests);			
		
	}

	@Test
	public void testGetJoined() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		Date currDate = new Date();
		GoodreadsUser currUser = new GoodreadsUser(userId);
		currUser.setJoined(currDate);
		
		// Evaluate
		assertEquals(currUser.getJoined(), currDate);
		
	}

	@Test
	public void testGetLastActive() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		Date currDate = new Date();
		GoodreadsUser currUser = new GoodreadsUser(userId);
		currUser.setLastActive(currDate);
		
		// Evaluate
		assertEquals(currUser.getLastActive(), currDate);
		
	}

	@Test
	public void testGetLocation() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		String currLocation = "Blackbird Yard";
		GoodreadsUser currUser = new GoodreadsUser(userId);
		currUser.setLocation(currLocation);
		
		// Evaluate
		assertEquals(currUser.getLocation(), currLocation);
		
	}

	@Test
	public void testGetName() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		String currName = "Honor Stephanie Alexander-Harrington";
		GoodreadsUser currUser = new GoodreadsUser(userId);
		currUser.setName(currName);
		
		// Evaluate
		assertEquals(currUser.getName(), currName);
		
	}

	@Test
	public void testGetUrl() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		try {

			// Set scene
			URL currURL = new URL("http://www.goodreads.com/haharrington ");
			GoodreadsUser currUser = new GoodreadsUser(userId);
			currUser.setUrl(currURL);
			
			// Evaluate
			assertEquals(currUser.getUrl(), currURL);			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
				
		
	}

	@Test
	public void testGetUsername() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		String currName = "haharrington";
		GoodreadsUser currUser = new GoodreadsUser(userId);
		currUser.setUsername(currName);
		
		// Evaluate
		assertEquals(currUser.getUsername(), currName);
		
	}

	@Test
	public void testGoodreadsUser() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Execute
		GoodreadsUser currUser = new GoodreadsUser(userId);
		
		// Evaluate
		assertTrue(currUser instanceof GoodreadsUser);		
		
	}

	@Test
	public void testSetAge() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		Integer currAge = 110;
		GoodreadsUser currUser = new GoodreadsUser(userId);
		
		// Execute
		currUser.setAge(currAge);
		
		// Evaluate
		assertEquals(currUser.getAge(), currAge);
		
	}

	@Test
	public void testSetGender() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		Gender currGender = Gender.female;
		GoodreadsUser currUser = new GoodreadsUser(userId);
		
		// Execute
		currUser.setGender(currGender);
		
		// Evaluate
		assertEquals(currUser.getGender(), currGender);	
		
	}

	@Test
	public void testSetInterests() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		String currInterests = "Intergalactic travel";
		GoodreadsUser currUser = new GoodreadsUser(userId);
		
		// Execute
		currUser.setInterests(currInterests);
		
		// Evaluate
		assertEquals(currUser.getInterests(), currInterests);			
		
	}

	@Test
	public void testSetJoined() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		Date currDate = new Date();
		GoodreadsUser currUser = new GoodreadsUser(userId);
		
		// Execute
		currUser.setJoined(currDate);
		
		// Evaluate
		assertEquals(currUser.getJoined(), currDate);
		
	}

	@Test
	public void testSetLastActive() {

		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		Date currDate = new Date();
		GoodreadsUser currUser = new GoodreadsUser(userId);
		
		// Execute
		currUser.setLastActive(currDate);
		
		// Evaluate
		assertEquals(currUser.getLastActive(), currDate);
		
	}

	@Test
	public void testSetLocation() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		String currLocation = "Blackbird Yard";
		GoodreadsUser currUser = new GoodreadsUser(userId);
		
		// Execute
		currUser.setLocation(currLocation);
		
		// Evaluate
		assertEquals(currUser.getLocation(), currLocation);
		
	}

	@Test
	public void testSetName() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		String currName = "Honor Stephanie Alexander-Harrington";
		GoodreadsUser currUser = new GoodreadsUser(userId);
		
		// Execute
		currUser.setName(currName);
		
		// Evaluate
		assertEquals(currUser.getName(), currName);
		
	}

	@Test
	public void testSetUrl() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		try {

			// Set scene
			URL currURL = new URL("http://www.goodreads.com/haharrington ");
			GoodreadsUser currUser = new GoodreadsUser(userId);
			
			// Execute
			currUser.setUrl(currURL);
			
			// Evaluate
			assertEquals(currUser.getUrl(), currURL);			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
				
		
	}

	@Test
	public void testSetUsername() {
		
		// Announce
		System.out.println(this.name.getMethodName());
		
		// Set scene
		String currName = "haharrington";
		GoodreadsUser currUser = new GoodreadsUser(userId);
		
		// Execute
		currUser.setUsername(currName);
		
		// Evaluate
		assertEquals(currUser.getUsername(), currName);
		
	}	
	
}
