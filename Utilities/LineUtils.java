package Utilities;

import DataStructures.Line;
import DataStructures.Object3D;

public class LineUtils {
	public static boolean linesIntersect(Line line1, Line line2) {
		Object3D firstLineDirectingVector = getLineDirectingVector(line1);
		Object3D secondLineDirectingVector = getLineDirectingVector(line2);
		return calculateCompositionalProduct(line1, line2) == 0
				&& MatrixUtils.computeRankFromTwo3DVectors(
						firstLineDirectingVector, secondLineDirectingVector) == 2;
	}

	public static boolean linesAreSkew(Line line1, Line line2) {
		return calculateCompositionalProduct(line1, line2) != 0;
	}

	public static boolean linesAreParallel(Line line1, Line line2) {
		Object3D firstLineDirectingVector = getLineDirectingVector(line1);
		Object3D secondLineDirectingVector = getLineDirectingVector(line2);
		Object3D connectingVector = getConnectingVector(line1, line2);
		return calculateCompositionalProduct(line1, line2) == 0
				&& MatrixUtils.computeRankFromTwo3DVectors(
						firstLineDirectingVector, secondLineDirectingVector) == 1
				&& MatrixUtils.computeRankFromTwo3DVectors(connectingVector,
						firstLineDirectingVector) == 2;
	}

	public static boolean linesOverlap(Line line1, Line line2) {
		Object3D firstLineDirectingVector = getLineDirectingVector(line1);
		Object3D secondLineDirectingVector = getLineDirectingVector(line2);
		Object3D connectingVector = getConnectingVector(line1, line2);
		return calculateCompositionalProduct(line1, line2) == 0
				&& MatrixUtils.computeRankFromTwo3DVectors(
						firstLineDirectingVector, secondLineDirectingVector) == 1
				&& MatrixUtils.computeRankFromTwo3DVectors(connectingVector,
						firstLineDirectingVector) == 1
				&& MatrixUtils.computeRankFromTwo3DVectors(connectingVector,
						secondLineDirectingVector) == 1;
	}

	private static Object3D getLineDirectingVector(Line line) {
		return new Object3D(-line.getDx(), -line.getDy(), -line.getDz());
	}

	private static Object3D getConnectingVector(Line line1, Line line2) {
		Object3D line2StartingPoint = line2.getStartingPoint();
		Object3D line1StartingPoint = line1.getStartingPoint();
		Object3D connectingVector = new Object3D(line2StartingPoint.getX()
				- line1StartingPoint.getX(), line2StartingPoint.getY()
				- line1StartingPoint.getY(), line2StartingPoint.getZ()
				- line1StartingPoint.getZ());
		return connectingVector;
	}

	private static double calculateCompositionalProduct(Line line1, Line line2) {
		Object3D connectingVector = getConnectingVector(line1, line2);
		Object3D firstLineDirectingVector = getLineDirectingVector(line1);
		Object3D secondLineDirectingVector = getLineDirectingVector(line2);
		return MatrixUtils.computeDeterminant3D(connectingVector,
				firstLineDirectingVector, secondLineDirectingVector);
	}
}
