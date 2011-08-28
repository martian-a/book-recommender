package uk.co.bluegumtree.code.java.goodreads.collector.model;

public class GoodreadsApiConnectionSettings {

	private static final String LOCATION = "http://www.goodreads.com/";
	private static final String KEY = "LNWVHb7t6EER4dReUzfZg";
	private static final String SECRET = "DI2sjBbdRzoEoWkrQTz48QQlHY47OCTQIoALP0RwkTU";

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
