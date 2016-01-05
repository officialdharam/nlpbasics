package in.techieme.nlp.lm.core;

import java.util.List;

/**
 * <pre>
 * <ul>
 * <li>Probability of a Sentence</li>
 * <li>Probability of a upcoming word</li>
 * </pre>
 * 
 * A language model is a model that computes either of the two P(W) = P(w1, w2,
 * w3, w4, ... wn) or P(wn | w1, w2, w3, ... wn-1)
 * 
 * Chain Rule of Probability says that
 * 
 * <pre>
 * P(A,B,C,D) = P(A) P(B|A) P(C|A,B) P(D|A,B,C)
 * For e.g.: P("The tiger is a fierce animal") = P(The) * P(tiger | The) * P(is | The tiger) * P(a | The tiger is) *
 *  P(fierce | The tiger is a) * P(animal | The tiger is a fierce)
 *  
 *  Generalization:
 *  P(w1, w2, w3, ... wn) = product over i ->  P(wi | w1, w2, w3, ... wi-1)
 *  
 *  How to find these probability? 
 *  Count and Divide
 *  P(The tiger is a fierce animal) / P(The tiger is a fierce)
 *  
 *  Its impossible to calculate this, because it is impossible to count all the possible English 
 *  sentences using these words in these sequences.
 *  
 *  So we apply the Markov's assumption which suggests that
 *  <ol>
 *  <li>P(animal | The tiger is a fierce) = P(animal | fierce), or</li>
 *  <li>P(animal | The tiger is a fierce) = P(animal | a fierce) and so on </li>
 * </pre>
 * 
 * There are many cases of the Markov's model, the simplest being the unigram
 * model More intelligent is a bigram model. We can extend these to n-gram
 * models for effective sentence predictability.
 * 
 * It would still be an insufficient model of language because the languages
 * have long distance dependencies. For practical purposes, we can work using 4
 * or 5 grams
 * 
 * Also, if you train it on one corpus and test on another, it might not yield
 * anything worthy. This clearly shows that these models overfit.
 * 
 * For e.g.: If we train in Wall Street Journal and try to get some sentences
 * from Shakespeare it will yield nothing worthwhile.
 * 
 * Making models more robust
 * 
 * <pre>
 * 1) Laplace's Smoothing - Assumes that we saw each
 * word one more time. This steals the probability mass from the actual
 * occurrences to compensate for the imaginary occurrences.Hence, it is not good
 * for n-grams because these models will have a lots of unseen bigrams and hence
 * the mass stolen would be too much.
 * 
 * This is good when there are not many unseen bigrams.
 * E.g. Text classification
 * 
 * Back-off and Interpolation
 * Backing off to a lower order n-gram if the higher order is not seen enough times.
 * Interpolation is mixing up all the n grams (uni, bi and tri)
 * Linear Interpolation
 * 
 *  2) Good Turing Smoothing - discounting
 *  3) Kneser Ney Smoothing -  absolute discounting
 * </pre>
 * 
 * @author dprasad
 *
 */
public interface Model {
	String BEGIN_SENT = "<s>";
	String END_SENT = "</s>";

	public void train();

	public String predictNextWord(String phrase);
	
	public String predictNextWord(String phrase, List<String> wordList);
}
