package in.techieme.nlp.core;

import java.util.HashSet;
import java.util.Set;

public class NLPDictionary {

	static NLPDictionary _instance;
	static String _fNameDictionary = "C:/WORK_HOME/NLP_COMM_TALK/data/dictionary.csv";
	Set<Word> words = null;

	private NLPDictionary() {

	}

	public Set<Word> getWords(){
		return _instance.words;
	}
	
	
	public static NLPDictionary getInstance() {
		if (_instance == null) {
			synchronized (NLPDictionary.class) {
				if (_instance == null)
					_instance = new NLPDictionary();
				_instance.words = makeDictionary();
			}
		}
		return _instance;
	}

	private static Set<Word> makeDictionary() {
		int c = 0;
		Word w = null;
		String word = null;
		String pos = null;
		int rank = 0;
		int count = 0;
		float dispersion = 0.0f;

		String readFile = FileIO.readFile(_fNameDictionary);
		readFile = readFile.replaceAll("\n", ",");
		String[] split = readFile.split(",");
		Set<Word> words = new HashSet<Word>();

		for (String s : split) {
			if (c == 0) {
				rank = Integer.parseInt(s.trim());
			} else if (c == 1) {
				word = s.trim();
			} else if (c == 2) {
				pos = s.trim();
			} else if (c == 3) {
				count = Integer.parseInt(s.trim());
			} else if (c == 4) {
				dispersion = Float.parseFloat(s.trim());
			}
			c++;
			if (c % 5 == 0) {
				c = 0;
				w = new Word(word, count, rank, pos, dispersion);
				words.add(w);
			}
		}

		return words;
	}
}
