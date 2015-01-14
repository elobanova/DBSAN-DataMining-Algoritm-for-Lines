package DataStructures;

public class Object3D extends Object2D {
	public static final int SPACE_DIMENSION = 3;

	private final double z;

	public Object3D(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}

	/**
	 * Returns the z coordinate of the 3D object
	 * 
	 * @return z
	 */
	public double getZ() {
		return this.z;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Object3D))
			return false;
		Object3D otherPoint3D = (Object3D) other;
		return this.x == otherPoint3D.x && this.y == otherPoint3D.y
				&& this.z == otherPoint3D.z;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (int) this.x;
		hash = 53 * hash + (int) this.y;
		hash = 53 * hash + (int) this.z;
		return hash;
	}

	@Override
	public boolean isNonZeroObject() {
		return getX() != 0 || getY() != 0 || getZ() != 0;
	}
}
