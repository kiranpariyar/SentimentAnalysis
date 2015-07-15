package com.processing;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kiran on 7/2/15.
 */
public class TweetPreproces {

    public ArrayList<String> getPreprocessedTweet(ArrayList<String> tweetList){

        System.out.println(tweetList.size());

        for (int i=0; i<tweetList.size(); i++){
            String str=tweetList.get(i);
            //removing @ and # tags
//            System.out.println("removing @ and # tags");
            Pattern pattern1 = Pattern.compile("@|#");
            Matcher matcher1 = pattern1.matcher(str);
            str = matcher1.replaceAll("");
//            System.out.println(str);

            //replacing more than one ! with one !
//            System.out.println("replacing more than one ! with one !");
            Pattern pattern2 = Pattern.compile("!{1,}");
            Matcher matcher2 = pattern2.matcher(str);
            str = matcher2.replaceAll("!");
//            System.out.println(str);

            //removing additional space
//            System.out.println("removing additional space");
            Pattern pattern3 = Pattern.compile("\\s{1,}");
            Matcher matcher3 = pattern3.matcher(str);
            str = matcher3.replaceAll(" ");
//            System.out.println(str);

            //matching and removing URL
//            System.out.println("matching and removing URL");
            Pattern pattern4 = Pattern.compile("(https?|ftp|file|http)://" +
                    "[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
            Matcher matcher4 = pattern4.matcher(str);
            str = matcher4.replaceAll("");
//            System.out.println(str);
            tweetList.set(i,str);
        }

        return tweetList;
    }
}
