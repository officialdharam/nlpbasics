package in.techieme.nlp.core;

public class Word {

	String word;
	String pos;
	int rank;
	int count;
	float dispersion;

	public Word(String w, int c, int r, String p, float d) {
		this.word = w;
		this.count = c;
		this.rank = r;
		this.pos = p;
		this.dispersion = d;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getDispersion() {
		return dispersion;
	}

	public void setDispersion(float dispersion) {
		this.dispersion = dispersion;
	}

}
