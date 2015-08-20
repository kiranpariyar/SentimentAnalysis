package com.testing;

/**
 *Test.java
 *
 *Aug 6, 2015 10:44:55 PM
 * @author madandhungana@gmail.com
 *
 *
 */
public class Test {
	
	private String tweet;
	private int sentimentRank;
	
	

		public Test(String tweet, int parseInt) {
		this.tweet=tweet;
		this.sentimentRank=parseInt;
	}
		
		public String getTweet(){
			return this.tweet;
		}
	
		public void setTweet(String string){
			this.tweet= string;
		}
		
		public void setSentimentRank(int a){
			this.sentimentRank=a;
		}
		
		public int getSentimentRank(){
			return this.sentimentRank;
		}
	
	/*public void setTweet() {
		
	}*/
	
	
	
	
	
	

}
