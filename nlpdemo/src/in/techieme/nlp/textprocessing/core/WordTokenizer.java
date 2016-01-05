package in.techieme.nlp.textprocessing.core;

import in.techieme.nlp.core.NLPUtils;

public class WordTokenizer {

	public String[] tokenizeBySpace(String doc) {
		if (doc != null && doc.length() > 0)
			return doc.split(" ");
		else
			return null;
	}

	public String[] tokenizeByNormalizingAllSplChars(String doc) {
		if (doc != null && doc.length() > 0) {
			doc = doc.replaceAll(NLPUtils.REGEX_SPLCHARS, "");
			return doc.split(" ");
		} else
			return null;
	}
}
