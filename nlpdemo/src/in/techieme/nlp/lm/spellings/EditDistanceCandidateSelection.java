package in.techieme.nlp.lm.spellings;

import in.techieme.nlp.core.NLPDictionary;
import in.techieme.nlp.core.Word;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditDistanceCandidateSelection {

	static Set<String> _WORDLIST = null;

	public static void main(String[] args) {
		buildWordList();
		EditDistanceCandidateSelection cl = new EditDistanceCandidateSelection();
		Set<String> match = cl._1EditDistance("nme");
		System.out.println(match);
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

	public Set<String> _1EditDistance(String word) {
		buildWordList();
		char[] C = word.trim().toLowerCase().toCharArray();
		Set<String> M = new HashSet<String>();
		Set<String> insert = insert(C);
		Set<String> delete = delete(C);
		Set<String> swap = swap(C);
		M.addAll(insert);
		M.addAll(delete);
		M.addAll(swap);
		return M;
	}

	private static Set<String> insert(char[] C) {
		Set<String> M = new HashSet<String>();
		StringBuilder sb = null;
		for (int i = 0; i <= C.length; i++) {
			sb = new StringBuilder(new String(C));
			sb.insert(i, ".");

			Pattern P = Pattern.compile(sb.toString());
			for (String w : _WORDLIST) {
				Matcher MT = P.matcher(w);
				boolean matches = MT.matches();
				if (matches) {
					M.add(w);
				}
			}
		}
		return M;
	}

	private static Set<String> delete(char[] C) {
		Set<String> M = new HashSet<String>();
		StringBuilder sb = null;
		for (int i = 0; i < C.length; i++) {
			sb = new StringBuilder(new String(C));
			sb.replace(i, i + 1, "");

			if (_WORDLIST.contains(sb.toString()))
				M.add(sb.toString());
		}
		return M;
	}

	private static Set<String> swap(char[] C) {
		Set<String> M = new HashSet<String>();
		StringBuilder sb = null;
		for (int i = 0; i < C.length - 1; i++) {
			char[] CP = Arrays.copyOf(C, C.length);
			char c = CP[i + 1];
			CP[i + 1] = CP[i];
			CP[i] = c;
			sb = new StringBuilder(new String(CP));
			if (_WORDLIST.contains(sb.toString()))
				M.add(sb.toString());
		}
		return M;
	}
}
