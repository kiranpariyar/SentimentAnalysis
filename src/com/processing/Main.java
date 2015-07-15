package com.processing;

import com.databaseconnection.DataInsertion;
import com.dataretrievtion.RetrieveData;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        int sizeOfTweet;

        //for storing tweets and rank
        ArrayList<String> tweets = new ArrayList<String>();
        ArrayList<Integer> tweetRank = new ArrayList<Integer>();

        //Retrieving Tweets from twitter
        System.out.println("Retrieving Tweets from twitter :\n");
        RetrieveData retrieveData = new RetrieveData();
        retrieveData.getTweetObjectList();
        sizeOfTweet = retrieveData.tweetObjectList.size();


        for(int i=0; i<sizeOfTweet; i++){
            tweets.add(retrieveData.tweetObjectList.get(i).getTweet());
            System.out.println(tweets.get(i));
        }

        //Preprocessing the tweets
        System.out.print("Preprocessing the tweets :\n");
        TweetPreproces tweetPreproces = new TweetPreproces();
        tweets = tweetPreproces.getPreprocessedTweet(tweets);

        for(String tweet : tweets){
            System.out.println(tweet);
        }


//      sentiment analysis of tweets
        SentimentAnalysis.init();
        tweetRank = SentimentAnalysis.findSentiment(tweets);

        for(int i=0; i<sizeOfTweet; i++){
            retrieveData.tweetObjectList.get(i).setSentimentRank(tweetRank.get(i));
            System.out.println(retrieveData.tweetObjectList.get(i).getTweet()+" : "+
                    retrieveData.tweetObjectList.get(i).getSentimentRank());
        }

        //saving data to mongodb

        DataInsertion dataInsertion = new DataInsertion();
        dataInsertion.insertTweetList(retrieveData.tweetObjectList);

    }
}
