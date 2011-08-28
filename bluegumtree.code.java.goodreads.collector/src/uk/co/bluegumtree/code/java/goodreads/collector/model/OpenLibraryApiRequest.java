package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.util.HashMap;
import java.util.Map;

import co.uk.bluegumtree.code.java.api.ApiRequest;

public class OpenLibraryApiRequest extends ApiRequest {

	public OpenLibraryApiRequest(String currMethod, HashMap<String, String> currParameters, String relativePath) {
		super(currMethod, currParameters, relativePath);
	}

	public String getRequestString() {

		String requestString = this.getRelativePath();
		requestString = requestString + "?" + this.toString();
		return requestString;
	}

	@Override
	public String toString() {

		String out = "";

		for (Map.Entry<String, String> currParameter : this.parameters.entrySet()) {
			out = out + "&" + currParameter.getKey() + "=" + currParameter.getValue();
		}

		return out;
	}
}
