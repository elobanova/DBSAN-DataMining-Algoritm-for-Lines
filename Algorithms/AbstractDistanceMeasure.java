package Algorithms;

import DataStructures.Line;

public abstract class AbstractDistanceMeasure {

	/**
	 * Overriding methods of subclasses compute the various distances between
	 * two closest points of the given lines
	 * 
	 * @param line1
	 *            the first 3-D line of the measured
	 * @param line2
	 *            the second 3-D line of the measured
	 * @return the distance between two closest points of the given lines
	 */
	protected abstract double dist(Line line1, Line line2);
}
