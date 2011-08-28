package co.uk.bluegumtree.code.java.api.goodreads;

import java.net.URL;
import java.util.Date;
import java.util.HashSet;

public class GoodreadsUser extends GoodreadsObject {

	protected String name;
	protected Long id;
	protected String username;
	protected URL url;
	protected GoodreadsFeedback reviews;
	protected GoodreadsLibrary books;

	protected Integer age;
	protected Gender gender;
	protected String location;
	protected Date joined;
	protected Date lastActive;
	protected String interests;

	public GoodreadsUser(Long currId) {
		this.id = currId;
		this.reviews = new GoodreadsFeedback();
		this.books = new GoodreadsLibrary();
	}

	public void addBook(GoodreadsBook currBook) {
		this.books.addBook(currBook);
	}

	public void addReview(GoodreadsReview currReview) {
		this.reviews.addReview(currReview);
	}

	public boolean equals(GoodreadsUser anotherUser) {
		if (this.id.equals(anotherUser.getId())) {
			return true;
		}
		return false;
	}

	public Integer getAge() {
		return this.age;
	}

	public HashSet<GoodreadsBook> getBooks() {
		return this.books.getBooks();
	}

	public GoodreadsFeedback getFeedback() {
		return this.reviews;
	}

	public Gender getGender() {
		return this.gender;
	}

	public Long getId() {
		return this.id;
	}

	public String getInterests() {
		return this.interests;
	}

	public Date getJoined() {
		return this.joined;
	}

	public Date getLastActive() {
		return this.lastActive;
	}

	public GoodreadsLibrary getLibrary() {
		return this.books;
	}

	public String getLocation() {
		return this.location;
	}

	public String getName() {
		return this.name;
	}

	public HashSet<GoodreadsReview> getReviews() {
		return this.reviews.getReviews();
	}

	public URL getUrl() {
		return this.url;
	}

	public String getUsername() {
		return this.username;
	}

	public void setAge(Integer currAge) {
		this.age = currAge;
	}

	public void setGender(Gender currGender) {
		if (currGender != null) {
			this.gender = currGender;
		}
	}

	public void setInterests(String currInterests) {
		this.interests = currInterests;
	}

	public void setJoined(Date currDate) {
		this.joined = currDate;
	}

	public void setLastActive(Date currDate) {
		this.lastActive = currDate;
	}

	public void setLocation(String currLocation) {
		if (currLocation.equals("")) {
			currLocation = null;
		}
		this.location = currLocation;
	}

	public void setName(String currName) {
		if (currName.equals("")) {
			currName = null;
		}
		this.name = currName;
	}

	public void setUrl(URL currURL) {
		if (currURL.equals("")) {
			currURL = null;
		}
		this.url = currURL;
	}

	public void setUsername(String currUsername) {
		if (currUsername.equals("")) {
			currUsername = null;
		}
		this.username = currUsername;
	}
}
