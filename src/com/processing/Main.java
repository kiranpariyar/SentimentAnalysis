package com.processing;

import com.databaseconnection.DataInsertion;
import com.dataretrievtion.RetrieveData;
import com.dataretrievtion.Tweet;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        int sizeOfTweet;
        ArrayList<Tweet> tweetArrayList = new ArrayList<Tweet>();
        ArrayList<String> tweets = new ArrayList<String>();
        ArrayList<Integer> tweetRank = new ArrayList<Integer>();

        RetrieveData retrieveData = new RetrieveData();
        tweetArrayList = retrieveData.getTweetObjectList();
        sizeOfTweet = tweetArrayList.size();

        for(int i=0; i<sizeOfTweet; i++){
            tweets.add(tweetArrayList.get(i).getTweet());
        }



//      sentiment analysis of tweets
        SentimentAnalysis.init();
        tweetRank = SentimentAnalysis.findSentiment(tweets);

        for(int i=0; i<sizeOfTweet; i++){
            tweetArrayList.get(i).setSentimentRank(tweetRank.get(i));
            System.out.println(tweetArrayList.get(i).getTweet()+" : "+
                    tweetArrayList.get(i).getSentimentRank());
        }

        //saving data to mongodb

        DataInsertion dataInsertion = new DataInsertion();
        dataInsertion.insertTweetList(tweetArrayList);

    }
}
