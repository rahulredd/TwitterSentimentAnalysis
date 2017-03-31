# Twitter Sentiment Analysis 
Generate some basic sentiment analysis on the attached set of tweets for Boulder and find the area with the best average sentiment. Area is defined as a bounding box a quarter mile by a quarter mile (this doesn't have to be the exact best fit, it's ok to subdivide Boulder into rectangles). Average sentiment is the average sentiment score of tweets within the area, with a four tweet minimum. Your output should be the set of tweets from the area with the best average sentiment, and a brief statement of how you did it. Extra credit if you visual your results on a map. You can use an external library to generate the sentiment scores.

## Getting Started

	1.	Create bounding boxes over Boulder using google maps API. (Divide Boulder into grids)
	2.	Send the NW and SE coordinates of each grid to a servlet.
	3.	In the servlet, read the csv line by line .
	4.	For every latitude longitude pair, iterate over the list of grid coordinates and put it in the grid the pair belongs to. 
	5.	Calculate sentiment for each tweet using Stanford NLP library for that lat/lng pair and add it a hash map and add that tweet to another hash map with key as the grid and value as a list of distinct tweets.  (Using two hash maps- one for keeping track of sentiment count in each grid, and another one for the list of distinct tweets(using a Set of String) in that grid. Key is the grid number and value is sentiment count or list of tweets.  I am using another hashmap to map the grid number to list of coordinates of the grid).
	6.	After processing the entire csv, calculate the average sentiment for each grid by dividing the total sentiment count at that grid with the number of tweets in that grid. (Considering grid with more than 4 tweets). 
	7.	Display the results on the console. 

