package com.databaseconnection;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class RetrivingMongoData {

    public static void main(String[] args) throws UnknownHostException {
        int positive=0,negative=0,neutral=0;
        String search_term= "Samsung";
        List sentiment = new ArrayList();
        DB db = (new MongoClient("localhost", 27017)).getDB("tweetdatabase");   //connection to database
        System.out.println("Connect to database sueeccessfully");
        DBCollection dbCollection = db.getCollection("tweet");  //getting the collection
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("tweet", new BasicDBObject("$regex", ".*" + search_term +".*")
                .append("$options", "i"));  //query to find the item with string Samsung
        DBCursor cursor;
        cursor = dbCollection.find(basicDBObject);

        while (cursor.hasNext()) {
            DBObject theObj = cursor.next();
            sentiment.add(theObj.get("sentimentRank"));//getting all the sentiment rank for search term
            System.out.println(sentiment.get(0));
        }
    //calculating the number of positive negative and neutral tweets
        for(int i=0;i<sentiment.size();i++)
            if((Integer)sentiment.get(i)==1)
                positive++;
            else if((Integer)sentiment.get(i)==2)
                negative++;
            else
                neutral++;


        System.out.println("Positive tweets are:\t" + positive);
        System.out.println("Negative tweets are:\t" + negative);
        System.out.println("Neutral tweets are:\t" + neutral);

    }


}


