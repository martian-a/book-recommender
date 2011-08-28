package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.io.IOException;
import java.net.MalformedURLException;

import co.uk.bluegumtree.code.java.api.ApiConnection;
import co.uk.bluegumtree.code.java.api.InvalidApiRequestException;

public abstract class GoodreadsObject extends DataObject {

	public static final String saveDirectory = "goodreads/";
	public static final int MAX_PAGES = 2;

	public ApiConnection getConn() throws MalformedURLException {
		return new GoodreadsApiConnection();
	}

	public ApiConnection getConn(GoodreadsUser currUser) throws MalformedURLException {
		if (currUser != null) {
			return new GoodreadsApiConnection(currUser.getName());
		} else {
			return new GoodreadsApiConnection();
		}
	}

	@Override
	public String getFilename() throws DataCollectorException {
		return super.getFilename() + saveDirectory;
	}

	public GoodreadsApiResponse update(GoodreadsApiRequest currRequest) throws GoodreadsUpdateException {
		return this.update(null, currRequest);
	}

	public GoodreadsApiResponse update(GoodreadsUser currUser, GoodreadsApiRequest currRequest) throws GoodreadsUpdateException {

		GoodreadsApiConnection currConn;
		try {
			currConn = (GoodreadsApiConnection) this.getConn(currUser);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new GoodreadsUpdateException(e.getMessage());
		}

		try {
			currConn.submitRequest(currRequest);
		} catch (IOException e) {
			e.printStackTrace();
			throw new GoodreadsUpdateException(e.getMessage());
		} catch (InvalidApiRequestException e) {
			e.printStackTrace();
			throw new GoodreadsUpdateException(e.getMessage());
		}

		return (GoodreadsApiResponse) currConn.getResponse();
	}
}
