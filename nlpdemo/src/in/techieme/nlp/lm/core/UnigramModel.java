package in.techieme.nlp.lm.core;

import in.techieme.nlp.core.FileIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class UnigramModel implements Model {

	private String _trainingFileName = "C:/WORK_HOME/NLP_COMM_TALK/DEMO DAY/2-lm-training-corpus/1-sentence-training-corpus.txt";
	private Map<String, Integer> unigramCounts = new HashMap<String, Integer>();
	private Map<String, Float> unigramProbability = new HashMap<String, Float>();
	private int totalWordCount = 0;

	@Override
	public void train() {
		String fileContent = FileIO.readFile(_trainingFileName);
		String[] lines = fileContent.split("\n");
		for (String line : lines) {
			String[] words = line.split(" ");
			totalWordCount += words.length;
			for (String word : words) {
				Integer i = unigramCounts.get(word);
				if (i == null)
					i = 0;
				i++;
				unigramCounts.put(word, i);
			}
		}

		calculateUnigramProbability();
	}

	public Map<String, Integer> getUnigramCounts() {
		train();
		return this.unigramCounts;
	}

	private void calculateUnigramProbability() {
		for (Entry<String, Integer> e : unigramCounts.entrySet()) {
			String k = e.getKey();
			Integer v = e.getValue();
			Float f = (float) ((1.0) * v / totalWordCount);
			unigramProbability.put(k, f);
		}
	}

	@Override
	public String predictNextWord(String phrase) {
		List<String> words = new ArrayList<String>();
		for (Entry<String, Float> e : unigramProbability.entrySet()) {
			if (!Model.BEGIN_SENT.equals(e.getKey()) && !Model.END_SENT.equals(e.getKey()) && e.getValue() > 0.0005) {
				words.add(e.getKey());
			}
		}

		Random rand = new Random();
		int N = rand.nextInt(words.size());
		if (N > 0)
			return words.get(N - 1);
		else
			return words.get(0);
	}

	@Override
	public String predictNextWord(String phrase, List<String> wordList) {
		List<String> words = new ArrayList<String>();
		for (Entry<String, Float> e : unigramProbability.entrySet()) {
			if (!Model.BEGIN_SENT.equals(e.getKey()) && !Model.END_SENT.equals(e.getKey()) && e.getValue() > 0.0005) {
				words.add(e.getKey());
			}
		}

		for (String w : words) {
			if (wordList.contains(w))
				return w;
		}

		Random rand = new Random();
		int N = rand.nextInt(words.size());
		if (N > 0)
			return words.get(N - 1);
		else
			return words.get(0);
	}

}
