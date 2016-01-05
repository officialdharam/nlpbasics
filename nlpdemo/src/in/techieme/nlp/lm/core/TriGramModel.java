package in.techieme.nlp.lm.core;

import in.techieme.nlp.core.FileIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class TriGramModel implements Model {
	private String _trainingFileName = "C:/WORK_HOME/NLP_COMM_TALK/DEMO DAY/2-lm-training-corpus/1-sentence-training-corpus.txt";
	private Map<String, Integer> bigramCounts = new HashMap<String, Integer>();
	private Map<String, Integer> trigramCounts = new HashMap<String, Integer>();
	private Map<String, Float> trigramProbability = new HashMap<String, Float>();

	/*
	 * @Override public void train() { String fileContent =
	 * FileIO.readFile(_trainingFileName); String[] lines =
	 * fileContent.split("\n"); for (String line : lines) { String[] words =
	 * line.split(" "); for (int i = 0; i < words.length - 2; i++) { String key
	 * = words[i] + ":"; for (int j = i + 1; j < words.length - 1; j++) { String
	 * bigram = key + words[j] + ":"; for (int k = j + 1; k < words.length; k++)
	 * { String trigram = bigram + words[k]; Integer count =
	 * trigramCounts.get(trigram); if (count == null) count = 0; count++;
	 * trigramCounts.put(trigram, count); } } } calculateTrigramProbability(); }
	 * }
	 */

	@Override
	public void train() {
		String fileContent = FileIO.readFile(_trainingFileName);
		String[] lines = fileContent.split("\n");
		for (String line : lines) {
			String[] words = line.split(" ");
			for (int i = 0; i < words.length - 2; i++) {
				String trigram = words[i] + ":" + words[i + 1] + ":" + words[i + 2];
				Integer count = trigramCounts.get(trigram);
				if (count == null)
					count = 0;
				count++;
				trigramCounts.put(trigram, count);
			}
			calculateTrigramProbability();
		}
	}

	public Map<String, Integer> getTrigramCounts() {
		train();
		return this.trigramCounts;
	}

	private void calculateTrigramProbability() {
		BiGramModel model = new BiGramModel();
		bigramCounts = model.getBigramCounts();
		for (Entry<String, Integer> e : trigramCounts.entrySet()) {
			String key = e.getKey();
			String[] split = key.split(":");
			String bigram = split[0] + ":" + split[1];
			float probablity = (float) ((1.0) * e.getValue() / bigramCounts.get(bigram));
			trigramProbability.put(key, probablity);
		}
	}

	@Override
	public String predictNextWord(String phrase) {
		String[] split = phrase.split(" ");
		int L = split.length;
		String bigram = null;
		if (L > 1)
			bigram = split[L - 2] + ":" + split[L - 1] + ":";

		List<String> trigrams = new ArrayList<String>();
		for (Entry<String, Float> e : trigramProbability.entrySet()) {
			String K = e.getKey();
			if (K.startsWith(bigram)) {
				trigrams.add(e.getKey());
			}
		}

		// find the maximum probability
		float max = 0.0f;
		for (String s : trigrams) {
			Float f = trigramProbability.get(s);
			if (f > max)
				max = f;
		}

		// find all the trigrams which have probability equal to max
		List<String> trigramList = new ArrayList<String>();
		for (String s : trigrams) {
			Float V = trigramProbability.get(s);
			if (V == max)
				trigramList.add(s);
		}

		String string = null;
		if (trigramList.size() > 0) {
			Random rand = new Random();
			string = trigramList.get(rand.nextInt(trigramList.size()));
		} else {
			string = trigramList.get(0);
		}
		System.out.println(string);
		return string.split(":")[2];
	}

	@Override
	public String predictNextWord(String phrase, List<String> wordList) {
		// TODO Auto-generated method stub
		return null;
	}
}