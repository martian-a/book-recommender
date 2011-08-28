package uk.co.bluegumtree.code.java.goodreads.recommender.model;

public enum Factor {

	TOTALPAGES, TITLELENGTH, PUBLICATIONYEAR, ORIGINALPUBLICATIONYEAR, ALL;

	public String getFilename() throws InvalidFactorException {

		if (this.equals(TOTALPAGES)) {
			return "total_pages";
		}

		if (this.equals(TITLELENGTH)) {
			return "title_length";
		}

		if (this.equals(PUBLICATIONYEAR)) {
			return "publication_year";
		}

		if (this.equals(ORIGINALPUBLICATIONYEAR)) {
			return "original_publication_year";
		}

		if (this.equals(ALL)) {
			return "all";
		}

		throw new InvalidFactorException();
	}

}
