package uk.co.bluegumtree.code.java.goodreads.recommender.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.TreeMap;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class ProfileTest {

	@Rule
	public TestName name = new TestName();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetMean_DescriptiveStats() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		DescriptiveStatistics stats = new DescriptiveStatistics();
		stats.addValue(240);

		// Evaluate
		assertEquals((int) stats.getMean(), 240);

		stats.addValue(60);

		// Evaluate
		assertEquals((int) stats.getMean(), 150);

	}

	@Test
	public void testGetMean_TreeMapOfIntegerInteger() {

		// Announce
		System.out.println(this.name.getMethodName());

		// Set scene
		TreeMap<Integer, Integer> data = new TreeMap<Integer, Integer>();
		data.put(240, 1);

		// Evaluate
		assertEquals(Profile.getMean(data), 240);

	}

}
