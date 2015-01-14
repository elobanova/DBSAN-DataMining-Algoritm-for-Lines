package DataStructures;

/**
 * Simple class to store line information
 *
 */
public class Line {
	public final static int NO_CLUSTER_ASSIGNED = -1;

	// Absolute coordinates of the line end points
	private final float m_absX;
	private final float m_absY;
	private final float m_absZ;

	// Directional coordinates from the line start point
	private final float m_dirX;
	private final float m_dirY;
	private final float m_dirZ;

	// Assigns cluster to the line
	// -1 - No cluster assigned / outlier
	// >= 0 - Cluster assigned (0,1,2,3,...)
	private int m_clusterIdentifier;

	/**
	 * Line constructor
	 * 
	 * @param absX
	 *            absolute x coordinate
	 * @param absY
	 *            absolute y coordinate
	 * @param absZ
	 *            absolute z coordinate
	 * @param dirX
	 *            directional x coordinate
	 * @param dirY
	 *            directional y coordinate
	 * @param dirZ
	 *            directional z coordinate
	 */
	public Line(float absX, float absY, float absZ, float dirX, float dirY,
			float dirZ) {
		this.m_absX = absX;
		this.m_absY = absY;
		this.m_absZ = absZ;
		this.m_dirX = dirX;
		this.m_dirY = dirY;
		this.m_dirZ = dirZ;

		// Initially no cluster assigned
		this.m_clusterIdentifier = NO_CLUSTER_ASSIGNED;
	}

	/**
	 * Gets the absolute X coordinate
	 * 
	 * @return absolute X coordinate
	 */
	public float getAx() {
		return m_absX;
	}

	/**
	 * Gets the absolute Y coordinate
	 * 
	 * @return absolute Y coordinate
	 */
	public float getAy() {
		return m_absY;
	}

	/**
	 * Gets the absolute Z coordinate
	 * 
	 * @return absolute Z coordinate
	 */
	public float getAz() {
		return m_absZ;
	}

	/**
	 * Gets the directional X coordinate
	 * 
	 * @return directional X coordinate
	 */
	public float getDx() {
		return m_dirX;
	}

	/**
	 * Gets the directional Y coordinate
	 * 
	 * @return directional Y coordinate
	 */
	public float getDy() {
		return m_dirY;
	}

	/**
	 * Gets the directional Z coordinate
	 * 
	 * @return directional Z coordinate
	 */
	public float getDz() {
		return m_dirZ;
	}

	/**
	 * Returns the assigned cluster ID
	 * 
	 * @return cluster ID
	 */
	public int getCluster() {
		return this.m_clusterIdentifier;
	}

	/**
	 * Assigns the line to cluster ID
	 * 
	 * @param cluster
	 *            the given cluster ID
	 */
	public void setCluster(int cluster) {
		this.m_clusterIdentifier = cluster;
	}

	/**
	 * Returns the 3-dimensional starting point of this line
	 * 
	 * @return starting 3D-point
	 */
	public Object3D getStartingPoint() {
		return new Object3D(getAx(), getAy(), getAz());
	}

	/**
	 * Returns the 3-dimensional ending point of this line
	 * 
	 * @return ending 3D-point
	 */
	public Object3D getEndingPoint() {
		return new Object3D(getAx() - getDx(), getAy() - getDy(), getAz()
				- getDz());
	}

	public boolean isParallelTo(Line otherLine) {
		if (otherLine.getDx() == 0) {
			
		}
		
		
		float xRelation = getDx() / otherLine.getDx();
		float yRelation = getDy() / otherLine.getDy();
		float zRelation = getDz() / otherLine
				.getDz();
		return xRelation == yRelation && yRelation == zRelation;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Line))
			return false;
		Line otherLine = (Line) other;
		return this.m_absX == otherLine.m_absX
				&& this.m_absY == otherLine.m_absY
				&& this.m_absZ == otherLine.m_absZ
				&& this.m_dirX == otherLine.m_dirX
				&& this.m_dirY == otherLine.m_dirY
				&& this.m_dirZ == otherLine.m_dirZ
				&& this.m_clusterIdentifier == otherLine.m_clusterIdentifier;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (int) this.m_absX;
		hash = 53 * hash + (int) this.m_absY;
		hash = 53 * hash + (int) this.m_absZ;
		hash = 53 * hash + (int) this.m_dirX;
		hash = 53 * hash + (int) this.m_dirY;
		hash = 53 * hash + (int) this.m_dirZ;
		return hash;
	}
}
