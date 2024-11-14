package edu.westga.medmyst.project.model;

/**
 * The TestType class
 * @author demmons1
 * @version Fall 2024
 */
public class TestType {
	private String typeName;
	private String description;
	
	/**
	 * Creates a new TestType
	 * @param typeName the type name
	 * @param description the description
	 */
	 public TestType(String typeName, String description) {
	        this.typeName = typeName;
	        this.description = description;
	    }

	 /**
	  * Gets the type name
	  * @return the typeName
	  */
	    public String getTypeName() {
	    	return typeName; 
	    }
	    
	    /**
	     * Sets the type name
	     * @param typeName the new typeName
	     */
	    public void setTypeName(String typeName) {
	    	this.typeName = typeName; 
	    }

	    /**
	     * Gets the description
	     * @return the description
	     */
	    public String getDescription() {
	    	return description; 
	    }
	    
	    /**
	     * sets the description
	     * @param description the new description
	     */
	    public void setDescription(String description) {
	    	this.description = description; 
	    }
}
