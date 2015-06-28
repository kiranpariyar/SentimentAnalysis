package com.preprocessing;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<String> tweets = new ArrayList<String>();
        DataInputStream testData = new DataInputStream(new
                FileInputStream("/home/kiran/majorproject/SentimentAnalysis/src/testdata.txt"));
        String count;
        while((count = testData.readLine())!=null) {
            tweets.add(count);
        }
        testData.close();

        //tokenization of text
//        TextPreprocessor textProcess = new TextPreprocessor();
//        textProcess.tokenizeByWord();
//        textProcess.tokenizeBySentence();
//        textProcess.posTags(tweets);


        // dependency parsing
//        DependencyParsing dParser = new DependencyParsing();
//        String[] aggs= {"-tagger","-model"};
//        dParser.parseSentence(args);

        // sentiment analysis of tweets
        SentimentAnalysis.init();
        SentimentAnalysis.findSentiment(tweets);
    }
}
