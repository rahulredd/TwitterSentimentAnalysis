package com.spatial.challenge;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessCsv {
	public static final Logger LOG = LoggerFactory.getLogger(ProcessCsv.class);

	public static void csvToMap(String csvFile, HashMap<Integer, Integer> sentimentCount, List<List<String>> coordinates, HashMap<Integer, Set<String>> box) throws IOException {
		String line = "";
		String cvsSplitBy = ",";
		String latitude = null;
		String longitude = null;
		String tweet = null;
		int count = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String header = br.readLine();
			LOG.info("Reading CSV");
			while ((line = br.readLine()) != null) {
				//TODO - Testing.Clean up after use 
				if (count == 1000) {
					break;
				}
				count++;
				if(line.length() > 0) {
					String[] tweets = line.split(cvsSplitBy);
					if (tweets.length > 2) {
						if (!tweets[0].isEmpty()) {
							latitude = tweets[0];
						}
						if (!tweets[1].isEmpty()) {
							longitude = tweets[1];
						}
						if (!tweets[2].isEmpty()) {
							tweet = tweets[2];
						}
						if (latitude != null && longitude != null && tweet != null) {
							double lat = 0.0d;
							double lng = 0.0d;
							try {
								lat = Double.parseDouble(latitude);
							} catch (NumberFormatException e) {
								LOG.warn("Invalid Latitude in csv", e.getMessage());
								continue;
							}
							try {
								lng = Double.parseDouble(longitude);
							} catch (NumberFormatException e) {
								LOG.warn("Invalid Longitude in csv", e.getMessage());
								continue;
							}
							tweet = cleanTweet(tweet);
							if (tweet.isEmpty()) {
								LOG.warn("Empty tweet in csv");
								continue;
							}
							for (int i = 0; i < coordinates.size(); i++) {
								List<String> coordinate = coordinates.get(i);
								if(isInBox(lat, lng, coordinate)) {
									int sentiment = 0;
									if (box.containsKey(i)) {
										if (!box.get(i).contains(tweet)) {
											sentiment = GenerateSentiment.findSentiment(tweet);
											box.get(i).add(tweet);
											sentimentCount.put(i, sentimentCount.get(i) + sentiment);
										}
									} else {
										sentiment = GenerateSentiment.findSentiment(tweet);
										Set<String> list = new HashSet<>();
										list.add(tweet);
										box.put(i, list);
										sentimentCount.put(i, sentiment);
									}
									break;
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}

	public static boolean isInBox(double lat, double lng, List<String> coordinate) {
		if (coordinate.isEmpty()) {
			return false;
		}
		double leftX = Double.parseDouble(coordinate.get(0));
		double leftY = Double.parseDouble(coordinate.get(2));
		double rightX = Double.parseDouble(coordinate.get(1));
		double rightY = Double.parseDouble(coordinate.get(3));
		if ((leftY <= lat && lat <= leftX) && (rightY <= lng && lng <= rightX)) {
			return true;
		}
		return false;
	}

	public static String cleanTweet(String tweet) {
		tweet = tweet.toLowerCase();
		tweet = tweet.replaceAll("((www\\.[^\\s]+)|(https?://[^\\s]+))", "");
		tweet = tweet.replaceAll("#", "");
		return tweet;
	}
}
