package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;

import co.uk.bluegumtree.code.java.api.ApiConnection;
import co.uk.bluegumtree.code.java.api.InvalidApiRequestException;

public class GoodreadsApiConnection extends ApiConnection {

	public static Calendar previousRequestTime;
	private String username;

	public GoodreadsApiConnection() throws MalformedURLException {
		this(null);
	}

	public GoodreadsApiConnection(String currUsername) throws MalformedURLException {
		super(GoodreadsApiConnectionSettings.getLocation());
		this.username = currUsername;
		this.setMode(Mode.GET);
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public void setResponse(String currResponse) {
		this.setResponse(new GoodreadsApiResponse(currResponse));
	}

	public void submitRequest(GoodreadsApiRequest currRequest) throws IOException, InvalidApiRequestException {

		// Check that at least a minute has passed since last request
		if (previousRequestTime != null) {

			long interval = 0;
			do {
				Calendar now = Calendar.getInstance();
				interval = Math.abs((now.getTimeInMillis() - previousRequestTime.getTimeInMillis()));
			} while (interval < 1000);
		}

		// Submit the request
		previousRequestTime = Calendar.getInstance();
		super.submitRequest(currRequest.getRequestString());
	}
}
