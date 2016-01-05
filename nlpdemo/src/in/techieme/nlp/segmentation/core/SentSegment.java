package in.techieme.nlp.segmentation.core;

public interface SentSegment {
	char[] eosChars = new char[] { '.', '!', '?', '\n' };
	char[] eosPunctuations = new char[] { '!', '?' };
	static char NEW_LINE = '\n';
	char PERIOD = '.';
	String[] ABBR = new String[] { "etc.", "M.B.B.S", "Dr.", "Gen.", "Hon.", "Mr.", "Mrs.", "Ms.", "Prof.", "Sr.", "Jr.", "St.", "Dept.", "Est.",
			"Fig.", "Inc.", "Mt.", "No.", "Oz.", "Sq.", "St.", "Adj.", "Adv.", "Obj.", "BA.", "MA.", "M.B.A" };

	public String[] sentSegment(String text);
}
