package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.io.IOException;
import java.net.MalformedURLException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import co.uk.bluegumtree.code.java.util.XmlReader;

public class OpenLibraryObject extends DataObject {

	public static final String saveDirectory = "openlibrary/";
	public static final int MAX_PAGES = 2;

	public OpenLibraryApiConnection getConn() throws MalformedURLException {
		return new OpenLibraryApiConnection();
	}

	@Override
	public String getFilename() throws DataCollectorException {
		return super.getFilename() + saveDirectory;
	}

	/**
	 * Custom check to filter out empty responses.
	 */
	@Override
	public void setXml(Document currXml) {

		// Check that the JSon-converted-to-XML response doesn't just consist of
		// the wrapper added during the conversion process.
		NodeList bookElements = XmlReader.readNodeList(currXml, "response/*");
		if (bookElements != null && bookElements.getLength() > 0) {
			super.setXml(currXml);
		}
	}

	public OpenLibraryApiResponse update(OpenLibraryApiRequest currRequest) throws DataCollectorUpdateException {

		OpenLibraryApiConnection currConn;
		try {
			currConn = this.getConn();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new DataCollectorUpdateException(e.getMessage());
		}

		try {
			currConn.submitRequest(currRequest);
		} catch (IOException e) {
			e.printStackTrace();
			throw new DataCollectorUpdateException(e.getMessage());
		}

		return (OpenLibraryApiResponse) currConn.getResponse();
	}
}
