package uk.co.bluegumtree.code.java.goodreads.recommender.model;

public enum Scope {

	POPULAR, MEDIOCRE, UNPOPULAR, ALL;

	@Override
	public String toString() {

		return super.toString().toLowerCase();

	}

}
