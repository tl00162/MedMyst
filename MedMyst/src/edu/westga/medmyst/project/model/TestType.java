package edu.westga.medmyst.project.model;

/**
 * The TestType class
 * 
 * @author demmons1
 * @version Fall 2024
 */
public class TestType {
	private String typeName;
	private String description;
	private double high;
	private double low;
	private String unit;

	/**
	 * Creates a new TestType
	 * 
	 * @param typeName    the type name
	 * @param description the description
	 */
	public TestType(String typeName, String description, double low, double high, String unit) {
		this.typeName = typeName;
		this.description = description;
		this.low = low;
		this.high = high;
		this.unit = unit;
	}

	/**
	 * Gets the type name
	 * 
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Sets the type name
	 * 
	 * @param typeName the new typeName
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * Gets the description
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * sets the description
	 * 
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the low threshold value for the test.
	 * 
	 * @return the low threshold value
	 */
	public double getLow() {
		return this.low;
	}

	/**
	 * Gets the high threshold value for the test.
	 * 
	 * @return the high threshold value
	 */
	public double getHigh() {
		return this.high;
	}

	/**
	 * Gets the unit of measurement for the test.
	 * 
	 * @return the unit of measurement
	 */
	public String getUnit() {
		return this.unit;
	}

	@Override
	public String toString() {
		return this.typeName;
	}
}
