package in.techieme.nlp.lm.spellings;

import in.techieme.nlp.core.NLPDictionary;
import in.techieme.nlp.core.Word;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ErrorDetection {
	static Set<String> _WORDLIST = null;

	static {
		buildWordList();
	}

	public static void buildWordList() {
		NLPDictionary dictionary = NLPDictionary.getInstance();
		Set<String> WORDLIST = new LinkedHashSet<String>();
		Set<Word> words = dictionary.getWords();
		for (Word W : words) {
			WORDLIST.add(W.getWord());
		}
		_WORDLIST = WORDLIST;
	}

	/**
	 * detects non spelling errors from a text
	 * 
	 * @param content
	 * @return
	 */
	public Map<String, int[]> nonSpellingErrors(String content) {

		Map<String, int[]> NSE = new LinkedHashMap<String, int[]>();
		char[] C = content.trim().toCharArray();
		StringBuilder sb = new StringBuilder();
		int wordStart = 0;
		boolean wordOver = false;
		for (int i = 0; i < C.length; i++) {
			if (isChar(C[i]))
				sb.append(C[i]);
			else {
				wordOver = true;
			}

			if (i == C.length - 1)
				wordOver = true;

			if (wordOver && !wordInDictionary(sb.toString()) && sb.toString().trim().length() > 0)
				NSE.put(sb.toString(), new int[] { wordStart, i - 1 });

			if (wordOver) {
				wordStart = i + 1;
				sb = new StringBuilder();
				wordOver = false;
			}
		}
		return NSE;
	}

	public void addWordToDictionary(String word) {
		_WORDLIST.add(word);
	}

	private boolean isChar(char c) {
		if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
			return true;
		return false;
	}

	private boolean wordInDictionary(String word) {
		return _WORDLIST.contains(word.toLowerCase());
	}
}
