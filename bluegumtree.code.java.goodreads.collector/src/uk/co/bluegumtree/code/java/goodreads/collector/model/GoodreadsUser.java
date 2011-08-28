package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.w3c.dom.Document;

import uk.co.bluegumtree.code.java.goodreads.collector.controller.GoodreadsDataCollector;
import co.uk.bluegumtree.code.java.util.Location;
import co.uk.bluegumtree.code.java.util.Logger;
import co.uk.bluegumtree.code.java.util.XmlReader;
import co.uk.bluegumtree.code.java.util.XmlWriteException;

public class GoodreadsUser extends GoodreadsObject {

	public static final String saveDirectory = "users";

	protected String name;
	protected Long id;
	protected String username;
	protected URL url;
	protected GoodreadsReviewsUser reviews;
	protected HashSet<GoodreadsBook> books;

	public GoodreadsUser(Long currId) {
		this.id = currId;
		this.books = new HashSet<GoodreadsBook>();
	}

	public GoodreadsUser(String currUsername) throws GoodreadsUpdateException, DataCollectorException {
		this.username = currUsername;
		this.id = this.getId(currUsername);
		this.books = new HashSet<GoodreadsBook>();
	}

	public boolean equals(GoodreadsUser anotherUser) {

		if (anotherUser == null) {
			return false;
		}

		if (anotherUser.getId() == null) {
			return false;
		}

		if (anotherUser.getId().equals(this.id)) {
			return true;
		}

		return false;
	}

	public HashSet<GoodreadsBook> getBooks() {
		return this.books;
	}

	@Override
	public String getFilename() throws DataCollectorException {

		if (this.getId() == null) {
			throw new DataCollectorException("Missing or invalid owner");
		}

		return super.getFilename() + saveDirectory + File.separator + this.getId();
	}

	@Override
	public ArrayList<File> getFiles() throws DataCollectorLoadException {
		return super.getFiles();
	}

	public Long getId() {
		return this.id;
	}

	/**
	 * Makes a call to the Goodreads API to determine the ID of the user whose
	 * username matches the String value supplied.
	 * 
	 * @param currUsername
	 * the username of the Goodreads user whose ID is sought.
	 * 
	 * @return the ID of the Goodreads user whose username was supplied.
	 * 
	 * @throws GoodreadsUpdateException
	 * if something goes wrong with the API request.
	 * 
	 * @throws DataCollectorException
	 * if it's not possible to determine the user's ID based on the username
	 * supplied.
	 */
	public Long getId(String currUsername) throws GoodreadsUpdateException, DataCollectorException {

		// Create parameters collection
		HashMap<String, String> currParameters = new HashMap<String, String>();
		currParameters.put("username", currUsername);

		// Create a Goodreads API request object
		GoodreadsApiRequest currRequest = new GoodreadsApiRequest("", currParameters, "user/show/");

		// Submit the request
		GoodreadsApiResponse currResponse = super.update(this, currRequest);

		try {

			// Retrieve the API response
			Document currXml = currResponse.getXml();

			// Parse the user's ID from the response XML
			Long currId = XmlReader.readLong(currXml, "GoodreadsResponse/user/id");

			// Check that the ID isn't null or an empty string
			if (currId != null && !currId.equals("")) {

				// Set the ID of this GoodreadsUser to the ID retrieved from the
				// response
				this.id = currId;

			} else {

				// The retrieved ID value is invalid
				throw new DataCollectorException("Unable to determine Goodreads ID for " + this.username);
			}

		} catch (XmlWriteException e) {

			// Something went wrong retrieving the XML from the response
			e.printStackTrace();
			throw new DataCollectorException("Unable to read the Goodreads response.");
		}

		// Return the Goodreads ID associated with the username supplied
		return this.getId();
	}

	public String getName() {
		return this.name;
	}

	public GoodreadsReviewsUser getReviews() {
		return this.reviews;
	}

	public String getSaveLocation() throws DataCollectorException {
		return Location.getSaveDir(GoodreadsObject.saveDirectory + this.getFilename() + "_0.xml");
	}

	public URL getUrl() {
		return this.url;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public void load() throws DataCollectorLoadException {

		// Check whether an update should be forced.
		if (GoodreadsDataCollector.mode.equals(Mode.UPDATE)) {
			try {

				// Force an update prior to loading
				this.update();

			} catch (DataCollectorUpdateException e) {

				// Forced update failed
				Logger.log(e);
			}
		}

		// Includes fallback to forced update if load is unsuccessful
		// and final check that XML isn't null
		super.load();

		if (this.xml != null) {

			for (Document currXml : this.xml) {

				if (this.name == null) {
					this.name = XmlReader.readString(currXml, "GoodreadsResponse/user/name");
				}
				if (this.id == null) {
					this.id = XmlReader.readLong(currXml, "GoodreadsResponse/user/id");
				}
				if (this.username == null) {
					this.username = XmlReader.readString(currXml, "GoodreadsResponse/user/user_name");
				}

			}
		}

		// this.loadReviews();
		// this.loadBooks();

	}

	public void loadReviews() throws DataCollectorLoadException {

		this.reviews = new GoodreadsReviewsUser(this);
		this.reviews.load();
		this.books.addAll(this.reviews.getBooks());
	}

	public void setName(String currName) {
		this.name = currName;
	}

	public void setReviews(GoodreadsReviewsUser currReviews) {
		if (currReviews != null) {
			this.reviews = currReviews;
		}
	}

	@Override
	public String toString() {
		String out = "User:\n";

		out += "  name: " + this.getName() + "\n";
		out += "  id: " + this.getId() + "\n";
		out += "  username: " + this.getUsername() + "\n";
		out += "  url: " + this.getUrl() + "\n";

		return out;
	}

	@Override
	public void update() throws DataCollectorUpdateException {

		System.out.println("Updating user: " + this.id);
		super.update();

		HashMap<String, String> currParameters = new HashMap<String, String>();
		GoodreadsApiRequest currRequest = new GoodreadsApiRequest("", currParameters, "user/show/" + this.id + ".xml");

		GoodreadsApiResponse currResponse = super.update(this, currRequest);
		try {
			this.setXml(currResponse.getXml());
		} catch (XmlWriteException e) {
			e.printStackTrace();
			throw new GoodreadsUpdateException(e.getMessage());
		}

		// Save the updated XML
		try {
			this.save();
		} catch (DataCollectorSaveException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fetches an XML listing of this user's reviews
	 * 
	 * @throws GoodreadsUpdateException
	 */
	public void updateReviews() throws DataCollectorUpdateException {

		if (this.reviews == null) {
			this.reviews = new GoodreadsReviewsUser(this);
		}
		this.reviews.update();
	}
}
