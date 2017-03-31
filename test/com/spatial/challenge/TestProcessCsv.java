package com.spatial.challenge;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class TestProcessCsv {
	private List<String> coordinates;

	@Before
	public void setup() {
		coordinates = new ArrayList<>();
		coordinates.add("40.09646484949391");
		coordinates.add("-105.22545903795344");
		coordinates.add("40.08748229071406");
		coordinates.add("-105.23721161717157");
	}

	@Test
	public void testIsInBox() {
		double lat = 40.09437269;
		double lng = -105.2254991;
		
		Assert.assertEquals(true, ProcessCsv.isInBox(lat, lng, coordinates));
		Assert.assertEquals(false, ProcessCsv.isInBox(lat, lng, new ArrayList<String>()));
		Assert.assertEquals(false, ProcessCsv.isInBox(0.0d, 0.0d, coordinates));

		lat = 41.09437269;
		lng = -105.2254991;
		Assert.assertEquals(false, ProcessCsv.isInBox(lat, lng, coordinates));
	}


	@Test
	public void testCleanTweet() {
		String tweet = "THIS PRODUCT IS AMAZING";
		Assert.assertEquals(tweet.toLowerCase(), ProcessCsv.cleanTweet(tweet));
		
		tweet = "######";
		Assert.assertEquals("", ProcessCsv.cleanTweet(tweet));
		
		tweet = "www.google.com @nyc this is awesome";
		Assert.assertEquals(" @nyc this is awesome", ProcessCsv.cleanTweet(tweet));
	}

}
