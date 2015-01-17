package DataStructures;

public class Pair<L, R> {

	private final L leftElement;
	private final R rightElement;

	/**
	 * Constructs the pair object from two elements of types L and R
	 * correspondingly
	 * 
	 * @param leftElement
	 *            the first element of the pair
	 * @param rightElement
	 *            the second element of the pair
	 */
	public static <L, R> Pair<L, R> createPair(L leftElement, R rightElement) {
		return new Pair<L, R>(leftElement, rightElement);
	}

	private Pair(L leftElement, R rightElement) {
		this.leftElement = leftElement;
		this.rightElement = rightElement;
	}

	/**
	 * Gets the first (left) element of the pair
	 * 
	 * @return the left element of the pair
	 */
	public L getLeftElement() {
		return this.leftElement;
	}

	/**
	 * Gets the second (right) element of the pair
	 * 
	 * @return the right element of the pair
	 */
	public R getRightElement() {
		return rightElement;
	}

}