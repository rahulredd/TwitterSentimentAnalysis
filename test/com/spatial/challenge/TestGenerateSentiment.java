package com.spatial.challenge;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class TestGenerateSentiment {

	@Before
	public void setup() throws IOException {
		GenerateSentiment.init();
	}
	
	@Test
	public void testFindSentiment() {
		String tweet = "This coding challenge is awesome";
		int sentiment = GenerateSentiment.findSentiment(tweet);
		Assert.assertEquals(2, sentiment);
		
		tweet = "";
		sentiment = GenerateSentiment.findSentiment(tweet);
		Assert.assertEquals(0, sentiment);
		
		tweet = "The climate is horrible";
		sentiment = GenerateSentiment.findSentiment(tweet);
		Assert.assertEquals(1, sentiment);
	}
}
