package in.techieme.nlp.sentimentanalysis;

import in.techieme.nlp.core.FileIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieReviewClassification {

	static String _fileNamePositive = "C:/WORK_HOME/STUDIES/NLP_COMM_TALK/data/spamdetection/positivereviews.txt";
	static String _fileNameNegative = "C:/WORK_HOME/STUDIES/NLP_COMM_TALK/data/spamdetection/negativereviews.txt";

	static NBModel model = new NBModel();

	public static void main(String[] args) {
		train();
		System.out.println(test("Bhaktas have nailed the Chamchas LOL Worst move flop another 5 movies of these Khans. Teach them a lesson to behave and respect the nation. Bajirao Mastani, and it has continued to rake in more moolah because Shah Rukh Khan reportedly knew the trick. The advance booking of Dilwale started from Monday of the releasing week, far ahead of Bajirao Mastani. However, the first Monday (after its Friday release) collections are always the litmus test for any film's acceptability, and this is where it looks like Bajirao Mastani has an edge over Dilwale. According to trade magazine Box office India (image attached below), on Monday Bajirao Mastani had the net collection of Rs 10.25 crore, whereas Dilwale ended up raking in Rs 10.09 crore."));
	}

	static {
		train();
	}

	public static void train() {
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

	public static String[] test(String testDoc) {
		testDoc = testDoc.replaceAll(NBModel.REGEX_SPLCHARS, "");
		return model.test(testDoc);
	}
}
