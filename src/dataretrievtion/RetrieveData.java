package dataretrievtion;

/**
 * Created by kiran on 6/29/15.
 */

import com.preprocessing.SentimentAnalysis;
import twitter4j.*;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class RetrieveData {

    //	Set this to your actual CONSUMER KEY and SECRET for your application as given to you by dev.twitter.com
    private static final String CONSUMER_KEY		= "kmA2pQFjmRmPibhp8RBtEe8aB";
    private static final String CONSUMER_SECRET 	= "XrQRsmUuAdWSH8vKm9kFlw7JvNo9CCN2erCIcE4QnGfk9xntEv";

    //	How many tweets to retrieve in every call to Twitter. 100 is the maximum allowed in the API
    private static final int TWEETS_PER_QUERY		= 5;

    private static final int MAX_QUERIES			= 1;

    //private static final String SEARCH_TERM= "until:2015-01-22 and love";
    private static final String SEARCH_TERM= "since:2015-06-24 and samsung";

    public static String cleanText(String text)
    {
        text = text.replace("\n", "\\n");
        text = text.replace("\t", "\\t");

        return text;
    }

    public static OAuth2Token getOAuth2Token()
    {
        OAuth2Token token = null;
        ConfigurationBuilder cb;

        cb = new ConfigurationBuilder();
        cb.setApplicationOnlyAuthEnabled(true);

        cb.setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_SECRET);

        try
        {
            token = new TwitterFactory(cb.build()).getInstance().getOAuth2Token();
        }
        catch (Exception e)
        {
            System.out.println("Could not get OAuth2 token");
            e.printStackTrace();
            System.exit(0);
        }

        return token;
    }

    /**
     * Get a fully application-authenticated Twitter object useful for making subsequent calls.
     *
     * @return	Twitter4J Twitter object that's ready for API calls
     */
    public static Twitter getTwitter()
    {
        OAuth2Token token;

        //	First step, get a "bearer" token that can be used for our requests
        token = getOAuth2Token();

        //	Now, configure our new Twitter object to use application authentication and provide it with
        //	our CONSUMER key and secret and the bearer token we got back from Twitter
        ConfigurationBuilder cb = new ConfigurationBuilder();

        cb.setApplicationOnlyAuthEnabled(true);

        cb.setOAuthConsumerKey(CONSUMER_KEY);
        cb.setOAuthConsumerSecret(CONSUMER_SECRET);

        cb.setOAuth2TokenType(token.getTokenType());
        cb.setOAuth2AccessToken(token.getAccessToken());

        //	And create the Twitter object!
        return new TwitterFactory(cb.build()).getInstance();

    }


    public static ArrayList<Tweet> getTweetObjectList()
    {
        ArrayList<Tweet>  tweetObjectList = new ArrayList<Tweet>();
        int	totalTweets = 0;
        long maxID = -1;
        String sentRank = " ";

        Twitter twitter = getTwitter();

        //	Now do a simple search to show that the tokens work
        try
        {

            //	This returns all the various rate limits in effect for us with the Twitter API
            Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus("search");

            //	This finds the rate limit specifically for doing the search API call we use in this program
            RateLimitStatus searchTweetsRateLimit = rateLimitStatus.get("/search/tweets");


            //	Always nice to see these things when debugging code...
            System.out.printf("You have %d calls remaining out of %d, Limit resets in %d seconds\n",
                    searchTweetsRateLimit.getRemaining(),
                    searchTweetsRateLimit.getLimit(),
                    searchTweetsRateLimit.getSecondsUntilReset());


            //	This is the loop that retrieve multiple blocks of tweets from Twitter
            for (int queryNumber=0;queryNumber < MAX_QUERIES; queryNumber++)
            {
                System.out.printf("\n\n!!! Starting loop %d\n\n", queryNumber);

                //	Do we need to delay because we've already hit our rate limits?
                if (searchTweetsRateLimit.getRemaining() == 0)
                {
                    //	Yes we do, unfortunately ...
                    System.out.printf("!!! Sleeping for %d seconds due to rate limits\n", searchTweetsRateLimit.getSecondsUntilReset());
                    Thread.sleep((searchTweetsRateLimit.getSecondsUntilReset()+2) * 1000l);
                }

                Query q = new Query(SEARCH_TERM);			// Search for tweets that contains this term
                q.setCount(TWEETS_PER_QUERY);				// How many tweets, max, to retrieve
                //q.resultType("recent");						// Get all tweets
                q.setLang("en");							// English language tweets, please
                if (maxID != -1)
                {
                    q.setMaxId(maxID - 1);
                }

                //	This actually does the search on Twitter and makes the call across the network
                QueryResult r = twitter.search(q);

                if (r.getTweets().size() == 0)
                {
                    break;			// Nothing? We must be done
                }

                for (Status s: r.getTweets())				// Loop through all the tweets...
                {
                    //	Increment our count of tweets retrieved
                    totalTweets++;

                    //	Keep track of the lowest tweet ID.  If you do not do this, you cannot retrieve multiple
                    //	blocks of tweets...
                    if (maxID == -1 || s.getId() < maxID)
                    {
                        maxID = s.getId();
                    }

                    //	Do something with the tweet....
                    /*System.out.printf("At %s, @%-20s said:  %s\n",
                            s.getCreatedAt().toString(),
                            s.getUser().getScreenName(),
                            cleanText(s.getText()));*/

//                    SentimentAnalysis.init();
//                    String sentiRank = SentimentAnalysis.findSentiment(cleanText(s.getText()));

                    Tweet tweetObject = new Tweet();
                    tweetObject.setDate(s.getCreatedAt());
                    tweetObject.setName(s.getUser().getScreenName());
                    tweetObject.setTweet(cleanText(s.getText()));
                    tweetObject.setRetweet(s.getRetweetCount());
                    tweetObject.setNoOfFollower(s.getUser().getFollowersCount());
//                    tweetObject.setSentiment(sentiRank);

//                  System.out.println(tweetObject.getTweet()+" : "+tweetObject.getSentiment());
                    tweetObjectList.add(tweetObject);

                    /*PrintWriter out = new PrintWriter(new FileWriter("/home/kiran/Desktop/output.txt",true));
                    out.print(s.getCreatedAt().toString() );
                    out.println(s.getUser().getScreenName());
                    out.println(cleanText(s.getText()));
                    out.println(s.getUser().getFollowersCount());
                    out.close();*/
                }

                //	As part of what gets returned from Twitter when we make the search API call, we get an updated
                //	status on rate limits.  We save this now so at the top of the loop we can decide whether we need
                //	to sleep or not before making the next call.
                searchTweetsRateLimit = r.getRateLimitStatus();
            }
        }
        catch (Exception e)
        {
            //	Catch all -- you're going to read the stack trace and figure out what needs to be done to fix it
            System.out.println("That didn't work well...wonder why?");
            e.printStackTrace();
        }

        System.out.printf("\n\nA total of %d tweets retrieved\n", totalTweets);
        return tweetObjectList;
    }
}