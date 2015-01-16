package Utilities;

import DataStructures.Object2D;
import DataStructures.Object3D;

public class VectorUtils {
	public static double calculateCompositionalProduct(
			Object3D connectingVector, Object3D firstLineDirectingVector,
			Object3D secondLineDirectingVector) {
		return MatrixUtils.computeDeterminant3D(connectingVector,
				firstLineDirectingVector, secondLineDirectingVector);
	}

	public static Object2D getDifference(Object2D o1, Object2D o2) {
		return new Object2D(o1.getX() - o2.getX(), o1.getY() - o2.getY());
	}

	public static double calculateDotProduct(Object2D o1, Object2D o2) {
		return o1.getX() * o2.getX() + o1.getY() * o2.getY();
	}

	public static Object3D getDifference(Object3D o1, Object3D o2) {
		return new Object3D(o1.getX() - o2.getX(), o1.getY() - o2.getY(),
				o1.getZ() - o2.getZ());
	}

	public static double calculateDotProduct(Object3D o1, Object3D o2) {
		return o1.getX() * o2.getX() + o1.getY() * o2.getY() + o1.getZ()
				* o2.getZ();
	}
}
