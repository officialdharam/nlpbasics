package in.techieme.nlp.sentimentanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class represents the Multinomial Naive Bayes Model
 * 
 * @author dprasad
 *
 */
public class NBModel {

	// use to replace all special characters by some other sequence of
	// characters
	public static final String REGEX_SPLCHARS = new String("[~`!@#$%^&*()\\-=_+\\[\\]{};':\"<>,./?|]");

	// list to store the classes derived from the training sample.
	private List<String> classes = new ArrayList<String>();

	// string to return when there is no class identified.
	private final String UNCLASSIFIED = "UNCLASSIFIED";

	// class - doc map stores a pair (class - list of documents, which are
	// arrays of strings, can be normalized and filtered or special characters)
	private Map<String, List<String[]>> docs = new HashMap<String, List<String[]>>();

	// class - probability map, stores the probability of each classes in the
	// training sample. Also called the prior probability
	private Map<String, Float> priorMap = new HashMap<String, Float>();

	// class, token - frequency map, stores the frequency of a token for a
	// class.
	private Map<Pair<String, String>, Integer> tokenFrequencyMap = new HashMap<Pair<String, String>, Integer>();

	// class, token - probability map, stores the probability of the token given
	// a class. Also called the likelihood or language model
	private Map<Pair<String, String>, Float> languageModel = new HashMap<Pair<String, String>, Float>();

	private Map<String, Float> smoothingPerClass = new HashMap<String, Float>();

	// total number of training documents available.
	private int totalDocCount = 0;

	// set of all words in the vocabulary
	private Set<String> vocabulary = new HashSet<String>();

	/*
	 * prepares the list of classes from the training sample.
	 */
	private void initClasses(String... clz) {
		for (String s : clz)
			classes.add(s);
	}

	/**
	 * method invoked for training the model using the input map, which maps a
	 * list of documents to a class.
	 * 
	 * @param fileContents
	 */
	public void train(final Map<String, List<String[]>> fileContents) {

		// find the list of classes in the training sample
		Set<String> classes = fileContents.keySet();
		initClasses(classes.toArray(new String[0]));

		// find the total doc count and prepare a copy of the input map
		for (Entry<String, List<String[]>> e : fileContents.entrySet()) {
			totalDocCount += e.getValue().size();
			docs.put(e.getKey(), e.getValue());
		}

		// calculate the prior probability also denoted as P(Nc).
		calculateClassProbability();

		// calculate the token frequencies for each of the classes also denoted
		// as count(w|c)
		calculateTokenFrequency();

		// calculate the numerator of the token probability for each class, also
		// denoted as
		calculateTokenProbability();
	}

	/*
	 * calculated as (documents in a particular class c) / (total documents) =
	 * count(Nc)/count(N)
	 */
	private void calculateClassProbability() {
		for (Entry<String, List<String[]>> e : docs.entrySet()) {
			priorMap.put(e.getKey(), (float) (1.0 * e.getValue().size()) / totalDocCount);
		}
	}

	/*
	 * this method calculates count(w|c), word given class
	 */
	private void calculateTokenFrequency() {
		for (Entry<String, List<String[]>> e : docs.entrySet()) {
			String clazz = e.getKey();
			List<String[]> docsInClass = e.getValue();

			for (String[] doc : docsInClass) {
				for (String s : doc) {
					String[] split = s.split(" ");

					for (String w : split) {
						// ad unique words to the vocabulary set
						vocabulary.add(w);
						Pair<String, String> p = new Pair<String, String>(clazz, w);
						// for each token + class pair, store the total number
						// of occurrences of the token.
						Integer i = tokenFrequencyMap.get(p);
						if (i == null)
							i = 0;
						i++;

						tokenFrequencyMap.put(p, i);
					}
				}
			}
		}
	}

	/*
	 * This method calculates the P(w|c) = (count(w|c) + 1)/ P(c) + |V|).
	 * Basically it is the conditional probability of a word given a class.
	 */
	private void calculateTokenProbability() {
		Map<String, Integer> totalWordCountInClasses = new HashMap<String, Integer>();

		// this section just calculates the total word count in each of the
		// classes.
		for (Entry<Pair<String, String>, Integer> e : tokenFrequencyMap.entrySet()) {
			Pair<String, String> pair = e.getKey();
			String clazz = pair.o1;
			Integer i = totalWordCountInClasses.get(clazz);
			if (i == null)
				i = 0;
			i += e.getValue();
			totalWordCountInClasses.put(clazz, i);
		}

		for (String cls : classes) {
			Integer clSize = totalWordCountInClasses.get(cls);
			int vSize = vocabulary.size();
			smoothingPerClass.put(cls, (1.0f) / (clSize + vSize));
		}

		/*
		 * this is to make the add one smoothing work generically, just add a
		 * count = zero for all the tokens in the vocabulary but not in a given
		 * class.
		 * 
		 * This will help in generically processing the probabilities next,
		 * without worrying about missing words in the respective map entries of
		 * token frequency map.
		 */
		for (String v : vocabulary) {
			for (String c : classes) {
				Pair<String, String> p = new Pair<String, String>(c, v);
				Integer i = tokenFrequencyMap.get(p);
				if (i == null)
					i = 0;
				tokenFrequencyMap.put(p, i);
			}
		}

		/*
		 * Laplace's or Add-One Smoothing, basically calculates the P(w|c) = (
		 * count(w|c) + 1)/ P(c) + |V|).
		 * 
		 * The LHS is the probability of the word given the class. The RHS is
		 * count of the word given a class divided by count of all the words in
		 * the class.
		 * 
		 * Also, we include the add one smoothing, by adding 1 in the numerator
		 * and the vocabulary size in the denominator
		 */
		for (Entry<Pair<String, String>, Integer> e : tokenFrequencyMap.entrySet()) {
			Pair<String, String> pair = e.getKey();
			String clazz = pair.o1;
			int count = e.getValue();
			float prob = (float) (count + 1) / (totalWordCountInClasses.get(clazz) + vocabulary.size());
			Pair<String, String> p = new Pair<String, String>(clazz, pair.o2);
			languageModel.put(p, prob);
			System.out.println(p+" - "+ prob);
		}

	}

	/**
	 * This method classifies the given test document into one of the classes as
	 * per the training.
	 * 
	 * @param document
	 * @return
	 */
	public String[] test(String document) {

		List<String> words = new ArrayList<String>();
		// normalize the document to derive each word.
		for (String w : document.split(" ")) {
			words.add(w);
		}

		Map<String, Double> probDoc = new HashMap<String, Double>();
		/*
		 * find the probability of the words in the document for each class and
		 * calculate the numerator of P(c|d) for each class.
		 * 
		 * P(c|d) = P(c) * P(w1|c)*P(w2|c)*P(w3|c)*...*P(wn|c)
		 */
		for (String c : classes) {
			for (String w : words) {
				Pair<String, String> p = new Pair<String, String>(c, w);
				Float wp = languageModel.get(p);
				Double f = probDoc.get(c);
				if (f == null) {
					f = Math.log(priorMap.get(c));
				}
				if (wp == null) {
					wp = smoothingPerClass.get(c);
				}
				f += Math.log(wp);
				probDoc.put(c, f);
			}
		}

		// find the max and return
		double max = Integer.MIN_VALUE;
		String docClass = UNCLASSIFIED;
		for (Entry<String, Double> pd : probDoc.entrySet()) {
			if (pd.getValue() > max) {
				max = pd.getValue();
				docClass = pd.getKey();
			}
		}

		return new String[] { docClass, String.valueOf(max) };
	}
}
