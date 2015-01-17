package Utilities;

import DataStructures.Object3D;

public class VectorUtils {

	/**
	 * Calculates the difference 3D vector of the given vector objects by
	 * creating a new 3D resulting vector which coordinates simply represent the
	 * difference between corresponding components (axis)
	 * 
	 * @param o1
	 *            the first vector object (minuend)
	 * @param o2
	 *            the second vector object (subtrahend)
	 * 
	 * @return the difference 3D vector of the given vector objects
	 * @throws IllegalArgumentException
	 *             if the objects given are null
	 */
	public static Object3D getDifference(Object3D o1, Object3D o2)
			throws IllegalArgumentException {
		if (o1 == null || o2 == null) {
			String errorMessage = "The difference operators are not valid.";
			throw new IllegalArgumentException(errorMessage);
		}

		return new Object3D(o1.getX() - o2.getX(), o1.getY() - o2.getY(),
				o1.getZ() - o2.getZ());
	}

	/**
	 * Calculates the dot product of two 3D vectors
	 * 
	 * @param o1
	 *            the first 3D vector object
	 * @param o2
	 *            the second 3D vector object
	 * 
	 * @return the dot product
	 * @throws IllegalArgumentException
	 *             if the objects given are null
	 */
	public static double calculateDotProduct(Object3D o1, Object3D o2)
			throws IllegalArgumentException {
		if (o1 == null || o2 == null) {
			String errorMessage = "The dot product operators are not valid.";
			throw new IllegalArgumentException(errorMessage);
		}

		return o1.getX() * o2.getX() + o1.getY() * o2.getY() + o1.getZ()
				* o2.getZ();
	}
}
