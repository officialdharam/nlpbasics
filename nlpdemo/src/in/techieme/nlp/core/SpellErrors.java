package in.techieme.nlp.core;

public class SpellErrors {

	private String error;
	private String[] corrections;

	public SpellErrors(String error, String[] corrections) {
		super();
		this.error = error;
		this.corrections = corrections;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String[] getCorrections() {
		return corrections;
	}

	public void setCorrections(String[] corrections) {
		this.corrections = corrections;
	}

}
