package uk.co.bluegumtree.code.java.goodreads.collector.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;

import co.uk.bluegumtree.code.java.api.ApiConnection;

public class GoogleApiConnection extends ApiConnection {

	public static Calendar previousRequestTime;

	public GoogleApiConnection() throws MalformedURLException {
		super(GoogleApiConnectionSettings.getLocation());
		this.setMode(Mode.GET);
	}

	@Override
	public void setResponse(String currResponse) {
		this.setResponse(new GoogleApiResponse(currResponse));
	}

	public void submitRequest(GoogleApiRequest currRequest) throws IOException {

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
