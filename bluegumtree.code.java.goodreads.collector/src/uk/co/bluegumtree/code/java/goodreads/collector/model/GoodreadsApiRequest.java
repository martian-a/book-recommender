package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.util.HashMap;
import java.util.Map;

import co.uk.bluegumtree.code.java.api.ApiRequest;
import co.uk.bluegumtree.code.java.api.InvalidApiRequestException;

public class GoodreadsApiRequest extends ApiRequest {

	public GoodreadsApiRequest(HashMap<String, String> currParameters) {
		super(currParameters);
	}

	public GoodreadsApiRequest(String currMethod) {
		super(currMethod);
	}

	public GoodreadsApiRequest(String currMethod, HashMap<String, String> currParameters, String relativePath) {
		super(currMethod, currParameters, relativePath);
	}

	public String getRequestString() throws InvalidApiRequestException {

		String requestString = this.getRelativePath();

		requestString = requestString + "?key=" + GoodreadsApiConnectionSettings.getKey() + this.toString();

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