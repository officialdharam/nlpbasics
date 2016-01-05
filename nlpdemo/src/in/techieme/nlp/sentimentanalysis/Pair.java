package in.techieme.nlp.sentimentanalysis;

public class Pair<S, T> {

	S o1;
	T o2;

	public Pair(S _1, T _2) {
		this.o1 = _1;
		this.o2 = _2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((o1 == null) ? 0 : o1.hashCode());
		result = prime * result + ((o2 == null) ? 0 : o2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Pair<S, T> other = (Pair<S, T>) obj;
		if (o1 == null) {
			if (other.o1 != null)
				return false;
		} else if (!o1.equals(other.o1))
			return false;
		if (o2 == null) {
			if (other.o2 != null)
				return false;
		} else if (!o2.equals(other.o2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\nPair [o1=" + o1 + ", o2=" + o2 + "]";
	}

}
