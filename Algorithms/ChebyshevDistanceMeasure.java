package Algorithms;

import DataStructures.Line;
import DataStructures.Object3D;
import DataStructures.Pair;
import Utilities.LineUtils;

public class ChebyshevDistanceMeasure extends AbstractDistanceMeasure {

	/**
	 * Computes the infinity norm distance between two closest points of the
	 * given lines
	 * 
	 * @param line1
	 *            the first 3-D line of the measured
	 * @param line2
	 *            the second 3-D line of the measured
	 * @return the distance between two closest points of the given lines
	 */
	@Override
	protected double dist(Line line1, Line line2) {
		Pair<Object3D, Object3D> closestPoints = LineUtils.findClosestPointsBetweenTheLines(
				line1, line2);
		Object3D point1 = closestPoints.getLeftElement();
		Object3D point2 = closestPoints.getRightElement();
		return Math.max(Math.max(Math.abs(point1.getX() - point2.getX()),
				Math.abs(point1.getY() - point2.getY())),
				Math.abs(point1.getZ() - point2.getZ()));
	}
}
