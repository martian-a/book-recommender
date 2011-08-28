package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import java.util.Comparator;
import java.util.Map;

public class ComparatorValueIntegerInteger implements Comparator<Integer> {

	Map<Integer, Integer> base;

	public ComparatorValueIntegerInteger(Map<Integer, Integer> currBase) {
		this.base = currBase;
	}

	@Override
	public int compare(Integer a, Integer b) {

		Integer valueA = this.base.get(a);
		Integer valueB = this.base.get(b);

		if (valueA < valueB) {

			// B is greater than A
			return 1;

		} else if (valueA > valueB) {

			// A is greater than B
			return -1;

		} else {

			// Equal value
			return 0;

		}
	}
}
