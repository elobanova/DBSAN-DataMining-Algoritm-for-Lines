package Utilities;

import DataStructures.Object2D;
import DataStructures.Object3D;

public class MatrixUtils {
	public static double computeDeterminant2D(Object2D v1, Object2D v2) {
		return v1.getX() * v2.getY() - v1.getY() * v2.getX();
	}

	public static double computeDeterminant3D(Object3D v1, Object3D v2,
			Object3D v3) {

		return v1.getX() * (v2.getY() * v3.getZ() - v2.getZ() * v3.getY())
				- v1.getY() * (v2.getX() * v3.getZ() - v2.getZ() * v3.getX())
				+ v1.getZ() * (v2.getX() * v3.getY() - v2.getY() * v3.getX());
	}

	public static int computeRankFromTwo3DVectors(Object3D v1, Object3D v2) {
		int resultingRank = 0;
		if (v1.isNonZeroObject() || v2.isNonZeroObject()) {
			resultingRank = 1;
		}

		if (resultingRank == 0) {
			return 0;
		} else {
			double firstMinor = v1.getX() * v2.getY() - v1.getY() * v2.getX();
			double secondMinor = v1.getX() * v2.getZ() - v1.getZ() * v2.getX();
			double thirdMinor = v1.getY() * v2.getZ() - v1.getZ() * v2.getY();

			if (firstMinor != 0 || secondMinor != 0 || thirdMinor != 0) {
				resultingRank++;
			}
			return resultingRank;
		}
	}
}
