package in.techieme.nlp.lm.spellings;

import java.util.Set;

public class ErrorCorrection {

	EditDistanceCandidateSelection errorSuggestions = new EditDistanceCandidateSelection();
	public Set<String> fetchSuggestions(String word) {
		Set<String> suggestions = errorSuggestions._1EditDistance(word);
		return suggestions;
	}
}
