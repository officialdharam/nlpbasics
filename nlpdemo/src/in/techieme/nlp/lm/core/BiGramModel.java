package in.techieme.nlp.lm.core;

import in.techieme.nlp.core.FileIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class BiGramModel implements Model {
	private String _trainingFileName = "C:/WORK_HOME/NLP_COMM_TALK/DEMO DAY/2-lm-training-corpus/1-sentence-training-corpus.txt";
	private Map<String, Integer> unigramCounts = new HashMap<String, Integer>();
	private Map<String, Integer> bigramCounts = new HashMap<String, Integer>();
	private Map<String, Float> bigramProbability = new HashMap<String, Float>();

	// @Override
	/*
	 * public void train() { String fileContent =
	 * FileIO.readFile(_trainingFileName); String[] lines =
	 * fileContent.split("\n"); for (String line : lines) { String[] words =
	 * line.split(" "); for (int i = 0; i < words.length - 1; i++) { String key
	 * = words[i] + ":"; for (int j = i + 1; j < words.length; j++) { String
	 * bigramKey = key + words[j]; Integer count = bigramCounts.get(bigramKey);
	 * if (count == null) count = 0; count++; bigramCounts.put(bigramKey,
	 * count); } } calculateBigramProbability(); } }
	 */

	@Override
	public void train() {
		String fileContent = FileIO.readFile(_trainingFileName);
		String[] lines = fileContent.split("\n");
		for (String line : lines) {
			String[] words = line.split(" ");
			for (int i = 0; i < words.length - 1; i++) {
				String trigram = words[i] + ":" + words[i + 1];
				Integer count = bigramCounts.get(trigram);
				if (count == null)
					count = 0;
				count++;
				bigramCounts.put(trigram, count);
			}
			calculateBigramProbability();
		}
	}

	public Map<String, Integer> getBigramCounts() {
		train();
		return this.bigramCounts;
	}

	private void calculateBigramProbability() {
		UnigramModel model = new UnigramModel();
		unigramCounts = model.getUnigramCounts();
		for (Entry<String, Integer> e : bigramCounts.entrySet()) {
			String key = e.getKey();
			String[] split = key.split(":");
			String unigram = split[0];
			float probablity = (float) ((1.0) * e.getValue() / unigramCounts.get(unigram));
			bigramProbability.put(key, probablity);
		}
	}

	@Override
	public String predictNextWord(String phrase) {
		String[] split = phrase.split(" ");
		int L = split.length;
		String unigram = null;
		if (L > 0)
			unigram = split[L - 1] + ":";

		List<String> bigrams = new ArrayList<String>();
		for (Entry<String, Float> e : bigramProbability.entrySet()) {
			String K = e.getKey();
			if (K.startsWith(unigram)) {
				bigrams.add(e.getKey());
			}
		}

		// find the maximum probability
		float max = 0.0f;
		for (String s : bigrams) {
			Float f = bigramProbability.get(s);
			if (f > max)
				max = f;
		}

		// find all the bigrams which have probability equal to max
		List<String> bigramList = new ArrayList<String>();
		for (String s : bigrams) {
			Float V = bigramProbability.get(s);
			if (V == max)
				bigramList.add(s);
		}

		String string = null;
		if (bigramList.size() > 0) {
			Random rand = new Random();
			string = bigramList.get(rand.nextInt(bigramList.size()));
		} else {
			string = bigramList.get(0);
		}

		return string.split(":")[1];
	}

	@Override
	public String predictNextWord(String phrase, List<String> wordList) {
		String[] split = phrase.split(" ");
		int L = split.length;
		String unigram = null;
		if (L > 0)
			unigram = split[L - 1] + ":";

		List<String> bigrams = new ArrayList<String>();
		for (String s : wordList) {
			bigrams.add(unigram + s);
		}

		// find the maximum probability
		float max = 0.0f;
		for (String s : bigrams) {
			Float f = bigramProbability.get(s);
			if (f > max)
				max = f;
		}

		// find all the bigrams which have probability equal to max
		List<String> bigramList = new ArrayList<String>();
		for (String s : bigrams) {
			Float V = bigramProbability.get(s);
			if (V == max)
				bigramList.add(s);
		}

		String string = null;
		if (bigramList.size() > 0) {
			Random rand = new Random();
			string = bigramList.get(rand.nextInt(bigramList.size()));
		} else {
			string = bigramList.get(0);
		}

		return string.split(":")[1];
	}
}