package DataStructures;

public class Object2D {
	public static final int SPACE_DIMENSION = 2;

	protected final double x;
	protected final double y;

	public Object2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x coordinate of the 2D object
	 * 
	 * @return x
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Returns the y coordinate of the 2D Object
	 * 
	 * @return y
	 */
	public double getY() {
		return this.y;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Object2D))
			return false;
		Object2D otherPoint2D = (Object2D) other;
		return this.x == otherPoint2D.x && this.y == otherPoint2D.y;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (int) this.x;
		hash = 53 * hash + (int) this.y;
		return hash;
	}

	/**
	 * Determines if the object has non-zero components
	 * 
	 * @return true if any of the object's components is not 0
	 */
	public boolean isNonZeroObject() {
		return getX() != 0 || getY() != 0;
	}
}
