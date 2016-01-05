package in.techieme.nlp.sentencecreation.core;

import in.techieme.nlp.lm.core.BiGramModel;
import in.techieme.nlp.lm.core.Model;
import in.techieme.nlp.lm.core.TriGramModel;
import in.techieme.nlp.lm.core.UnigramModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SentenceCreationClient {

	private int sentenceLength = 50;

	public String sentenceFromPhraseUnigrams(String words) {
		UnigramModel model = new UnigramModel();
		model.train();

		String[] split = words.split(" ");
		List<String> wordList = new ArrayList<String>();
		for (String s : split)
			wordList.add(s);
		StringBuilder sb = new StringBuilder("<s> ");
		int length = wordList.size();
		do {
			String nextToken = model.predictNextWord("", wordList);
			Iterator<String> it = wordList.iterator();
			while (it.hasNext()) {
				if (nextToken.equals(it.next())) {
					it.remove();
				}
			}
			sb.append(nextToken).append(" ");
			length--;
		} while (length > 0);
		sb.append("</s>");
		String sentence = sb.toString();
		sentence = sentence.replace("<s>", "");
		sentence = sentence.replace("</s>", "");
		return sentence;
	}

	public String randomSentenceUnigrams() {
		UnigramModel model = new UnigramModel();
		model.train();

		StringBuilder sb = new StringBuilder("<s> ");
		for (int i = 1; i < sentenceLength - 1; i++) {
			sb.append(model.predictNextWord("")).append(" ");
		}
		sb.append("</s>");
		String sentence = sb.toString();
		sentence = sentence.replace("<s>", "");
		sentence = sentence.replace("</s>", "");
		return sentence;
	}

	public String sentenceFromPhraseBigrams(String words) {
		BiGramModel bModel = new BiGramModel();
		bModel.train();
		String[] split = words.split(" ");
		List<String> wordList = new ArrayList<String>();
		for (String s : split)
			wordList.add(s);
		StringBuilder sb = new StringBuilder("<s> ");
		int length = wordList.size();
		do {
			String nextToken = bModel.predictNextWord(sb.toString(), wordList);
			Iterator<String> it = wordList.iterator();
			while (it.hasNext()) {
				if (nextToken.equals(it.next())) {
					it.remove();
				}
			}
			sb.append(nextToken).append(" ");
			length--;
		} while (length > 0);
		sb.append("</s>");
		String sentence = sb.toString();
		sentence = sentence.replace("<s>", "");
		sentence = sentence.replace("</s>", ".");
		return sentence;
	}

	public String randomSentenceBigrams() {
		BiGramModel bModel = new BiGramModel();
		bModel.train();
		StringBuilder sbBGram = new StringBuilder("<s> ");
		for (int i = 1; i < sentenceLength - 1; i++) {
			String nextToken = bModel.predictNextWord(sbBGram.toString());
			if (Model.END_SENT.equals(nextToken)) {
				sbBGram.append(nextToken).append(" ");
				sbBGram.append(Model.BEGIN_SENT).append(" ");
			} else {
				sbBGram.append(nextToken).append(" ");
			}
		}
		sbBGram.append("</s>");
		String sentence = sbBGram.toString();
		sentence = sentence.replace("<s>", "");
		sentence = sentence.replace("</s>", "");
		return sentence;
	}

	public String sentenceFromPhraseTrigrams(String words) {
		return null;
	}

	public String randomSentenceTrigrams() {
		TriGramModel tModel = new TriGramModel();
		tModel.train();
		StringBuilder sbTGram = new StringBuilder("<s> i ");
		for (int i = 1; i < sentenceLength - 1; i++) {
			String nextToken = tModel.predictNextWord(sbTGram.toString());
			if (Model.END_SENT.equals(nextToken)) {
				sbTGram.append(nextToken).append(" ");
				sbTGram.append(Model.BEGIN_SENT).append(" ");
				sbTGram.append("i ");
			} else {
				sbTGram.append(nextToken).append(" ");
			}
		}
		String sentence = sbTGram.toString();
		sentence = sentence.replace("<s>", "");
		sentence = sentence.replace("</s>", "");
		return sentence;
	}
}
