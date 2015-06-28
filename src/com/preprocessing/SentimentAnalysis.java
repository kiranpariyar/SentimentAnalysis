package com.preprocessing;

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

    public static void init() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(properties);
    }

    public static void findSentiment(ArrayList<String> alltweet) {
        String tweet;

        for(int i=0; i<alltweet.size(); i++) {
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

            switch (mainSentiment){
                case 0:
                    System.out.println(tweet + " :Very Negative "+ mainSentiment );
                    break;
                case 1:
                    System.out.println(tweet + " : Negative "+ mainSentiment );
                    break;
                case 2:
                    System.out.println(tweet + " : Neutral "+ mainSentiment );
                    break;
                case 3:
                    System.out.println(tweet + " : Positive "+ mainSentiment );
                    break;
                case 4:
                    System.out.println(tweet + " : Very Positive "+ mainSentiment );
                    break;
                default:
                    System.out.println("Sentiment cannot be predicted:");
            }
        }
    }
}
