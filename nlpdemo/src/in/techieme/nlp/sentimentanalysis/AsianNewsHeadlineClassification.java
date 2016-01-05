package in.techieme.nlp.sentimentanalysis;

import in.techieme.nlp.core.FileIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsianNewsHeadlineClassification {

	static String _fileNameIndia = "C:/WORK_HOME/NLP_COMM_TALK/data/spamdetection/india.txt";
	static String _fileNamePakistan = "C:/WORK_HOME/NLP_COMM_TALK/data/spamdetection/pakistan.txt";

	static NBModel model = new NBModel();

	public static void main(String[] args) {
		train();
		System.out.println(test("Lahore Hyderabad Chennai Delhi"));
	}

	static {
		train();
	}

	public static void train() {
		String India = FileIO.readFile(_fileNameIndia);
		String Pakistan = FileIO.readFile(_fileNamePakistan);

		String[] in = India.split("\n");
		String[] pk = Pakistan.split("\n");

		for (int i = 0; i < in.length; i++) {
			in[i] = in[i].replaceAll(NBModel.REGEX_SPLCHARS, "");
		}

		for (int i = 0; i < pk.length; i++) {
			pk[i] = pk[i].replaceAll(NBModel.REGEX_SPLCHARS, "");
		}

		List<String[]> indiaDocList = new ArrayList<String[]>();
		List<String[]> pakistanDocList = new ArrayList<String[]>();

		for (String s : in) {
			indiaDocList.add(s.split("\n"));
		}

		for (String s : pk) {
			pakistanDocList.add(s.split("\n"));
		}

		Map<String, List<String[]>> docs = new HashMap<String, List<String[]>>();
		docs.put("INDIA", indiaDocList);
		docs.put("PAKISTAN", pakistanDocList);
		model.train(docs);
	}

	public static String[] test(String headline) {
		return model.test(headline);
	}

}
