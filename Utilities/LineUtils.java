package Utilities;

import DataStructures.Line;
import DataStructures.Object3D;
import DataStructures.Pair;

public class LineUtils {

	/**
	 * Determines the directing vector of a given 3D line
	 * 
	 * @param line1
	 *            the 3D line segment
	 * 
	 * @return the 3D vector which specifies the direction of this line
	 */
	private static Object3D getLineDirectingVector(Line line) {
		return new Object3D(-line.getDx(), -line.getDy(), -line.getDz());
	}

	/**
	 * Locates the closest point between two 3D line segments parameterizing
	 * line1 by P(s) = (1-s)·P0 + s·P1 and line2 by Q(t) = (1-t)·Q0 + t·Q1. Then
	 * obtains the equality in case they intersect: (1-s)·P0 + s·P1 = (1-t)·Q0 +
	 * t·Q1. Solving this by each component leads to the system: P0 + s·(P1-P0)
	 * = Q0 + t·(Q1-Q0). Relative positioning of the lines is determined by the
	 * cross product value of their directing vectors.
	 * 
	 * @param line1
	 *            the first line's segment
	 * @param line2
	 *            the second line's segment
	 * 
	 * @return the pair of the closest points
	 * @throws IllegalArgumentException
	 *             if the lines given are null
	 */
	public static Pair<Object3D, Object3D> findClosestPointsBetweenTheLines(
			Line line1, Line line2) throws IllegalArgumentException {
		if (line1 == null || line2 == null) {
			String errorMessage = "The line arguments are not valid.";
			throw new IllegalArgumentException(errorMessage);
		}

		Object3D p0 = line1.getStartingPoint();
		Object3D p1 = line1.getEndingPoint();
		Object3D q0 = line2.getStartingPoint();
		Object3D q1 = line2.getEndingPoint();

		Object3D line1DirectingVector = getLineDirectingVector(line1);
		Object3D line2DirectingVector = getLineDirectingVector(line2);

		double a = VectorUtils.calculateDotProduct(line1DirectingVector,
				line1DirectingVector);
		double b = VectorUtils.calculateDotProduct(line1DirectingVector,
				line2DirectingVector);
		double c = VectorUtils.calculateDotProduct(line2DirectingVector,
				line2DirectingVector);
		double d = VectorUtils.calculateDotProduct(line1DirectingVector,
				VectorUtils.getDifference(p0, q0));
		double e = VectorUtils.calculateDotProduct(line2DirectingVector,
				VectorUtils.getDifference(p0, q0));
		double squaredCrossProduct = a * c - b * b;
		Pair<Double, Double> segmentsParameters = squaredCrossProduct > 0 ? computeParametersForNonParallel(
				squaredCrossProduct, a, b, c, d, e)
				: computeParametersForParallel(a, b, c, d, e);

		double s = segmentsParameters.getLeftElement();
		double t = segmentsParameters.getRightElement();

		Object3D closesPointBelongingToFirstLine = new Object3D((1 - s)
				* p0.getX() + s * p1.getX(), (1 - s) * p0.getY() + s
				* p1.getY(), (1 - s) * p0.getZ() + s * p1.getZ());
		Object3D closesPointBelongingToSecondLine = new Object3D((1 - t)
				* q0.getX() + t * q1.getX(), (1 - t) * q0.getY() + t
				* q1.getY(), (1 - t) * q0.getZ() + t * q1.getZ());
		return Pair.createPair(closesPointBelongingToFirstLine,
				closesPointBelongingToSecondLine);
	}

	/**
	 * Determines the parameters s and t in case two 3D segments are parallel
	 * and, therefore, their cross product is zero.
	 * 
	 * @return the pair of parameters s and t
	 */
	private static Pair<Double, Double> computeParametersForParallel(double a,
			double b, double c, double d, double e) {
		if (e <= 0) {
			return Pair.createPair((-d <= 0 ? 0 : (-d >= a ? 1 : -d / a)), 0d);
		} else if (e >= c) {
			return Pair.createPair((b - d <= 0 ? 0 : (b - d >= a ? 1 : (b - d)
					/ a)), 1d);
		}

		return Pair.createPair(0d, e / c);
	}

	/**
	 * Determines the parameters s and t in case two 3D segments are not parallel
	 * and, therefore, their squared cross product is greater than zero.
	 * 
	 * @return the pair of parameters s and t
	 */
	private static Pair<Double, Double> computeParametersForNonParallel(
			double squaredCrossProduct, double a, double b, double c, double d,
			double e) {
		double s = 0;
		double t = 0;
		double be = b * e;
		double cd = c * d;
		if (be < cd) {
			if (e <= 0) {
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
			s = be - cd;
			if (s >= squaredCrossProduct) {
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
					if (t >= squaredCrossProduct) {
						s = (b - d <= 0 ? 0 : (b - d >= a ? 1 : (b - d) / a));
						t = 1;
					} else {
						s /= squaredCrossProduct;
						t /= squaredCrossProduct;
					}
				}
			}
		}
		return Pair.createPair(s, t);
	}
}
