package in.techieme.nlp.segmentation.core;

import java.util.ArrayList;
import java.util.List;

public class EOSSentSeg implements SentSegment {

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
		List<String> sentences = new ArrayList<String>();
		List<Integer> endPos = endPos(text);
		int st = 0;
		for (Integer i : endPos) {
			sentences.add(text.substring(st, ++i));
			st = i;
		}
		return sentences.toArray(new String[0]);
	}

	private List<Integer> endPos(String text) {
		List<Integer> endPos = new ArrayList<Integer>();
		char[] C = text.toCharArray();
		for (int i = 0; i < C.length; i++) {
			for (char e : eosChars) {
				if (C[i] == e) {
					endPos.add(i);
					break;
				}
			}
		}
		endPos.add(C.length - 1);
		return endPos;
	}
}
