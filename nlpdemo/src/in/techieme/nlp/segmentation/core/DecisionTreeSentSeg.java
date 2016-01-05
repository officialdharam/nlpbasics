package in.techieme.nlp.segmentation.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Detecting if . is a sentence terminator Build a binary classifier. Which
 * looks at the period and makes a binary decision if it is the end of the
 * sentence.
 * 
 * <pre>
 * 1. Hand Written Classifiers
 * 2. Regular Expressions
 * 3. Machine learning
 * </pre>
 * 
 * <pre>
 * Decision Tree:
 * 1. Lots of blank lines after the word?
 * 			YES) EOS
 * 		 	NO) Final Punctuation is ?, ! or :?
 * 				YES) EOS
 * 			 	NO) Final Punctuation is Period
 * 					NO )	Not EOS
 * 					YES)	Period is etc or other abbreviations
 * 						NO ) 	EOS
 * 						YES)	Not EOS
 * </pre>
 */
public class DecisionTreeSentSeg implements SentSegment {

	@Override
	public String[] sentSegment(String text) {
		String[] sentSeg = process(text);
		List<String> list = new ArrayList<String>();
		for (String s : sentSeg) {
			if (s.length() > 0 && s.charAt(0) != NEW_LINE)
				list.add(s.trim());
		}

		return list.toArray(new String[0]);
	}

	private String[] process(String text) {
		int st = 0;
		List<String> sentences = new ArrayList<String>();

		List<Integer> endPos = endOfLine(text);

		finalPunctuation(text, endPos);

		detectPeriod(text, endPos);

		Collections.sort(endPos);

		for (Integer i : endPos) {
			sentences.add(text.substring(st, ++i));
			st = i;
		}

		return sentences.toArray(new String[0]);
	}

	private void detectPeriod(String text, List<Integer> endPos) {
		char[] C = text.toCharArray();
		List<String> abbr = Arrays.asList(ABBR);

		for (int i = 0; i < C.length; i++) {
			/**
			 * if current character is a period. Find the word with period and
			 * check in the abbreviation list. Word here is separated by spaces.
			 * If the abbreviation is the end of the sentence and contains a
			 * period, handle it specially.
			 * 
			 */
			if (C[i] == PERIOD) {
				int j = i - 1;
				while (C[j] != ' ') {
					j--;
				}
				int strt = j;
				int stp = j;
				j = i + 1;
				while (j < C.length && C[j] != ' ') {
					j++;
				}

				if (j < C.length)
					stp = j;
				else
					stp = C.length;

				String W = text.substring(strt, stp);
				String trim = W.trim();
				boolean notPresent = false;
				// if the period is not a part of the abbreviation.
				if (!abbr.contains(trim)) {
					notPresent = true;
					i = stp;
				}

				// period is just after the abbreviation(containing more
				// periods) to mark it as the end of sentence
				if (notPresent && trim.endsWith(".")) {
					String ss = trim.substring(0, trim.length() - 1);
					if (abbr.contains(ss)) {
						notPresent = false;
						endPos.add(i - 1);
						i = stp;
					}
				}

				if (notPresent)
					endPos.add(i);
			}
		}
	}

	private void finalPunctuation(String text, List<Integer> endPos) {
		char[] C = text.toCharArray();
		for (int i = 0; i < C.length; i++) {
			for (char c : eosPunctuations) {
				if (C[i] == c) {
					endPos.add(i);
					break;
				}
			}
		}
	}

	private List<Integer> endOfLine(String text) {
		List<Integer> endPos = new ArrayList<Integer>();
		char[] C = text.toCharArray();
		for (int i = 0; i < C.length; i++) {
			if (C[i] == NEW_LINE) {
				endPos.add(i);
			}
		}
		endPos.add(C.length - 1);
		return endPos;
	}

}
