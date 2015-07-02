package com.processing;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;

import java.io.StringReader;
import java.util.List;

/**
 * Created by kiran on 6/27/15.
 */
public class DependencyParsing {

    public void parseSentence(String[] args) {
        String modelPath = DependencyParser.DEFAULT_MODEL;
        String taggerPath = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";

        for (int argIndex = 0; argIndex < args.length; ) {
            if (args[argIndex].equals("-tagger")) {
                taggerPath = args[argIndex + 1];
                argIndex += 2;

            } else if (args[argIndex].equals("-model")) {
                modelPath = args[argIndex + 1];
                argIndex += 2;

            } else {
                throw new RuntimeException("Unknown argument " + args[argIndex]);
            }
        }

        String text = "I can almost always tell when movies use fake dinosaurs.";

        MaxentTagger tagger = new MaxentTagger(taggerPath);
        DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);

        DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));
        for (List<HasWord> sentence : tokenizer) {
            List<TaggedWord> tagged = tagger.tagSentence(sentence);
            GrammaticalStructure gs = parser.predict(tagged);

            // Print typed dependencies
            System.err.println(gs);

        }
    }
}
