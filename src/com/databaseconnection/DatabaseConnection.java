package com.databaseconnection;

import com.dataretrievtion.Tweet;

import com.mongodb.Block;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import com.mongodb.MongoClient;
import org.bson.Document;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
/**
 * Created by kiran on 7/2/15.
 */
public class DatabaseConnection {

    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection dbCollection;

    public void init() throws IOException {

        //Connect to a MongoDB instance running on the localhost on the default port 27017:
        mongoClient = new MongoClient();
        db = mongoClient.getDatabase("mytweetdata");
        dbCollection = db.getCollection("tweet");
    }


    public void insertTweetList(ArrayList<Tweet> tweetArrayList) throws IOException{

        List<Document> documents = new ArrayList<Document>();

        System.out.println("/**** Inserting data into mongodb *****/");
        for(int i=0; i<tweetArrayList.size(); i++){
            Document document = new Document();
            document.put("date",tweetArrayList.get(i).getDate());
            document.put("date", tweetArrayList.get(i).getDate());
            document.put("name", tweetArrayList.get(i).getName());
            document.put("tweet", tweetArrayList.get(i).getTweet());
            document.put("noOfFollower", tweetArrayList.get(i).getNoOfFollower());
            document.put("retweet", tweetArrayList.get(i).getRetweet());
            document.put("sentiment", tweetArrayList.get(i).getSentiment());
            document.put("sentimentRank", tweetArrayList.get(i).getSentimentRank());
            documents.add(i,document);
        }

        System.out.print(documents);
        dbCollection.insertMany(documents);
        System.out.println(tweetArrayList.size() + " Data insertion of mongodb successful ");

    }


    public void retrieveTweetList(){
        System.out.println("Collection tweet selected successfully");
        System.out.print("Listing database data");
        MongoCursor<Document> cursor = dbCollection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
    }

    public void countDistinctRank(){

        AggregateIterable<Document> iterable = dbCollection.aggregate(asList(
                new Document("$group", new Document("_id", "$sentimentRank").append("count", new Document("$sum", 1)))));


        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {

//             System.out.println(document.toJson());
               System.out.println(document.get("_id") + " : " + document.get("count"));
            }
        });
    }

}
