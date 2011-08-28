package uk.co.bluegumtree.code.java.goodreads.collector.model;

import co.uk.bluegumtree.code.java.api.ApiResponse;

public class OpenLibraryApiResponse extends ApiResponse {

	public OpenLibraryApiResponse(String currResponse) {
		super(currResponse);
		this.setFormat(Format.JSON);
	}
}
