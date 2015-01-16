package DataStructures;

public class Pair<L, R> {

	private final L leftElement;
	private final R rightElement;

	public static <L, R> Pair<L, R> createPair(L leftElement, R rightElement) {
		return new Pair<L, R>(leftElement, rightElement);
	}

	public Pair(L leftElement, R rightElement) {
		this.leftElement = leftElement;
		this.rightElement = rightElement;
	}

	public L getLeftElement() {
		return this.leftElement;
	}

	public R getRightElement() {
		return rightElement;
	}

}