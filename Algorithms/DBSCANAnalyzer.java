package Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataStructures.Line;
import DataStructures.Object3D;

public class DBSCANAnalyzer {

	private double eps;
	private final double minPts;
	private final AbstractDistanceMeasure distanceMeasure;

	public DBSCANAnalyzer(AbstractDistanceMeasure distanceMeasure) {
		this.distanceMeasure = distanceMeasure;
		this.minPts = countMinPts(Object3D.SPACE_DIMENSION);
	}

	public DBSCANAnalyzer(AbstractDistanceMeasure distanceMeasure, double eps) {
		this(distanceMeasure);
		this.eps = eps;
	}

	/**
	 * Sets the epsilon parameter to algorithm
	 * 
	 * @param eps
	 *            the epsilon parameter
	 */
	public void setEps(double eps) {
		if (eps != this.eps) {
			this.eps = eps;
		}
	}

	/**
	 * Clusters the given lines into classes by applying DBSCAN algorithm
	 * 
	 * @param lines
	 *            the lines to cluster
	 * @throws IllegalArgumentException
	 *             if the list of lines provided is null
	 */
	public void performClustering(List<Line> lines)
			throws IllegalArgumentException {
		if (lines == null) {
			String errorMessage = "The lines argument is not valid.";
			throw new IllegalArgumentException(errorMessage);
		}

		System.out.println("Performing clustering on lines...");
		Map<Line, Integer> visited = new HashMap<Line, Integer>();
		int clusterIdentifier = Line.NO_CLUSTER_ASSIGNED;
		for (Line line : lines) {

			// object is not yet classified
			if (visited.get(line) == null) {
				List<Line> neighbours = getNeighbours(line, lines);

				// line is a core-object
				if (neighbours.size() >= this.minPts) {

					// collect all objects density-reachable from point and
					// assign them to a new cluster
					clusterIdentifier++;
					collectDensityReachableObjects(line, clusterIdentifier,
							neighbours, lines, visited);
				} else {
					visited.put(line, Line.NO_CLUSTER_ASSIGNED);
				}
			}
		}

	}

	/**
	 * Finds and collects all objects density-reachable from the given line
	 * 
	 * @param line
	 *            the core line
	 * @param clusterIdentifier
	 *            the current cluster identifier
	 * @param neighbours
	 *            the list of a core line's neighbours
	 * @param lines
	 *            the lines to cluster
	 * @param visited
	 *            the set of already clustered lines
	 */
	private void collectDensityReachableObjects(Line line,
			int clusterIdentifier, List<Line> neighbours, List<Line> lines,
			Map<Line, Integer> visited) {
		System.out.println("Collecting density-reachable objects...");
		line.setCluster(clusterIdentifier);
		List<Line> neighboursCopy = new ArrayList<Line>(neighbours);
		for (int i = 0; i < neighboursCopy.size(); i++) {
			Line current = neighboursCopy.get(i);
			Integer currentClusterIdentifier = visited.get(current);

			// only check those lines which haven't been visited yet
			if (currentClusterIdentifier == null) {
				final List<Line> currentNeighbors = getNeighbours(current,
						lines);
				if (currentNeighbors.size() >= minPts) {
					// found the density-reachable, connect it with the set
					neighboursCopy = union(neighboursCopy, currentNeighbors);
				}
			}

			if (currentClusterIdentifier == null
					|| currentClusterIdentifier == Line.NO_CLUSTER_ASSIGNED) {
				visited.put(current, clusterIdentifier);
				current.setCluster(clusterIdentifier);
			}
		}
	}

	/**
	 * Searches for the neighbours of a given line in the area with radius equal
	 * to eps
	 * 
	 * @param line
	 *            the center line to locate the nearest neighbours to
	 * @param lines
	 *            the set of data lines
	 * @return the list of all the neighbours in the eps-radius sphere
	 */
	private List<Line> getNeighbours(Line line, List<Line> lines) {
		List<Line> neighbours = new ArrayList<Line>();
		for (Line neighbour : lines) {
			if (!lines.equals(neighbour)
					&& distanceMeasure.dist(neighbour, line) <= this.eps) {
				neighbours.add(neighbour);
			}
		}
		return neighbours;
	}

	/**
	 * Performs the union of two set of lines
	 * 
	 * @param firstSet
	 *            the set to be filled with the lines
	 * @param secondSet
	 *            the set to get the lines from
	 * @return resulting set consisting of the lines from both sets
	 */
	private List<Line> union(List<Line> firstSet, List<Line> secondSet) {
		for (Line line : secondSet) {
			if (!firstSet.contains(line)) {
				firstSet.add(line);
			}
		}
		return firstSet;
	}

	/**
	 * Computes the MinPts value based on dimension of the data space
	 * 
	 * @param dimension
	 *            dimension of the data space
	 * @return MinPts
	 * @throws IllegalArgumentException
	 *             if the dimension is negative
	 */
	private int countMinPts(int dimension) throws IllegalArgumentException {
		if (dimension > 0) {
			return 2 * dimension - 1;
		}

		String errorMessage = "The dimension argument is negative.";
		throw new IllegalArgumentException(errorMessage);
	}

	/**
	 * Performs the k-th distance calculation from the given line
	 * 
	 * @param lines
	 *            a list of lines to be clustered
	 * @param o
	 *            the center line to locate the distance from
	 * @param k
	 *            the order of the nearest neighbour
	 * @return the k-th distance value
	 * @throws IllegalArgumentException
	 *             if a set of data lines is null or the order of the nearest
	 *             neighbour exceeds the cardinality of a data set
	 */
	private double calculateKDistance(List<Line> lines, Line o, int k)
			throws IllegalArgumentException {
		if (lines == null) {
			String errorMessage = "The lines argument is null.";
			throw new IllegalArgumentException(errorMessage);
		}

		if (k > lines.size()) {
			String errorMessage = "The order of the nearest neighbour can not exceed the cardinality of a set";
			throw new IllegalArgumentException(errorMessage);
		}

		double[] distances = new double[lines.size()];
		for (int i = 0; i < lines.size(); i++) {
			if (!lines.get(i).equals(o)) {
				distances[i] = distanceMeasure.dist(lines.get(i), o);
			}
		}
		Arrays.sort(distances);
		return distances[k];
	}

	/**
	 * Calculates the MinPts-distance values for the user to select the
	 * "border object"
	 * 
	 * @param lines
	 *            a list of lines to be clustered
	 * @return MinPts-distance plot values
	 * @throws IllegalArgumentException
	 *             if a set of data lines is null
	 */
	public Double[] getDistanceValuesForEpsilonEstimate(List<Line> lines)
			throws IllegalArgumentException {
		if (lines != null) {
			final Double[] kDistancePlotValues = new Double[lines.size()];
			for (int i = 0; i < lines.size(); i++) {
				kDistancePlotValues[i] = calculateKDistance(lines,
						lines.get(i), countMinPts(Object3D.SPACE_DIMENSION));
			}
			Arrays.sort(kDistancePlotValues, new Comparator<Double>() {
				@Override
				public int compare(Double o1, Double o2) {
					return -Double.compare(o1, o2);
				}
			});
			return kDistancePlotValues;
		}

		String errorMessage = "The dataset argument is not valid.";
		throw new IllegalArgumentException(errorMessage);
	}
}
