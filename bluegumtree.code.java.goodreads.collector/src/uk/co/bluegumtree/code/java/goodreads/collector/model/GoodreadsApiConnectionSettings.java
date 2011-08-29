package uk.co.bluegumtree.code.java.goodreads.collector.model;

public class GoodreadsApiConnectionSettings {

	private static final String LOCATION = "http://www.goodreads.com/";
	private static final String KEY = "ADD YOUR OWN";
	private static final String SECRET = "ADD YOUR OWN";

	public static String getKey() {
		return KEY;
	}

	public static String getLocation() {
		return LOCATION;
	}

	public static String getsSecret() {
		return SECRET;
	}
}
