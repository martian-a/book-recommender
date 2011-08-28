package co.uk.bluegumtree.code.java.api.goodreads;

@SuppressWarnings("serial")
public class InvalidLanguageException extends Exception {

	private Integer id;
	private String abbreviation;

	public InvalidLanguageException(Integer currId) {
		super("Invalid language code: " + currId);
		this.id = currId;
	}

	public InvalidLanguageException(String currAbbreviation) {
		super("Invalid language abbreviation: " + currAbbreviation);
		this.abbreviation = currAbbreviation;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public Integer getId() {
		return this.id;
	}

}
