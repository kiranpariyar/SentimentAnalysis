package com.processing;

import com.databaseconnection.DatabaseConnection;
import com.dataretrievtion.RetrieveData;
import com.dataretrievtion.Tweet;

import java.io.IOException;
import java.util.ArrayList;


public class Main {


    public static void main(String[] args) throws IOException {
        int sizeOfTweet;
        ArrayList<Tweet> tweetObjectList;
        //for storing tweets and rank
        ArrayList<String> tweets = new ArrayList<String>();
        ArrayList<Integer> tweetRank = new ArrayList<Integer>();

        //Retrieving Tweets from twitter
        System.out.println("Retrieving Tweets from twitter :\n");
        RetrieveData retrieveData = new RetrieveData();
        tweetObjectList = retrieveData.getTweetObjectList();
        sizeOfTweet = tweetObjectList.size();


        for(int i=0; i<sizeOfTweet; i++){
//            System.out.println(tweetObjectList.get(i).getTweet());
            tweets.add(tweetObjectList.get(i).getTweet());
//            System.out.println(tweets.get(i));
        }

        //Preprocessing the tweets
        System.out.print("Preprocessing the tweets :\n");
        TweetPreproces tweetPreproces = new TweetPreproces();
        tweets = tweetPreproces.getPreprocessedTweet(tweets);

        for(String tweet : tweets){
            System.out.println(tweet);
        }


//      sentiment analysis of tweets
        System.out.println("\nProcessing Sentiment Analysis of Tweets:");
        tweetRank = SentimentAnalysis.findSentiment(tweets);

        for(int i=0; i<sizeOfTweet; i++){
            tweetObjectList.get(i).setSentimentRank(tweetRank.get(i));
            System.out.println(tweetObjectList.get(i).getTweet()+" : "+ tweetObjectList.get(i).getSentimentRank());
        }
        
        //saving data to mongodb
        System.out.println("\n");
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.init();
        databaseConnection.insertTweetList(tweetObjectList);
        databaseConnection.retrieveTweetList();
        databaseConnection.countDistinctRank();
    }
}
