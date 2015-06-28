package com.preprocessing;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiran on 6/27/15.
 */
public class TextPreprocessor {

    public String filePath;

    public TextPreprocessor() {
        this.filePath = "/home/kiran/majorproject/SentimentAnalysis/src/testdata.txt";
    }


    //tokenize by sentence
    public void tokenizeBySentence() throws IOException {
        DocumentPreprocessor dp = new DocumentPreprocessor(filePath);
        for (List sentence : dp) {
            System.out.println(sentence);
        }
    }

    //tokenize by token
    public void tokenizeByWord() throws IOException {
        PTBTokenizer ptbt = new PTBTokenizer(new FileReader(filePath),
                new CoreLabelTokenFactory(), "");
        for (CoreLabel label; ptbt.hasNext(); ) {
            label = (CoreLabel) ptbt.next();
            System.out.println(label);
        }


    }

    // sentence split
    public void splitSentence() throws IOException {
        DocumentPreprocessor dp = new DocumentPreprocessor(filePath);
        List<String> sentenceList = new ArrayList<String>();
        System.out.println(dp.toString());
        for (List<HasWord> sentence : dp) {
            String sentenceString = Sentence.listToString(sentence);
            sentenceList.add(sentenceString.toString());
        }
    }

    //part of speech tagger
    public void posTags(ArrayList<String> allTweet){

        String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/" +
                "english-left3words-distsim.tagger";
        // Initialize the tagger;
        MaxentTagger tagger = new MaxentTagger(taggerPath);
        for(int i=0; i<allTweet.size(); i++){
            String tagged = tagger.tagString(allTweet.get(i));
            System.out.println(tagged);
        }
    }

}
