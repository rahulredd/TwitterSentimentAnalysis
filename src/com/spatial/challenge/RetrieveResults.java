package com.spatial.challenge;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 * The challenge is to do some basic sentiment analysis 
 * on the attached set of tweets for Boulder and find the area 
 * with the best average sentiment. 
 * Area is defined as a bounding box a quarter mile by a quarter mile 
 * (this doesn't have to be the exact best fit, it's ok to subdivide Boulder 
 * into rectangles). Average sentiment is the average sentiment score of 
 * tweets within the area, with a four tweet minimum. 
 * Your output should be the set of tweets from the area with the best average 
 * sentiment, and a brief statement of how you did it. 
 * Extra credit if you visual your results on a map. 
 * You can use an external library to generate the sentiment scores.
 */
public class RetrieveResults {
	public static final Logger LOG = LoggerFactory.getLogger(RetrieveResults.class);

	public RetrieveResults() throws IOException {
		GenerateSentiment.init();
	}

	public static int runSentiment(HashMap<Integer, Integer> sentimentCount, HashMap<Integer, Set<String>> box) throws IOException {
		return getResults(sentimentCount, box);
	}

	private static int getResults(HashMap<Integer, Integer> sentimentCount, HashMap<Integer, Set<String>> box) {
		int maxAverage = Integer.MIN_VALUE;
		LOG.info("Generating Results");
		int maxLoc = 0;
		if (box.size() > 0) {
			for (Map.Entry<Integer, Set<String>> set : box.entrySet())  {
				int index = set.getKey();
				if (box.get(index).size() > 3) {
					int sentiment = sentimentCount.get(index);
					int average = sentiment / box.get(index).size();
					if (maxAverage < average) {
						maxAverage = average;
						maxLoc = index;
					}
				}
			}
		}
		return maxLoc;
//		System.out.println(maxLoc + " " + " " + box.get(maxLoc).size() +  " " + box.get(maxLoc) );
//		System.out.println(maxAverage + " " + sentimentCount);
	}

	public static void main(String[] args) throws IOException {
		// Testing
	}
}
