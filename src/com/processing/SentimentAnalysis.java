package com.processing;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by kiran on 6/26/15.
 */
public class SentimentAnalysis {


    static StanfordCoreNLP pipeline;
    public static ArrayList<Integer> sentimentRank;

    //initializing pipeline with annotators "tokenize, ssplit parse and sentiment"
    public static void init() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(properties);
    }

    //performing sentiment analysis on tweets
    public static ArrayList<Integer> findSentiment(ArrayList<String> alltweet) {
        sentimentRank = new ArrayList<Integer>();
        String tweet;

        for (int i = 0; i < alltweet.size(); i++) {
            int mainSentiment = 0;
            tweet = alltweet.get(i);
            if (tweet != null && tweet.length() > 0) {
                int longest = 0;
                Annotation annotation = pipeline.process(tweet);
                for (CoreMap sentence : annotation
                        .get(CoreAnnotations.SentencesAnnotation.class)) {
                    Tree tree = sentence
                            .get(SentimentCoreAnnotations.AnnotatedTree.class);
                    int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                    String partText = sentence.toString();
                    if (partText.length() > longest) {
                        mainSentiment = sentiment;
                        longest = partText.length();
                    }
                }
            }
//            System.out.println(tweet + " : "+ mainSentiment );
            sentimentRank.add(mainSentiment);
        }

        return sentimentRank;
    }
}
