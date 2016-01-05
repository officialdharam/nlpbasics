package in.techieme.nlp.sentimentanalysis;

import in.techieme.nlp.core.FileIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericReviewClassification {
	
	static String _fileNamePositive = "C:/WORK_HOME/NLP_COMM_TALK/data/spamdetection/positivereviews.txt";
	static String _fileNameNegative = "C:/WORK_HOME/NLP_COMM_TALK/data/spamdetection/negativereviews.txt";
	
	public void train(NBModel model) {
		
		Map<String, String> classTrainingData = new HashMap<String, String>();
		String positive = FileIO.readFile(_fileNamePositive);
		String negative = FileIO.readFile(_fileNameNegative);

		String[] pos = positive.split("\n");
		String[] neg = negative.split("\n");

		for (int i = 0; i < pos.length; i++) {
			pos[i] = pos[i].replaceAll(NBModel.REGEX_SPLCHARS, "");
		}

		for (int i = 0; i < neg.length; i++) {
			neg[i] = neg[i].replaceAll(NBModel.REGEX_SPLCHARS, "");
		}

		List<String[]> positiveDocList = new ArrayList<String[]>();
		List<String[]> negativeDocList = new ArrayList<String[]>();

		for (String s : pos) {
			positiveDocList.add(s.split("\n"));
		}

		for (String s : neg) {
			negativeDocList.add(s.split("\n"));
		}

		Map<String, List<String[]>> docs = new HashMap<String, List<String[]>>();
		docs.put("POSITIVE", positiveDocList);
		docs.put("NEGATIVE", negativeDocList);
		model.train(docs);
	}
}
