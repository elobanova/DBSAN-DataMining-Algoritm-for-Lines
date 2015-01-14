package Algorithms;

import Utilities.LineUtils;
import DataStructures.Line;

public class EucledianDistanceMeasure extends AbstractDistanceMeasure {

	@Override
	protected double dist(Line line1, Line line2) {
		if (LineUtils.linesAreParallel(line1, line2)) {
			return 1;
		}
		
		if (LineUtils.linesIntersect(line1, line2)) {
			return 0;
		}
		
		if (LineUtils.linesOverlap(line1, line2)) {
			return 0;
		}
		
		if (LineUtils.linesAreSkew(line1, line2)) {
			return 2;
		}
		return Math.sqrt(100);
	}
}
