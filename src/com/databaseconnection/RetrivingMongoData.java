package com.databaseconnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

//import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
public class RetrivingMongoData {
	
	
	public static void main(String[] args) throws UnknownHostException{
		int i=0;
		ArrayList<DBObject> obj=new ArrayList<DBObject>();
		DB db=(new MongoClient("localhost",27017)).getDB("TwitterData");
		System.out.println("Connect to database successfully");
		DBCollection dbCollection=db.getCollection("data");
		/*BasicDBObject basicDBObject = new BasicDBObject();
		basicDBObject.put("tweet","");
		DBCursor dbCursor = dbCollection.find(basicDBObject);
		while(dbCursor.hasNext()) System.out.print(dbCursor.next());*/
		DBCursor cursor =dbCollection.find();
		while(cursor.hasNext()){
			i++;
			//System.out.println(cursor.next());
			obj.add(cursor.next());
			System.out.println(i);
		}
		System.out.println(obj);
		System.out.println(obj.size());
	}

}
