package com.databaseconnection;

import com.dataretrievtion.Tweet;
import com.mongodb.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kiran on 7/2/15.
 */
public class DataInsertion {

    public void insertTweetList(ArrayList<Tweet> tweetArrayList) throws IOException{



        MongoClient mongoClient = new MongoClient("localhost",27017);
        DB db = mongoClient.getDB("tweetdatabase");
        DBCollection dbCollection = db.getCollection("tweet");
        BasicDBList basicDBList = new BasicDBList();
        DBObject[] totalrecord = new BasicDBObject[tweetArrayList.size()];

        System.out.println("/**** Inserting data into mongodb *****/");
        for(int i=0; i<tweetArrayList.size(); i++){

            BasicDBObject basicDbObject = new BasicDBObject();
            basicDbObject.put("date", tweetArrayList.get(i).getDate());
            basicDbObject.put("name", tweetArrayList.get(i).getName());
            basicDbObject.put("tweet", tweetArrayList.get(i).getTweet());
            basicDbObject.put("noOfFollower", tweetArrayList.get(i).getNoOfFollower());
            basicDbObject.put("retweet", tweetArrayList.get(i).getRetweet());
            basicDbObject.put("sentiment", tweetArrayList.get(i).getSentiment());
            basicDbObject.put("sentimentRank", tweetArrayList.get(i).getSentimentRank());
            totalrecord[i] = basicDbObject;
        }

        dbCollection.insert(totalrecord);

        System.out.println("/********* Data insertion of mongodb successful *********/");

    }
}
