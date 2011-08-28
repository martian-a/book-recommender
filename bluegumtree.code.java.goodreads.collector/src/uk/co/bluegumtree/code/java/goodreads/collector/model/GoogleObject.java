package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.io.IOException;
import java.net.MalformedURLException;

public class GoogleObject extends DataObject {

	public static final String saveDirectory = "google/";
	public static final int MAX_PAGES = 2;

	public GoogleApiConnection getConn() throws MalformedURLException {
		return new GoogleApiConnection();
	}

	@Override
	public String getFilename() throws DataCollectorException {
		return super.getFilename() + saveDirectory;
	}

	public GoogleApiResponse update(GoogleApiRequest currRequest) throws GoogleUpdateException {

		GoogleApiConnection currConn;
		try {
			currConn = this.getConn();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new GoogleUpdateException(e.getMessage());
		}

		try {
			currConn.submitRequest(currRequest);
		} catch (IOException e) {
			e.printStackTrace();
			throw new GoogleUpdateException(e.getMessage());
		}

		return (GoogleApiResponse) currConn.getResponse();
	}

}
