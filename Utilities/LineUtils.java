package Utilities;

import DataStructures.Line;
import DataStructures.Object3D;
import DataStructures.Pair;

public class LineUtils {
	public static boolean linesIntersect(Line line1, Line line2) {
		Object3D firstLineDirectingVector = getLineDirectingVector(line1);
		Object3D secondLineDirectingVector = getLineDirectingVector(line2);
		return VectorUtils.calculateCompositionalProduct(
				getConnectingVector(line1, line2), firstLineDirectingVector,
				secondLineDirectingVector) == 0
				&& MatrixUtils.computeRankFromTwo3DVectors(
						firstLineDirectingVector, secondLineDirectingVector) == 2;
	}

	public static boolean linesAreSkew(Line line1, Line line2) {
		return VectorUtils.calculateCompositionalProduct(
				getConnectingVector(line1, line2),
				getLineDirectingVector(line1), getLineDirectingVector(line2)) != 0;
	}

	public static boolean linesAreParallel(Line line1, Line line2) {
		Object3D firstLineDirectingVector = getLineDirectingVector(line1);
		Object3D secondLineDirectingVector = getLineDirectingVector(line2);
		Object3D connectingVector = getConnectingVector(line1, line2);
		return VectorUtils.calculateCompositionalProduct(connectingVector,
				firstLineDirectingVector, secondLineDirectingVector) == 0
				&& MatrixUtils.computeRankFromTwo3DVectors(
						firstLineDirectingVector, secondLineDirectingVector) == 1
				&& MatrixUtils.computeRankFromTwo3DVectors(connectingVector,
						firstLineDirectingVector) == 2;
	}

	public static boolean linesOverlap(Line line1, Line line2) {
		Object3D firstLineDirectingVector = getLineDirectingVector(line1);
		Object3D secondLineDirectingVector = getLineDirectingVector(line2);
		Object3D connectingVector = getConnectingVector(line1, line2);
		return VectorUtils.calculateCompositionalProduct(connectingVector,
				firstLineDirectingVector, secondLineDirectingVector) == 0
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

	public static Pair<Object3D, Object3D> findClosestPointsBetweenTheLines(Line line1,
			Line line2) {
		Object3D p0 = line1.getStartingPoint();
		Object3D p1 = line1.getEndingPoint();
		Object3D q0 = line2.getStartingPoint();
		Object3D q1 = line2.getEndingPoint();
		double a = VectorUtils.calculateDotProduct(
				VectorUtils.getDifference(p1, p0),
				VectorUtils.getDifference(p1, p0));
		double b = VectorUtils.calculateDotProduct(
				VectorUtils.getDifference(p1, p0),
				VectorUtils.getDifference(q1, q0));
		double c = VectorUtils.calculateDotProduct(
				VectorUtils.getDifference(q1, q0),
				VectorUtils.getDifference(q1, q0));
		double d = VectorUtils.calculateDotProduct(
				VectorUtils.getDifference(p1, p0),
				VectorUtils.getDifference(p0, q0));
		double e = VectorUtils.calculateDotProduct(
				VectorUtils.getDifference(q1, q0),
				VectorUtils.getDifference(p0, q0));

		/*
		 * Parameterize line1 by P(s) = (1-s)·P0 + s·P1 and line2 by Q(t) = (1-t)·Q0 + t·Q1.
		 * Then obtain the equality in case they intersect:
		 * (1-s)·P0 + s·P1 = (1-t)·Q0 + t·Q1. 
		 * Solving this by each component leads to the system:
		 * P0 + s·(P1-P0) = Q0 + t·(Q1-Q0).
		 */
		double det = a * c - b * b;
		double s = 0;
		double t = 0;
		if (det > 0) {
			double bte = b * e;
			double ctd = c * d;
			if (bte < ctd) {
				if (e <= 0) { //skew??
					s = (-d >= a ? 1 : (-d > 0 ? -d / a : 0));
					t = 0;
				} else if (e < c) {
					s = 0;
					t = e / c;
				} else {
					s = (b - d >= a ? 1 : (b - d > 0 ? (b - d) / a : 0));
					t = 1;
				}
			} else {
				s = bte - ctd;
				if (s >= det) {
					if (b + e <= 0) {
						s = (-d <= 0 ? 0 : (-d < s ? -d / a : 1));
						t = 0;
					} else if (b + e < c) {
						s = 1;
						t = (b + e) / c;
					} else {
						s = (b - d <= 0 ? 0 : (b - d < a ? (b - d) / a : 1));
						t = 1;
					}
				} else {
					double ate = a * e;
					double btd = b * d;
					if (ate <= btd) {
						s = (-d <= 0 ? 0 : (-d >= a ? 1 : -d / a));
						t = 0;
					} else {
						t = ate - btd;
						if (t >= det) {
							s = (b - d <= 0 ? 0
									: (b - d >= a ? 1 : (b - d) / a));
							t = 1;
						} else {
							s /= det;
							t /= det;
						}
					}
				}
			}
		} else {
			if (e <= 0) {
				s = (-d <= 0 ? 0 : (-d >= a ? 1 : -d / a));
				t = 0;
			} else if (e >= c) {
				s = (b - d <= 0 ? 0 : (b - d >= a ? 1 : (b - d) / a));
				t = 1;
			} else {
				s = 0;
				t = e / c;
			}
		}

		Object3D closesPointBelongingToFirstLine = new Object3D((1 - s)
				* p0.getX() + s * p1.getX(), (1 - s) * p0.getY() + s
				* p1.getY(), (1 - s) * p0.getZ() + s * p1.getZ());
		Object3D closesPointBelongingToSecondLine = new Object3D((1 - t)
				* q0.getX() + t * q1.getX(), (1 - t) * q0.getY() + t
				* q1.getY(), (1 - t) * q0.getZ() + t * q1.getZ());
		return new Pair<Object3D, Object3D>(closesPointBelongingToFirstLine,
				closesPointBelongingToSecondLine);
	}
}
