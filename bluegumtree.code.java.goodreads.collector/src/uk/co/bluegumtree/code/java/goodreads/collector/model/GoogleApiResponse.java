package uk.co.bluegumtree.code.java.goodreads.collector.model;

import co.uk.bluegumtree.code.java.api.ApiResponse;
import co.uk.bluegumtree.code.java.util.XmlWriteException;
import co.uk.bluegumtree.code.java.util.XmlWriter;

public class GoogleApiResponse extends ApiResponse {

	public GoogleApiResponse(String currResponse) {
		super(currResponse);
		this.setFormat(Format.XML);
	}

	public void Save(String saveLocation) {

		if (this.getFormat() == Format.XML) {
			try {
				XmlWriter.write(this.getXml(), saveLocation);
			} catch (XmlWriteException e) {
				e.printStackTrace();
			}
		} else {
			// TODO: JSON
		}

	}

}
