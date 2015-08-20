package com.testing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.processing.SentimentAnalysis;
import com.processing.TweetPreproces;



/**
 *CsvParser.java
 *
 *Aug 6, 2015 10:44:38 PM
 * @author madandhungana@gmail.com
 *
 *
 */
public class CsvParser  {
 
  
public static void main(String[] args) {
	  
	List<Test> list=new ArrayList<Test>();
	ArrayList<String> list2=new ArrayList<String>();
	ArrayList<Integer> sentimentRank=new ArrayList<Integer>();
	String csvFile = "D:/code-home/SentimentAnalysis/test_data/testdata.csv";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = "\t";
	int count=0;
	int c=3;
	int d=5;
	
	
 	try {
		 
		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {
			count++;		    
			String []tweet = line.split(cvsSplitBy);
			System.out.println(count);
//			System.out.println(tweet[1]);
//			System.out.println(tweet[1]+ tweet[3]);
			Test te=new Test(tweet[3],Integer.parseInt(tweet[1]));    // add data to object
			list.add(te);						//add object to list
		}
		
			
		for(int i=0;i<list.size();i++){
				list2.add(list.get(i).getTweet());
			}
		
		long l=System.currentTimeMillis();
		System.out.println(l);
		
//		   Pre-processing
        TweetPreproces tweetPreproces = new TweetPreproces();
        list2 = tweetPreproces.getPreprocessedTweet(list2);

		System.out.println("Preprocessing completed");
		
		sentimentRank = SentimentAnalysis.findSentiment(list2);
		
		long m=System.currentTimeMillis();
		
		long n=m-l;
		System.out.println(n);
		for(int i=0;i<sentimentRank.size();i++){
			
			
			int a= list.get(i).getSentimentRank();
			int b =-1;
			if(sentimentRank.get(i)==3||sentimentRank.get(i)==4){
				b=1;
			}
			else if(sentimentRank.get(i)==0||sentimentRank.get(i)==1){
				b=0;
			}
			else{
				d--;
			}
			
			if(a==b){
				c++;
			} else{
				d++;
			}
				
			
		}
		
		System.out.println("-----------------------------------------------------------------------------------------------");
		System.out.println(c+"\t"+d);
		
		float f=c*100/(c+d);
		System.out.println("Percentage accuracy is: "+ f);
		
		
 
	} catch (Exception e) {
		e.printStackTrace();
	}  finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	System.out.println("Done");
  }
 
}